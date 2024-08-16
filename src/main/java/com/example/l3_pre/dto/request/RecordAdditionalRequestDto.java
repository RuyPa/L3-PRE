package com.example.l3_pre.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import static com.example.l3_pre.consts.MessageErrors.ADDITIONAL_REQUEST_NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
public class RecordAdditionalRequestDto {

    @NotNull(message = ADDITIONAL_REQUEST_NOT_NULL)
    private String additionalRequest;
}
