-- ====================================================
-- user table
-- ====================================================
drop table t_user
go
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
go

alter table t_user
  add constraint t_user_pkey
  primary key(id)
go
--alter table t_user lock datarows
--go
-- deffault user
insert into t_user values ("U0001", "test1", "password", "R0001", "test1@example.com","s1","n1","text","df",getdate(), null)
go
insert into t_user values ("U0002", "test2", "password", "R0002", "test2@example.com","s2","n2","text","df",getdate(), null)
go
-- ====================================================
-- code table (for List value)
-- ====================================================
drop table t_code
go

create table t_code (
   code varchar(10)
  , category varchar(20)
  , data varchar(50)
)

alter table t_code
  add constraint t_code_pkey
  primary key(code)
go

insert into t_code values ("s1", "sex","male")
go
insert into t_code values ("s2", "sex","famale")
go
insert into t_code values ("n1", "nationality","Japan")
go
insert into t_code values ("n2", "nationality","USA")
go
insert into t_code values ("n3", "nationality","Others")
go
insert into t_code values ("h1", "habit","drinking")
go
insert into t_code values ("h2", "habit","smorking")
go
insert into t_code values ("null", "dummy","")
go
insert into t_code values ("h1,h2", "dummy","drinling,smorking")
go



-- ====================================================
-- role table
-- ====================================================
drop table t_role
go
create table t_role (
  id varchar(5)
  , name varchar(32)
  , start_date datetime
  , end_date datetime null
)
go
alter table t_role
  add constraint t_role_pkey
  primary key(id)
go
--alter table t_role lock datarows
--go
insert into t_role values ("ALL", "Default", getdate(), null)
go
insert into t_role values ("R0001", "Administrator", getdate(), null)
go
insert into t_role values ("R0002", "User", getdate(), null)
go

-- ====================================================
-- action permission table
-- ====================================================
drop table t_action_perm
go
create table t_action_perm (
  role_id varchar(5)
  , url varchar(50)
  , start_date datetime
  , end_date datetime null
)
go
alter table t_action_perm
  add constraint t_role_action_pkey
  primary key(role_id, url)
go
--alter table t_user_role lock datarows
--go
--R0001
insert into t_action_perm values ("ALL", "/top", getdate(), null)
go
insert into t_action_perm values ("R0001", "/user", getdate(), null)
go
insert into t_action_perm values ("R0001", "/collection", getdate(), null)
go
insert into t_action_perm values ("R0001", "/upload", getdate(), null)
go


-- ====================================================
-- menu table
-- ====================================================
drop table t_menu
go
create table t_menu (
  id varchar(5)
  , name varchar(32)
  , content varchar(32)
  , url varchar(128)
  , start_date datetime
  , end_date datetime null
)
go
alter table t_menu
  add constraint t_menu_pkey
  primary key(id)
go
--alter table t_menu lock datarows
--go
--default menu
insert into t_menu values ("M0001", "user", "user", "/user", getdate(), null)
go
insert into t_menu values ("M0002", "collection", "collection", "/collection", getdate(), null)
go
insert into t_menu values ("M0003", "upload", "upload", "/upload", getdate(), null)
go

-- ====================================================
-- role's menu table
-- ====================================================
drop table t_role_menu
go
create table t_role_menu (
  role_id varchar(5)
  , menu_id varchar(5)
  , start_date datetime
  , end_date datetime null
)
go
alter table t_role_menu
  add constraint t_role_menu_pkey
  primary key (role_id, menu_id)
go
--alter table t_user_menu lock datarows
--go
-- defult user's menu
insert  into t_role_menu values ("R0001", "M0001", getdate(), null)
go
insert  into t_role_menu values ("R0001", "M0002", getdate(), null)
go
insert  into t_role_menu values ("R0001", "M0003", getdate(), null)
go
insert  into t_role_menu values ("R0002", "M0001", getdate(), null)
go



