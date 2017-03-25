/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  fchishti-sw
 * Created: Mar 21, 2017
 */

create table USERTABLE(
    ID INT NOT NULL AUTO_INCREMENT,
    FIRSTNAME varchar(255),
    LASTNAME varchar(255),
    PASSWORD char(64),
    EMAIL varchar(255),
    PRIMARY KEY (id)
);

create table STUDENTTABLE(
    ID INT NOT NULL AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    STUDENT_ID INT NOT NULL,
    MAJOR_CODE INT,
    PRIMARY KEY(id),
    FOREIGN KEY(USER_ID)
        REFERENCES USERTABLE(ID)
        ON DELETE CASCADE
);

create table GROUPTABLE(
    ID INT NOT NULL AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    GROUPNAME varchar(255),
    PRIMARY KEY(id),
    FOREIGN KEY(USER_ID)
        REFERENCES USERTABLE(ID)
        ON DELETE CASCADE
);

create table COURSETABLE(
    ID INT NOT NULL AUTO_INCREMENT,
    TITLE varchar(255),
    COURSEPREFIX varchar(255),
    CODE INT,
    PRIMARY KEY (id)
);

create table STUDENTCOURSESTABLE(
    ID INT NOT NULL AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    COURSE_ID varchar(255),
    PRIMARY KEY(id),
    FOREIGN KEY(USER_ID)
        REFERENCES USERTABLE(ID)
        ON DELETE CASCADE
);

insert into USERTABLE (FIRSTNAME, LASTNAME, PASSWORD, EMAIL)
        values('Faaez','Chishti','c4289629b08bc4d61411aaa6d6d4a0c3c5f8c1e848e282976e29b6bed5aeedc7','fchishti@uco.edu');

insert into STUDENTTABLE (USER_ID, STUDENT_ID, MAJOR_CODE) 
        values ((SELECT ID FROM USERTABLE where FIRSTNAME = 'Faaez'), 12345678, 6100);

insert into GROUPTABLE (USER_ID,GROUPNAME) 
        values ((SELECT ID FROM USERTABLE where FIRSTNAME = 'Faaez'),'studentgroup');

insert into USERTABLE (FIRSTNAME, LASTNAME, PASSWORD, EMAIL)
        values('Hong','Sung','c4289629b08bc4d61411aaa6d6d4a0c3c5f8c1e848e282976e29b6bed5aeedc7','hsung@uco.edu');

insert into GROUPTABLE (USER_ID,GROUPNAME) 
        values ((SELECT ID FROM USERTABLE where FIRSTNAME = 'Hong'),'facultygroup');

insert into GROUPTABLE (USER_ID,GROUPNAME) 
        values ((SELECT ID FROM USERTABLE where FIRSTNAME = 'Hong'),'admingroup');

insert into COURSETABLE (TITLE, COURSEPREFIX, CODE)
        values ('Programming I', 'CMSC', 1613);

insert into COURSETABLE (TITLE, COURSEPREFIX, CODE)
        values ('Programming II', 'CMSC', 2613);

insert into COURSETABLE (TITLE, COURSEPREFIX, CODE)
        values ('Operating Systems', 'CMSC', 4153);

insert into STUDENTCOURSESTABLE (USER_ID, COURSE_ID)
        values ((SELECT ID FROM USERTABLE where FIRSTNAME = 'Faaez'),(SELECT ID FROM COURSETABLE where TITLE = 'Programming I'));