package com.example.l3_pre.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.l3_pre.consts.ValueConst.ID_MIN_VALUE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageErrors {
    public static final String REGISTRATION_NOT_ALLOW_UPDATE = "Not allowed to update this record!";
    public static final String INVALID_DATE_VALUE = "Date must be in the future!";
    public static final String SUBMISSION_CONTENT_NOT_BLANK = "Submission content not blank, must fill!";
    public static final String END_REASON_NOT_BLANK = "End reason not blank, must fill!";
    public static final String ID_VALUE_NOT_VALID = "Id value must be greater than " + ID_MIN_VALUE + "!";
    public static final String LEADER_ID_NOT_NULL = "Leader id is not null!";
    public static final String EMPLOYEE_ID_NOT_NULL = "Employee id is not null!";
    public static final String REGISTRATION_ID_NOT_FOUND = "This registration id not found!";
    public static final String END_PROFILE_ID_NOT_FOUND = "This end profile id not found!";
    public static final String EMPLOYEE_ID_NOT_FOUND = "This employee id not found!";
    public static final String LEADER_ID_NOT_FOUND = "This leader id not found!";
    public static final String INVALID_OR_EXPIRED_TOKEN = "The token is invalid or expired!";
    public static final String USERNAME_OR_PASSWORD_INCORRECT = "Username or Password is incorrect!";
    public static final String NOT_RECORD_OWNER = "Only record creator can update!";
    public static final String NOT_RECIPIENT = "Only receive leader can manipulate record!";
    public static final String NOT_SUBMITTED = "This record has not been submitted!";
    public static final String NOT_ALLOW_TO_SUBMIT = "This record has been manipulated, can not submit!";
    public static final String INVALID_STATUS_INPUT = "Invalid status input, must be in " + "{REJECTED, ADDITIONAL_REQUEST, APPROVED}";
    public static final String NOT_NULL_STATUS_VALUE = "Status value is not null!";
    public static final String NOT_ALLOW_HANDLE_PROCESSED_RECORD = "This record has been processed, can not handle!";
    public static final String NOT_APPROVED_EMPLOYEE = "This employee is not approved by leader!";
    public static final String NOT_AUTHORIZED = "This function is not authorized";
    public static final String PAGE_SIZE_NOT_NULL = "Page size is not null!";
    public static final String PAGE_INDEX_NOT_NULL = "Page Index is not null!";
    public static final String RECORD_TYPE_ID_NOT_NULL = "Record type id is not null!";
    public static final String REJECTED_REASON_NOT_NULL = "Rejected reason is not null!";
    public static final String ENDED_REASON_NOT_NULL = "Ended reason is not null!";
    public static final String RECORD_EXISTENCE = "This record for employee is existed!";
    public static final String RECORD_TYPE_ID_NOT_VALID = "Record type id is invalid!";
    public static final String RECORD_ID_NOT_EXISTS = "Record id is not exist!";
    public static final String ADDITIONAL_REQUEST_NOT_NULL = "Additional request is not null!";
    public static final String APPOINTMENT_DATE_NOT_NULL = "Appointment date is not null!";
    public static final String NOT_OFFICIAL_EMPLOYEE = "This employee is not official!";
    public static final String USER_NOT_EXIST = "User is not exist!";
    public static final String ENDED_REASON_NOT_REQUIRED = "Ended reason is not required!";
}
