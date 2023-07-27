CREATE TABLE category
(
    category_id        bigint      NOT NULL AUTO_INCREMENT,
    parent_id          bigint NULL,
    name               varchar(20) NOT NULL,
    level              int         NOT NULL,
    active             boolean     NOT NULL,
    created_date       timestamp   NOT NULL CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_modified_date timestamp   NOT NULL CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (category_id)
);

INSERT INTO category (name, level, active)
values ('절화',1,true );

INSERT INTO category (name, level, active)
values ('관엽',1,true );

INSERT INTO category (name, level, active)
values ('난',1,true );

INSERT INTO category (name, level, active)
values ('춘란',1,true );