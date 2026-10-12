package pe.utp.inventario.model;
import java.io.Serializable;
import java.sql.Timestamp;
public class Movimiento implements Serializable {
    private static final long serialVersionUID=1L;
    private int idMovimiento;
    private int idProducto;
    private String productoNombre;
    private String usuarioNombre;
    private String tipo;
    private int cantidad;
    private Timestamp fechaRegistro;
    private String observacion;
    public int getIdMovimiento(){return idMovimiento;}
    public void setIdMovimiento(int v){idMovimiento=v;}
    public int getIdProducto(){return idProducto;}
    public void setIdProducto(int v){idProducto=v;}
    public String getProductoNombre(){return productoNombre;}
    public void setProductoNombre(String v){productoNombre=v;}
    public String getUsuarioNombre(){return usuarioNombre;}
    public void setUsuarioNombre(String v){usuarioNombre=v;}
    public String getTipo(){return tipo;}
    public void setTipo(String v){tipo=v;}
    public int getCantidad(){return cantidad;}
    public void setCantidad(int v){cantidad=v;}
    public Timestamp getFechaRegistro(){return fechaRegistro;}
    public void setFechaRegistro(Timestamp v){fechaRegistro=v;}
    public String getObservacion(){return observacion;}
    public void setObservacion(String v){observacion=v;}
}
