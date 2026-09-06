<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.utp.inventario.model.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Stock Bajo</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="header">
    <strong>Inventario Web</strong>
    <div class="nav"><a href="dashboard">Dashboard</a><a href="productos">Productos</a><a href="movimientos">Movimientos</a></div>
</div>
<div class="container">
    <h1>Reporte de productos con stock bajo</h1>
    <table>
        <thead><tr><th>Producto</th><th>Categoría</th><th>Stock actual</th><th>Stock mínimo</th><th>Estado</th></tr></thead>
        <tbody>
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            if (productos != null && !productos.isEmpty()) {
                for (Producto p : productos) {
        %>
        <tr>
            <td><%= p.getNombre() %></td>
            <td><%= p.getCategoria() %></td>
            <td><%= p.getStockActual() %></td>
            <td><%= p.getStockMinimo() %></td>
            <td><span class="badge">Reponer</span></td>
        </tr>
        <%      }
            } else { %>
        <tr><td colspan="5">No hay productos con stock bajo.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
