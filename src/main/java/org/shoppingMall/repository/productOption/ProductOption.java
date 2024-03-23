package org.shoppingMall.repository.productOption;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_option")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Integer productOptionId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "product_size", nullable = false)
    private String productSize;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
