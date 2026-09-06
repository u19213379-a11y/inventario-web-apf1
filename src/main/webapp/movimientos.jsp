<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.utp.inventario.model.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Movimientos - Inventario</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="header">
    <strong>Inventario Web</strong>
    <div class="nav"><a href="dashboard">Dashboard</a><a href="productos">Productos</a><a href="reporte-stock">Stock bajo</a></div>
</div>
<div class="container">
    <div class="card">
        <h1>Registrar movimiento</h1>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post" action="movimientos">
            <label>Producto</label>
            <select name="idProducto" required>
                <%
                    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                    if (productos != null) {
                        for (Producto p : productos) {
                %>
                <option value="<%= p.getIdProducto() %>"><%= p.getNombre() %> - stock: <%= p.getStockActual() %></option>
                <%      }
                    }
                %>
            </select>
            <label>Tipo de movimiento</label>
            <select name="tipo" required>
                <option value="ENTRADA">Entrada</option>
                <option value="SALIDA">Salida</option>
            </select>
            <label>Cantidad</label>
            <input type="number" name="cantidad" min="1" required>
            <label>Observación</label>
            <textarea name="observacion" rows="3"></textarea>
            <button class="btn" type="submit">Guardar movimiento</button>
        </form>
    </div>
</div>
</body>
</html>
