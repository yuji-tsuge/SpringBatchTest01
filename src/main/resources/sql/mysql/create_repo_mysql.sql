
-- �f�[�^�x�[�X�̍쐬
drop database spring_batch_repo;
create database spring_batch_repo;

-- ���[�U�̍쐬
drop user batch;
grant all on spring_batch_repo.* to 'batch'@'localhost' identified by 'password' with grant option;

-- �f�[�^�x�[�X�̑I��
use spring_batch_repo;

-- DDL�̎��s
source schema-mysql.sql
