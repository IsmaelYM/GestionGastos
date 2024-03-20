<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nueva Tienda</title>
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/resources/images/hucha.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f6ff; /* Azul claro */
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            margin-top: 20px;
            color: #0056b3; /* Azul oscuro */
        }

        form {
            width: 50%;
            margin: 20px auto;
        }

        label {
            font-weight: bold;
        }

        .btn-primary {
            background-color: #0056b3; /* Azul oscuro */
        }

        .btn-primary:hover {
            background-color: #003366; 
        }

        .btn-secondary {
            background-color: #6c757d;
        }

        .btn-secondary:hover {
            background-color: #495057; 
        }
    </style>
</head>
<body>
    <!-- Esto es el botón del logout, ¿hay que ponerlo en cada página? -->
    <div style="text-align: right; margin: 10px;">
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Cerrar Sesión</a>
    </div>
    
    <h2>Añadir Nueva Tienda</h2>
    
    <form action="NuevaTiendaServlet" method="post">
        <div class="form-group">
            <label for="nombreTienda">Nombre de la Tienda:</label>
            <input type="text" class="form-control" name="nombreTienda" required>
        </div>

        <button type="submit" class="btn btn-primary">Añadir Tienda</button>
        <button type="reset" class="btn btn-secondary">Limpiar</button>
    </form>
     <div class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/PrincipalServlet" class="btn btn-primary">Volver a la Página Principal</a>
    </div>
</body>
</html>