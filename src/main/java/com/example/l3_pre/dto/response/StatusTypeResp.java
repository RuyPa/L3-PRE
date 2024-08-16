package com.example.l3_pre.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusTypeResp {

    private Integer id;
    private String name;

    public StatusTypeResp(Integer statusId, String statusName) {
        this.id = statusId;
        this.name = statusName;
    }
}
