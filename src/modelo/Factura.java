package modelo;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class Factura {
    
    private int id;
    private String codigo;
    private String fechaDeFactura;
    private String placa;
    private String propietario;
    private String claseDeVehiculo;
    private int id_parqueadero;
    private int facturadoPor;
    private String estadoDeFactura;
    private String estaContabilizada;
    private int id_convenio;
    private int id_tarifa;
    private String fechaDeIngresoVehiculo;
    private int id_cierre;
    private String fechaDeSalidaVehiculo;
    private String diferencia;
    private String impuesto;
    private String valorAPagar;
    private String efectivo;
    private String cambio;    
        
    private final Logger log = Logger.getLogger(Factura.class);
    private final URL url = Factura.class.getResource("Log4j.properties");
    
    //Constructor
    public Factura(int id, String codigo, String fechaDeFactura, String placa, String propietario, String claseDeVehiculo, int id_parqueadero, int facturadoPor, String estadoDeFactura, String estaContabilizada, int id_convenio, int id_tarifa, String fechaDeIngresoVehiculo, int id_cierre, String fechaDeSalidaVehiculo, String diferencia, String impuesto, String valorAPagar, String efectivo, String cambio) {
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
        this.fechaDeSalidaVehiculo = fechaDeSalidaVehiculo;
        this.diferencia = diferencia;
        this.impuesto = impuesto;
        this.valorAPagar = valorAPagar;
        this.efectivo = efectivo;
        this.cambio = cambio;
    }
    
    //getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
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

    public int getFacturadoPor() {
        return facturadoPor;
    }

    public void setFacturadoPor(int facturadoPor) {
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
    
    public String getFechaDeSalidaVehiculo() {
        return fechaDeSalidaVehiculo;
    }

    public void setFechaDeSalidaVehiculo(String fechaSalidaVehiculo) {
        this.fechaDeSalidaVehiculo = fechaSalidaVehiculo;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }
    
    public String getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(String valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public String getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(String efectivo) {
        this.efectivo = efectivo;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }
 
}
