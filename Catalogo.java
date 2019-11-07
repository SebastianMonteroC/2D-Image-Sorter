
public class Catalogo {
    private Figura figura[];
    private Interfaz interfaz;
    private int cantidadFiguras;
    
    public Catalogo(Figura figura[], int cantidadFiguras){
        this.cantidadFiguras = cantidadFiguras;
        crearCatalogo(figura);
    }

    public void crearCatalogo(Figura figura[]){
        this.figura = new Figura[cantidadFiguras];
        for(int i = 0; i < cantidadFiguras; i++){
            this.figura[i] = figura[i];;
        }
    }
    
    public Figura getFiguraEspecifica(int indice){
        return figura[indice];
    }
    
    public void setFiguraEspecifica(int indice, Figura figura){
        this.figura[indice] = figura;
    }
    
    public Figura[] getFigura(){
        return figura;
    }
    public void mostrarInventario(){
        for(int i = 0; i < cantidadFiguras; i++){
                figura[i].mostrarFigura();
        }
    }
        
    public void filtrarPorManchas(int max, int min){
        for(int n = 0; n < figura.length; n++){
            if(enRango(max, min, figura[n].getManchas())){
                figura[n].mostrarFigura();
            }
        }
    }
    
    public void filtrarSegunNumFigura(int numFigura){
        if(numFigura < 0 || numFigura > cantidadFiguras){
            interfaz.mensaje("El numero debe ser de 0 a " + (cantidadFiguras-1));
        }
        else{
            figura[numFigura].mostrarFigura();
        }
    }
    
    public void filtrarSegunEscala(int max, int min){
        //code
    }
    
    public void filtrarSegunDimensiones(int maxAncho, int minAncho, int maxAltura, int minAltura){
        //code
    }
    
    public void filtrarSegunArea(int max, int min){
        //code
    }
    
    public void setInterfaz(Interfaz interfaz){
        this.interfaz = interfaz;
    }
    
    public boolean enRango(int max, int min, int valorAEvaluar){
        return (valorAEvaluar > min && valorAEvaluar < max);
    }
}
