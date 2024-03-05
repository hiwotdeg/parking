create table users
(
    id          serial not null,
    created_on  timestamp(6) with time zone,
    is_active   boolean,
    updated_on  timestamp(6) with time zone,
    role        varchar(255) check (role in ('DRIVER', 'PROVIDER', 'ADMIN')),
    password    varchar(255),
    role_id     integer,
    phonenumber varchar(255),
    primary key (id)
);