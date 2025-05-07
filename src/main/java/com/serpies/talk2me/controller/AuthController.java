package com.serpies.talk2me.controller;

import com.serpies.talk2me.db.dto.AuthTokenDto;
import com.serpies.talk2me.model.LoginRequestDto;
import com.serpies.talk2me.model.SignUpRequestDto;
import com.serpies.talk2me.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthTokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/sign-up")
    public AuthTokenDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return authService.signUp(signUpRequestDto);
    }

}
