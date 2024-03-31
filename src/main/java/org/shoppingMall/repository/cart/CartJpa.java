package org.shoppingMall.repository.cart;

import org.shoppingMall.repository.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartJpa extends JpaRepository<Cart, Integer> {


    List<Cart> findByUser(User user);
}