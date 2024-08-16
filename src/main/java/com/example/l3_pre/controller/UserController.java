package com.example.l3_pre.controller;

import com.example.l3_pre.common.L3Response;
import com.example.l3_pre.dto.response.UserResp;
import com.example.l3_pre.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/leaders")
    public L3Response<List<UserResp>> getAllLeaders() {
        return L3Response.build(userService.getAllLeaders());
    }
}
