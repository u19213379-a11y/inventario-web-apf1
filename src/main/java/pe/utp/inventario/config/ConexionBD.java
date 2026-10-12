package pe.utp.inventario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** JDBC centralizado. Variables de entorno: DB_URL, DB_USER y DB_PASSWORD. */
public final class ConexionBD {
    private static final String URL_POR_DEFECTO =
        "jdbc:mysql://localhost:3306/inventario_apf1?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima";

    private ConexionBD() { }

    private static String valor(String nombre, String defecto) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? defecto : valor;
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(
            valor("DB_URL", URL_POR_DEFECTO),
            valor("DB_USER", "root"),
            valor("DB_PASSWORD", "")
        );
    }

    public static boolean probarConexion() {
        try (Connection connection = obtenerConexion()) {
            return connection.isValid(3);
        } catch (SQLException ex) {
            return false;
        }
    }
}
