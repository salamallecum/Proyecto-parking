package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class FacturaControlador {
    
    private final Logger log = Logger.getLogger(FacturaControlador.class);
    private URL url = FacturaControlador.class.getResource("Log4j.properties");
       
    //Constructor
    public FacturaControlador() {}
    
    //Metodo que permite actualizar las facturas que se encuentren abiertas con la información actualizada de un vehiculo    
    public void actualizarFacturas(String placa, String dueño, String tipVehi, int IdParq, int IdConv, int IdTarif){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+placa+"', Propietario='"+dueño+"', Tipo_vehiculo='"+tipVehi+"', No_parqueadero='"+IdParq+"', Id_convenio='"+IdConv+"', Id_tarifa='"+IdTarif+"' where Placa ='"+placa+"' and Estado_fctra='Abierta'");

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar facturas, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al actualizar las facturas con la información actualizadaz de un vehiculo en el sistema: " + e); 
        } 
    }
    
}
