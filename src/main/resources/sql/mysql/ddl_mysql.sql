-- ====================================================
-- user table
-- ====================================================
drop table t_user;

create table t_user (
  id varchar(5)
  , name varchar(16)
  , password varchar(50)
  , role_id varchar(5)
  , email varchar(64)
  , sex varchar(10)
  , nationality varchar(10)
  , text varchar(100)
  , defKey    varchar(2)
  , start_date datetime
  , end_date datetime null)
;

alter table t_user
  add constraint t_user_pkey
  primary key(id)
;

-- deffault user
insert into t_user values ('U0001', 'test1', 'password', 'R0001', 'test1@example.com','s1','n1','text','df',current_timestamp(), null)
;
insert into t_user values ('U0002', 'test2', 'password', 'R0002', 'test2@example.com','s2','n2','text','df',current_timestamp(), null)
;
-- ====================================================
-- code table (for List value)
-- ====================================================
drop table t_code;

create table t_code (
   code varchar(10)
  , category varchar(20)
  , data varchar(50)
)
;

alter table t_code
  add constraint t_code_pkey
  primary key(code)
;

insert into t_code values ('s1', 'sex','male');
insert into t_code values ('s2', 'sex','famale');
insert into t_code values ('n1', 'nationality','Japan');
insert into t_code values ('n2', 'nationality','USA');
insert into t_code values ('n3', 'nationality','Others');
insert into t_code values ('h1', 'habit','drinking');
insert into t_code values ('h2', 'habit','smorking');
insert into t_code values ('null', 'dummy','');
insert into t_code values ('h1,h2', 'dummy','drinling,smorking');


-- ====================================================
-- role table
-- ====================================================
drop table t_role;

create table t_role (
  id varchar(5)
  , name varchar(32)
  , start_date datetime
  , end_date datetime null
)
;

alter table t_role
  add constraint t_role_pkey
  primary key(id)
;

-- alter table t_role lock datarows

insert into t_role values ('ALL', 'Default', current_timestamp(), null);
insert into t_role values ('R0001', 'Administrator', current_timestamp(), null);
insert into t_role values ('R0002', 'User', current_timestamp(), null);

-- ====================================================
-- action permission table
-- ====================================================
drop table t_action_perm;

create table t_action_perm (
  role_id varchar(5)
  , url varchar(50)
  , start_date datetime
  , end_date datetime null
);

alter table t_action_perm
  add constraint t_role_action_pkey
  primary key(role_id, url)
;

-- alter table t_user_role lock datarows

-- R0001

insert into t_action_perm values ('ALL', '/top', current_timestamp(), null)
;
insert into t_action_perm values ('R0001', '/user', current_timestamp(), null)
;
insert into t_action_perm values ('R0001', '/collection', current_timestamp(), null)
;
insert into t_action_perm values ('R0001', '/upload', current_timestamp(), null)
;


-- ====================================================
-- menu table
-- ====================================================
drop table t_menu
;

create table t_menu (
  id varchar(5)
  , name varchar(32)
  , content varchar(32)
  , url varchar(128)
  , start_date datetime
  , end_date datetime null
);

alter table t_menu
  add constraint t_menu_pkey
  primary key(id)
;

-- alter table t_menu lock datarows
-- default menu
insert into t_menu values ('M0001', 'user', 'user', '/user', current_timestamp(), null)
;
insert into t_menu values ('M0002', 'collection', 'collection', '/collection', current_timestamp(), null)
;
insert into t_menu values ('M0003', 'upload', 'upload', '/upload', current_timestamp(), null)
;

-- ====================================================
-- role's menu table
-- ====================================================
drop table t_role_menu
;
create table t_role_menu (
  role_id varchar(5)
  , menu_id varchar(5)
  , start_date datetime
  , end_date datetime null
)
;
alter table t_role_menu
  add constraint t_role_menu_pkey
  primary key (role_id, menu_id)
;

-- alter table t_user_menu lock datarows

-- defult user's menu
insert  into t_role_menu values ('R0001', 'M0001', current_timestamp(), null)
;
insert  into t_role_menu values ('R0001', 'M0002', current_timestamp(), null)
;
insert  into t_role_menu values ('R0001', 'M0003', current_timestamp(), null)
;
insert  into t_role_menu values ('R0002', 'M0001', current_timestamp(), null)
;

