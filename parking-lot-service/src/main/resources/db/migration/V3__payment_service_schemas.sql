create table coupon_balance
(
    id         serial not null,
    amount     numeric(38, 2),
    created_on timestamp(6) with time zone,
    is_active  boolean,
    updated_on timestamp(6) with time zone,
    user_id    varchar(255),
    primary key (id)
)