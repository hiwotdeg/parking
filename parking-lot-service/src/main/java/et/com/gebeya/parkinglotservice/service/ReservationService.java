package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.*;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReservationResponseDto;
import et.com.gebeya.parkinglotservice.enums.ReservationStatus;
import et.com.gebeya.parkinglotservice.exception.*;
import et.com.gebeya.parkinglotservice.model.*;
import et.com.gebeya.parkinglotservice.repository.ReservationRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ReservationSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import et.com.gebeya.parkinglotservice.util.MessagingUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingLotService parkingLotService;
    private final PricingService pricingService;
    private final DriverService driverService;
    private final WebClient.Builder webClientBuilder;
    private final MessageService messageService;
    private final VehicleService vehicleService;

    @Transactional
    @CircuitBreaker(name = "default", fallbackMethod = "fallBackMethod")
    @Retry(name = "default")

    public Map<String, String> book(Integer parkingLotId, ReservationRequestDto dto) {
        UserDto driverId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = driverService.getDriver(driverId.getId());
        ParkingLot parkingLot = parkingLotService.getParkingLot(parkingLotId);
        if (parkingLot.getAvailableSlot() <= 0)
            throw new ParkingLotAvailabilityException("the parking lot that you selected is full at the moment");
        ParkingLotProvider provider = parkingLot.getParkingLotProvider();
        PriceRequestDto requestDto = PriceRequestDto.builder().duration(dto.getStayingDuration()).build();
        BigDecimal price = pricingService.dynamicPricing(requestDto, parkingLotId);
        Reservation reservation = Reservation.builder().price(price).parkingLot(parkingLot).driver(driver).stayingDuration(dto.getStayingDuration()).reservationStatus(ReservationStatus.PENDING).isActive(true).build();
        checkBalanceForDriver(driverId.getId(), price);
        Vehicle vehicle = vehicleService.getVehicle(dto.getVehicleId());
        reservation.setVehicle(vehicle);
        reservationRepository.save(reservation);
        notifyBooking(provider.getId(), driverId.getId(), parkingLot.getName(), dto.getStayingDuration());
        return Map.of("message", "you have reserved a parking lot successfully");

    }


    public Mono<BalanceResponseDto> transferBalance(TransferBalanceRequestDto transferBalanceRequestDto) {
        return webClientBuilder.build()
                .post()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/transfer")
                .bodyValue(transferBalanceRequestDto)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class);
    }


    private Mono<BalanceResponseDto> checkBalance(Integer driverId) {
        return webClientBuilder.build().get()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/driver_balance/" + driverId)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class);
    }


    private Map<String, String> fallBackMethod(Throwable throwable) {
        log.error("fallback error=>{}, message=>{}", throwable.getClass(), throwable.getMessage());
        if (throwable instanceof InsufficientBalance) {
            throw new InsufficientBalance(throwable.getMessage());
        } else if (throwable instanceof WebClientResponseException.BadRequest) {
            throw new ClientErrorException(((WebClientResponseException.BadRequest) throwable).getResponseBodyAsString());
        }
        throw new RuntimeException(throwable.getMessage());
    }

    private void checkBalanceForDriver(Integer driverId, BigDecimal desiredAmount) {
        BalanceResponseDto balanceResponseDto = checkBalance(driverId).block();
        assert balanceResponseDto != null;
        if (balanceResponseDto.getBalance().compareTo(desiredAmount) <= 0)
            throw new InsufficientBalance("You have insufficient balance. please purchase coupons");
    }


    private void notifyBooking(Integer providerId, Integer driverId, String parkingLotName, LocalTime duration) {
        messageService.sendPushNotificationForDriver(driverId, MessagingUtil.driverBookNotification(parkingLotName, duration));
        messageService.sendPushNotificationForProvider(providerId, MessagingUtil.providerWithdrawalNotification());
    }

    public List<ReservationResponseDto> getReservationByProviderId() {
        UserDto providerId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Reservation> reservationList = reservationRepository.findAll(ReservationSpecification.getReservationByProviderId(providerId.getId()));
        return MappingUtil.mapListOfReservationToReservationResponseDto(reservationList);
    }

    @Transactional
    @CircuitBreaker(name = "auth", fallbackMethod = "fallBackMethod")
    @Retry(name = "auth")
    public ReservationResponseDto updateReservation(Integer reservationId, UpdateReservation updateReservation) {
        Reservation reservation = getActiveReservationsById(reservationId);
        if (isFiveMinutesDifference(reservation.getCreatedOn())) {
            reservation.setReservationStatus(ReservationStatus.REJECTED);
            reservationRepository.save(reservation);
            throw new ReservationUpdateAfterFiveMinuteException("Reservation request must be accepted or rejected within 5 minutes of submission");
        }
        if (updateReservation.getReservationStatus().equals(ReservationStatus.ACCEPTED)) {
            reservation.setReservationStatus(updateReservation.getReservationStatus());
            ParkingLotProvider provider = reservation.getParkingLot().getParkingLotProvider();
            TransferBalanceRequestDto transferBalanceRequestDto = TransferBalanceRequestDto.builder().providerId(provider.getId()).driverId(reservation.getDriver().getId()).amount(reservation.getPrice()).build();
            Mono<BalanceResponseDto> balanceResponseDtoMono = transferBalance(transferBalanceRequestDto);
            BalanceResponseDto balanceResponseDto = balanceResponseDtoMono.block();
            log.info("Response from the paymentService==> {}", balanceResponseDto);
            reservation = reservationRepository.save(reservation);
            messageService.sendPushNotificationForDriver(reservation.getDriver().getId(), MessagingUtil.driverBookNotification(reservation.getParkingLot().getName(), reservation.getStayingDuration()));
            return MappingUtil.mapReservationToReservationResponseDto(reservation);
        } else {
            reservation.setReservationStatus(updateReservation.getReservationStatus());
            reservation = reservationRepository.save(reservation);
            return MappingUtil.mapReservationToReservationResponseDto(reservation);
        }
    }


    private Reservation getActiveReservationsById(Integer id) {
        List<Reservation> reservations = reservationRepository.findAll(ReservationSpecification.getReservationById(id));
        if (reservations.isEmpty())
            throw new ActiveReservationNotFound("Active Reservation not found");
        return reservations.get(0);

    }

    private static boolean isFiveMinutesDifference(Instant givenInstant) {
        Instant currentInstant = Instant.now();
        Duration duration = Duration.between(givenInstant, currentInstant);
        return duration.compareTo(Duration.ofMinutes(5)) >= 0;
    }

    public Map<String, String> cancelReservation(Integer reservationId) {
        Reservation reservation = getActiveReservationsById(reservationId);
        if (reservation.getReservationStatus().equals(ReservationStatus.PENDING) && reservation.getIsActive().equals(true)) {
            reservation.setIsActive(false);
            reservationRepository.save(reservation);

        } else if (reservation.getReservationStatus().equals(ReservationStatus.ACCEPTED) && reservation.getIsActive().equals(true)) {
            reservation.setIsActive(false);
            reservationRepository.save(reservation);
            messageService.sendPushNotificationForDriver(reservation.getDriver().getId(), MessagingUtil.cancelReservationNotificationForDriver(reservation.getParkingLot().getName()));
        } else {
            throw new CancelReservationException("cancel reservation allowed for active accepted and active pending reservations only");
        }
        return Map.of("message", "you have cancelled your reservation");
    }

    public List<ReservationResponseDto> getAllReservation(Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll(pageable).stream().toList();
        return MappingUtil.mapListOfReservationToReservationResponseDto(reservations);
    }
}
