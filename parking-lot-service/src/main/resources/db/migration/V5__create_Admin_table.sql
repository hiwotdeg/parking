create table admin
(
    id          serial not null,
    created_on  timestamp(6) with time zone,
    email       varchar(255),
    first_name  varchar(255),
    is_active   boolean,
    last_name   varchar(255),
    middle_name varchar(255),
    phone_no    varchar(255),
    updated_on  timestamp(6) with time zone,
    primary key (id)
);

alter table if exists admin
drop constraint if exists UK_c0r9atamxvbhjjvy5j8da1kam;

alter table if exists admin
    add constraint UK_c0r9atamxvbhjjvy5j8da1kam unique (email);

alter table if exists admin
drop constraint if exists UK_2fy3rhqhhmwykgs5fpbohuosq;

alter table if exists admin
    add constraint UK_2fy3rhqhhmwykgs5fpbohuosq unique (phone_no);