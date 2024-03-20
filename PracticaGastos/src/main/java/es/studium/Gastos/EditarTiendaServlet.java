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

@WebServlet("/EditarTiendaServlet")
public class EditarTiendaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        // Obtener los datos de la solicitud
        int idTienda = Integer.parseInt(request.getParameter("idTienda"));
        String nuevoNombreTienda = request.getParameter("nuevoNombreTienda");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

            // Actualizar la tienda
            String updateQuery = "UPDATE tiendas SET nombreTienda=? WHERE idTienda=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, nuevoNombreTienda);
                preparedStatement.setInt(2, idTienda);

                preparedStatement.executeUpdate();
            }
            
            response.sendRedirect("PrincipalServlet");
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }
        response.sendRedirect("PrincipalServlet");
    }
}
