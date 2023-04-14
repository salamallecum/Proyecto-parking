package modelo;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    
     public static ArrayList<String> listadoNombresConvenio = new ArrayList<String>(); 
    
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
    
    //Metodo que se encarga de almacenar los nombres de convenio en un arraylist 
    public void almacenarNombresConvenio(){
        
        //Traemos todos los parqueaderos
        PreparedStatement pst4 = null;
        ResultSet rs4 = null;       
        Connection cn4 = Conexion.conectar();
        
        //Pedimos al sistema que cuente cuantos convenios tiene registrados
        int numConveniosRegistrados = contarConveniosRegistrados();
        
        try{
           pst4 = cn4.prepareStatement("select Nombre_convenio from convenios"); 
           rs4 = pst4.executeQuery();
                          
           while(rs4.next()){               
               for (int i = 0; i < numConveniosRegistrados; i++) {
                   if(!listadoNombresConvenio.contains(rs4.getString("Nombre_convenio"))){
                       listadoNombresConvenio.add(i, rs4.getString("Nombre_convenio"));
                   }
               }
           }            
           rs4.close();
          
        }catch(SQLException ex){
            log.fatal("ERROR - Se ha producido un error al armar Arraylist de nombres de parqueaderos: " + ex.toString());
        }    
    } 
    
    //Metodo que se encarga de contar cuantos convenios hay registrados en el sistema
    private int contarConveniosRegistrados(){
        
        int numConv = 0;
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select COUNT(*) from convenios");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                numConv = rs.getInt("COUNT(*)");
                cn.close();
                return numConv;
            }
            
        } catch (SQLException e) {
            log.fatal("ERROR - Se ha producido un error al contar la cantidad de convenios registrados en el sistema: " + e);
        } 
        return numConv;
    }
}
