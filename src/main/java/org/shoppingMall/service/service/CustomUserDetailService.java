package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.roles.Roles;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.repository.userRoles.UserRoles;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class CustomUserDetailService implements UserDetailsService {
    private final UserJpa userJpa;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userJpa.findByEmailFetchJoin(email)
                .orElseThrow(()-> new NullPointerException("email에 해당하는 user를 찾을 수 없습니다."));

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserRoles().stream().map(UserRoles::getRoles).map(Roles::getName).collect(Collectors.toList()))
                .build();
    }
}
