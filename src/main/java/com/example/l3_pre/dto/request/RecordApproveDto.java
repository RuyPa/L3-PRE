package com.example.l3_pre.dto.request;

import com.example.l3_pre.validation.annotations.ValidDateValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.example.l3_pre.consts.MessageErrors.APPOINTMENT_DATE_NOT_NULL;

@Getter
@Setter
@NoArgsConstructor
public class RecordApproveDto {

    @ValidDateValue
    @NotNull(message = APPOINTMENT_DATE_NOT_NULL)
    private LocalDate appointmentDate;
}
