package com.example.l3_pre.controller;

import com.example.l3_pre.common.L3Response;
import com.example.l3_pre.exception.InvalidInputException;
import com.example.l3_pre.security.JwtService;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.l3_pre.consts.MessageErrors.USERNAME_OR_PASSWORD_INCORRECT;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public L3Response<String> login(@RequestParam("username") String username,
                                    @RequestParam("password") String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception ex) {
            throw new InvalidInputException(ErrorMessageConstant.UNAUTHORIZED,
                    new ApiMessageError(USERNAME_OR_PASSWORD_INCORRECT));
        }
        return new L3Response<>(jwtService.createToken(username));
    }
}
