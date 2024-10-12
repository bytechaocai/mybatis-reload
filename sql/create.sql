create schema mybatis;

create table mybatis.t_user
(
    id varchar(17) not null primary key,
    username varchar(64) not null default '',
    age int not null default 18,
    birthday timestamp not null default current_timestamp(),
    delete_status int not null default 0,
    create_time timestamp not null default current_timestamp(),
    update_time timestamp,
    delete_time timestamp
);
