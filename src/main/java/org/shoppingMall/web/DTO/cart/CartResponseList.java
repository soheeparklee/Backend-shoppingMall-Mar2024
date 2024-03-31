package org.shoppingMall.web.DTO.cart;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseList {
    private Integer productId;
    private String productName;
    private String productImg;
    private List<CartProductOptionResponse> cartProductOptionResponseList;
    private int productTotalPrice;

}
