package pe.utp.inventario.servlet;

import pe.utp.inventario.dao.ProductoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/movimientos")
public class MovimientoServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("productos", productoDAO.listar());
            request.getRequestDispatcher("/movimientos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            String tipo = request.getParameter("tipo");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String observacion = request.getParameter("observacion");
            int idUsuario = 1;

            productoDAO.registrarMovimiento(idProducto, idUsuario, tipo, cantidad, observacion);
            response.sendRedirect(request.getContextPath() + "/productos?mensaje=Movimiento registrado correctamente");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        }
    }
}
