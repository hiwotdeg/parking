package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.ReviewSearchRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ResponseModel;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewSearch;
import et.com.gebeya.parkinglotservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/lots/{parkingLotId}/reviews") // driver
    public ResponseEntity<ReviewResponseDto> addReview(@Valid @RequestBody AddReviewRequestDto addReviewRequestDto, @PathVariable("parkingLotId") Integer parkingLotId) {
        return ResponseEntity.ok(reviewService.createReviewForParkingLot(addReviewRequestDto, parkingLotId));
    }

    @PatchMapping("/lots/{parkingLotId}/reviews/{reviewId}") // driver
    public ResponseEntity<ReviewResponseDto> updateReview(@Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto,
                                                          @PathVariable("parkingLotId") Integer parkingLotId,
                                                          @PathVariable("reviewId") Integer reviewId) {
        return ResponseEntity.ok(reviewService.updateReviewForParkingLot(updateReviewRequestDto, parkingLotId, reviewId));
    }

    @DeleteMapping("/lots/{parkingLotId}/reviews/{reviewId}") // driver
    public ResponseEntity<ResponseModel> deleteReview(@PathVariable("parkingLotId") Integer parkingLotId, @PathVariable("reviewId") Integer reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(parkingLotId, reviewId));
    }

    @GetMapping("/lots/{parkingLotId}/reviews") // driver, provider
    public ResponseEntity<List<ReviewSearch>> getReviews(@PathVariable("parkingLotId") Integer parkingLotId, @ModelAttribute ReviewSearchRequestDto reviewSearchRequestDto) {
        return ResponseEntity.ok(reviewService.getReviews(reviewSearchRequestDto, parkingLotId));
    }

    @GetMapping("/reviews") // admin
    public ResponseEntity<List<ReviewSearch>> getAllReviews(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviews(pageable));
    }
}
