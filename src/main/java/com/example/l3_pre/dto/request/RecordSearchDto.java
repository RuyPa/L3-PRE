package com.example.l3_pre.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordSearchDto {

    private Integer pageSize;

    private Integer pageIndex;

    private Integer recordTypeId;
}
