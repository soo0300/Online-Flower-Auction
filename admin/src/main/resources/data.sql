create table admin
(
    admin_id           bigint primary key not null auto_increment,
    login_id           varchar(20)        not null unique,
    login_pw           varchar(60)        not null,
    name               varchar(20)        not null,
    tel                varchar(13)        not null unique,
    position           varchar(2)         not null,
    active             boolean            not null default true,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now()
);

insert into admin(login_id, login_pw, name, tel, position)
values ('lyt1228', 'pw1!', '임우택', '010-1111-1111', '00'),
       ('leeyr0412', 'pw2!', '이예리', '010-2222-2222', '10'),
       ('ghdgn22', 'pw3!', '홍승준', '010-3333-3333', '10'),
       ('seoyj505', 'pw4!', '서용준', '010-4444-4444', '10'),
       ('hans52410537', 'pw5!', '신성주', '010-5555-5555', '10'),
       ('', 'pw6!', '김수진', '010-6666-6666', '10');