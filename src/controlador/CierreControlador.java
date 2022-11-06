package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Cierre;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.PanelCaja;

/**
 *
 * @author ALEJO
 */
public class CierreControlador {
    
   private final Logger log = Logger.getLogger(CierreControlador.class);
   private URL url = CierreControlador.class.getResource("Log4j.properties");
   
   //Constructor
   public CierreControlador() {}  
    
    //Metodo que genera la fecha en la quese realizo el cierre de caja       
    public String fecha_Cierre(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }
    
    //Metodo que bloquea la caja una vez hecho el cierre
    public void bloquearCaja(){
        
        PanelCaja.btn_abrirCaja.setEnabled(true);
        PanelCaja.txt_Placa.setEnabled(false);
        PanelCaja.txt_nombrePropietario.setEnabled(false);
        PanelCaja.cmb_clase.setEnabled(false);
        PanelCaja.txt_convenio.setEnabled(false);
        PanelCaja.txt_tarifa.setEnabled(false);
        PanelCaja.table_operacionParqueadero.setEnabled(false);
        PanelCaja.btn_estadoParqueadero.setEnabled(false);
        PanelCaja.btn_generarCierreDeCaja.setEnabled(false);
        PanelCaja.btn_ingresar.setEnabled(false);
        PanelCaja.cmb_numParqueadero.setEnabled(false);

    }
    
    //Metodo que crea un cierre de caja
    public void crearCierreDeCaja(Cierre cier){
        
        //Inserta el arqueo en la base de datos de arqueos      
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "insert into cierres values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst3.setInt(1, cier.getId());
            pst3.setString(2, cier.getCodigo());
            pst3.setString(3, cier.getCodigoArqueo()); 
            pst3.setString(4, cier.getFecha_cierre());
            pst3.setString(5, cier.getUsuario());
            pst3.setString(6, cier.getBase_caja());
            pst3.setString(7, cier.getNumBilletesDe100Mil());
            pst3.setString(8, cier.getNumBilletesDe50Mil());
            pst3.setString(9, cier.getNumBilletesDe20Mil());
            pst3.setString(10, cier.getNumBilletesDe10Mil());
            pst3.setString(11, cier.getNumBilletesDe5Mil());
            pst3.setString(12, cier.getNumBilletesDe2Mil());
            pst3.setString(13, cier.getNumBilletesOMonedasDeMil());
            pst3.setString(14, cier.getNumMonedasDe500());
            pst3.setString(15, cier.getNumMonedasDe200());
            pst3.setString(16, cier.getNumMonedasDe100());
            pst3.setString(17, cier.getNumMonedasDe50());
            pst3.setInt(18, cier.getMontoEnBilletes100Mil());
            pst3.setInt(19, cier.getMontoEnBilletes50Mil()); 
            pst3.setInt(20, cier.getMontoEnBilletes20Mil());
            pst3.setInt(21, cier.getMontoEnBilletes10Mil());
            pst3.setInt(22, cier.getMontoEnBilletes5Mil());
            pst3.setInt(23, cier.getMontoEnBilletes2Mil());
            pst3.setInt(24, cier.getMontoEnBilletesOMonedasMil());
            pst3.setInt(25, cier.getMontoEnMonedasDe500());
            pst3.setInt(26, cier.getMontoEnMonedasDe200());
            pst3.setInt(27, cier.getMontoEnMonedasDe100());
            pst3.setInt(28, cier.getMontoEnMonedasDe50());
            pst3.setString(29, cier.getProducido());
            pst3.setString(30, cier.getTotal_esperado());  
            pst3.setString(31, cier.getDinero_caja()); 
            pst3.setString(32, cier.getDiferencia()); 
            pst3.setString(33, cier.getDineroAConsignar()); 
            pst3.setString(34, cier.getNo_facturas()); 
            pst3.setString(35, cier.getObservaciones());
            
            pst3.executeUpdate();
            cn3.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un cierre de caja en el sistema: " + e);
        } 
    }
    
    //Metodo que imprime el ticket de cuando se realizael cierre de caja al final de turno
    public void generarTicketCierreDeCaja(String codigoDeCierre){
               
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("codigo", codigoDeCierre);
           
           JasperReport reporte = null;
           
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Cierre.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

           //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setTitle("Ticket de cierre de caja");
            view.setVisible(true);
           
           //Hace que se imprima directamente
           //JasperPrintManager.printReport(jprint, false);

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Cierre de Caja, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de cierre de caja de final de turno: " + ex);
       }
    }
    
    //Metodo que le aprovisiona el id de cierre a las facturas que han sido contabilizadas en un cierre
    public void asignarCierreAFacturasContabilizadas(int idCierre){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Contabilizada='Si', Id_cierre ='"+idCierre+"' where Estado_fctra = 'Cerrada' and Contabilizada='No'");

            pst.executeUpdate();
            cn.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al contabilizar facturas, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar asginar un cierreafacturas contabilizadas: " + e);
        }
    }
    
    //Metodo que obtiene el id del cierre creado
    public int obtenerIdCierreConCodigo(String codigoDelCierre){
        
        int idDelCierre = 0;
        try {
            Connection cn8 = Conexion.conectar();
            PreparedStatement pst8 = cn8.prepareStatement(
                "SELECT Id_cierre FROM cierres WHERE Codigo = '"+codigoDelCierre+"'");
            ResultSet rs8 = pst8.executeQuery();

            if(rs8.next()){
                idDelCierre = rs8.getInt("Id_cierre");
            }
            cn8.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al seleccionar cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar consultar el id de un cierre: " + e);
        }
        return idDelCierre;
    }
    
    //Metodo que calcula el dinero que se debe consignar para conservar solo la base en caja
    public String calcularDineroAConsignar(String dineroEnCaja, String base){
        
        String dineroAConsignar = "";
        
        System.out.println("Dinero en caja recibido: "+dineroEnCaja);
        System.out.println("Base recibida: "+base);                
        
        //Le quitamos el formato de moneda al dinero de caja
        String charsToRemove = "$.";
                
        for (char c : charsToRemove.toCharArray()) {
            dineroEnCaja = dineroEnCaja.replace(String.valueOf(c), "");
        }

        dineroEnCaja = dineroEnCaja.replaceAll(",", ".");

        if(dineroEnCaja.contains(".")){
            dineroEnCaja = dineroEnCaja.substring(0,dineroEnCaja.indexOf("."));
        }       
                  
        //Convertimos las cantidades a enteros para el calculo correspondiente
        int baseInt = Integer.parseInt(base);
        int dineroCajaInt = Integer.parseInt(dineroEnCaja);
        dineroCajaInt = Integer.parseInt(dineroEnCaja);
        
        System.out.println("Dinero de caja convertido a int: "+ dineroCajaInt);

        int dineroAConsig = dineroCajaInt - baseInt;
        System.out.println("Dinero a consignar: "+dineroAConsig);
        
        //Damos formato de moneda al dinero a consignar
        dineroAConsignar = Integer.toString(dineroAConsig);
        Double consignacion = new Double(dineroAConsignar);
        Locale region = Locale.getDefault();
        //Currency moneda = Currency.getInstance(region);
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(region);
        dineroAConsignar = formatoMoneda.format(consignacion);
        
        return dineroAConsignar;
    }

    
}
