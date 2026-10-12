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
import pe.utp.inventario.dao.ProductoDAO;
import pe.utp.inventario.model.Categoria;
import pe.utp.inventario.model.Producto;

@ManagedBean(name="productoBean")
@ViewScoped
public class ProductoBean implements Serializable {
    private static final long serialVersionUID=1L;
    private final ProductoDAO dao=new ProductoDAO();
    private List<Producto> productos=new ArrayList<>();
    private List<Categoria> categorias=new ArrayList<>();
    private Producto nuevo=new Producto();
    private String filtro="";
    private int pagina=0;
    private int cantidadTotal;
    private final int tamano=8;

    @PostConstruct
    public void inicializar(){
        try {
            categorias=new CategoriaDAO().listar();
            actualizarTabla();
        }catch(SQLException ex){mensaje(FacesMessage.SEVERITY_ERROR,"No se pudieron cargar los registros de MySQL.");}
    }
    private void actualizarTabla() throws SQLException {
        cantidadTotal=dao.contar(filtro);
        int maxPagina=Math.max(0,(cantidadTotal-1)/tamano);
        pagina=Math.min(Math.max(0,pagina),maxPagina);
        productos=dao.listar(filtro,pagina,tamano);
    }
    public void buscar(){pagina=0;refrescar();}
    public void siguiente(){if((pagina+1)*tamano<cantidadTotal)pagina++;refrescar();}
    public void anterior(){if(pagina>0)pagina--;refrescar();}
    private void refrescar(){
        try{actualizarTabla();}
        catch(SQLException ex){mensaje(FacesMessage.SEVERITY_ERROR,"Error consultando productos.");}
    }
    public void registrar(){
        try{
            dao.registrar(nuevo);
            nuevo=new Producto();pagina=0;actualizarTabla();
            mensaje(FacesMessage.SEVERITY_INFO,"Producto guardado correctamente. Stock inicial: 0; registra una entrada.");
        }catch(SQLException|IllegalArgumentException ex){
            mensaje(FacesMessage.SEVERITY_ERROR,"No se guardó el producto: "+ex.getMessage());
        }
    }
    private void mensaje(FacesMessage.Severity severity,String texto){
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(severity,texto,null));
    }
    public List<Producto> getProductos(){return productos;}
    public List<Categoria> getCategorias(){return categorias;}
    public Producto getNuevo(){return nuevo;}
    public void setNuevo(Producto p){nuevo=p;}
    public String getFiltro(){return filtro;}
    public void setFiltro(String v){filtro=v;}
    public int getPagina(){return pagina+1;}
    public int getTotalPaginas(){return Math.max(1,(cantidadTotal+tamano-1)/tamano);}
    public int getCantidadTotal(){return cantidadTotal;}
    public boolean isHayAnterior(){return pagina>0;}
    public boolean isHaySiguiente(){return (pagina+1)*tamano<cantidadTotal;}
}
