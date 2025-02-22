package modelo;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class Vehiculo {
    
    private int id = 0;
    private String qr_consecutivo = "";
    private String placa = "";
    private String tipoIdentificacion = "";
    private String numIdentificacion = "";
    private String propietario = "";
    private String tipoVehiculo = "";
    private String color = "";
    private int id_parqueadero = 0;
    private int id_convenio = 0;
    private int id_tarifa = 0;
    private String estaEnParqueadero = "";
    
    private final Logger log = Logger.getLogger(Vehiculo.class);
    private URL url = Vehiculo.class.getResource("Log4j.properties");
    
    //Constructor
    public Vehiculo(int id, String qr_consecutivo, String placa, String tipoIdentificacion, String numIdentificacion, String propietario, String tipoVehiculo, String color, int id_parqueadero, int id_convenio, int id_tarifa) {
        this.id = id;
        this.qr_consecutivo = qr_consecutivo;
        this.placa = placa;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numIdentificacion = numIdentificacion;
        this.propietario = propietario;
        this.tipoVehiculo = tipoVehiculo;
        this.color = color;
        this.id_parqueadero = id_parqueadero;
        this.id_convenio = id_convenio;
        this.id_tarifa = id_tarifa;
    } 
        
    //Metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQr_consecutivo() {
        return qr_consecutivo;
    }

    public void setQr_consecutivo(String qr_consecutivo) {
        this.qr_consecutivo = qr_consecutivo;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }
    
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public int getId_parqueadero() {
        return id_parqueadero;
    }

    public void setId_parqueadero(int id_parqueadero) {
        this.id_parqueadero = id_parqueadero;
    }

    public int getId_convenio() {
        return id_convenio;
    }

    public void setId_convenio(int id_convenio) {
        this.id_convenio = id_convenio;
    }

    public int getId_tarifa() {
        return id_tarifa;
    }

    public void setId_tarifa(int id_tarifa) {
        this.id_tarifa = id_tarifa;
    }
    
    public String getEstaEnParqueadero() {
        return estaEnParqueadero;
    }

    public void setEstaEnParqueadero(String estaEnParq) {
        this.estaEnParqueadero = estaEnParq;
    }
}
