package pe.utp.inventario.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Compatibilidad con enlaces del APF1. La lógica vigente está en JSF/Facelets. */
@WebServlet("/reporte-stock")
public class ReporteStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/reporte-stock.xhtml");
    }
    // HttpServlet mantiene HTTP 405 para POST antiguo: las operaciones se hacen por JSF.
}
