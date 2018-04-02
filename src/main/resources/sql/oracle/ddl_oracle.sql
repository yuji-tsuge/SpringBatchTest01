-- ====================================================
-- user table
-- ====================================================
drop table t_user
;

create table t_user (
  id varchar2(5)
  , name varchar2(16)
  , password varchar2(50)
  , role_id varchar2(5)
  , email varchar2(64)
  , sex varchar2(10)
  , nationality varchar2(10)
  , text varchar2(100)
  , defKey    varchar2(2)
  , start_date date
  , end_date date )
;

alter table t_user
  add constraint t_user_pkey
  primary key(id)
;
--alter table t_user lock datarows
--;
-- deffault user
insert into t_user values ('U0001', 'test1', 'password', 'R0001', 'test1@example.com','s1','n1','text','df',sysdate, null)
;
insert into t_user values ('U0002', 'test2', 'password', 'R0002', 'test2@example.com','s2','n2','text','df',sysdate, null)
;
-- ====================================================
-- code table (for List value)
-- ====================================================
drop table t_code
;

create table t_code (
   code varchar2(10)
  , category varchar2(20)
  , data varchar2(50)
)
;
alter table t_code
  add constraint t_code_pkey
  primary key(code)
;

insert into t_code values ('s1', 'sex','male')
;
insert into t_code values ('s2', 'sex','famale')
;
insert into t_code values ('n1', 'nationality','Japan')
;
insert into t_code values ('n2', 'nationality','USA')
;
insert into t_code values ('n3', 'nationality','Others')
;
insert into t_code() values ('role', 'ALL', 'Default')
;
insert into t_code() values ('role', 'R0001', 'Administrator')
;
insert into t_code() values ('role', 'R0002', 'User')
;

-- ====================================================
-- role table
-- ====================================================
drop table t_role
;
create table t_role (
  id varchar2(5)
  , name varchar2(32)
  , start_date date
  , end_date date 
)
;
alter table t_role
  add constraint t_role_pkey
  primary key(id)
;

insert into t_role values ('ALL', 'Default', sysdate, null)
;
insert into t_role values ('R0001', 'Administrator', sysdate, null)
;
insert into t_role values ('R0002', 'User', sysdate, null)
;

-- ====================================================
-- action permission table
-- ====================================================
drop table t_action_perm
;
create table t_action_perm (
  role_id varchar2(5)
  , url varchar2(50)
  , start_date date
  , end_date date 
)
;
alter table t_action_perm
  add constraint t_role_action_pkey
  primary key(role_id, url)
;

--R0001
insert into t_action_perm values ('ALL', '/top', sysdate, null)
;
insert into t_action_perm values ('R0001', '/user', sysdate, null)
;
insert into t_action_perm values ('R0001', '/collection', sysdate, null)
;
insert into t_action_perm values ('R0001', '/upload', sysdate, null)
;


-- ====================================================
-- menu table
-- ====================================================
drop table t_menu
;
create table t_menu (
  id varchar2(5)
  , name varchar2(32)
  , content varchar2(32)
  , url varchar2(128)
  , start_date date
  , end_date date 
)
;
alter table t_menu
  add constraint t_menu_pkey
  primary key(id)
;

--default menu
insert into t_menu values ('M0001', 'user', 'user', '/user', sysdate, null)
;
insert into t_menu values ('M0002', 'collection', 'collection', '/collection', sysdate, null)
;
insert into t_menu values ('M0003', 'upload', 'upload', '/upload', sysdate, null)
;

-- ====================================================
-- role's menu table
-- ====================================================
drop table t_role_menu
;
create table t_role_menu (
  role_id varchar2(5)
  , menu_id varchar2(5)
  , start_date date
  , end_date date 
)
;
alter table t_role_menu
  add constraint t_role_menu_pkey
  primary key (role_id, menu_id)
;

-- defult user's menu
insert  into t_role_menu values ('R0001', 'M0001', sysdate, null)
;
insert  into t_role_menu values ('R0001', 'M0002', sysdate, null)
;
insert  into t_role_menu values ('R0001', 'M0003', sysdate, null)
;
insert  into t_role_menu values ('R0002', 'M0001', sysdate, null)
;



