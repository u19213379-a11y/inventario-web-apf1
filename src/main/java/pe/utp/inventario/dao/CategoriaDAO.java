package pe.utp.inventario.dao;

import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public List<Categoria> listar() throws SQLException {
        List<Categoria> datos=new ArrayList<>();
        String sql="SELECT id_categoria,nombre,descripcion FROM categorias ORDER BY nombre";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()) {
            while(rs.next()){
                Categoria item=new Categoria();
                item.setIdCategoria(rs.getInt("id_categoria"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                datos.add(item);
            }
        }
        return datos;
    }

    public void registrar(Categoria categoria) throws SQLException {
        if(categoria.getNombre()==null || categoria.getNombre().isBlank()) throw new IllegalArgumentException("Nombre requerido");
        String sql="INSERT INTO categorias(nombre,descripcion) VALUES (?,?)";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,categoria.getNombre().trim());
            ps.setString(2,categoria.getDescripcion());
            ps.executeUpdate();
        }
    }
}
