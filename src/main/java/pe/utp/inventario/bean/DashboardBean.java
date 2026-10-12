package pe.utp.inventario.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.dao.MovimientoDAO;
import pe.utp.inventario.dao.ProductoDAO;
import pe.utp.inventario.model.Movimiento;

@ManagedBean(name="dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {
    private static final long serialVersionUID=1L;
    private int productos;
    private int alertas;
    private long unidades;
    private boolean conexionActiva;
    private List<Movimiento> recientes=new ArrayList<>();
    @PostConstruct public void cargar(){
        try{
            ProductoDAO p=new ProductoDAO();
            productos=p.contar("");alertas=p.contarBajoStock();unidades=p.totalUnidades();
            recientes=new MovimientoDAO().listarRecientes(5);
            conexionActiva=true;
        }catch(SQLException ex){conexionActiva=false;}
    }
    public int getProductos(){return productos;}
    public int getAlertas(){return alertas;}
    public long getUnidades(){return unidades;}
    public boolean isConexionActiva(){return conexionActiva;}
    public List<Movimiento> getRecientes(){return recientes;}
}
