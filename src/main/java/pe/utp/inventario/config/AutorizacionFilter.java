package pe.utp.inventario.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.utp.inventario.bean.SesionBean;

/** Evita acceso sin login a vistas privadas, independientemente de la navegación JSF. */
@WebFilter(urlPatterns={"/dashboard.xhtml","/productos.xhtml","/movimientos.xhtml",
                       "/reporte-stock.xhtml","/categorias.xhtml"})
public class AutorizacionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        HttpSession session=req.getSession(false);
        SesionBean bean=session==null?null:(SesionBean)session.getAttribute("sesionBean");
        if(bean==null || !bean.isAutenticado()){
            res.sendRedirect(req.getContextPath()+"/login.xhtml");
            return;
        }
        if(req.getServletPath().equals("/categorias.xhtml") && !bean.isAdmin()){
            res.sendRedirect(req.getContextPath()+"/dashboard.xhtml");
            return;
        }
        chain.doFilter(request,response);
    }
}
