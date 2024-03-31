package org.shoppingMall.web.DTO.cart;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductResponse {
    private List<CartResponseList> cartResponseLists;
    private Integer totalQuantity;
    private Integer cartTotalPrice;


}
