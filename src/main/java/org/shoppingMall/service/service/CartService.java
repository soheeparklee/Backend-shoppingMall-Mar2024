package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.cart.Cart;
import org.shoppingMall.repository.cart.CartJpa;
import org.shoppingMall.repository.productOption.ProductOption;
import org.shoppingMall.repository.productOption.ProductOptionJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.InvalidValueException;
import org.shoppingMall.service.exceptions.NotEnoughStockException;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.service.exceptions.SoldOutException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserJpa userJpa;
    private final CartJpa cartJpa;
    private final ProductOptionJpa productOptionJpa;

    public ResponseDTO addToCart(CustomUserDetails customUserDetails, Integer productOptionId, Integer addAmount) {
        User user= userJpa.findByEmailFetchJoin(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: " + customUserDetails.getEmail()));
        ProductOption productOption= productOptionJpa.findById(productOptionId)
                .orElseThrow(()-> new NotFoundException("Cannot find product with ID "+ productOptionId ));
        if(productOption.getProduct().getProductStatus().equals("soldOut")){
            throw new SoldOutException("This product is sold out");
        }

        if(productOption.getStock() < addAmount){
            throw new NotEnoughStockException("Not enough stock. Only "+ productOption.getStock()+ " left");
        }

        Cart cart= Cart.builder()
                .productOption(productOption)
                .user(user)
                .cartAmount(addAmount)
                .build();
        cartJpa.save(cart);

        return new ResponseDTO(HttpStatus.OK.value(), "Product successfully added to cart", cart);
    }
}
