
public class Main
{
    
    public static void main(String [] args){
        boolean archivoEncontrado = false;
        Interfaz interfaz = new Interfaz();
        String nombreArchivo = "";
        while(!archivoEncontrado){
            try{
                nombreArchivo = interfaz.pedirNombreImagen();
                Imagen i = new Imagen(nombreArchivo); 
                archivoEncontrado = true;
           }
            catch(Exception e){
                interfaz.mensaje("Error: El nombre del archivo no fue encontrado");
            }
        }
        ProcesadorDeImagenes procesadorDeImagenes = new ProcesadorDeImagenes(nombreArchivo);
        interfaz.setProcesadorDeImagenes(procesadorDeImagenes);
        procesadorDeImagenes.setInterfaz(interfaz);        
        archivoEncontrado = true;
        interfaz.mostrarVentana();
        
    }
}
