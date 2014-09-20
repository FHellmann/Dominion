set TOPDIR=%~dp0
set CHECKDIR=%TOPDIR%stylechecker\

@echo #### Checkstyler ####
java -jar %CHECKDIR%checkstyle-5.6-all.jar -c %CHECKDIR%checkstyle.xml -r %~dp0..\src > %TOPDIR%result-checkstyle.txt
@echo #### Simian ####
java -jar %CHECKDIR%simian-2.3.34.jar -config=%CHECKDIR%simian.config %TOPDIR%..\src\**\*.java > %TOPDIR%result-simian.txt
@echo #### PMD ####
%CHECKDIR%\pmd-bin-5.1.1\bin\pmd -verbose -R %CHECKDIR%pmd.xml -f html -d %TOPDIR%..\src > %TOPDIR%result-pmd.html

PAUSE
