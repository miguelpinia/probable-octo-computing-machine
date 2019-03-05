begin;

drop schema if exists datos cascade;
create schema datos;


drop table if exists datos.user;
create table datos.user (
  id serial primary key,
  name text not null,
  created_by text not null,
  created_date date not null
);

commit;
