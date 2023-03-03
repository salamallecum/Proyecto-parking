package modelo;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import java.util.Vector;


/**
 *
 * @author ALEJO
 */
public class Parqueadero {
    
    private int id;
    private String nombre;
    private String estado;
    private String tipoParqueadero;
    private String placa;
    private String propietario;
    private String estaEnParqueo;
    
    private final Logger log = Logger.getLogger(Parqueadero.class);
    private URL url = Parqueadero.class.getResource("Log4j.properties");
    
    public static ArrayList<String> listadoNombresParqueadero = new ArrayList<String>();
    
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getEstaOcupado() {
        return estaEnParqueo;
    }

    public void setEstaOcupado(String estaEnParqueo) {
        this.estaEnParqueo = estaEnParqueo;
    }

    public String getTipoParqueadero() {
        return tipoParqueadero;
    }

    public void setTipoParqueadero(String tipoParqueadero) {
        this.tipoParqueadero = tipoParqueadero;
    }
      
        
    //Metodos
    public String toString(){
        return this.nombre;
    }
    
    //Agrega los valores de la tabla de parqueaderos al combobox 
    public Vector<Parqueadero> mostrarParqueaderosTipoVisitanteDisponibles(){
        
        //Traemos todoslos parqueaderos
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;       
        Connection cn3 = Conexion.conectar();
        
        Vector<Parqueadero> datos = new Vector<Parqueadero>();
        Parqueadero dat = null;

        
        try{
           pst3 = cn3.prepareStatement("select Id_parqueadero, Nombre_parqueadero from parqueaderos where Estado = 'Disponible' and TipoParq = 'VISITANTE'"); 
           rs3 = pst3.executeQuery();
           
           dat = new Parqueadero();
           dat.setId(0);
           dat.setNombre("Seleccione");
           datos.add(dat);
           
           while(rs3.next()){
               dat = new Parqueadero();
               dat.setId(rs3.getInt("Id_parqueadero"));
               dat.setNombre(rs3.getString("Nombre_parqueadero"));
               datos.add(dat);
           }

           rs3.close();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar listado de parqueaderos disponibles, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al cargar listado de parqueaderos disponibles en Panel Caja: " + ex.toString());
        }
        return datos;
    }  
    
    //Agrega los valores de la tabla de parqueaderos al combobox de la ventana de registro y edición de vehiculos
    public Vector<Parqueadero> mostrarParqueaderosTipoResidente(){
        
        //Traemos todos los parqueaderos
        PreparedStatement pst4 = null;
        ResultSet rs4 = null;       
        Connection cn4 = Conexion.conectar();
        
        Vector<Parqueadero> datosPrueba = new Vector<Parqueadero>();
        Parqueadero dat = null;
        
        try{
           pst4 = cn4.prepareStatement("select Id_parqueadero, Nombre_parqueadero from parqueaderos where TipoParq = 'RESIDENTE'"); 
           rs4 = pst4.executeQuery();
           
           dat = new Parqueadero();
           dat.setId(0);
           dat.setNombre("Seleccione");
           datosPrueba.add(dat);
           
           while(rs4.next()){
               dat = new Parqueadero();
               dat.setId(rs4.getInt("Id_parqueadero"));
               dat.setNombre(rs4.getString("Nombre_parqueadero"));
               datosPrueba.add(dat);
           }
           rs4.close();
           
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar listado de parqueaderos, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al al cargar listado de parqueaderos: " + ex.toString());
        }
        return datosPrueba;
    }
    
    //Metodo que se encarga de almacenar los nombres de paqueadero en un arraylist 
    public void almacenarNombresParqueadero(){
        
        //Traemos todos los parqueaderos
        PreparedStatement pst4 = null;
        ResultSet rs4 = null;       
        Connection cn4 = Conexion.conectar();
        
        //Pedimos al sistema que cuente cuantos parqueaderos de tipo residente tiene registrados
        int numParqueaderosRegistrados = contarParqueaderosRegistrados("RESIDENTE");
        
        try{
           pst4 = cn4.prepareStatement("select Nombre_parqueadero from parqueaderos where TipoParq = 'RESIDENTE'"); 
           rs4 = pst4.executeQuery();
                          
           while(rs4.next()){               
               for (int i = 0; i < numParqueaderosRegistrados; i++) {
                   if(!listadoNombresParqueadero.contains(rs4.getString("Nombre_parqueadero"))){
                       listadoNombresParqueadero.add(i, rs4.getString("Nombre_parqueadero"));
                   }
               }
           }            
           rs4.close();
          
        }catch(SQLException ex){
            log.fatal("ERROR - Se ha producido un error al armar Arraylist de nombres de parqueaderos: " + ex.toString());
        }    
    } 
    
    //Metodo que se encarga de contar cuantos parqueaderos sea tipo residente o visitante se encuentran registrados en el sistema
    private int contarParqueaderosRegistrados(String tipoParq){
        
        int numParq = 0;
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select COUNT(*) from parqueaderos where TipoParq = '"+tipoParq+"'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                numParq = rs.getInt("COUNT(*)");
                cn.close();
                return numParq;
            }
            
        } catch (SQLException e) {
            log.fatal("ERROR - Se ha producido un error al contar la cantidad de parqueaderos registrados en el sistema: " + e);
        }    
        return numParq;
    }
}
