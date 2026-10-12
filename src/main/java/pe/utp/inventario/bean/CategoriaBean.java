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
import pe.utp.inventario.dao.CategoriaDAO;
import pe.utp.inventario.model.Categoria;

@ManagedBean(name="categoriaBean")
@ViewScoped
public class CategoriaBean implements Serializable {
    private static final long serialVersionUID=1L;
    private final CategoriaDAO dao=new CategoriaDAO();
    private List<Categoria> categorias=new ArrayList<>();
    private Categoria nueva=new Categoria();
    @PostConstruct public void iniciar(){refrescar();}
    public void registrar(){
        SesionBean sesion=FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(
            FacesContext.getCurrentInstance(),"#{sesionBean}",SesionBean.class);
        if(!sesion.isAdmin()){mensaje(FacesMessage.SEVERITY_ERROR,"Solo el administrador puede registrar categorías.");return;}
        try {dao.registrar(nueva);nueva=new Categoria();refrescar();mensaje(FacesMessage.SEVERITY_INFO,"Categoría registrada.");}
        catch(SQLException|IllegalArgumentException ex){mensaje(FacesMessage.SEVERITY_ERROR,"No fue posible registrar la categoría.");}
    }
    private void refrescar(){try{categorias=dao.listar();}catch(SQLException ex){mensaje(FacesMessage.SEVERITY_ERROR,"Sin conexión a categorías.");}}
    private void mensaje(FacesMessage.Severity sev,String texto){FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(sev,texto,null));}
    public List<Categoria> getCategorias(){return categorias;}
    public Categoria getNueva(){return nueva;}
    public void setNueva(Categoria v){nueva=v;}
}
