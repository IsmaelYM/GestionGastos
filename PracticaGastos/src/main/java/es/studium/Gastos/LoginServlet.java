package es.studium.Gastos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource pool;

    public LoginServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            pool = (DataSource) ctx.lookup("java:comp/env/jdbc/gastos");
            if (pool == null) {
                throw new ServletException("DataSource desconocida 'gastos'");
            }
        } catch (NamingException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");

            if (usuario == null || usuario.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("error", "Debes introducir tu usuario y contraseña");
                request.getRequestDispatcher("/Login.jsp").forward(request, response);
                return;
            }

            try (Connection conn = pool.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nombreUsuario = ? AND claveUsuario = SHA2(?,256)")) {
                stmt.setString(1, usuario);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    request.setAttribute("error", "Nombre de usuario o contraseña incorrectos");
                    request.getRequestDispatcher("/Login.jsp").forward(request, response);
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("usuario", usuario);
                    response.sendRedirect("PrincipalServlet");
                }
            } catch (SQLException ex) {
                throw new ServletException("Error al procesar la solicitud", ex);
            }
        }

}