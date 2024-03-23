package org.shoppingMall.repository.review;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingMall.repository.product.Product;
import org.shoppingMall.repository.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "review_contents")
    private String reviewContents;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;
}
