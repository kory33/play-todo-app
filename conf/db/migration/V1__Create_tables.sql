create table todo_list (
  id varchar(36) not null primary key,
  title varchar(60) not null
);

create table todo_item (
  id bigint auto_increment not null primary key,
  todo_list_id varchar(36) not null,
  title varchar(60) not null,
  description text
);

create table tag (
  id bigint auto_increment not null primary key,
  todo_list_id varchar(36) not null,
  name varchar(35) not null,
  color char(6) not null
);

create table reply (
  id bigint auto_increment not null primary key,
  todo_item_id bigint not null,
  content text
);

create table todo_item_tag (
  todo_item_id bigint not null,
  tag_id bigint not null
);