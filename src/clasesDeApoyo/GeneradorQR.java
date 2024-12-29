package clasesDeApoyo;

import com.barcodelib.barcode.QRCode;
import java.io.File;
import java.net.URL;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author ASUS
 */
public class GeneradorQR {
    
    //Definimos las propiedades que tendran los codigos qr
    int udm = 0;
    int resol = 72;
    float mi = 0.000f;
    float md = 0.000f;
    float ms = 0.000f;
    float min = 0.000f;
    int rot = 0;
    float tam = 5.000f;
    
    private static final Logger log = Logger.getLogger(GeneradorQR.class);
    private URL url = Conexion.class.getResource("Log4j.properties");
    
    String rutaQrs = new File("").getAbsolutePath()+"\\qrCodes";
    File carpetaQrs = new File(rutaQrs);

    //Constructor
    public GeneradorQR() {
    }
    
    //Metodo que se encarga de generar el codigo Qr a parrir de un texto de entrada
    public void generarQR(String texto){
               
        //Verificamos la existencia de la carpeta que guarda los codigos qr
        if(!carpetaQrs.exists()){
            crearCarpetaDeQrs();
        }
        
        try{
            //Creamos el codigo qr
            QRCode codigoQr = new QRCode();
            //Asignamos propiedades al codigo qr
            codigoQr.setData(texto);
            codigoQr.setDataMode(QRCode.MODE_BYTE);
            codigoQr.setUOM(udm);
            codigoQr.setLeftMargin(mi);
            codigoQr.setResolution(resol);
            codigoQr.setRightMargin(md);
            codigoQr.setTopMargin(ms);
            codigoQr.setBottomMargin(min);
            codigoQr.setRotate(rot);
            codigoQr.setModuleSize(tam);

            String archiv = rutaQrs+"/"+texto+".gif";
            codigoQr.renderBarcode(archiv);

            //Abrimos el codigo qr
            //Desktop d = Desktop.getDesktop();
            //d.open(new File(archiv));
                       
        }catch(Exception e){
            log.fatal("ERROR - Se ha producido un error al generar el codigo qr: " + e);
        }
    }
    
    //Metodo que se encarga de actualizar en la carpeta qrCodes, el codigo qr de un vehiculo
    public void actualizarQR(String placaABuscar, String propietarioABuscar, String placa, String propietario){
        
        String infoQrNvo = "";
        String qrABuscar = placaABuscar+" - "+propietarioABuscar; 
        String qrNuevo = placa+" - "+propietario;
        
        //Validamos si el nombre del qr a buscar no es igual al que se pretende generar
        if(!qrABuscar.equals(qrNuevo)){
            
            //Generamos el nuevo nombre del codigo qr a generar
            if(!placaABuscar.equals(placa)){
               infoQrNvo = placa;    
            }else{
               infoQrNvo = placaABuscar; 
            }

            if(!propietarioABuscar.equals(propietario)){
               infoQrNvo = infoQrNvo+" - "+propietario;    
            }else{
               infoQrNvo = infoQrNvo+" - "+propietarioABuscar; 
            }
            
            //Buscamos el codigo qr previamente existente para eliminarlo
            if(carpetaQrs.exists() && carpetaQrs.isDirectory()){
                File[] listadoDeQrs = carpetaQrs.listFiles();
                
                //Contamos cuantos codigos qr tiene la carpeta de qrs
                int cantidadQrs = contarCodigosQr(listadoDeQrs);
                                
                if(cantidadQrs > 0){
                    for(File qr : listadoDeQrs){
                        if(qr.isFile() && qr.getName().equals(qrABuscar+".gif")){
                            //Eliminamos el codigo qr previamente existente y generamos el nvo codigo
                            qr.delete();
                            generarQR(infoQrNvo);
                        }else{
                            generarQR(infoQrNvo);
                        }
                   }
                }else{
                    generarQR(infoQrNvo);
                }               
            }else{
                crearCarpetaDeQrs();
                generarQR(infoQrNvo);
            }   
        }
    }
    
    //Metodo que crea la carpeta donde se almaccenan los codigos qr
    public void crearCarpetaDeQrs(){
        carpetaQrs.mkdir();
    }
    
    //Metodo que cuenta cuantos archivos hay la carrpeta de qrs
    public int contarCodigosQr(File[] qrs){
        int cantidad = 0;
        for(File qr : qrs){
            if(qr.isFile()){
                cantidad++;
            }
       }
        return cantidad;
    }
    
    //Metodo que elimina un codigo qr de la carpeta de qrs
    public void eliminarQr(String texto){
               
        //Buscamos el codigo qr para eliminarlo
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
    }
}
