package pe.utp.inventario.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Compatibilidad con enlaces del APF1. La lógica vigente está en JSF/Facelets. */
@WebServlet("/movimientos")
public class MovimientoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/movimientos.xhtml");
    }
    // HttpServlet mantiene HTTP 405 para POST antiguo: las operaciones se hacen por JSF.
}
