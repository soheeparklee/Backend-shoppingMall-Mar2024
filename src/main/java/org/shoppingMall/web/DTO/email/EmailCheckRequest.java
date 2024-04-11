package org.shoppingMall.web.DTO.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailCheckRequest {
    @Email
    @NotEmpty(message= "이메일 형식을 확인해주세요.")
    private String email;
    @NotEmpty(message = "인증 번호를 입력해 주세요.")
    private String authNum;
}
