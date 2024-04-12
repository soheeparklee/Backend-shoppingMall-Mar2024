package org.shoppingMall.repository.productPhoto;

import org.shoppingMall.repository.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoJpa extends JpaRepository<ProductPhoto, Integer> {
    List<ProductPhoto> findByProduct(Product product);
}
