package pe.utp.inventario.servlet;

import pe.utp.inventario.config.ConexionBD;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("conexionActiva", ConexionBD.probarConexion());
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
