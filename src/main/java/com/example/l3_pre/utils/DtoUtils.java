package com.example.l3_pre.utils;

import com.example.l3_pre.dto.response.BaseDto;
import com.example.l3_pre.dto.response.UserDetailsInfoResp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoUtils {

    public static void setCreatedByInformation(BaseDto baseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfoResp userDetailsInfoResp = (UserDetailsInfoResp) authentication.getPrincipal();
        baseDto.setCreatedBy(userDetailsInfoResp.getId());
    }
}
