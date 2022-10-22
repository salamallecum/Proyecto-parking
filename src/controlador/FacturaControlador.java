package controlador;

import clasesDeApoyo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Factura;
import modelo.Tarifa;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.LiquidacionVehiculo;
import static vista.LiquidacionVehiculo.lbl_totalAPagar;


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
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setTitle("Ticket de ingreso vehiculo " + placaVehiculo);
            
            
            //Hace que se imprima directamente
            //JasperPrintManager.printReport(jprint, false);
            

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de ingreso, contacte al administrador!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar el ticket de ingreso de un vehiculo: " + ex); 
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
           JasperViewer view = new JasperViewer(jprint, false);
           view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           view.setTitle("Ticket de salida vehiculo " + placa_tick);
           view.setVisible(true);
          
           //Hace que se imprima directamente
           //JasperPrintManager.printReport(jprint, false);

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Salida, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de salida para un vehiculo: " + ex);
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
    
    //Metodo que se encarga de convertir las fechas de ingreso y salida de vehiculos de String a Date
    public Date convertidorDeFechasADate(String fechaAConvertir){
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaConvertida = new Date();
        
        try {
            fechaConvertida = dateFormat.parse(fechaAConvertir);

        } catch (ParseException ex) {
            log.fatal("ERROR - Se ha producido un error al intentar convertir una fecha de String a Date: " + ex); 
        }
        
        return fechaConvertida;
        
    }
    
    //Metodo que calcula la diferencia que existe entre dos fechas en milisegundos
    public long calcularDiferenciaDeFechasEnMilisegundos(Calendar fechaDeIngreso, Date fechaDeSalida){
        long resultado = Math.abs(fechaDeIngreso.getTimeInMillis() - fechaDeSalida.getTime());
        return resultado;        
    }
    
    //Metodo que le aplica el descuento ala diferencia de tiempo obtenida y hace el calculo correspondiente 
    public long calcularDiferenciaConDescuento(long dif, long desc) {
        long diferenciaFinal = dif - desc;
        return diferenciaFinal;
    }   
    
    //Metodo que calcula el monto a pagar
    public String calcularPago(long mon, long dif){
        long total_a_pagar = mon * dif;      
        String total_pagar_str = Long.toString(total_a_pagar);
        return total_pagar_str;
    }
    
    public Calendar convertidorDeFechasDeDateACalendar(Date fechaAConvertir){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaAConvertir);
        return cal;
    } 
    
    //Metodo que calcula el monto a pagar con minutos adicionales (PARA EL COBRO POR HORAS)
    public String calcularPagoTeniendoEnCuentaMinutosUtilizados(long mon, long dif, Tarifa tarifaInvolucrada, long diferenciaEnMilisegundos){
        
        //Hacemos el calculo de pago teniendo en cuenta la diferencia en horas
        long total_a_pagar_en_horas = mon * dif;
        String diferenciaAdicional = "";
               
        //Leemos la tarifa del campo txt de minutos adicionales
        String tarifaMinAdicionales_str = tarifaInvolucrada.getMontoTiempoAdicional();
        long tarifaMinAdicionales = Long.parseLong(tarifaMinAdicionales_str);
                
        //Obtenemos la diferencia en minutos total
        long diferenciaTotalEnMinutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaEnMilisegundos);
        long tiempoADescontar = 60 * dif;
        long minutosExtra = diferenciaTotalEnMinutos - tiempoADescontar;
        long totalAPagarMinutosExtra = minutosExtra * tarifaMinAdicionales;
        
        //Calculamos el total a pagar neto (en horas + minutos adicionales)
        long totalAPagarNeto = total_a_pagar_en_horas + totalAPagarMinutosExtra;
        
        //Convertimos los datos para mostrar en pantalla
        String minutosExtra_str = Long.toString(minutosExtra);
        String totalAPagarNeto_str = darFormatoMoneda(Long.toString(totalAPagarNeto));
        
        //Mostramos los datos en pantalla
        lbl_totalAPagar.setText(totalAPagarNeto_str);
        lbl_totalAPagar.setVisible(true);
        
        diferenciaAdicional = " y " + minutosExtra_str + " minutos";
        
        return diferenciaAdicional;
            
    }
    
    //Metodo que calcula el monto a pagar con minutos adicionales (PARA EL COBRO POR DIAS)
    public String calcularPagoTeniendoEnCuentaHorasUtilizadas(long mon, long dif, Tarifa tarifaInvolucrada, long diferenciaEnMilisegundos) {
        
        //Hacemos el calculo de pago teniendo en cuenta la diferencia en dias
        long total_a_pagar_en_dias = mon * dif;
        String diferenciaAdicional = "";
               
        //Leemos la tarifa del campo txt de horas adicionales
        String tarifaHorasAdicionales_str = tarifaInvolucrada.getMontoTiempoAdicional();;
        long tarifaHorasAdicionales = Long.parseLong(tarifaHorasAdicionales_str);
                
        //Obtenemos la diferencia en horas total
        long diferenciaTotalEnHoras = TimeUnit.MILLISECONDS.toHours(diferenciaEnMilisegundos);
        long tiempoADescontar = 24 * dif;
        long horasExtra = diferenciaTotalEnHoras - tiempoADescontar;
        long totalAPagarHorasExtra = horasExtra * tarifaHorasAdicionales;
        
        //Calculamos el total a pagar neto (en dias + horas adicionales)
        long totalAPagarNeto = total_a_pagar_en_dias + totalAPagarHorasExtra;
        
        //Convertimos los datos para mostrar en pantalla
        String horasExtra_str = Long.toString(horasExtra);
        String totalAPagarNeto_str = darFormatoMoneda(Long.toString(totalAPagarNeto));
        
        //Mostramos los datos en pantalla
        lbl_totalAPagar.setText(totalAPagarNeto_str);
        lbl_totalAPagar.setVisible(true);
        
        diferenciaAdicional = " y " + horasExtra_str + " horas";
        
        return diferenciaAdicional;
    
    }   
    
    //Metodo que crea facturas que se cobran con normalidad
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
            JOptionPane.showMessageDialog(null, "¡¡ERROR al ingresar factura en el sistema!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear una factura en el sistema: " + e);
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
                "select * from facturas where Placa = '" + placaDelVehiculo + "' and Estado_fctra='Abierta'");
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
    
    //Metodo que permite liquidar una factura una vez el vehiculo sale del parqueadero
    public void liquidarFacturaDeVehiculo(String horaSalida, String placa, String valor_a_pagar, String dineroRecibido, String cambio){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Hora_salida ='"+horaSalida+"',Valor_a_pagar='"+valor_a_pagar+"',Efectivo='"+dineroRecibido+"',Cambio='"+cambio+"'where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

            pst.executeUpdate();
            cn.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al ingresar liquidación de vehiculo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar registrar la liquidación de un vehiculo: " + e);
        }
    }
    
    //Metodo que permite identificar si el vehiculo en cuestion esta involucrado en alguna factura de primer ingreso
    public boolean consultarSiVehiculoTieneFacturaDePrimerIngreso(String placa){

        boolean vehiculoTieneFacturaDePrimerIngreso = false;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select * from facturas where Placa = '" + placa + "' and Hora_ingreso = '1990-01-01 23:59:00'");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                vehiculoTieneFacturaDePrimerIngreso = true;
            }else{
                vehiculoTieneFacturaDePrimerIngreso = false;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "ERROR al revisar facturación de registro o edición de vehiculo, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al intentar revisar facturación de registro o edicion de un vehiculo: " + ex);
        }
        
        return vehiculoTieneFacturaDePrimerIngreso;
    }
    
    //Metodo para eliminar la factura de primer ingreso generada para un vehiculo que es registrado en el sistema
    public void eliminarFacturaDePrimerIngresoDeUnVehiculo(String placa){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from facturas where Placa=? and Hora_ingreso='1990-01-01 23:59:00'");
            ps1.setString(1, placa);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar factura de primer ingreso!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar la factura de primer ingreso del vehiculo: "+ placa + e);
        }   
    }
    
    //Metodo que permite actualizar las facturas que se encuentren abiertas con la información actualizada de un vehiculo    
    public void actualizarFactura1erIngreso(String placa, Factura facturaEdit){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+placa+"', Propietario='"+facturaEdit.getPropietario()+"', Tipo_vehiculo='"+facturaEdit.getClaseDeVehiculo()+"', No_parqueadero='"+facturaEdit.getId_parqueadero()+"', Id_convenio='"+facturaEdit.getId_convenio()+"', Id_tarifa='"+facturaEdit.getId_tarifa()+"' where Placa ='"+placa+"' and Estado_fctra='Abierta' and Hora_ingreso = null");

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar facturas, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar la factura abierta de un vehiculo: " + e);  
        } 
    }
    
    //Metodo que cuenta la cantidad de facturas generadas y por contabilizar
    public String contarFacturas(){
        
        String cantidadFacturas = "";
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "select count(*) from facturas where Estado_fctra='Cerrada' AND Contabilizada='No'");
            ResultSet rs3 = pst3.executeQuery();

            if(rs3.next()){
                int cant_facturas = rs3.getInt("count(*)");
                cantidadFacturas = Integer.toString(cant_facturas);
            }
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al contar facturas generadas en el turno, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al contar las facturas generadas en el turno: " + e);
        }
        return cantidadFacturas;
    }
    
    //Metodo que calcula el producido en caja a lo largo del turno del parqueadero
    public String calcularProducido(){
        
        int producido = 0;
        String producido_str = "";
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT SUM(Valor_a_pagar) FROM facturas WHERE Contabilizada = 'No'");
            ResultSet rs2 = pst2.executeQuery();

            if(rs2.next()){
                producido = rs2.getInt("SUM(Valor_a_pagar)");
                producido_str = Integer.toString(producido);
            }
            cn2.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al calcular producido del turno, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar calcular el dinero producido a lo largo del turno: " + e);
        }
        return producido_str;
    }

}
