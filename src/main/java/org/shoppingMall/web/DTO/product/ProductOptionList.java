package org.shoppingMall.web.DTO.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionList {
    private Integer optionId;
    private String color;
    private String productSize;
    private Integer stock;

    public ProductOptionList(String color, String productSize, Integer stock) {
        this.color = color;
        this.productSize = productSize;
        this.stock = stock;
    }
}
