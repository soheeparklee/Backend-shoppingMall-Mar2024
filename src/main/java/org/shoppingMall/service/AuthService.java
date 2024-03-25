package org.shoppingMall.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.config.security.JwtTokenProvider;
import org.shoppingMall.repository.roles.Roles;
import org.shoppingMall.repository.roles.RolesJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userRoles.UserRoles;
import org.shoppingMall.repository.userRoles.UserRolesJpa;
import org.shoppingMall.web.DTO.LoginRequest;
import org.shoppingMall.web.DTO.SignUpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpa userJpa;
    private final UserRolesJpa userRolesJpa;
    private final RolesJpa rolesJpa;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

//    @Transactional(transactionManager = "tm")
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
                .failureCount(0)
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

    public String login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),  loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user= userJpa.findByEmailFetchJoin(loginRequest.getEmail())
                    .orElseThrow(()-> new NullPointerException("해당 이메일로 계정을 찾을 수 없습니다."));

            if(user.getStatus().equals("withdrwal")) throw new AccessDeniedException("탈퇴한 회원입니다.");

            List<String> roles= user.getUserRoles().stream().map(UserRoles::getRoles).map(Roles::getName).collect(Collectors.toList());
            return jwtTokenProvider.createToken(loginRequest.getEmail(), roles);

        } catch(Exception e){
            e.printStackTrace();
            throw new NotAcceptableStatusException("login not possible");
        }
    }

    public String getNickName(LoginRequest loginRequest) {
        User user= userJpa.findByEmailFetchJoin(loginRequest.getEmail())
                .orElseThrow(()-> new NullPointerException("해당 이메일로 계정을 찾을 수 없습니다."));
        return user.getNickName();
    }


//    public boolean logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        Authentication auth= SecurityContextHolder.getContext().getAuthentication(); //JwtAuthenticationFilter에서 setAuthentication했음
//        if(auth != null){
//            String currentToken= jwtTokenProvider.resolveToken(httpServletRequest);
//            jwtTokenProvider.addToBlackList(currentToken);
//            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
//        }
//        return true;
//    }

    public void withdrawl(String userEmail) {
        User user= userJpa.findByEmail(userEmail)
                .orElseThrow(()-> new NotFoundException("해당 이메일로 계정을 찾을 수 없습니다."));
        user.setStatus("withdrawl");
        userJpa.save(user);
    }
}

