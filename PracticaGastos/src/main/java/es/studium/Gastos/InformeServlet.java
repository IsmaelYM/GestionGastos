package es.studium.Gastos;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "InformeServlet", urlPatterns = { "/InformeServlet" })
public class InformeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource pool;

    public InformeServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            InitialContext ctx = new InitialContext();
            pool = (DataSource) ctx.lookup("java:comp/env/jdbc/gastos");
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el mes y el año seleccionados por el usuario
        int mes = Integer.parseInt(request.getParameter("mes"));
        int año = Integer.parseInt(request.getParameter("anio"));

        List<Compra> compras = obtenerComprasDelMes(mes, año); // Obtener las compras del mes y año seleccionados

        // Calcular el total de las compras
        BigDecimal total = BigDecimal.ZERO;
        for (Compra compra : compras) {
            total = total.add(compra.getImporte());
        }

        // Agregar el total a la lista de compras
        request.setAttribute("compras", compras);
        request.setAttribute("total", total);

        // Redirigir a la página JSP para mostrar las compras
        request.getRequestDispatcher("/Informes.jsp").forward(request, response);
    }

    // Método para obtener las compras del mes y año seleccionados desde la base de datos
    private List<Compra> obtenerComprasDelMes(int mes, int año) {
        List<Compra> compras = new ArrayList<>();
        try (Connection conn = pool.getConnection()) {
            // Consulta SQL para obtener las compras del mes y año seleccionados
            String query = "SELECT c.*, t.nombreTienda " +
            		"FROM compras c " +
            		"INNER JOIN tiendas t ON c.idTiendaFK = t.idTienda " +
            		"WHERE MONTH(fechaCompra) = ? AND YEAR(fechaCompra) = ? " +
            		"ORDER BY fechaCompra ASC";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, mes);
                statement.setInt(2, año);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Compra compra = new Compra();
                        // Configurar la compra con los datos obtenidos de la base de datos
                        compra.setId(resultSet.getInt("idCompra"));
                        compra.setFecha(resultSet.getDate("fechaCompra"));
                        compra.setImporte(resultSet.getBigDecimal("importeCompra"));
                        compra.setNombreTienda(resultSet.getString("nombreTienda"));
                        // Agregar la compra a la lista
                        compras.add(compra);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }
}
