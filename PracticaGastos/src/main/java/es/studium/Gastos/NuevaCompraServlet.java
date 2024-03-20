package es.studium.Gastos;import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/NuevaCompraServlet")
public class NuevaCompraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String fechaCompra = request.getParameter("fechaCompra");
        String importeCompraStr = request.getParameter("importeCompra");
        importeCompraStr = importeCompraStr.replace(",", "."); // Reemplazar la coma por el punto
        double importeCompra = Double.parseDouble(importeCompraStr);
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idTienda = Integer.parseInt(request.getParameter("idTienda"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gastos", "usuarioGastos", "usuarioGastos");

            // Aquí vamos a insertar la nueva compra
            String insertQuery = "INSERT INTO compras (fechaCompra, importeCompra, idUsuarioFK, idTiendaFK) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, fechaCompra);
                preparedStatement.setDouble(2, importeCompra);
                preparedStatement.setInt(3, idUsuario);
                preparedStatement.setInt(4, idTienda);

                preparedStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }

        response.sendRedirect("PrincipalServlet");
    }
}
