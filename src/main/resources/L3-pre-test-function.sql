-- use L3;
-- SET @registrationJson = '{"note": "new registration for leader",
-- 							"managerId": 1,
-- 						"emptblregistrationloyeeId": 2}';
-- CALL createNewRegistration(@registrationJson);

-- use L3;
-- SET @json_data = '{"id": 1, "submissionContent": "New submission content", "leaderId": 3}';
-- CALL submissToLeader(@json_data);

-- USE L3;
-- SET SQL_SAFE_UPDATES = 0;
-- SET @json_data = '{"note": "update this registration"}';
-- SET @id = 9;
-- CALL updateRegistration(@json_data, @id);
-- SET SQL_SAFE_UPDATES = 1;

-- use L3;
-- call getAllLeaders();

-- use L3;
-- call checkStatusOfRegistration(9);

-- USE L3;
-- SET @endProfileJson = '{"endReason": "new end profile",
--                         "endAt": "2024-07-17",
--                         "managerId": 1,
--                         "employeeId": 2}';
-- CALL createNewEndProfile(@endProfileJson);

-- use L3;
-- call getAllEndProfiles();

use L3;
call deleteEndProfile(2);

