package org.shoppingMall.repository.cart;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingMall.repository.productOption.ProductOption;
import org.shoppingMall.repository.user.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cart_amount", nullable = false)
    private Integer cartAmount;
}
