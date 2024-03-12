alter table if exists users
drop
constraint if exists UK_e0lwdxik3bicfuqcf2fp2coli;
alter table if exists users
    add constraint UK_e0lwdxik3bicfuqcf2fp2coli unique (phonenumber);