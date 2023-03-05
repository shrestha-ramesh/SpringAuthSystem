create table car(
    id int auto_increment,
    name varchar(255),
    brand varchar(255)
);
create table bike(
    id int auto_increment,
    name varchar(255),
    brand varchar(255)
);

insert into car(name, brand) values('Civic', 'Honda');
insert into car(name, brand) values('Toyota', 'Corolla');
insert into car(name, brand) values('Hyundai', 'Venue');

insert into bike(name, brand) values('R15', 'Yamaha');
insert into bike(name, brand) values('Splendor', 'Hero');
insert into bike(name, brand) values('Apache', 'TVS');