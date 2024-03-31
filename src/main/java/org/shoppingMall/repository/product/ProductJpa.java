package org.shoppingMall.repository.product;

import org.shoppingMall.web.DTO.product.ProductMainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpa extends JpaRepository<Product, Integer> {

    @Query(
            "SELECT new org.shoppingMall.web.DTO.product.ProductMainResponse(" +
                    "p.productId, p.user.userId, p.productName, p.productPrice, p.productStatus, p.category,  p.createdAt, pp.photoUrl, COUNT(DISTINCT r.reviewId), AVG(r.score)) " +
                    "FROM Product p " +
                    "LEFT JOIN p.productPhotos pp " +
                    "LEFT JOIN p.reviews r " +
                    "WHERE pp.photoType = true AND p.productStatus = 'sell' " +
                    "GROUP BY p.productId, p.user.userId, p.productName, p.productPrice, p.productStatus, p.category, p.createdAt, pp.photoUrl"
    )
    Page<ProductMainResponse> findAllProducts(Pageable pageable);
}
