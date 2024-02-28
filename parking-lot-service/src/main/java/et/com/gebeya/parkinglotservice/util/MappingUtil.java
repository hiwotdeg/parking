package et.com.gebeya.parkinglotservice.util;

import et.com.gebeya.parkinglotservice.dto.requestdto.*;
import et.com.gebeya.parkinglotservice.dto.responsedto.*;
import et.com.gebeya.parkinglotservice.enums.Authority;
import et.com.gebeya.parkinglotservice.enums.ParkingLotRole;
import et.com.gebeya.parkinglotservice.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MappingUtil {
    private MappingUtil() {
    }

    public static AddUserRequest mapParkingLotProviderToAddUserRequest(ParkingLotProvider provider) {
        return AddUserRequest.builder()
                .phoneNo(provider.getPhoneNo())
                .role(Authority.PROVIDER)
                .roleId(provider.getId())
                .build();
    }

    public static Driver mapAddDiverRequestDtoToDriver(AddDriverRequestDto dto) {
        return Driver.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNo(dto.getPhoneNo())
                .email(dto.getEmail())
                .isActive(true).imageUrl("")
                .build();
    }

    public static Driver mapUpdateDiverRequestDtoToDriver(UpdateDriverRequestDto dto, Driver driver) {
        if (dto.getFirstName() != null)
            driver.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            driver.setLastName(dto.getLastName());
        if (dto.getEmail() != null)
            driver.setEmail(dto.getEmail());
        if (dto.getPhoneNo() != null)
            driver.setEmail(dto.getEmail());
        if (dto.getImageUrl() != null)
            driver.setImageUrl(dto.getImageUrl());
        return driver;
    }

    public static DriverResponseDto mapDriverToDriverResponseDto(Driver driver) {
        return DriverResponseDto.builder()
                .id(driver.getId())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .email(driver.getEmail())
                .phoneNo(driver.getPhoneNo())
                .imageUrl(driver.getImageUrl())
                .build();
    }

    public static AddUserRequest mapCustomerToAddUserRequest(Driver driver) {
        return AddUserRequest.builder()
                .phoneNo(driver.getPhoneNo())
                .role(Authority.USER)
                .roleId((driver.getId()))
                .build();
    }

    public static ParkingLotProvider mapToParkingLotProvider(AddUserRequest request) {
        ParkingLotProvider provider = new ParkingLotProvider();
        provider.setPhoneNo(request.getPhoneNo());
        return provider;
    }

    public static ParkingLot mapAddParkingLotToParkingLot(AddParkingLotDto parkingLotRequest) {
        return ParkingLot.builder().name(parkingLotRequest.getName())
                .address(parkingLotRequest.getAddress())
                .rating(5.0f)
                .availableSlot(parkingLotRequest.getCapacity())
                .isActive(true)
                .latitude(parkingLotRequest.getLatitude())
                .longitude(parkingLotRequest.getLongitude())
                .parkingLotImageLink(mapStringToParkingLotImage(parkingLotRequest.getImageUrl()))
                .capacity(parkingLotRequest.getCapacity())
                .parkingType(parkingLotRequest.getParkingType()).build();
    }

    private static List<ParkingLotImage> mapStringToParkingLotImage(List<String> images) {
        List<ParkingLotImage> imageList = new ArrayList<>();
        images.forEach(request -> imageList.add(ParkingLotImage.builder().imageUrl(request).build()));
        return imageList;
    }

    public static ParkingLot updateParkingLot(ParkingLot parkingLot, UpdateParkingLotDto dto) {
//        if (!dto.getImageUrl().isEmpty())
//            parkingLot.setParkingLotImageLink(mapStringToParkingLotImage(dto.getImageUrl()));
        if (dto.getAvailableSlot() != null)
            parkingLot.setAvailableSlot(dto.getAvailableSlot());
        if (dto.getName() != null)
            parkingLot.setName(dto.getName());
        if (dto.getAddress() != null)
            parkingLot.setAddress(dto.getAddress());
        if (dto.getLatitude() != null)
            parkingLot.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null)
            parkingLot.setLongitude(dto.getLongitude());
        if (dto.getCapacity() != null)
            parkingLot.setCapacity(dto.getCapacity());
        if (dto.getParkingType() != null)
            parkingLot.setParkingType(dto.getParkingType());
        return parkingLot;
    }

    public static ParkingLotResponseDto parkingLotResponse(ParkingLot parkingLot) {
        return ParkingLotResponseDto.builder().id(parkingLot.getId())
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .capacity(parkingLot.getCapacity())
                .images(mapParkingLotImageToString(parkingLot.getParkingLotImageLink()))
                .availableSlot(parkingLot.getAvailableSlot())
                .parkingType(parkingLot.getParkingType())
                .rating(parkingLot.getRating())
                .build();

    }

    private static List<String> mapParkingLotImageToString(List<ParkingLotImage> parkingLotImages) {
        List<String> images = new ArrayList<>();
        parkingLotImages.forEach(request -> images.add(request.getImageUrl()));
        return images;
    }

    public static ParkingLotProvider mapAddProviderDtoToParkingLotProvider(AddProviderDto dto) {
        return ParkingLotProvider.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNo(dto.getPhoneNo())
                .imageUrl("")
                .isActive(true)
                .isVerified(true)
                .role(ParkingLotRole.ADMIN)
                .build();
    }

    public static Review mapAddReviewRequestDtoToReview(AddReviewRequestDto dto) {
        return Review.builder()
                .rate(dto.getRate())
                .comment(dto.getComment())
                .build();
    }

    public static ProviderResponseDto mapParkingLotProviderToProviderResponseDto(ParkingLotProvider provider) {
        return ProviderResponseDto.builder()
                .id(provider.getId())
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .email(provider.getEmail())
                .phoneNo(provider.getPhoneNo())
                .imageUrl(provider.getImageUrl())
                .role(provider.getRole()).build();
    }

    public static ParkingLotProvider updateParkingLotProvider(UpdateProviderRequestDto dto, ParkingLotProvider provider) {
        if (dto.getFirstName() != null)
            provider.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            provider.setLastName(dto.getLastName());
        if (dto.getEmail() != null)
            provider.setEmail(dto.getEmail());
        if (dto.getPhoneNo() != null)
            provider.setPhoneNo(dto.getPhoneNo());
        if (dto.getImageUrl() != null)
            provider.setImageUrl(dto.getImageUrl());
        return provider;
    }

    private static OperationHour operationHourDtoDtoToOperationHour(ParkingLot parkingLot, OperationHourDto dto) {
        LocalTime endTime = dto.getEndTIme().minusMinutes(1);
        if (dto.getStartTime().getHour() > dto.getEndTIme().getHour()) {
            return OperationHour.builder().pricePerHour(dto.getPrice())
                    .startTime(LocalDateTime.of(2024, 1, 1, dto.getStartTime().getHour(), dto.getStartTime().getMinute()))
                    .endTime(LocalDateTime.of(2024, 1, 2, endTime.getHour(), endTime.getMinute()))
                    .pricePerHour(dto.getPrice())
                    .parkingLot(parkingLot)
                    .build();
        } else {
            return OperationHour.builder().pricePerHour(dto.getPrice())
                    .startTime(LocalDateTime.of(2024, 1, 1, dto.getStartTime().getHour(), dto.getStartTime().getMinute()))
                    .endTime(LocalDateTime.of(2024, 1, 1, endTime.getHour(), endTime.getMinute()))
                    .pricePerHour(dto.getPrice())
                    .parkingLot(parkingLot)
                    .build();
        }

    }

    public static List<OperationHour> addOperationRequestDtoToOperationHour(ParkingLot parkingLot, List<OperationHourDto> dto) {
        List<OperationHour> operationHours = new ArrayList<>();
        dto.forEach(request -> operationHours.add(operationHourDtoDtoToOperationHour(parkingLot, request)));
        return operationHours;
    }

    public static List<OperationHourResponseDto> listOfOperationHourToListOfOperationHourResponseDto(List<OperationHour> operationHour) {
        List<OperationHourResponseDto> operationHourList = new ArrayList<>();
        operationHour.forEach(request -> operationHourList.add(operationHourToOperationHourResponseDto(request)));
        return operationHourList;
    }

    public static OperationHourResponseDto operationHourToOperationHourResponseDto(OperationHour operationHour) {
        return OperationHourResponseDto.builder()
                .id(operationHour.getId())
                .price(operationHour.getPricePerHour())
                .startTime(LocalTime.of(operationHour.getStartTime().getHour(), operationHour.getStartTime().getMinute()))
                .endTime(LocalTime.of(operationHour.getEndTime().getHour(), operationHour.getEndTime().getMinute()))
                .build();
    }

    public static VehicleResponseDto vehicleToVehicleResponseDto(Vehicle vehicle) {
        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .plate(vehicle.getPlate())
                .build();
    }

    public static List<VehicleResponseDto> vehicleToListOfVehicleResponseDto(List<Vehicle> vehicles){
        List<VehicleResponseDto> vehicleResponseDtoList = new ArrayList<>();
        vehicles.forEach(vehicle -> vehicleResponseDtoList.add(vehicleToVehicleResponseDto(vehicle)));
        return vehicleResponseDtoList;
    }

    public static Vehicle vehicleRequestDtoToVehicle(VehicleRequestDto vehicle) {
        return Vehicle.builder()
                .name(vehicle.getName())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .plate(vehicle.getPlate())
                .isActive(true)
                .build();
    }

    public static Vehicle updateVehicle(VehicleRequestDto dto, Vehicle vehicle) {
        if (dto.getName() != null)
            vehicle.setName(dto.getName());
        if (dto.getYear() != null)
            vehicle.setYear(dto.getYear());
        if (dto.getModel() != null)
            vehicle.setModel(dto.getModel());
        if (dto.getPlate() != null)
            vehicle.setPlate(dto.getPlate());
        return vehicle;
    }


}
