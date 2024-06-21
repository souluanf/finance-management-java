create table transaction
(
    id               bigint auto_increment primary key,
    created_at       datetime(6)    not null,
    created_by       varchar(255) not null,
    last_modified_by varchar(255) null,
    updated_at       datetime(6)    null,
    amount           decimal(38, 2) null,
    from_user_id     bigint null,
    to_user_id       bigint null
);

