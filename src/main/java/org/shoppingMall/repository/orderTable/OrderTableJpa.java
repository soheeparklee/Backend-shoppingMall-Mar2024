package org.shoppingMall.repository.orderTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTableJpa extends JpaRepository<OrderTable, Integer> {
}
