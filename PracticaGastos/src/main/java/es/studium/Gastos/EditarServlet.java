package es.studium.Gastos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/EditarServlet")
public class EditarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource pool;

    public EditarServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            InitialContext ctx = new InitialContext();
            pool = (DataSource) ctx.lookup("java:comp/env/jdbc/gastos");
            System.out.println("Conexión al DataSource establecida correctamente.");
        } catch (NamingException e) {
            System.err.println("Error al establecer la conexión al DataSource: " + e.getMessage());
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener ID de compra a editar
        String idCompraStr = request.getParameter("idCompra");
        if (idCompraStr == null || idCompraStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }
        int idCompra = Integer.parseInt(idCompraStr);

        // Reenviar la solicitud al JSP de edición junto con el ID de compra
        request.setAttribute("idCompra", idCompra);
        request.getRequestDispatcher("/EditarCompra.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCompraStr = request.getParameter("idCompra");
        String nuevaFechaStr = request.getParameter("nuevaFecha");
        String nuevoImporteStr = request.getParameter("nuevoImporte");
        String nuevaTiendaIdStr = request.getParameter("nuevaTienda");

        // Verificar si el parámetro idCompra está presente
        if (idCompraStr == null || idCompraStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro idCompra.");
            return;
        }

        int idCompra;
        try {
            idCompra = Integer.parseInt(idCompraStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro idCompra no es un número válido.");
            return;
        }

        // Convertir la fecha a LocalDate si se proporciona
        LocalDate nuevaFecha = null;
        if (nuevaFechaStr != null && !nuevaFechaStr.isEmpty()) {
            nuevaFecha = LocalDate.parse(nuevaFechaStr);
        }

        // Actualizar la compra con los datos proporcionados
        actualizarCompra(idCompra, nuevaFecha, nuevoImporteStr, nuevaTiendaIdStr);

        // Redireccionar de vuelta a la página principal
        response.sendRedirect(request.getContextPath() + "/PrincipalServlet");
    }

    private void actualizarCompra(int idCompra, LocalDate nuevaFecha, String nuevoImporteStr, String nuevaTiendaIdStr)
            throws ServletException {
        try (Connection conn = pool.getConnection()) {
            StringBuilder queryBuilder = new StringBuilder("UPDATE compras SET ");

            // Agregar la actualización de fecha si se proporciona
            if (nuevaFecha != null) {
                queryBuilder.append("fechaCompra = ?");
            }

            // Agregar la actualización de importe si se proporciona
            if (nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
                if (nuevaFecha != null) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append("importeCompra = ?");
            }

            // Agregar la actualización de tienda si se proporciona
            if (nuevaTiendaIdStr != null && !nuevaTiendaIdStr.isEmpty()) {
                if (nuevaFecha != null || nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append("idTiendaFK = ?");
            }

            queryBuilder.append(" WHERE idCompra = ?");

            try (PreparedStatement statement = conn.prepareStatement(queryBuilder.toString())) {
                int parameterIndex = 1;
                // Setear los parámetros de la consulta
                if (nuevaFecha != null) {
                    statement.setObject(parameterIndex++, nuevaFecha);
                }
                if (nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
                    double nuevoImporte = Double.parseDouble(nuevoImporteStr);
                    statement.setDouble(parameterIndex++, nuevoImporte);
                }
                if (nuevaTiendaIdStr != null && !nuevaTiendaIdStr.isEmpty()) {
                    int nuevaTiendaId = Integer.parseInt(nuevaTiendaIdStr);
                    statement.setInt(parameterIndex++, nuevaTiendaId);
                }
                statement.setInt(parameterIndex, idCompra);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la edición de la compra", e);
        }
    }
}
