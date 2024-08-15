create table address(
    country varchar(2) not null,
    primary_address char(1) not null check (primary_address in ('N','Y')),
    prov varchar(8) not null,
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
