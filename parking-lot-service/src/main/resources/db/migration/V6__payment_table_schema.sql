create table payment
(
    id             serial not null,
    amount         integer,
    checkout_id    varchar(255),
    created_on     timestamp(6) with time zone,
    payment_status smallint check (payment_status between 0 and 2),
    payment_type   smallint check (payment_type between 0 and 1),
    updated_on     timestamp(6) with time zone,
    user_id        varchar(255),
    primary key (id)
)