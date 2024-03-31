package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.product.Product;
import org.shoppingMall.repository.product.ProductJpa;
import org.shoppingMall.repository.productOption.ProductOption;
import org.shoppingMall.repository.productOption.ProductOptionJpa;
import org.shoppingMall.repository.productPhoto.ProductPhoto;
import org.shoppingMall.repository.productPhoto.ProductPhotoJpa;
import org.shoppingMall.repository.review.ReviewJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.product.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserJpa userJpa;
    private final ProductJpa productJpa;
    private final ProductPhotoJpa productPhotoJpa;
    private final ProductOptionJpa productOptionJpa;
    private final ReviewJpa reviewJpa;
    public ResponseDTO registerProduct(CustomUserDetails customUserDetails, ProductRequest productRequest) {
        try {
            User user = userJpa.findByEmail(customUserDetails.getEmail())
                    .orElseThrow(() -> new NotFoundException("Cannot find user with email: " + customUserDetails.getEmail()));
            Product product = Product.builder()
                    .user(user)
                    .productName(productRequest.getProductName())
                    .productPrice(productRequest.getProductPrice())
                    .category(productRequest.getCategory())
                    .productStatus(productRequest.getProductStatus())
                    .rating(0F)
                    .createdAt(LocalDateTime.now())
                    .build();
            productJpa.save(product);

            List<ProductPhoto> productPhotos = productRequest.getPhotoRequestList()
                    .stream()
                    .map((pp) -> ProductPhoto.builder()
                            .product(product)
                            .photoUrl(pp.getPhotoUrl())
                            .photoType(pp.getPhotoType())
                            .build())
                    .toList();
            productPhotoJpa.saveAll(productPhotos);

            List<ProductOption> productOptions = productRequest.getProductOptionList()
                    .stream()
                    .map((po) -> ProductOption.builder()
                            .product(product)
                            .color(po.getColor())
                            .productSize(po.getProductSize())
                            .stock(po.getStock())
                            .build())
                    .toList(); //.collect(Collectors.toList()) ???
            productOptionJpa.saveAll(productOptions);

            return new ResponseDTO(HttpStatus.OK.value(), "Product register success");
        }catch(NullPointerException npe){
            npe.printStackTrace();
            throw new NullPointerException("No token"); //run, postman모두에 보임
        }
    }
}
