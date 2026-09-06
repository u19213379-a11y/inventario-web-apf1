<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Inventario</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="header">
    <strong>Inventario Web</strong>
    <div class="nav">
        <a href="dashboard">Dashboard</a>
        <a href="productos">Productos</a>
        <a href="movimientos">Movimientos</a>
        <a href="reporte-stock">Stock bajo</a>
    </div>
</div>
<div class="container">
    <h1>Dashboard</h1>
    <div class="grid">
        <div class="card"><h3>Productos</h3><p>Consulta de stock y datos básicos.</p></div>
        <div class="card"><h3>Movimientos</h3><p>Entradas y salidas de inventario.</p></div>
        <div class="card"><h3>JDBC</h3><p><span class="badge"><%= Boolean.TRUE.equals(request.getAttribute("conexionActiva")) ? "Conexión exitosa" : "Conexión no disponible" %></span></p></div>
    </div>
</div>
</body>
</html>
