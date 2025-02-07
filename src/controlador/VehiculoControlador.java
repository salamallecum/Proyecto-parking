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
import vista.MenuAdministrador;
import vista.PanelVehiculos;
import static vista.PanelVehiculos.modelo;
import static vista.PanelVehiculos.Table_listaVehiculos;
import static vista.PanelVehiculos.dueño;
import static vista.PanelVehiculos.placa;

/**
 *
 * @author ALEJO
 */
public class VehiculoControlador extends Thread{
    
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", "", 0, 0, 0);
    ParqueaderoControlador parqControlador;
    ParametroControlador paramControla;
    
    
    //Definimos las propiedades que tendran los codigos qr
    int udm = 0;
    int resol = 72;
    float mi = 0.000f;
    float md = 0.000f;
    float ms = 0.000f;
    float min = 0.000f;
    int rot = 0;
    float tam = 5.000f;
    
    //Ruta y carpeta de qrs para consulta del usuario
    String rutaQrs = System.getProperty("user.dir")+"\\qrCodes";
    File carpetaQrs = new File(rutaQrs);
    
    //Variables del proceso de registro y actualizacion de qrs vehiculares
    Thread hilo4 = new Thread(this);
    private boolean hiloRegistroTicketsQRSuspendido;
    Thread hilo5 = new Thread(this);
    private boolean hiloActualizacionTicketsQRSuspendido;
             
    private final Logger log = Logger.getLogger(VehiculoControlador.class);
    private final URL url = VehiculoControlador.class.getResource("/clasesDeApoyo/Log4j.properties");
    
    //Constructor
    public VehiculoControlador() {}   
    
    //Metodo que evalua la existencia de un vehiculo previamente en el sistema (devuelve 1 si el vehiculo existe y 0 si es lo contrario)
    public int evaluarExistenciaDelVehiculo(String consecutivoQr, String placa){
               
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
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "insert into vehiculos values (?,?,?,?,?,?,?,?)");

            pst2.setInt(1, veh.getId());
            pst2.setString(2, veh.getQr_consecutivo());
            pst2.setString(3, veh.getPlaca());
            pst2.setString(4, veh.getPropietario());
            pst2.setString(5, veh.getTipo());
            pst2.setInt(6, veh.getId_parqueadero());
            pst2.setInt(7, veh.getId_convenio());
            pst2.setInt(8, veh.getId_tarifa());
            
            pst2.executeUpdate();
            cn2.close();          
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡Error al crear vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al crear un vehiculo en el sistema: " + e);
        }
    }
    
    //Metodo que genera el reporte PDF de los vehiculos registrados
    public void generarReportePDFdeVehiculosRegistrados(String sql){
         
        try{
            Connection cn3 = Conexion.conectar();
            
            //Enviamos la ruta de la imagen y la sentencia sql como parametros
            Map parametro = new HashMap();
            parametro.put("imagen", this.getClass().getResourceAsStream(rutaImgReporteAColor));
            parametro.put("sql", sql);

            JasperReport reporte = null;
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoVehiculos.jasper"));
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
    
    //Metodo que genera el reporte PDF del listado de los vehiculos registrados
    public Image getIconImagePDFVehiculos() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Carro.png"));
        return retValue;
    }
    
    //Metodo que busca un vehiculo teniendo en cuenta varios criterios de busqueda
    public void buscarVehiculo(String sentenciaSql){
        
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
            JOptionPane.showMessageDialog(null, "¡¡Error de busqueda de vehiculos!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar buscar los vehiculos. " + ex);
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
                        "SELECT Ve.Placa, Ve.Propietario, Ve.TipoVehiculo, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa");
            
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
    
    //Metodo para verificar que el vehiculo no se encuentre en parqueadero
    public boolean verificarSiVehiculoEstaEnParqueadero(String placa){
        
        boolean vehiculoEnParqueadero = false;
        
        //Verifica que el vehiculo no se encuentre en parqueadero
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Esta_en_parqueadero from parqueaderos where Placa = '" + placa + "' AND Esta_en_parqueadero='Si'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                vehiculoEnParqueadero = true;
                cn.close();
                return vehiculoEnParqueadero;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡Error al verficar si el vehiculo esta en el parqueadero!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar validar si el vehiculo esta en parqueadero: " + e);
        } 
        
        return vehiculoEnParqueadero;
    }

    //Metodo para eliminar un vehiculo del sistema
    public void eliminarVehiculo(String placa){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          
            
            ps1 = cn1.prepareStatement("delete from vehiculos where Placa=?");
            ps1.setString(1, placa);
            ps1.execute();
            cn1.close();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al eliminar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al intentar eliminar el vehiculo: "+ placa + e);
        }   
    }

    //Metodo que consulta la ifnromación de un vehiculo teniendo en cuenta su consecutivo qr o placa
    public Vehiculo consultarInformacionDeUnVehiculo(String consecutivoQR, String placaDelVehiculo){
        
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            String sql;
            PreparedStatement pst;
            ResultSet rs;
            
            if(consecutivoQR != null){
                sql = "select Placa, Propietario, TipoVehiculo, Id_parqueadero, Id_convenio, Id_tarifa from vehiculos where Qr_consecutivo = '"+consecutivoQR+"'";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    vehiculoConsultado.setQr_consecutivo(consecutivoQR);
                    vehiculoConsultado.setPlaca(rs.getString("Placa"));
                    vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                    vehiculoConsultado.setTipo(rs.getString("TipoVehiculo"));
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
                    vehiculoConsultado.setTipo(rs.getString("TipoVehiculo"));
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
    public void liberarVehiculo(int IdL){
        
        //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible 
        try{
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement("update vehiculos set Placa='000000' where Id_vehiculo='"+IdL+"'");

            pst3.executeUpdate();
            cn3.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al liberar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al liberar un vehiculo: " + e);
        }
    }
       
    //Metodo que permite identificar si el vehiculo en cuestion esta involucrado en alguna factura
    public boolean consultarSiVehiculoTieneFacturasAbiertas(String placa){

        boolean vehiculoTieneFacturasAbiertas = false;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select * from facturas where Placa = '" + placa + "' and Estado_fctra = 'Abierta'");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                vehiculoTieneFacturasAbiertas = true;
            }else{
                vehiculoTieneFacturasAbiertas = false;
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
            PreparedStatement pst = cn.prepareStatement("update vehiculos set Qr_consecutivo ='"+vehAActualizar.getQr_consecutivo()+"',Placa ='"+vehAActualizar.getPlaca()+"',Propietario='"+vehAActualizar.getPropietario()+"',TipoVehiculo='"+vehAActualizar.getTipo()+"',Id_parqueadero="+vehAActualizar.getId_parqueadero()+",Id_convenio="+vehAActualizar.getId_convenio()+",Id_tarifa="+vehAActualizar.getId_tarifa()+" where Id_vehiculo="+vehAActualizar.getId());

            pst.executeUpdate();
            cn.close(); 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al actualizar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al modificar un vehiculo del sistema: " + e);
        } 
    }
    
    //Metodo que recarga el vehiculo al cerrar la ventana de edición del mismo 
    public void recargarVehiculo(int idDelVehiculo, String placa){
        
        try{
            Connection cn9 = Conexion.conectar();
            PreparedStatement pst9 = cn9.prepareStatement("update vehiculos set Placa ='"+placa+"' where Id_vehiculo ='"+idDelVehiculo+"'");

            pst9.executeUpdate();
            cn9.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡Error al recargar vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
        } 
    }
    
    //Metodo que consulta  el id del parqueadero que esta ocupando un vehiculo
    public int consultarIdParqQueOcupaUnVehiculo(String placa){
       
        int idParq = 0;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Id_parqueadero from parqueaderos where Placa = '" + placa + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                idParq= rs.getInt("Id_parqueadero");
                cn.close();
           
            } else {
                log.fatal("ERROR - No se ha encontrado el ID del parqueadero que ocupa un vehiculo");
            }
        }catch (SQLException ex){ 
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar parqueadero que ocupa un vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al consultar el ID del parqueadero que ocupa un vehiculo: " + ex); 
        } 
        return idParq;
    }
    
    //Metodo que se encarga de generar el codigo Qr de un vehiculo a partir de un texto de entrada
    public void generarQR(String nombreArchivo, String contenido){
               
        //Verificamos la existencia de la carpeta que guarda los codigos qr
        if(!carpetaQrs.exists()){
            crearCarpetaDeQrs();
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

            String qrParaConsulta = rutaQrs+"/"+nombreArchivo+".gif";
            codigoQr.renderBarcode(qrParaConsulta);
                     
               
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar codigo qr de vehiculo!!, contacte al administrador.", "Error", JOptionPane.ERROR_MESSAGE, paramControla.getIcon("/icons/Cancelar.png", 32, 32));
            log.fatal("ERROR - Se ha producido un error al generar el codigo qr: " + e);
        }
    }
    
    //Metodo que se encarga de actualizar en la carpeta qrCodes, el codigo qr de un vehiculo
    public void actualizarQR(String placaABuscar, String propietarioABuscar, String placa, String propietario, String contenido){
        
        String infoQrNvo;
        String qrABuscar = placaABuscar+" - "+propietarioABuscar; 
        String qrNuevo = placa+" - "+propietario;
        int cantidadQrs;
        File[] listadoDeQrs;
        
        //Validamos si el nombre del qr a buscar no es igual al que se pretende generar
        if(!qrABuscar.equals(qrNuevo)){
            
            //Reanudamos el proceso actualizacion de ticket qr vehicular
            reanudarProcesoDeActualizaciónQRVehicular();
            
            //Generamos el nuevo nombre del codigo qr a generar
            if(!placaABuscar.equals(placa)){
               infoQrNvo = placa+contenido;    
            }else{
               infoQrNvo = placaABuscar+contenido; 
            }
            
            //Buscamos el codigo qr en la carpeta qrCodes para eliminarlo
            if(carpetaQrs.exists() && carpetaQrs.isDirectory()){
                listadoDeQrs = carpetaQrs.listFiles();
                
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);
                                
                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                            //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                            qr.delete();
                            generarQR(qrNuevo, infoQrNvo);
                        }else{
                            generarQR(qrNuevo, infoQrNvo);
                        }
                   }
                }else{
                    generarQR(qrNuevo, infoQrNvo);
                }               
            }else{
                crearCarpetaDeQrs();
                generarQR(qrNuevo, infoQrNvo);
            }
            
            //Buscamos el codigo qr en el paquete qr de la aplicacion para eliminarlo
            if(carpetaQrs.exists() && carpetaQrs.isDirectory()){
                listadoDeQrs = carpetaQrs.listFiles();
                
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                cantidadQrs = contarCodigosQr(listadoDeQrs);
                                
                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                            //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                            qr.delete();
                            generarQR(qrNuevo, infoQrNvo);
                        }else{
                            generarQR(qrNuevo, infoQrNvo);
                        }
                   }
                }else{
                    generarQR(qrNuevo, infoQrNvo);
                }
            }
        }
    }
    
    //Metodo que crea la carpeta donde se almacenan los codigos qr de los vehiculos
    public void crearCarpetaDeQrs(){
        carpetaQrs.mkdir();
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
    public void eliminarQr(String texto){
               
        //Buscamos el codigo qr en la carpeta qrCodes para eliminarlo
        if(carpetaQrs.exists() && carpetaQrs.isDirectory()){
            File[] listadoDeQrs = carpetaQrs.listFiles();
            
            //Contamos cuantos codigos qr tiene la carpeta de qrs
            int cantidadQrs = contarCodigosQr(listadoDeQrs);
                                
            if(cantidadQrs > 0){
                for(File qr : listadoDeQrs){
                    if(qr.isFile() && qr.getName().equals(texto+".gif")){
                        //Eliminamos el codigo qr 
                        qr.delete();   
                    }
                }
            }
        }
        
        //Buscamos el codigo qr en el paquete qr de la aplicacion para eliminarlo
        if(carpetaQrs.exists() && carpetaQrs.isDirectory()){
            File[] listadoDeQrsApp = carpetaQrs.listFiles();
            
            //Contamos cuantos codigos qr tiene la carpeta de qrs
            int cantidadQrs = contarCodigosQr(listadoDeQrsApp);
                                
            if(cantidadQrs > 0){
                for(File qr : listadoDeQrsApp){
                    if(qr.isFile() && qr.getName().equals(texto+".gif")){
                        //Eliminamos el codigo qr 
                        qr.delete();   
                    }
                }
            }
        }
    }
    
    //Metodo que genera el ticket del codigo qr de un vehiculo
    public void generarTicketQrVehiculo(String nombreQr, boolean vistaPrevia, String tipoCambio){
             
        while(!new File(rutaQrs+"\\"+nombreQr+".gif").exists()){
            
            try {
                hilo4.sleep(1000);
            } catch (InterruptedException ex) {
                log.fatal("ERROR - Se ha producido un error al intentar esperar que se registre el qr del vehiculo: " + ex);
            }
            try {
                hilo5.sleep(1000);
            } catch (InterruptedException ex) {
                log.fatal("ERROR - Se ha producido un error al intentar esperar que se actualizar el qr del vehiculo: " + ex);
            }
        }
        
        try{
            //Agregamos los parametros con los cuales se generara el ticket
            Map parametros = new HashMap ();
            parametros.put("nombre_qr", nombreQr);
            parametros.put("imagen", rutaQrs+"\\"+nombreQr+".gif");

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
            
            if(tipoCambio.equals("REGISTRO")){
                suspenderProcesoDeRegistroQRVehicular();
            }else if(tipoCambio.equals("ACTUALIZACION")){
                suspenderProcesoDeActualizacionQRVehicular();
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
        
        while(ct4 == hilo4){
            while(!hilo4.isInterrupted()){
            
                //Comprueba que el hilo de registro de qrs se encuentre suspendido
                hiloRegistroTicketsQrEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(placa+" - "+dueño, false, "REGISTRO");
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
        
        while(ct5 == hilo5){
            while(!hilo5.isInterrupted()){
            
                //Comprueba que el hilo de actualizacion de qrs se encuentre suspendido
                hiloActualizacionTicketsQrEnSuspension();

                try {
                    //Cada 8 segundos ejecutará la funcion de generacion de ticket qr
                    sleep(8000);
                    generarTicketQrVehiculo(placa+" - "+dueño, false, "ACTUALIZACION");
                } catch (InterruptedException ex) {
                    interrupt();
                }
            }
        }
    }   
    
    //Metodo que carga en el sistema el proceso registro de tickets qr
    public synchronized void cargarProcesoDeRegistroQRVehicular(){
        hilo4.start();
        hiloRegistroTicketsQRSuspendido = true;
    }
    
    //Metodo que carga en el sistema el proceso actualizacion de tickets qr
    public synchronized void cargarProcesoDeActualizacionQRVehicular(){
        hilo5.start();
        hiloActualizacionTicketsQRSuspendido = true;
    }
    
    //Metodo que reanuda el hilo de generación de ticket qr para un vehiculo
    public synchronized void reanudarProcesoDeRegistroQRVehicular(){
        hiloRegistroTicketsQRSuspendido = false;
        notifyAll();
    }
    
    //Metodo que reanuda el hilo de actualización de ticket qr para un vehiculo
    public synchronized void reanudarProcesoDeActualizaciónQRVehicular(){
        hiloActualizacionTicketsQRSuspendido = false;
        notifyAll();
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para un vehiculo
    public synchronized void suspenderProcesoDeRegistroQRVehicular(){
       hiloRegistroTicketsQRSuspendido = true;
    }
    
    //Metodo que suspende el hilo de generación de ticket qr para un vehiculo
    public synchronized void suspenderProcesoDeActualizacionQRVehicular(){
       hiloActualizacionTicketsQRSuspendido = true;
    }
    
    //Metodo que determina que hace el hilo de registro de ticket qr mientras se encuentra suspendido
    public synchronized void hiloRegistroTicketsQrEnSuspension(){
        while(hiloRegistroTicketsQRSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 
    
    //Metodo que determina que hace el hilo de registro de ticket qr mientras se encuentra suspendido
    public synchronized void hiloActualizacionTicketsQrEnSuspension(){
        while(hiloActualizacionTicketsQRSuspendido){
            try {
                wait();
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    } 
}
