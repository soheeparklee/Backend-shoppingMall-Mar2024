package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.product.Product;
import org.shoppingMall.repository.product.ProductJpa;
import org.shoppingMall.repository.review.Review;
import org.shoppingMall.repository.review.ReviewJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.review.ReviewRequest;
import org.shoppingMall.web.DTO.review.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserJpa userJpa;
    private final ProductJpa productJpa;
    private final ReviewJpa reviewJpa;
    public ResponseDTO registerReview(CustomUserDetails customUserDetails, Integer productId, ReviewRequest reviewRequest) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email:" + customUserDetails.getEmail()));

        Product product= productJpa.findById(productId)
                .orElseThrow(()-> new NotFoundException("Cannot find product with id: "+ productId));

        Review review= Review.builder()
                .user(user)
                .product(product)
                .reviewContents(reviewRequest.getReviewContents())
                .score(reviewRequest.getScore())
                .createdAt(LocalDateTime.now())
                .build();
        reviewJpa.save(review);

        ReviewResponse reviewResponse= ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .userName(review.getUser().getNickName())
                .productName(product.getProductName())
                .reviewContents(review.getReviewContents())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .build();
        return new ResponseDTO(HttpStatus.OK.value(), "Review Register Success", reviewResponse);
    }
}
