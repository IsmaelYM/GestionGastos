package es.studium.Gastos;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/GuardarEdicionServlet")
public class GuardarEdicionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource pool;

    public GuardarEdicionServlet() {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCompraStr = request.getParameter("idCompra");
        String nuevaFecha = request.getParameter("nuevaFecha");
        String nuevoImporteStr = request.getParameter("nuevoImporte");
        String nuevaTiendaIdStr = request.getParameter("nuevaTienda");

        // Validar que el parámetro idCompra no sea nulo
        if (idCompraStr != null && !idCompraStr.isEmpty()) {
            try {
                int idCompra = Integer.parseInt(idCompraStr);

                // Obtener una conexión desde el pool
                try (Connection conn = pool.getConnection()) {
                    StringBuilder queryBuilder = new StringBuilder("UPDATE compras SET ");
                    boolean hasUpdates = false;

                    // Construir la consulta dinámicamente según los campos proporcionados en la solicitud
                    if (nuevaFecha != null && !nuevaFecha.isEmpty()) {
                        queryBuilder.append("fechaCompra = ?, ");
                        hasUpdates = true;
                    }
                    if (nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
                        queryBuilder.append("importeCompra = ?, ");
                        hasUpdates = true;
                    }
                    // Agregar la actualización de la tienda si se proporciona
                    if (nuevaTiendaIdStr != null && !nuevaTiendaIdStr.isEmpty()) {
                        queryBuilder.append("idTiendaFK = ?, ");
                        hasUpdates = true;
                    }

                    // Eliminar la coma y el espacio al final de la consulta
                    if (hasUpdates) {
                        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
                        queryBuilder.append(" WHERE idCompra = ?");
                        String query = queryBuilder.toString();

                        // Crear la declaración preparada
                        try (PreparedStatement statement = conn.prepareStatement(query)) {
                            int parameterIndex = 1;

                            // Establecer los parámetros según los campos proporcionados en la solicitud
                            if (nuevaFecha != null && !nuevaFecha.isEmpty()) {
                                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Date fecha = inputFormat.parse(nuevaFecha);
                                statement.setDate(parameterIndex++, new java.sql.Date(fecha.getTime()));
                            }
                            if (nuevoImporteStr != null && !nuevoImporteStr.isEmpty()) {
                                BigDecimal nuevoImporte = new BigDecimal(nuevoImporteStr);
                                statement.setBigDecimal(parameterIndex++, nuevoImporte);
                            }
                            // Establecer el parámetro nuevaTiendaId si se proporciona
                            if (nuevaTiendaIdStr != null && !nuevaTiendaIdStr.isEmpty()) {
                                int nuevaTiendaId = Integer.parseInt(nuevaTiendaIdStr);
                                statement.setInt(parameterIndex++, nuevaTiendaId);
                            }

                            // Establecer el parámetro idCompra
                            statement.setInt(parameterIndex, idCompra);

                            // Ejecutar la actualización
                            statement.executeUpdate();
                        }
                    }
                }

                // Establecer un atributo en la solicitud para indicar que se ha editado correctamente
                request.setAttribute("edicionExitosa", true);

                // Redirigir de vuelta a la página de edición para mostrar el mensaje de confirmación
                request.getRequestDispatcher("/EditarCompra.jsp").forward(request, response);
            } catch (NumberFormatException | SQLException | ParseException e) {
                // Manejar cualquier excepción que ocurra durante el proceso de edición
                throw new ServletException("Error al procesar la edición de la compra", e);
            }
        } else {
            // Si el parámetro idCompra es nulo o vacío, mostrar un mensaje de error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro idCompra incorrecto o faltante");
        }
    }
}
