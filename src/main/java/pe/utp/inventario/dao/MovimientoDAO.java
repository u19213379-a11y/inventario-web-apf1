package pe.utp.inventario.dao;

import pe.utp.inventario.config.ConexionBD;
import pe.utp.inventario.model.Movimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Ajuste de stock e historial en una única transacción (ACID). */
public class MovimientoDAO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public void registrarMovimiento(int idProducto,int idUsuario,String tipo,int cantidad,String observacion)
           throws SQLException {
        if(idProducto<=0 || idUsuario<=0 || cantidad<=0 ||
                !("ENTRADA".equals(tipo)||"SALIDA".equals(tipo))) {
            throw new IllegalArgumentException("Producto, usuario, tipo o cantidad no válidos");
        }
        try(Connection con=ConexionBD.obtenerConexion()){
            con.setAutoCommit(false);
            try {
                int actual;
                try(PreparedStatement ps=con.prepareStatement(
                    "SELECT stock_actual FROM productos WHERE id_producto=? AND estado=1 FOR UPDATE")) {
                    ps.setInt(1,idProducto);
                    try(ResultSet rs=ps.executeQuery()){
                        if(!rs.next()) throw new IllegalArgumentException("Producto inactivo o inexistente");
                        actual=rs.getInt(1);
                    }
                }
                long siguiente="ENTRADA".equals(tipo)?(long)actual+cantidad:(long)actual-cantidad;
                if(siguiente<0) throw new IllegalArgumentException("Stock insuficiente para registrar la salida");
                if(siguiente>Integer.MAX_VALUE) throw new IllegalArgumentException("El stock supera el máximo permitido");
                try(PreparedStatement ps=con.prepareStatement(
                    "INSERT INTO movimientos(id_producto,id_usuario,tipo,cantidad,observacion) VALUES (?,?,?,?,?)")){
                    ps.setInt(1,idProducto);ps.setInt(2,idUsuario);ps.setString(3,tipo);
                    ps.setInt(4,cantidad);ps.setString(5,observacion);ps.executeUpdate();
                }
                try(PreparedStatement ps=con.prepareStatement(
                    "UPDATE productos SET stock_actual=? WHERE id_producto=?")){
                    ps.setInt(1,(int)siguiente);ps.setInt(2,idProducto);
                    if(ps.executeUpdate()!=1) throw new SQLException("No se actualizó el producto");
                }
                con.commit();
            }catch(SQLException|RuntimeException ex){
                try {con.rollback();}catch(SQLException rollback){ex.addSuppressed(rollback);}
                throw ex;
            }finally{con.setAutoCommit(true);}
        }
    }

    public List<Movimiento> listarRecientes(int cantidadMaxima) throws SQLException {
        List<Movimiento> lista=new ArrayList<>();
        String sql="SELECT m.id_movimiento,m.id_producto,p.nombre AS producto_nombre,"
             +"u.nombres AS usuario_nombre,m.tipo,m.cantidad,m.fecha_registro,m.observacion "
             +"FROM movimientos m INNER JOIN productos p ON m.id_producto=p.id_producto "
             +"INNER JOIN usuarios u ON m.id_usuario=u.id_usuario "
             +"ORDER BY m.fecha_registro DESC,m.id_movimiento DESC LIMIT ?";
        try(Connection con=ConexionBD.obtenerConexion();PreparedStatement ps=con.prepareStatement(sql)){
            ps.setInt(1,Math.min(Math.max(1,cantidadMaxima),100));
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    Movimiento item=new Movimiento();
                    item.setIdMovimiento(rs.getInt("id_movimiento"));
                    item.setIdProducto(rs.getInt("id_producto"));
                    item.setProductoNombre(rs.getString("producto_nombre"));
                    item.setUsuarioNombre(rs.getString("usuario_nombre"));
                    item.setTipo(rs.getString("tipo"));
                    item.setCantidad(rs.getInt("cantidad"));
                    item.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    item.setObservacion(rs.getString("observacion"));
                    lista.add(item);
                }
            }
        }
        return lista;
    }
}
