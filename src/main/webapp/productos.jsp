<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.utp.inventario.model.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos - Inventario</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="header">
    <strong>Inventario Web</strong>
    <div class="nav"><a href="dashboard">Dashboard</a><a href="movimientos">Registrar movimiento</a><a href="reporte-stock">Stock bajo</a></div>
</div>
<div class="container">
    <h1>Listado de productos</h1>
    <% if (request.getParameter("mensaje") != null) { %>
        <div class="alert success"><%= request.getParameter("mensaje") %></div>
    <% } %>
    <table>
        <thead>
        <tr><th>ID</th><th>Categoría</th><th>Producto</th><th>Unidad</th><th>Stock</th><th>Mínimo</th></tr>
        </thead>
        <tbody>
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            if (productos != null) {
                for (Producto p : productos) {
        %>
        <tr>
            <td><%= p.getIdProducto() %></td>
            <td><%= p.getCategoria() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getUnidadMedida() %></td>
            <td><%= p.getStockActual() %></td>
            <td><%= p.getStockMinimo() %></td>
        </tr>
        <%      }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
