package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class ParametroControlador {
    
   private final Logger log = Logger.getLogger(ParametroControlador.class);
   private URL url = ParametroControlador.class.getResource("Log4j.properties");
    
   //Constructor
   public ParametroControlador() {}
      
    //Metodo encargado de hacer updae para cadaunode los parámetros del sistema
    public void actualizarParámetro(String nombreParámetro, String valor){
     
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update parametros set Valor ='"+valor+"' where Nombre_parametro ='"+nombreParámetro+"'");

            pst.executeUpdate();
            cn.close(); 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al modificar uno de los parámetros adicionales del sistema, Parámetro: " + nombreParámetro + " Error generado: "+ e);
        }
    }
    
    //Metodo que permite consultar el valor de un parametro para cargarlo en la interfaz
    public String consultarValorDeUnParametro(String nombreDeParametro){
       
        String valor = "";
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Valor from parametros where Nombre_parametro = '" + nombreDeParametro + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                valor = rs.getString("Valor");
                cn.close();
           
            } else {
                log.fatal("ERROR - No se ha encontrado el valor del parametro " + nombreDeParametro);
            }
        }catch (SQLException ex){ 
            log.fatal("ERROR - Se ha producido un error al consultar el valor del parametro " + nombreDeParametro + " en el sistema: " + ex); 
        } 
        return valor;
    }
    
    //Metodo que se encarga de generar los consecutivos para las facturas, cierres y arqueos de caja
    public String generarConsecutivo(int tamaño) {
  
        // Puede personalizar los personajes que desea agregar a las cadenas al azar
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = NUMBER;
        SecureRandom random = new SecureRandom();

        if (tamaño < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(tamaño);

        for (int i = 0; i < tamaño; i++) {
            // 0-62 (exclusive), retornos aleatorios 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}

