package com.example.l3_pre.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordTypeResp {

    private Integer id;
    private String name;

    public RecordTypeResp(Integer recordTypeId, String recordTypeName) {
        this.id = recordTypeId;
        this.name = recordTypeName;
    }
}
