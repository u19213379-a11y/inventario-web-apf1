# Publicación del APF2 en nuestro repositorio original

Repositorio: https://github.com/u19213379-a11y/inventario-web-apf1

**Importante:** no crear otro repositorio. Primero compilamos en NetBeans/Tomcat y revisamos los archivos.

## Opción recomendada: GitHub Desktop (preserva el historial)

1. Entramos en GitHub Desktop e iniciamos sesión con la cuenta propietaria del repositorio.
2. En `File > Clone repository`, elegimos `inventario-web-apf1` y lo clonamos en una carpeta distinta de la copia descargada como ZIP.
3. Creamos una rama `apf2-jsf` (`Current branch > New branch`) antes de copiar los cambios.
4. En el explorador de Windows abrimos la carpeta `inventario-web-apf1` de este paquete y copiamos **todo su contenido**, incluidas las carpetas `src`, `database`, `docs`, `scripts` y los archivos `.gitignore`, `pom.xml`, `README.md`. Lo pegamos **dentro de la raíz del repositorio clonado**, aceptando el reemplazo de los archivos antiguos. **No copiamos la carpeta superior como subcarpeta ni borramos `.git`.**
5. Regresamos a GitHub Desktop y revisamos los cambios de la pestaña `Changes`. Verificamos especialmente `src/main/java`, `src/main/webapp`, `pom.xml` y `database/inventario_apf2.sql`.
5. Creamos una rama `apf2-jsf` (`Current branch > New branch`) para revisar los cambios sin alterar directamente la rama principal.
6. Escribimos un commit propio y verdadero, por ejemplo `feat(apf2): integrar JSF, Facelets y Bootstrap`; presionamos `Commit to apf2-jsf`.
7. Pulsamos `Publish branch` y, tras la revisión del equipo, abrimos un pull request hacia la rama predeterminada.
8. Comprobamos en GitHub que `pom.xml`, `src/main/java/pe/utp/inventario/bean`, las vistas `.xhtml` y el SQL actualizado aparezcan en la **raíz correcta**. Guardamos capturas reales de la sección `Commits`.

## Alternativa: web de GitHub

Entramos al repositorio original, creamos primero una rama `apf2-jsf` y usamos `Agregar archivo > Subir archivos` para arrastrar el **contenido interno** de la carpeta `inventario-web-apf1` de este paquete. El navegador debe mantener las rutas internas; revisamos la lista de archivos antes de confirmar. No debe aparecer una carpeta duplicada `inventario-web-apf1/inventario-web-apf1` ni `Proyecto_Unificado_Java_JSF`.

**Nota:** el navegador puede limitar cantidades de archivos y la subida de carpetas. Para un proyecto Java con varias carpetas recomendamos GitHub Desktop, porque conserva todas las rutas, facilita detectar cambios y evita el envío de un ZIP sin descomprimir.

## Revisión posterior

- `mvn clean package` termina con `BUILD SUCCESS`.
- Tomcat 9 despliega la aplicación sin excepciones.
- MySQL conecta y el inicio de sesión académico funciona.
- Entradas/salidas modifican el stock y el historial de forma correcta.
- `h:dataTable` lista registros reales de MySQL.
- Adjuntamos capturas reales y el enlace del repositorio actualizado al informe.

**No subir** archivos `.env`, `setenv.bat` con secretos, `target/`, contraseñas privadas ni datos reales de clientes.
