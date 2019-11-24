CREATE TABLE EMPLOYEE(
    ID          LONG AUTO_INCREMENT PRIMARY KEY,
    Name        VARCHAR2(20),
    Tax_Id      VARCHAR2(50),
    Role        VARCHAR2(50)
);

insert into employee
values (101, 'Daniel', 'Daniel-1', 'Developer');

insert into employee
values (102, 'Peter', 'Peter-1', 'Product Manager');

insert into employee
values (103, 'Marius', 'Marius-1', 'Developer');

insert into employee
values (104, 'Torsten', 'Torsten-1', 'CEO');

insert into employee
values (105, 'Theresa', 'Theresa-1', 'Developer');

insert into employee
values (106, 'Patrick', 'Patrick-1', 'Developer');

insert into employee
values (107, 'Jana', 'Jana-1', 'CHRO');

insert into employee
values (108, 'Hakan', 'Hakan-1', 'Product Manager');

insert into employee
values (109, 'Marcel', 'Marcel-1', 'Developer');

insert into employee
values (110, 'Dorian', 'Dorian-1', '');

commit;