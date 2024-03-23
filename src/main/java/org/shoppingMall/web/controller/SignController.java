package org.shoppingMall.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.service.AuthService;
import org.shoppingMall.web.DTO.LoginRequest;
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
        return isSuccess ? "sign up successful" : "sign up fail";
     }


     @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        String token= authService.login(loginRequest);
        httpServletResponse.setHeader("Token", token);
        return "successfully logged in";
     }
}
