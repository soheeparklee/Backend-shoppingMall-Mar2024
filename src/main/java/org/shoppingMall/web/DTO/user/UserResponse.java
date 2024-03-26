package org.shoppingMall.web.DTO.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String email;
    private String nickName;
    private String profileImg;
    private String address;
    private String gender;
    private String status;
    private Integer failureCount;
}
