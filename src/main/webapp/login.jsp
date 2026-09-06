<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Inventario</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="container">
    <div class="card" style="max-width:460px;margin:auto;">
        <h2>Inicio de sesión</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post" action="login">
            <label>Correo</label>
            <input type="email" name="correo" value="admin@utp.edu.pe" required>
            <label>Contraseña</label>
            <input type="password" name="clave" value="123456" required>
            <button class="btn" type="submit">Ingresar</button>
        </form>
    </div>
</div>
</body>
</html>
