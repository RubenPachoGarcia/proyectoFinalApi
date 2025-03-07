package proyectoFinalApi.proyectoFinalApi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Clase que permite la conexion con la base de datos y poder operar con ella*/

public class DBConexion {
    public static Connection getConnection() throws SQLException {
        try {
            String urlDB = "jdbc:postgresql://localhost:1045/proyectoFinal"; 
            String usuarioDB = "postgres";
            String contraseniaDB = "sevillista04";
            return DriverManager.getConnection(urlDB, usuarioDB, contraseniaDB);
        } catch (SQLException e) {
            throw new SQLException("No se pudo conectar a la bbdd.", e);
        }
    }
}
