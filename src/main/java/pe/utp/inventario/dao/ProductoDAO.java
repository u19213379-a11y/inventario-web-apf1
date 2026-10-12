package pe.utp.inventario.dao;

import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Toda lectura de inventario se ejecuta vía JDBC y PreparedStatement. */
public class ProductoDAO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private static final String BASE="SELECT p.id_producto,p.id_categoria,c.nombre AS categoria_nombre,"
        +"p.nombre,p.descripcion,p.unidad_medida,p.stock_actual,p.stock_minimo,p.estado "
        +"FROM productos p INNER JOIN categorias c ON c.id_categoria=p.id_categoria WHERE p.estado=1 ";

    private Producto leer(ResultSet rs) throws SQLException {
        Producto p=new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setIdCategoria(rs.getInt("id_categoria"));
        p.setCategoriaNombre(rs.getString("categoria_nombre"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setUnidadMedida(rs.getString("unidad_medida"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setActivo(rs.getBoolean("estado"));
        return p;
    }

    public List<Producto> listar(String filtro, int pagina, int tamano) throws SQLException {
        int offset=Math.max(0,pagina)*tamano;
        String sql=BASE+" AND (p.nombre LIKE ? OR c.nombre LIKE ?) ORDER BY p.id_producto DESC LIMIT ? OFFSET ?";
        List<Producto> lista=new ArrayList<>();
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql)){
            String patron="%"+(filtro==null?"":filtro.trim())+"%";
            ps.setString(1,patron);ps.setString(2,patron);ps.setInt(3,tamano);ps.setInt(4,offset);
            try(ResultSet rs=ps.executeQuery()){while(rs.next())lista.add(leer(rs));}
        }
        return lista;
    }

    public int contar(String filtro) throws SQLException {
        String sql="SELECT COUNT(*) FROM productos p INNER JOIN categorias c ON c.id_categoria=p.id_categoria "
                  +"WHERE p.estado=1 AND (p.nombre LIKE ? OR c.nombre LIKE ?)";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql)){
            String patron="%"+(filtro==null?"":filtro.trim())+"%";
            ps.setString(1,patron);ps.setString(2,patron);
            try(ResultSet rs=ps.executeQuery()){rs.next();return rs.getInt(1);}
        }
    }

    public int contarBajoStock() throws SQLException {
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(
            "SELECT COUNT(*) FROM productos WHERE estado=1 AND stock_actual<=stock_minimo");
            ResultSet rs=ps.executeQuery()){rs.next();return rs.getInt(1);}
    }

    public long totalUnidades() throws SQLException {
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(
            "SELECT COALESCE(SUM(stock_actual),0) FROM productos WHERE estado=1");
            ResultSet rs=ps.executeQuery()){rs.next();return rs.getLong(1);}
    }

    public List<Producto> listarBajoStock() throws SQLException {
        List<Producto> lista=new ArrayList<>();
        String sql=BASE+" AND p.stock_actual<=p.stock_minimo ORDER BY p.stock_actual ASC,p.nombre ASC";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){while(rs.next())lista.add(leer(rs));}
        return lista;
    }

    public void registrar(Producto p) throws SQLException {
        if(p.getIdCategoria()<=0 || p.getNombre()==null || p.getNombre().isBlank()
                || p.getStockMinimo()<0) throw new IllegalArgumentException("Datos de producto inválidos");
        String sql="INSERT INTO productos(id_categoria,nombre,descripcion,unidad_medida,stock_actual,stock_minimo,estado)"
                  +" VALUES (?,?,?,?,0,?,1)";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,p.getIdCategoria());ps.setString(2,p.getNombre().trim());
            ps.setString(3,p.getDescripcion());ps.setString(4,p.getUnidadMedida());
            ps.setInt(5,p.getStockMinimo());ps.executeUpdate();
        }
    }
}
