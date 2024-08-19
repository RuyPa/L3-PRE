-- Create the database
CREATE DATABASE IF NOT EXISTS L3;
USE L3;

-- Create tblUser
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

-- Create tblEmployee
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
    createAt DATETIME DEFAULT CURRENT_TIMESTAMP
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

-- Create tblCertification
CREATE TABLE tblCertification (
    id int AUTO_INCREMENT PRIMARY KEY,
    issuedDate DATE,
    detail VARCHAR(255),
    field VARCHAR(255),
    employeeId int,
	foreign key (employeeId) references tblEmployee(id)
);

-- Create tblRole
CREATE TABLE tblRole (
    id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Create tblUserRole
CREATE TABLE tblUserRole (
    id int AUTO_INCREMENT PRIMARY KEY,
    userId int,
    roleId int,
	foreign key (userId) references tblUser(id),
	foreign key (roleId) references tblRole(id)
);

use L3;
-- Create tblRegistration
CREATE TABLE tblRegistration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    submissionDate DATETIME,
    submissionContent VARCHAR(255),
    status VARCHAR(255),
	createAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    note VARCHAR(255),
    leaderId int,
    managerId int,
    employeeId int,
	foreign key (managerId) references tblUser(id),
	foreign key (employeeId) references tblEmployee(id)
);

use L3;
-- Create tblEndProfile
CREATE TABLE tblEndProfile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    endAt DATE,
    endReason VARCHAR(255),
    status VARCHAR(255),
	createAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    employeeId int,
    managerId int,
	foreign key (managerId) references tblUser(id),
	foreign key (employeeId) references tblEmployee(id)
);

insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy1", 123456, "Đỗ Bá Duy", "2002-07-10", "duy1@gmail.com", "Ha Noi", "01347851");
insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy2", 123456, "Đỗ Bá Giuy", "2002-07-10", "duy2@gmail.com", "Ha Noi", "01347871");
insert into tbluser (username, password, name, dob, email, address,phoneNumber) values ("duy3", 123456, "Đỗ Bá Ruy", "2002-07-10", "duy3@gmail.com", "Ha Noi", "01347811");

insert into tblrole (name) values ("Leader");
insert into tblrole (name) values ("Manager");

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

insert into tblcertification (issuedDate ,detail, field, employeeId) values ("2022-01-01", "Chứng chỉ Tiếng Anh Toeic", "Tiếng Trung", 1);
insert into tblcertification (issuedDate ,detail, field, employeeId) values ("2021-05-01", "Bằng tốt nghiệp", "Công nghệ phần mềm", 2);
insert into tblcertification (issuedDate ,detail, field, employeeId) values ("2019-06-01", "Bằng tốt nghiệp", "Truyền thông đa phương tiện", 2);
insert into tblcertification (issuedDate ,detail, field, employeeId) values ("2018-02-01", "Bằng tốt nghiệp", "Công nghệ phần mềm", 3);