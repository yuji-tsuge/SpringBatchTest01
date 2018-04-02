@echo off
call setenvs.bat

set TARGET_DIST=%1
if not "%TARGET_DIST%" == "" goto createArchive
set TARGET_DIST=local

: createArchive
@echo on
call mvn clean package -P %TARGET_DIST% -DskipTests=true
