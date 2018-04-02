@echo off

if not "%BASE_DIR%" == "" goto end

set BASE_DIR=C:\job\mysql
set MYSQL_BIN=%BASE_DIR%\bin
set PATH=%PATH%;%MYSQL_BIN%

:end

