
-- category

drop sequence if exists product_seq;

create sequence product_seq start with 1001 increment by 1;

drop sequence if exists category_seq;

create sequence category_seq start with 11 increment by 1;

drop table category if exists;

create table category (category_no integer not null, category_name varchar(255), parent_no integer, depth integer not null, primary key (category_no));

drop table product if exists;

create table product (product_no bigint not null, brand_name varchar(255), product_name varchar(255), product_price decimal(19,2), category_no integer, primary key (product_no));
