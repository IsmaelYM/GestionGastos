<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="es.studium.Gastos.Compra"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Compras</title>
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

h1 {
	text-align: center;
	margin-top: 20px;
	color: #0056b3; /* Azul oscuro */
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
	border: 1px solid #bdd9ff; /* Azul claro */
}

th, td {
	padding: 10px;
	text-align: left;
	border-bottom: 1px solid #bdd9ff; /* Azul claro */
}

th {
	background-color: #c2e0ff; /* Azul claro */
}

tr:nth-child(even) {
	background-color: #e6f3ff; /* Azul muy claro */
}

tr:hover {
	background-color: #cce5ff; /* Azul claro */
}

td:last-child {
	text-align: center;
}
</style>
</head>
<body>
	<div style="text-align: right; margin: 10px;">
		<a href="<%=request.getContextPath()%>/logout" class="btn btn-danger">Cerrar
			Sesión</a>
	</div>
	<h1>Lista de Compras</h1>
	<div class="text-center" style="margin-bottom: 20px;">
		<p id="mesActual" class="font-weight-bold">Mes Actual:</p>
	</div>
	<table border="1">
		<thead>
			<tr>
				<th class="text-center">Fecha</th>
				<th class="text-center">Tienda</th>
				<th class="text-center">Importe</th>
				<th class="text-center">Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			@SuppressWarnings("unchecked")
			List<Compra> compras = (List<Compra>) request.getAttribute("compras");
			if (compras != null && !compras.isEmpty()) {
				BigDecimal totalImporte = BigDecimal.ZERO; // Inicializar el total de importe como 0
				for (Compra compra : compras) {
					totalImporte = totalImporte.add(compra.getImporte()); // Sumar el importe de cada compra
			%>
			<tr>
				<td><%=new SimpleDateFormat("dd-MM-yyyy").format(compra.getFecha())%></td>
				<td><%=compra.getNombreTienda()%></td>
				<td><%=compra.getImporte().toString()%> €</td>
				<td>
					<div class="btn-group">
						<form action="GuardarEdicionServlet" method="post">
							<input type="hidden" name="idCompra" value="<%=compra.getId()%>">
							<input type="submit" class="btn btn-outline-primary mx-3"
								value="Editar">
						</form>
						<form action="EliminarCompraServlet" method="post">
							<input type="hidden" name="idCompra" value="<%=compra.getId()%>">
							<input type="submit" class="btn btn-outline-danger"
								value="Eliminar">
						</form>
						
						<input type="hidden" name="idCompra" value="<%=compra.getId()%>">
<% System.out.println("Valor de idCompra: " + compra.getId()); %>
						
					</div>
				</td>
			</tr>
			<%
			}
			%>
			<tr>
				<td colspan="2" class="text-center"><strong>Total:</strong></td>
				<td><strong><%=totalImporte.toString()%> €</strong></td>
				<!-- Mostrar el total de importe -->
				<td></td>
			</tr>
			<%
			} else {
			%>
			<tr>
				<td colspan="4">No hay compras disponibles.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<div class="container mt-3 py-3">
		<div class="float-left text-center">
			<a href="<%=request.getContextPath()%>/NuevaTienda.jsp"
				class="btn btn-primary">Nueva Tienda</a> <a
				href="<%=request.getContextPath()%>/EditarTienda.jsp"
				class="btn btn-secondary ml-2">Editar Tienda</a> <a
				href="<%=request.getContextPath()%>/EliminarTienda.jsp"
				class="btn btn-danger ml-2">Eliminar Tienda</a>
		</div>
		<div class="float-right text-center">
			<a href="<%=request.getContextPath()%>/NuevaCompra.jsp"
				class="btn btn-primary">Nueva Compra</a> <a
				href="<%=request.getContextPath()%>/Informes.jsp"
				class="btn btn-secondary ml-2">Informes</a>
		</div>
	</div>
	<script>
		let listaMeses = [ "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" ];
		let fecha = new Date();
		let mesActual = fecha.getMonth();
		document.getElementById("mesActual").innerHTML += listaMeses[mesActual];
	</script>
</body>
</html>


