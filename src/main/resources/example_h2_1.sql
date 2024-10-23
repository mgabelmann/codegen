--create tables
create table address(
    country varchar(2) not null,
    primary_address char(1) not null check (primary_address in ('N','Y')),
    prov varchar(8) not null,

    --address_id integer not null,
    --person_id integer not null,
    address_id uuid not null,
    person_id uuid not null,

    postal varchar(16) not null,
    attention varchar(100) not null,
    city varchar(100) not null,
    civic_address varchar(100),
    delivery_info varchar(100),
    postal_info varchar(100),
    primary key (address_id)
);

create table person (
    birth_dt date not null,
    sex_code varchar(1) not null,

    person_id uuid not null,
    --person_id integer not null,

    first_name varchar(75) not null,
    last_name varchar(75) not null,
    middle_name varchar(75),
    primary key (person_id)
);

create table sex_code (
    id varchar(1) not null,
    description varchar(24) not null,
    primary key (id)
);


--alter table statements for FKs
/*
alter table if exists person
    add constraint FKkr973bltqks4ob25rav9smmgf
    foreign key (sex_code)
    references sex_code;

alter table if exists address
    add constraint FK81ihijcn1kdfwffke0c0sjqeb
    foreign key (person_id)
    references person;
 */
