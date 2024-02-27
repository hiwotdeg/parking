package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody AddReviewRequestDto addReviewRequestDto){
        return ResponseEntity.ok(reviewService.createReviewForParkingLot(addReviewRequestDto));
    }

    @PatchMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> updateReview(@RequestBody UpdateReviewRequestDto updateReviewRequestDto){
        return ResponseEntity.ok(reviewService.updateReviewForParkingLot(updateReviewRequestDto));
    }
}
