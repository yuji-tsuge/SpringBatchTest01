@echo off

rem set JAVA_HOME=C:\job\jdk1.8.0_60
set JAVA_HOME=C:\job\jdk1.8.0_131
set M2_HOME=C:\job\apache-maven-3.2.1
rem set PATH=.\;C:\Windows\System32;%JAVA_HOME%\bin;%M2_HOME%\bin
set PATH=.\;%JAVA_HOME%\bin;%M2_HOME%\bin

set date_tmp=%date:/=%
set time_tmp=%time: =0%
set yyyy=%date_tmp:~0,4%
set yy=%date_tmp:~2,2%
set mm=%date_tmp:~4,2%
set dd=%date_tmp:~6,2%
set hh=%time_tmp:~0,2%
set mi=%time_tmp:~3,2%
set ss=%time_tmp:~6,2%
set sss=%time_tmp:~9,2%
set datetime=%yyyy%%mm%%dd%%hh%%mi%%ss%%sss%
set time_tmp=
set date_tmp=

@echo on
@echo ... setup complete.