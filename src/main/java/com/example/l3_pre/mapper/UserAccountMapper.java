package com.example.l3_pre.mapper;

import com.example.l3_pre.dto.response.UserResp;

import javax.persistence.*;

import static com.example.l3_pre.consts.Mapper.USER_ACCOUNT_MAPPER;

@Entity
@SqlResultSetMapping(
        name = USER_ACCOUNT_MAPPER,
        classes = @ConstructorResult(
                targetClass = UserResp.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "password", type = String.class),
                        @ColumnResult(name = "roleName", type = String.class),
                }
        )
)
public class UserAccountMapper {
    @Id
    private Integer id;
}

