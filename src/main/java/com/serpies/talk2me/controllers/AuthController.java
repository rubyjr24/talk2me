package com.serpies.talk2me.controllers;

import com.serpies.talk2me.db.dtos.AuthTokenDto;
import com.serpies.talk2me.db.entities.AuthToken;
import com.serpies.talk2me.models.LoginRequestDto;
import com.serpies.talk2me.models.SignUpRequestDto;
import com.serpies.talk2me.services.AuthService;
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
