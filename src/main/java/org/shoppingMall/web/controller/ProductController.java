package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.ProductService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.product.ProductRequest;
import org.springframework.data.domain.Pageable;
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

    @Operation(summary="모든 상품 조회(평균 별점 포함)")
    @GetMapping("/find/main-page")
    public ResponseDTO findAllProducts(Pageable pageable){
        return productService.findAllProducts(pageable);
    }

    @Operation(summary="하나의 상품에 대해 상세조회")
    @GetMapping("/find/detail/{productId}")
    public ResponseDTO findProductDetail(@PathVariable Integer productId){
        return productService.findProductDetail(productId);
    }

    @Operation(summary="카테코리별 조회")
    @GetMapping("/find")
    public ResponseDTO findProductByCategory(@RequestParam("category") String category){
        return productService.findProductByCategory(category);
    }


}
