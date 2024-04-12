package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.ReviewService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.review.ReviewRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary="리뷰 등록")
    @PostMapping("/register/{productId}")
    public ResponseDTO registerReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @PathVariable Integer productId,
                                      @RequestBody ReviewRequest reviewRequest){
        return reviewService.registerReview(customUserDetails, productId, reviewRequest);
    }

    @Operation(summary="리뷰 수정")
    @PutMapping("/update/{reviewId}")
    public ResponseDTO updateReview(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @PathVariable Integer reviewId,
                                    @RequestBody ReviewRequest reviewRequest){
        return reviewService.updateReview(customUserDetails, reviewId, reviewRequest);
    }

}
