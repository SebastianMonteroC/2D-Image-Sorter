
public class Main
{
    public static void main(String [] args){
        boolean archivoEncontrado = false;
        Interfaz interfaz = new Interfaz();
        
        while(!archivoEncontrado){
            try{
                String nombreArchivo = interfaz.pedirNombreImagen();
                ProcesadorDeImagenes procesadorDeImagenes = new ProcesadorDeImagenes(nombreArchivo);
                interfaz.setProcesadorDeImagenes(procesadorDeImagenes);
                procesadorDeImagenes.setInterfaz(interfaz);        
                archivoEncontrado = true;
            }
            catch(Exception e){
                interfaz.mensaje("Error: El nombre del archivo no fue encontrado");
            }
        }
        interfaz.mostrarVentana();
        
    }
}
