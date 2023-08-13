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
public class Usuario {
    
    private int id;
    private String nombres;
    private String apellidos;
    private String celular;
    private String telefono;
    private String usuario;
    private String clave;
    private String rol;
    private String activo;
    
    private final Logger log = Logger.getLogger(Usuario.class);
    private URL url = Usuario.class.getResource("Log4j.properties");
    
    //Metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
        
    //Agrega los valores de la tabla de usuarios a los combobox en gestor factura, cierres y arqueos  
    public Vector<Usuario> listadoUsuariosDelSistema(){
        
        //Traemos los usuarios
        PreparedStatement pst3 = null;
        ResultSet rs3 = null;       
        Connection cn3 = Conexion.conectar();
        
        Vector<Usuario> datos = new Vector<Usuario>();
        Usuario dat = null;
        
        try{
           pst3 = cn3.prepareStatement("select Id_usuario, Nombres from usuarios"); 
           rs3 = pst3.executeQuery();
           
           dat = new Usuario();
           dat.setId(0);
           dat.setNombres("Seleccione");
           datos.add(dat);
           
           while(rs3.next()){
               dat = new Usuario();
               dat.setId(rs3.getInt("Id_usuario"));
               dat.setNombres(rs3.getString("Nombres"));
               datos.add(dat);
           }
           
           System.out.println(datos);

           rs3.close();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al cargar listado de usuarios del sistema, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al cargar listado de usuarios: " + ex.toString());
        }
        return datos;
    } 
}
