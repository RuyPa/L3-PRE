CREATE DATABASE IF NOT EXISTS L3;
USE L3;

CREATE TABLE tblUser (
                         id int AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(255),
                         password VARCHAR(255),
                         name VARCHAR(255),
                         dob DATE,
                         email VARCHAR(255),
                         address VARCHAR(255),
                         phoneNumber VARCHAR(255)
);

CREATE TABLE tblEmployee (
                             id int AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255),
                             code VARCHAR(255),
                             gender VARCHAR(255),
                             dob DATE,
                             address VARCHAR(255),
                             team VARCHAR(255),
                             imageUrl VARCHAR(255),
                             citizenId VARCHAR(255),
                             phoneNumber VARCHAR(255),
                             email VARCHAR(255),
                             createBy int,
                             createAt DATETIME DEFAULT CURRENT_TIMESTAMP,
                             statusId int,
                             foreign key (statusId) references tblstatus(id)
);

-- Create tblFamilyRelationship
CREATE TABLE tblFamilyRelationship (
                                       id int AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255),
                                       gender VARCHAR(255),
                                       dob DATE,
                                       address VARCHAR(255),
                                       citizenId VARCHAR(255),
                                       relationship VARCHAR(255),
                                       employeeId int,
                                       foreign key (employeeId) references tblEmployee(id)
);

use L3;
CREATE TABLE tblCertification (
                                  id int AUTO_INCREMENT PRIMARY KEY,
                                  issuedDate DATE,
                                  detail VARCHAR(255),
                                  field VARCHAR(255),
                                  certificateType VARCHAR(255),
                                  issuedBy VARCHAR(255),
                                  employeeId int,
                                  FOREIGN KEY (employeeId) REFERENCES tblEmployee(id)
);

CREATE TABLE tblRole (
                         id int AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255)
);

use L3;
create table tblStatus(
                          id int auto_increment primary key,
                          name varchar(255)
);

use L3;
create table tblRecordType(
                              id int auto_increment primary key,
                              name varchar (255)
);

use L3;
CREATE TABLE tblRecord (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           submissionDate DATE,
                           submissionContent VARCHAR(255),
                           createdBy INT,
                           note VARCHAR(255),
                           employeeId INT,
                           leaderId INT,
                           createdDate DATE,
                           rejectedDate DATE,
                           rejectedReason VARCHAR(255),
                           approvedDate DATE,
                           appointmentDate DATE,
                           additionalRequest VARCHAR(255),
                           endedDate DATE,
                           endedReason VARCHAR(255),
                           recordTypeId INT,
                           statusId INT,
                           FOREIGN KEY (recordTypeId) REFERENCES tblRecordType(id),
                           FOREIGN KEY (statusId) REFERENCES tblStatus(id),
                           FOREIGN KEY (employeeId) REFERENCES tblEmployee(id)
);

use L3;
CREATE TABLE tblUserRole (
                             id int AUTO_INCREMENT PRIMARY KEY,
                             userId int,
                             roleId int,
                             foreign key (userId) references tblUser(id),
                             foreign key (roleId) references tblRole(id)
);

insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy1", 123456, "Đỗ Bá Duy", "2002-07-10", "duy1@gmail.com", "Ha Noi", "01347851");
insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy2", 123456, "Đỗ Bá Giuy", "2002-07-10", "duy2@gmail.com", "Ha Noi", "01347871");
insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy3", 123456, "Đỗ Bá Ruy", "2002-07-10", "duy3@gmail.com", "Ha Noi", "01347811");

insert into tblrole (name) values ("ROLE_LEADER");
insert into tblrole (name) values ("ROLE_MANAGER");

insert into tbluserrole (userId, roleId) values (1, 1);
insert into tbluserrole (userId, roleId) values (2, 1);
insert into tbluserrole (userId, roleId) values (2, 2);
insert into tbluserrole (userId, roleId) values (3, 2);

insert into tblemployee (name, code, gender, dob, address, team, imageUrl, citizenId, phoneNumber, email, createBy) values ("Trần Đình Dưỡng", "B20DCCN148", "nam", "2002-10-12", "Cầu Giấy Hà Nội", "T01", "https://play-lh.googleusercontent.com/bzcajmQOcPuyWo0VcnTHbaBbu7lZ4bCxCkaNp4CbySVADZC1glt4gVgwKJ8nXsbolcMT", "033202000789", "0126454", "đuongezpari@gmail.com", 1);
insert into tblemployee (name, code, gender, dob, address, team, imageUrl, citizenId, phoneNumber, email, createBy) values ("Nguyễn Thế Quý", "B20DCCN147", "nam", "2002-12-13", "Hoàn Kiếm Hà Nội", "T01", "https://i.ytimg.com/vi/bf4yyStDWHE/maxresdefault.jpg", "033202000788", "012645463", "quyabc@gmail.com", 1);
insert into tblemployee (name, code, gender, dob, address, team, imageUrl, citizenId, phoneNumber, email, createBy) values ("Lâm Nhật Phát", "B20DCCN144", "nữ", "2002-09-12", "Hà Đông Hà Nội", "T02", "https://hips.hearstapps.com/hmg-prod/images/avtr-stills-03-654e58e595a8c.jpg?crop=0.8079012345679012xw:1xh;center,top", "033202000777", "012642554", "lamnhaphat@gmail.com", 2);

insert into tblfamilyrelationship (name, gender, dob, address, citizenId, relationship, employeeId) values ("Doãn Thị Hương", "nữ", "1972-02-02", "Nam Định", "033202000123", "dì", 1);
insert into tblfamilyrelationship (name, gender, dob, address, citizenId, relationship, employeeId) values ("Lê Văn Quyết", "nam", "1971-01-01", "Nghệ An", "033202000111", "chú", 1);
insert into tblfamilyrelationship (name, gender, dob, address, citizenId, relationship, employeeId) values ("Nông Văn Quang", "nam", "1973-03-03", "Hưng Yên", "033202000333", "cậu", 2);
insert into tblfamilyrelationship (name, gender, dob, address, citizenId, relationship, employeeId) values ("Triệu Lâm Nhất", "nam", "1950-05-05", "Lai Châu", "033202000444", "ông", 3);

use L3;
INSERT INTO tblCertification (issuedDate, detail, field, certificateType, issuedBy, employeeId) VALUES ("2022-01-01", "Chứng chỉ Tiếng Anh Toeic", "Ngoại ngữ", "Toeic", "ETS", 1);
INSERT INTO tblCertification (issuedDate, detail, field, certificateType, issuedBy, employeeId) VALUES ("2021-05-01", "Bằng tốt nghiệp", "Công nghệ phần mềm", "Bằng Đại học", "Đại học ABC", 2);
INSERT INTO tblCertification (issuedDate, detail, field, certificateType, issuedBy, employeeId) VALUES ("2019-06-01", "Bằng tốt nghiệp", "Truyền thông đa phương tiện", "Bằng Đại học", "Đại học DEF", 2);
INSERT INTO tblCertification (issuedDate, detail, field, certificateType, issuedBy, employeeId) VALUES ("2018-02-01", "Bằng tốt nghiệp", "Công nghệ phần mềm", "Bằng Đại học", "Đại học GHI", 3);

use L3;
insert into tblrecordtype(name) value("REGISTRATION");
insert into tblrecordtype(name) value("TERMINATION");

insert into tblstatus(name) value("NEW");
insert into tblstatus(name) value("PENDING");
insert into tblstatus(name) value("REJECTED");
insert into tblstatus(name) value("APPROVED");
insert into tblstatus(name) value("ADDITIONAL_REQUEST");
insert into tblstatus(name) value("TERMINATED");
insert into tblstatus(name) value("OFFICIAL");

