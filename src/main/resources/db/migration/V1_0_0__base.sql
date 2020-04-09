create table `customers` (
  `id` bigint not null auto_increment,
  email varchar(255),
  name varchar(255),
  primary key (`id`)
);

create table `accounts` (
  `id` bigint not null auto_increment,
  account_number varchar(32),
  account_type tinyint,
  customer_id bigint,
  balance bigint default 0,
  primary key (`id`),
  foreign key (customer_id) references `customers`(`id`)
);

insert into `customers` (email, name) values ('abc.xyz@bankrupt.com', 'Abc Xyz');
insert into `customers` (email, name) values ('vivek.prajapati@outlook.com', 'Vivek Prajapati');

insert into `accounts` (account_number, account_type, customer_id, balance) values ('NL12ABNA0123456789', 0, 1, 10000);
insert into `accounts` (account_number, account_type, customer_id, balance) values ('NL80ABNA0419499482', 0, 2, 10000);

create table `transfers` (
  `id` bigint not null auto_increment,
  payment_reference varchar(36) not null,
  source_id bigint not null,
  amount bigint not null,
  target_id bigint not null,
  description varchar(255),
  timestamp datetime not null,
  foreign key (target_id) references `accounts`(`id`)
);
