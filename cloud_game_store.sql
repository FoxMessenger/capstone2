create schema if not exists productcloud_game_store;
create schema if not exists product;
create schema if not exists customer;
create schema if not exists invoice;
create schema if not exists invoice_item;
create schema if not exists level_up ;

create schema if not exists cloud_game_store_test;
create schema if not exists product_test;
create schema if not exists customer_test;
create schema if not exists invoice_test;
create schema if not exists invoice_item_test;
create schema if not exists level_up_test;

use product;
use cloud_game_store;
use customer;
use invoice;
use invoice_item;
use level_up;

use product_test;
use cloud_game_store_test;
use customer_test;
use invoice_test;
use invoice_item_test;
use level_up_test;

create table if not exists users(
   username varchar(50) not null primary key,
   password varchar(100) not null,
   enabled boolean not null
);
create table if not exists authorities (
   username varchar(50) not null,
   authority varchar(50) not null,
   constraint fk_authorities_users foreign key(username) references users(username));
   create unique index ix_auth_username on authorities (username,authority
);

-- password: admin
insert into users (username, password, enabled) values ('admin', '$2a$10$AHwsmz.AIfhZD41R8dF7FOBAsGSGkHCRwLThNf8TylZt9XILVwAnW', true);
insert into authorities (username, authority) values ('admin', 'ADMIN');
delete from users where username = 'admin';

select * from users;
select * from authorities;

