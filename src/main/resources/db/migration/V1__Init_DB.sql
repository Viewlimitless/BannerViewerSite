create table banner (
    id bigint not null,
    content varchar(255),
    deleted bit not null,
    name varchar(255) not null,
    price decimal(8,2) not null,
    category_id bigint not null,
    primary key (id));

create table category (
    id bigint not null,
    deleted bit not null,
    name varchar(255) not null,
    reqname varchar(255) not null,
    primary key (id));

create table hibernate_sequence (
next_val bigint);

insert into hibernate_sequence values ( 1 );

insert into hibernate_sequence values ( 1 );

insert into hibernate_sequence values ( 1 );

create table request (
    id bigint not null,
    user_agent varchar(255) not null,
    date datetime not null,
    ip_address varchar(255) not null,
    banner_id bigint ,
    primary key (id));

alter table banner
    add constraint FK2evybotynuel3qb4r4tkqvjuh
    foreign key (category_id) references category (id);

alter table request
    add constraint FKrbdxvkl5bwuycjo82cq0nosl4
    foreign key (banner_id) references banner (id);