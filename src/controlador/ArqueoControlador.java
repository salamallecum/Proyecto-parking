package controlador;

import clasesDeApoyo.Conexion;
import static controlador.FacturaControlador.rutaImgDocuments;
import static controlador.FacturaControlador.rutaImgTickets;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Arqueo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.EditarArqueoDeCaja;
import vista.EditarCierreDeCaja;
import vista.GestionarArqueos;
import static vista.GestionarArqueos.codigoArqueo_update;
import static vista.GestionarArqueos.hayArqueoVisualizandose;
import static vista.GestionarArqueos.lbl_numeroDeArqueos;
import static vista.GestionarArqueos.lbl_perdidasEnArqueos;
import static vista.GestionarArqueos.modelo;
import static vista.GestionarArqueos.table_listaArqueos;


/**
 *
 * @author ALEJO
 */
public class ArqueoControlador {
    
   Arqueo arqueoConsultado = new Arqueo(0, "", 0, "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "", "");
   FacturaControlador factControla;
   CierreControlador cierreControla;
   int totalPerdidasArqueos;
   public String totalPerdidasArqueos_str;
   public String cantidadDeArqueosSistema;
   ArrayList diferenciasDeArqueos;
      
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
            pst3.setInt(4, arq.getUsuario());
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
        
    //Metodo que genera la fecha en la quese realizo el arqueo de caja       
    public String fecha_Arqueo(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String fecha_movVehiculo = dateFormat.format(date);
        return fecha_movVehiculo;
    }
    
    //Metodo que imprime el ticket de cuando se realizael arqueo de caja al inicio de turno
    public void generarTicketArqueoDeCaja(String codigoDeArqueo, boolean vistaPrevia){
               
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("codigo", codigoDeArqueo);
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgTickets));
           
           JasperReport reporte = null;
           
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/Arqueo.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
           if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setIconImage(getIconImagePDFUser());
                view.setTitle("Ticket de arqueo de caja");
                view.setVisible(true);
                           
                //Agregamos un evento para cuando el visor del reporte se cierre
                view.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    EditarCierreDeCaja.btn_verArqueoPrevio.setEnabled(true);
                }
                });
           
           }else{
               //Hace que se imprima directamente
               JasperPrintManager.printReport(jprint, false);
           } 

       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Ticket de Arqueo de Caja, revise la conexión de la impresora o contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar ticket de arqueo de caja de inicio de turno: " + ex);
       }
    }
    
    //Metodo que agrega el icono a la ventana de reporte PDF de arqueos
    public Image getIconImagePDFUser() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/preview.png"));
        return retValue;
    }
    
    //Metodo que carga el contenido de la tabla del Administrador de arqueos
    public void cargarTablaAdministradorDeArqueos(){
        
        //Cargamos los datos de la tabla
        try {
            modelo = new DefaultTableModel();
            table_listaArqueos.setModel(modelo);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "SELECT arqueos.fecha_arqueo, arqueos.codigo, usuarios.Usuario, arqueos.diferencia FROM arqueos INNER JOIN usuarios ON arqueos.Id_usuario = usuarios.Id_usuario");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modelo.addColumn("Fecha");
            modelo.addColumn("Codigo");
            modelo.addColumn("Usuario");
            modelo.addColumn("Diferencia ($)");

            int[] anchosTabla = {10,10,5,10};

            for(int x=0; x < cantidadColumnas; x++){
                table_listaArqueos.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
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
            
        //Agregamos la funcion de ver informacion de factura al hacer click sobre el registro de la tabla
        table_listaArqueos.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            int fila_point = table_listaArqueos.rowAtPoint(e.getPoint());
            int columna_point = 1;

            if(fila_point > -1){
                codigoArqueo_update = (String) modelo.getValueAt(fila_point, columna_point);
                generarInformacionDeArqueoEnFrame();  
            }
        }    
    });
    }

    //Metodo que genera la informacion de un arqueo en el jframe
    public void generarInformacionDeArqueoEnFrame(){

        if(!hayArqueoVisualizandose){
            hayArqueoVisualizandose = true;
            new EditarArqueoDeCaja().setVisible(true);
        } 
    } 

    //Metodo que consulta la información de un arqueo
    public Arqueo consultarInformacionDeUnArqueo(String codigoArq){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from arqueos where codigo = '" + codigoArq + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                arqueoConsultado.setId(rs.getInt("Id_arqueo"));
                arqueoConsultado.setCodigo(rs.getString("codigo"));
                arqueoConsultado.setBase_caja(rs.getString("base_caja"));
                arqueoConsultado.setUsuario(rs.getInt("Id_usuario"));
                arqueoConsultado.setNumBilletesDe100Mil(rs.getString("numerobilletes100mil"));
                arqueoConsultado.setNumBilletesDe50Mil(rs.getString("numerobilletes50mil"));
                arqueoConsultado.setNumBilletesDe20Mil(rs.getString("numerobilletes20mil"));
                arqueoConsultado.setNumBilletesDe10Mil(rs.getString("numerobilletes10mil"));
                arqueoConsultado.setNumBilletesDe5Mil(rs.getString("numerobilletes5mil"));
                arqueoConsultado.setNumBilletesDe2Mil(rs.getString("numerobilletes2mil"));
                arqueoConsultado.setNumBilletesOMonedasDeMil(rs.getString("numerobilletesMil"));
                arqueoConsultado.setNumMonedasDe500(rs.getString("numeromonedas500")); 
                arqueoConsultado.setNumMonedasDe200(rs.getString("numeromonedas200"));
                arqueoConsultado.setNumMonedasDe100(rs.getString("numeromonedas100"));
                arqueoConsultado.setNumMonedasDe50(rs.getString("numeromonedas50"));
                arqueoConsultado.setMontoTotalCaja(rs.getString("total_caja"));
                arqueoConsultado.setDiferenciaTotal(rs.getString("diferencia"));
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion de un arqueo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de un arqueo: " + e);
        }
        return arqueoConsultado;        
    }

    //Metodo que permite actualizar arqueos    
    public void actualizarArqueo(Arqueo arqEdit){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update arqueos set fecha_arqueo ='"+arqEdit.getFecha_arqueo()+"', Id_usuario='"+arqEdit.getUsuario()+"', base_caja='"+arqEdit.getBase_caja()+"', numerobilletes100mil='"+arqEdit.getNumBilletesDe100Mil()+"', numerobilletes50mil='"+arqEdit.getNumBilletesDe50Mil()+"', numerobilletes20mil='"+arqEdit.getNumBilletesDe20Mil()+"', numerobilletes10mil='"+arqEdit.getNumBilletesDe10Mil()+"', numerobilletes5mil='"+arqEdit.getNumBilletesDe5Mil()+"', numerobilletes2mil='"+arqEdit.getNumBilletesDe2Mil()+"', numerobilletesMil='"+arqEdit.getNumBilletesOMonedasDeMil()+"', numeromonedas500='"+arqEdit.getNumMonedasDe500()+"', numeromonedas200='"+arqEdit.getNumMonedasDe200()+"', numeromonedas100='"+arqEdit.getNumMonedasDe100()+"', numeromonedas50='"+arqEdit.getNumMonedasDe50()+"', montoen100mil="+arqEdit.getMontoEnBilletes100Mil()+", montoen50mil="+arqEdit.getMontoEnBilletes50Mil()+", montoen20mil="+arqEdit.getMontoEnBilletes20Mil()+", montoen10mil="+arqEdit.getMontoEnBilletes10Mil()+", montoen5mil="+arqEdit.getMontoEnBilletes5Mil()+", montoen2mil="+arqEdit.getMontoEnBilletes2Mil()+", montoenmil="+arqEdit.getMontoEnBilletesOMonedasMil()+", montoen500="+arqEdit.getMontoEnMonedasDe500()+", montoen200="+arqEdit.getMontoEnMonedasDe200()+", montoen100="+arqEdit.getMontoEnMonedasDe100()+", montoen50="+arqEdit.getMontoEnMonedasDe50()+", total_caja='"+arqEdit.getMontoTotalCaja()+"', diferencia='"+arqEdit.getDiferenciaTotal()+"' where Id_arqueo ="+arqEdit.getId());
            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al actualizar arqueo, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar actualizar un arqueo: " + e);  
        } 
    }
    
    //Metodo para eliminar un arqueo asociado a un cierre
    public void eliminarArqueo(String codigoArqueo){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from arqueos where codigo=?");
            ps1.setString(1, codigoArqueo);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar arqueo de un cierre!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar el arqueo de un cierre: " + e);
        }   
    }
    
    //Metodo que se encarga de generar las estadisticas dependiendo del filtro aplicado en la busqueda de los arqueos del gestor de arqueos
    public void generarEstadisticasMedianteUnCriterioDeterminado(String sentenciaSQL){
        
        factControla = new FacturaControlador();
        cierreControla = new CierreControlador();
                
        //Contamos los arqueos
        cantidadDeArqueosSistema = contarArqueosQueTienenUnCriterioEspecifico(sentenciaSQL);
                
        //Calculamos el total de perdidas por arqueos 
        //Obtenemos las diferencias de los arqueos para hacer calculo de perdidas por arqueos
        diferenciasDeArqueos = obtenerDiferenciasDeArqueosBajoAlgunCriterio(sentenciaSQL);
        
        //Calculamos el total de perdidas por arqueos teniendo en cuenta las diferencias de los arqueos obtenidos
        totalPerdidasArqueos = cierreControla.calcularTotalPerdidas(diferenciasDeArqueos);
        totalPerdidasArqueos_str = factControla.agregarFormatoMoneda(Integer.toString(totalPerdidasArqueos));
        
    }
    
    //Pinta de color verde el total de perdidas solo si este es menor o igual a cero pesos, de lo contrario, permanece de color rojo
    public void evaluarGravedadDePerdidas(){
        if(totalPerdidasArqueos <= 0){
            lbl_perdidasEnArqueos.setForeground(Color.GREEN);
        }else{
            lbl_perdidasEnArqueos.setForeground(Color.red);
        }
    } 
    
    //Metodo que cuenta la cantidad de arqueos generados
    public String contarArqueosQueTienenUnCriterioEspecifico(String sql){
        
        String cantidadArqueos = "";
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "select count(*) from arqueos where 1=1" + sql);
            ResultSet rs3 = pst3.executeQuery();

            if(rs3.next()){
                int cant_arqueos = rs3.getInt("count(*)");
                cantidadArqueos = Integer.toString(cant_arqueos);
            }
            cn3.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al contar arqueos generados en el turno, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al contar loc arqueos generados en el turno: " + e);
        }
        return cantidadArqueos;
    }
    
    //Metodo que consulta las diferencias de los arqueos utilizando un criterio de busqueda
    public ArrayList obtenerDiferenciasDeArqueosBajoAlgunCriterio(String sentenciaSQL){
        
        ArrayList diferencias = new ArrayList();
        
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "SELECT diferencia FROM arqueos WHERE 1=1" + sentenciaSQL);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                
                String valor = rs2.getString("diferencia");
                
                //Le quitamos el formato moneda a cada monto de diferencia
                valor = factControla.quitarFormatoMoneda(valor);
                
                diferencias.add(valor);
            }
            cn2.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al obtener diferencias de arqueos, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar obtener las diferencias de los arqueos generados en el turno. " + e);
        }
        return diferencias;
    }
    
    //Metodo que busca un cierre teniendo en cuenta uno o varios criterios de busqueda
    public void buscarArqueo(String sentenciaSql){
        
        try{
            String [] titulos = {"Fecha", "Codigo", "Usuario", "Diferencia ($)"};
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(sentenciaSql);
            ResultSet rs6 = pst6.executeQuery(sentenciaSql);
            
            String[] fila = new String[4];
            
            while(rs6.next()){
                fila[0] = rs6.getString("arqueos.fecha_arqueo");
                fila[1] = rs6.getString("arqueos.codigo");
                fila[2] = rs6.getString("usuarios.Usuario");
                fila[3] = rs6.getString("arqueos.diferencia");
                modelo.addRow(fila);
            }
            table_listaArqueos.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda de arqueos!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar los arqueos. " + ex);
        }
    }
    
    //Metodo que genera el reporte pdf del consolidado de arqueos del sistema
    public void generarReporteConsolidadoDeArqueos(String sql){
                       
        try{
           
           Connection cn3 = Conexion.conectar();

           Map parametro = new HashMap();
           parametro.clear();
           parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgDocuments));
           parametro.put("numarqueos", cantidadDeArqueosSistema);
           parametro.put("perdidas", totalPerdidasArqueos_str);
           parametro.put("sql", sql);
                    
           JasperReport reporte = null;
           reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ConsolidadoArqueos.jasper"));

           JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
           
            //Da una vista previa del ticket
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setIconImage(getIconImagePDFUser());
            view.setTitle("Consolidado de arqueos de caja");
            view.setVisible(true);

            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                GestionarArqueos.btn_generaPDFArqueos.setEnabled(true);
            }
            });
           
       }catch(JRException ex){
           JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Consolidado de Arqueos de Caja, contacte al administrador!!");
           log.fatal("ERROR - Se ha producido un error al intentar generar reporte pdf de consolidado de arqueos: " + ex);
       }
    }
}       

    