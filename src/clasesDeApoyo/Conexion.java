/*
 Clase que define la conexion a la base de datos
 */
package clasesDeApoyo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import org.apache.log4j.Logger;

public class Conexion {
    
   private static final Logger log = Logger.getLogger(Conexion.class);
   private URL url = Conexion.class.getResource("Log4j.properties");

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
            log.fatal("ERROR - Se ha producido un error en la conexion local: " + e);
        }
        return (null);
    }
    
    //Metodo que se encarga de probar si la conexion a la base de datos funciona
    public static Connection TestDeConexion(){
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
            return null;
        }   
    }
    
    //Metodo que se encarga de crear la base de datos con la que tendra comunicacion el sistema
    public static void crearBaseDeDatos(){
        com.mysql.jdbc.Connection con = null;
        String sURL = "jdbc:mysql://localhost:3306";
        try {
            con = (com.mysql.jdbc.Connection) DriverManager.getConnection(sURL,"root","");
            com.mysql.jdbc.Statement stmt = (com.mysql.jdbc.Statement) con.createStatement();
            stmt.execute("create database bd_sistemaparking");
            log.info("Base de datos creada correctamente");
        } catch (SQLException e) { 
            log.fatal("Error en la conexión:" + e.toString());
        } finally {
            try {
              // Cerramos posibles conexiones abiertas
              if (con!=null) con.close();    
            } catch (Exception e) {
              log.fatal("Error cerrando conexiones: " + e.toString());
            } 
        }
    }
    
    //Metodo que se encarga de cargar el script con la estructura de tablas de la base de datos del sistema
    public static void cargarScriptDeEstructura(){
        
        try {
            Process p = Runtime.getRuntime().exec("mysql -u root bd_sistemaparking");
            OutputStream os = p.getOutputStream();
            FileInputStream fis = new FileInputStream("BaseDeDatos/bd_sistemaparking.sql");
            byte[] buffer = new byte[1000];
            
            int leido = fis.read(buffer);
            
            while(leido > 0){
                os.write(buffer, 0, leido);
                leido = fis.read(buffer);
                
            }
            os.flush();
            os.close();
            fis.close();            
            
        } catch (IOException ex) {
            log.fatal("Se ha producido un error al intentar cargar el script.sql de estructura de la base de datos: " + ex);
        }
    }
}
