<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.studium.Gastos.Tienda"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Compra</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/resources/images/hucha.png">
</head>
<body>

<div class="container">
    <h1 class="mt-5 mb-4 text-center">Editar Compra</h1>
    
    <!-- Código para obtener las tiendas -->
    <% 
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Tienda> tiendas = new ArrayList<>();
        
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

            statement = connection.prepareStatement("SELECT idTienda, nombreTienda FROM tiendas");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idTienda = resultSet.getInt("idTienda");
                String nombreTienda = resultSet.getString("nombreTienda");
                Tienda tienda = new Tienda(idTienda, nombreTienda);
                tiendas.add(tienda);
            }
        } catch (SQLException e) {
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
    
    <!-- Formulario de edición de compra -->
    <form action="EditarServlet" method="post">
        <div class="form-group row">
            <label for="nuevaFecha" class="col-sm-2 col-form-label">Fecha</label>
            <div class="col-sm-4">
                <input type="date" id="nuevaFecha" name="nuevaFecha" class="form-control">
            </div>
        </div>

        <div class="form-group row">
            <label for="nuevoImporte" class="col-sm-2 col-form-label">Importe:</label>
            <div class="col-sm-4">
                <input type="text" id="nuevoImporte" name="nuevoImporte" class="form-control">
            </div>
        </div>

        <div class="form-group row">
            <label for="nuevaTienda" class="col-sm-2 col-form-label">Tienda:</label>
            <div class="col-sm-4">
                <select id="nuevaTienda" name="nuevaTienda" class="form-control">
                    <% 
                        for (Tienda tienda : tiendas) {
                    %>
                    <option value="<%=tienda.getId()%>"><%=tienda.getNombreTienda()%></option>
                    <%
                        }
                    %>
                </select>
            </div>
        </div>

        <input type="hidden" name="idCompra" value="<%=request.getParameter("idCompra")%>">
        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
    </form>
    <!-- Botón para volver a la página principal -->
    <div class="text-center mt-3">
        <a href="<%= request.getContextPath() %>/PrincipalServlet" class="btn btn-primary">Volver a la Página Principal</a>
    </div>
</div>
</body>
</html>



