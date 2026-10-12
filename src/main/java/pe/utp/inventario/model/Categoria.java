package pe.utp.inventario.model;
import java.io.Serializable;
public class Categoria implements Serializable {
    private static final long serialVersionUID=1L;
    private int idCategoria;
    private String nombre;
    private String descripcion;
    public int getIdCategoria(){return idCategoria;}
    public void setIdCategoria(int v){idCategoria=v;}
    public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;}
    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String v){descripcion=v;}
}
