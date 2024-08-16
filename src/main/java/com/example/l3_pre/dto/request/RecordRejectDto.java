package com.example.l3_pre.dto.request;

import com.example.l3_pre.consts.MessageErrors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RecordRejectDto {

    @NotNull(message = MessageErrors.REJECTED_REASON_NOT_NULL)
    private String rejectedReason;
}
