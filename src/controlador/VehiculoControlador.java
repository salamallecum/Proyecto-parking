package controlador;

import clasesDeApoyo.Conexion;
import com.barcodelib.barcode.QRCode;
import static controlador.UsuarioControlador.rutaImgReporteAColor;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Vehiculo;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import vista.GestionarBicisyOtros;
import static vista.GestionarBicisyOtros.Table_listaBicisYOtros;
import vista.MenuAdministrador;
import vista.PanelVehiculos;
import static vista.PanelVehiculos.modelo;
import static vista.PanelVehiculos.Table_listaVehiculos;
import static vista.PanelVehiculos.dueño;
import static vista.PanelVehiculos.placa;
import static vista.GestionarBicisyOtros.tipoIdentificacion;
import static vista.GestionarBicisyOtros.identificacion;
import static vista.GestionarBicisyOtros.dueñoBiciUOtro;
import static vista.GestionarBicisyOtros.modeloBicisUOtros;
import static vista.GestionarBicisyOtros.tipoVehiculoBiciUOtro;
import static vista.PanelVehiculos.tipoVehiculo;


/**
 *
 * @author ALEJO
 */
public class VehiculoControlador extends Thread{
    
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", "", "", "", "", 0, 0, 0);
    ParqueaderoControlador parqControlador;
    ParametroControlador paramControla = new ParametroControlador();
    
    
    //Definimos las propiedades que tendran los codigos qr
    int udm = 0;
    int resol = 72;
    float mi = 0.000f;
    float md = 0.000f;
    float ms = 0.000f;
    float min = 0.000f;
    int rot = 0;
    float tam = 5.000f;
    
    //Rutas y carpetas de qrs para consulta del usuario
    String rutaQrs = System.getProperty("user.dir")+"\\qrCodes";
    String rutaQrsCarros = rutaQrs+"\\Carros";
    String rutaQrsMotos = rutaQrs+"\\Motos";
    String rutaQrsBicis = rutaQrs+"\\Bicicletas";
    File carpetaQrs = new File(rutaQrs);
    File carpetaQrsCarros = new File(rutaQrsCarros);
    File carpetaQrsMotos = new File(rutaQrsMotos);
    File carpetaQrsBicis = new File(rutaQrsBicis);
    
    //Variables del proceso de registro y actualizacion de qrs vehiculares
    Thread hilo4 = new Thread(this);
    private boolean hiloRegistroTicketsQRCarrosYMotosSuspendido;
    Thread hilo5 = new Thread(this);
    private boolean hiloActualizacionTicketsQRCarrosYMotosSuspendido;
    Thread hilo6 = new Thread(this);
    private boolean hiloRegistroTicketsQRBicisYOtrosSuspendido;
    Thread hilo7 = new Thread(this);
    private boolean hiloActualizacionTicketsQRBicisYOtrosSuspendido;
             
    private final Logger log = Logger.getLogger(VehiculoControlador.class);
    private final URL url = VehiculoControlador.class.getResource("/clasesDeApoyo/Log4j.properties");
    
    //Constructor
    public VehiculoControlador() {}   
    
    //Metodo que evalua la existencia de un vehiculo previamente en el sistema (devuelve 1 si el vehiculo existe y 0 si es lo contrario)
    public int evaluarExistenciaDelVehiculo(String consecutivoQr, String placa, String tipoIdentificacion, String noIdentificacion){
               
        int resultado = 0;
        //Valida si el vehiculo ingresado existe previamente en la BD  
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            ResultSet rs;
            String sql;
            if(consecutivoQr != null){
                sql = "select 1 resultado from vehiculos where Qr_consecutivo= '"+consecutivoQr+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    resultado = rs.getInt("resultado");
                    cn.close();              
                }else{
                    resultado = 0;
                }  
            }else if(placa != null){
                sql = "select 1 resultado from vehiculos where Placa= '"+placa+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    resultado = rs.getInt("resultado");
                    cn.close();              
                }else{
                    resultado = 0;
                }
            }else if(tipoIdentificacion != null && noIdentificacion != null){
                sql = "select 1 resultado from vehiculos where Tipo_Identificacion= '"+tipoIdentificacion+"' and No_identificacion= '"+noIdentificacion+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    resultado = rs.getInt("resultado");
                    cn.close();              
                }else{
                    resultado = 0;
                }
            }    
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡Error al evaluae existencia del vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un vehiculo en el sistema: " + ex); 
        }
        
        return resultado;
    }
        
    //Metodo que registra un vehiculo en el sistema
    public void crearVehiculo(Vehiculo veh){
        
        //Inserta el registro en la base de datos
        try {               
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            ResultSet rs;
            String sql;
            if(!veh.getPlaca().equals("")){
                sql = "insert into vehiculos (Id_vehiculo, Qr_consecutivo, Placa, Propietario, TipoVehiculo, Id_parqueadero, Id_convenio, Id_tarifa) values (?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
               
                pst.setInt(1, veh.getId());
                pst.setString(2, veh.getQr_consecutivo());
                pst.setString(3, veh.getPlaca());
                pst.setString(4, veh.getPropietario());
                pst.setString(5, veh.getTipoVehiculo());
                pst.setInt(6, veh.getId_parqueadero());
                pst.setInt(7, veh.getId_convenio());
                pst.setInt(8, veh.getId_tarifa());          
                pst.executeUpdate();
                cn.close();
                
            }else if(!veh.getTipoIdentificacion().equals("") && !veh.getNumIdentificacion().equals("")){
                sql = "insert into vehiculos (Id_vehiculo, Qr_consecutivo, Tipo_Identificacion, No_identificacion, Propietario, TipoVehiculo, Color, Id_parqueadero, Id_convenio, Id_tarifa) values (?,?,?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
               
                pst.setInt(1, veh.getId());
                pst.setString(2, veh.getQr_consecutivo());
                pst.setString(3, veh.getTipoIdentificacion());
                pst.setString(4, veh.getNumIdentificacion());
                pst.setString(5, veh.getPropietario());
                pst.setString(6, veh.getTipoVehiculo());
                pst.setString(7, veh.getColor());
                pst.setInt(8, veh.getId_parqueadero());
                pst.setInt(9, veh.getId_convenio());
                pst.setInt(10, veh.getId_tarifa());           
                pst.executeUpdate();
                cn.close();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡Error al crear vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al crear un vehiculo en el sistema: " + e);
        }
    }
    
    //Metodo que genera el reporte PDF de carros y motos registrados
    public void generarReportePDFdeCarrosYMotosRegistradas(String sql){
        
        try{
            Connection cn3 = Conexion.conectar();
            
            //Enviamos la ruta de la imagen y la sentencia sql como parametros
            Map parametro = new HashMap();
            parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgReporteAColor));
            parametro.put("sql", sql);

            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoCarrosYMotos.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(getIconImagePDFVehiculos());
            view.setTitle("Reporte de vehiculos registrados");
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
            
            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
               MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
               PanelVehiculos.btn_generarReporteVehiculos.setEnabled(true);
            }
            });
            

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡Error al generar reporte de vehiculos!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar generar reporte PDF de los vehiculos del sistema: " + ex);
        }
    }
    
     //Metodo que genera el reporte PDF de bicis y otros vehiculos registrados
    public void generarReportePDFdeBicisYOtrosRegistradas(String sql){
         
        try{
            Connection cn3 = Conexion.conectar();
            
            //Enviamos la ruta de la imagen y la sentencia sql como parametros
            Map parametro = new HashMap();
            parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgReporteAColor));
            parametro.put("sql", sql);

            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoBicis.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, cn3);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(getIconImagePDFVehiculos());
            view.setTitle("Reporte de vehiculos registrados");
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
            
            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
               MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false;
                GestionarBicisyOtros.btn_generarReporteBicisyOtros.setEnabled(true);
            }
            });
            

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡Error al generar reporte de vehiculos!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar generar reporte PDF de los vehiculos del sistema: " + ex);
        }
    }
    
    
    //Metodo que genera el reporte PDF del listado de los vehiculos registrados
    public Image getIconImagePDFVehiculos() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Carro.png"));
        return retValue;
    }
    
    //Metodo que busca uno o mas carros o motos teniendo en cuenta varios criterios de busqueda
    public void buscarCarrosYMotos(String sentenciaSql){
        
        try{
            String [] titulos = {"Placa", "Propietario", "Tipo", "N° Parq", "Convenio", "Tarifa"};
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(sentenciaSql);
            ResultSet rs6 = pst6.executeQuery(sentenciaSql);
            
            String[] fila = new String[6];
            
            while(rs6.next()){
                fila[0] = rs6.getString("Ve.Placa");
                fila[1] = rs6.getString("Ve.Propietario");
                fila[2] = rs6.getString("Ve.TipoVehiculo");
                fila[3] = rs6.getString("Parq.Nombre_parqueadero");
                fila[4] = rs6.getString("Conv.Nombre_convenio");
                fila[5] = rs6.getString("Tar.Nombre_tarifa");
                modelo.addRow(fila);
                
            }
            Table_listaVehiculos.setModel(modelo);
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡Error de busqueda de Carros y motos!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar buscar los carros o motos. " + ex);
        }
    }
    
     //Metodo que busca uno o mas bicicletas u otros teniendo en cuenta varios criterios de busqueda
    public void buscarBicicletasUOtros(String sentenciaSql){
        
        try{
            String [] titulos = {"Tipo identif", "N° identificación", "Propietario", "Tipo", "N° Parq", "Color", "Convenio", "Tarifa"};
            modeloBicisUOtros = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(sentenciaSql);
            ResultSet rs6 = pst6.executeQuery(sentenciaSql);
            
            String[] fila = new String[8];
            
            while(rs6.next()){
                fila[0] = rs6.getString("Ve.Tipo_Identificacion");
                fila[1] = rs6.getString("Ve.No_identificacion");
                fila[2] = rs6.getString("Ve.Propietario");
                fila[3] = rs6.getString("Ve.TipoVehiculo");
                fila[4] = rs6.getString("Parq.Nombre_parqueadero");
                fila[5] = rs6.getString("Ve.Color");
                fila[6] = rs6.getString("Conv.Nombre_convenio");
                fila[7] = rs6.getString("Tar.Nombre_tarifa");
                modeloBicisUOtros.addRow(fila);
                
            }
            Table_listaBicisYOtros.setModel(modeloBicisUOtros);
            ajustarTamañoColumnasTablaBicisYOtros();
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡Error de busqueda de Bicicletas y otros!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar buscar las bicicletas u otros. " + ex);
        }
    }
   
    //Metodo que carga la tabla de vehiculos por Default al abrir el panel de vehiculos
    public void cargarTablaDeVehiculosPorDefault(){
        
        //Cargamos los datos de la tabla vehiculos
        try {
            modelo = new DefaultTableModel(){
                //Permite que no se puedan editar las filas de la tabla
                @Override
                public boolean isCellEditable(int row, int column) {
                    if(column >= 0){
                        return false;
                    }else{
                        return false;
                    }
                }
            };
            
            Table_listaVehiculos.setModel(modelo);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "SELECT Ve.Placa, Ve.Propietario, Ve.TipoVehiculo, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa WHERE Ve.TipoVehiculo IN ('AUTOMOVIL', 'MOTO')");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modelo.addColumn("Placa");
            modelo.addColumn("Propietario");
            modelo.addColumn("Tipo");
            modelo.addColumn("N° Parq");
            modelo.addColumn("Convenio");
            modelo.addColumn("Tarifa");       
                        
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    
                        filas[i] = rs.getObject(i + 1); 
                }
                modelo.addRow(filas);
            }
            cn.close();                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡Error al llenar tabla de vehiculos!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar cargar la tabla de vehiculos por default: " + ex);
        }
    }
    
    //Metodo que carga la tabla de la ventana de biciletas y otros vehiculos
    public void cargarTablaDeBicicletasYOtrosVehiculos(){
        
        //Cargamos los datos de la tabla
        try {
            modeloBicisUOtros = new DefaultTableModel(){
                //Permite que no se puedan editar las filas de la tabla
                @Override
                public boolean isCellEditable(int row, int column) {
                    if(column >= 0){
                        return false;
                    }else{
                        return false;
                    }
                }
            };
            
            Table_listaBicisYOtros.setModel(modeloBicisUOtros);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "SELECT Ve.Tipo_Identificacion, Ve.No_identificacion, Ve.Propietario, Ve.TipoVehiculo, Parq.Nombre_parqueadero, Ve.Color, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa WHERE Ve.TipoVehiculo IN ('BICICLETA', 'PATINETA', 'OTRO')");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modeloBicisUOtros.addColumn("Tipo identif");
            modeloBicisUOtros.addColumn("N° identificación");
            modeloBicisUOtros.addColumn("Propietario");
            modeloBicisUOtros.addColumn("Tipo");
            modeloBicisUOtros.addColumn("N° Parq");
            modeloBicisUOtros.addColumn("Color");
            modeloBicisUOtros.addColumn("Convenio");
            modeloBicisUOtros.addColumn("Tarifa");   
            
            ajustarTamañoColumnasTablaBicisYOtros();
                        
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    
                        filas[i] = rs.getObject(i + 1); 
                }
                modeloBicisUOtros.addRow(filas);
            }
            cn.close();                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡Error al llenar tabla de bicicletas y otros!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar cargar la tabla de bicicletas y otros por default: " + ex);
        }
    }
    
    //Metodo para verificar que el vehiculo no se encuentre en parqueadero
    public boolean verificarSiVehiculoEstaEnParqueadero(String qr_consecutivo, String placa, String tipoIdentificacion, String numIdentificacion){
        
        boolean vehiculoEnParqueadero = false;
        
        //Verifica que el vehiculo no se encuentre en parqueadero
        try {
            Connection cn = Conexion.conectar();
            String sql = "";
            if(qr_consecutivo != null){
                sql = " select parq.Esta_en_parqueadero FROM parqueaderos parq, vehiculos veh WHERE parq.Tipo_Identificacion = veh.Tipo_Identificacion and parq.No_identificacion = veh.No_identificacion and veh.Qr_consecutivo = '"+qr_consecutivo+"' and parq.Esta_en_parqueadero='Si'";
            }else if(placa != null){
                sql = "select Esta_en_parqueadero from parqueaderos where Placa = '" + placa + "' AND Esta_en_parqueadero='Si'";
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = "select Esta_en_parqueadero from parqueaderos where Tipo_Identificacion = '" + tipoIdentificacion + "' AND No_identificacion = '" + numIdentificacion+ "' AND Esta_en_parqueadero='Si'";
            }
            
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                vehiculoEnParqueadero = true;
            }else{
                vehiculoEnParqueadero = false;
            }
            cn.close();
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡Error al verficar si el vehiculo esta en el parqueadero!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar validar si el vehiculo esta en parqueadero: " + e);
        } 
        
        return vehiculoEnParqueadero;
    }

    //Metodo para eliminar un vehiculo del sistema
    public void eliminarVehiculo(int idDelVehiculo){
        
        try{
            Connection cn1 = Conexion.conectar(); 
            PreparedStatement ps1 = null;
            String sql = "delete from vehiculos where Id_vehiculo=?";            
            
            ps1 = cn1.prepareStatement(sql);
            ps1.setInt(1, idDelVehiculo);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al eliminar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar eliminar el vehiculo: "+ placa + e);
        }   
    }

    //Metodo que consulta la ifnromación de un vehiculo teniendo en cuenta su consecutivo qr o placa
    public Vehiculo consultarInformacionDeUnVehiculo(String consecutivoQR, int idVehiculo, String placaDelVehiculo, String tipoIdentificacion, String numIdentificacion){
        
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            String sql;
            PreparedStatement pst;
            ResultSet rs;
            
            if(consecutivoQR != null){
                sql = "select Id_vehiculo, Placa, Tipo_Identificacion, No_identificacion, Propietario, TipoVehiculo, Color, Id_parqueadero, Id_convenio, Id_tarifa from vehiculos where Qr_consecutivo = '"+consecutivoQR+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    vehiculoConsultado.setQr_consecutivo(consecutivoQR);
                    vehiculoConsultado.setId(rs.getInt("Id_vehiculo"));
                    vehiculoConsultado.setPlaca(rs.getString("Placa"));
                    vehiculoConsultado.setTipoIdentificacion(rs.getString("Tipo_Identificacion"));
                    vehiculoConsultado.setNumIdentificacion(rs.getString("No_identificacion"));
                    vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                    vehiculoConsultado.setTipoVehiculo(rs.getString("TipoVehiculo"));
                    vehiculoConsultado.setColor(rs.getString("Color"));
                    vehiculoConsultado.setId_parqueadero(rs.getInt("Id_parqueadero"));
                    vehiculoConsultado.setId_convenio(rs.getInt("Id_convenio"));
                    vehiculoConsultado.setId_tarifa(rs.getInt("Id_tarifa")); 
                    cn.close();              
                }else{
                    vehiculoConsultado = null;
                }
                
            }else if(idVehiculo != 0){
                sql = "select SUBSTR(Qr_consecutivo,7) Consecutivo_qr, Placa, Tipo_Identificacion, No_identificacion, Propietario, TipoVehiculo, Id_parqueadero, Id_convenio, Id_tarifa from vehiculos where Id_vehiculo = "+idVehiculo;
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    vehiculoConsultado.setQr_consecutivo(rs.getString("Consecutivo_qr"));
                    vehiculoConsultado.setPlaca(rs.getString("Placa"));
                    vehiculoConsultado.setTipoIdentificacion(rs.getString("Tipo_Identificacion"));
                    vehiculoConsultado.setNumIdentificacion(rs.getString("No_identificacion"));
                    vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                    vehiculoConsultado.setTipoVehiculo(rs.getString("TipoVehiculo"));
                    vehiculoConsultado.setId_parqueadero(rs.getInt("Id_parqueadero"));
                    vehiculoConsultado.setId_convenio(rs.getInt("Id_convenio"));
                    vehiculoConsultado.setId_tarifa(rs.getInt("Id_tarifa")); 
                    cn.close();              
                }else{
                    vehiculoConsultado = null;
                }
            
            }else if(placaDelVehiculo != null){
                sql = "select Id_vehiculo, SUBSTR(Qr_consecutivo,7) Consecutivo_qr, Placa, Propietario, TipoVehiculo, Id_parqueadero, Id_convenio, Id_tarifa from vehiculos where Placa = '"+placaDelVehiculo+ "'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    vehiculoConsultado.setId(rs.getInt("Id_vehiculo"));
                    vehiculoConsultado.setQr_consecutivo(rs.getString("Consecutivo_qr"));
                    vehiculoConsultado.setPlaca(rs.getString("Placa"));
                    vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                    vehiculoConsultado.setTipoVehiculo(rs.getString("TipoVehiculo"));
                    vehiculoConsultado.setId_parqueadero(rs.getInt("Id_parqueadero"));
                    vehiculoConsultado.setId_convenio(rs.getInt("Id_convenio"));
                    vehiculoConsultado.setId_tarifa(rs.getInt("Id_tarifa")); 
                    cn.close();              
                }else{
                    vehiculoConsultado = null;
                }
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = "select Id_vehiculo, SUBSTR(Qr_consecutivo,7) Consecutivo_qr, Tipo_Identificacion, No_identificacion, Propietario, TipoVehiculo, Color, Id_parqueadero, Id_convenio, Id_tarifa from vehiculos where Tipo_Identificacion = '"+tipoIdentificacion+ "' and No_identificacion = '"+numIdentificacion+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    vehiculoConsultado.setId(rs.getInt("Id_vehiculo"));
                    vehiculoConsultado.setQr_consecutivo(rs.getString("Consecutivo_qr"));
                    vehiculoConsultado.setTipoIdentificacion(rs.getString("Tipo_Identificacion"));
                    vehiculoConsultado.setNumIdentificacion(rs.getString("No_identificacion"));
                    vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                    vehiculoConsultado.setTipoVehiculo(rs.getString("TipoVehiculo"));
                    vehiculoConsultado.setColor(rs.getString("Color"));
                    vehiculoConsultado.setId_parqueadero(rs.getInt("Id_parqueadero"));
                    vehiculoConsultado.setId_convenio(rs.getInt("Id_convenio"));
                    vehiculoConsultado.setId_tarifa(rs.getInt("Id_tarifa")); 
                    cn.close();              
                }else{
                    vehiculoConsultado = null;
                }
            }         
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡Error al cargar informacion del vehiculo seleccionado!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar consultar la informacion de un vehiculo: " + e);
        }
        return vehiculoConsultado;        
    }

    //Metodo que libera el elimina el vehiculo a editar para que pueda ser modificado
    public void liberarVehiculo(String placa, String tipoIdentificacion, String numIdentificacion){
         
        try{
            Connection cn3 = Conexion.conectar();
            String sql = "";
            
            if(placa != null){
                sql = "update vehiculos set Placa='000000' where Placa='"+placa+"'";
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = "update vehiculos set Tipo_Identificacion='X', No_identificacion='000000' where Tipo_Identificacion='"+tipoIdentificacion+"' and No_identificacion='"+numIdentificacion+"'";
            }
                       
            PreparedStatement pst3 = cn3.prepareStatement(sql);
            pst3.executeUpdate();
            cn3.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al liberar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al liberar un vehiculo: " + e);
        }
    }
       
    //Metodo que permite identificar si el vehiculo en cuestion esta involucrado en alguna factura (devuelve 1 si esta en algun proceso de factuacion y 0 si no)
    public int consultarSiVehiculoTieneFacturasAbiertas(int idVehiculo, String placa, String tipoIdentificacion, String identificacion){

        int vehiculoTieneFacturasAbiertas = 0;
        //Valida si el vehiculo tiene algun proceso de facturación pendiente 
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            ResultSet rs = null;
            String sql;
            if(idVehiculo != 0){
                sql = "select 1 resultado from facturas where Id_vehiculo= "+idVehiculo+" and Estado_fctra = 'Abierta' ";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                
            }else if(placa != null){
                sql = "select 1 resultado from facturas where Placa= '"+placa+"' and Estado_fctra = 'Abierta' ";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
               
            }else if(tipoIdentificacion != null && identificacion != null){
                sql = "select 1 resultado from facturas where Tipo_Identificacion= '"+tipoIdentificacion+"' and No_identificacion= '"+identificacion+"' and Estado_fctra = 'Abierta'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();     
            } 
            
            if(rs.next()){
                vehiculoTieneFacturasAbiertas = rs.getInt("resultado");
                cn.close();              
            }else{
                vehiculoTieneFacturasAbiertas = 0;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "¡¡Error al revisar facturación abierta de vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
           log.fatal("ERROR - Se ha producido un error al intentar revisar facturación abierta de vehiculo: " + ex);
        }
        return vehiculoTieneFacturasAbiertas;
    }

    //Metodo que permite la edicion de un vehiculo en el sistema
    public void actualizarVehiculo(Vehiculo vehAActualizar){
        
        try{
            Connection cn = Conexion.conectar();
            String sql = "";
            
            if(!vehAActualizar.getPlaca().equals("")){
                sql = "update vehiculos set Qr_consecutivo ='"+vehAActualizar.getQr_consecutivo()+"',Placa ='"+vehAActualizar.getPlaca()+"',Propietario='"+vehAActualizar.getPropietario()+"',TipoVehiculo='"+vehAActualizar.getTipoVehiculo()+"',Id_parqueadero="+vehAActualizar.getId_parqueadero()+",Id_convenio="+vehAActualizar.getId_convenio()+",Id_tarifa="+vehAActualizar.getId_tarifa()+" where Id_vehiculo="+vehAActualizar.getId();             
            }else if(!vehAActualizar.getTipoIdentificacion().equals("") && !vehAActualizar.getNumIdentificacion().equals("")){
                sql = "update vehiculos set Qr_consecutivo ='"+vehAActualizar.getQr_consecutivo()+"',Tipo_Identificacion ='"+vehAActualizar.getTipoIdentificacion()+"',No_identificacion ='"+vehAActualizar.getNumIdentificacion()+"',Propietario='"+vehAActualizar.getPropietario()+"',TipoVehiculo='"+vehAActualizar.getTipoVehiculo()+"',Color='"+vehAActualizar.getColor()+"',Id_parqueadero="+vehAActualizar.getId_parqueadero()+",Id_convenio="+vehAActualizar.getId_convenio()+",Id_tarifa="+vehAActualizar.getId_tarifa()+" where Id_vehiculo="+vehAActualizar.getId();
            }
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.executeUpdate();
            cn.close(); 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al actualizar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al modificar un vehiculo del sistema: " + e);
        } 
    }
    
    //Metodo que recarga el vehiculo al cerrar la ventana de edición del mismo (sea carro moto bicicleta u otro) 
    public void recargarVehiculo(int idDelVehiculo, String placa, String tipoIdentificacion, String numIdentificacion){
        
        try{
            Connection cn9 = Conexion.conectar();
            String sql = "";
            
            if(placa != null){
                sql = "update vehiculos set Placa ='"+placa+"' where Id_vehiculo ='"+idDelVehiculo+"'";
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = "update vehiculos set Tipo_Identificacion ='"+tipoIdentificacion+"', No_identificacion ='"+numIdentificacion+"' where Id_vehiculo = "+idDelVehiculo;
            }
            
            PreparedStatement pst9 = cn9.prepareStatement(sql);

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al recargar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
        } 
    }
    
    //Metodo que consulta  el id del parqueadero que esta ocupando un vehiculo
    public int consultarIdParqQueOcupaUnVehiculo(String placa, String tipoIdentificacion, String numIdentificacion){
       
        int idParq = 0; 
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            ResultSet rs;
            String sql;
            
            if(placa != null){
                sql = "select Id_parqueadero from parqueaderos where Placa = '" + placa + "'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    idParq = rs.getInt("Id_parqueadero");
                    cn.close();              
                }else{
                    log.fatal("ERROR - No se ha encontrado el ID del parqueadero que ocupa un vehiculo(carro o moto)");
                }
                
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = "select Id_parqueadero from parqueaderos where Tipo_Identificacion= '"+tipoIdentificacion+"' and No_identificacion= '"+numIdentificacion+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    idParq = rs.getInt("Id_parqueadero");
                    cn.close();              
                }else{
                    log.fatal("ERROR - No se ha encontrado el ID del parqueadero que ocupa un vehiculo(bici)");
                }
            }  
        
        }catch (SQLException ex){ 
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar parqueadero que ocupa un vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al consultar el ID del parqueadero que ocupa un vehiculo: " + ex); 
        } 
        return idParq;
    }
    
     //Metodo que consulta  el id de un vehiculo
    public int consultarIdDeUnVehiculo(String placa, String tipoIdentificacion, String numIdentificacion){
       
        int idVehi = 0; 
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            ResultSet rs = null;
            String sql = "select Id_vehiculo from vehiculos";
            
            if(placa != null){
                sql = sql+" where Placa = '" + placa + "'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                                
            }else if(tipoIdentificacion != null && numIdentificacion != null){
                sql = sql+" where Tipo_Identificacion= '"+tipoIdentificacion+"' and No_identificacion= '"+numIdentificacion+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();  
            } 
            
            if(rs.next()){
                idVehi = rs.getInt("Id_vehiculo");                   
            }
            cn.close();
        
        }catch (SQLException ex){ 
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar el id de un vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al consultar el ID de un vehiculo: " + ex); 
        } 
        return idVehi;
    }
    
    //Metodo que se encarga de generar el codigo Qr de un vehiculo a partir de un texto de entrada
    public void generarQR(String nombreArchivo, String contenido, String tipoVehiculo){
            
        String qrParaConsulta = "";
        //Verificamos la existencia de las carpetas que guardan los codigos qr
        if(!carpetaQrs.exists()){
            crearCarpeta(carpetaQrs);
        }
        if(!carpetaQrsCarros.exists()){
            crearCarpeta(carpetaQrsCarros);
        }
        if(!carpetaQrsMotos.exists()){
            crearCarpeta(carpetaQrsMotos);
        }
        if(!carpetaQrsBicis.exists()){
            crearCarpeta(carpetaQrsBicis);
        }
        
        try{
            //Creamos el codigo qr
            QRCode codigoQr = new QRCode();
            //Asignamos propiedades al codigo qr
            codigoQr.setData(contenido);
            codigoQr.setDataMode(QRCode.MODE_BYTE);
            codigoQr.setUOM(udm);
            codigoQr.setLeftMargin(mi);
            codigoQr.setResolution(resol);
            codigoQr.setRightMargin(md);
            codigoQr.setTopMargin(ms);
            codigoQr.setBottomMargin(min);
            codigoQr.setRotate(rot);
            codigoQr.setModuleSize(tam);

            //Evaluamos que tipo de vehiculo genero el qr para guardarlo en la carpeta correspondiente
            if(tipoVehiculo.equals("AUTOMOVIL")){
                qrParaConsulta = rutaQrsCarros+"/"+nombreArchivo+".gif";
            }else if(tipoVehiculo.equals("MOTO")){
                qrParaConsulta = rutaQrsMotos+"/"+nombreArchivo+".gif";
            }else if(tipoVehiculo.equals("BICICLETA") || tipoVehiculo.equals("PATINETA") || tipoVehiculo.equals("OTRO")){
                qrParaConsulta = rutaQrsBicis+"/"+nombreArchivo+".gif";
            }
            codigoQr.renderBarcode(qrParaConsulta);                     
               
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar codigo qr de vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al generar el codigo qr: " + e);
        }
    }
    
    //Metodo que se encarga de actualizar en la carpeta qrCodes, el codigo qr de un vehiculo
    public void actualizarQR(String tipoVehiculoABuscar, String tipoVehiculo, String placaABuscar, String placa, String contenido, String tipoIdentificacionABuscar, String numIdentificacionABuscar, String tipoIdentificacion, String numIdentificacion, String propietarioABuscar, String propietario){
        
        String infoQrNvo = "";
        String qrABuscar = "";
        String qrNuevo = "";
        
        if(placaABuscar != null && placa != null){
            qrABuscar = placaABuscar+" - "+propietarioABuscar; 
            qrNuevo = placa+" - "+propietario;
        }else if(tipoIdentificacionABuscar != null && tipoIdentificacion != null && numIdentificacionABuscar != null && numIdentificacion != null){
            qrABuscar = tipoIdentificacionABuscar+numIdentificacionABuscar+" - "+propietarioABuscar;
            qrNuevo = tipoIdentificacion+numIdentificacion+" - "+propietario;
        }
        
        int cantidadQrs;
        File[] listadoDeQrs;       
            
        if(!tipoVehiculoABuscar.equals(tipoVehiculo)){

            //Buscamos el codigo qr previamente generado para eliminarlo
            if(tipoVehiculoABuscar.equals("AUTOMOVIL")){

                //Buscamos el codigo qr en la carpeta de qrs de carros para eliminarlo
                if(carpetaQrsCarros.exists() && carpetaQrsCarros.isDirectory()){
                    listadoDeQrs = carpetaQrsCarros.listFiles();

                    //Contamos cuantos codigos qr tiene la carpeta de qrs
                    cantidadQrs = contarCodigosQr(listadoDeQrs);

                    if(cantidadQrs > 0){
                        for(File qr : listadoDeQrs){
                            if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                                //Eliminamos el codigo qr previamente existente
                                qr.delete();
                            }
                        }
                    }              
                }

            }else if(tipoVehiculoABuscar.equals("MOTO")){

                //Buscamos el codigo qr en la carpeta de qrs de motos para eliminarlo
                if(carpetaQrsMotos.exists() && carpetaQrsMotos.isDirectory()){
                    listadoDeQrs = carpetaQrsMotos.listFiles();

                    //Contamos cuantos codigos qr tiene la carpeta de qrs
                    cantidadQrs = contarCodigosQr(listadoDeQrs);

                    if(cantidadQrs > 0){
                        for(File qr : listadoDeQrs){
                            if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                                //Eliminamos el codigo qr previamente existente
                                qr.delete();
                            }
                        }
                    }              
                }
            }

            //Actalizamos los nvos qrs para que esten en sus respectivas carpetas
            //Validamos que tipo de vehiculo es al que se le quiere crear el codigo qr actualizado
            if(tipoVehiculo.equals("AUTOMOVIL") || tipoVehiculo.equals("MOTO")){

                //Reanudamos el proceso actualizacion de ticket qr vehicular
                reanudarProcesoDeActualizaciónQRCarrosYMotos();

                //Generamos el nuevo contenido del codigo qr a generar
                if(!placaABuscar.equals(placa)){
                   infoQrNvo = placa+contenido;    
                }else{
                   infoQrNvo = placaABuscar+contenido; 
                }

                if(tipoVehiculo.equals("AUTOMOVIL")){
                    //Generamos el codigo qr actualizado 
                    if(carpetaQrsCarros.exists() && carpetaQrsCarros.isDirectory()){
                        generarQR(qrNuevo, infoQrNvo, tipoVehiculo);               
                    }else{
                        crearCarpeta(carpetaQrsCarros);
                        generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                    }

                }else if(tipoVehiculo.equals("MOTO")){
                   //Generamos el codigo qr actualizado 
                    if(carpetaQrsMotos.exists() && carpetaQrsMotos.isDirectory()){
                        generarQR(qrNuevo, infoQrNvo, tipoVehiculo);               
                    }else{
                        crearCarpeta(carpetaQrsMotos);
                        generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                    }
                }
            }

        }else{
            //Validamos que tipo de vehiculo es al que se le quiere actualizar el codigo qr
            if(tipoVehiculo.equals("AUTOMOVIL") || tipoVehiculo.equals("MOTO")){

                //Reanudamos el proceso actualizacion de ticket qr vehicular
                reanudarProcesoDeActualizaciónQRCarrosYMotos();

                //Generamos el nuevo contenido del codigo qr a generar
                if(!placaABuscar.equals(placa)){
                   infoQrNvo = placa+contenido;    
                }else{
                   infoQrNvo = placaABuscar+contenido; 
                }

                if(tipoVehiculo.equals("AUTOMOVIL")){
                    //Buscamos el codigo qr en la carpeta de qrs de carros para eliminarlo
                    if(carpetaQrsCarros.exists() && carpetaQrsCarros.isDirectory()){
                        listadoDeQrs = carpetaQrsCarros.listFiles();

                        //Contamos cuantos codigos qr tiene la carpeta de qrs
                        cantidadQrs = contarCodigosQr(listadoDeQrs);

                        if(cantidadQrs > 0){
                            for(File qr : listadoDeQrs){
                                if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                                    //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                                    qr.delete();
                                    generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                                }else{
                                    generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                                }
                           }
                        }else{
                            generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                        }               
                    }else{
                        crearCarpeta(carpetaQrsCarros);
                        generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                    }
                }else if(tipoVehiculo.equals("MOTO")){
                    //Buscamos el codigo qr en la carpeta de qrs de motos para eliminarlo
                    if(carpetaQrsMotos.exists() && carpetaQrsMotos.isDirectory()){
                        listadoDeQrs = carpetaQrsMotos.listFiles();

                        //Contamos cuantos codigos qr tiene la carpeta de qrs
                        cantidadQrs = contarCodigosQr(listadoDeQrs);

                        if(cantidadQrs > 0){
                            for(File qr : listadoDeQrs){
                                if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                                    //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                                    qr.delete();
                                    generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                                }else{
                                    generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                                }
                           }
                        }else{
                            crearCarpeta(carpetaQrsMotos);
                            generarQR(qrNuevo, infoQrNvo, tipoVehiculo);
                        }
                    }
                }
            }   
        }
        
        if(tipoVehiculo.equals("BICICLETA") || tipoVehiculo.equals("PATINETA") || tipoVehiculo.equals("OTRO")){

            //Reanudamos el proceso actualizacion de ticket qr vehicular
            reanudarProcesoDeActualizaciónQRBicisYOtros();

            //Buscamos el codigo qr en la carpeta de qrs de bicis para eliminarlo
            if(carpetaQrsBicis.exists() && carpetaQrsBicis.isDirectory()){
                listadoDeQrs = carpetaQrsBicis.listFiles();

                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);

                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                            //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                            qr.delete();
                            generarQR(qrNuevo, contenido, tipoVehiculo);
                        }else{
                            generarQR(qrNuevo, contenido, tipoVehiculo);
                        }
                   }
                }else{
                    crearCarpeta(carpetaQrsBicis);
                    generarQR(qrNuevo, contenido, tipoVehiculo);
                }
            }
        }  
    }
    
    //Metodo que crea la carpeta donde se almacenan los codigos qr de los vehiculos
    public void crearCarpeta(File carpetaACrear){
        carpetaACrear.mkdir();
    }
    
    //Metodo que cuenta cuantos archivos hay en las carpetas de qrs de los vehiculos
    public int contarCodigosQr(File[] qrs){
        int cantidad = 0;
        for(File qr : qrs){
            if(qr.isFile()){
                cantidad++;
            }
       }
        return cantidad;
    }
    
    //Metodo que elimina un codigo qr de la carpeta de qrs de los vehiculos
    public void eliminarQr(String nombreQr, String tipoDeVehiculo){
        File[] listadoDeQrs;
        int cantidadQrs = 0;
        
        //Evaluamos que tipo de vehiculo tiene el vehiculo del qr a eliminar
        if(tipoDeVehiculo.equals("AUTOMOVIL")){
            //Buscamos el codigo qr en la carpeta de qrs de carros para eliminarlo
            if(carpetaQrsCarros.exists() && carpetaQrsCarros.isDirectory()){
                listadoDeQrs = carpetaQrsCarros.listFiles();
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);

                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(nombreQr+".gif")){
                            //Eliminamos el codigo qr 
                            qr.delete();   
                        }
                    }
                }
            }
        }else if(tipoDeVehiculo.equals("MOTO")){
            //Buscamos el codigo qr en la carpeta de qrs de carros para eliminarlo
            if(carpetaQrsMotos.exists() && carpetaQrsMotos.isDirectory()){
                listadoDeQrs = carpetaQrsMotos.listFiles();
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);

                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(nombreQr+".gif")){
                            //Eliminamos el codigo qr 
                            qr.delete();   
                        }
                    }
                }
            }
        }else if(tipoDeVehiculo.equals("BICICLETA") || tipoDeVehiculo.equals("PATINETA") || tipoDeVehiculo.equals("OTRO")){
            //Buscamos el codigo qr en la carpeta de qrs de carros para eliminarlo
            if(carpetaQrsBicis.exists() && carpetaQrsBicis.isDirectory()){
                listadoDeQrs = carpetaQrsBicis.listFiles();
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);

                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(nombreQr+".gif")){
                            //Eliminamos el codigo qr 
                            qr.delete();   
                        }
                    }
                }
            }
        }
    }
    
    //Metodo que genera el ticket del codigo qr de un vehiculo
    public void generarTicketQrVehiculo(String nombreQr, boolean vistaPrevia, String tipoCambio, String tipoDeVehiculo){
          
        //Mapeamos los parametros con los cuales se generara el ticket
        Map parametros = new HashMap ();
        parametros.put("nombre_qr", nombreQr);
        
        if(!tipoCambio.equals("")){        
            if(tipoDeVehiculo.equals("AUTOMOVIL")){
                parametros.put("imagen", rutaQrsCarros+"\\"+nombreQr+".gif");
                while(!new File(rutaQrsCarros+"\\"+nombreQr+".gif").exists()){
                    try {
                        hilo4.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se registre el qr del carro: " + ex);
                    }
                    try {
                        hilo5.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se actualize el qr del carro: " + ex);
                    }
                }
            }else if(tipoDeVehiculo.equals("MOTO")){
                parametros.put("imagen", rutaQrsMotos+"\\"+nombreQr+".gif");
                while(!new File(rutaQrsMotos+"\\"+nombreQr+".gif").exists()){
                    try {
                        hilo4.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se registre el qr de la moto: " + ex);
                    }
                    try {
                        hilo5.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se actualize el qr de la moto: " + ex);
                    }
                }
            }else if(tipoDeVehiculo.equals("BICICLETA") || tipoDeVehiculo.equals("PATINETA") || tipoDeVehiculo.equals("OTRO")){
                parametros.put("imagen", rutaQrsBicis+"\\"+nombreQr+".gif");
                while(!new File(rutaQrsBicis+"\\"+nombreQr+".gif").exists()){
                    try {
                        hilo6.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se registre el qr de la bici u otro: " + ex);
                    }
                    try {
                        hilo7.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.fatal("ERROR - Se ha producido un error al intentar esperar que se actualize el qr de la bici u otro: " + ex);
                    }
                }
            }
        }else{
            if(tipoDeVehiculo.equals("AUTOMOVIL")){
                parametros.put("imagen", rutaQrsCarros+"\\"+nombreQr+".gif");
            }else if(tipoDeVehiculo.equals("MOTO")){
                parametros.put("imagen", rutaQrsMotos+"\\"+nombreQr+".gif");
            }else if(tipoDeVehiculo.equals("BICICLETA") || tipoDeVehiculo.equals("PATINETA") || tipoDeVehiculo.equals("OTRO")){
                parametros.put("imagen", rutaQrsBicis+"\\"+nombreQr+".gif");
            }    
        }
    
        try{
            JasperReport reporte = null;

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/qrVehiculo.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());

            if(vistaPrevia == true){
               //Da una vista previa del ticket
                JasperViewer view = new JasperViewer(jprint, false);
                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                view.setTitle("Codigo QR de vehiculo " + nombreQr);
                view.setVisible(true);
                view.setIconImage(getIconImagePDFVehiculos());

           }else{
                //Hace que se imprima directamente
               JasperPrintManager.printReport(jprint, false);
           } 
            
            if(tipoCambio.equals("REGISTRO_CYM")){
                suspenderProcesoDeRegistroQRCarrosYMotos();
            }else if(tipoCambio.equals("ACTUALIZACION_CYM")){
                suspenderProcesoDeActualizacionQRCarrosYMotos();
            }else if(tipoCambio.equals("REGISTRO_BYO")){
                suspenderProcesoDeRegistroQRBicisYOtros();
            }else if(tipoCambio.equals("ACTUALIZACION_BYO")){
                suspenderProcesoDeActualizacionQRBicisYOtros();
            }    

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡Error al generar Ticket qr del vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar generar el ticket qr de un vehiculo: " + ex); 
        }   
    } 

    //Metodo que se encarga de ejecutar los hilos que registran y actualizan los tickets qr de los vehiculos
    @Override
    public void run() {
        Thread ct4 = Thread.currentThread();
        Thread ct5 = Thread.currentThread();
        Thread ct6 = Thread.currentThread();
        Thread ct7 = Thread.currentThread();
        
        while(ct4 == hilo4){
            while(!hilo4.isInterrupted()){
            
                //Comprueba que el hilo de registro de qrs de carros y motos se encuentre suspendido
                hiloRegistroTicketsQrCarrosYMotosEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(placa+" - "+dueño, false, "REGISTRO_CYM", tipoVehiculo);
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
        
        while(ct5 == hilo5){
            while(!hilo5.isInterrupted()){
            
                //Comprueba que el hilo de actualizacion de qrs carros y motos se encuentre suspendido
                hiloActualizacionTicketsQrCarrosYMotosEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(placa+" - "+dueño, false, "ACTUALIZACION_CYM", tipoVehiculo);
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
        
        while(ct6 == hilo6){
            while(!hilo6.isInterrupted()){
            
                //Comprueba que el hilo de registro de qrs bicis y otros se encuentre suspendido
                hiloRegistroTicketsQrBicisYOtrosEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(tipoIdentificacion+identificacion+" - "+dueñoBiciUOtro, false, "REGISTRO_BYO", tipoVehiculoBiciUOtro);
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
        
        while(ct7 == hilo7){
            while(!hilo7.isInterrupted()){
            
                //Comprueba que el hilo de actualizacion de qrs bicis y otros se encuentre suspendido
                hiloActualizacionTicketsQrBicisYOtrosEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(tipoIdentificacion+identificacion+" - "+dueñoBiciUOtro, false, "ACTUALIZACION_BYO", tipoVehiculoBiciUOtro);
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
    }   
    
    //Metodo que carga en el sistema el proceso registro de tickets qr para carros y motos
    public synchronized void cargarProcesoDeRegistroQRCarrosYMotos(){
        hilo4.start();
        hiloRegistroTicketsQRCarrosYMotosSuspendido = true;
    }
    
    //Metodo que carga en el sistema el proceso registro de tickets qr para bicicletas y otros vehiculos
    public synchronized void cargarProcesoDeRegistroQRBicisYOtros(){
        hilo6.start();
        hiloRegistroTicketsQRBicisYOtrosSuspendido = true;
    }
    
    //Metodo que carga en el sistema el proceso actualizacion de tickets qr para carros y motos
    public synchronized void cargarProcesoDeActualizacionQRCarrosYMotos(){
        hilo5.start();
        hiloActualizacionTicketsQRCarrosYMotosSuspendido = true;
    }
    
    //Metodo que carga en el sistema el proceso actualizacion de tickets qr para biicletas y otros vehiculos
    public synchronized void cargarProcesoDeActualizacionQRBicisYOtros(){
        hilo7.start();
        hiloActualizacionTicketsQRBicisYOtrosSuspendido = true;
    }
    
    //Metodo que reanuda el hilo de generación de ticket qr para carros y motos
    public synchronized void reanudarProcesoDeRegistroQRCarrosYMotos(){
        hiloRegistroTicketsQRCarrosYMotosSuspendido = false;
        notifyAll();
    }
    
    //Metodo que reanuda el hilo de generación de ticket qr para bicicletas y otros vehiculos
    public synchronized void reanudarProcesoDeRegistroQRBicisYOtros(){
        hiloRegistroTicketsQRBicisYOtrosSuspendido = false;
        notifyAll();
    }
    
    //Metodo que reanuda el hilo de actualización de ticket qr para carros y motos
    public synchronized void reanudarProcesoDeActualizaciónQRCarrosYMotos(){
        hiloActualizacionTicketsQRCarrosYMotosSuspendido = false;
        notifyAll();
    }
    
    //Metodo que reanuda el hilo de actualización de ticket qr para bicicletas y otros vehiculos
    public synchronized void reanudarProcesoDeActualizaciónQRBicisYOtros(){
        hiloActualizacionTicketsQRBicisYOtrosSuspendido = false;
        notifyAll();
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para carros y motos
    public synchronized void suspenderProcesoDeRegistroQRCarrosYMotos(){
       hiloRegistroTicketsQRCarrosYMotosSuspendido = true;
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para bicicletas y otros
    public synchronized void suspenderProcesoDeRegistroQRBicisYOtros(){
       hiloRegistroTicketsQRBicisYOtrosSuspendido = true;
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para un vehiculo
    public synchronized void suspenderProcesoDeActualizacionQRCarrosYMotos(){
       hiloActualizacionTicketsQRCarrosYMotosSuspendido = true;
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para un vehiculo
    public synchronized void suspenderProcesoDeActualizacionQRBicisYOtros(){
       hiloActualizacionTicketsQRBicisYOtrosSuspendido = true;
    }
    
    //Metodo que determina que hace el hilo de registro de ticket qr de carros y motos mientras se encuentra suspendido
    public synchronized void hiloRegistroTicketsQrCarrosYMotosEnSuspension(){
        while(hiloRegistroTicketsQRCarrosYMotosSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 
    
    //Metodo que determina que hace el hilo de registro de ticket qr de bicis y otros  mientras se encuentra suspendido
    public synchronized void hiloRegistroTicketsQrBicisYOtrosEnSuspension(){
        while(hiloRegistroTicketsQRBicisYOtrosSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    }
    
    //Metodo que determina que hace el hilo de actualizacion de ticket qr de carros y motos mientras se encuentra suspendido
    public synchronized void hiloActualizacionTicketsQrCarrosYMotosEnSuspension(){
        while(hiloActualizacionTicketsQRCarrosYMotosSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 
    
    //Metodo que determina que hace el hilo de actualizacion de ticket qr de bicis y otros mientras se encuentra suspendido
    public synchronized void hiloActualizacionTicketsQrBicisYOtrosEnSuspension(){
        while(hiloActualizacionTicketsQRBicisYOtrosSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 
    
    public void ajustarTamañoColumnasTablaBicisYOtros(){
        //Obtenemos las columnas de la tabla
        TableColumn col1 = Table_listaBicisYOtros.getColumnModel().getColumn(0);
        TableColumn col2 = Table_listaBicisYOtros.getColumnModel().getColumn(1);
        TableColumn col3 = Table_listaBicisYOtros.getColumnModel().getColumn(2);
        TableColumn col4 = Table_listaBicisYOtros.getColumnModel().getColumn(3);
        TableColumn col5 = Table_listaBicisYOtros.getColumnModel().getColumn(4);
        TableColumn col6 = Table_listaBicisYOtros.getColumnModel().getColumn(5);
        TableColumn col7 = Table_listaBicisYOtros.getColumnModel().getColumn(6);
        TableColumn col8 = Table_listaBicisYOtros.getColumnModel().getColumn(7);
       
        //Establecemos el ancho de las columnas
        col1.setPreferredWidth(70);
        col2.setPreferredWidth(100);
        col3.setPreferredWidth(200);
        col4.setPreferredWidth(90);
        col5.setPreferredWidth(200);
        col6.setPreferredWidth(100);
        col7.setPreferredWidth(150);
        col8.setPreferredWidth(150);
    }
}
