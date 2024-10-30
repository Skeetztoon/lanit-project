CREATE TABLE ROLE
(
    id   NUMBER PRIMARY KEY,
    name varchar2(50) NOT NULL UNIQUE
);
CREATE SEQUENCE sq_role_id START WITH 1 INCREMENT BY 1;

CREATE TABLE EMPLOYEE
(
    id            NUMBER PRIMARY KEY,
    login         VARCHAR2(255) NOT NULL UNIQUE,
    first_name    VARCHAR2(255) NOT NULL,
    patronymic    VARCHAR2(255),
    last_name     VARCHAR2(255) NOT NULL,
    hash_password VARCHAR2(255) NOT NULL,
    email         VARCHAR(255)  NOT NULL UNIQUE,
    state         VARCHAR2(10)  NOT NULL
);
CREATE SEQUENCE sq_employee_id START WITH 1 INCREMENT BY 1;

CREATE TABLE EMPLOYEE_ROLE
(
    employee_id NUMBER NOT NULL,
    role_id NUMBER NOT NULL
);

CREATE TABLE OFFICE
(
    id NUMBER PRIMARY KEY,
    name VARCHAR2(40) NOT NULL
);

CREATE SEQUENCE sq_office_id START WITH 1 INCREMENT BY 1;

CREATE TABLE ATTENDANCE
(
    id NUMBER PRIMARY KEY,
    employee_id NUMBER NOT NULL,
    office_id NUMBER NOT NULL,
    entrance_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP NOT NULL
);

CREATE SEQUENCE sq_attendance_id START WITH 1 INCREMENT BY 1;

CREATE TABLE VACATION
(
    id NUMBER PRIMARY KEY,
    employee_id NUMBER NOT NULL,
    start DATE,
    end DATE
);

CREATE SEQUENCE sq_vacation_id START WITH 1 INCREMENT BY 1;

INSERT INTO ROLE VALUES (next value for sq_role_id, 'ROLE_ADMIN');
INSERT INTO ROLE VALUES (next value for sq_role_id, 'ROLE_OMNI');
INSERT INTO ROLE VALUES (next value for sq_role_id, 'ROLE_USER');
INSERT INTO ROLE VALUES (next value for sq_role_id, 'ROLE_HR');

-- LOGIN:PASSWORD:
-- admin:admin
-- omni:omni
-- user:user
-- hr:hrhr
-- ivanov:ivanov
-- petrov:petrov
-- sergeev:sergeev
INSERT INTO EMPLOYEE (id, login, first_name, patronymic, last_name, hash_password, email, state)
VALUES (next value for sq_employee_id, 'admin', 'Admin', null, 'Admin', '$2a$04$l6jf/IelD8EcKEx0z5LJFur01DtdBcTLUxfiq79X1GF2hgJdmIeEW', 'admin@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'omni', 'Omni', null, 'Omni', '$2a$04$y1oDipZIlwPEGBPtTrXT4.9enhB4zuQoQKstVueSSmK9qqdY.yU6y', 'omni@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'user', 'User', null, 'User', '$2a$04$uUTMuVyvusd6gIkxLdrF5ufDQ0K359C0Pjq6yBtbctOo3y6mhpwiy', 'user@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'hr', 'Hr', null, 'Hr', '$2a$10$XSQvfCA/R0pKCL01GUA9T.tQCUKRFfRt3uyOLEEYgCJQOaTN/F8Aa', 'hr@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'ivanov', 'Иван', 'Иванович', 'Иванов', '$2a$10$68olLYWwEaKT5tET0pyqEO2wALSDaQauWvXpYtyCWGRrxugiA3ibK', 'ivanov@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'petrov', 'Петр', 'Петрович', 'Петров', '$2a$10$apcWOiLHASd.0ZdPwVzKMOEnw8uLKJeb5dKMweD1X/S9QuwHTv9VW', 'petrov@hrjedu.com', 'ACTIVE'),
       (next value for sq_employee_id, 'sergeev', 'Сергей', 'Сергеевич', 'Сергеев', '$2a$10$B5oxDvzLGBAIbHnYFiSA5ux/6OtiPZWoDTkBpsxRl49lQlFYJhmjm', 'sergeev@hrjedu.com', 'ACTIVE');

INSERT INTO EMPLOYEE_ROLE
VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 3), (6, 3), (7, 3);

INSERT INTO OFFICE
VALUES (next value for sq_office_id, 'Москва'),
       (next value for sq_office_id, 'Нижний Новгород'),
       (next value for sq_office_id, 'Диптаун'),
       (next value for sq_office_id, 'Уфа');

INSERT INTO ATTENDANCE
VALUES  (next value for sq_attendance_id, 3, 1, PARSEDATETIME('10-01-2020 09:04:24','dd-MM-yyyy hh:mm:ss'), PARSEDATETIME('10-01-2020 18:20:01','dd-MM-yyyy hh:mm:ss')),
        (next value for sq_attendance_id, 3, 1, PARSEDATETIME('13-01-2020 09:20:25','dd-MM-yyyy hh:mm:ss'), PARSEDATETIME('13-01-2020 18:00:52','dd-MM-yyyy hh:mm:ss')),
        (next value for sq_attendance_id, 3, 2, PARSEDATETIME('14-01-2020 08:59:12','dd-MM-yyyy hh:mm:ss'), PARSEDATETIME('14-01-2020 18:05:45','dd-MM-yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('14.01.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('14.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('15.01.2020 09:40:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('15.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('16.01.2020 08:40:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('16.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('17.01.2020 11:00:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('17.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('20.01.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('20.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('21.01.2020 09:02:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('21.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('22.01.2020 08:40:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('22.01.2020 16:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('23.01.2020 08:58:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('23.01.2020 17:40:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('24.01.2020 09:10:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('24.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('27.01.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('27.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('28.01.2020 10:02:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('28.01.2020 15:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('29.01.2020 08:30:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('29.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('30.01.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('30.01.2020 20:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('31.01.2020 08:45:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('31.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('03.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('03.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('04.02.2020 08:56:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('04.02.2020 14:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('05.02.2020 08:51:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('05.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('06.02.2020 09:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('06.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('07.02.2020 08:43:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('07.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('10.02.2020 07:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('10.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('11.02.2020 08:53:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('11.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('12.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('12.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,5,2,PARSEDATETIME('13.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('13.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('14.01.2020 08:51:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('14.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('15.01.2020 09:42:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('15.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('16.01.2020 08:30:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('16.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('17.01.2020 07:00:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('17.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('20.01.2020 08:52:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('20.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('21.01.2020 09:12:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('21.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('22.01.2020 08:40:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('22.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('23.01.2020 08:28:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('23.01.2020 19:40:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('24.01.2020 09:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('24.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('27.01.2020 08:39:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('27.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('28.01.2020 10:22:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('28.01.2020 19:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('29.01.2020 08:10:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('29.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('30.01.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('30.01.2020 20:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('31.01.2020 08:55:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('31.01.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('03.02.2020 08:39:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('03.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('04.02.2020 09:56:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('04.02.2020 19:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('05.02.2020 08:21:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('05.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('06.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('06.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('07.02.2020 08:49:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('07.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('10.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('10.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('11.02.2020 08:53:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('11.02.2020 19:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('12.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('12.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,6,1,PARSEDATETIME('13.02.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('13.02.2020 18:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('14.01.2020 08:58:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('14.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('15.01.2020 08:04:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('15.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('16.01.2020 08:30:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('16.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('17.01.2020 08:00:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('17.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('20.01.2020 08:52:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('20.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('21.01.2020 08:12:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('21.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('22.01.2020 08:40:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('22.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('23.01.2020 08:28:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('23.01.2020 17:40:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('24.01.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('24.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('27.01.2020 08:39:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('27.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('28.01.2020 08:22:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('28.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('29.01.2020 08:10:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('29.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('30.01.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('30.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('31.01.2020 08:55:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('31.01.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('03.02.2020 08:39:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('03.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('04.02.2020 08:56:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('04.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('05.02.2020 08:21:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('05.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('06.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('06.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('07.02.2020 08:49:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('07.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('10.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('10.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('11.02.2020 08:53:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('11.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('12.02.2020 08:59:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('12.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss')),
        (next value for sq_attendance_id,7,1,PARSEDATETIME('13.02.2020 08:50:00','dd.MM.yyyy hh:mm:ss'),PARSEDATETIME('13.02.2020 17:59:00','dd.MM.yyyy hh:mm:ss'));
