package controlador;

import clasesDeApoyo.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Vehiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import static vista.PanelVehiculos.modelo;
import static vista.PanelVehiculos.Table_listaVehiculos;

/**
 *
 * @author ALEJO
 */
public class VehiculoControlador {
    
    Vehiculo vehiculoConsultado = new Vehiculo(0, "", "", "", 0, 0, 0);
    ParqueaderoControlador parqControlador;
    
    private final Logger log = Logger.getLogger(VehiculoControlador.class);
    private final URL url = VehiculoControlador.class.getResource("/clasesDeApoyo/Log4j.properties");
    
    //Constructor
    public VehiculoControlador() {}   
    
    //Metodo que evalua la existencia de un vehiculo previamente en el sistema
    public boolean evaluarExistenciaDelVehiculo(String placa){
        
        boolean elvehiculoYaExiste = false;
        
        //Valida que el vehiculo ingresado no exista previamente en la BD  
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Placa from vehiculos where Placa = '" + placa + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                elvehiculoYaExiste = true;
                cn.close();
            } 
            
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar placa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un vehiculo en el sistema: " + ex); 
        }
        
        return elvehiculoYaExiste;
    }
        
    //Metodo que registra un vehiculo en el sistema
    public void crearVehiculo(Vehiculo veh){
        
        //Inserta el registro en la base de datos
        try {
            Connection cn2 = Conexion.conectar();
            PreparedStatement pst2 = cn2.prepareStatement(
                "insert into vehiculos values (?,?,?,?,?,?,?)");

            pst2.setInt(1, veh.getId());
            pst2.setString(2, veh.getPlaca());
            pst2.setString(3, veh.getPropietario());
            pst2.setString(4, veh.getClase());
            pst2.setInt(5, veh.getId_parqueadero());
            pst2.setInt(6, veh.getId_convenio());
            pst2.setInt(7, veh.getId_tarifa());
            
            pst2.executeUpdate();
            cn2.close();          
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al crear vehiculo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un vehiculo en el sistema: " + e);
        }
    }
    
    //Metodo que genera el reporte PDF de los vehiculos registrados
    public void generarReportePDFdeVehiculosRegistrados(){
        
        try{
            Connection cn3 = Conexion.conectar();

            JasperReport reporte = null;
            //String path = "src\\Reportes\\ListadoVehiculos.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoVehiculos.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, cn3);

            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(getIconImagePDFVehiculos());
            view.setTitle("Reporte de vehiculos registrados");
            log.info("INFO - Reporte de vehiculos registrados generado satisfactoriamente.");

        }catch(JRException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Reporte de Vehiculos, contacte al administrador!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar reporte PDF de los vehiculos del sistema: " + ex);
        }
    }
    
    //Metodo que genera el reporte PDF del listado de los vehiculos registrados
    public Image getIconImagePDFVehiculos() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/Carro.png"));
        return retValue;
    }
    
    //Metodo que busca un vehiculo teniendo en cuenta su placa
    public void busquedaDeVehiculoPorPlaca(String text){
        
        try{
            String [] titulos = {"Placa", "Propietario", "Clase", "N° Parq", "Convenio", "Tarifa"};
            String filtro = ""+text+"_%";
            String SQL = "SELECT Ve.Placa, Ve.Propietario, Ve.Clase, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa AND Ve.Placa like "+'"'+filtro+'"';
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[6];
            
            while(rs6.next()){
                fila[0]=rs6.getString("Ve.Placa");
                fila[1]=rs6.getString("Ve.Propietario");
                fila[2]=rs6.getString("Ve.Clase");
                fila[3]=rs6.getString("Parq.Nombre_parqueadero");
                fila[4]=rs6.getString("Conv.Nombre_convenio");
                fila[5]=rs6.getString("Tar.Nombre_tarifa");
                modelo.addRow(fila);
            }
            
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda por placa!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar un vehiculo por medio de su placa: " + ex);
        }
    }
    
    //Metodo que busca un vehiculo teniendo en cuenta su propietario
    public void busquedaDeVehiculoPorPropietario(String text){
        
        try{
            String [] titulos = {"Placa", "Propietario", "Clase", "N° Parq", "Convenio", "Tarifa"};
            String filtro = ""+text+"_%";
            String SQL = "SELECT Ve.Placa, Ve.Propietario, Ve.Clase, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa AND Ve.Propietario like "+'"'+filtro+'"';
            modelo = new DefaultTableModel(null, titulos);
            
            Connection cn6 = Conexion.conectar();
            PreparedStatement pst6 = cn6.prepareStatement(SQL);
            ResultSet rs6 = pst6.executeQuery(SQL);
            
            String[] fila = new String[6];
            
            while(rs6.next()){
                fila[0]=rs6.getString("Ve.Placa");
                fila[1]=rs6.getString("Ve.Propietario");
                fila[2]=rs6.getString("Ve.Clase");
                fila[3]=rs6.getString("Parq.Nombre_parqueadero");
                fila[4]=rs6.getString("Conv.Nombre_convenio");
                fila[5]=rs6.getString("Tar.Nombre_tarifa");
                modelo.addRow(fila);
            }
            rs6.close();
            cn6.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR de busqueda por propietario!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar buscar un vehiculo por medio de su propietario: " + ex);
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
                        "SELECT Ve.Placa, Ve.Propietario, Ve.Clase, Parq.Nombre_parqueadero, Conv.Nombre_convenio, Tar.Nombre_tarifa FROM vehiculos Ve INNER JOIN parqueaderos Parq ON Ve.Id_parqueadero = Parq.Id_parqueadero INNER JOIN convenios Conv ON Ve.Id_convenio = Conv.Id_convenio INNER JOIN tarifas Tar ON Ve.Id_tarifa = Tar.Id_tarifa");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modelo.addColumn("Placa");
            modelo.addColumn("Propietario");
            modelo.addColumn("Clase");
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
            JOptionPane.showMessageDialog(null, "Error al llenar tabla de vehiculos, ¡Contacte al administrador!");
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
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar vehiculo!!, contacte al administrador.");
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
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar el vehiculo: "+ placa + e);
        }   
    }

    //Metodo que consulta la ifnromación de un vehiculo para cargarlo en la ventana de edición de vehiculos
    public Vehiculo consultarInformacionDeUnVehiculo(String placaDelVehiculo){
               
        //Hace la consulta de registros a la base de datos
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select * from vehiculos where Placa = '" + placaDelVehiculo + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                vehiculoConsultado.setId(rs.getInt("Id_vehiculo"));
                vehiculoConsultado.setPlaca(rs.getString("Placa"));
                vehiculoConsultado.setPropietario(rs.getString("Propietario"));
                vehiculoConsultado.setClase(rs.getString("Clase"));
                vehiculoConsultado.setId_parqueadero(rs.getInt("Id_parqueadero"));
                vehiculoConsultado.setId_convenio(rs.getInt("Id_convenio"));
                vehiculoConsultado.setId_tarifa(rs.getInt("Id_tarifa")); 
                cn.close();              
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar informacion del vehiculo seleccionado!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la informacion de un vehiculo para su edición: " + e);
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
            JOptionPane.showMessageDialog(null, "Error al liberar vehiculo, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al liberar un vehiculo: " + e);
        }
    }
    
    //Metodo que permite identificar si el vehiculo en cuestion esta involucrado en alguna factura
    public boolean consultarSiVehiculoTieneFacturas(String placa){

        boolean vehiculoTieneFacturas = false;
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select * from facturas where Placa = '" + placa + "'");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                vehiculoTieneFacturas = true;
            }else{
                vehiculoTieneFacturas = false;
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "ERROR al revisar facturación de vehiculo, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al intentar revisar facturación de vehiculo: " + ex);
        }
        
        return vehiculoTieneFacturas;
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
           JOptionPane.showMessageDialog(null, "ERROR al revisar facturación abierta de vehiculo, contacte al administrador.");
           log.fatal("ERROR - Se ha producido un error al intentar revisar facturación abierta de vehiculo: " + ex);
        }
        
        return vehiculoTieneFacturasAbiertas;
    }

    //Metodo que permite la edicion de un vehiculo en el sistema
    public void actualizarVehiculo(Vehiculo vehAActualizar){
        
        try{
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("update vehiculos set Placa ='"+vehAActualizar.getPlaca()+"',Propietario='"+vehAActualizar.getPropietario()+"',Clase='"+vehAActualizar.getClase()+"',Id_parqueadero="+vehAActualizar.getId_parqueadero()+",Id_convenio="+vehAActualizar.getId_convenio()+",Id_tarifa="+vehAActualizar.getId_tarifa()+" where Id_vehiculo="+vehAActualizar.getId());

            pst.executeUpdate();
            cn.close(); 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar vehiculo!!, contacte al administrador.");
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
            JOptionPane.showMessageDialog(null, "Error al recargar vehiculo, contacte al administrador.");
        } 
    }
    
    
}
