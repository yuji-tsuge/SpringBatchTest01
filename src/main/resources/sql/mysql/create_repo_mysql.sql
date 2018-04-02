
-- データベースの作成
drop database spring_batch_repo;
create database spring_batch_repo;

-- ユーザの作成
drop user batch;
grant all on spring_batch_repo.* to 'batch'@'localhost' identified by 'password' with grant option;

-- データベースの選択
use spring_batch_repo;

-- DDLの実行
source schema-mysql.sql
