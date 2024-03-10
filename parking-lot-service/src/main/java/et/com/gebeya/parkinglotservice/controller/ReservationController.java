package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.ReservationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReservation;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReservationResponseDto;
import et.com.gebeya.parkinglotservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/lots/{parkingLotId}/reservations") // driver
    public ResponseEntity<Map<String, String>> book(@Valid @RequestBody ReservationRequestDto requestDto, @PathVariable("parkingLotId") Integer parkingLotId) {
        return ResponseEntity.ok(reservationService.book(parkingLotId, requestDto));
    }

    @PostMapping("/reservations/{reservationId}/requests") // provider
    public ResponseEntity<ReservationResponseDto> updateStatus(@PathVariable("reservationId") Integer reservationId, @Valid @RequestBody UpdateReservation dto) {
        return ResponseEntity.ok(reservationService.updateReservation(reservationId, dto));
    }

    @PostMapping("/reservations/{reservationId}/cancel") // driver
    public ResponseEntity<Map<String, String>> cancelReservation(@PathVariable("reservationId") Integer reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @GetMapping("/reservations/my") // provider
    public ResponseEntity<List<ReservationResponseDto>> getReservationByProviderID() {
        return ResponseEntity.ok(reservationService.getReservationByProviderId());
    }

    @GetMapping("/reservations") // admin
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(reservationService.getAllReservation(pageable));
    }

}
