use kkoch_admin;

-- admin table create

drop table if exists admin;
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

-- auction table create

drop table if exists auction;
create table auction
(
    auction_id         bigint primary key not null auto_increment,
    code               integer            not null,
    start_time         timestamp          not null,
    active             boolean            not null default true,
    status             varchar(20)        not null default 'READY',
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    admin_id           bigint             null,
    foreign key (admin_id) references admin (admin_id)
);

-- notice table create

drop table if exists notice;
create table notice
(
    notice_id          bigint primary key not null auto_increment,
    title              varchar(50)        not null,
    content            longtext           not null,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    admin_id           bigint             null,
    foreign key (admin_id) references admin (admin_id)
);

-- category table create

drop table if exists category;
create table category
(
    category_id        bigint primary key not null auto_increment,
    name               varchar(20)        not null,
    level              integer            not null,
    active             boolean            not null default true,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    parent_id          bigint             null,
    foreign key (parent_id) references category (parent_id)
);

-- plane table create

drop table if exists plant;
create table plant
(
    plant_id           bigint primary key not null auto_increment,
    active             boolean            not null default true,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    code               bigint             not null,
    type               bigint             not null,
    name               bigint             not null,
    foreign key (code) references category (category_id),
    foreign key (type) references category (category_id),
    foreign key (name) references category (category_id)
);

-- stats table create

drop table if exists stats;
create table stats
(
    stats_id           bigint primary key not null auto_increment,
    price_avg          integer            not null,
    grade              varchar(20)        not null,
    count              integer            not null,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    plant_id           bigint             null,
    foreign key (plant_id) references plant (plant_id)
);

-- trade table create

drop table if exists trade;
create table trade
(
    trade_id           bigint primary key not null auto_increment,
    total_price        integer            not null,
    trade_time         timestamp          not null,
    pickup_status      boolean            not null,
    active             boolean            not null,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    member_id          bigint             null
);

-- auction article table create

drop table if exists auction_article;
create table auction_article
(
    auction_article_id bigint primary key not null auto_increment,
    auction_number     varchar(10)        not null,
    grade              varchar(20)        not null,
    count              integer            not null,
    bid_price          integer            not null default 0,
    bid_time           integer            not null default now(),
    region             varchar(20)        not null,
    shipper            varchar(20)        not null,
    start_price        integer            not null,
    created_date       timestamp          not null default now(),
    last_modified_date timestamp          not null default now(),
    plant_id           bigint             null,
    auction_id         bigint             null,
    trade_id           bigint             null,
    foreign key (plant_id) references plant (plant_id),
    foreign key (auction_id) references auction (auction_id),
    foreign key (trade_id) references trade (trade_id)
);

-- insert admin

insert into admin(login_id, login_pw, name, tel, position)
values ('lyt1228', 'pw1!', '임우택', '010-1111-1111', '00'),
       ('leeyr0412', 'pw2!', '이예리', '010-2222-2222', '10'),
       ('ghdgn22', 'pw3!', '홍승준', '010-3333-3333', '10'),
       ('seoyj505', 'pw4!', '서용준', '010-4444-4444', '10'),
       ('hans52410537', 'pw5!', '신성주', '010-5555-5555', '10'),
       ('soo0300', 'pw6!', '김수진', '010-6666-6666', '10');
