package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.cart.Cart;
import org.shoppingMall.repository.cart.CartJpa;
import org.shoppingMall.repository.product.Product;
import org.shoppingMall.repository.productPhoto.ProductPhoto;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.cart.CartProductOptionResponse;
import org.shoppingMall.web.DTO.cart.CartProductResponse;
import org.shoppingMall.web.DTO.cart.CartResponseList;
import org.shoppingMall.web.DTO.user.UserRequest;
import org.shoppingMall.web.DTO.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpa userJpa;
    private final CartJpa cartJpa;

    public ResponseDTO findUserByToken(CustomUserDetails customUserDetails) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: "+ customUserDetails.getEmail()));

        UserResponse userResponse= UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .profileImg(user.getProfileImg())
                .address(user.getAddress())
                .gender(user.getGender())
                .status(user.getStatus())
                .failureCount(user.getFailureCount())
                .build();
                return new ResponseDTO(HttpStatus.OK.value(), "Find user "+ user.getNickName() + " success", userResponse);
    }

    public ResponseDTO updateUser(CustomUserDetails customUserDetails, UserRequest userRequest) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: "+ customUserDetails.getEmail()));

        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setNickName(userRequest.getNickName());
        user.setPassword(userRequest.getPassword());
        userJpa.save(user);
        return new ResponseDTO(HttpStatus.OK.value(), "User info update success");

    }

    public ResponseDTO getUserCart(CustomUserDetails customUserDetails) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: "+ customUserDetails.getEmail()));
        List<Cart> carts= cartJpa.findByUser(user);
        if(carts.isEmpty()){
            return new ResponseDTO(HttpStatus.OK.value(), "Cart is empty");
        }
        List<CartResponseList> cartResponseLists= carts.stream()
                .filter(cart -> !cart.getProductOption().getProduct().getProductStatus().equals("SoldOut"))
                .map((cart)->{

                    Product product= cart.getProductOption().getProduct();

                    List<String> productImgUrl= product.getProductPhotos()
                            .stream()
                            .filter(ProductPhoto::getPhotoType)
                            .map(ProductPhoto::getPhotoUrl)
                            .collect(Collectors.toList());

                    CartProductOptionResponse cartProductOptionResponse = CartProductOptionResponse
                            .builder()
                            .optionId(cart.getProductOption().getProductOptionId())
                            .color(cart.getProductOption().getColor())
                            .size(cart.getProductOption().getProductSize())
                            .price(product.getProductPrice())
                            .quantity(cart.getProductOption().getStock())
                            .build();

                    CartResponseList cartResponseList= CartResponseList
                            .builder()
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productImg(productImgUrl.get(0))
                            .cartProductOptionResponseList(List.of(cartProductOptionResponse))
                            .productTotalPrice(cartProductOptionResponse.getPrice()  * cartProductOptionResponse.getQuantity())
                            .build();
                    return cartResponseList;

                })
                .collect(Collectors.toList());

        Integer totalQuantity= carts.stream().mapToInt(Cart::getCartAmount).sum();
        Integer cartTotalPrice= cartResponseLists.stream().mapToInt(CartResponseList::getProductTotalPrice).sum();

        CartProductResponse cartProductResponse= CartProductResponse
                .builder()
                .cartResponseLists(cartResponseLists)
                .totalQuantity(totalQuantity)
                .cartTotalPrice(cartTotalPrice)
                .build();
        return new ResponseDTO(HttpStatus.OK.value(), "Find user cart success ", cartProductResponse);

    }
}
