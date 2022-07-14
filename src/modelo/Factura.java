package modelo;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class Factura {
    
    private int id;
    private int codigo;
    private String fechaDeFactura;
    private String placa;
    private String propietario;
    private String claseDeVehiculo;
    private int id_parqueadero;
    private String facturadoPor;
    private String estadoDeFactura;
    private String estaContabilizada;
    private int id_convenio;
    private int id_tarifa;
    private String fechaDeIngresoVehiculo;
    private int id_cierre;   
        
    private final Logger log = Logger.getLogger(Factura.class);
    private URL url = Factura.class.getResource("Log4j.properties");
    
    //Constructor
    public Factura(int id, int codigo, String fechaDeFactura, String placa, String propietario, String claseDeVehiculo, int id_parqueadero, String facturadoPor, String estadoDeFactura, String estaContabilizada, int id_convenio, int id_tarifa, String fechaDeIngresoVehiculo, int id_cierre) {
        this.id = id;
        this.codigo = codigo;
        this.fechaDeFactura = fechaDeFactura;
        this.placa = placa;
        this.propietario = propietario;
        this.claseDeVehiculo = claseDeVehiculo;
        this.id_parqueadero = id_parqueadero;
        this.facturadoPor = facturadoPor;
        this.estadoDeFactura = estadoDeFactura;
        this.estaContabilizada = estaContabilizada;
        this.id_convenio = id_convenio;
        this.id_tarifa = id_tarifa;
        this.fechaDeIngresoVehiculo = fechaDeIngresoVehiculo;
        this.id_cierre = id_cierre;
    }
    
    //getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFechaDeFactura() {
        return fechaDeFactura;
    }

    public void setFechaDeFactura(String fechaDeFactura) {
        this.fechaDeFactura = fechaDeFactura;
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

    public String getClaseDeVehiculo() {
        return claseDeVehiculo;
    }

    public void setClaseDeVehiculo(String claseDeVehiculo) {
        this.claseDeVehiculo = claseDeVehiculo;
    }

    public int getId_parqueadero() {
        return id_parqueadero;
    }

    public void setId_parqueadero(int id_parqueadero) {
        this.id_parqueadero = id_parqueadero;
    }

    public String getFacturadoPor() {
        return facturadoPor;
    }

    public void setFacturadoPor(String facturadoPor) {
        this.facturadoPor = facturadoPor;
    }

    public String getEstadoDeFactura() {
        return estadoDeFactura;
    }

    public void setEstadoDeFactura(String estadoDeFactura) {
        this.estadoDeFactura = estadoDeFactura;
    }

    public String getEstaContabilizada() {
        return estaContabilizada;
    }

    public void setEstaContabilizada(String estaContabilizada) {
        this.estaContabilizada = estaContabilizada;
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

    public String getFechaDeIngresoVehiculo() {
        return fechaDeIngresoVehiculo;
    }

    public void setFechaDeIngresoVehiculo(String fechaDeIngresoVehiculo) {
        this.fechaDeIngresoVehiculo = fechaDeIngresoVehiculo;
    }

    public int getId_cierre() {
        return id_cierre;
    }

    public void setId_cierre(int id_cierre) {
        this.id_cierre = id_cierre;
    }
}
