package pe.utp.inventario.dao;

import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.config.ClaveUtil;
import pe.utp.inventario.model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    public Usuario autenticar(String correo, String clave) throws SQLException {
        final String sql="SELECT id_usuario,nombres,correo,clave,rol FROM usuarios WHERE correo=? AND estado=1";
        try (Connection con=ConexionBD.obtenerConexion(); PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, correo==null?"":correo.trim());
            try (ResultSet rs=ps.executeQuery()) {
                if (!rs.next() || !ClaveUtil.verificar(clave,rs.getString("clave"))) return null;
                Usuario usuario=new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
                return usuario;
            }
        }
    }
}
