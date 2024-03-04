package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.ReservationRequestDto;
import et.com.gebeya.parkinglotservice.model.Reservation;
import et.com.gebeya.parkinglotservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/lots/{parkingLotId}/reservations")
    public ResponseEntity<Map<String,String>> book(@RequestBody ReservationRequestDto requestDto, @PathVariable("parkingLotId") Integer parkingLotId){
        return ResponseEntity.ok(reservationService.book(parkingLotId,ReservationRequestDto.builder().stayingDuration(requestDto.getStayingDuration()).build()));
    }
}
