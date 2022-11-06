package vista;

import com.sun.glass.events.KeyEvent;
import controlador.ConvenioControlador;
import controlador.FacturaControlador;
import controlador.ParqueaderoControlador;
import controlador.TarifaControlador;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import modelo.Convenio;
import modelo.Factura;
import modelo.Tarifa;
import org.apache.log4j.Logger;

/**
 *
 * @author ALEJO
 */
public class LiquidacionVehiculo extends javax.swing.JFrame {

    String usuario;
    String parqueadero_actualizado;
    public static int ID;
    javax.swing.JTable tablaOperacionParqueadero;
    DefaultTableModel modeloCaja;
    int Fila;
    
    Factura facturaALiquidar = new Factura (0, "", "", "", "", "", 0, "", "", "", 0, 0, "", 0);
    Tarifa tarifaACobrar = new Tarifa(0, "", "", "", "", "", "", "", "", "", ""); 
    Convenio convenioAAplicar = new Convenio(0, "", "", "");
    String montoAPagarParaCalculoPago = "";
    String vueltas = "";
    
    FacturaControlador facturaControla = new FacturaControlador();
    ParqueaderoControlador parqControla = new ParqueaderoControlador();
    TarifaControlador tarifaControla = new TarifaControlador();
    ConvenioControlador convControla = new ConvenioControlador();
    
    long diferenciaDeFechasEnMilisegundos;    
    
    private final Logger log = Logger.getLogger(LiquidacionVehiculo.class);
    private URL url = LiquidacionVehiculo.class.getResource("Log4j.properties");
          
    /**
     * Creates new form LiquidacionVehiculo
     */
    public LiquidacionVehiculo() {
        initComponents();
        usuario = Login.usuario;
        parqueadero_actualizado = PanelCaja.parqueadero_update;
        tablaOperacionParqueadero = PanelCaja.table_operacionParqueadero;
        modeloCaja = PanelCaja.modeloCaja;
        
        lbl_diferencia.setVisible(false);
        lbl_totalAPagar.setVisible(false);
        lbl_dineroCambio.setVisible(false);
       
        setSize(417, 492);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Liquidación de vehiculo");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        Fila = tablaOperacionParqueadero.getSelectedRow();
        
        if(parqueadero_actualizado!=null){
        
            facturaALiquidar = facturaControla.consultarInformacionDeUnaFacturaAbierta(parqueadero_actualizado);
            
            //Cargamos la información en el frame
            ID = facturaALiquidar.getId();
            lbl_codigo.setText(facturaALiquidar.getCodigo());
            lbl_placa.setText(facturaALiquidar.getPlaca());
            lbl_propietario.setText(facturaALiquidar.getPropietario());
            lbl_tipoVehiculo.setText(facturaALiquidar.getClaseDeVehiculo());
            lbl_noParqueadero.setText(parqControla.consultarNombreDeParqueaderoMedianteID(facturaALiquidar.getId_parqueadero()));
            lbl_facturadoPor.setText(facturaALiquidar.getFacturadoPor());
            lbl_convenio.setText(convControla.consultarNombreDeConvenioMedianteID(facturaALiquidar.getId_convenio()));
            lbl_tarifa.setText(tarifaControla.consultarNombreDeTarifaMedianteID(facturaALiquidar.getId_tarifa()));
            
            String fecha_ingreso = facturaALiquidar.getFechaDeIngresoVehiculo();
            
            if(fecha_ingreso.equals("1990-01-01 23:59:00.0")){
                lbl_horaIngreso.setText("Registro 1er vez en sistema.");
                lbl_horaSalida.setText("N/A");
                lbl_diferencia.setText("N/A");
                lbl_diferencia.setVisible(true);
                lbl_totalAPagar.setText("0");
                lbl_totalAPagar.setVisible(true);
                txt_dineroRecibido.setEnabled(false);
                btn_calcular.setEnabled(false);
                lbl_dineroCambio.setText("0");
                lbl_dineroCambio.setVisible(true);
                
            }else{
                lbl_horaIngreso.setText(fecha_ingreso);               
                lbl_horaSalida.setText(facturaControla.fecha_Salidavehiculo());
            
                //Traemos la fecha en la que ingreso el vehiculo y la fecha en la que salió y las convertimos a Date y luego a Calendar
                String str_fechaIngresoVehiculo = facturaALiquidar.getFechaDeIngresoVehiculo();
                Date fechaDeIngreso = facturaControla.convertidorDeFechasADate(str_fechaIngresoVehiculo); 
                Calendar calendar_fechaIngreso = facturaControla.convertidorDeFechasDeDateACalendar(fechaDeIngreso);

                String str_fechaSalidaVehiculo = facturaControla.fecha_Salidavehiculo();
                Date fechaDeSalida = facturaControla.convertidorDeFechasADate(str_fechaSalidaVehiculo);            

                //Traemos el objeto tarifa que se va a cobrar
                tarifaACobrar = tarifaControla.consultarUnaTarifaMedianteID(facturaALiquidar.getId_tarifa());

                //Traemos el objeto convenio que se va a aplicar
                convenioAAplicar = convControla.consultarUnConvenioMedianteID(facturaALiquidar.getId_convenio());
            
                //Obtenemos los datos de la tarifa a cobrar
                String montoDeTarifa = tarifaACobrar.getMontoTarifa();
                String frecuenciaTarifa = tarifaACobrar.getFrecuenciaTarifa();
                String aplicarDescuento = tarifaACobrar.getTarifaTieneDescuento();
                String aplicarCostoAdicional = tarifaACobrar.getTarifaCobraTiempoAdicional();

                //Evaluamos el nombre del convenio y tarifa
                if(convenioAAplicar.getNombre().equals("NINGUNO") && tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                    lbl_totalAPagar.setText("0");
                    txt_dineroRecibido.setEnabled(false);
                    txt_dineroRecibido.setText("0");
                    btn_calcular.setEnabled(false);
                    lbl_dineroCambio.setText("0");
                }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                    lbl_totalAPagar.setText("0");
                    txt_dineroRecibido.setEnabled(false);
                    txt_dineroRecibido.setText("0");
                    btn_calcular.setEnabled(false);
                    lbl_dineroCambio.setText("0");
                }else if(convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){

                    long long_montoTarifa = Long.parseLong(montoDeTarifa);
                    long diferencia = 0;

                    String descuento_str;
                    long descuento;
                    long diferenciaConDescuento = 0;
                    String dif_str;
                    String montoAPagar = "";
                    String difConDescuento_str = "";
                
                    //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y lafecha desalida del vehiculo
                    diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                    //Evaluamos la frecuencia de la tarifa a aplicar
                    if(frecuenciaTarifa.equals("MINUTO")){

                        diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " minutos";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);   

                        }else{
                            dif_str = Long.toString(diferencia);
                            lbl_diferencia.setText(dif_str + " minutos");
                            lbl_diferencia.setVisible(true);

                           //Damos formato de moneda al monto a pagar
                           montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.darFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 

                        }            

                    }else if(frecuenciaTarifa.equals("HORA")){

                        diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " horas";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                                difConDescuento_str = diferenciaConDescuento + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                                lbl_diferencia.setText(difConDescuento_str);   
                                lbl_diferencia.setVisible(true);

                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }

                        }else{
                            dif_str = Long.toString(diferencia) + " horas";
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(dif_str);
                               lbl_diferencia.setVisible(true);
                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }
                        }
                    
                    }else if(frecuenciaTarifa.equals("DIA")){

                        diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " días";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               difConDescuento_str = difConDescuento_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(difConDescuento_str);   
                               lbl_diferencia.setVisible(true);

                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }
                        
                        }else{
                            dif_str = Long.toString(diferencia) + " días";
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(dif_str);
                               lbl_diferencia.setVisible(true);
                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }
                        }
                    }
                
                }else if(!convenioAAplicar.getNombre().equals("NINGUNO") && !tarifaACobrar.getNombreTarifa().equals("NINGUNA")){
                    //En este caso, asi tenga un convenio asignado, si la tarifa no es ninguna, predominará la tarifa    
                        long long_montoTarifa = Long.parseLong(montoDeTarifa);
                    long diferencia = 0;

                    String descuento_str;
                    long descuento;
                    long diferenciaConDescuento = 0;
                    String dif_str;
                    String montoAPagar = "";
                    String difConDescuento_str = "";
                
                    //Calculamos la diferencia el milisegundo que existe entre la fecha de ingreso y lafecha desalida del vehiculo
                    diferenciaDeFechasEnMilisegundos = facturaControla.calcularDiferenciaDeFechasEnMilisegundos(calendar_fechaIngreso, fechaDeSalida);

                    //Evaluamos la frecuencia de la tarifa a aplicar
                    if(frecuenciaTarifa.equals("MINUTO")){

                        diferencia = TimeUnit.MILLISECONDS.toMinutes(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " minutos";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            //Damos formato de moneda al monto a pagar
                            montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                            montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true);   

                        }else{
                            dif_str = Long.toString(diferencia);
                            lbl_diferencia.setText(dif_str + " minutos");
                            lbl_diferencia.setVisible(true);

                           //Damos formato de moneda al monto a pagar
                           montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                            montoAPagar = facturaControla.darFormatoMoneda(facturaControla.calcularPago(long_montoTarifa, diferencia));
                            lbl_totalAPagar.setText(montoAPagar);
                            lbl_totalAPagar.setVisible(true); 

                        }            

                    }else if(frecuenciaTarifa.equals("HORA")){

                        diferencia = TimeUnit.MILLISECONDS.toHours(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " horas";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                                difConDescuento_str = diferenciaConDescuento + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                                lbl_diferencia.setText(difConDescuento_str);   
                                lbl_diferencia.setVisible(true);

                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }

                        }else{
                            dif_str = Long.toString(diferencia) + " horas";
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaMinutosUtilizados(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(dif_str);
                               lbl_diferencia.setVisible(true);
                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }
                        }

                    }else if(frecuenciaTarifa.equals("DIA")){

                        diferencia = TimeUnit.MILLISECONDS.toDays(diferenciaDeFechasEnMilisegundos);

                        if(aplicarDescuento.equals("Si")){
                            descuento_str = tarifaACobrar.getTiempoDelDescuento();
                            descuento = Long.parseLong(descuento_str);
                            diferenciaConDescuento = facturaControla.calcularDiferenciaConDescuento(diferencia, descuento); 
                            difConDescuento_str = Long.toString(diferenciaConDescuento) + " días";
                            lbl_diferencia.setText(difConDescuento_str);   
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               difConDescuento_str = difConDescuento_str + facturaControla.calcularPagoTeniendoEnCuentaHorasUtilizadas(long_montoTarifa, diferenciaConDescuento, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(difConDescuento_str);   
                               lbl_diferencia.setVisible(true);

                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferenciaConDescuento);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }

                        }else{
                            dif_str = Long.toString(diferencia) + " días";
                            lbl_diferencia.setText(dif_str);
                            lbl_diferencia.setVisible(true);

                            if(aplicarCostoAdicional.equals("Si")){
                               dif_str = dif_str + facturaControla.calcularPagoTeniendoEnCuentaHorasUtilizadas(long_montoTarifa, diferencia, tarifaACobrar, diferenciaDeFechasEnMilisegundos);
                               lbl_diferencia.setText(dif_str);
                               lbl_diferencia.setVisible(true);
                            }else{
                                //Damos formato de moneda al monto a pagar
                                montoAPagarParaCalculoPago = facturaControla.calcularPago(long_montoTarifa, diferencia);
                                montoAPagar = facturaControla.darFormatoMoneda(montoAPagarParaCalculoPago);
                                lbl_totalAPagar.setText(montoAPagar);
                                lbl_totalAPagar.setVisible(true); 
                            }
                        }
                    }
                }
            }
        }
   }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Caja.png"));
        return retValue;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_imprimirFactura = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_dineroRecibido = new javax.swing.JTextField();
        lbl_codigo = new javax.swing.JLabel();
        lbl_placa = new javax.swing.JLabel();
        lbl_tipoVehiculo = new javax.swing.JLabel();
        lbl_horaIngreso = new javax.swing.JLabel();
        lbl_horaSalida = new javax.swing.JLabel();
        lbl_noParqueadero = new javax.swing.JLabel();
        lbl_totalAPagar = new javax.swing.JLabel();
        lbl_dineroCambio = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_propietario = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_facturadoPor = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lbl_convenio = new javax.swing.JLabel();
        btn_calcular = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        lbl_tarifa = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_diferencia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btn_imprimirFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print_15107.png"))); // NOI18N
        btn_imprimirFactura.setText("Imprimir Comprobante");
        btn_imprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirFacturaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Cod. Factura:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Placa:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Tipo Vehiculo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Hora Ingreso:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Hora Salida:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Numero de Parqueadero:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Valor a pagar:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Efectivo:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Cambio:");

        txt_dineroRecibido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_dineroRecibidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_dineroRecibidoFocusLost(evt);
            }
        });
        txt_dineroRecibido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dineroRecibidoActionPerformed(evt);
            }
        });
        txt_dineroRecibido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dineroRecibidoKeyTyped(evt);
            }
        });

        lbl_codigo.setText("codigo_factura");

        lbl_placa.setText("placa_vehiculo");

        lbl_tipoVehiculo.setText("tipo_vehiculo");

        lbl_horaIngreso.setText("hora_entrada");

        lbl_horaSalida.setText("hora_salida");

        lbl_noParqueadero.setText("numero_parqueadero");

        lbl_totalAPagar.setText("valor_a_pagar");

        lbl_dineroCambio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_dineroCambio.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Propietario:");

        lbl_propietario.setText("propietario_vehiculo");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Facturado por:");

        lbl_facturadoPor.setText("usuarioQueFactura");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Convenio:");

        lbl_convenio.setText("convenioDelVehiculo");

        btn_calcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/calculator-icon_34473.png"))); // NOI18N
        btn_calcular.setName(""); // NOI18N
        btn_calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_calcularActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Tarifa:");

        lbl_tarifa.setText("tarifaDelVehiculo");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Tiempo total:");

        lbl_diferencia.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbl_diferencia.setForeground(new java.awt.Color(0, 0, 255));
        lbl_diferencia.setText("diferencia");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_codigo)
                            .addComponent(lbl_placa)
                            .addComponent(lbl_propietario)
                            .addComponent(lbl_tipoVehiculo)
                            .addComponent(lbl_noParqueadero, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_facturadoPor)
                            .addComponent(lbl_convenio)
                            .addComponent(lbl_tarifa)
                            .addComponent(lbl_horaIngreso)
                            .addComponent(lbl_horaSalida)
                            .addComponent(lbl_totalAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_dineroCambio)
                            .addComponent(lbl_diferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_imprimirFactura)
                        .addGap(112, 112, 112)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_codigo))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_placa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_propietario)
                    .addComponent(jLabel10))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_tipoVehiculo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_noParqueadero))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_facturadoPor))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbl_convenio))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_tarifa))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_horaIngreso))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_horaSalida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbl_diferencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbl_totalAPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txt_dineroRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_calcular, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbl_dineroCambio))
                .addGap(26, 26, 26)
                .addComponent(btn_imprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Metodo del boton calcular
    private void btn_calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_calcularActionPerformed
        calcularVueltas();
    }//GEN-LAST:event_btn_calcularActionPerformed

    private void btn_imprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirFacturaActionPerformed
        
        if(PanelCaja.laCajaFueAbierta == false){
            JOptionPane.showMessageDialog(null, "No Permitido");
            txt_dineroRecibido.setText("");
        }else{
            
            boolean ventanaEmergenteCopiaTicketSalida = false;
        
            String ingreso = lbl_horaIngreso.getText();
            String placa = lbl_placa.getText();
            String parqueadero = lbl_noParqueadero.getText();
            String dueño = lbl_propietario.getText();
        
            if(ingreso.equals("Registro 1er vez en sistema.")){

                facturaControla.liquidarFacturaDeVehiculo("1990-01-01 23:59:00.0", placa, "0", "0", "0");
                dispose();
                parqControla.actualizarEstadoDeParqueadero(placa, dueño, parqControla.consultarIdParqueadero(parqueadero), "No");
                facturaControla.cerrarFactura(placa);
                facturaControla.generarTicketSalida(placa);

                ventanaEmergenteCopiaTicketSalida = true;

                while(ventanaEmergenteCopiaTicketSalida == true){
                   String botones[] = {"Imprimir copia", "Cerrar"};
                   //El segundo atributo numerico (el numero 1)representa el icono de tipo de mensaje, es decir puede ser informativo de advertencia de error o sin icono
                   int eleccionFinalizarArqueo = JOptionPane.showOptionDialog(this, "Vehiculo liquidado satisfactoriamente.", "Liquidar vehiculo", 0, 1, null, botones, this);

                   if(eleccionFinalizarArqueo == JOptionPane.YES_OPTION){
                       facturaControla.generarTicketSalida(placa); 
                   }

                   if(eleccionFinalizarArqueo == JOptionPane.NO_OPTION){
                       ventanaEmergenteCopiaTicketSalida = false;
                       dispose();
                       PanelCaja.hayVehiculoLiquidandose = false;
                   }
                }  

            }else{

                String dineroRecibido = txt_dineroRecibido.getText();

                if(dineroRecibido.equals("")){
                    txt_dineroRecibido.setBackground(Color.red);
                    JOptionPane.showMessageDialog(null, "Digite el efectivo recibido para hacer el calculo correspondiente.");
                    txt_dineroRecibido.setBackground(Color.white);

                }else{
                    calcularVueltas();
                    String monto_a_pagar = lbl_totalAPagar.getText();
                    String horaSalida = lbl_horaSalida.getText();
                    String dineroRecibMoney = facturaControla.darFormatoMoneda(dineroRecibido);
                    String cambio = lbl_dineroCambio.getText();

                    facturaControla.liquidarFacturaDeVehiculo(horaSalida, placa, monto_a_pagar, dineroRecibMoney, cambio);
                    dispose();
                    parqControla.liberarParqueadero(placa);
                    facturaControla.cerrarFactura(placa);
                    facturaControla.generarTicketSalida(placa);

                    ventanaEmergenteCopiaTicketSalida = true;

                    while(ventanaEmergenteCopiaTicketSalida == true){
                       String botones[] = {"Imprimir copia", "Cerrar"};
                       //El segundo atributo numerico (el numero 1)representa el icono de tipo de mensaje, es decir puede ser informativo de advertencia de error o sin icono
                       int eleccionFinalizarArqueo = JOptionPane.showOptionDialog(this, "Vehiculo liquidado satisfactoriamente.", "Liquidar vehiculo", 0, 1, null, botones, this);

                       if(eleccionFinalizarArqueo == JOptionPane.YES_OPTION){
                           facturaControla.generarTicketSalida(placa); 
                       }

                       if(eleccionFinalizarArqueo == JOptionPane.NO_OPTION){
                           ventanaEmergenteCopiaTicketSalida = false;
                           dispose();
                           PanelCaja.hayVehiculoLiquidandose = false;
                       }
                    }         
                }  
            }
        }          
    }//GEN-LAST:event_btn_imprimirFacturaActionPerformed

    private void txt_dineroRecibidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String dineroRecibido = txt_dineroRecibido.getText();
            String vueltas = facturaControla.calcularVueltas(montoAPagarParaCalculoPago, dineroRecibido);
            lbl_dineroCambio.setText(facturaControla.darFormatoMoneda(vueltas));
            lbl_dineroCambio.setVisible(true);
        }
    }//GEN-LAST:event_txt_dineroRecibidoKeyPressed

    private void txt_dineroRecibidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dineroRecibidoKeyReleased

    private void txt_dineroRecibidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoKeyTyped
        //Evalua que se digiten numeros no letras
        char validar = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            
            JOptionPane.showMessageDialog(rootPane, "Ingrese solo numeros.");
        }
    }//GEN-LAST:event_txt_dineroRecibidoKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cerrarLiquidacionVehiculo();
    }//GEN-LAST:event_formWindowClosing

    private void txt_dineroRecibidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoFocusLost

    }//GEN-LAST:event_txt_dineroRecibidoFocusLost

    private void txt_dineroRecibidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dineroRecibidoActionPerformed

    private void txt_dineroRecibidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_dineroRecibidoFocusGained
       txt_dineroRecibido.setText("");
       lbl_dineroCambio.setText("0");
       lbl_dineroCambio.setVisible(false);
    }//GEN-LAST:event_txt_dineroRecibidoFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidacionVehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //Esto cambia la apariencia de la app para que se acomode al Siste Operativo
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new LiquidacionVehiculo().setVisible(true);
                }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    //Logger.getLogger(LiquidacionVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btn_calcular;
    private javax.swing.JButton btn_imprimirFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JLabel lbl_codigo;
    public static javax.swing.JLabel lbl_convenio;
    public static javax.swing.JLabel lbl_diferencia;
    public static javax.swing.JLabel lbl_dineroCambio;
    public static javax.swing.JLabel lbl_facturadoPor;
    public static javax.swing.JLabel lbl_horaIngreso;
    public static javax.swing.JLabel lbl_horaSalida;
    public static javax.swing.JLabel lbl_noParqueadero;
    public static javax.swing.JLabel lbl_placa;
    public static javax.swing.JLabel lbl_propietario;
    public static javax.swing.JLabel lbl_tarifa;
    public static javax.swing.JLabel lbl_tipoVehiculo;
    public static javax.swing.JLabel lbl_totalAPagar;
    public static javax.swing.JTextField txt_dineroRecibido;
    // End of variables declaration//GEN-END:variables

         
    //Metodo que se invoca al cerrar el jFrame
    private void cerrarLiquidacionVehiculo(){
        
        String botones[] = {"Cerrar", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(this, "¿Está seguro que desea cerrar?", "Liquidar vehiculo", 0, 3, null, botones, this);
        
        if(eleccion == JOptionPane.YES_OPTION){
            PanelCaja.hayVehiculoLiquidandose = false;
            dispose();
        }
    }
    
    //Metodo que hace el calculo del monto de vueltas a nivel de presentacion del frame
    public void calcularVueltas(){
        String dineroRecibido = txt_dineroRecibido.getText();
        vueltas = facturaControla.calcularVueltas(montoAPagarParaCalculoPago, dineroRecibido);
        lbl_dineroCambio.setText(facturaControla.darFormatoMoneda(vueltas)); 
        lbl_dineroCambio.setVisible(true);
    }
}
