package org.shoppingMall.service.service;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.service.exceptions.NotFoundException;
import org.shoppingMall.web.DTO.ResponseDTO;
import org.shoppingMall.web.DTO.user.UserRequest;
import org.shoppingMall.web.DTO.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpa userJpa;
    public ResponseDTO findUserByToken(CustomUserDetails customUserDetails) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: "+ customUserDetails.getEmail()));

        UserResponse userResponse= UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .profileImg(user.getProfileImg())
                .address(user.getAddress())
                .gender(user.getGender())
                .status(user.getStatus())
                .failureCount(user.getFailureCount())
                .build();
                return new ResponseDTO(HttpStatus.OK.value(), "Find user "+ user.getNickName() + " success", userResponse);
    }

    public ResponseDTO updateUser(CustomUserDetails customUserDetails, UserRequest userRequest) {
        User user= userJpa.findByEmail(customUserDetails.getEmail())
                .orElseThrow(()-> new NotFoundException("Cannot find user with email: "+ customUserDetails.getEmail()));

        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setNickName(userRequest.getNickName());
        user.setPassword(userRequest.getPassword());
        userJpa.save(user);
        return new ResponseDTO(HttpStatus.OK.value(), "User info update success");

    }
}
