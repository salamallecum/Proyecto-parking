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
public class Tarifa {
    
    private int id;
    private String nombreTarifa;
    private String montoTarifa;
    private String frecuenciaTarifa;
    private String tarifaAnulada;
    private String tarifaTieneDescuento;
    private String tiempoDelDescuento;
    private String unidadDelDescuento;
    private String tarifaCobraTiempoAdicional;
    private String montoTiempoAdicional;
    private String unidadDelTiempoAdicional;
    
    private final Logger log = Logger.getLogger(Tarifa.class);
    private URL url = Tarifa.class.getResource("Log4j.properties");
    
     public static ArrayList<String> listadoNombresTarifa = new ArrayList<String>();

    public Tarifa(int id, String nombreTarifa, String montoTarifa, String frecuenciaTarifa, String tarifaAnulada, String tarifaTieneDescuento, String tiempoDelDescuento, String unidadDelDescuento, String tarifaCobraTiempoAdicional, String montoTiempoAdicional, String unidadDelTiempoAdicional) {
        this.id = id;
        this.nombreTarifa = nombreTarifa;
        this.montoTarifa = montoTarifa;
        this.frecuenciaTarifa = frecuenciaTarifa;
        this.tarifaAnulada = tarifaAnulada;
        this.tarifaTieneDescuento = tarifaTieneDescuento;
        this.tiempoDelDescuento = tiempoDelDescuento;
        this.unidadDelDescuento = unidadDelDescuento;
        this.tarifaCobraTiempoAdicional = tarifaCobraTiempoAdicional;
        this.montoTiempoAdicional = montoTiempoAdicional;
        this.unidadDelTiempoAdicional = unidadDelTiempoAdicional;
    }

    public Tarifa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreTarifa() {
        return nombreTarifa;
    }

    public void setNombreTarifa(String nombreTarifa) {
        this.nombreTarifa = nombreTarifa;
    }

    public String getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(String montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public String getFrecuenciaTarifa() {
        return frecuenciaTarifa;
    }

    public void setFrecuenciaTarifa(String frecuenciaTarifa) {
        this.frecuenciaTarifa = frecuenciaTarifa;
    }
    
    public String getTarifaAnulada() {
        return tarifaAnulada;
    }

    public void setTarifaAnulada(String tarifaAnulada) {
        this.tarifaAnulada = tarifaAnulada;
    }

    public String getTarifaTieneDescuento() {
        return tarifaTieneDescuento;
    }

    public void setTarifaTieneDescuento(String tarifaTieneDescuento) {
        this.tarifaTieneDescuento = tarifaTieneDescuento;
    }

    public String getTiempoDelDescuento() {
        return tiempoDelDescuento;
    }

    public void setTiempoDelDescuento(String tiempoDelDescuento) {
        this.tiempoDelDescuento = tiempoDelDescuento;
    }

    public String getUnidadDelDescuento() {
        return unidadDelDescuento;
    }

    public void setUnidadDelDescuento(String unidadDelDescuento) {
        this.unidadDelDescuento = unidadDelDescuento;
    }

    public String getTarifaCobraTiempoAdicional() {
        return tarifaCobraTiempoAdicional;
    }

    public void setTarifaCobraTiempoAdicional(String tarifaCobraTiempoAdicional) {
        this.tarifaCobraTiempoAdicional = tarifaCobraTiempoAdicional;
    }

    public String getMontoTiempoAdicional() {
        return montoTiempoAdicional;
    }

    public void setMontoTiempoAdicional(String montoTiempoAdicional) {
        this.montoTiempoAdicional = montoTiempoAdicional;
    }

    public String getUnidadDelTiempoAdicional() {
        return unidadDelTiempoAdicional;
    }

    public void setUnidadDelTiempoAdicional(String unidadDelTiempoAdicional) {
        this.unidadDelTiempoAdicional = unidadDelTiempoAdicional;
    }    
    
    //Metodos
    public String toString(){
        return this.nombreTarifa;
    }
    
    //Agrega los valores de la tabla de parqueaderos al combobox 
    public Vector<Tarifa> mostrarTarifasDisponibles(){
        
        //Traemos los parqueaderos que esten disponibles
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;       
        Connection cn3 = Conexion.conectar();
        
        Vector<Tarifa> datos = new Vector<Tarifa>();
        Tarifa dat = null;

        
        try{
           pst3 = cn3.prepareStatement("select Id_tarifa, Nombre_tarifa from tarifas "); 
           rs3 = pst3.executeQuery();
           
           dat = new Tarifa(0, "", "", "", "", "", "", "", "", "", "");
           dat.setId(0);
           dat.setNombreTarifa("Seleccione");
           datos.add(dat);
           
                     
           while(rs3.next()){
               dat = new Tarifa(0, "", "", "", "", "", "", "", "", "", "");
               dat.setId(rs3.getInt("Id_tarifa"));
               dat.setNombreTarifa(rs3.getString("Nombre_tarifa"));
               datos.add(dat);
           }

           rs3.close();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar listado de tarifas disponibles, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al al cargar listado de tarifas disponibles: " + ex.toString());
        }
        return datos;
    }
    
    //Metodo que se encarga de almacenar los nombres de tarifa en un arraylist 
    public void almacenarNombresTarifa(){
        
        //Traemos todos los parqueaderos
        PreparedStatement pst4 = null;
        ResultSet rs4 = null;       
        Connection cn4 = Conexion.conectar();
        
        //Pedimos al sistema que cuente cuantas tarifas tiene registrados
        int numTarifasRegistradas = contarTarifasRegistradas();
        
        try{
           pst4 = cn4.prepareStatement("select Nombre_tarifa from tarifas"); 
           rs4 = pst4.executeQuery();
                          
           while(rs4.next()){               
               for (int i = 0; i < numTarifasRegistradas; i++) {
                   if(!listadoNombresTarifa.contains(rs4.getString("Nombre_tarifa"))){
                       listadoNombresTarifa.add(i, rs4.getString("Nombre_tarifa"));
                   }
               }
           }            
           rs4.close();
          
        }catch(SQLException ex){
            log.fatal("ERROR - Se ha producido un error al armar Arraylist de nombres de parqueaderos: " + ex.toString());
        }    
    } 
    
    //Metodo que se encarga de contar cuantas tarifas se encuentran registrados en el sistema
    private int contarTarifasRegistradas(){
        
        int numTarif = 0;
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select COUNT(*) from tarifas");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                numTarif = rs.getInt("COUNT(*)");
                cn.close();
                return numTarif;
            }
            
        } catch (SQLException e) {
            log.fatal("ERROR - Se ha producido un error al contar la cantidad de tarifas registradas en el sistema: " + e);
        } 
        return numTarif;
    }
}
