package pe.utp.inventario.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import pe.utp.inventario.dao.UsuarioDAO;
import pe.utp.inventario.model.Usuario;

@ManagedBean(name="sesionBean")
@SessionScoped
public class SesionBean implements Serializable {
    private static final long serialVersionUID=1L;
    private String correo;
    private String clave;
    private Usuario usuario;

    public String iniciarSesion() {
        try {
            Usuario autenticado=new UsuarioDAO().autenticar(correo,clave);
            clave=null;
            if(autenticado==null){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Credenciales incorrectas","Revisa el correo y la contraseña."));
                return null;
            }
            usuario=autenticado;
            return "/dashboard?faces-redirect=true";
        }catch(SQLException ex){
            clave=null;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo conectar a MySQL","Verifica el servidor y DB_PASSWORD."));
            return null;
        }
    }

    public String cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    /** Se llama desde f:event preRenderView de todas las páginas privadas. */
    public void proteger() throws IOException {
        if(usuario==null){
            FacesContext ctx=FacesContext.getCurrentInstance();
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/login.xhtml");
            ctx.responseComplete();
        }
    }

    public void protegerAdmin() throws IOException {
        if(usuario==null){proteger();return;}
        if(!isAdmin()){
            FacesContext ctx=FacesContext.getCurrentInstance();
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+"/dashboard.xhtml");
            ctx.responseComplete();
        }
    }

    public void redirigirInicio() throws IOException {
        FacesContext ctx=FacesContext.getCurrentInstance();
        ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath()+
            (usuario==null?"/login.xhtml":"/dashboard.xhtml"));
        ctx.responseComplete();
    }
    public boolean isAutenticado(){return usuario!=null;}
    public boolean isAdmin(){return usuario!=null && "ADMIN".equals(usuario.getRol());}
    public Usuario getUsuario(){return usuario;}
    public String getCorreo(){return correo;}
    public void setCorreo(String correo){this.correo=correo;}
    public String getClave(){return clave;}
    public void setClave(String clave){this.clave=clave;}
}
