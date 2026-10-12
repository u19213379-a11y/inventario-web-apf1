# Plan de pruebas manuales APF2 — pendiente de ejecución en Tomcat y MySQL

| Código | Escenario | Comportamiento esperado | Estado inicial |
|---|---|---|---|
| P01 | Abrir `/login.xhtml` | Aparece formulario Bootstrap | No ejecutado |
| P02 | Usuario ADMIN + clave de demo | Redirige a dashboard; se crea sesión | No ejecutado |
| P03 | Clave incorrecta | Mensaje; no inicia sesión | No ejecutado |
| P04 | Acceso directo a productos sin sesión | Redirecciona al login | No ejecutado |
| P05 | Crear producto válido | Se inserta con stock 0 y aparece en tabla | No ejecutado |
| P06 | Buscar por nombre o categoría | Tabla filtrada sin errores | No ejecutado |
| P07 | Paginación con más de 8 productos | Botones siguiente/anterior funcionales | No ejecutado |
| P08 | Registrar ENTRADA 5 | Inserta movimiento y suma 5 en productos | No ejecutado |
| P09 | Registrar SALIDA mayor al stock | Transacción rechazada; sin cambios | No ejecutado |
| P10 | Usuario ENCARGADO abre categorias.xhtml | Redirige a dashboard | No ejecutado |
| P11 | Reporte con stock actual <= mínimo | Devuelve productos de reposición | No ejecutado |
| P12 | Desconectar MySQL | Aparece mensaje de error; no exponer credenciales | No ejecutado |
| P13 | Vista móvil 375px | Navbar colapsa y tablas permiten scroll | No ejecutado |

## Verificación de consistencia tras movimientos
```sql
SELECT id_producto, nombre, stock_actual, stock_minimo FROM productos ORDER BY id_producto;
SELECT id_movimiento, id_producto, id_usuario, tipo, cantidad, fecha_registro
FROM movimientos ORDER BY id_movimiento DESC LIMIT 20;
```

Una prueba se marca OK solo al verificarla en un Tomcat/MySQL real; este archivo es un plan, no un acta de pruebas ejecutadas.
