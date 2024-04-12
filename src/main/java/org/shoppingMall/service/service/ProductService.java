package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.product.Product;
import org.shoppingMall.repository.product.ProductJpa;
import org.shoppingMall.repository.productOption.ProductOption;
import org.shoppingMall.repository.productOption.ProductOptionJpa;
import org.shoppingMall.repository.productPhoto.ProductPhoto;
import org.shoppingMall.repository.productPhoto.ProductPhotoJpa;
import org.shoppingMall.repository.review.Review;
import org.shoppingMall.repository.review.ReviewJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.service.exceptions.SoldOutException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.product.*;
import org.shoppingMall.web.DTO.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

            List<ProductPhoto> productPhotos = productRequest.getPhotoRequestDto()
                    .stream()
                    .map((pp) -> ProductPhoto.builder()
                            .product(product)
                            .photoUrl(pp.getPhotoUrl())
                            .photoType(pp.getPhotoType())
                            .build())
                    .toList();
            productPhotoJpa.saveAll(productPhotos);

            List<ProductOption> productOptions = productRequest.getProductOptionDto()
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

    public ResponseDTO findAllProducts(Pageable pageable) {
        Page<ProductMainResponse> productMainResponses= productJpa.findAllProducts(pageable);
        if(productMainResponses.isEmpty()) throw new NotFoundException("No Products registered to be sold");

        return new ResponseDTO(HttpStatus.OK.value(), "Main page find success", productMainResponses);

    }

    public ResponseDTO findProductDetail(Integer productId) {
        Product product= productJpa.findById(productId)
                .orElseThrow(()-> new NotFoundException("Cannot find product with Id: "+ productId));

        if(product.getProductStatus().equals("soldOut")) throw new SoldOutException("This product is sold out.");

        List<ProductPhoto> productPhotos= productPhotoJpa.findByProduct(product);
        List<PhotoRequestDto> photoRequestDtoList = productPhotos
                .stream()
                .map((p)-> new PhotoRequestDto(
                        p.getProductPhotoId(),
                        p.getPhotoUrl(),
                        p.getPhotoType()
                ))
                .toList();

        List<ProductOption> productOptions= productOptionJpa.findByProduct(product);
        List<ProductOptionDto> productOptionDtoList= productOptions
                .stream()
                .map((po)-> new ProductOptionDto(
                        po.getProductOptionId(),
                        po.getColor(),
                        po.getProductSize(),
                        po.getStock()
                )).toList();

        List<Review> reviews= reviewJpa.findByProduct(product);
        List<ReviewResponse> reviewResponseList= reviews
                .stream()
                .map((r)-> new ReviewResponse(
                        r.getReviewId(),
                        r.getUser().getNickName(),
                        r.getProduct().getProductName(),
                        r.getReviewContents(),
                        r.getScore(),
                        r.getCreatedAt()
                )).toList();

        Double scoreAvg= reviews.stream().collect(Collectors.averagingDouble(Review::getScore));

        ProductDetailResponse productDetailResponse= ProductDetailResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .category(product.getCategory())
                .productStatus(product.getProductStatus())
                .createAt(product.getCreatedAt())
                .finishAt(product.getFinishedAt())
                .scoreAvg(scoreAvg)
                .photoRequestDtos(photoRequestDtoList)
                .productOptionDtos(productOptionDtoList)
                .reviewResponseLists(reviewResponseList)
                .build();

        return new ResponseDTO(HttpStatus.OK.value(), "Product detail find success", productDetailResponse);
    }
}
