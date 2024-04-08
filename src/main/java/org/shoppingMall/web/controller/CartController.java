package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.CartService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary="장바구니에 상품 추가")
    @PostMapping("/add/{productOptionId}/{addAmount}")
    public ResponseDTO addToCart(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @PathVariable Integer productOptionId,
                                 @PathVariable Integer addAmount){
       return cartService.addToCart(customUserDetails, productOptionId, addAmount);
    }
}
