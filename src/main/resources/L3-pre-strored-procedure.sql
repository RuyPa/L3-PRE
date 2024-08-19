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
	insert into tblregistration (status, note, managerId, employeeId)
    values (
		"NEW",
        JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.note')),
        JSON_EXTRACT(registrationJson, '$.managerId'),
        JSON_EXTRACT(registrationJson, '$.employeeId')
    );
    SET new_registration_id = LAST_INSERT_ID();
    SELECT regis.* FROM tblregistration regis WHERE id = new_registration_id;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllRegistrations`()
BEGIN
	select regis.*
    from tblregistration regis
    where 1 = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getRegistrationById`(in registrationId int)
BEGIN
	select regis.*
    from tblregistration regis
    where regis.id = registrationId;
END$$
DELIMITER ;	

USE L3;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `submitToLeader`(in registrationJson JSON, in id int)
BEGIN
    DECLARE v_submissionContent VARCHAR(255);
    DECLARE v_leaderId INT;
    DECLARE v_submissionDate DATE; 

    -- Extract values from JSON object
    SET v_submissionContent = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.submissionContent'));
    SET v_leaderId = JSON_EXTRACT(registrationJson, '$.leaderId');
    SET v_submissionDate = NOW();

    -- Update tblregistration
    UPDATE tblregistration
    SET status = 'PENDING',
        submissionDate = v_submissionDate,
        submissionContent = v_submissionContent,
        leaderId = v_leaderId
    WHERE tblregistration.id = id;
    
    SELECT * FROM tblregistration WHERE tblregistration.id = id;
END$$
DELIMITER ;

USE L3;
DELIMITER $$
CREATE PROCEDURE `updateRegistration`(IN registrationJson JSON, IN id INT)
BEGIN
   DECLARE v_id INT DEFAULT NULL;
   DECLARE v_employeeId INT DEFAULT NULL;
   DECLARE v_note VARCHAR(255);

   SET v_id = id;
   
   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.employeeId') THEN
       SET v_employeeId = JSON_EXTRACT(registrationJson, '$.employeeId');
   END IF;
   
   IF JSON_CONTAINS_PATH(registrationJson, 'all', '$.note') THEN
       SET v_note = JSON_UNQUOTE(JSON_EXTRACT(registrationJson, '$.note'));
   END IF;
    
   -- Update tblregistration if id is provided
   IF v_id IS NOT NULL THEN
       UPDATE tblregistration
       SET 
           note = COALESCE(v_note, note),
           employeeId = COALESCE(v_employeeId, employeeId)
       WHERE id = v_id;
   END IF;
   
   SELECT * FROM tblregistration WHERE tblregistration.id = v_id;
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `createNewEndProfile`(IN endProfileJson JSON)
BEGIN
    DECLARE new_id INT;

    INSERT INTO tblendProfile(status, endAt, endReason, managerId, employeeId)
    VALUES (
        "NEW",
        JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endAt')),
        JSON_UNQUOTE(JSON_EXTRACT(endProfileJson, '$.endReason')),
        JSON_EXTRACT(endProfileJson, '$.managerId'),
        JSON_EXTRACT(endProfileJson, '$.employeeId')
    );

    SET new_id = LAST_INSERT_ID();
    SELECT * FROM tblendProfile WHERE id = new_id;
END$$

DELIMITER ;

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEndProfileById`(in endProfileId int)
BEGIN
	select endprofile.*
    from tblendprofile endprofile
    where endprofile.id = endProfileId;
END$$
DELIMITER ;	

use L3;
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEndProfiles`()
BEGIN
	select endprofile.*
    from tblendprofile endprofile;
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