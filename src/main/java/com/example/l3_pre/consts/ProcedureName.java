package com.example.l3_pre.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcedureName {

    public static class User {
        private User() {

        }

        public static final String GET_ALL_LEADERS = "getAllLeaders";
        public static final String CHECK_USER_ID_EXISTENCE = "checkUserIdExistence";
        public static final String USER_ID = "userId";
        public static final String CHECK_ACCOUNT_EXISTENCE = "checkUserAccountExistence";
        public static final String USERNAME = "userName";
        public static final String USER_NOT_FOUND = "User account is not found!";
        public static final String LEADER_ID = "leaderId";
        public static final String EMPLOYEE_ID = "employeeId";
    }


    public static class Record{

        private Record(){

        }

        public static final String GET_BY_RECORD_ID = "getByRecordId";
        public static final String RECORD_ID = "recordId";
        public static final String CREATE_RECORD = "createRecord";
        public static final String RECORD_CREATE_UPDATE_JSON = "recordCreateUpdateJson";
        public static final String GET_ALL_BY_LEADER = "getAllByLeader";
        public static final String GET_ALL_BY_MANAGER = "getAllByManager";
        public static final String RECORD_SEARCH_JSON = "recordSearchJson";
        public static final String RECORD_SUBMISSION_JSON = "recordSubmissionJson";
        public static final String SUBMIT_TO_LEADER = "submitToLeader";
        public static final String REJECT_RECORD = "rejectRecord";
        public static final String RECORD_REJECT_JSON = "recordRejectJson";
        public static final String APPROVE_RECORD = "approveRecord";
        public static final String RECORD_APPROVE_JSON = "recordApproveJson";
        public static final String ADDITIONAL_REQUEST_RECORD = "additionalRequestRecord";
        public static final String RECORD_ADDITIONAL_REQUEST_JSON = "recordAdditionalRequestJson";
        public static final String UPDATE_RECORD = "updateRecord";
        public static final String CHECK_RECORD_CREATE_BY_EMPLOYEE_ID = "checkRecordCreateByEmployeeId";
        public static final String CHECK_RECORD_ID_EXISTENCE = "checkRecordIdExistence";
        public static final String GET_ONE_BY_LEADER = "getOneByLeader";
        public static final String GET_ONE_BY_MANAGER = "getOneByManager";
        public static final String CHECK_GET_ONE_BY_LEADER = "checkGetOneByLeader";
        public static final String CHECK_RECORD_STATUS = "checkRecordStatus";
        public static final String MANAGER_ID = "managerId";
        public static final String CHECK_UPDATE = "checkUpdate";
        public static final String CHECK_OFFICIAL_EMPLOYEE = "checkOfficialEmployee";
        public static final String CHECK_GET_ONE_BY_MANAGER = "checkGetOneByManager";
    }
}
