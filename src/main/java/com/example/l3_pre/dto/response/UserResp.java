package com.example.l3_pre.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResp {

    private Integer id;

    private String username;

    private String password;

    private String name;

    private Date dob;

    private String email;

    private String address;

    private String phoneNumber;

    private List<RoleResp> roleRespList;

    public UserResp(Integer id, String name, Integer roleId, String roleName) {
        this.id = id;
        this.name = name;
        this.roleRespList = new ArrayList<>();
        roleRespList.add(new RoleResp(roleId, roleName));
    }

    public UserResp(Integer id, String username, String password, String roleName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleRespList = new ArrayList<>();
        roleRespList.add(new RoleResp(roleName));
    }
}
