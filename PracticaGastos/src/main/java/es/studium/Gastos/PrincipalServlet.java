package es.studium.Gastos;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "PrincipalServlet", urlPatterns = { "/PrincipalServlet" })
public class PrincipalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource pool;

    public PrincipalServlet() {
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login");
            return;
        }
        List<Compra> compras = new ArrayList<>();
        try (Connection conn = pool.getConnection()) {
            Calendar cal = Calendar.getInstance();
            int mesActual = cal.get(Calendar.MONTH) + 1; 
            int añoActual = cal.get(Calendar.YEAR);

            String queryCompras = "SELECT c.idCompra, c.fechaCompra, c.importeCompra, t.nombreTienda " +
                    "FROM compras c " +
                    "INNER JOIN tiendas t ON c.idTiendaFK = t.idTienda " +
                    "WHERE MONTH(c.fechaCompra) = ? AND YEAR(c.fechaCompra) = ? " +
                    "ORDER BY c.fechaCompra ASC";

            try (PreparedStatement statement = conn.prepareStatement(queryCompras)) {
                statement.setInt(1, mesActual);
                statement.setInt(2, añoActual);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Compra compra = new Compra();
                        compra.setId(resultSet.getInt("idCompra"));
                        compra.setFecha(resultSet.getDate("fechaCompra"));
                        compra.setImporte(resultSet.getBigDecimal("importeCompra"));
                        compra.setNombreTienda(resultSet.getString("nombreTienda"));
                        compras.add(compra);
                    }
                }
            }
            
            // Calcular la suma del importe total de las compras
            BigDecimal totalImporte = BigDecimal.ZERO;
            for (Compra compra : compras) {
                totalImporte = totalImporte.add(compra.getImporte());
            }

            // Establecer el total de importe como un atributo en el objeto HttpServletRequest
            request.setAttribute("totalImporte", totalImporte);

            request.setAttribute("compras", compras);
            request.getRequestDispatcher("/Principal.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

