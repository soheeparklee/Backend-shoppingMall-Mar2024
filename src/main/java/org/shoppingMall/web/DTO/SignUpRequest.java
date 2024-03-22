package org.shoppingMall.web.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String phoneNumber;
    private String nickName;
    private String email;
    private String password;
    private String profileImg;
    private String address;
    private String gender;
}
