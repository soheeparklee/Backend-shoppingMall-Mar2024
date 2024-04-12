package org.shoppingMall.web.DTO.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMainResponse {
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private String category;
    private String productStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createAt;
    private String mainPhotoUrl;
    private Integer reviewCount;
    private Float scoreAvg;

    public ProductMainResponse(Integer productId, String productName, Integer productPrice, String category, String productStatus, LocalDateTime createAt, String mainPhotoUrl, Long reviewCount, Double scoreAvg) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
        this.productStatus = productStatus;
        this.createAt = createAt;
        this.mainPhotoUrl = mainPhotoUrl;
        this.reviewCount = reviewCount != null ? reviewCount.intValue() : 0;
        this.scoreAvg = scoreAvg != null? scoreAvg.floatValue() : 0;
    }
}
