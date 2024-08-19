package com.example.l3_pre.dto.request;

import com.example.l3_pre.dto.response.BaseDto;
import com.example.l3_pre.validation.annotations.ValidRecordTypeId;
import com.example.l3_pre.validation.annotations.ValidUserIdExists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.example.l3_pre.consts.MessageErrors.*;
import static com.example.l3_pre.consts.ValueConst.ID_MIN_VALUE;

@Getter
@Setter
@NoArgsConstructor
public class RecordCreateUpdateDto implements BaseDto {

    @NotNull(message = RECORD_TYPE_ID_NOT_NULL)
    @Min(value = ID_MIN_VALUE, message = ID_VALUE_NOT_VALID)
    @ValidRecordTypeId
    private Integer recordTypeId;

    @NotNull(message = EMPLOYEE_ID_NOT_NULL)
    @Min(value = ID_MIN_VALUE, message = ID_VALUE_NOT_VALID)
    @ValidUserIdExists(message = EMPLOYEE_ID_NOT_FOUND)
    private Integer employeeId;

    private String endedReason;

    private String note;

    private Integer createdBy;
}
