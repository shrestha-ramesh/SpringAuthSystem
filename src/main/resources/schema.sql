drop table if exists product;

create table product(
    id int not null primary key,
    name varchar(256),
    price double not null
);