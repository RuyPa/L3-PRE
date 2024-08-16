package com.example.l3_pre.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum RecordType {

    REGISTRATION(1, "REGISTRATION"),
    TERMINATION(2, "TERMINATION");

    private final Integer id;
    private final String statusName;
}
