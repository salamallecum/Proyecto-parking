package controlador;

import clasesDeApoyo.Conexion;
import static controlador.FacturaControlador.rutaImgDocuments;
import static controlador.FacturaControlador.rutaImgTickets;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Cierre;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.EditarCierreDeCaja;
import vista.GestionarCierres;
import static vista.GestionarCierres.hayCierreAbierto;
import static vista.GestionarCierres.lbl_perdidasEnCierres;
import static vista.GestionarCierres.modelo;
import static vista.GestionarCierres.table_listaCierres;
import vista.PanelCaja;

/**
 *
 * @author ALEJO
 */
public class CierreControlador {
          
   private final Logger log = Logger.getLogger(CierreControlador.class);
   private URL url = CierreControlador.class.getResource("Log4j.properties");
   
   Cierre cierreConsultado = new Cierre(0, "", "", 0, "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", "", "", "", "", "");
   FacturaControlador factControla;
   ParqueaderoControlador parqControla;
   public String totalEsperadoProducidoCierres = "";
   public String totalRealProducidoCierres = "";
   public String totalPerdidasCierres_str = "";
   public String cantidadDeCierresSistema = "";
   int totalPerdidasCierres;
   ArrayList producidosDeCierres;
   ArrayList diferenciasDeCierres;

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
        PanelCaja.table_operacionParqueadero.setEnabled(false);
        limpiarTablaOperacionParqueadero(PanelCaja.table_operacionParqueadero);
        factControla.detenerHiloOperacionParqueadero();
        parqControla.detenerHiloParqueaderosVisitantesDisponiblesPanelCaja();
        
    }
    
    //Metodo que permite limpiar todos los registros de la tabla de operacion del parqueadero una vez se ha realizado el cierre
    public void limpiarTablaOperacionParqueadero(JTable tabla){
        try {
            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
            int filas=tabla.getRowCount();
            for (int i = 0;filas>i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar limpiar la tabla de operacion del parqueadero, contacte al administrador!!!.");
            log.fatal("ERROR - Se ha producido un error al intentar resetear registros de la tabla de operacion del parqueadero: " + ex);
        }
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
            pst3.setInt(5, cier.getUsuario());
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
    public void generarTicketCierreDeCaja(String codigoDeCierre, boolean vistaPrevia){
               
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("codigo", codigoDeCierre);
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgTickets));
           
           JasperReport reporte = null;
           
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Cierre.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);

           if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Ticket de cierre de caja");
                view.setVisible(true);
           
           }else{
               //Hace que se imprima directamente
               JasperPrintManager.printReport(jprint, false);
           } 

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
            log.fatal("ERROR - Se ha producido un error al intentar asginar un cierre a facturas contabilizadas: " + e);
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
        
        //Se cre un objeto de tipo FacturaControlador
        FacturaControlador facturaControla = new FacturaControlador();
        
        String dineroCaja = facturaControla.quitarFormatoMoneda(dineroEnCaja);              
                  
        //Convertimos las cantidades a enteros para el calculo correspondiente
        int baseInt = Integer.parseInt(base);
        int dineroCajaInt = Integer.parseInt(dineroCaja);
               
        int dineroAConsig = dineroCajaInt - baseInt;
        
        //Damos formato de moneda al dinero a consignar
        dineroAConsignar = Integer.toString(dineroAConsig);
        Double consignacion = new Double(dineroAConsignar);
        Locale region = Locale.getDefault();
        //Currency moneda = Currency.getInstance(region);
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(region);
        dineroAConsignar = formatoMoneda.format(consignacion);
        
        return dineroAConsignar;
    }

    //Metodo que permite consultarla informacion de un cierre
    public Cierre consultarInformacionDeUnCierre(int idDelCierre, String codigoCierre){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from cierres where Id_cierre = " + idDelCierre + " or Codigo='"+codigoCierre+"'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                cierreConsultado.setId(rs.getInt("Id_cierre"));
                cierreConsultado.setCodigo(rs.getString("Codigo"));
                cierreConsultado.setCodigoArqueo(rs.getString("Codigo_arqueo"));
                cierreConsultado.setFecha_cierre(rs.getString("Fecha_cierre"));
                cierreConsultado.setUsuario(rs.getInt("Id_usuario"));
                cierreConsultado.setBase_caja(rs.getString("Base_caja"));
                cierreConsultado.setNumBilletesDe100Mil(rs.getString("numerobilletes100mil"));
                cierreConsultado.setNumBilletesDe50Mil(rs.getString("numerobilletes50mil"));
                cierreConsultado.setNumBilletesDe20Mil(rs.getString("numerobilletes20mil"));
                cierreConsultado.setNumBilletesDe10Mil(rs.getString("numerobilletes10mil"));
                cierreConsultado.setNumBilletesDe5Mil(rs.getString("numerobilletes5mil"));
                cierreConsultado.setNumBilletesDe2Mil(rs.getString("numerobilletes2mil"));
                cierreConsultado.setNumBilletesOMonedasDeMil(rs.getString("numerobilletesMil"));
                cierreConsultado.setNumMonedasDe500(rs.getString("numeromonedas500"));
                cierreConsultado.setNumMonedasDe200(rs.getString("numeromonedas200"));
                cierreConsultado.setNumMonedasDe100(rs.getString("numeromonedas100"));
                cierreConsultado.setNumMonedasDe50(rs.getString("numeromonedas50"));
                cierreConsultado.setMontoEnBilletes100Mil(rs.getInt("montoen100mil"));
                cierreConsultado.setMontoEnBilletes50Mil(rs.getInt("montoen50mil"));
                cierreConsultado.setMontoEnBilletes20Mil(rs.getInt("montoen20mil"));
                cierreConsultado.setMontoEnBilletes10Mil(rs.getInt("montoen10mil"));
                cierreConsultado.setMontoEnBilletes5Mil(rs.getInt("montoen5mil"));
                cierreConsultado.setMontoEnBilletes2Mil(rs.getInt("montoen2mil"));
                cierreConsultado.setMontoEnBilletesOMonedasMil(rs.getInt("montoenmil"));
                cierreConsultado.setMontoEnMonedasDe500(rs.getInt("montoen500"));
                cierreConsultado.setMontoEnMonedasDe200(rs.getInt("montoen200"));
                cierreConsultado.setMontoEnMonedasDe100(rs.getInt("montoen100"));
                cierreConsultado.setMontoEnMonedasDe50(rs.getInt("montoen50"));
                cierreConsultado.setProducido(rs.getString("Producido"));
                cierreConsultado.setTotal_esperado(rs.getString("Total_esperado"));
                cierreConsultado.setDinero_caja(rs.getString("Dinero_en_caja"));
                cierreConsultado.setDiferencia(rs.getString("Diferencia"));
                cierreConsultado.setDineroAConsignar(rs.getString("Dinero_a_consignar"));
                cierreConsultado.setNo_facturas(rs.getString("No_facturas"));
                cierreConsultado.setObservaciones(rs.getString("Observaciones")); 
                
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de un cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de un cierre: " + e);
        }
        return cierreConsultado;        
    }
    
    //Metodo paraactualizar montos finales de un cierre
    public void actualizarMontosFinalesDeUnCierre(int idCierre, String producido, String totalEsperado, String dineroEnCaja, String diferencia, String dineroAConsignar, String noFacturas){
        
        //Actualizamos el cierre implicado con los nuevos datos
        try{
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement("update cierres set Producido='"+producido+"', Total_esperado='"+totalEsperado+"', Dinero_en_caja='"+dineroEnCaja+"', Diferencia ='"+diferencia+"', Dinero_a_consignar='"+dineroAConsignar+"', No_facturas='"+noFacturas+"' where Id_cierre="+idCierre);

            pst6.executeUpdate();
            cn6.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar montos finales de un cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar los montos finales de un cierre: " + e);
        }
    }
    
    //Metodo que busca un cierre teniendo en cuenta uno o varios criterios de busqueda
    public void buscarCierre(String sentenciaSql){
        
        try{
            String [] titulos = {"Fecha", "Codigo", "Usuario", "Producido ($)", "Diferencia ($)"};
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(sentenciaSql);
            ResultSet rs6 = pst6.executeQuery(sentenciaSql);
            
            String[] fila = new String[5];
            
            while(rs6.next()){
                fila[0] = rs6.getString("cierres.Fecha_cierre");
                fila[1] = rs6.getString("cierres.Codigo");
                fila[2] = rs6.getString("usuarios.Usuario");
                fila[3] = rs6.getString("cierres.Producido");
                fila[4] = rs6.getString("cierres.Diferencia");
                modelo.addRow(fila);
            }
            table_listaCierres.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de cierres!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar los cierres. " + ex);
        }
    }
        
    //Metodo que carga el contenido de la tabla del Administrador de cierres
    public void cargarTablaAdministradorDeCierres(){
        
        //Cargamos los datos de la tabla
        try {
            modelo = new DefaultTableModel();
            table_listaCierres.setModel(modelo);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "SELECT cierres.Fecha_cierre, cierres.Codigo, usuarios.Usuario, cierres.Producido, cierres.Diferencia FROM cierres INNER JOIN usuarios ON cierres.Id_usuario = usuarios.Id_usuario AND Id_cierre <> 1");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Fecha");
            modelo.addColumn("Codigo");
            modelo.addColumn("Usuario");
            modelo.addColumn("Producido ($)");
            modelo.addColumn("Diferencia ($)");

            int[] anchosTabla = {10,10,5,10,10};

            for(int x=0; x < cantidadColumnas; x++){
                table_listaCierres.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
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
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de cierres, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al intentar llenar la tabla de cierres del Administrador de cierres. " + e);
            }
            
        //Agregamos la funcion de ver informacion de cierre al hacer click sobre el registro de la tabla
        GestionarCierres.table_listaCierres.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = GestionarCierres.table_listaCierres.rowAtPoint(e.getPoint());
            int columna_point = 1;

            if(fila_point > -1){
                GestionarCierres.codigoCierre_update = (String) modelo.getValueAt(fila_point, columna_point);
                generarInformacionDeCierreEnFrame();  
            }
        }    
    });
    }
    
    //Metodo que genera la informacion de un cierre en el jframe
    public void generarInformacionDeCierreEnFrame(){

        if(!hayCierreAbierto){
            GestionarCierres.hayCierreAbierto = true;
            new EditarCierreDeCaja().setVisible(true);
        }    
    }
    
    //Metodo que consulta la información de un cierre
    public Cierre consultarInformacionDeUnCierre(String codigoCier){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from cierres where Codigo = '" + codigoCier + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                cierreConsultado.setId(rs.getInt("Id_cierre"));
                cierreConsultado.setCodigo(rs.getString("Codigo"));
                cierreConsultado.setCodigoArqueo(rs.getString("Codigo_arqueo"));
                cierreConsultado.setBase_caja(rs.getString("Base_caja"));
                cierreConsultado.setUsuario(rs.getInt("Id_usuario"));
                cierreConsultado.setNumBilletesDe100Mil(rs.getString("numerobilletes100mil"));
                cierreConsultado.setNumBilletesDe50Mil(rs.getString("numerobilletes50mil"));
                cierreConsultado.setNumBilletesDe20Mil(rs.getString("numerobilletes20mil"));
                cierreConsultado.setNumBilletesDe10Mil(rs.getString("numerobilletes10mil"));
                cierreConsultado.setNumBilletesDe5Mil(rs.getString("numerobilletes5mil"));
                cierreConsultado.setNumBilletesDe2Mil(rs.getString("numerobilletes2mil"));
                cierreConsultado.setNumBilletesOMonedasDeMil(rs.getString("numerobilletesMil"));
                cierreConsultado.setNumMonedasDe500(rs.getString("numeromonedas500")); 
                cierreConsultado.setNumMonedasDe200(rs.getString("numeromonedas200"));
                cierreConsultado.setNumMonedasDe100(rs.getString("numeromonedas100"));
                cierreConsultado.setNumMonedasDe50(rs.getString("numeromonedas50"));
                cierreConsultado.setProducido(rs.getString("Producido"));
                cierreConsultado.setTotal_esperado(rs.getString("Total_esperado"));
                cierreConsultado.setDinero_caja(rs.getString("Dinero_en_caja"));
                cierreConsultado.setDiferencia(rs.getString("Diferencia"));
                cierreConsultado.setNo_facturas(rs.getString("No_facturas"));
                cierreConsultado.setObservaciones(rs.getString("Observaciones"));
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de un cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de un cierre: " + e);
        }
        return cierreConsultado;        
    }
        
    //Metodo que permite actualizar cierres    
    public void actualizarCierre(Cierre cierEdit){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update cierres set Fecha_cierre ='"+cierEdit.getFecha_cierre()+"', Id_usuario="+cierEdit.getUsuario()+", base_caja='"+cierEdit.getBase_caja()+"', numerobilletes100mil='"+cierEdit.getNumBilletesDe100Mil()+"', numerobilletes50mil='"+cierEdit.getNumBilletesDe50Mil()+"', numerobilletes20mil='"+cierEdit.getNumBilletesDe20Mil()+"', numerobilletes10mil='"+cierEdit.getNumBilletesDe10Mil()+"', numerobilletes5mil='"+cierEdit.getNumBilletesDe5Mil()+"', numerobilletes2mil='"+cierEdit.getNumBilletesDe2Mil()+"', numerobilletesMil='"+cierEdit.getNumBilletesOMonedasDeMil()+"', numeromonedas500='"+cierEdit.getNumMonedasDe500()+"', numeromonedas200='"+cierEdit.getNumMonedasDe200()+"', numeromonedas100='"+cierEdit.getNumMonedasDe100()+"', numeromonedas50='"+cierEdit.getNumMonedasDe50()+"', montoen100mil="+cierEdit.getMontoEnBilletes100Mil()+", montoen50mil="+cierEdit.getMontoEnBilletes50Mil()+", montoen20mil="+cierEdit.getMontoEnBilletes20Mil()+", montoen10mil="+cierEdit.getMontoEnBilletes10Mil()+", montoen5mil="+cierEdit.getMontoEnBilletes5Mil()+", montoen2mil="+cierEdit.getMontoEnBilletes2Mil()+", montoenmil="+cierEdit.getMontoEnBilletesOMonedasMil()+", montoen500="+cierEdit.getMontoEnMonedasDe500()+", montoen200="+cierEdit.getMontoEnMonedasDe200()+", montoen100="+cierEdit.getMontoEnMonedasDe100()+", montoen50="+cierEdit.getMontoEnMonedasDe50()+", Total_esperado='"+cierEdit.getTotal_esperado()+"', Dinero_en_caja='"+cierEdit.getDinero_caja()+"', Diferencia='"+cierEdit.getDiferencia()+"', Dinero_a_consignar='"+cierEdit.getDineroAConsignar()+"', Observaciones='"+cierEdit.getObservaciones()+"' where Id_cierre ="+cierEdit.getId());
            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar cierre, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar un cierre: " + e);  
        } 
    }
    
    //Metodo para eliminar un cierre de caja del sistema
    public void eliminarCierre(String codigoDelCierre){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from cierres where Codigo=?");
            ps1.setString(1, codigoDelCierre);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar un cierre" + e);
        }   
    }
    
    //Metodo que consulta los producidos de los cierres utilizando un criterio de busqueda
    public ArrayList obtenerProducidosDeCierresBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList producidos = new ArrayList();
        factControla = new FacturaControlador();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Producido FROM cierres WHERE 1=1 AND Id_cierre <> 1" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("Producido");
                
                //Le quitamos el formato moneda a cada monto de producido
                valor = factControla.quitarFormatoMoneda(valor);
                
                producidos.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener producidos de cierres, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener los producidos de los cierres generados en el turno. " + e);
        }
        return producidos;
    }
    
    //Metodo que calcula el valor del producido teniendo en cuenta un arreglo de producidos
    public String calcularProducido(ArrayList producidos){
        
        String prod = "";
        int resultado = 0;
        
        for(int i = 0; i < producidos.size(); i++){
            
            String producido_str = String.valueOf(producidos.get(i));
            int producido = Integer.parseInt(producido_str);
            resultado = resultado + producido;
        }
        
        prod = Integer.toString(resultado);      
        
        return prod;
    }
    
    //Metodo que cuenta la cantidad de cierres generados
    public String contarCierresQueTienenUnCriterioEspecifico(String sql){
        
        String cantidadCierres = "";
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "select count(*) from cierres where 1=1 AND Id_cierre <> 1" + sql);
            ResultSet rs3 = pst3.executeQuery();

            if(rs3.next()){
                int cant_cierres = rs3.getInt("count(*)");
                cantidadCierres = Integer.toString(cant_cierres);
            }
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al contar cierres generados en el turno, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al contar loc cierres generados en el turno: " + e);
        }
        return cantidadCierres;
    }
    
    //Metodo que consulta las diferencias de los cierres utilizando un criterio de busqueda
    public ArrayList obtenerDiferenciasDeCierresBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList diferencias = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Diferencia FROM cierres WHERE 1=1 AND Id_cierre <> 1" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("Diferencia");
                
                //Le quitamos el formato moneda a cada monto de producido
                valor = factControla.quitarFormatoMoneda(valor);
                
                diferencias.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener diferencias de cierres, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener las diferencias de los cierres generados en el turno. " + e);
        }
        return diferencias;
    }
    
    //Metodo que calcula el total en perdidas teniendo en cuenta un arreglo de diferencias de cierres, facturas o arqueos
    public int calcularTotalPerdidas(ArrayList diferencias){
       
        int resultado = 0;
        
        for(int i = 0; i < diferencias.size(); i++){
            
            String diferencia_str = String.valueOf(diferencias.get(i));
            int diferencia = Integer.parseInt(diferencia_str);
            resultado = resultado + diferencia;
        }     
        
        return resultado;
    }
    
    //Metodo que consulta los dineros consignados de los cierres utilizando un criterio de busqueda
    public ArrayList obtenerDineroAConsignarDeCierresBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList dinerosAConsignar = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT Dinero_a_consignar FROM cierres WHERE 1=1 AND Id_cierre <> 1" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("Dinero_a_consignar");
                
                //Le quitamos el formato moneda a cada monto de dinero a consignar
                valor = factControla.quitarFormatoMoneda(valor);
                
                dinerosAConsignar.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener dineros a consignar de cierres, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener los dineros consignados de los cierres generados en el turno. " + e);
        }
        return dinerosAConsignar;
    }
    
    //Metodo que calcula el total obtenido de ganancias teniendo en cuenta el totalde ganancias esperado y el totalde perdidas alcanzado en un conjunto de cierres
    public String calcularTotalGananciasReales(String totalGananciasEsperado, String totalPerdidas){
        
        String resultado = "";

        //Convertimos las cifras obtenidas a int
        int gananciasEsperadas = Integer.parseInt(totalGananciasEsperado);
        int perdidas = Integer.parseInt(totalPerdidas);
        
        resultado = Integer.toString(gananciasEsperadas - perdidas);
        return resultado;
    }
    
    //Metodo que se encarga de generar las estadisticas dependiendo del filtro aplicado en la busqueda de los cierres del gestor de cierres
    public void generarEstadisticasMedianteUnCriterioDeterminado(String sentenciaSQL){
        
        factControla = new FacturaControlador();
        
        //Calculamos el total de ganancias por cierres de todos los cierres del sistema
        //Obtenemos los producidos de los cierres para hacer calculo del producido por cierres
        producidosDeCierres = obtenerProducidosDeCierresBajoAlgunCriterio(sentenciaSQL);

        //Calculamos el total por cierres teniendo en cuenta los producidos de los cierres obtenidos
        totalEsperadoProducidoCierres = factControla.agregarFormatoMoneda(calcularProducido(producidosDeCierres));
        
        //Contamos los cierres
        cantidadDeCierresSistema = contarCierresQueTienenUnCriterioEspecifico(sentenciaSQL);
        
        //Calculamos el total de perdidas por cierres de todos los cierres del sistema
        //Obtenemos las diferencias de los cierres para hacer calculo de perdidas por cierres
        diferenciasDeCierres = obtenerDiferenciasDeCierresBajoAlgunCriterio(sentenciaSQL);
        
        //Calculamos el total de perdidas por cierres teniendo en cuenta las diferencias de los cierres obtenidos
        totalPerdidasCierres = calcularTotalPerdidas(diferenciasDeCierres);
        totalPerdidasCierres_str = factControla.agregarFormatoMoneda(Integer.toString(totalPerdidasCierres));
        
        //Calculamos el total real de ganancias obtenidas restando del total esperado en ganancias el total por perdidas
        totalRealProducidoCierres = factControla.agregarFormatoMoneda(calcularTotalGananciasReales(factControla.quitarFormatoMoneda(totalEsperadoProducidoCierres), Integer.toString(totalPerdidasCierres)));
                
    }
    
    //Pinta de color verde el total de perdidas solo si este es menor o igual a cero pesos, de lo contrario, permanece de color rojo
    public void evaluarGravedadDePerdidas(){
        if(totalPerdidasCierres <= 0){
            lbl_perdidasEnCierres.setForeground(Color.GREEN);
        }else{
            lbl_perdidasEnCierres.setForeground(Color.red);
        }
    }  
    
    //Metodo que genera el reporte pdf del consolidado de cierres del sistema
    public void generarReporteConsolidadoDeCierres(String sql){
                       
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgDocuments));
           parametro.put("numcierres", cantidadDeCierresSistema);
           parametro.put("gananciasesp", totalEsperadoProducidoCierres);
           parametro.put("ganancias", totalRealProducidoCierres);
           parametro.put("perdidas", totalPerdidasCierres_str);
           parametro.put("totalconsigna", factControla.agregarFormatoMoneda(Integer.toString(calcularTotalDineroConsignado(obtenerDineroAConsignarDeCierresBajoAlgunCriterio(sql)))));
           parametro.put("factgeneradas", Integer.toString(calcularTotalDeFacturasGeneradas(obtenerTotalDeFacturasDeCierresBajoAlgunCriterio(sql))));
           parametro.put("sql", sql);
                    
           JasperReport reporte = null;
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ConsolidadoCierres.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setIconImage(getIconImagePDFUser());
            view.setTitle("Consolidado de cierres de caja");
            view.setVisible(true);

            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                GestionarCierres.btn_generaPDFCierres.setEnabled(true);
            }
            });
           
       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Consolidado de Cierres de Caja, contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar reporte pdf de consolidado de cierres: " + ex);
       }
    }
    
    //Metodo que agrega el icono a la ventana de reporte PDF de cierres
    public Image getIconImagePDFUser() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/preview.png"));
        return retValue;
    }
    
    //Metodo que calcula el total en dinero consignado teniendo en cuenta un arreglo de dineros a consignar de cierres, facturas o arqueos
    public int calcularTotalDineroConsignado(ArrayList dinerosConsig){
       
        int resultado = 0;
        
        for(int i = 0; i < dinerosConsig.size(); i++){
            
            String dineroConsig_str = String.valueOf(dinerosConsig.get(i));
            int dineroConsig = Integer.parseInt(dineroConsig_str);
            resultado = resultado + dineroConsig;
        }     
        
        return resultado;
    }
    
    //Metodo que consulta el total de facturas de los cierres utilizando un criterio de busqueda
    public ArrayList obtenerTotalDeFacturasDeCierresBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList numDeFacturas = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT No_facturas FROM cierres WHERE 1=1 AND Id_cierre <> 1" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("No_facturas");
                numDeFacturas.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener numerosde facturas de cierres, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener los numerosde facturas de los cierres generados en el turno. " + e);
        }
        return numDeFacturas;
    }
    
    //Metodo que calcula el total de facturas de los cierres teniendo en cuenta un arreglo de total de facturas de cierres, facturas o arqueos
    public int calcularTotalDeFacturasGeneradas(ArrayList totalNumFacturas){
       
        int resultado = 0;
        
        for(int i = 0; i < totalNumFacturas.size(); i++){
            
            String numFacturas_str = String.valueOf(totalNumFacturas.get(i));
            int numFacturas = Integer.parseInt(numFacturas_str);
            resultado = resultado + numFacturas;
        }     
        
        return resultado;
    }
        
}
