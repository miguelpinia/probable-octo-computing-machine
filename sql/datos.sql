begin;

drop extension if exists pgcrypto;
create extension pgcrypto;

drop schema if exists mapita cascade;
create schema mapita;

---------------------------------------------
-- Creamos una tabla de roles de usuarios. --
---------------------------------------------

drop table if exists mapita.rol;

create table mapita.rol (
  id serial primary key,
  rol text unique not null
);

comment on table mapita.rol
is
'El rol ROL que puede tomar un usuario son USUARIO, INFORMADOR, ADMINISTRADOR';

insert into mapita.rol (id, rol)
values (1, 'USUARIO'),
       (2, 'INFORMADOR'),
       (3, 'ADMINISTRADOR');

drop table if exists mapita.usuario;

create table mapita.usuario (
  id serial primary key,
  nombre text unique not null,
  correo text unique not null,
  password text not null,
  fotografia bytea,
  constraint email_valido check (correo ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

comment on table mapita.usuario
is
'El usuario NOMBRE tiene el correo CORREO con la contraseña PASSWORD';


---------------------------------------------
-- Un usuario puede tener múltiples roles. --
---------------------------------------------

drop table if exists mapita.usuario_rol;

create table mapita.usuario_rol (
  id serial primary key,
  usuario_id integer not null references mapita.usuario(id),
  rol_id integer not null references mapita.rol(id)
);

-----------------------------------------------------------------------
-- Agregamos un trigger para garantizar que la inserción de usuarios --
-- con su contraseña es segura, es decir, la contraseña del usuario  --
-- se va a cifrar con la función crypt y el algoritmo blowfish 'bf'  --
-- con 8 iteraciones para la función hash.                           --
-----------------------------------------------------------------------

drop function if exists mapita.hash();

create or replace function mapita.hash() returns trigger as $$
  begin
    if TG_OP = 'INSERT' then
       new.password = crypt(new.password, gen_salt('bf', 8)::text);
    end if;
    return new;
  end;
$$ language plpgsql;

comment on function mapita.hash()
is
'Cifra la contraseña del usuario al guardarla en la base de datos.';

drop trigger if exists hashpassword on mapita.usuario;

create trigger hashpassword
before insert on mapita.usuario
for each row execute procedure mapita.hash();

comment on trigger hashpassword on mapita.usuario
is
'Hashea la contraseña de un usuario al insertarlo en la base de datos.';

drop function if exists mapita.obten_usuario;

create or replace function mapita.obten_usuario(usuario text, contraseña text) returns mapita.usuario as $$
  select *
  from mapita.usuario
  where usuario = usuario and password = crypt(contraseña, password)
$$ language sql stable;

insert into mapita.usuario (nombre, password, correo)
values ('Miguel', 'contraseña', 'miguel_pinia@ciencias.unam.mx'),
       ('Olga', 'micontraseña', 'olga@mail.com'),
       ('Juan', 'password', 'juan@mail.com');

insert into mapita.usuario_rol (usuario_id, rol_id)
values (1, 1),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1);

create table mapita.color (
  id serial primary key,
  nombre text not null,
  hex_color text unique not null,
  constraint is_hex_color check (hex_color ~* '^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$')
);

insert into mapita.color (nombre, hex_color)
values ('black', '#000000'),
       ('red', '#FF0000'),
       ('lime', '#00FF00'),
       ('blue', '#0000FF'),
       ('green', '#008000');

create table mapita.tema (
  id serial primary key,
  color_id integer not null references mapita.color(id),
  nombre text not null,
  datos text not null
);

insert into mapita.tema (color_id, nombre, datos)
values (1, 'Campo de futbol', 'Información sobre los campos de futbol conocidos'),
       (2, 'Campo de tenis', 'Información sobre los campos de tenis conocidos'),
       (3, 'Gimnasio al aire libre', 'Información sobre los gimnasios al aire libre'),
       (4, 'Restaurantes mexicanos', 'Restaurantes mexicanos');

create table mapita.marcador (
  id serial primary key,
  tema_id integer not null references mapita.tema(id),
  descripcion text not null,
  datos text not null,
  ubicacion point not null
);

insert into mapita.marcador (tema_id, descripcion, datos, ubicacion)
values (3, 'Gimnasio de casa popular', 'Gimnasio ubicado en la Magdalena Contreras', '(19.322930, -99.221742)'),
       (3, 'Gimnasio de alberca olímpica', 'Gimnasio ubicado a un costado de la alberca olímpica de la UNAM', '(19.330626, -99.185229)');

create table mapita.lista_comentario (
  id serial primary key,
  marcador_id integer not null references mapita.marcador(id)
);

insert into mapita.lista_comentario (marcador_id)
values (1), (2);

create table mapita.comentario (
  id serial primary key,
  lista_comentario_id integer not null references mapita.lista_comentario(id),
  comentario text not null,
  calificacion integer not null
);

insert into mapita.comentario (lista_comentario_id, comentario, calificacion)
values (1, 'Un comentario', 0), (1, 'Dos comentarios', 10),
       (1, 'Tres comentarios', 20), (2, 'Un comentario', 100), (2, 'sin comentarios', 1);


commit;
