package pe.utp.inventario.bean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import pe.utp.inventario.dao.ProductoDAO;
import pe.utp.inventario.model.Producto;

@ManagedBean(name="reporteBean")
@ViewScoped
public class ReporteBean implements Serializable {
    private static final long serialVersionUID=1L;
    private List<Producto> bajos=new ArrayList<>();
    @PostConstruct public void cargar(){
        try{bajos=new ProductoDAO().listarBajoStock();}
        catch(SQLException ex){FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo consultar el reporte de stock.",null));}
    }
    public List<Producto> getBajos(){return bajos;}
    public int getTotal(){return bajos.size();}
}
