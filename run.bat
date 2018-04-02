@echo off
set CLASSPATH=target\classes
for %%i in (".\lib\*.jar") do call ".\cpappend.bat" %%i

@echo on
@echo CLASSPATH=%CLASSPATH%
@echo ApplicationContext         : %1
@echo JobNname or JobExecutionId : %2
@echo JobParameter[1] or Option  : %3
@echo JobParameter[2]            : %4
@echo JobParameter[3]            : %5
@echo JobParameter[4]            : %6
@echo JobParameter[5]            : %7
@echo JobParameter[5]            : %8

call setenvs.bat
@echo on
java -Ddatetime=%datetime% -DjobName=%2 org.springframework.batch.core.launch.support.CommandLineJobRunner %1 %2 %3 "datetime=%datetime%" %4 %5 %6 %7 %8
