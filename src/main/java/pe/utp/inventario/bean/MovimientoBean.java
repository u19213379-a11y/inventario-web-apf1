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
import pe.utp.inventario.dao.MovimientoDAO;
import pe.utp.inventario.dao.ProductoDAO;
import pe.utp.inventario.model.Movimiento;
import pe.utp.inventario.model.Producto;

@ManagedBean(name="movimientoBean")
@ViewScoped
public class MovimientoBean implements Serializable {
    private static final long serialVersionUID=1L;
    private final MovimientoDAO dao=new MovimientoDAO();
    private List<Producto> productos=new ArrayList<>();
    private List<Movimiento> recientes=new ArrayList<>();
    private int productoId;
    private int cantidad=1;
    private String tipo="ENTRADA";
    private String observacion="";

    @PostConstruct public void iniciar(){refrescar();}
    private void refrescar(){
        try{
            productos=new ProductoDAO().listar("",0,1000);
            recientes=dao.listarRecientes(12);
        }catch(SQLException ex){mensaje(FacesMessage.SEVERITY_ERROR,"No se pudo leer el inventario.");}
    }
    public void registrar(){
        SesionBean sesion=FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(
            FacesContext.getCurrentInstance(),"#{sesionBean}",SesionBean.class);
        if(!sesion.isAutenticado()){mensaje(FacesMessage.SEVERITY_ERROR,"La sesión expiró.");return;}
        try{
            dao.registrarMovimiento(productoId,sesion.getUsuario().getIdUsuario(),tipo,cantidad,observacion);
            cantidad=1;observacion="";refrescar();
            mensaje(FacesMessage.SEVERITY_INFO,"Movimiento registrado. El stock quedó actualizado.");
        }catch(IllegalArgumentException ex){mensaje(FacesMessage.SEVERITY_ERROR,ex.getMessage());}
        catch(SQLException ex){mensaje(FacesMessage.SEVERITY_ERROR,"No fue posible registrar el movimiento en MySQL.");}
    }
    private void mensaje(FacesMessage.Severity sev,String txt){FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(sev,txt,null));}
    public List<Producto> getProductos(){return productos;}
    public List<Movimiento> getRecientes(){return recientes;}
    public int getProductoId(){return productoId;}
    public void setProductoId(int v){productoId=v;}
    public int getCantidad(){return cantidad;}
    public void setCantidad(int v){cantidad=v;}
    public String getTipo(){return tipo;}
    public void setTipo(String v){tipo=v;}
    public String getObservacion(){return observacion;}
    public void setObservacion(String v){observacion=v;}
}
