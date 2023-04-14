package controlador;

import clasesDeApoyo.Conexion;
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
import static vista.GestionarCierres.modelo;
import vista.PanelCaja;

/**
 *
 * @author ALEJO
 */
public class CierreControlador {
          
   private final Logger log = Logger.getLogger(CierreControlador.class);
   private URL url = CierreControlador.class.getResource("Log4j.properties");
   
   Cierre cierreConsultado = new Cierre(0, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "", "", "", "", "", "");
   FacturaControlador factControla = new FacturaControlador();
   ParqueaderoControlador parqControla = new ParqueaderoControlador();
   
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
        parqControla.detenerHiloParqueaderosVisitantesDisponibles();
        
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
    public void generarTicketCierreDeCaja(String codigoDeCierre, boolean vistaPrevia){
               
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("codigo", codigoDeCierre);
           
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
                cierreConsultado.setUsuario(rs.getString("Nombre_usuario"));
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
    
    //Metodo que busca un cierre teniendo en cuenta su codigo
    public void busquedaCierrePorCodigo(String texto){
       
        try{
            String [] titulos = {"Codigo", "Fecha", "Usuario", "Valor ($)"};
            String filtro = "%"+texto+"%";
            String SQL = "select Codigo, Fecha_cierre, Nombre_usuario, Dinero_en_caja from cierres where Codigo like "+'"'+filtro+'"';
            GestionarCierres.modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[4];
            
            while(rs6.next()){
                fila[0] = rs6.getString("Codigo");
                fila[1] = rs6.getString("Fecha_cierre");
                fila[2] = rs6.getString("Nombre_usuario");
                fila[3] = rs6.getString("Dinero_en_caja");
                GestionarCierres.modelo.addRow(fila);
            }
            GestionarCierres.table_listaCierres.setModel(GestionarCierres.modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar un cierre por medio de su codigo. " + ex);
        }                
    } 
    
    //Metodo que busca un cierre teniendo en cuenta su usuario
    public void busquedaCierrePorUsuario(String texto){
       
        try{
            String [] titulos = {"Codigo", "Fecha", "Usuario", "Valor ($)"};
            String filtro = "%"+texto+"%";
            String SQL = "select Codigo, Fecha_cierre, Nombre_usuario, Dinero_en_caja from cierres where Nombre_usuario like "+'"'+filtro+'"';
            GestionarCierres.modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[4];
            
            while(rs6.next()){
                fila[0] = rs6.getString("Codigo");
                fila[1] = rs6.getString("Fecha_cierre");
                fila[2] = rs6.getString("Nombre_usuario");
                fila[3] = rs6.getString("Dinero_en_caja");
                GestionarCierres.modelo.addRow(fila);
            }
            GestionarCierres.table_listaCierres.setModel(GestionarCierres.modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar un cierre por medio de su usuario. " + ex);
        }                
    }
    
    //Metodo que carga el contenido de la tabla del Administrador de cierres
    public void cargarTablaAdministradorDeCierres(){
        
        //Cargamos los datos de la tabla
        try {
            modelo = new DefaultTableModel();
            GestionarCierres.table_listaCierres.setModel(modelo);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Codigo, Fecha_cierre, Nombre_usuario, Total_esperado from cierres");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Codigo");
            modelo.addColumn("Fecha");
            modelo.addColumn("Usuario");
            modelo.addColumn("Total esperado ($)");

            int[] anchosTabla = {10,10,5,10};

            for(int x=0; x < cantidadColumnas; x++){
                GestionarCierres.table_listaCierres.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
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
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de arqueos, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al intentar llenar la tabla de arqueos del Administrador de arqueos. " + e);
            }
            
        //Agregamos la funcion de ver informacion de cierre al hacer click sobre el registro de la tabla
        GestionarCierres.table_listaCierres.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = GestionarCierres.table_listaCierres.rowAtPoint(e.getPoint());
            int columna_point = 0;

            if(fila_point > -1){
                GestionarCierres.codigoCierre_update = (String) modelo.getValueAt(fila_point, columna_point);
                generarInformacionDeCierreEnFrame(GestionarCierres.codigoCierre_update);  
            }
        }    
    });
    }
    
    //Metodo que genera la informacion de un cierre en el jframe
    public void generarInformacionDeCierreEnFrame(String codCierre){

        if(GestionarCierres.hayCierreAbierto == true || codCierre.equals("4444")){
            JOptionPane.showMessageDialog(null,"No permitido.");
        }else{
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
                cierreConsultado.setUsuario(rs.getString("Nombre_usuario"));
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
            PreparedStatement pst9 = cn9.prepareStatement("update cierres set Fecha_cierre ='"+cierEdit.getFecha_cierre()+"', Nombre_usuario='"+cierEdit.getUsuario()+"', base_caja='"+cierEdit.getBase_caja()+"', numerobilletes100mil='"+cierEdit.getNumBilletesDe100Mil()+"', numerobilletes50mil='"+cierEdit.getNumBilletesDe50Mil()+"', numerobilletes20mil='"+cierEdit.getNumBilletesDe20Mil()+"', numerobilletes10mil='"+cierEdit.getNumBilletesDe10Mil()+"', numerobilletes5mil='"+cierEdit.getNumBilletesDe5Mil()+"', numerobilletes2mil='"+cierEdit.getNumBilletesDe2Mil()+"', numerobilletesMil='"+cierEdit.getNumBilletesOMonedasDeMil()+"', numeromonedas500='"+cierEdit.getNumMonedasDe500()+"', numeromonedas200='"+cierEdit.getNumMonedasDe200()+"', numeromonedas100='"+cierEdit.getNumMonedasDe100()+"', numeromonedas50='"+cierEdit.getNumMonedasDe50()+"', montoen100mil="+cierEdit.getMontoEnBilletes100Mil()+", montoen50mil="+cierEdit.getMontoEnBilletes50Mil()+", montoen20mil="+cierEdit.getMontoEnBilletes20Mil()+", montoen10mil="+cierEdit.getMontoEnBilletes10Mil()+", montoen5mil="+cierEdit.getMontoEnBilletes5Mil()+", montoen2mil="+cierEdit.getMontoEnBilletes2Mil()+", montoenmil="+cierEdit.getMontoEnBilletesOMonedasMil()+", montoen500="+cierEdit.getMontoEnMonedasDe500()+", montoen200="+cierEdit.getMontoEnMonedasDe200()+", montoen100="+cierEdit.getMontoEnMonedasDe100()+", montoen50="+cierEdit.getMontoEnMonedasDe50()+", Total_esperado='"+cierEdit.getTotal_esperado()+"', Dinero_en_caja='"+cierEdit.getDinero_caja()+"', Diferencia='"+cierEdit.getDiferencia()+"', Dinero_a_consignar='"+cierEdit.getDineroAConsignar()+"', Observaciones='"+cierEdit.getObservaciones()+"' where Id_cierre ="+cierEdit.getId());
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
}
