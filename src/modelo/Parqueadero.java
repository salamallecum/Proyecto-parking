package modelo;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private String placa;
    private String propietario;
    private String estaEnParqueo;
    
    private final Logger log = Logger.getLogger(Parqueadero.class);
    private URL url = Parqueadero.class.getResource("Log4j.properties");
    
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
    
        
    //Metodos
    public String toString(){
        return this.nombre;
    }
    
    //Agrega los valores de la tabla de parqueaderos al combobox 
    public Vector<Parqueadero> mostrarParqueaderosDisponibles(){
        
        //Traemos todoslos parqueaderos
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;       
        Connection cn3 = Conexion.conectar();
        
        Vector<Parqueadero> datos = new Vector<Parqueadero>();
        Parqueadero dat = null;

        
        try{
           pst3 = cn3.prepareStatement("select Id_parqueadero, Nombre_parqueadero from parqueaderos where Estado = 'Disponible'"); 
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
            log.fatal("ERROR - Se ha producido un error al al cargar listado de parqueaderos disponibles: " + ex.toString());
        }
        return datos;
    }  
    
    //Agrega los valores de la tabla de parqueaderos al combobox de la ventana de registro y edición de vehiculos
    public Vector<Parqueadero> mostrarParqueaderos(){
        
        //Traemos todoslos parqueaderos
        PreparedStatement pst4 = null;
        ResultSet rs4 = null;       
        Connection cn4 = Conexion.conectar();
        
        Vector<Parqueadero> datosPrueba = new Vector<Parqueadero>();
        Parqueadero dat = null;

        
        try{
           pst4 = cn4.prepareStatement("select Id_parqueadero, Nombre_parqueadero from parqueaderos"); 
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
            JOptionPane.showMessageDialog(null, "Error al cargar listado de parqueaderos al combobox de edición de vehiculos, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al al cargar listado de parqueaderos alcombobox de parqueaderos dela ventana de edición de vehiculos: " + ex.toString());
        }
        return datosPrueba;
    }
}
