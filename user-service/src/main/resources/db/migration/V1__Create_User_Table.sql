create table user
(
    id               bigint auto_increment primary key,
    created_at       datetime(6)  not null,
    created_by       varchar(255) not null,
    last_modified_by varchar(255) null,
    updated_at       datetime(6)  null,
    address          varchar(255) null,
    email            varchar(255) null,
    name             varchar(255) null
);

