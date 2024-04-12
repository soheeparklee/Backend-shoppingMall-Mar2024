package org.shoppingMall.web.DTO.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.shoppingMall.web.DTO.review.ReviewResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ProductDetailResponse {
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private String category;
    private String productStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime finishAt;
    private Double scoreAvg;
    private List<PhotoRequestDto> photoRequestDtos;
    private List<ProductOptionDto> productOptionDtos;
    private List<ReviewResponse> reviewResponseLists;

    public ProductDetailResponse(Integer productId, String productName, Integer productPrice, String category, String productStatus, LocalDateTime createAt, LocalDateTime finishAt, Double scoreAvg, List<PhotoRequestDto> photoRequestDtos, List<ProductOptionDto> productOptionDtos, List<ReviewResponse> reviewResponseLists) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
        this.productStatus = productStatus;
        this.createAt = createAt;
        this.finishAt = finishAt == null ? createAt.plusYears(1) : finishAt;
        this.scoreAvg = scoreAvg;
        this.photoRequestDtos = photoRequestDtos;
        this.productOptionDtos = productOptionDtos;
        this.reviewResponseLists = reviewResponseLists;
    }
}


