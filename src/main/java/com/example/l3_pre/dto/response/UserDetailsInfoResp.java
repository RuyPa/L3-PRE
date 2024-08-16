package com.example.l3_pre.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsInfoResp extends User {

    private final Integer id;

    public UserDetailsInfoResp(UserResp userResp) {
        super(userResp.getUsername()
                , userResp.getPassword()
                , userResp.getRoleRespList()
                        .stream()
                        .map(roleDto -> new SimpleGrantedAuthority(roleDto.getName())).collect(Collectors.toList()));
        this.id = userResp.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsInfoResp)) return false;
        if (!super.equals(o)) return false;
        UserDetailsInfoResp that = (UserDetailsInfoResp) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
