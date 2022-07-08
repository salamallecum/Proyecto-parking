package modelo;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;


/**
 *
 * @author ALEJO
 */
public class Convenio{ 
    
    private int id;
    private String nombre;
    private String monto;
    private String frecuencia;
    
    private final Logger log = Logger.getLogger(Convenio.class);
    private URL url = Convenio.class.getResource("Log4j.properties");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }  
    
    //Metodos
    public String toString(){
        return this.nombre;
    }
    
    //Agrega los valores de la tabla de parqueaderos al combobox 
    public Vector<Convenio> mostrarConveniosDisponibles(){
        
        //Traemos los parqueaderos que esten disponibles
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;       
        Connection cn3 = Conexion.conectar();
        
        Vector<Convenio> datos = new Vector<Convenio>();
        Convenio dat = null;

        
        try{
           pst3 = cn3.prepareStatement("select Id_convenio, Nombre_convenio from convenios "); 
           rs3 = pst3.executeQuery();
           
           dat = new Convenio();
           dat.setId(0);
           dat.setNombre("Seleccione");
           datos.add(dat);
           
                     
           while(rs3.next()){
               dat = new Convenio();
               dat.setId(rs3.getInt("Id_convenio"));
               dat.setNombre(rs3.getString("Nombre_convenio"));
               datos.add(dat);
           }

           rs3.close();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar listado de convenios disponibles, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al al cargar listado de convenios disponibles: " + ex.toString());
        }
        return datos;
    }
}
