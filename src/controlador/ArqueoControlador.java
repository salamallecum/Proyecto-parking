package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Arqueo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.OtrosParametros;

/**
 *
 * @author ALEJO
 */
public class ArqueoControlador {
    
   private final Logger log = Logger.getLogger(ArqueoControlador.class);
   private URL url = ArqueoControlador.class.getResource("Log4j.properties");
   
   //Constructor
   public ArqueoControlador() {}
   
   //Metodo que crea un arqueo
    public void crearArqueo(Arqueo arq){
        
        //Inserta el arqueo en la base de datos de arqueos      
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "insert into arqueos values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst3.setInt(1, arq.getId());
            pst3.setString(2, arq.getCodigo());
            pst3.setString(3, arq.getFecha_arqueo());
            pst3.setString(4, arq.getUsuario());
            pst3.setString(5, arq.getBase_caja());
            pst3.setString(6, arq.getNumBilletesDe100Mil());
            pst3.setString(7, arq.getNumBilletesDe50Mil());
            pst3.setString(8, arq.getNumBilletesDe20Mil());
            pst3.setString(9, arq.getNumBilletesDe10Mil());
            pst3.setString(10, arq.getNumBilletesDe5Mil());
            pst3.setString(11, arq.getNumBilletesDe2Mil());
            pst3.setString(12, arq.getNumBilletesOMonedasDeMil());
            pst3.setString(13, arq.getNumMonedasDe500());
            pst3.setString(14, arq.getNumMonedasDe200());
            pst3.setString(15, arq.getNumMonedasDe100());
            pst3.setString(16, arq.getNumMonedasDe50());
            pst3.setInt(17, arq.getMontoEnBilletes100Mil());
            pst3.setInt(18, arq.getMontoEnBilletes50Mil()); 
            pst3.setInt(19, arq.getMontoEnBilletes20Mil());
            pst3.setInt(20, arq.getMontoEnBilletes10Mil());
            pst3.setInt(21, arq.getMontoEnBilletes5Mil());
            pst3.setInt(22, arq.getMontoEnBilletes2Mil());
            pst3.setInt(23, arq.getMontoEnBilletesOMonedasMil());
            pst3.setInt(24, arq.getMontoEnMonedasDe500());
            pst3.setInt(25, arq.getMontoEnMonedasDe200());
            pst3.setInt(26, arq.getMontoEnMonedasDe100());
            pst3.setInt(27, arq.getMontoEnMonedasDe50());
            pst3.setString(28, arq.getMontoTotalCaja());
            pst3.setString(29, arq.getDiferenciaTotal());            

            pst3.executeUpdate();
            cn3.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar arqueo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un arqueo de caja en el sistema: " + e);
        } 
    }
    
    //Metodo que genera los codigos de los arqueos
    public String codigosArqueo(){
        Random miAleatorio = new Random();
        int N = miAleatorio.nextInt(1000000000);
        String n = Integer.toString(N);
        return n;
    }
    
    //Metodo que genera la fecha en la quese realizo el arqueo de caja       
    public String fecha_Arqueo(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }
    
    //Metodo que imprime el ticket de cuando se realizael arqueo de caja al inicio de turno
    public void generarTicketArqueoDeCaja(String codigoDeArqueo){
               
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("codigo", codigoDeArqueo);
           
           JasperReport reporte = null;
           
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Arqueo.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

           //Da una vista previa del ticket
//         JasperViewer view = new JasperViewer(jprint, false);
//         view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//         view.setTitle("Ticket de arqueo de caja");
//         view.setVisible(true);
           
           //Hace que se imprima directamente
           JasperPrintManager.printReport(jprint, false);

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Arqueo de Caja, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de arqueo de caja de inicio de turno: " + ex);
       }
    }
}       
    