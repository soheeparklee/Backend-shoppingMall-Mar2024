package org.shoppingMall.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shoppingMall.service.exceptions.BadRequestException;
import org.shoppingMall.service.service.EmailCertificationService;
import org.shoppingMall.service.service.RedisUtil;
import org.shoppingMall.web.DTO.email.EmailCheckRequest;
import org.shoppingMall.web.DTO.email.EmailRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailCertificationController {

    private final EmailCertificationService emailCertificationService;
    private final RedisUtil redisUtil;
    @PostMapping("/send-mail")
    public String sendMail(@RequestBody @Valid EmailRequest emailRequest){
        System.out.println("이메일 인증 이메일 : " + emailRequest.getEmail());
        return emailCertificationService.joinEmail(emailRequest.getEmail());
    }

    @PostMapping("/check-auth-num")
    public String checkAuthNum(@RequestBody @Valid EmailCheckRequest emailCheckRequest){
        Boolean checked= emailCertificationService.checkAuthNum(emailCheckRequest.getEmail(), emailCheckRequest.getAuthNum());
        if(checked){
            return "OK";
        }else{
            throw new BadRequestException("잘못된 인증 번호 입니다.", emailCheckRequest.getAuthNum());
        }
    }
}
