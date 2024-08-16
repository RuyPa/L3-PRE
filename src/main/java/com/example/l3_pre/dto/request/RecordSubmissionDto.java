package com.example.l3_pre.dto.request;

import com.example.l3_pre.validation.annotations.ValidUserIdExists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.example.l3_pre.consts.MessageErrors.*;
import static com.example.l3_pre.consts.MessageErrors.LEADER_ID_NOT_FOUND;
import static com.example.l3_pre.consts.ValueConst.ID_MIN_VALUE;

@Getter
@Setter
@NoArgsConstructor
public class RecordSubmissionDto {

    @Min(value = ID_MIN_VALUE, message = ID_VALUE_NOT_VALID)
    @NotNull(message = LEADER_ID_NOT_NULL)
    @ValidUserIdExists(message = LEADER_ID_NOT_FOUND)
    private Integer leaderId;

    @NotNull(message = SUBMISSION_CONTENT_NOT_BLANK)
    private String submissionContent;
}
