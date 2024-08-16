package com.example.l3_pre.mapper;

import com.example.l3_pre.dto.RecordResp;
import javax.persistence.*;
import java.time.LocalDate;

import static com.example.l3_pre.consts.Mapper.RECORD_MAPPER;
@Entity
@SqlResultSetMapping(
        name = RECORD_MAPPER,
        classes = @ConstructorResult(
                targetClass = RecordResp.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "submissionDate", type = LocalDate.class),
                        @ColumnResult(name = "submissionContent", type = String.class),
                        @ColumnResult(name = "createdBy", type = Integer.class),
                        @ColumnResult(name = "note", type = String.class),
                        @ColumnResult(name = "employeeId", type = Integer.class),
                        @ColumnResult(name = "leaderId", type = Integer.class),
                        @ColumnResult(name = "createdDate", type = LocalDate.class),
                        @ColumnResult(name = "rejectedDate", type = LocalDate.class),
                        @ColumnResult(name = "rejectedReason", type = String.class),
                        @ColumnResult(name = "approvedDate", type = LocalDate.class),
                        @ColumnResult(name = "appointmentDate", type = LocalDate.class),
                        @ColumnResult(name = "additionalRequest", type = String.class),
                        @ColumnResult(name = "endedDate", type = LocalDate.class),
                        @ColumnResult(name = "endedReason", type = String.class),
                        @ColumnResult(name = "recordTypeId", type = Integer.class),
                        @ColumnResult(name = "statusId", type = Integer.class),
                        @ColumnResult(name = "recordTypeName", type = String.class),
                        @ColumnResult(name = "statusName", type = String.class)
                }
        )
)
public class RecordMapper {
    @Id
    private Integer id;
}
