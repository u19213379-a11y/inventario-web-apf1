# Sistema web de control de inventario — Distribuidora Nova Lima S.A.C.

**Curso:** Desarrollo Web Integrado · **Equipo:** Paisic Campos Anil, Yucra Mamani Vanesa y Thomas Romani Arbieto · **UTP 2026-2**

Consolidamos el proyecto que iniciamos en el APF1 y desarrollamos su segunda etapa con JavaServer Faces (JSF), Facelets, Managed Beans y Bootstrap. Seguimos utilizando el mismo esquema MySQL `inventario_apf1`, la estructura Maven y el nombre del WAR `inventario-web-apf1`.

## 1. Qué incorporamos

- Autenticación por usuario de MySQL con roles ADMIN y ENCARGADO (ya no se usa la contraseña fija del antiguo Servlet).
- Dashboard con indicadores y movimientos recientes.
- Alta y búsqueda de productos con paginación de `<h:dataTable>`.
- Gestión de categorías reservada al rol ADMIN.
- Entradas y salidas con control de stock y transacciones SQL.
- Reportes de reposición cuando el stock es menor o igual al mínimo.
- Bootstrap 5.3.3, plantilla Facelets reutilizable y formularios JSF.
- Retrocompatibilidad de URLs anteriores del APF1 mediante redirecciones (Servlets/JSP); **las pantallas activas son `.xhtml`**.
- Prototipos A, B y C del APF1 conservados en `src/main/webapp/prototipos/`.

## 2. Requisitos

- Java JDK 17, Maven 3.9+ y NetBeans 17+.
- **Apache Tomcat 9**, no Tomcat 10/11 (el proyecto usa bibliotecas `javax.*`).
- MySQL Server 8.x y Workbench.
- Conexión a Internet para los recursos Bootstrap vía CDN.

## 3. Preparación de la base de datos

**Antes de ejecutar scripts sobre una base existente, realizamos una copia de seguridad en MySQL.**

- `database/inventario_apf1.sql`: script **histórico del APF1**; no volver a ejecutarlo sobre una base ya creada, pues sus `CREATE TABLE` e `INSERT` no están diseñados para migraciones.
- `database/inventario_apf2.sql`: script actualizado que crea tablas ausentes y migra únicamente las dos contraseñas de demostración del APF1 cuando siguen siendo `123456`. Conserva el nombre de base `inventario_apf1` y las cuatro entidades.

En MySQL Workbench abrimos y ejecutamos `database/inventario_apf2.sql`. Si ya existían datos reales, revisamos las sentencias de carga de demostración antes de aplicarlas. No es un script para producción.

### Variables de conexión (configurar en el proceso de Tomcat)

- `DB_URL=jdbc:mysql://localhost:3306/inventario_apf1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima`
- `DB_USER=root` (ejemplo de desarrollo)
- `DB_PASSWORD=CLAVE_LOCAL_DE_MYSQL`

En Windows, las variables definidas en otra consola no siempre pasan al proceso de Tomcat que ejecuta NetBeans. Podemos configurarlas en el entorno del servidor local, sin publicarlas en GitHub ni dentro del proyecto. La aplicación usa `ConexionBD` y no almacena claves de MySQL en el código.

### Usuarios académicos de prueba

| Rol | Correo | Clave de demostración |
|---|---|---|
| Administrador | `admin@utp.edu.pe` | `NovaDemo2026!` |
| Encargado | `almacen@utp.edu.pe` | `Almacen2026!` |

Las claves se comparan usando PBKDF2-HMAC-SHA256 con salt (solo demostración local). Evitamos usar estas cuentas en una instalación real.

## 4. Compilación y despliegue

1. Abrimos esta carpeta, que contiene `pom.xml`, como **proyecto Maven** en NetBeans.
2. Seleccionamos JDK 17 y Tomcat 9.
3. Ejecutamos `mvn clean package` dentro de la carpeta raíz.
4. Si el comando finaliza con `BUILD SUCCESS`, copiamos `target/inventario-web-apf1.war` al directorio `webapps` de Tomcat 9 o lo ejecutamos desde NetBeans.
5. Abrimos `http://localhost:8080/inventario-web-apf1/` y comprobamos el inicio de sesión, productos, movimientos y reportes.

El `web.xml` del APF2 configura `FacesServlet` con mapeo `*.xhtml`.

## 5. Organización

| Ubicación | Uso |
|---|---|
| `src/main/java/pe/utp/inventario/bean` | Managed Beans JSF |
| `src/main/java/pe/utp/inventario/dao` | Consultas y operaciones JDBC |
| `src/main/java/pe/utp/inventario/model` | Clases de dominio |
| `src/main/java/pe/utp/inventario/config` | Conexión, PBKDF2 y filtro de acceso |
| `src/main/java/pe/utp/inventario/servlet` | Redirecciones compatibles con rutas APF1 |
| `src/main/webapp/*.xhtml` | Páginas principales de Facelets |
| `src/main/webapp/prototipos` | Alternativas visuales del primer avance |
| `src/main/webapp/*.jsp` | Redirecciones compatibles con antiguas URLs |
| `src/main/webapp/WEB-INF` | `web.xml`, `faces-config.xml` y plantilla |
| `database/` | SQL de APF1 y de APF2 |
| `docs/` | Diagramas y notas históricas |
| `scripts/` | Comprobaciones y guía de evidencias |

## 6. Pruebas y entrega

El código se ha revisado de forma estática, pero **no contamos aún con una ejecución comprobada de Maven/Tomcat/MySQL en la computadora del equipo**. Antes de presentar las capturas del APF2 debemos confirmar el despliegue y completar `PLAN_PRUEBAS.md`.

Conservamos la evolución del APF1 en los commits del mismo repositorio GitHub. La captura del historial de commits debe corresponder a cambios realmente publicados por los integrantes; no se inventarán capturas ni colaboradores.

Consulta `PASOS_GITHUB.md` para subir los cambios y `scripts/guia_capturas.md` para las evidencias de la rúbrica.
