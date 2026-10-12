# Sistema Web de Gestión y Control de Inventarios

Proyecto académico del curso **Desarrollo Web Integrado - UTP**, desarrollado para el caso de estudio de **Distribuidora Nova Lima S.A.C.**

## Descripción

Aplicación web para registrar productos, controlar entradas y salidas de inventario, consultar el stock disponible y visualizar productos con stock bajo.

## Tecnologías

- Java 17
- Java EE: JSP y Servlets
- Apache Tomcat 9
- Maven
- MySQL 8
- JDBC con MySQL Connector/J
- HTML5 y CSS3 responsive

## Módulos principales

- Inicio de sesión
- Dashboard
- Gestión de productos
- Registro de movimientos de inventario
- Reporte de stock bajo

## Requisitos previos

- JDK 17
- Apache Tomcat 9
- MySQL Server 8
- Apache Maven 3.9+
- NetBeans, Eclipse o IntelliJ IDEA

## Configuración de base de datos

Ejecutar el script:

```text
database/inventario_apf1.sql
```

Verificar los parámetros de conexión en:

```text
src/main/java/pe/utp/inventario/config/ConexionBD.java
```

Configuración local incluida en el proyecto:

```text
URL: jdbc:mysql://localhost:3306/inventario_apf1
Usuario: root
Contraseña: admin
```

Los datos de conexión deben ajustarse a la configuración local de MySQL.

## Compilación

```bash
mvn clean package
```

Maven generará el archivo WAR en:

```text
target/inventario-web-apf1.war
```

## Ejecución

Desplegar el archivo WAR en Apache Tomcat 9 y acceder desde:

```text
http://localhost:8080/inventario-web-apf1/
```
