<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Tienda</title>
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

        .btn-danger {
            background-color: #dc3545; /* Rojo */
        }

        .btn-danger:hover {
            background-color: #c82333; /* Rojo oscuro */
        }
    </style>
</head>
<body>
    <!-- Botón de cerrar sesión -->
    <div style="text-align: right; margin: 10px;">
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Cerrar Sesión</a>
    </div>

    <h2>Eliminar Tienda</h2>

    <form action="EliminarTiendaServlet" method="post">
        <div class="form-group">
            <label for="idTienda">Seleccione la Tienda a Eliminar:</label>
            <select class="form-control" name="idTienda" required>
                <% 
                    Connection connection = null;
                    PreparedStatement statement = null;
                    ResultSet resultSet = null;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

                        statement = connection.prepareStatement("SELECT idTienda, nombreTienda FROM tiendas");
                        resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                            int idTienda = resultSet.getInt("idTienda");
                            String nombreTienda = resultSet.getString("nombreTienda");
                %>
                            <option value="<%= idTienda %>"><%= nombreTienda %></option>
                <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (resultSet != null) resultSet.close();
                            if (statement != null) statement.close();
                            if (connection != null) connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                %>
            </select>
        </div>

        <div class="container mt-3">
            <div class="text-center">
                <button type="submit" class="btn btn-danger">Eliminar Tienda</button>
            </div>
        </div>
    </form>

    <div class="container mt-3">
        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success" role="alert">
                <%= request.getAttribute("successMessage") %>
            </div>
        <% } else if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>
    </div>
</body>
</html>
