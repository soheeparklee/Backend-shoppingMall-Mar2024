package org.shoppingMall.web.controller;

import lombok.RequiredArgsConstructor;
import org.shoppingMall.repository.roles.RolesJpa;
import org.shoppingMall.repository.user.User;
import org.shoppingMall.repository.user.UserJpa;
import org.shoppingMall.repository.userDetails.CustomUserDetails;
import org.shoppingMall.repository.userRoles.UserRolesJpa;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserJpa userJpa;
    private final RolesJpa rolesJpa;
    private final UserRolesJpa userRolesJpa;

    @GetMapping("/test")
    public String test() {
        User user = userJpa.findById(1).orElseThrow(() -> new NotFoundException("ss"));

        Integer userId = user.getUserId();

        return "test success: " + userId;
    }

    @GetMapping("/test2")
    public String test2(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return "test success, userId: " + customUserDetails.getUserId();
    }
}
