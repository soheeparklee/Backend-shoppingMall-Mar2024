package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.UserService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.user.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class UserController {
    private final UserService userService;
    @Operation(summary="토큰으로 가입정보 조회")
    @GetMapping("/find")
    public ResponseDTO findUserByToken(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            return userService.findUserByToken(customUserDetails);
        } else {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "CustomUserDetails is null", null);
        }
    }

    @Operation(summary = "유저 정보 수정")
    @PutMapping("/update")
    public ResponseDTO updateUser(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @RequestBody UserRequest userRequest){
        return userService.updateUser(customUserDetails, userRequest);
    }
}
