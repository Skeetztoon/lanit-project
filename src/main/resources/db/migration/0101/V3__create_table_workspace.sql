CREATE TABLE WORKSPACE
(
    id           NUMBER PRIMARY KEY,
    office_id    NUMBER NOT NULL,
    floor        NUMBER,
    room         NUMBER,
    table_number NUMBER NOT NULL,
    employee_id  NUMBER
);

ALTER TABLE WORKSPACE
ADD CONSTRAINT fk_workspace_office
FOREIGN KEY (office_id) REFERENCES OFFICE (id);

ALTER TABLE WORKSPACE
ADD CONSTRAINT fk_workspace_employee
FOREIGN KEY (employee_id) REFERENCES EMPLOYEE (id);

CREATE SEQUENCE sq_workspace_id START WITH 1 INCREMENT BY 1;

INSERT INTO WORKSPACE
VALUES (next value for sq_workspace_id, 2, 7, 711, 7, 5),
       (next value for sq_workspace_id, 2, 7, 711, 8, null),
       (next value for sq_workspace_id, 2, 7, 711, 9, null),
       (next value for sq_workspace_id, 2, 7, 711, 10, null),
       (next value for sq_workspace_id, 2, 7, 711, 11, null),
       (next value for sq_workspace_id, 1, 3, 324, 1, 6),
       (next value for sq_workspace_id, 1, 3, 324, 2, 7),
       (next value for sq_workspace_id, 1, 3, 324, 3, null),
       (next value for sq_workspace_id, 1, 3, 324, 4, null),
       (next value for sq_workspace_id, 1, 3, 324, 5, null);