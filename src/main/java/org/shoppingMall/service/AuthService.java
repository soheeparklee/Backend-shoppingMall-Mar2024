package org.shoppingMall.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.roles.Roles;
import org.shoppingMall.repository.roles.RolesJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userRoles.UserRoles;
import org.shoppingMall.repository.userRoles.UserRolesJpa;
import org.shoppingMall.web.DTO.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpa userJpa;
    private final UserRolesJpa userRolesJpa;
    private final RolesJpa rolesJpa;

    private final PasswordEncoder passwordEncoder;

    public boolean signUp(SignUpRequest signUpRequest) {
        if(userJpa.existsByEmail(signUpRequest.getEmail())){
            return false;
        }
        Roles roles= rolesJpa.findByName("ROLE_USER");

        User user= User.builder()
                .name(signUpRequest.getName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .email(signUpRequest.getEmail())
                .nickName(signUpRequest.getNickName())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .profileImg(signUpRequest.getProfileImg())
                .address(signUpRequest.getAddress())
                .gender(signUpRequest.getGender())
                .status("normal")
                .failiureCount(0)
                .createdAt(LocalDateTime.now())
                .build();
        userJpa.save(user);
        userRolesJpa.save(
                UserRoles.builder()
                        .user(user)
                        .roles(roles)
                        .build()
        );
        return true;
    }
}
