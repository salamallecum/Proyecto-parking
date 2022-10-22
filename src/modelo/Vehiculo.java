package modelo;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class Vehiculo {
    
    private int id = 0;
    private String placa = "";
    private String propietario = "";
    private String clase = "";
    private int id_parqueadero = 0;
    private int id_convenio = 0;
    private int id_tarifa = 0;
    private String estaEnParqueadero = "";
    
    private final Logger log = Logger.getLogger(Vehiculo.class);
    private URL url = Vehiculo.class.getResource("Log4j.properties");
    
    //Constructor
    public Vehiculo(int id, String placa, String propietario, String clase, int id_parqueadero, int id_convenio, int id_tarifa) {
        this.id = id;
        this.placa = placa;
        this.propietario = propietario;
        this.clase = clase;
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
