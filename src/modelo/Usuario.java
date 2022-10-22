package modelo;

import java.net.URL;
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
    
    //Constructor   
    public Usuario(int id, String nombres, String apellidos, String celular, String telefono, String usuario, String clave, String rol, String activo) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.telefono = telefono;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
        this.activo = activo;
    }    
    
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
}
