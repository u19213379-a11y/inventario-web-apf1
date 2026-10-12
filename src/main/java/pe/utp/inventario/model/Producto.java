package pe.utp.inventario.model;
import java.io.Serializable;
public class Producto implements Serializable {
    private static final long serialVersionUID=1L;
    private int idProducto;
    private int idCategoria;
    private String categoriaNombre;
    private String nombre;
    private String descripcion;
    private String unidadMedida="unidad";
    private int stockActual;
    private int stockMinimo=5;
    private boolean activo=true;
    public int getIdProducto(){return idProducto;}
    public void setIdProducto(int v){idProducto=v;}
    public int getIdCategoria(){return idCategoria;}
    public void setIdCategoria(int v){idCategoria=v;}
    public String getCategoriaNombre(){return categoriaNombre;}
    public void setCategoriaNombre(String v){categoriaNombre=v;}
    public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;}
    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String v){descripcion=v;}
    public String getUnidadMedida(){return unidadMedida;}
    public void setUnidadMedida(String v){unidadMedida=v;}
    public int getStockActual(){return stockActual;}
    public void setStockActual(int v){stockActual=v;}
    public int getStockMinimo(){return stockMinimo;}
    public void setStockMinimo(int v){stockMinimo=v;}
    public boolean isActivo(){return activo;}
    public void setActivo(boolean v){activo=v;}
    public boolean isBajo(){return stockActual<=stockMinimo;}
}
