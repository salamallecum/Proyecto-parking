package controlador;

import clasesDeApoyo.Conexion;
import java.awt.Color;
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
import vista.GestionarFacturas;
import static vista.GestionarFacturas.codigoFactura_update;
import static vista.GestionarFacturas.esFacturaAbierta;
import static vista.GestionarFacturas.hayFacturaVisualizandose;
import static vista.GestionarFacturas.lbl_perdidasEnFacturas;
import static vista.GestionarFacturas.modelo;
import static vista.GestionarFacturas.table_listaFacturas;
import vista.InformacionFacturaFinal;
import vista.InformacionFacturaIngreso;


/**
 *
 * @author ALEJO
 */
public class FacturaControlador implements Runnable {
    
    private final Logger log = Logger.getLogger(FacturaControlador.class);
    private URL url = FacturaControlador.class.getResource("Log4j.properties");
    private String valorAPagarPorDiferenciaAdicional = "";   
    public static String rutaImgTickets = "/icons/ImgTickets.jpg";
    public static String rutaImgDocuments = "/icons/ImgReporte.jpg";
    
    Factura facturaConsultada = new Factura(0, "", "", "", "", "", 0, 0, "", "", 0, 0, "", 0, "", "", "", "", "", "");

    //Hilo encargado del cargue de la tabla de opercaion del parqueradero en el panel caja
    public Thread hilo2 = new Thread(this);
    ParqueaderoControlador parqControlador = new ParqueaderoControlador();
    ParametroControlador parametroControla = new ParametroControlador();
    UsuarioControlador usuarioControla = new UsuarioControlador();
    CierreControlador cierreControla = new CierreControlador();
    public static boolean ejecutarHiloOpParq = false; 
    public static boolean esUnNumeroNegativo = false;
    
    public String totalEspGananciasFacturas = "";
    String totalEsperadoGananciasFacturas;
    public String cantidadDeFacturasSistema = "";
    public String totalRealGananciasFacturas = "";
    public String totalPerdidasFacturas_str = "";
    ArrayList valoresAPagarDeFacturas;
    ArrayList dineroDeCambioDeFacturas;
    int totalPerdidasFacturas;
           
    //Constructor
    public FacturaControlador() {}
    
    //Metodo que se encarga de detener el hilo que detiene el hilo que muestra la operacion del parqueadero en la tabla del panel caja
    public void detenerHiloOperacionParqueadero(){
        ejecutarHiloOpParq = false;
    }
    
    //Metodo que se encarga de ejecutar el hilo que detiene el hilo que muestra la operacion del parqueadero en la tabla del panel caja
    public void ejecutarHiloOperacionparqueadero(){
        ejecutarHiloOpParq = true;
        if(ejecutarHiloOpParq == true){
            hilo2.start();
        }
    }
        
    //Metodo que permite actualizar las facturas que se encuentre abierta con la información actualizada de un vehiculo    
    public void actualizarFacturaAbierta(int id, String placa, String dueño, String tipVehi, int IdParq, int IdConv, int IdTarif){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+placa+"', Propietario='"+dueño+"', Tipo_vehiculo='"+tipVehi+"', No_parqueadero='"+IdParq+"', Id_convenio='"+IdConv+"', Id_tarifa='"+IdTarif+"' where Id_factura ='"+id+"' and Estado_fctra='Abierta'");

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
            parametros.put("imagen", this.getClass().getResourceAsStream(rutaImgTickets));
                     
            JasperReport reporte = null;
            //String path = "src\\Reportes\\TicketIngreso.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/TicketIngreso.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametros, cn3);
            
            if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Ticket de ingreso vehiculo " + placaVehiculo);
                view.setVisible(true);
                view.setIconImage(getIconImageTicket());
                
                //Agregamos un evento para cuando el visor del reporte se cierre
                view.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    InformacionFacturaIngreso.btn_imprimirFactura.setEnabled(true);
                }
                });
           
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
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgTickets));
           
           JasperReport reporte = null;
           //String path = "src\\Reportes\\TicketSalida.jasper";

           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/TicketSalida.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
           if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Ticket de salida vehiculo " + placa_tick);
                view.setVisible(true);
                view.setIconImage(getIconImageTicket());
                
                //Agregamos un evento para cuando el visor del reporte se cierre
                view.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    InformacionFacturaFinal.btn_imprimirFactura.setEnabled(true);
                }
                });
           
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
    
    //Metodo que permite aplicar un descuento a la diferencia de fechas en milisegundos
    public long aplicarDescuentoADiferenciaEnMilisegundos(long difFechasEnMilisegundos, long diferenciaAntesDescuento, long diferenciaConDescuento){
        
        long resultado;
        if(diferenciaAntesDescuento <= 0){
            resultado = diferenciaConDescuento * difFechasEnMilisegundos;
        }else{
            resultado = (diferenciaConDescuento * difFechasEnMilisegundos) / diferenciaAntesDescuento;
        }        
        return resultado;
    }
        
    //Metodo que le aplica el descuento ala diferencia de tiempo obtenida y hace el calculo correspondiente 
    public long calcularDiferenciaConDescuento(long dif, long desc) {
        long diferenciaFinal = dif - desc;       
        return diferenciaFinal;
    }   
    
    //Metodo que permite convertir de minutos a milisegundos mediante regalde 3 teniendo en cuenta un caso base
    public long convertirDeMinutosAMilisegundos(long diferenciaBaseEnMinutos, long diferenciaBaseEnMilisegundos, long minutosACalcular){
        long resultado = (diferenciaBaseEnMilisegundos * minutosACalcular) / diferenciaBaseEnMinutos;
        return resultado;
    }
    
    //Metodo que permite convertir de horas a milisegundos mediante regalde 3 teniendo en cuenta un caso base
    public long convertirDeHorasAMilisegundos(long diferenciaBaseEnHoras, long diferenciaBaseEnMilisegundos, long horasACalcular){
        long resultado = (diferenciaBaseEnMilisegundos * horasACalcular) / diferenciaBaseEnHoras;
        return resultado;
    }   
    
    //Metodo que calcula el monto a pagar
    public String calcularPago(long mon, long dif){
        
        //Traemos el porcentaje a cobrar por concepto de impuesto
        String impuesto = parametroControla.consultarValorDeUnParametro("IMPUESTO");
        int porcImp = Integer.parseInt(impuesto);
        
        long montoPorTiempo = mon * dif;
        int montoDeImpuesto = ((int)montoPorTiempo * porcImp)/100;
                        
        //Transformamos el numero para que sea monetariamnte pagable
        String montoDeImpuesto_str = Integer.toString(montoDeImpuesto);
                
        char[] montoImpuestoArreglo = new char[montoDeImpuesto_str.length()];
        
        int ultdigito = montoDeImpuesto_str.length() - 1;
        int penultDigito = montoDeImpuesto_str.length() - 2;
        
        for(int i = 0; i < montoDeImpuesto_str.length(); i++){             
            montoImpuestoArreglo[i] = montoDeImpuesto_str.charAt(i);  
        }
        
        if(montoImpuestoArreglo[ultdigito] != '0'){
            montoImpuestoArreglo[ultdigito] = '0';
        }
        
        if(montoImpuestoArreglo[penultDigito] != '0'){
            
            int digito = Character.getNumericValue(montoImpuestoArreglo[penultDigito]);
            
            if(digito <= 5){
                
                if(digito < 2.5){
                    montoImpuestoArreglo[penultDigito] = '0';
                }else{
                    montoImpuestoArreglo[penultDigito] = '5'; 
                }
                montoDeImpuesto_str = String.valueOf(montoImpuestoArreglo);
                montoDeImpuesto = Integer.parseInt(montoDeImpuesto_str);
            
            }else{
                if(digito == 9){
                    montoImpuestoArreglo[penultDigito] = '0';
                    montoDeImpuesto_str = String.valueOf(montoImpuestoArreglo);
                    montoDeImpuesto = Integer.parseInt(montoDeImpuesto_str);
                    montoDeImpuesto = montoDeImpuesto + 100;
                
                }else{
                    montoImpuestoArreglo[penultDigito] = '0';
                    int digitoAntePen = Character.getNumericValue(montoImpuestoArreglo[penultDigito - 1]); 
                    digitoAntePen = digitoAntePen + 1;
                    montoImpuestoArreglo[penultDigito-1] = Character.forDigit(digitoAntePen, 10);
                    montoDeImpuesto_str = String.valueOf(montoImpuestoArreglo);
                    montoDeImpuesto = Integer.parseInt(montoDeImpuesto_str);
                }
            }                        
        }
                      
        //Sumamos el monto por impuesto al monto por tiempo para sacar el total a pagar
        long totalAPagar = montoPorTiempo + (long)montoDeImpuesto;
                        
        String total_pagar_str = Long.toString(totalAPagar);
        return total_pagar_str;
    }
    
    public Calendar convertidorDeFechasDeDateACalendar(Date fechaAConvertir){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaAConvertir);
        return cal;
    } 
    
    //Metodo que calcula el monto a pagar con minutos adicionales (PARA EL COBRO POR HORAS)
    public String calcularPagoTeniendoEnCuentaMinutosUtilizados(long mon, long dif, Tarifa tarifaInvolucrada, long diferenciaEnMilisegundos){
       
        //Leemos la tarifa del campo de minutos adicionales
        String tarifaMinAdicionales_str = tarifaInvolucrada.getMontoTiempoAdicional();
        String tarifaHoras_str = tarifaInvolucrada.getMontoTarifa();
        long tarifaHoras = Long.parseLong(tarifaHoras_str);
        long tarifaMinAdicionales = Long.parseLong(tarifaMinAdicionales_str);
        long tiempoADescontar;
        long totalAPagarMinutosExtra = 0;
        long totalAPagarHorasAdic = 0;
        long totalAPagarNeto = 0;
        long minAdicReales = 0;
        String totalAPagarNeto_str;
        String minutosExtra_str;
        String diferenciaAdicional = "";
        long minutosExtra;
                    
        //Obtenemos la diferencia en minutos total
        long diferenciaTotalEnMinutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaEnMilisegundos);        
        
        if(dif <= 0){
                                      
            minutosExtra = diferenciaTotalEnMinutos;
            totalAPagarMinutosExtra = minutosExtra * tarifaMinAdicionales;

            //Asignamos el total a pagar neto (solo los minutos adicionales)
            totalAPagarNeto = totalAPagarMinutosExtra;

            //Convertimos los datos para mostrar en pantalla
            minutosExtra_str = Long.toString(diferenciaTotalEnMinutos);
            totalAPagarNeto_str = agregarFormatoMoneda(Long.toString(totalAPagarNeto));

            valorAPagarPorDiferenciaAdicional = totalAPagarNeto_str;
            diferenciaAdicional = " horas y " + minutosExtra_str + " minutos";

            return diferenciaAdicional;
        
        }else{         
          
            //Hacemos el calculo de pago teniendo en cuenta la diferencia en horas inicial
            long total_a_pagar_en_horas = mon * dif;           

            tiempoADescontar = 60 * dif;
            minutosExtra = diferenciaTotalEnMinutos - tiempoADescontar;
            
            //Verificamos si el tiempo extra es superior a 60 minutos, si es asi, lo toma como horas           
            if(minutosExtra >= 60){
                double horasAdicionales = Math.floor(minutosExtra / 60);
                               
                //Hacemos el calculo del total a pagar de las horas adic teniendo en cuenta la tarifa                
                totalAPagarHorasAdic = tarifaHoras * (long)horasAdicionales;
                
                //Sumamos las horas adicionales a la diferencia inicial                
                dif = dif + (long)horasAdicionales;
                
                //Sumamos el total a pagar de las horas adic al total a pagar de horas inicial 
                total_a_pagar_en_horas = total_a_pagar_en_horas + totalAPagarHorasAdic;
                
                //Calculamos los verdaderos minutos extras, para ello restamos los minutos de las horas adicionales obtenidas al total de minutos extra
                minAdicReales = minutosExtra - ((long)horasAdicionales * 60);
                
                //Calculamos la tarifa para los verdaderos minutos extra
                totalAPagarMinutosExtra = minAdicReales * tarifaMinAdicionales;
                
                //Calculamos el total a pagar neto (en horas + minutos adicionales)
                totalAPagarNeto = total_a_pagar_en_horas + totalAPagarMinutosExtra;
                
                //Convertimos los datos para mostrar en pantalla
                minutosExtra_str = Long.toString(minAdicReales);
                totalAPagarNeto_str = agregarFormatoMoneda(Long.toString(totalAPagarNeto));

                valorAPagarPorDiferenciaAdicional = totalAPagarNeto_str;
                diferenciaAdicional = " horas y " + minutosExtra_str + " minutos";
                return diferenciaAdicional; 
                
            }else{
                totalAPagarMinutosExtra = minutosExtra * tarifaMinAdicionales;
               
                //Calculamos el total a pagar neto (en horas + minutos adicionales)
                totalAPagarNeto = total_a_pagar_en_horas + totalAPagarMinutosExtra;

                //Convertimos los datos para mostrar en pantalla
                minutosExtra_str = Long.toString(minutosExtra);
                totalAPagarNeto_str = agregarFormatoMoneda(Long.toString(totalAPagarNeto));

                valorAPagarPorDiferenciaAdicional = totalAPagarNeto_str;
                diferenciaAdicional = " horas y " + minutosExtra_str + " minutos";
                return diferenciaAdicional; 
            }
        }     
    }
    
    //Metodo que retorna el valor a pagar para los metodos con diferencia adicional
    public String obtenervalorAPagarPorDiferenciaAdicional(){
        return valorAPagarPorDiferenciaAdicional;
    }   
    
    //Metodo que se encarga deconvertir un numero de horas a minutos
    public long convertirHorasAMinutos(long numeroDeHoras){
        long resultado = numeroDeHoras * 60;
        return resultado;
    }
    
    //Metodo que se encarga de convertir un numero de dias a horas
    public long convertirDiasAHoras(long numeroDeDias){
        long resultado = numeroDeDias * 24;
        return resultado;
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
            pst2.setInt(9, nvaFactura.getFacturadoPor());
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
            
            if(cuentaAPagar.equals("") || cuentaAPagar.equals("0")){
                cambio_str = efectivoRecibido;
            }else{
                int efectivo_int = Integer.parseInt(efectivoRecibido); 
                int cuentaPagar_int = Integer.parseInt(cuentaAPagar);
                int cambio = cuentaPagar_int - efectivo_int;
                cambio_str = String.valueOf(cambio);
            } 
        }
        return cambio_str;
    }
        
    //Metodo que consulta la información de una factura abierta para la liquidación de un vehiculo 
    public Factura consultarInformacionDeUnaFacturaAbiertaParaSuLiquidacion(String placaDelVehiculo){
               
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
                facturaConsultada.setFacturadoPor(rs.getInt("Facturado_por"));
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
                facturaConsultada.setFacturadoPor(rs.getInt("Facturado_por"));
                facturaConsultada.setId_convenio(rs.getInt("Id_convenio"));
                facturaConsultada.setId_tarifa(rs.getInt("Id_tarifa")); 
                facturaConsultada.setFechaDeIngresoVehiculo(rs.getString("Hora_ingreso"));
                facturaConsultada.setFechaDeSalidaVehiculo(rs.getString("Hora_salida"));
                facturaConsultada.setDiferencia(rs.getString("Diferencia"));
                facturaConsultada.setImpuesto(rs.getString("Impuesto"));
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
    
    //Metodo que consulta la información de una factura cerrada para la liquidación de un vehiculo o su visualizacion
    public Factura consultarInformacionDeUnaFacturaAbiertaParaSuEdicion(String codigoFactura){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from facturas where Codigo = '" + codigoFactura + "' and Estado_fctra='Abierta'");
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
                facturaConsultada.setFacturadoPor(rs.getInt("Facturado_por"));
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
    
    
    //Metodo que permite liquidar una factura una vez el vehiculo sale del parqueadero
    public void liquidarFacturaDeVehiculo(int idUsuario, String horaSalida, String placa, String valor_a_pagar, String diferencia, String dineroRecibido, String cambio, String impuesto){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update facturas set Facturado_por= "+idUsuario+", Hora_salida ='"+horaSalida+"', Diferencia ='"+diferencia+"', Impuesto ='"+impuesto+"', Valor_a_pagar='"+valor_a_pagar+"',Efectivo='"+dineroRecibido+"',Cambio='"+cambio+"'where Placa ='"+placa+"' AND Estado_fctra = 'Abierta'");

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
    
    //Metodo para eliminar la factura abierta de un vehiculo
    public void eliminarFacturaAbierta(String placa){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from facturas where Placa=? and Estado_fctra='Abierta'");
            ps1.setString(1, placa);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar factura abierta!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar la factura abierta del vehiculo: "+ placa +" " + e);
        }   
    }
    
    //Metodo para eliminar las facturas que estan asociadas a un cierre
    public void eliminarFacturasDeUnCierre(int idCierre){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from facturas where Id_cierre=?");
            ps1.setInt(1, idCierre);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar facturas de un cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar las facturas de  un cierre: " + e);
        }   
    }
    
    //Metodo que permite actualizar las facturas de salida  
    public void actualizarFacturaSalida(Factura facturaAActualizar){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+facturaAActualizar.getPlaca()+"', Propietario='"+facturaAActualizar.getPropietario()+"', Tipo_vehiculo='"+facturaAActualizar.getClaseDeVehiculo()+"', No_parqueadero="+facturaAActualizar.getId_parqueadero()+", Facturado_por='"+facturaAActualizar.getFacturadoPor()+"', Id_convenio="+facturaAActualizar.getId_convenio()+", Id_tarifa="+facturaAActualizar.getId_tarifa()+", Diferencia='"+facturaAActualizar.getDiferencia()+"', Impuesto='"+facturaAActualizar.getImpuesto()+"', Valor_a_pagar='"+facturaAActualizar.getValorAPagar()+"', Efectivo='"+facturaAActualizar.getEfectivo()+"', Cambio='"+facturaAActualizar.getCambio()+"' where Id_factura ="+facturaAActualizar.getId());

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar factura de salida, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar la factura de salida de un vehiculo: " + e);  
        } 
    }
    
    //Metodo que permite actualizar las facturas de ingreso    
    public void actualizarFacturaIngreso(Factura facturaAActualizar){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update facturas set Placa ='"+facturaAActualizar.getPlaca()+"', Propietario='"+facturaAActualizar.getPropietario()+"', Tipo_vehiculo='"+facturaAActualizar.getClaseDeVehiculo()+"', No_parqueadero="+facturaAActualizar.getId_parqueadero()+", Facturado_por='"+facturaAActualizar.getFacturadoPor()+"', Id_convenio="+facturaAActualizar.getId_convenio()+", Id_tarifa="+facturaAActualizar.getId_tarifa()+" where Id_factura ="+facturaAActualizar.getId());

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar factura de ingreso, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar la factura de ingreso de un vehiculo: " + e);  
        } 
    }
    
    //Metodo que cuenta la cantidad de facturas generadas y por contabilizar
    public String contarFacturasQueTienenUnCriterioEspecifico(String sql){
        
        String cantidadFacturas = "";
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "select count(*) from facturas where 1=1" + sql);
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
    public ArrayList obtenerValoresAPagarFacturasBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList totalesAPagar = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Valor_a_pagar FROM facturas WHERE 1=1" + sentenciaSQL);
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
                        "SELECT facturas.Fecha_factura, facturas.Codigo, usuarios.Usuario, facturas.Valor_a_pagar FROM facturas INNER JOIN usuarios ON facturas.Facturado_por = usuarios.Id_usuario");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Fecha");
            modelo.addColumn("Codigo");
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
            int columna_point = 1;

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

        if(!hayFacturaVisualizandose){
            hayFacturaVisualizandose = true;
            
            if(esFacturaAbierta == true){
                new InformacionFacturaIngreso().setVisible(true);
            }else{
               new InformacionFacturaFinal().setVisible(true); 
            } 
        }  
    } 
    
    //Metodo que busca una factura teniendo en cuenta uno o varios criterios de busqueda
    public void buscarFactura(String sentenciaSql){
        
        try{
            String [] titulos = {"Fecha", "Codigo", "Usuario", "Valor ($)"};
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(sentenciaSql);
            ResultSet rs6 = pst6.executeQuery(sentenciaSql);
            
            String[] fila = new String[4];
            
            while(rs6.next()){
                fila[0] = rs6.getString("facturas.Fecha_factura");
                fila[1] = rs6.getString("facturas.Codigo");
                fila[2] = rs6.getString("usuarios.Usuario");
                fila[3] = rs6.getString("facturas.Valor_a_pagar");
                modelo.addRow(fila);
            }
            table_listaFacturas.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de facturas!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar las facturas. " + ex);
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
    
    //Metodo que borra una factura teniendo en cuenta su codigo
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
        
        //Validamos si el numero es un numero negativo
        if(monto.contains("(")){
            esUnNumeroNegativo = true;
        }else{
            esUnNumeroNegativo = false;
        }
        
        //Le quitamos el formato de moneda al monto dado
        String charsToRemove = "$.(";
                
        for (char c : charsToRemove.toCharArray()) {
            monto = monto.replace(String.valueOf(c), "");
        }
        
        monto = monto.replaceAll(",", ".");

        if(monto.contains(".")){
            monto = monto.substring(0,monto.indexOf("."));
        }
        
        if(esUnNumeroNegativo){
            montoConvertido = "-"+monto;
        }else{
            montoConvertido = monto;
        }
        
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
    
    //Metodo que carga el contenido de la tabla del Administrador de facturas con las facturas que posee un cierre en especifico
    public void cargarFacturasDeUnCierre(int idCierre){
        
        //Cargamos los datos de la tabla
        try {
            modelo = new DefaultTableModel();
            table_listaFacturas.setModel(modelo);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "SELECT facturas.Fecha_factura, facturas.Codigo, usuarios.Usuario, facturas.Valor_a_pagar FROM facturas INNER JOIN usuarios ON facturas.Facturado_por = usuarios.Id_usuario where Id_cierre = "+idCierre);

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Fecha");
            modelo.addColumn("Codigo");
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
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de facturas con las facturas de un cierre, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al intentar llenar la tabla de facturas del Administrador de facturascon las facturas de cierre. " + e);
            }
            
        //Agregamos la funcion de ver informacion de factura al hacer click sobre el registro de la tabla
        table_listaFacturas.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = table_listaFacturas.rowAtPoint(e.getPoint());
            int columna_point = 1;

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
    
    //Metodo que agrega una factura al cierre donde esta involucrada, despues de ser actualizada
    public void agregarFacturaActualizadaAUnCierre(String totalAPagarDeFactura, int idCierreImplicado){
                
        //Creamos un objeto de tipo CierreControlador
        CierreControlador cierreControla = new CierreControlador();
        
        Cierre cierreInvolucrado = cierreControla.consultarInformacionDeUnCierre(idCierreImplicado, "");
                  
        //Hacemos el calculo correspondiente (Sumamos el valor de la factura al total esperado del cierre y sumamos 1a factura al total de facturas)
        String totalEsperado = cierreInvolucrado.getTotal_esperado();
        String cantFacturas = cierreInvolucrado.getNo_facturas();
        String dineroEnCaja = cierreInvolucrado.getDinero_caja();
        String producido = cierreInvolucrado.getProducido();
        String dineroAConsignar = cierreInvolucrado.getDineroAConsignar();
        
        //Quitamos el formato moneda a las cifras del cierre para su modificacion
        totalEsperado = quitarFormatoMoneda(totalEsperado);
        dineroEnCaja = quitarFormatoMoneda(dineroEnCaja);
        producido = quitarFormatoMoneda(producido);
        dineroAConsignar = quitarFormatoMoneda(dineroAConsignar);
        totalAPagarDeFactura = quitarFormatoMoneda(totalAPagarDeFactura);
       
        int totalEsp_int = Integer.parseInt(totalEsperado);
        int dineroEnCaja_int = Integer.parseInt(dineroEnCaja);
        int noFacturas_impl = Integer.parseInt(cantFacturas);
        int producido_int = Integer.parseInt(producido);
        int dineroAConsignar_int = Integer.parseInt(dineroAConsignar);
        int valorAPagarFactura = Integer.parseInt(totalAPagarDeFactura);
        
        //Sumamos el valor a pagar de la factura al dinero esperado, al dinero total existente en caja, al producido y al dinero a consignar
        int nuevoTotalEsperado = totalEsp_int + valorAPagarFactura;
        int nuevoDineroEnCaja = dineroEnCaja_int + valorAPagarFactura;
        int nuevoProducido = producido_int + valorAPagarFactura;
        int nuevoDineroAConsignar = dineroAConsignar_int + valorAPagarFactura;
        
        //Calculamos la nueva diferencia y restamos una factura al cierre
        int nuevaDiferencia = nuevoTotalEsperado - nuevoDineroEnCaja;
        int nuevaCantFacturas = noFacturas_impl + 1;
                        
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
    
    //Metodo que permite consultar el id de una factura abierta
    public int consultarIdDeUnaFacturaAbierta(String placa){
       
        int idFact = 0;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Id_factura from facturas where Placa = '" + placa + "' and Estado_fctra = 'Abierta'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                idFact = rs.getInt("Id_factura");
                cn.close();
           
            } else {
                log.fatal("ERROR - No se ha encontrado el ID de la factura abierta para la placa indicada");
            }
        }catch (SQLException ex){ 
            log.fatal("ERROR - Se ha producido un error al consultar el ID de una factura abierta en el sistema: " + ex); 
        } 
        return idFact;
    }

    //Metodo que ejecuta el hilo que trae los datos del estado de cupo de parqueadero en tiempo real    
    @Override
    public void run() {
        Thread ct1 = Thread.currentThread();
        
        while(ejecutarHiloOpParq == true){

            if(ct1 == hilo2){
                //Cargamos los datos de la tabla
                parqControlador.mostrarTablaFacturacionDeVehiculosEnParqueaderoPanelCaja();

                try{
                    ct1.sleep(10000);
                }catch(InterruptedException e){
                    log.fatal("ERROR - Se ha producido un error al intentar cargar la tabla de vehiculos ingresados al parqueadero del panelCaja: " + e); 
                }
            }
        }
    }
    
    //Metodo que consulta los dineros de cambio de las facturas utilizando un criterio de busqueda
    public ArrayList obtenerCambiosDeFacturasBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList cambios = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Cambio FROM facturas WHERE 1=1 AND Estado_fctra = 'Cerrada' AND Cambio <> '$0,00'" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("Cambio");
                
                //Le quitamos el formato moneda a cada diferencia de factura
                valor = quitarFormatoMoneda(valor);
                
                cambios.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener el dinero de cambio de las facturas, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener el dinero de cambio de las facturas generadas en el turno. " + e);
        }
        return cambios;
    }
    
    //Metodo que se encargade generar las estadisticas dependiendo del filtro aplicado en la busqueda de las facturas del gestor de facturas
    public void generarEstadisticasMedianteUnCriterioDeterminado(String sentenciaSQL){
        
        //Calculamos el total de ganancias por facturas de todas las facturas del sistema
        //Obtenemos los valores a pagar de las facturas para hacer calculo del total por facturas
        valoresAPagarDeFacturas = obtenerValoresAPagarFacturasBajoAlgunCriterio(sentenciaSQL);

        //Calculamos el total por facturas teniendo en cuenta los valores de pagar de las facturas obtenidas
        totalEsperadoGananciasFacturas = calcularProducido(valoresAPagarDeFacturas);
        totalEspGananciasFacturas = agregarFormatoMoneda(totalEsperadoGananciasFacturas);
        
        //Contamos las facturas
        cantidadDeFacturasSistema = contarFacturasQueTienenUnCriterioEspecifico(sentenciaSQL);
        
        //Calculamos el total de perdidas por facturas de lasfacturasque pertenecen a un cierre especifico
        //Obtenemos las diferencias de las facturas para hacer calculo de perdidas por facturas
        dineroDeCambioDeFacturas = obtenerCambiosDeFacturasBajoAlgunCriterio(sentenciaSQL);

        //Calculamos el total de perdidas por facturas teniendo en cuenta las diferencias de las facturas obtenidas
        totalPerdidasFacturas = cierreControla.calcularTotalPerdidas(dineroDeCambioDeFacturas);
        totalPerdidasFacturas_str = agregarFormatoMoneda(Integer.toString(totalPerdidasFacturas));

        //Calculamos el total real de ganancias obtenidas restando del total esperado en ganancias el total por perdidas
        totalRealGananciasFacturas = agregarFormatoMoneda(cierreControla.calcularTotalGananciasReales(totalEsperadoGananciasFacturas, Integer.toString(totalPerdidasFacturas)));
     
    }
    
    //Pinta de color verde el total de perdidas solo si este es menor o igual a cero pesos, de lo contrario, permanece de color rojo
    public void evaluarGravedadDePerdidas(){
        if(totalPerdidasFacturas <= 0){
            lbl_perdidasEnFacturas.setForeground(Color.green);
        }else{
            lbl_perdidasEnFacturas.setForeground(Color.red);
        }
    } 
    
    //Metodo que genera el reporte pdf del consolidado de facturas del sistema
    public void generarReporteConsolidadoDeFacturas(String sql){
                       
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgDocuments));
           parametro.put("numfacturas", cantidadDeFacturasSistema);
           parametro.put("gananciasesp", totalEspGananciasFacturas);
           parametro.put("ganancias", totalRealGananciasFacturas);
           parametro.put("perdidas", totalPerdidasFacturas_str);
           parametro.put("sql", sql);
                    
           JasperReport reporte = null;
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ConsolidadoFacturas.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setIconImage(getIconImagePDFUser());
            view.setTitle("Consolidado de facturas");
            view.setVisible(true);

            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                GestionarFacturas.btn_generaPDFFacturas.setEnabled(true);
            }
            });
           
       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Consolidado de Facturas, contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar reporte pdf de consolidado de facturas: " + ex);
       }
    }
    
     //Metodo que agrega el icono a la ventana de reporte PDF de facturas
    public Image getIconImagePDFUser() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/preview.png"));
        return retValue;
    }
}
