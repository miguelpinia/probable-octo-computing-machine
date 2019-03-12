begin;

drop schema if exists datos cascade;
create schema datos;


drop table if exists datos.user;

create table datos.user (
  id serial primary key,
  nombre text not null,
  correo text not null,
  contrasena text not null
);

commit;
