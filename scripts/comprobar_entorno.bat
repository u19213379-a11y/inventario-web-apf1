@echo off
chcp 65001 >nul
echo ==== NOVA LIMA APF2 ==== 
java -version
call mvn -version
if errorlevel 1 (
 echo ERROR: Instala Maven 3.9+ y configura PATH.
 pause
 exit /b 1
)
call mvn clean package
if errorlevel 1 (
 echo ERROR: Fallo la compilacion; revisa el mensaje anterior.
 pause
 exit /b 1
)
echo WAR generado: target\inventario-web-apf1.war
echo Copia el WAR a TOMCAT9\webapps y enciende Tomcat 9.
pause
