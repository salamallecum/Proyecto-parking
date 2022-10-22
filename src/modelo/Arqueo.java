package modelo;

import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class Arqueo {
        
    private int Id;
    private String codigo;
    private String usuario;
    private String fecha_arqueo;
    private String base_caja;

    private String numBilletesDe100Mil;
    private String numBilletesDe50Mil;
    private String numBilletesDe20Mil;
    private String numBilletesDe10Mil;
    private String numBilletesDe5Mil;
    private String numBilletesDe2Mil;
    private String numBilletesOMonedasDeMil;
    private String numMonedasDe500;
    private String numMonedasDe200;
    private String numMonedasDe100;
    private String numMonedasDe50;

    private int montoEnBilletes100Mil;
    private int montoEnBilletes50Mil;
    private int montoEnBilletes20Mil;
    private int montoEnBilletes10Mil;
    private int montoEnBilletes5Mil;
    private int montoEnBilletes2Mil;
    private int montoEnBilletesOMonedasMil;
    private int montoEnMonedasDe500;
    private int montoEnMonedasDe200;
    private int montoEnMonedasDe100;
    private int montoEnMonedasDe50; 

    private String montoTotalCaja;
    private String diferenciaTotal;
    
    private final Logger log = Logger.getLogger(Arqueo.class);
    private URL url = Arqueo.class.getResource("Log4j.properties");
    
    //Constructor
    public Arqueo(int Id, String codigo, String usuario, String fecha_arqueo, String base_caja, String numBilletesDe100Mil, String numBilletesDe50Mil, String numBilletesDe20Mil, String numBilletesDe10Mil, String numBilletesDe5Mil, String numBilletesDe2Mil, String numBilletesOMonedasDeMil, String numMonedasDe500, String numMonedasDe200, String numMonedasDe100, String numMonedasDe50, int montoEnBilletes100Mil, int montoEnBilletes50Mil, int montoEnBilletes20Mil, int montoEnBilletes10Mil, int montoEnBilletes5Mil, int montoEnBilletes2Mil, int montoEnBilletesOMonedasMil, int montoEnMonedasDe500, int montoEnMonedasDe200, int montoEnMonedasDe100, int montoEnMonedasDe50, String montoTotalCaja, String diferenciaTotal) {
        this.Id = Id;
        this.codigo = codigo;
        this.usuario = usuario;
        this.fecha_arqueo = fecha_arqueo;
        this.base_caja = base_caja;
        this.numBilletesDe100Mil = numBilletesDe100Mil;
        this.numBilletesDe50Mil = numBilletesDe50Mil;
        this.numBilletesDe20Mil = numBilletesDe20Mil;
        this.numBilletesDe10Mil = numBilletesDe10Mil;
        this.numBilletesDe5Mil = numBilletesDe5Mil;
        this.numBilletesDe2Mil = numBilletesDe2Mil;
        this.numBilletesOMonedasDeMil = numBilletesOMonedasDeMil;
        this.numMonedasDe500 = numMonedasDe500;
        this.numMonedasDe200 = numMonedasDe200;
        this.numMonedasDe100 = numMonedasDe100;
        this.numMonedasDe50 = numMonedasDe50;
        this.montoEnBilletes100Mil = montoEnBilletes100Mil;
        this.montoEnBilletes50Mil = montoEnBilletes50Mil;
        this.montoEnBilletes20Mil = montoEnBilletes20Mil;
        this.montoEnBilletes10Mil = montoEnBilletes10Mil;
        this.montoEnBilletes5Mil = montoEnBilletes5Mil;
        this.montoEnBilletes2Mil = montoEnBilletes2Mil;
        this.montoEnBilletesOMonedasMil = montoEnBilletesOMonedasMil;
        this.montoEnMonedasDe500 = montoEnMonedasDe500;
        this.montoEnMonedasDe200 = montoEnMonedasDe200;
        this.montoEnMonedasDe100 = montoEnMonedasDe100;
        this.montoEnMonedasDe50 = montoEnMonedasDe50;
        this.montoTotalCaja = montoTotalCaja;
        this.diferenciaTotal = diferenciaTotal;
    }
    
    //Getter y setter
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_arqueo() {
        return fecha_arqueo;
    }

    public void setFecha_arqueo(String fecha_arqueo) {
        this.fecha_arqueo = fecha_arqueo;
    }

    public String getBase_caja() {
        return base_caja;
    }

    public void setBase_caja(String base_caja) {
        this.base_caja = base_caja;
    }

    public String getNumBilletesDe100Mil() {
        return numBilletesDe100Mil;
    }

    public void setNumBilletesDe100Mil(String numBilletesDe100Mil) {
        this.numBilletesDe100Mil = numBilletesDe100Mil;
    }

    public String getNumBilletesDe50Mil() {
        return numBilletesDe50Mil;
    }

    public void setNumBilletesDe50Mil(String numBilletesDe50Mil) {
        this.numBilletesDe50Mil = numBilletesDe50Mil;
    }

    public String getNumBilletesDe20Mil() {
        return numBilletesDe20Mil;
    }

    public void setNumBilletesDe20Mil(String numBilletesDe20Mil) {
        this.numBilletesDe20Mil = numBilletesDe20Mil;
    }

    public String getNumBilletesDe10Mil() {
        return numBilletesDe10Mil;
    }

    public void setNumBilletesDe10Mil(String numBilletesDe10Mil) {
        this.numBilletesDe10Mil = numBilletesDe10Mil;
    }

    public String getNumBilletesDe5Mil() {
        return numBilletesDe5Mil;
    }

    public void setNumBilletesDe5Mil(String numBilletesDe5Mil) {
        this.numBilletesDe5Mil = numBilletesDe5Mil;
    }

    public String getNumBilletesDe2Mil() {
        return numBilletesDe2Mil;
    }

    public void setNumBilletesDe2Mil(String numBilletesDe2Mil) {
        this.numBilletesDe2Mil = numBilletesDe2Mil;
    }

    public String getNumBilletesOMonedasDeMil() {
        return numBilletesOMonedasDeMil;
    }

    public void setNumBilletesOMonedasDeMil(String numBilletesOMonedasDeMil) {
        this.numBilletesOMonedasDeMil = numBilletesOMonedasDeMil;
    }

    public String getNumMonedasDe500() {
        return numMonedasDe500;
    }

    public void setNumMonedasDe500(String numMonedasDe500) {
        this.numMonedasDe500 = numMonedasDe500;
    }

    public String getNumMonedasDe200() {
        return numMonedasDe200;
    }

    public void setNumMonedasDe200(String numMonedasDe200) {
        this.numMonedasDe200 = numMonedasDe200;
    }

    public String getNumMonedasDe100() {
        return numMonedasDe100;
    }

    public void setNumMonedasDe100(String numMonedasDe100) {
        this.numMonedasDe100 = numMonedasDe100;
    }

    public String getNumMonedasDe50() {
        return numMonedasDe50;
    }

    public void setNumMonedasDe50(String numMonedasDe50) {
        this.numMonedasDe50 = numMonedasDe50;
    }

    public int getMontoEnBilletes100Mil() {
        return montoEnBilletes100Mil;
    }

    public void setMontoEnBilletes100Mil(int montoEnBilletes100Mil) {
        this.montoEnBilletes100Mil = montoEnBilletes100Mil;
    }

    public int getMontoEnBilletes50Mil() {
        return montoEnBilletes50Mil;
    }

    public void setMontoEnBilletes50Mil(int montoEnBilletes50Mil) {
        this.montoEnBilletes50Mil = montoEnBilletes50Mil;
    }

    public int getMontoEnBilletes20Mil() {
        return montoEnBilletes20Mil;
    }

    public void setMontoEnBilletes20Mil(int montoEnBilletes20Mil) {
        this.montoEnBilletes20Mil = montoEnBilletes20Mil;
    }

    public int getMontoEnBilletes10Mil() {
        return montoEnBilletes10Mil;
    }

    public void setMontoEnBilletes10Mil(int montoEnBilletes10Mil) {
        this.montoEnBilletes10Mil = montoEnBilletes10Mil;
    }

    public int getMontoEnBilletes5Mil() {
        return montoEnBilletes5Mil;
    }

    public void setMontoEnBilletes5Mil(int montoEnBilletes5Mil) {
        this.montoEnBilletes5Mil = montoEnBilletes5Mil;
    }

    public int getMontoEnBilletes2Mil() {
        return montoEnBilletes2Mil;
    }

    public void setMontoEnBilletes2Mil(int montoEnBilletes2Mil) {
        this.montoEnBilletes2Mil = montoEnBilletes2Mil;
    }

    public int getMontoEnBilletesOMonedasMil() {
        return montoEnBilletesOMonedasMil;
    }

    public void setMontoEnBilletesOMonedasMil(int montoEnBilletesOMonedasMil) {
        this.montoEnBilletesOMonedasMil = montoEnBilletesOMonedasMil;
    }

    public int getMontoEnMonedasDe500() {
        return montoEnMonedasDe500;
    }

    public void setMontoEnMonedasDe500(int montoEnMonedasDe500) {
        this.montoEnMonedasDe500 = montoEnMonedasDe500;
    }

    public int getMontoEnMonedasDe200() {
        return montoEnMonedasDe200;
    }

    public void setMontoEnMonedasDe200(int montoEnMonedasDe200) {
        this.montoEnMonedasDe200 = montoEnMonedasDe200;
    }

    public int getMontoEnMonedasDe100() {
        return montoEnMonedasDe100;
    }

    public void setMontoEnMonedasDe100(int montoEnMonedasDe100) {
        this.montoEnMonedasDe100 = montoEnMonedasDe100;
    }

    public int getMontoEnMonedasDe50() {
        return montoEnMonedasDe50;
    }

    public void setMontoEnMonedasDe50(int montoEnMonedasDe50) {
        this.montoEnMonedasDe50 = montoEnMonedasDe50;
    }

    public String getMontoTotalCaja() {
        return montoTotalCaja;
    }

    public void setMontoTotalCaja(String montoTotalCaja) {
        this.montoTotalCaja = montoTotalCaja;
    }

    public String getDiferenciaTotal() {
        return diferenciaTotal;
    }

    public void setDiferenciaTotal(String diferenciaTotal) {
        this.diferenciaTotal = diferenciaTotal;
    }
}
