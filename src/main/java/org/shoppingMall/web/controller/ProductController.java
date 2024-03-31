package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.ProductService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.product.ProductRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Operation(summary="상품 등록")
    @PostMapping("/register")
    public ResponseDTO registerProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                       @RequestBody ProductRequest productRequest){
    return productService.registerProduct(customUserDetails, productRequest);
    }
}