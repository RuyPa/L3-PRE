USE L3;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllLeaders`()
BEGIN
	select user.*, role.id as roleId, role.name as roleName
    from tblUser user
    left join tbluserrole userrole on userrole.userId = user.id
    left join tblrole role on role.id = userrole.roleId
    where 1 = 1 and role.id = 1;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createNewRegistration`(in registrationJson JSON)
BEGIN
    declare new_registration_id int;
	insert into tblregistration (status, createdBy, employeeId)
    values (
		"NEW",
        JSON_EXTRACT(registrationJson, '$.createdBy'),
        JSON_EXTRACT(registrationJson, '$.employeeId')
    );
    SET new_registration_id = LAST_INSERT_ID();
    SELECT regis.* FROM tblregistration regis WHERE id = new_registration_id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPaginatedRegistrationsByManager`(in pageable JSON)
BEGIN
	declare pageSize int;
    declare pageIndex int;
    declare offset int;

    set pageSize = JSON_EXTRACT(pageable, '$.pageSize');
    set pageIndex = JSON_EXTRACT(pageable, '$.pageIndex');
    set offset = (pageIndex - 1) * pageSize;

	select regis.*
    from tblregistration regis
    order by regis.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPaginatedRegistrationsByLeader`(in pageable JSON, in leaderId int)
BEGIN
	declare pageSize int;
    declare pageIndex int;
    declare offset int;

    set pageSize = JSON_EXTRACT(pageable, '$.pageSize');
    set pageIndex = JSON_EXTRACT(pageable, '$.pageIndex');
    set offset = (pageIndex - 1) * pageSize;

	select regis.*
    from tblregistration regis
    where regis.leaderId = leaderId and status = "PENDING"
    order by regis.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneRegistrationByManager`(in registrationId int)
BEGIN
	select regis.*
    from tblregistration regis
    where regis.id = registrationId;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneRegistrationByLeader`(in registrationId int, in leaderId int)
BEGIN
	select regis.*
    from tblregistration regis
    where regis.id = registrationId and leaderId = regis.leaderId;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneEndProfileByLeader`(in endprofileId int, in leaderId int)
BEGIN
	select endprofile.*
    from tblendprofile endprofile
    where endprofile.id = endprofileId and leaderId = endprofile.leaderId;
END$$
DELIMITER ;



USE L3;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `submitEndProfileToLeader`(in endProfileJson JSON, in id int)
BEGIN
    DECLARE v_submissionContent VARCHAR(255);
    DECLARE v_leaderId INT;
    DECLARE v_submissionDate DATE;

    -- Extract values from JSON object
    SET v_submissionContent = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.submissionContent'));
    SET v_leaderId = JSON_EXTRACT(endProfileJson, '$.leaderId');
    SET v_submissionDate = NOW();

    -- Update tblregistration
    UPDATE tblendprofile
    SET status = 'PENDING',
        submissionDate = v_submissionDate,
        submissionContent = v_submissionContent,
        leaderId = v_leaderId
    WHERE tblendprofile.id = id;

    SELECT * FROM tblendprofile WHERE tblendprofile.id = id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateRegistrationByManager`(IN registrationJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_employeeId INT DEFAULT NULL;

   SET v_id = id;

   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.employeeId') THEN
       SET v_employeeId = JSON_EXTRACT(registrationJson, '$.employeeId');
   END IF;

   IF v_id IS NOT NULL THEN
       UPDATE tblregistration
       SET
           employeeId = COALESCE(v_employeeId, employeeId)
       WHERE id = v_id;
   END IF;

   SELECT * FROM tblregistration WHERE tblregistration.id = v_id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateEndProfileByManager`(IN endProfileJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_employeeId INT DEFAULT NULL;
   Declare v_endedReason varchar(255) default null;

   SET v_id = id;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.employeeId') THEN
       SET v_employeeId = JSON_EXTRACT(endProfileJson, '$.employeeId');
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.endedReason') THEN
       SET v_endedReason =  JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endedReason'));
   END IF;

   IF v_id IS NOT NULL THEN
       UPDATE tblendprofile
       SET
           employeeId = COALESCE(v_employeeId, employeeId),
           endedReason = coalesce(v_endedReason, endedReason)
       WHERE tblendprofile.id = v_id;
   END IF;

   SELECT * FROM tblendprofile WHERE tblendprofile.id = v_id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateRegistrationByLeader`(IN registrationJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_rejectedDate datetime;
   DECLARE v_rejectedReason varchar(255);
   DECLARE v_approvedDate datetime;
   DECLARE v_appointmentDate datetime;
   DECLARE v_status varchar(255);
   DECLARE v_additionalRequest varchar(255);

   SET v_id = id;

   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.rejectedReason') THEN
       SET v_rejectedReason = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.rejectedReason'));
       SET v_rejectedDate = now();
   END IF;

   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.appointmentDate') THEN
       SET v_appointmentDate = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.appointmentDate'));
       SET v_approvedDate = now();
   END IF;

   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.status') THEN
       SET v_status = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.status'));
   END IF;

   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.additionalRequest') THEN
       SET v_additionalRequest = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.additionalRequest'));
   END IF;

   IF v_id IS NOT NULL THEN
       UPDATE tblregistration
       SET
		   rejectedDate = COALESCE(v_rejectedDate, rejectedDate),
           rejectedReason = COALESCE(v_rejectedReason, rejectedReason),
           approvedDate = COALESCE(v_approvedDate, approvedDate),
           appointmentDate = COALESCE(v_appointmentDate, appointmentDate),
           status = COALESCE(v_status, status),
           additionalRequest = COALESCE(v_additionalRequest, additionalRequest)
       WHERE tblregistration.id = v_id;
   END IF;

   SELECT * FROM tblregistration WHERE tblregistration.id = v_id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateEndProfileByLeader`(IN endProfileJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_rejectedDate datetime;
   DECLARE v_rejectedReason varchar(255);
   DECLARE v_approvedDate datetime;
   DECLARE v_status varchar(255);
   DECLARE v_additionalRequest varchar(255);
   DECLARE v_feedback varchar(255);

   SET v_id = id;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.rejectedReason') THEN
       SET v_rejectedReason = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.rejectedReason'));
	   SET v_rejectedDate = now();
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.feedback') THEN
       SET v_feedback = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.feedback'));
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.status') THEN
    SET v_status = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.status'));

    IF v_status = "APPROVED" THEN
        SET v_approvedDate = NOW();
    END IF;
END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.additionalRequest') THEN
       SET v_additionalRequest = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.additionalRequest'));
   END IF;

   IF v_id IS NOT NULL THEN
       UPDATE tblendprofile
       SET
		   rejectedDate = COALESCE(v_rejectedDate, rejectedDate),
           rejectedReason = COALESCE(v_rejectedReason, rejectedReason),
           approvedDate = COALESCE(v_approvedDate, approvedDate),
           feedback = COALESCE(v_feedback, feedback),
           status = COALESCE(v_status, status),
           additionalRequest = COALESCE(v_additionalRequest, additionalRequest)
       WHERE tblendprofile.id = v_id;
   END IF;

   SELECT * FROM tblendprofile WHERE tblendprofile.id = v_id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRegistration`(in id int)
BEGIN
	SET @@SESSION.sql_safe_updates = 0;

    DELETE FROM tblregistration
    where tblregistration.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPendingRegistrations`(in status varchar(255))
BEGIN
	select regis.*
    from tblregistration regis
    where 1 = 1 and regis.status = status;
END$$
DELIMITER ;

use L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkStatusOfRegistration`(IN id INT)
BEGIN
	select regis.status
    from tblregistration regis
    where regis.id = id;
END$$

DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStatusOfEndProfile`(IN id INT)
BEGIN
	select endprofile.status
    from tblendprofile endprofile
    where endprofile.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createNewEndProfile`(IN endProfileJson JSON)
BEGIN
    DECLARE new_id INT;

    INSERT INTO tblendProfile(status, endedDate, endedReason, employeeId, createdBy)
    VALUES (
        "NEW",
        now(),
        JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endedReason')),
        JSON_EXTRACT(endProfileJson, '$.employeeId'),
        JSON_EXTRACT(endProfileJson, '$.createdBy')
    );

    SET new_id = LAST_INSERT_ID();
    SELECT * FROM tblendProfile WHERE id = new_id;
END$$

DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneProfileByManager`(in endProfileId int)
BEGIN
	select endprofile.*
    from tblendprofile endprofile
    where endprofile.id = endProfileId;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPaginatedEndProfilesByManager`(in pageable json)
BEGIN
	declare pageSize int;
    declare pageIndex int;
    declare offset int;

    set pageSize = JSON_EXTRACT(pageable, '$.pageSize');
    set pageIndex = JSON_EXTRACT(pageable, '$.pageIndex');
    set offset = (pageIndex - 1) * pageSize;

	select endprofile.*
    from tblendprofile endprofile
    order by endprofile.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPaginatedEndProfilesByLeader`(in pageable JSON, in leaderId int)
BEGIN
	declare pageSize int;
    declare pageIndex int;
    declare offset int;

    set pageSize = JSON_EXTRACT(pageable, '$.pageSize');
    set pageIndex = JSON_EXTRACT(pageable, '$.pageIndex');
    set offset = (pageIndex - 1) * pageSize;

	select endprofile.*
    from tblendprofile endprofile
    where endprofile.leaderId = leaderId and status = "PENDING"
    order by endprofile.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteEndProfile`(in id int)
BEGIN
	SET @@SESSION.sql_safe_updates = 0;

    DELETE FROM tblendprofile endprofile
    where endprofile.id = id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateEndProfile`(IN endProfileJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_employeeId INT DEFAULT NULL;
   DECLARE v_endAt date;
   DECLARE v_managerId INT DEFAULT NULL;
   DECLARE v_endReason varchar(255) DEFAULT NULL;


   SET v_id = id;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.employeeId') THEN
       SET v_employeeId = JSON_EXTRACT(endProfileJson, '$.employeeId');
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.managerId') THEN
       SET v_managerId = JSON_EXTRACT(endProfileJson, '$.managerId');
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.endAt') THEN
       SET v_endAt = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endAt'));
   END IF;

   IF JSON_CONTAINS_PATH(endProfileJson, 'all', '$.endReason') THEN
       SET v_endReason = JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endReason'));
   END IF;

   -- Update tblregistration if id is provided
   IF v_id IS NOT NULL THEN
       UPDATE tblendprofile
       SET
           managerId = COALESCE(v_managerId, managerId),
           employeeId = COALESCE(v_employeeId, employeeId),
           endAt = coalesce(v_endAt, endAt),
           endReason = coalesce(v_endReason, endReason)
       WHERE id = v_id;
   END IF;

   SELECT * FROM tblendprofile WHERE tblendprofile.id = v_id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkIdRegistrationExistence`(in id int)
BEGIN
    SELECT COUNT(regis.id) > 0
    FROM tblregistration regis
    WHERE regis.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkUserIdExistence`(in id int)
BEGIN
    SELECT COUNT(user.id) > 0
    FROM tbluser user
    WHERE user.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkLeaderExistence`(in id int)
BEGIN
    SELECT COUNT(user.id) > 0
    FROM tbluser user
    join tbluserrole userrole on userrole.userId= user.id
    join tblrole role on role.id = userrole.roleId
    WHERE user.id = id and role.name = "ROLE_LEADER";
END$$
DELIMITER ;

USE L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkUserAccountExistence`(IN username varchar(255))
BEGIN
    SELECT user.id, user.username, user.password, role.name as roleName
    FROM tbluser AS user
    LEFT JOIN tbluserrole AS userrole ON userrole.userId = user.id
    LEFT JOIN tblrole AS role ON role.id = userrole.roleId
    WHERE user.username = username ;
END$$

DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkIdEndProfileExistence`(in id int)
BEGIN
    SELECT COUNT(endprofile.id) > 0
    FROM tblendprofile endprofile
    WHERE endprofile.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkApprovedEmployee`(in id int)
BEGIN
    SELECT COUNT(regis.id) > 0
    FROM tblregistration regis
    WHERE regis.employeeId = id and regis.status = "APPROVED";
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getLeaderIdFromSubmittedRegistration`(in id int)
BEGIN
    SELECT regis.leaderId
    from tblregistration regis
    where regis.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getLeaderIdFromSubmittedEndProfile`(in id int)
BEGIN
    SELECT endprofile.leaderId
    from tblendprofile endprofile
    where endprofile.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getStatusOfRegistration`(in id int)
BEGIN
    SELECT regis.status
    from tblregistration regis
    where regis.employeeId = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCreatorOfRegistration`(in id int)
BEGIN
    SELECT regis.createdBy
    from tblregistration regis
    where regis.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCreatorOfEndProfile`(in id int)
BEGIN
    SELECT endprofile.createdBy
    from tblendprofile endprofile
    where endprofile.id = id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createRecord`(
    IN recordCreateUpdateJson JSON
)
BEGIN
    DECLARE v_note VARCHAR(255);
    DECLARE v_createdBy INT;
    DECLARE v_recordTypeId INT;
    DECLARE v_employeeId INT;
    Declare v_endedReason varchar(255);

    IF JSON_CONTAINS_PATH(recordCreateUpdateJson, 'all', '$.note') THEN
        SET v_note = JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.note'));
    END IF;

	IF JSON_CONTAINS_PATH(recordCreateUpdateJson, 'all', '$.note') THEN
        SET v_endedReason = JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.endedReason'));
    END IF;

    INSERT INTO tblrecord ( createdDate, createdBy, recordTypeId, employeeId, note, statusId, endedReason)
    VALUES (
        NOW(),
        JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.createdBy')),
        JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.recordTypeId')),
        JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.employeeId')),
        COALESCE(v_note, null),
        1,
		NULLIF(v_endedReason, '')
    );

    SET @recordId = LAST_INSERT_ID();
    CALL getByRecordId(@recordId);
END$$

DELIMITER ;


use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getByRecordId`(in id int)
BEGIN
    SELECT record.* , retype.name as recordTypeName, status.name as statusName
    FROM tblrecord record
    left join tblrecordtype retype on retype.id = record.recordTypeId
    left join tblstatus status on status.id = record.statusId
    WHERE record.id = id;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllByLeader`(in recordSearchJson varchar(255), in leaderId int)
BEGIN

	declare pageSize int;
    declare pageIndex int;
    declare recordTypeId int;
    declare offset int;

    set pageSize = JSON_EXTRACT(recordSearchJson, '$.pageSize');
    set pageIndex = JSON_EXTRACT(recordSearchJson, '$.pageIndex');
    set recordTypeId = JSON_EXTRACT(recordSearchJson, '$.recordTypeId');
    set offset = (pageIndex - 1) * pageSize;

    SELECT record.* , retype.name as recordTypeName, status.name as statusName
    FROM tblrecord record
    left join tblrecordtype retype on retype.id = record.recordTypeId
    left join tblstatus status on status.id = record.statusId
    WHERE status.id = 2 and retype.id = recordTypeId and record.leaderId = leaderId
	order by record.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllByManager`(in recordSearchJson varchar(255), in managerId int)
BEGIN

	declare pageSize int;
    declare pageIndex int;
    declare recordTypeId int;
    declare offset int;

    set pageSize = JSON_EXTRACT(recordSearchJson, '$.pageSize');
    set pageIndex = JSON_EXTRACT(recordSearchJson, '$.pageIndex');
    set recordTypeId = JSON_EXTRACT(recordSearchJson, '$.recordTypeId');
    set offset = (pageIndex - 1) * pageSize;

    SELECT record.* , retype.name as recordTypeName, status.name as statusName
    FROM tblrecord record
    left join tblrecordtype retype on retype.id = record.recordTypeId
    left join tblstatus status on status.id = record.statusId
    WHERE retype.id = recordTypeId and record.createdBy = managerId
	order by record.id asc
    limit offset, pagesize;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `submitToLeader`(in recordJson JSON, in id int)
BEGIN
    UPDATE tblrecord
    SET statusId = 2,
        submissionDate = NOW(),
        submissionContent = JSON_UNQUOTE(JSON_EXTRACT(recordJson, '$.submissionContent')),
        leaderId = JSON_EXTRACT(recordJson, '$.leaderId')
    WHERE tblrecord.id = id;

    call getByRecordId(id);
END$$
DELIMITER ;

USE L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRecord`(
    IN recordCreateUpdateJson JSON,
    IN id INT
)
BEGIN
    DECLARE currentStatusId INT;

    SELECT statusId INTO currentStatusId
    FROM tblrecord
    WHERE tblrecord.id = id;

    IF currentStatusId = 5 THEN
        UPDATE tblrecord
        SET statusId = 2,
            note = JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.note'))
        WHERE tblrecord.id = id;
    ELSE
        UPDATE tblrecord
        SET note = JSON_UNQUOTE(JSON_EXTRACT(recordCreateUpdateJson, '$.note'))
        WHERE tblrecord.id = id;
    END IF;
    CALL getByRecordId(id);
END$$

DELIMITER ;

USE L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `rejectRecord`(
    IN recordRejectJson JSON,
    IN id INT
)
BEGIN
	UPDATE tblrecord
       SET
           rejectedReason = JSON_UNQUOTE(JSON_EXTRACT(recordRejectJson, '$.rejectedReason')),
           rejectedDate = now(),
           statusId = 3
       WHERE tblrecord.id = id;
    CALL getByRecordId(id);
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `approveRecord`(
    IN recordApproveJson JSON,
    IN id INT
)
BEGIN
    DECLARE recordTypeId INT;
    DECLARE employeeId INT;

    SELECT record.employeeId, record.recordTypeId
    INTO employeeId, recordTypeId
    FROM tblrecord record
    WHERE record.id = id;

	IF recordTypeId = 1 THEN
        UPDATE tblemployee
        SET statusId = 7
        WHERE tblemployee.id = employeeId;
    ELSEIF recordTypeId = 2 THEN
        UPDATE tblemployee
        SET statusId = 6
        WHERE tblemployee.id = employeeId;
    END IF;

    UPDATE tblrecord
    SET
        appointmentDate = JSON_UNQUOTE(JSON_EXTRACT(recordApproveJson, '$.appointmentDate')),
        approvedDate = NOW(),
        statusId = 4
    WHERE tblrecord.id = id;

    CALL getByRecordId(id);
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `additionalRequestRecord`(
    IN recordAdditionalRequestJson JSON,
    IN id INT
)
BEGIN
	UPDATE tblrecord
       SET
           additionalRequest = JSON_UNQUOTE(JSON_EXTRACT(recordAdditionalRequestJson, '$.additionalRequest')),
           statusId = 5
       WHERE tblrecord.id = id;
    CALL getByRecordId(id);
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkRecordCreateByEmployeeId`(in createUpdateJson json)
BEGIN
    SELECT COUNT(record.id) > 0
    FROM tblrecord record
    WHERE record.recordTypeId = JSON_EXTRACT(createUpdateJson, '$.recordTypeId') and record.statusId <> 3
    and record.employeeId = JSON_EXTRACT(createUpdateJson, '$.employeeId');
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkRecordIdExistence`(in recordId int)
BEGIN
    SELECT COUNT(record.id) > 0
    FROM tblrecord record
    WHERE record.id = recordId;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getOneByLeader`(in recordId int, in leaderId int)
BEGIN
	SELECT record.* , retype.name as recordTypeName, status.name as statusName
    FROM tblrecord record
    left join tblrecordtype retype on retype.id = record.recordTypeId
    left join tblstatus status on status.id = record.statusId
    where record.id = recordId and record.statusId = 2;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkGetOneByLeader`(in recordId int, in leaderId int)
BEGIN
SELECT CASE
           WHEN EXISTS (
               SELECT 1
               FROM tblrecord record
               WHERE record.id = recordId
                 AND (record.statusId = 2 AND record.leaderId = leaderId)
           )
           THEN 1
           ELSE 0
       END AS result;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkGetOneByManager`(in recordId int, in managerId int)
BEGIN
SELECT CASE
           WHEN EXISTS (
               SELECT 1
               FROM tblrecord record
               WHERE record.id = recordId
                 AND record.createdBy = managerId
           )
           THEN 1
           ELSE 0
       END AS result;
END$$
DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkRecordStatus`(in recordId int)
BEGIN
	SELECT record.statusId
    from tblrecord record
    where record.id = recordId;
END$$
DELIMITER ;

USE L3;
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkUpdate`(
    IN recordId INT,
    IN managerId INT
)
BEGIN
    SELECT CASE
               WHEN EXISTS (
                   SELECT 1
                   FROM tblrecord
                   WHERE id = recordId
                     AND (statusId = 1 OR statusId = 5)
                     AND createdBy = managerId
               )
               THEN 1
               ELSE 0
           END AS result;
END$$

DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkOfficialEmployee`(in employeeId int)
BEGIN
    SELECT COUNT(e.id) > 0
    FROM tblemployee e
    WHERE e.id = employeeId and e.statusId = 7;
END$$
DELIMITER ;