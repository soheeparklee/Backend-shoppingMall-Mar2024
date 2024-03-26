package org.shoppingMall.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.service.UserService;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/my-page")
public class UserController {
    private final UserService userService;
    @Operation(summary="토큰으로 가입정보 조회")
    @GetMapping("/find/{userEmail}")
    public ResponseDTO findUserByToken(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
            return userService.findUserByToken(customUserDetails);
    }
}
