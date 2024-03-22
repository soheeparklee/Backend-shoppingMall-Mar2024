package org.shoppingMall.web.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.service.AuthService;
import org.shoppingMall.web.DTO.SignUpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String register(@RequestBody SignUpRequest signUpRequest){
        boolean isSuccess= authService.signUp(signUpRequest);
        return isSuccess ? "회원가입 성공" : "회원가입 실패";
     }
}
