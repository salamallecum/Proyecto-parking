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
import java.util.Random;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Factura;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.LiquidacionVehiculo;
import static vista.LiquidacionVehiculo.fecha_movVehiculo;
import vista.OtrosParametros;

/**
 *
 * @author ALEJO
 */
public class FacturaControlador {
    
    private final Logger log = Logger.getLogger(FacturaControlador.class);
    private URL url = FacturaControlador.class.getResource("Log4j.properties");
       
    //Constructor
    public FacturaControlador() {}
    
    //Metodo que permite actualizar las facturas que se encuentren abiertas con la información actualizada de un vehiculo    
    public void actualizarFacturas(String placa, String dueño, String tipVehi, int IdParq, int IdConv, int IdTarif){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+placa+"', Propietario='"+dueño+"', Tipo_vehiculo='"+tipVehi+"', No_parqueadero='"+IdParq+"', Id_convenio='"+IdConv+"', Id_tarifa='"+IdTarif+"' where Placa ='"+placa+"' and Estado_fctra='Abierta'");

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar facturas, contacte al administrador.");
            
        } 
    }
    
    //Metodo que genera los codigos de las facturas
    public int codigosFactura(){
        Random miAleatorio = new Random();
        int N = miAleatorio.nextInt(1000000000);
        return N;
    }
    
    //Metodo que genera el ticket de ingreso de un vehiculo
    public void generarTicketIngreso(String placaVehiculo){
        
        try{
            Connection cn3 = Conexion.conectar();
            
            //Agregamos los parametros con los cuales se generara el ticket
            Map parametros = new HashMap ();
            parametros.put("placa_vehiculo", placaVehiculo);
                     
            JasperReport reporte = null;
            //String path = "src\\Reportes\\TicketIngreso.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/TicketIngreso.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametros, cn3);

            //Da una vista previa del ticket
            /*
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setTitle("Ticket de ingreso vehiculo " + placaVehiculo);
            */
            
            //Hace que se imprima directamente
            JasperPrintManager.printReport(jprint, false);
            

        }catch(JRException ex){
            //Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de ingreso, contacte aladministrador!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar el ticket de ingreso de un vehiculo: " + ex); 
        }
    }
    
    //Metodo que genera la fecha en que fue generada la factura
    public String fecha_de_factura(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fecha_factura = sdf.format(date);
        return fecha_factura;
    }
    
    //Metodo que genera la fecha de entrada del vehiculo       
    public String fecha_Ingresovehiculo(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }
    
    //Metodo que crea facturas
    public void crearFactura (Factura nvaFactura){
        
        //Inserta el registro en la base de datos
        try {               
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "insert into facturas values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst2.setInt(1, nvaFactura.getId());
            pst2.setDouble(2, nvaFactura.getCodigo());
            pst2.setString(3, nvaFactura.getFechaDeFactura());
            pst2.setString(4, nvaFactura.getPlaca());
            pst2.setString(5, nvaFactura.getPropietario());
            pst2.setString(6, nvaFactura.getClaseDeVehiculo());
            pst2.setInt(7, nvaFactura.getId_parqueadero());
            pst2.setString(8, nvaFactura.getFacturadoPor());
            pst2.setString(9, nvaFactura.getEstadoDeFactura());
            pst2.setString(10, nvaFactura.getEstaContabilizada()); 
            pst2.setInt(11, nvaFactura.getId_convenio());
            pst2.setInt(12, nvaFactura.getId_tarifa());
            pst2.setString(13, nvaFactura.getFechaDeIngresoVehiculo()); 
            pst2.setString(14, "");
            pst2.setInt(15, 0);
            pst2.setInt(16, 0);
            pst2.setInt(17, 0);
            pst2.setInt(18, 1);

            pst2.executeUpdate();
            cn2.close();
        
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al ingresar vehiculo desconocido!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear una factura de un vehiculo desconocido en el sistema: " + e);
        }
    }

    //Metodo que genera la liquidación de un vehiculo en el jframe de liquidacion de vehiculo
    public void liquidarVehiculo(String placa){
        
        //Hace la consulta del codigo,tipo de vehiculo , Facturado por, a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "SELECT Fac.Id_factura, Fac.Codigo, Fac.Propietario, Fac.Tipo_vehiculo, Fac.Facturado_por, Fac.Estado_fctra, Fac.Hora_ingreso, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa from facturas Fac INNER JOIN parqueaderos Parq ON Fac.No_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Fac.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Fac.Id_tarifa = Tar.Id_tarifa AND Fac.Placa = '" + placa + "' AND Fac.Estado_fctra = 'Abierta'");

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                LiquidacionVehiculo.ID = rs.getInt("Fac.Id_factura");
                LiquidacionVehiculo.lbl_codigo.setText(rs.getString("Fac.Codigo"));
                LiquidacionVehiculo.lbl_placa.setText(placa);
                LiquidacionVehiculo.lbl_propietario.setText(rs.getString("Fac.Propietario"));
                LiquidacionVehiculo.lbl_tipoVehiculo.setText(rs.getString("Fac.Tipo_vehiculo"));
                LiquidacionVehiculo.lbl_noParqueadero.setText(rs.getString("Parq.Nombre_parqueadero"));
                LiquidacionVehiculo.lbl_facturadoPor.setText(rs.getString("Fac.Facturado_por"));
                LiquidacionVehiculo.lbl_convenio.setText(rs.getString("Conv.Nombre_convenio"));
                LiquidacionVehiculo.lbl_tarifa.setText(rs.getString("Tar.Nombre_tarifa"));
                LiquidacionVehiculo.lbl_horaIngreso.setText(rs.getString("Fac.Hora_ingreso"));
                LiquidacionVehiculo.lbl_horaSalida.setText(FacturaControlador.fecha_Salidavehiculo());                    
            }  
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar liquidación de vehiculo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar generar liquidación de un vehiculo en el sistema: " + e);
        }
    }
    
    //Metodo que genera la fecha de salida del vehiculo       
    public static String fecha_Salidavehiculo(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }
    
    //Metodo que imprime el ticket de salida de un vehiculo
    public void generarTicketSalida(String placa_tick){
        
        try{
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("placa", placa_tick);

           JasperReport reporte = null;
           //String path = "src\\Reportes\\TicketSalida.jasper";

           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/TicketSalida.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

           //Da una vista previa del ticket
           /*
           JasperViewer view = new JasperViewer(jprint, false);
           view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           view.setTitle("Ticket de salida vehiculo " + placa_tick);
           view.setVisible(true);
           */

           //Hace que se imprima directamente
           JasperPrintManager.printReport(jprint, false);

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Salida, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de salida para un vehiculo: " + ex);
       }
    }
    
    //Metodo que cierra la factura de un vehiculo unavez este ha salido del parqueadero
    public void cerrarFactura(String placa){
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Estado_fctra = 'Cerrada' where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

            pst.executeUpdate();
            cn.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cerrar factura!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cerrar la factura de un vehiculo: " + e);
        } 
    }
    
    //Metodo que calcula las vueltas que hay que darle al cliente una vez paga su servicio de parqueo
    public String calcularVueltas(String cuentaAPagar, String efectivoRecibido){
        
        String cambio_str = "";
        
        if(efectivoRecibido.equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese el dinero recibido para calcular.");
        }else{            
            int efectivo_int = Integer.parseInt(efectivoRecibido); 
            int cuentaPagar_int = Integer.parseInt(cuentaAPagar);
            int cambio = efectivo_int - cuentaPagar_int;
            cambio_str = String.valueOf(cambio);
        }
        return cambio_str;
    }
    
    //Arroja en formato de moneda la cantidad de dinero que se le indique
    public String darFormatoMoneda(String montoAConvertir){
        
        //Damos formato de moneda al monto
        Double valorBase = new Double(montoAConvertir);
        Locale region = Locale.getDefault();
        //Currency moneda = Currency.getInstance(region);
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(region);
        
        return formatoMoneda.format(valorBase);
    }
    
    



}
