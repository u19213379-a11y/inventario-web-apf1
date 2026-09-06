package pe.utp.inventario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/inventario_apf1?useSSL=false&serverTimezone=America/Lima";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver MySQL Connector/J", e);
        }
    }

    public static boolean probarConexion() {
        try (Connection connection = obtenerConexion()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}
