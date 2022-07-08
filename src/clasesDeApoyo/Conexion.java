/*
 Clase que define la conexion a la base de datos
 */
package clasesDeApoyo;

import java.sql.*;

public class Conexion {
    //Conexión Local
    public static Connection conectar(){
        try {
            String host = "localhost";
            String port = "3306";
            String db = "bd_sistemaparking";
            String user = "root";
            String pass = "";
            String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password=" + pass + "&useSSL=false";
            Connection cn = DriverManager.getConnection(url);
            return cn;
        } catch (SQLException e) {
            System.out.println("Error en conexión local " + e);
        }
        return (null);
    }
}
