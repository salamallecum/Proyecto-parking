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


/**
 *
 * @author ALEJO
 */
public class FacturaControlador {
    
    Factura facturaConsultada = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0);
    
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
            log.fatal("ERROR - Se ha producido un error al intentar actualizar la factura abierta de un vehiculo: " + e);
            
        } 
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
//            JasperViewer view = new JasperViewer(jprint, false);
//            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//            view.setVisible(true);
//            view.setTitle("Ticket de ingreso vehiculo " + placaVehiculo);
            
            
            //Hace que se imprima directamente
            JasperPrintManager.printReport(jprint, false);
            

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de ingreso, contacte al administrador!!");
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
    
    //Metodo que genera la fecha de salida del vehiculo       
    public String fecha_Salidavehiculo(){
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
                "insert into facturas (Id_factura, Codigo, Id_cierre, Fecha_factura, Placa, Propietario, Tipo_vehiculo, No_parqueadero, "
                        + "Facturado_por, Estado_fctra, Contabilizada, Id_convenio, Id_tarifa, Hora_ingreso) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst2.setInt(1, nvaFactura.getId());
            pst2.setString(2, nvaFactura.getCodigo());
            pst2.setInt(3, 1);
            pst2.setString(4, nvaFactura.getFechaDeFactura());
            pst2.setString(5, nvaFactura.getPlaca());
            pst2.setString(6, nvaFactura.getPropietario());
            pst2.setString(7, nvaFactura.getClaseDeVehiculo());
            pst2.setInt(8, nvaFactura.getId_parqueadero());
            pst2.setString(9, nvaFactura.getFacturadoPor());
            pst2.setString(10, nvaFactura.getEstadoDeFactura());
            pst2.setString(11, nvaFactura.getEstaContabilizada()); 
            pst2.setInt(12, nvaFactura.getId_convenio());
            pst2.setInt(13, nvaFactura.getId_tarifa());
            pst2.setString(14, nvaFactura.getFechaDeIngresoVehiculo());             

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
                LiquidacionVehiculo.lbl_horaSalida.setText(fecha_Salidavehiculo());                    
            }  
            cn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar liquidación de vehiculo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar generar liquidación de un vehiculo en el sistema: " + e);
        }
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
    
    //Metodo que consulta la información de una factura abierta para la liquidación de un vehiculo
    public Factura consultarInformacionDeUnaFacturaAbierta(String placaDelVehiculo){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from facturas where Placa = '" + placaDelVehiculo + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                facturaConsultada.setId(rs.getInt("Id_factura"));
                facturaConsultada.setCodigo(rs.getString("Codigo"));
                facturaConsultada.setId_cierre(rs.getInt("Id_cierre"));
                facturaConsultada.setFechaDeFactura(rs.getString("Fecha_factura"));
                facturaConsultada.setPlaca(rs.getString("Placa"));
                facturaConsultada.setPropietario(rs.getString("Propietario"));
                facturaConsultada.setClaseDeVehiculo(rs.getString("Tipo_vehiculo"));
                facturaConsultada.setId_parqueadero(rs.getInt("No_parqueadero"));
                facturaConsultada.setFacturadoPor(rs.getString("Facturado_por"));
                facturaConsultada.setId_convenio(rs.getInt("Id_convenio"));
                facturaConsultada.setId_tarifa(rs.getInt("Id_tarifa")); 
                facturaConsultada.setFechaDeIngresoVehiculo(rs.getString("Hora_ingreso"));
                
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de la liquidación del vehiculo seleccionado!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de una factura abierta para su liquidación: " + e);
        }
        return facturaConsultada;        
    }
    
    
    //Metodo que calcula la tarifa a pagar de parqueadero
//    public static void calcularTarifa(String variableConvenio, String variableTarifa){
//        
//        if(variableConvenio.equals("NINGUNO") && variableTarifa.equals("NINGUNA")){
//            lbl_totalAPagar.setText("0");
//            txt_dineroRecibido.setEnabled(false);
//            txt_dineroRecibido.setText("0");
//            btn_calcular.setEnabled(false);
//            lbl_dineroCambio.setText("0");
//        }else if(!variableConvenio.equals("NINGUNO") && variableTarifa.equals("NINGUNA")){
//            lbl_totalAPagar.setText("0");
//            txt_dineroRecibido.setEnabled(false);
//            txt_dineroRecibido.setText("0");
//            btn_calcular.setEnabled(false);
//            lbl_dineroCambio.setText("0");
//        }else if(variableConvenio.equals("NINGUNO") && !variableTarifa.equals("NINGUNA")){
            
//            //Hace la consulta de la tarifa asignada al vehiculo
//            try {
//                Connection cn2 = Conexion.conectar();
//                PreparedStatement pst2 = cn2.prepareStatement(
//                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
//                ResultSet rs2 = pst2.executeQuery();
//
//                if(rs2.next()){
//                    String monto = rs2.getString("Monto");
//                    montoDelaTarifa = Double.parseDouble(monto);
//                }
//                cn2.close();
//            } catch (SQLException e) {
//               JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
//            }
//            
//            try{
//                //Calculamos el valor a pagar por el vehiculo
//                double valorAPagar = 0.0;
//                long valorPagarDefinitivo;
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar cal  = Calendar.getInstance();
//                Date date = cal.getTime();
//                String fechaHora = dateFormat.format(date);
//
//                //Obtenemos la hora de ingreso y la convertimos a Date
//                String hora_entradaVehiculo = lbl_horaIngreso.getText();
//
//                hora_ingr = dateFormat.parse(hora_entradaVehiculo);
//
//                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
//                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;
//
//                //Aplicamos la tarifa al tiempo estimado
//                valorAPagar = minutosACobrar * montoDelaTarifa;
//                
//                
//                //Redondeamos el valor a pagar
//                valorPagarDefinitivo = Math.round(valorAPagar);
//                
//                String valor_Pagar = Long.toString(valorPagarDefinitivo);
//                lbl_totalAPagar.setText(valor_Pagar);
//                    
//                    
//            }catch (ParseException ex) {  
//                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
//        }else if(!variableConvenio.equals("NINGUNO") && !variableTarifa.equals("NINGUNA")){
            //En este caso, asi tenga un convenio aignado, si la tarifa no es ninguna, predominará la tarifa
            //Hace la consulta de la tarifa asignada al vehiculo
             //Hace la consulta de la tarifa asignada al vehiculo
//            try {
//                Connection cn2 = Conexion.conectar();
//                PreparedStatement pst2 = cn2.prepareStatement(
//                    "SELECT Monto from tarifas where Nombre_tarifa ='" + variableTarifa + "'");
//                ResultSet rs2 = pst2.executeQuery();
//
//                if(rs2.next()){
//                    String monto = rs2.getString("Monto");
//                    montoDelaTarifa = Integer.parseInt(monto);
//                }
//                cn2.close();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar tarifa de vehiculo!!, contacte al administrador.");
//            }
//            
//            try{
//                //Calculamos el valor a pagar por el vehiculo
//                double valorAPagar = 0.0;
//                long valorPagarDefinitivo;
//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar cal  = Calendar.getInstance();
//                Date date = cal.getTime();
//                String fechaHora = dateFormat.format(date);
//
//                //Obtenemos la hora de ingreso y la convertimos a Date
//                String hora_entradaVehiculo = lbl_horaIngreso.getText();
//
//                hora_ingr = dateFormat.parse(hora_entradaVehiculo);
//
//                //Calculamos la diferencia de tiempos (tiempo de ingreso vs tiempo de salida)
//                int minutosACobrar = (int) (date.getTime()-hora_ingr.getTime())/60000;
//
//                //Aplicamos la tarifa al tiempo estimado
//                valorAPagar = minutosACobrar * montoDelaTarifa;
//                System.out.println("Valor original: " + valorAPagar);
//                
//                //Redondeamos el valor a pagar
//                valorPagarDefinitivo = Math.round(valorAPagar);
//                
//                String valor_Pagar = Long.toString(valorPagarDefinitivo);
//                lbl_totalAPagar.setText(valor_Pagar);
//                    
//                    
//            }catch (ParseException ex) {  
//                //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }    



}
