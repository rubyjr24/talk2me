package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.AuthTokenDto;
import com.serpies.talk2me.db.dto.UserDto;
import com.serpies.talk2me.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthTokenDto login(@RequestBody UserDto userDto) {
        return authService.login(userDto);
    }

    @PostMapping("/sign-up")
    public AuthTokenDto signUp(@RequestBody UserDto userDto) {
        return authService.signUp(userDto);
    }

}
