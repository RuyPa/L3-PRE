package com.example.l3_pre.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResp {

    private Integer id;
    private String name;

    public RoleResp(Integer roleId, String roleName) {
        this.id = roleId;
        this.name = roleName;
    }

    public RoleResp(String roleName) {
        this.name = roleName;
    }
}
