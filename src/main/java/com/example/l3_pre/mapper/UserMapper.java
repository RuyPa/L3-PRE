package com.example.l3_pre.mapper;

import com.example.l3_pre.dto.response.UserResp;

import javax.persistence.*;

import static com.example.l3_pre.consts.Mapper.USER_MAPPER;

@Entity
@SqlResultSetMapping(
        name = USER_MAPPER,
        classes = @ConstructorResult(
                targetClass = UserResp.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "roleId", type = Integer.class),
                        @ColumnResult(name = "roleName", type = String.class),
                }
        )
)
public class UserMapper {
    @Id
    private Integer id;
}
