create table driver
(
    id         serial not null,
    created_on timestamp(6) with time zone,
    email      varchar(255),
    first_name varchar(255),
    image_url  varchar(255),
    is_active  boolean,
    last_name  varchar(255),
    phone_no   varchar(255),
    updated_on timestamp(6) with time zone,
    primary key (id)
);

create table operation_hour
(
    id             serial not null,
    end_time       timestamp(6),
    price_per_hour numeric(38, 2),
    start_time     timestamp(6),
    parking_lot_id integer,
    primary key (id)
);
create table parking_lot
(
    id             serial not null,
    address        varchar(255),
    available_slot integer,
    capacity       integer,
    created_on     timestamp(6) with time zone,
    is_active      boolean,
    latitude       float(53),
    longitude      float(53),
    name           varchar(255),
    parking_type   smallint check (parking_type between 0 and 2),
    rating         float4,
    updated_on     timestamp(6) with time zone,
    provider_id    integer,
    primary key (id)
);

create table parking_lot_image
(
    id             serial not null,
    image_url      varchar(255),
    parking_lot_id integer,
    primary key (id)
);

create table parking_lot_provider
(
    id          serial not null,
    created_on  timestamp(6) with time zone,
    email       varchar(255),
    first_name  varchar(255),
    image_url   varchar(255),
    is_active   boolean,
    is_verified boolean,
    last_name   varchar(255),
    phone_no    varchar(255),
    role        smallint check (role between 0 and 1),
    updated_on  timestamp(6) with time zone,
    primary key (id)
);

create table reservation
(
    id                 serial not null,
    created_on         timestamp(6) with time zone,
    is_active          boolean,
    price              numeric(38, 2),
    reservation_status smallint check (reservation_status between 0 and 2),
    staying_duration   time(6),
    updated_on         timestamp(6) with time zone,
    driver_id          integer,
    parking_lot_id     integer,
    vehicle_id         integer,
    primary key (id)
);

create table review
(
    id             serial not null,
    comment        varchar(255),
    created_on     timestamp(6) with time zone,
    is_active      boolean,
    rate           float4 not null,
    updated_on     timestamp(6) with time zone,
    driver_id      integer,
    parking_lot_id integer,
    primary key (id)
);

create table vehicle
(
    id         serial not null,
    created_on timestamp(6) with time zone,
    is_active  boolean,
    model      varchar(255),
    name       varchar(255),
    plate      varchar(255),
    updated_on timestamp(6) with time zone,
    year       integer,
    driver_id  integer,
    primary key (id)
);


alter table if exists driver
    drop constraint if exists UK_fchuyotq64tagkwktlh4qttyy;
alter table if exists driver
    add constraint UK_fchuyotq64tagkwktlh4qttyy unique (email);

alter table if exists driver
    drop constraint if exists UK_c4cocwic0px2memd5nvhm444e;
alter table if exists driver
    add constraint UK_c4cocwic0px2memd5nvhm444e unique (phone_no);

alter table if exists parking_lot_provider
    drop constraint if exists UK_4crbpf6mpgycwyrs9f31ex45;
alter table if exists parking_lot_provider
    add constraint UK_4crbpf6mpgycwyrs9f31ex45 unique (email);

alter table if exists parking_lot_provider
    drop constraint if exists UK_e0lwdxlk3bicfuqcf2fp2cali;
alter table if exists parking_lot_provider
    add constraint UK_e0lwdxlk3bicfuqcf2fp2cali unique (phone_no);

alter table if exists operation_hour
    add constraint FK5tm3hjhbulpwcinotbb1xlxwl
        foreign key (parking_lot_id)
            references parking_lot;

alter table if exists parking_lot
    add constraint FKb87p4l5t73jdx3quwj6neer51
        foreign key (provider_id)
            references parking_lot_provider;


alter table if exists parking_lot_image
    add constraint FKmac24o0im7n9qf3if0nn7teat
        foreign key (parking_lot_id)
            references parking_lot;

alter table if exists reservation
    add constraint FKiabmugn54sqbxjcpujig6tawg
        foreign key (driver_id)
            references driver;

alter table if exists reservation
    add constraint FKjyqih0028km9u32wpiympdcys
        foreign key (parking_lot_id)
            references parking_lot;

alter table if exists reservation
    add constraint FKrm327sr0rb11mme0kbsm37od5
        foreign key (vehicle_id)
            references vehicle;

alter table if exists review
    add constraint FKc5ins2nksp4wrkoiusu9quc3g
        foreign key (driver_id)
            references driver;

alter table if exists review
    add constraint FKalr67dyaldg1hfip99wg45xqa
        foreign key (parking_lot_id)
            references parking_lot;

alter table if exists vehicle
    add constraint FKdpor9ohov2f3optwe7twe49tt
        foreign key (driver_id)
            references driver;



CREATE OR REPLACE FUNCTION update_average_rating()
    RETURNS TRIGGER AS
$$
DECLARE
    avg_rating REAL;
BEGIN
    SELECT AVG(review.rate)
    INTO avg_rating
    FROM review
    WHERE parking_lot_id = NEW.parking_lot_id;


    UPDATE parking_lot
    SET rating = avg_rating
    WHERE id = NEW.parking_lot_id;

    RETURN NULL;
END;
$$
    LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_rating_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON review
    FOR EACH ROW
EXECUTE FUNCTION update_average_rating();


CREATE OR REPLACE FUNCTION update_parking_lot()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.reservation_status = 0 THEN
        IF NEW.is_active = true THEN
            UPDATE parking_lot
            SET available_slot = available_slot - 1
            WHERE id = NEW.parking_lot_id;
        ELSE
            UPDATE parking_lot
            SET available_slot = available_slot + 1
            WHERE id = NEW.parking_lot_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reservation_update_trigger
    AFTER UPDATE
    ON reservation
    FOR EACH ROW
EXECUTE FUNCTION update_parking_lot();