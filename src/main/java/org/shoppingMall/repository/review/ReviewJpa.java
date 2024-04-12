package org.shoppingMall.repository.review;

import org.shoppingMall.repository.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJpa extends JpaRepository<Review, Integer> {
    List<Review> findByProduct(Product product);
}
