package proyectoFinalApi.proyectoFinalApi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConexion {
    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost:1045/proyectoFinal"; 
            String usuario = "postgres";
            String contrasenia = "sevillista04";
            return DriverManager.getConnection(url, usuario, contrasenia);
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar a la bbdd.", e);
        }
    }
}
