
create table test(
    comp_key1 varchar(8) not null,
    comp_key2 varchar(8) not null,
    field1 varchar(100),
    field2 char(1) not null,

    primary key (comp_key1, comp_key2)
);



