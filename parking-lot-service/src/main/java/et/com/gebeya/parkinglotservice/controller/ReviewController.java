package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/lots/{parkingLotId}/reviews")
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody AddReviewRequestDto addReviewRequestDto, @PathVariable("parkingLotId") Integer parkingLotId){
        return ResponseEntity.ok(reviewService.createReviewForParkingLot(addReviewRequestDto ,parkingLotId ));
    }

    @PatchMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> updateReview(@RequestBody UpdateReviewRequestDto updateReviewRequestDto){
        return ResponseEntity.ok(reviewService.updateReviewForParkingLot(updateReviewRequestDto));
    }

//    @GetMapping("lot/{parkingLotId}/reviews")
//    public ResponseEntity<List<ReviewResponseDto>> getReviews(@PathVariable("parkingLotId") Integer parkingLotId){
//
//    }
}
