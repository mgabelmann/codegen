CREATE TABLE test.department (
    id NUMBER(10) NOT NULL,
    dept_name VARCHAR2(50) NOT NULL,
    CONSTRAINT department_pk PRIMARY KEY (id)
);

CREATE TABLE test.employee (
    --UUID in java
    id RAW(16) NOT NULL,
    employee_no NUMBER(10) NOT NULL,
    employee_name VARCHAR2(50) NOT NULL,
    dept_id NUMBER(10),

    CONSTRAINT employee_pk PRIMARY KEY (id),
    CONSTRAINT department_fk FOREIGN KEY (dept_id) REFERENCES deptartment(id)
);
