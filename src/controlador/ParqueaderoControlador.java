package controlador;

import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import clasesDeApoyo.Conexion;
import static controlador.UsuarioControlador.rutaImgReporteAColor;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import modelo.Parqueadero;
import modelo.Vehiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;
import static vista.EditarFacturaFinal.cmb_parqVisitantesFinal;
import static vista.EditarFacturaIngreso.cmb_parqVisitantes;
import static vista.EstadoParqueadero.Table_estado;
import vista.GestionarParqueaderos;
import static vista.GestionarParqueaderos.modeloParq;
import static vista.GestionarParqueaderos.table_listaParqueaderos;
import vista.MenuAdministrador;
import static vista.PanelCaja.cmb_numParqueadero;
import static vista.PanelCaja.modeloCaja;
import static vista.PanelCaja.table_operacionParqueadero;




/**
 *
 * @author ALEJO
 */
public class ParqueaderoControlador implements Runnable{
    
   VehiculoControlador vehiControlador;
   DefaultTableModel modeloEstadoParq;
   Parqueadero parq = new Parqueadero();
   
   //Variables que regulan cuando los hilos 2 y 3 se les dio start
   boolean hilo1YaInició = false;
   boolean hilo2YaInició = false;
   boolean hilo3YaInició = false;
 
      
   private final Logger log = Logger.getLogger(ParqueaderoControlador.class);
   private URL url = ParqueaderoControlador.class.getResource("Log4j.properties");
   public static boolean ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas; 
   public static boolean ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja; 
   
   //Hilo encargado del cargue del listado de parqueaderos de visitantes disponibles
    public Thread hilo1 = new Thread(this);
    public Thread hilo2 = new Thread(this);
    public Thread hilo3 = new Thread(this);
       
   //Constructor
   public ParqueaderoControlador() {}
     
    //Metodo que evalua si el parquaadero indicado para el vehiculo está ocupado
    public void evaluarSiParqueaderoEstaOcupado(Vehiculo vehi){
             
        //Valida que el parqueadero indicado no se encuentre ocupado
        try {
            Connection cn5 = Conexion.conectar();
            PreparedStatement pst5;
            pst5 = cn5.prepareStatement(
                        "select Id_parqueadero, Estado from parqueaderos where Id_parqueadero = '" + vehi.getId_parqueadero()+ "' AND Estado='Ocupado'");

            ResultSet rs5 = pst5.executeQuery();

            if (rs5.next()) {
                JOptionPane.showMessageDialog(null, "El parqueadero indicado se encuentra ocupado.");
                cn5.close();
            } else {  
                vehiControlador.crearVehiculo(vehi);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al revisar cupo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al revisar cupo del parqueadero" + ex);
        }
    }

    //Metodo que actualiza el estado de un parqueadero ya sea disponible o ocupado
    public void actualizarEstadoDeParqueadero(String placa, String dueño, int idParq, String estaEnParq){
                
        //Actualizamos el estado del parqueadero seleccionado de Disponible a Ocupado
        try{
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement("update parqueaderos set Estado ='Ocupado', Placa='"+placa+"', Propietario='"+dueño+"', Esta_en_parqueadero='"+estaEnParq+"' where Id_parqueadero="+idParq);

            pst3.executeUpdate();
            cn3.close();
           
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al pasar de Disponible a Ocupado un parqueadero" + ex);
        } 
    }
    
    //Metodo que se encarga de detener los hilos que muestran el listado de parqueaderos de visitantes disponibles enlas ventanas editarFacturaIngreso y EditarFacturaFinal
    public void detenerHilosParqueaderosVisitantesDisponiblesEdicionFacturas(){
        ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas = false;
    }
    
    //Metodo que se encarga de detener los hilos que muestran el listado de parqueaderos de visitantes disponibles del panel caja
    public void detenerHiloParqueaderosVisitantesDisponiblesPanelCaja(){
        ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja = false;
    }
    
    //Metodo que se encarga de ejecutar el hilo que muestra el listado de parqueaderos de visitantes disponibles del panel caja
    public void ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja(){
        if(hilo1YaInició == false){
            hilo1.start();
            ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja = true;
            hilo1YaInició = true;
        }else{
           ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja = true; 
        }  
    }
    
    //Metodo que se encarga de ejecutar el hilo que muestra el listado de parqueaderos de visitantes disponibles en la ventana EditarFacturaFinal
    public void ejecutarHiloParqueaderosVisitantesDisponiblesEditarFacturaFinal(){
        if(hilo2YaInició == false){
            hilo2.start();
            ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas = true;
            hilo2YaInició = true;
        }else{
           ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas = true; 
        }  
    }
    
    //Metodo que se encarga de ejecutar el hilo que muestra el listado de parqueaderos de visitantes disponibles en la ventana EditarFacturaINgreso
    public void ejecutarHiloParqueaderosVisitantesDisponiblesEditarFacturaIngreso(){
        if(hilo3YaInició == false){
            hilo3.start();
            ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas = true;
            hilo3YaInició = true;
        }else{
           ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas = true; 
        } 
    }
        
    //Cargamos la tabla de parqueaderos de la ventana gestionarParqueaderos
    public void cargarTablaDeParqueaderos(){
        
        //Cargamos los datos de la tabla
        try {
            modeloParq = new DefaultTableModel(){
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
            table_listaParqueaderos.setModel(modeloParq);
               
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Nombre_parqueadero, Estado, TipoParq, Placa, Propietario, Esta_en_parqueadero from parqueaderos");
            
            ResultSet rs = pst.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
           
            modeloParq.addColumn("Nombre");
            modeloParq.addColumn("Estado");
            modeloParq.addColumn("TipoParq");
            modeloParq.addColumn("Placa");
            modeloParq.addColumn("Propietario");
            modeloParq.addColumn("Está en parqueo?");
            
            int[] anchosTabla = {10,10,5,5,20,5};
            
            for(int x=0; x < cantidadColumnas; x++){
                table_listaParqueaderos.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }
            
            while (rs.next()) {
                
                Object[] filas = new Object[cantidadColumnas];
                
                for (int i = 0; i < cantidadColumnas; i++) {
                    
                        filas[i] = rs.getObject(i + 1); 
                }
                modeloParq.addRow(filas);
            }
            cn.close();                    
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al llenar tabla de parqueaderos, ¡Contacte al administrador!");
                log.fatal("ERROR - Se ha producido un error al cargar los parqueaderos de la BD a la Tabla de parqueaderos: " + e);
            }
    }

    //Metodo que genera el reporte PDF de los parqueaderos registrados
    public void generarPDFParqueaderosRegistrados(){
        
        //Enviamos el paramtetro con la ruta que traera la img del reporte
        Map parametroImg = new HashMap();
        parametroImg.put("imagen", this.getClass().getResourceAsStream(rutaImgReporteAColor));
        
        try{
            Connection cn3 = Conexion.conectar();

            JasperReport reporte = null;
            //String path = "src\\Reportes\\ListadoParqueaderos.jasper";

            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/ListadoParqueaderos.jasper"));

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametroImg, cn3);

            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            view.setIconImage(obtenerIconoReportePDFParqueaderos());
            view.setTitle("Reporte de parqueaderos registrados");
            log.info("INFO - Reporte de parqueaderos registrados generado satisfactoriamente.");
            MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = true;
            
            //Agregamos un evento para cuando el visor del reporte se cierre
            view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
               MenuAdministrador.hayAlgunaVentanaAbiertaDelSistema = false; 
                GestionarParqueaderos.btn_generaPDF.setEnabled(true);
            }
            });

        }catch(JRException ex){
            //Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al generar Reporte!!");
            log.fatal("ERROR - Se ha producido un error al intentar generar reporte PDF de los parqueaderos del sistema: " + ex);
        }
    }
    
    //Metodo que trae el icono de la ventana de reporte del parqueadero
    public Image obtenerIconoReportePDFParqueaderos() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("icons/gestParking.png"));
        return retValue;
    }
    
    //Metodo que crea un parqueadero
    public void crearParqueadero(Parqueadero nvoParq){
       
        //Inserta el parqueadero en la base de datos de parqueaderos      
        try {
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement(
                "insert into parqueaderos(Id_parqueadero, Nombre_parqueadero, TipoParq, Estado, Placa, Esta_en_parqueadero) values (?,?,?,?,?,?)");

            pst3.setInt(1, nvoParq.getId());
            pst3.setString(2, nvoParq.getNombre());
            pst3.setString(3, nvoParq.getTipoParqueadero());
            pst3.setString(4, nvoParq.getEstado());
            pst3.setString(5, nvoParq.getPlaca());
            pst3.setString(6, nvoParq.getEstaOcupado());


            pst3.executeUpdate();
            cn3.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al crear un parqueadero en el sistema: " + e);
        } 
    }
    
    //Metodo que evalua si el parqueadero ya existe en el sistema
    public boolean evaluarExistenciaDeParqueadero(String parq){
        
        boolean elParqueaderoExiste = false;
        //Valida que el parqueadero ingresado no exista previamente en la BD  
        try {
            Connection cn4 = Conexion.conectar();
            PreparedStatement pst4;
            pst4 = cn4.prepareStatement(
                        "select Nombre_parqueadero from parqueaderos where Nombre_parqueadero = '" + parq + "'");
            
            ResultSet rs4 = pst4.executeQuery();
            
            if (rs4.next()) {
                cn4.close();
                elParqueaderoExiste = true;
            } else { 
                elParqueaderoExiste = false;
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar nombre de Parquadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar la existencia de un parqueadero en el sistema: " + ex);
        }
        return elParqueaderoExiste;
    }
    
    //Metodo que elimina un parqueadero del sistema
    public void eliminarParqueadero(String nombreParqueadero){
        
        PreparedStatement ps1 = null;
        try{
            Connection cn1 = Conexion.conectar();          

            ps1 = cn1.prepareStatement("delete from parqueaderos where Nombre_parqueadero=?");
            ps1.setString(1, nombreParqueadero);
            ps1.execute();

            JOptionPane.showMessageDialog(null, "El parqueadero: " + nombreParqueadero + " ha sido eliminado");
            cn1.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al eliminar parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar eliminar un parqueadero: " + e);
        }
    }
    
    //Metodo que evlua si está el propietario en el parqueadero
    public boolean evaluarSiEstaPropietarioEnParqueadero(String nombreParqueadero){
        
        boolean propietarioEstaEnParqueadero = false;
        //Verifica que el parqueadero seleccionado no tenga cupo en parqueadero
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst;
            pst = cn.prepareStatement(
                        "select Esta_en_parqueadero from parqueaderos where Nombre_parqueadero = '"+nombreParqueadero+"' AND Esta_en_parqueadero='Si'");

            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            propietarioEstaEnParqueadero = rs.next();
            
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al verificar cupo de parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al validar si un propietario estaba en parqueadero: " + e);
        }
        return propietarioEstaEnParqueadero;    
    }
       
    //Metodo que libera el parqueadero del vehiculo
    public void liberarParqueadero(String placaL){
        
        //Actualizamos el estado del parqueadero seleccionado de Ocupado a Disponible 
        try{
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement("update parqueaderos set Estado ='Disponible', Placa='',Propietario='', Esta_en_parqueadero='' where Placa='"+placaL+"'");

            pst3.executeUpdate();
            cn3.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al liberar parqueadero, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al dar disponibilidad al parqueadero que utilizaba el vehiculo: "+ placaL + e);
        }
    }
        
    //Metodo que consulta el estado de disponibilidad de un parqueadero mediante su id
    public boolean consultarDisponibilidadDeParqueaderoMedianteID(int idParq){
        
        boolean parqueaderoOcupado = false;
        
        //Verifica la disponibilidad del parqueadero por su id
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Estado from parqueaderos where Id_parqueadero = " + idParq);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String estadoParq = rs.getString("Estado");
                
                if(estadoParq.equals("Ocupado")){
                    parqueaderoOcupado = true;
                    cn.close();
                }else{
                    parqueaderoOcupado = false;
                }   
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar vehiculo!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar si la disponibilidad de un parquadero utilizando su ID: " + e);
        } 
        return parqueaderoOcupado;
    }
    
    //Metodo que permite consultar el nombre de un parqueadero para su muestreo usando su id
    public String consultarNombreDeParqueaderoMedianteID(int idParq){
              
        String nombreParqueadero = "";
                
        //Consulta el nombre del parqueadero utilizando su id
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Nombre_parqueadero from parqueaderos where Id_parqueadero = " + idParq);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                nombreParqueadero = rs.getString("Nombre_parqueadero");   
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar el nombre de un parquadero utilizando su ID: " + e);
        } 
        return nombreParqueadero;
    }
    
    //Metodo que imprime ticket de seguimiento parqueadero
    public void generarReporteDeEstadoParqueadero(){     
                
        PrinterMatrix printer = new PrinterMatrix();

        Extenso e = new Extenso();

        e.setNumber(101.85);
        int filas = Table_estado.getRowCount();

        //Definir el tamanho del papel para la impresion  aca 25 lineas y 80 columnas
        printer.setOutSize(filas + 8, 46);
        //Imprimir * 1ra linea de la columa de 1 a 80
        printer.printCharAtCol(1, 1, 46, "=");
        //Imprimir Encabezado nombre del La EMpresa
       printer.printTextWrap(1, 2, 12, 46, "ESTADO DEL PARQUEADERO");
       //printer.printTextWrap(linI, linE, colI, colE, null);
       
       printer.printTextWrap(3, 8, 1, 46, "Estado  |  No Parq  |  Placa  |  Parqueado?");
       printer.printCharAtCol(5, 1, 46, "-");
       
       int pie = 1;
            
        for (int i = 0; i < filas; i++) { 
            printer.printTextWrap(5 + i, 10, 1, 46, Table_estado.getValueAt(i, 0)+"  |  "+Table_estado.getValueAt(i, 1)+"  |  "+Table_estado.getValueAt(i, 2)+"  |  "+Table_estado.getValueAt(i, 3)); 
            pie++; 
        } 
               
        if(pie > filas){
            printer.printTextWrap(filas + 6, 0, 8, 46, "Todos los derechos reservados ");
        }
             
        printer.toFile("bin\\impresion.txt");

      FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("bin\\impresion.txt");
        } catch (FileNotFoundException ex) {
            log.fatal("ERROR - Se ha producido un error de localizacion del archivo impresion.txt: " + ex);
        }
        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();


        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);

            } catch (PrintException ex) {
                log.fatal("ERROR - Se ha producido un error al intentar imprimir el reporte de estado del parqueadero: " + ex);
            }
        } else {
           log.fatal("ERROR - Se ha producido un error al intentar imprimir el reporte de estado del parqueadero, verifique la conexión de su impresora");
        }

        //inputStream.close();
       
    }
    
    //Metodo que permite mostrar la tabla del estado de parqueadero en tiempo real (funcion del panel caja boton "Estado de parqueadero")
    public void mostrarTablaDeEstadoDelParqueaderoEnTiempoReal(){
        
        //Consulta de datos a la BD
        try {
            modeloEstadoParq = new DefaultTableModel(0, 4);
            Table_estado.setModel(modeloEstadoParq);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select  Estado, Nombre_parqueadero, Placa, Esta_en_parqueadero from parqueaderos");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();
            
           
            //Aqui colocamos las etiquetas de la tabla
            JTableHeader tableHeader = Table_estado.getTableHeader();
            TableColumnModel tableColumnModel = tableHeader.getColumnModel();
            
            TableColumn tableColumn0 = tableColumnModel.getColumn(0);
            tableColumn0.setHeaderValue( "Estado" );
                        
            TableColumn tableColumn2 = tableColumnModel.getColumn(1);
            tableColumn2.setHeaderValue( "N° parq" );
            
            TableColumn tableColumn3 = tableColumnModel.getColumn(2);
            tableColumn3.setHeaderValue( "Placa" );
                       
            TableColumn tableColumn4 = tableColumnModel.getColumn(3);
            tableColumn4.setHeaderValue( "Parqueado?" );
                       
            tableHeader.repaint();
                
            int[] anchosTabla = {5,15,20,10};

            for(int x=0; x < cantidadColumnas; x++){
                Table_estado.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloEstadoParq.addRow(filas);
            }
                cn.close();        

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar información del parqueadero en tiempo real, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al intentar cargar la tabla de vehiculos en el parqueadero en tiempo real: " + e);
        }
    }
    
    //Metodo que permite mosrar la tabla de vehiculos en facturación en tiempo real (tabla del panel caja)
    public void mostrarTablaFacturacionDeVehiculosEnParqueaderoPanelCaja(){
        
        try {
            modeloCaja = new DefaultTableModel();
            table_operacionParqueadero.setModel(modeloCaja);

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                        "select Fac.Fecha_factura, Fac.Placa, Fac.Propietario, Parq.Nombre_parqueadero from facturas Fac INNER JOIN parqueaderos Parq ON Fac.No_parqueadero = Parq.Id_parqueadero AND Estado_fctra ='Abierta'");

            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadColumnas = rsmd.getColumnCount();

            modeloCaja.addColumn("Fecha");
            modeloCaja.addColumn("Placa");
            modeloCaja.addColumn("Propietario");
            modeloCaja.addColumn("Numero de Parqueadero");

            int[] anchosTabla = {10,10,15,5};

            for(int x=0; x < cantidadColumnas; x++){
                table_operacionParqueadero.getColumnModel().getColumn(x).setPreferredWidth(anchosTabla[x]);
            }

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {

                    filas[i] = rs.getObject(i + 1); 
                }
                modeloCaja.addRow(filas);
            }
            cn.close();                    
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar operación del Parqueadero, ¡Contacte al administrador!");
            log.fatal("ERROR - Se ha producido un error al intentar visualizar la operación del Parqueadero: " + e); 
        }
    }
    
    //Metodo que permite consultar el id de un parqueadero utilizando su nombre
    public int consultarIdParqueadero(String nombreParq){
        
        int idParqueadero = 0;
                
        //Consulta el id del parqueadero utilizando su id
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select Id_parqueadero from parqueaderos where Nombre_parqueadero = '" + nombreParq + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idParqueadero = rs.getInt("Id_parqueadero");   
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al comparar parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar el id de un parquadero utilizando su nombre: " + e);
        } 
        return idParqueadero;   
    }
    
    //Metodo que actualiza la informacion de un propietario en un parqueadero
    public void actualizarInfoDePropietarioEnParqueadero(String placa, String dueño, String noParq){
                
        try{
            Connection cn3 = Conexion.conectar();
            PreparedStatement pst3 = cn3.prepareStatement("update parqueaderos set Placa='"+placa+"', Propietario='"+dueño+"' where Nombre_parqueadero='"+noParq+"'");

            pst3.executeUpdate();
            cn3.close();
           
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "¡¡ERROR al actualizar informacion en parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al actualizar info del propietario de un parqueadero" + ex);
        } 
    }
    
    //Metodo que consulta el tipo de parqueadero mediante su id
    public String consultarTipoParqueaderoMedianteID(int idParq){
        
        String tipoParq = "";
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                "select TipoParq from parqueaderos where Id_parqueadero = " + idParq);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tipoParq = rs.getString("TipoParq");  
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "¡¡ERROR al consultar tipo parqueadero!!, contacte al administrador.");
            log.fatal("ERROR - Se ha producido un error al intentar validar el tipo de un parquadero utilizando su ID: " + e);
        } 
        return tipoParq;
    }
    
    //Metodo que ejecuta el hilo que trae listado de parqueaderos visitante disponibles en tiempo real    
    @Override
    public void run() {
        
        Thread ct1 = Thread.currentThread();
        Thread ct2 = Thread.currentThread();
        Thread ct3 = Thread.currentThread();
                
        while(ejecutarHiloParqueaderosVisitantesDisponiblesPanelCaja == true){          
            
            if(ct1 == hilo1){
            //Cargamos los datos en los combobox
            DefaultComboBoxModel modeloParq = new DefaultComboBoxModel(parq.mostrarParqueaderosTipoVisitanteDisponibles());
            cmb_numParqueadero.setModel(modeloParq);

                try{
                    ct1.sleep(100000);
                }catch(InterruptedException e){
                    log.fatal("ERROR - Se ha producido un error al intentar cargar el listado de parqueaderos visitantes disponibles: " + e); 
                }
            }
        }    
        
        while(ejecutarHiloParqueaderosVisitantesDisponiblesEdicionFacturas == true){
            
            if(ct2 == hilo2){
                //Cargamos los datos en los combobox
                DefaultComboBoxModel modeloParq = new DefaultComboBoxModel(parq.mostrarParqueaderosTipoVisitante());
                cmb_parqVisitantesFinal.setModel(modeloParq);

                try{
                    ct2.sleep(100000);
                }catch(InterruptedException e){
                    log.fatal("ERROR - Se ha producido un error al intentar cargar el listado de parqueaderos visitantes disponibles en edicion factura final: " + e); 
                }
            }
            
            if(ct3 == hilo3){
                //Cargamos los datos en los combobox
                DefaultComboBoxModel modeloParq = new DefaultComboBoxModel(parq.mostrarParqueaderosTipoVisitanteDisponibles());
                cmb_parqVisitantes.setModel(modeloParq);

                try{
                    ct3.sleep(100000);
                }catch(InterruptedException e){
                    log.fatal("ERROR - Se ha producido un error al intentar cargar el listado de parqueaderos visitantes disponibles en edicion factura ingreso: " + e); 
                }
            }
        }
    }
    
}
