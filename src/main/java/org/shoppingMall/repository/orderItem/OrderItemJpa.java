package org.shoppingMall.repository.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemJpa extends JpaRepository<OrderItem, Integer> {
}
