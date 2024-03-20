<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Nueva Compra</title>
<link rel="icon" type="image/png"
	href="<%=request.getContextPath()%>/resources/images/hucha.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
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
	<!-- Botón de cerrar sesión -->
	<div style="text-align: right; margin: 10px;">
		<a href="<%=request.getContextPath()%>/logout"
			class="btn btn-danger">Cerrar Sesión</a>
	</div>

	<h2>Nueva Compra</h2>

	<form action="NuevaCompraServlet" method="post">
		<div class="form-group">
			<label for="fechaCompra">Fecha de Compra:</label> <input type="date"
				class="form-control" name="fechaCompra" required>
		</div>

		<div class="form-group">
			<label for="importeCompra">Importe de Compra:</label> <input
				type="text" class="form-control" name="importeCompra"
				pattern="^\d+(\,\d{1,2})?$" required>
		</div>


		<div class="form-group">
			<label for="idUsuario">Usuario:</label> <select class="form-control"
				name="idUsuario" required>
				<%
				Connection connection = null;
				PreparedStatement statement = null;
				ResultSet resultSet = null;

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

					statement = connection.prepareStatement("SELECT idUsuario, nombreUsuario FROM usuarios");
					resultSet = statement.executeQuery();

					while (resultSet.next()) {
						int idUsuario = resultSet.getInt("idUsuario");
						String nombreUsuario = resultSet.getString("nombreUsuario");
				%>
				<option value="<%=idUsuario%>"><%=nombreUsuario%></option>
				<%
				}
				} catch (Exception e) {
				e.printStackTrace();
				} finally {
				try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				}
				%>
			</select>
		</div>

		<div class="form-group">
			<label for="idTienda">Tienda:</label> <select class="form-control"
				name="idTienda" required>
				<%
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

					statement = connection.prepareStatement("SELECT idTienda, nombreTienda FROM tiendas");
					resultSet = statement.executeQuery();

					while (resultSet.next()) {
						int idTienda = resultSet.getInt("idTienda");
						String nombreTienda = resultSet.getString("nombreTienda");
				%>
				<option value="<%=idTienda%>"><%=nombreTienda%></option>
				<%
				}
				} catch (Exception e) {
				e.printStackTrace();
				} finally {
				try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
				} catch (SQLException e) {
				e.printStackTrace();
				}
				}
				%>
			</select>
		</div>



		<div class="container mt-3">
			<div class="float-left">
				<div>
					<button type="submit" class="btn btn-primary mr-2">Añadir
						Compra</button>
				</div>
			</div>
			<button type="reset" class="btn btn-secondary">Limpiar</button>
			<div class="text-center mt-3">
				<a href="<%=request.getContextPath()%>/PrincipalServlet"
					class="btn btn-primary">Volver a la Página Principal</a>
			</div>
		</div>
	</form>


	<div class="container mt-3">
		<%
		if (request.getAttribute("successMessage") != null) {
		%>
		<div class="alert alert-success" role="alert">
			<%=request.getAttribute("successMessage")%>
		</div>
		<%
		} else if (request.getAttribute("errorMessage") != null) {
		%>
		<div class="alert alert-danger" role="alert">
			<%=request.getAttribute("errorMessage")%>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>