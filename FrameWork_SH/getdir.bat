SET "str1=%~dp0"
set "str2=bin"

set "str3=%str1%%str2%"


echo.%str3%

set CLASSPATH=D:/testng/testng-6.8.jar;%str3%

java org.testng.TestNG testng.xml



pause



pause