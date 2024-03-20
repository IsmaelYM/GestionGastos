<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Formulario de Acceso</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center align-items-center" style="height: 100vh;">
            <div class="col-md-6">
                <form action="login" method="post" class="bg-white p-4 rounded shadow-sm">
                    <h1 class="text-center mb-4">Iniciar Sesión</h1>
                    <div class="form-group">
                        <label for="usuario">Usuario:</label>
                        <input type="text" id="usuario" name="usuario" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="password">Contraseña:</label>
                        <input type="password" id="password" name="password" class="form-control">
                    </div>
                    <input type="submit" value="Iniciar Sesión" class="btn btn-primary btn-block">
                    <% 
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                            out.println("<p class=\"text-danger text-center mt-3\">" + error + "</p>");
                        }
                    %>
                </form>
            </div>
        </div>
    </div>
    
</body>
</html>
