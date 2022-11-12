package controlador;

import clasesDeApoyo.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Cierre;
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
import static vista.GestionarFacturas.codigoFactura_update;
import static vista.GestionarFacturas.esFacturaAbierta;
import static vista.GestionarFacturas.hayFacturaVisualizandose;
import static vista.GestionarFacturas.modelo;
import static vista.GestionarFacturas.table_listaFacturas;
import vista.InformacionFacturaFinal;
import vista.InformacionFacturaIngreso;
import static vista.LiquidacionVehiculo.lbl_totalAPagar;


/**
 *
 * @author ALEJO
 */
public class FacturaControlador {
    
    private final Logger log = Logger.getLogger(FacturaControlador.class);
    private URL url = FacturaControlador.class.getResource("Log4j.properties");
    
    Factura facturaConsultada = new Factura(0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0, "", "", "", "", "");
           
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
    public void generarTicketIngreso(String placaVehiculo, boolean vistaPrevia){
        
        try{
            Connection cn3 = Conexion.conectar();
            
            //Agregamos los parametros con los cuales se generara el ticket
            Map parametros = new HashMap ();
            parametros.put("placa_vehiculo", placaVehiculo);
                     
            JasperReport reporte = null;
            //String path = "src\\Reportes\\TicketIngreso.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/TicketIngreso.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametros, cn3);
            
            if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Ticket de ingreso vehiculo " + placaVehiculo);
                view.setVisible(true);
                view.setIconImage(getIconImageTicket());
           
           }else{
               //Hace que se imprima directamente
               JasperPrintManager.printReport(jprint, false);
           }          

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de ingreso, contacte al administrador!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar el ticket de ingreso de un vehiculo: " + ex); 
        }
    }
       
    //Metodo que imprime el ticket de salida de un vehiculo
    public void generarTicketSalida(String placa_tick, boolean vistaPrevia){
        
        try{
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("placa", placa_tick);

           JasperReport reporte = null;
           //String path = "src\\Reportes\\TicketSalida.jasper";

           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/TicketSalida.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
           if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Ticket de salida vehiculo " + placa_tick);
                view.setVisible(true);
                view.setIconImage(getIconImageTicket());
           
           }else{
               //Hace que se imprima directamente
               JasperPrintManager.printReport(jprint, false);
           }

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Salida, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de salida para un vehiculo: " + ex);
       }
    }
    
    //Metodo que trae la img de la vista previa del ticket sea ingreso o salida
    public Image getIconImageTicket() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/bill_icon.png"));
        return retValue;
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
        String totalAPagarNeto_str = agregarFormatoMoneda(Long.toString(totalAPagarNeto));
        
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
        String totalAPagarNeto_str = agregarFormatoMoneda(Long.toString(totalAPagarNeto));
        
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
        
    //Metodo que consulta la información de una factura abierta para la liquidación de un vehiculo o visualizacion de la misma
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
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de una factura abierta!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de una factura abierta: " + e);
        }
        return facturaConsultada;        
    }
    
    //Metodo que consulta la información de una factura cerrada para la liquidación de un vehiculo o su visualizacion
    public Factura consultarInformacionDeUnaFacturaCerrada(String codigoFactura){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from facturas where Codigo = '" + codigoFactura + "' and Estado_fctra='Cerrada'");
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
                facturaConsultada.setFechaDeSalidaVehiculo(rs.getString("Hora_salida"));
                facturaConsultada.setDiferencia(rs.getString("Diferencia"));
                facturaConsultada.setValorAPagar(rs.getString("Valor_a_pagar"));
                facturaConsultada.setEfectivo(rs.getString("Efectivo"));
                facturaConsultada.setCambio(rs.getString("Cambio"));
                
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de una factura cerrada!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de una factura cerrada: " + e);
        }
        return facturaConsultada;        
    }
    
    //Metodo que permite liquidar una factura una vez el vehiculo sale del parqueadero
    public void liquidarFacturaDeVehiculo(String horaSalida, String placa, String valor_a_pagar, String diferencia, String dineroRecibido, String cambio){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Hora_salida ='"+horaSalida+"', Diferencia ='"+diferencia+"', Valor_a_pagar='"+valor_a_pagar+"',Efectivo='"+dineroRecibido+"',Cambio='"+cambio+"'where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

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
    
    //Metodo que consulta los valores a pagar de las facturas pendientes por contabilizar
    public ArrayList obtenerValoresAPagarFacturas(){
        
        ArrayList totalesAPagar = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Valor_a_pagar FROM facturas WHERE Contabilizada = 'No'");
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("Valor_a_pagar");
                String charsToRemove = "$.";
                
                for (char c : charsToRemove.toCharArray()) {
                    valor = valor.replace(String.valueOf(c), "");
                }
                
                valor = valor.replaceAll(",", ".");
                
                if(valor.contains(".")){
                    valor = valor.substring(0,valor.indexOf("."));
                }               
                
                totalesAPagar.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener totales a pagar de facturas, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obenter los totales a pagar de las facturas generadas en el turno. " + e);
        }
        return totalesAPagar;
    }
    
    //Metodo que calcula el valor del producido teniendo en cuenta un arreglo de totales a pagar
    public String calcularProducido(ArrayList valoresAPagar){
        
        String prod = "";
        int resultado = 0;
        
        for(int i = 0; i < valoresAPagar.size(); i++){
            
            String valorPagar = String.valueOf(valoresAPagar.get(i));
            int valorAPagar = Integer.parseInt(valorPagar);
            resultado = resultado + valorAPagar;
        }
        
        prod = Integer.toString(resultado);      
        
        return prod;
    }
    
    //Metodo que carga el contenido de la tabla del Administrador de facturas
    public void cargarTablaAdministradorDeFacturas(){
        
        //Cargamos los datos de la tabla
        try {
            modelo = new DefaultTableModel();
            table_listaFacturas.setModel(modelo);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Codigo, Fecha_Factura,  Facturado_por, Valor_a_pagar from facturas");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Codigo");
            modelo.addColumn("Fecha");
            modelo.addColumn("Usuario");
            modelo.addColumn("Valor ($)");

            int[] anchosTabla = {10,10,5,10};

            for(int x=0; x < cantidadColumnas; x++){
                table_listaFacturas.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {

                        filas[i] = rs.getObject(i + 1); 
                }
                modelo.addRow(filas);
            }
            cn.close();                    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de facturas, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al intentar llenar la tabla de facturas del Administrador de facturas. " + e);
            }
            
        //Agregamos la funcion de ver informacion de factura al hacer click sobre el registro de la tabla
        table_listaFacturas.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = table_listaFacturas.rowAtPoint(e.getPoint());
            int columna_point = 0;

            if(fila_point > -1){
                codigoFactura_update = (String) modelo.getValueAt(fila_point, columna_point);
                boolean estFctra = validarEstadoFactura(codigoFactura_update);
                
                if(estFctra == true){
                    esFacturaAbierta = true;
                    generarInformacionDeFacturaEnFrame();
                    
                }else{
                    esFacturaAbierta = false;
                    generarInformacionDeFacturaEnFrame();                    
                }
            }
        }
    });
    }    

    //Metodo que genera la informacion de una factura en el jframe, sea abierta o cerrada
    public void generarInformacionDeFacturaEnFrame(){

        if(hayFacturaVisualizandose == true){
            JOptionPane.showMessageDialog(null,"No permitido.");
        }else{
            hayFacturaVisualizandose = true;
            
            if(esFacturaAbierta == true){
                new InformacionFacturaIngreso().setVisible(true);
            }else{
               new InformacionFacturaFinal().setVisible(true); 
            } 
        }    
    } 
    
    //Metodo que busca una factura teniendo en cuenta su usuario
    public void busquedaFacturaPorUsuario(String texto){
        
        try{
            String [] titulos = {"Codigo", "Fecha", "Usuario", "Valor ($)"};
            String filtro = "%"+texto+"%";
            String SQL = "select Codigo, Fecha_factura,  Facturado_por, Valor_a_pagar from facturas where Facturado_por like "+'"'+filtro+'"';
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[4];
            
            while(rs6.next()){
                fila[0] = rs6.getString("Codigo");
                fila[1] = rs6.getString("Fecha_factura");
                fila[2] = rs6.getString("Facturado_por");
                fila[3] = rs6.getString("Valor_a_pagar");
                modelo.addRow(fila);
            }
            table_listaFacturas.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de factura por usuario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar una factura por medio de su usuario. " + ex);
        }
    }
    
    //Metodo que busca una factura teniendo en cuenta su codigo
    public void busquedaFacturaPorCodigo(String texto){
       
        try{
            String [] titulos = {"Codigo", "Fecha", "Usuario", "Valor ($)"};
            String filtro = "%"+texto+"%";
            String SQL = "select Codigo, Fecha_factura,  Facturado_por, Valor_a_pagar from facturas where Codigo like "+'"'+filtro+'"';
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[4];
            
            while(rs6.next()){               
                fila[0]= rs6.getString("Codigo");
                fila[1]=rs6.getString("Fecha_factura");
                fila[2]=rs6.getString("Facturado_por");
                fila[3]= rs6.getString("Valor_a_pagar");
                modelo.addRow(fila);
            }
            table_listaFacturas.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de factura!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar una factura por medio de su codigo. " + ex);
        }                
    } 
    
    //Metodo que valida el estado de la factura
    public boolean validarEstadoFactura(String codigo){
        
        boolean estadoFactura = false;
            
        try {
            Connection cn7 = Conexion.conectar();
            PreparedStatement pst7;
            pst7 = cn7.prepareStatement(
                        "select Estado_fctra from facturas where Codigo = '"+codigo+"'");

            ResultSet rs7 = pst7.executeQuery();
           
            if (rs7.next()) {
                String valor = rs7.getString("Estado_fctra");
                
                if(valor.equals("Abierta")){
                    estadoFactura = true;
                }else{
                    estadoFactura = false;
                }
            }       
        }catch (SQLException e) {
           JOptionPane.showMessageDialog(null, "¡¡ERROR al validar estado de Factura!!, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al intentar validar el estado de una factura. " + e);
        }
        return estadoFactura;
    }
    
    //Metodo que averigua si la factura indicada fue contabilizada
    public boolean verificarSifacturaFueContabilizada(String cod_factura){
        
        boolean facturaContabilizada= true;
        
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "SELECT Contabilizada from facturas where Codigo='"+ cod_factura + "' AND Estado_fctra = 'Cerrada' AND Contabilizada='Si'");

            ResultSet rs3 = pst3.executeQuery();

            if(rs3.next()){
                String valor = rs3.getString("Contabilizada");
                
                if(valor.equals("Si")){
                    facturaContabilizada = true;
                }else{
                    facturaContabilizada = false;
                }   
            }
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al validar contabilidad de factura!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar si una factura ya esta contabilizada. " + e);
        }
        return facturaContabilizada;
    }
    
    //Metodo que borra una factura
    public void borrarFactura(String cod_factura){

        //Eliminamos la factura
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("delete from facturas where Codigo= '"+ cod_factura + "'");
            pst.executeUpdate(); 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar una factura. " + e);
        }
    }
    
    //Metodo que descuenta la factura del cierre donde esta involucrada
    public void descontarFacturaDeUnCierre(String codigoFactura){
        
        int idCierreImplicado = obtenerIdCierreConCodigoFactura(codigoFactura);
        
        //Creamos un objeto de tipo CierreControlador
        CierreControlador cierreControla = new CierreControlador();
        
        String totalAPagarDeFactura = obtenerTotalAPagarDeUnaFacturaMedianteElCodigo(codigoFactura);
        Cierre cierreInvolucrado = cierreControla.consultarInformacionDeUnCierre(idCierreImplicado, "");
                  
        //Hacemos el calculo correspondiente (restamos el valor de la factura al total esperado del cierre y restamos 1a factura al total de facturas)
        String totalEsperado = cierreInvolucrado.getTotal_esperado();
        String cantFacturas = cierreInvolucrado.getNo_facturas();
        String dineroEnCaja = cierreInvolucrado.getDinero_caja();
        String producido = cierreInvolucrado.getProducido();
        String dineroAConsignar = cierreInvolucrado.getDineroAConsignar();
        
        //Quitamos el formato moneda a las cifras del cierre para su modificacion
        totalEsperado = quitarFormatoMoneda(totalEsperado);
        dineroEnCaja = quitarFormatoMoneda(dineroEnCaja);
        totalAPagarDeFactura = quitarFormatoMoneda(totalAPagarDeFactura);
        producido = quitarFormatoMoneda(producido);
        dineroAConsignar = quitarFormatoMoneda(dineroAConsignar);
       
        int totalEsp_int = Integer.parseInt(totalEsperado);
        int valorAPagarFactura = Integer.parseInt(totalAPagarDeFactura);
        int dineroEnCaja_int = Integer.parseInt(dineroEnCaja);
        int noFacturas_impl = Integer.parseInt(cantFacturas);
        int producido_int = Integer.parseInt(producido);
        int dineroAConsignar_int = Integer.parseInt(dineroAConsignar);
        
        //Descontamos el valor a pagar de la factura del dinero esperado, al dinero total existente en caja, al producido y al dinero a consignar
        int nuevoTotalEsperado = totalEsp_int - valorAPagarFactura;
        int nuevoDineroEnCaja = dineroEnCaja_int - valorAPagarFactura;
        int nuevoProducido = producido_int - valorAPagarFactura;
        int nuevoDineroAConsignar = dineroAConsignar_int - valorAPagarFactura;
        
        //Calculamos la nueva diferencia y restamos una factura al cierre
        int nuevaDiferencia = nuevoTotalEsperado - nuevoDineroEnCaja;
        int nuevaCantFacturas = noFacturas_impl - 1;
                        
        //Convertimos a string los nuevos resultados para el cierre
        totalEsperado = Integer.toString(nuevoTotalEsperado);
        dineroEnCaja = Integer.toString(nuevoDineroEnCaja);
        String diferencia = Integer.toString(nuevaDiferencia);
        cantFacturas = Integer.toString(nuevaCantFacturas);
        producido = Integer.toString(nuevoProducido);
        dineroAConsignar = Integer.toString(nuevoDineroAConsignar);
        
        //Aplicamos el formato moneda a los nuevos resultados para el cierre
        totalEsperado = agregarFormatoMoneda(totalEsperado);
        dineroEnCaja = agregarFormatoMoneda(dineroEnCaja);
        diferencia = agregarFormatoMoneda(diferencia);
        producido = agregarFormatoMoneda(producido);
        dineroAConsignar = agregarFormatoMoneda(dineroAConsignar);
        
        //Actualizamos el cierre 
        cierreControla.actualizarMontosFinalesDeUnCierre(idCierreImplicado, producido, totalEsperado, dineroEnCaja, diferencia, dineroAConsignar, cantFacturas);
                                        
    }
    
    //Metodo que total a pagar de unafactura usando su codigo
    public String obtenerTotalAPagarDeUnaFacturaMedianteElCodigo(String codigoDeFactura){
        
        String valorPag = "";
        try {
            Connection cn8 = Conexion.conectar();
            PreparedStatement pst8 = cn8.prepareStatement(
                "SELECT Valor_a_pagar FROM facturas WHERE Codigo = '"+codigoDeFactura+"'");
            ResultSet rs8 = pst8.executeQuery();

            if(rs8.next()){
                valorPag = rs8.getString("Valor_a_pagar");
            }
            cn8.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar total a pagar!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar consultar el total a pagar de una factura usando un codigo de factura: " + e);
        }
        return valorPag;
    }
    
    //Metodo que obtiene el id del cierre mediante el codigo de una factura que este tenga
    public int obtenerIdCierreConCodigoFactura (String codigoDeFactura){
        
        int idDelCierre = 0;
        try {
            Connection cn8 = Conexion.conectar();
            PreparedStatement pst8 = cn8.prepareStatement(
                "SELECT Id_cierre FROM facturas WHERE Codigo = '"+codigoDeFactura+"'");
            ResultSet rs8 = pst8.executeQuery();

            if(rs8.next()){
                idDelCierre = rs8.getInt("Id_cierre");
            }
            cn8.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al seleccionar cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar consultar el id de un cierre usando un codigo de factura: " + e);
        }
        return idDelCierre;
    }
    
    public String quitarFormatoMoneda(String monto){
        
        String montoConvertido = "";
        
        //Le quitamos el formato de moneda al monto dado
        String charsToRemove = "$.";
                
        for (char c : charsToRemove.toCharArray()) {
            monto = monto.replace(String.valueOf(c), "");
        }
        monto = monto.replaceAll(",", ".");

        if(monto.contains(".")){
            monto = monto.substring(0,monto.indexOf("."));
        }
        
        montoConvertido = monto;
        
        return montoConvertido;
    }
    
    //Arroja en formato de moneda la cantidad de dinero que se le indique
    public String agregarFormatoMoneda(String montoAConvertir){
        
        //Damos formato de moneda al monto
        Double valorBase = new Double(montoAConvertir);
        Locale region = Locale.getDefault();
        //Currency moneda = Currency.getInstance(region);
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(region);
        
        return formatoMoneda.format(valorBase);
    }
}
