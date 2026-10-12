package pe.utp.inventario.model;
import java.io.Serializable;
public class Usuario implements Serializable {
    private static final long serialVersionUID=1L;
    private int idUsuario;
    private String nombres;
    private String correo;
    private String rol;
    public int getIdUsuario(){return idUsuario;}
    public void setIdUsuario(int v){idUsuario=v;}
    public String getNombres(){return nombres;}
    public void setNombres(String v){nombres=v;}
    public String getCorreo(){return correo;}
    public void setCorreo(String v){correo=v;}
    public String getRol(){return rol;}
    public void setRol(String v){rol=v;}
}
