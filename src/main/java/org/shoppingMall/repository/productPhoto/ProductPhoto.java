package org.shoppingMall.repository.productPhoto;

import jakarta.persistence.*;
import lombok.*;
import org.shoppingMall.repository.product.Product;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_photo")
public class ProductPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_photo_id")
    private Integer productPhotoId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "photo_type", nullable = false)
    private Boolean photoType;
}
