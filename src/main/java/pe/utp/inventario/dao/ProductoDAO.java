package pe.utp.inventario.dao;

import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() throws SQLException {
        String sql = "SELECT p.id_producto, p.id_categoria, c.nombre AS categoria, p.nombre, p.descripcion, " +
                "p.unidad_medida, p.stock_actual, p.stock_minimo " +
                "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria " +
                "WHERE p.estado = 1 ORDER BY p.nombre";
        List<Producto> productos = new ArrayList<>();

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setUnidadMedida(rs.getString("unidad_medida"));
                producto.setStockActual(rs.getInt("stock_actual"));
                producto.setStockMinimo(rs.getInt("stock_minimo"));
                productos.add(producto);
            }
        }
        return productos;
    }

    public List<Producto> listarStockBajo() throws SQLException {
        String sql = "SELECT p.id_producto, p.id_categoria, c.nombre AS categoria, p.nombre, p.descripcion, " +
                "p.unidad_medida, p.stock_actual, p.stock_minimo " +
                "FROM productos p INNER JOIN categorias c ON p.id_categoria = c.id_categoria " +
                "WHERE p.estado = 1 AND p.stock_actual <= p.stock_minimo ORDER BY p.stock_actual ASC";
        List<Producto> productos = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setUnidadMedida(rs.getString("unidad_medida"));
                producto.setStockActual(rs.getInt("stock_actual"));
                producto.setStockMinimo(rs.getInt("stock_minimo"));
                productos.add(producto);
            }
        }
        return productos;
    }

    public void registrarMovimiento(int idProducto, int idUsuario, String tipo, int cantidad, String observacion) throws SQLException {
        String insertMovimiento = "INSERT INTO movimientos (id_producto, id_usuario, tipo, cantidad, observacion) VALUES (?, ?, ?, ?, ?)";
        String updateEntrada = "UPDATE productos SET stock_actual = stock_actual + ? WHERE id_producto = ?";
        String updateSalida = "UPDATE productos SET stock_actual = stock_actual - ? WHERE id_producto = ? AND stock_actual >= ?";

        try (Connection cn = ConexionBD.obtenerConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psMov = cn.prepareStatement(insertMovimiento);
                 PreparedStatement psStock = cn.prepareStatement("ENTRADA".equalsIgnoreCase(tipo) ? updateEntrada : updateSalida)) {

                psMov.setInt(1, idProducto);
                psMov.setInt(2, idUsuario);
                psMov.setString(3, tipo.toUpperCase());
                psMov.setInt(4, cantidad);
                psMov.setString(5, observacion);
                psMov.executeUpdate();

                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                if (!"ENTRADA".equalsIgnoreCase(tipo)) {
                    psStock.setInt(3, cantidad);
                }
                int filas = psStock.executeUpdate();
                if (filas == 0) {
                    cn.rollback();
                    throw new SQLException("No se pudo actualizar el stock. Verifique la cantidad disponible.");
                }
                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                throw e;
            }
        }
    }
}
