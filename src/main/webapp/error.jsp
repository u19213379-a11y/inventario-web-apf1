<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
<div class="container"><div class="card"><h1>Error del sistema</h1><p><%= request.getAttribute("error") %></p><a class="btn" href="dashboard">Volver</a></div></div>
</body>
</html>
