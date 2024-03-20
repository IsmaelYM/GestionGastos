package es.studium.Gastos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EliminarCompraServlet")
public class EliminarCompraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        // Obtener el ID de la compra a eliminar
        int idCompra = Integer.parseInt(request.getParameter("idCompra"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

            // Eliminar la compra
            String deleteQuery = "DELETE FROM compras WHERE idCompra=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, idCompra);

                preparedStatement.executeUpdate();
            }
            // Redirigir al usuario a la página principal
            response.sendRedirect("PrincipalServlet");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // En caso de error, redirigir al usuario a una página de error
            response.sendRedirect("error.jsp");
        }
    }
}



