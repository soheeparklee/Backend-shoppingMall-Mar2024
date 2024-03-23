package org.shoppingMall.repository.productPhoto;

import org.shoppingMall.repository.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPhotoJpa extends JpaRepository<ProductPhoto, Integer> {
}
