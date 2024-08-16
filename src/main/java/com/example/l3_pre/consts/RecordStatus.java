package com.example.l3_pre.consts;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordStatus {

    NEW(1, "NEW"),
    PENDING(2, "PENDING"),
    REJECTED(3, "REJECTED"),
    APPROVED(4, "APPROVED"),
    ADDITIONAL_REQUEST(5, "ADDITIONAL_REQUEST"),
    TERMINATED(6, "TERMINATED"),
    OFFICIAL(7, "OFFICIAL");

    private final Integer id;
    private final String statusName;
}
