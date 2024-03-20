<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="es.studium.Gastos.Compra" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informes</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/resources/images/hucha.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
            background-color: #003366; /* Azul más oscuro */
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
		<a href="<%=request.getContextPath()%>/logout"
			class="btn btn-danger">Cerrar Sesión</a>
	</div>
    <h1>Informe de Compras</h1>
    <form action="<%=request.getContextPath()%>/InformeServlet" method="post">
        <div class="form-group">
            <label for="mes">Seleccione el Mes:</label>
            <select class="form-control" name="mes" id="mes">
                <%-- Agregar opciones de los meses --%>
                <%
                    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
                    for (int i = 0; i < meses.length; i++) {
                %>
                        <option value="<%=i+1%>"><%=meses[i]%></option>
                <%
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="anio">Seleccione el Año:</label>
            <select class="form-control" name="anio" id="anio">
                <%-- Agregar opciones de los años --%>
                <%
                    Calendar cal = Calendar.getInstance();
                    int añoActual = cal.get(Calendar.YEAR);
                    for (int i = añoActual; i >= 2000; i--) {
                %>
                        <option value="<%=i%>"><%=i%></option>
                <%
                    }
                %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Generar Informe</button>
    </form>

    <%-- Tabla para mostrar las compras --%>
    <table border="1">
        <thead>
            <tr>
                <th class="text-center">Fecha</th>
                <th class="text-center">Tienda</th>
                <th class="text-center">Importe</th>
            </tr>
        </thead>
        <tbody>
            <%-- Iterar sobre las compras para mostrarlas en la tabla --%>
            <%
            @SuppressWarnings("unchecked")
            List<Compra> compras = (List<Compra>) request.getAttribute("compras");
            if (compras != null && !compras.isEmpty()) {
                for (Compra compra : compras) {
            %>
                    <tr>
                        <td><%=new SimpleDateFormat("dd-MM-yyyy").format(compra.getFecha())%></td>
                        <td><%=compra.getNombreTienda()%></td>
                        <td><%=compra.getImporte().toString()%> €</td>
                    </tr>
            <%
                }
                // Obtener el total de las compras
                BigDecimal total = (BigDecimal) request.getAttribute("total");
            %>
                    <tr>
                        <td colspan="2" class="text-center"><strong>Total:</strong></td>
                        <td><strong><%=total.toString()%> €</strong></td>
                    </tr>
            <%
            } else {
            %>
                <tr>
                    <td colspan="3">No hay compras disponibles para este mes y año.</td>
                </tr>
            <%
            }
            %>
        </tbody>
    </table>
    <div class="text-center mt-3">
    <a href="<%=request.getContextPath()%>/NuevaCompra.jsp"
				class="btn btn-primary">Nueva Compra</a>
        <a href="<%= request.getContextPath() %>/PrincipalServlet" class="btn btn-primary">Volver a la Página Principal</a>
    </div>
</body>
</html>
