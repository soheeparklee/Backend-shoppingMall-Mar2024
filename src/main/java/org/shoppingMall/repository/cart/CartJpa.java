package org.shoppingMall.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJpa implements JpaRepository<Cart, Integer> {
}
 586ea96