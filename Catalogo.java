
public class Catalogo {
    private Figura figura[];
    private int cantidadDeFiguras;
    
    public Catalogo(Figura figura[], int cantidadDeFiguras){
        this.cantidadDeFiguras = cantidadDeFiguras;
        crearCatalogo(figura);
    }

    public void crearCatalogo(Figura figura[]){
        this.figura = new Figura[cantidadDeFiguras];
        for(int i = 0; i < cantidadDeFiguras; i++){
            this.figura[i] = figura[i];
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
}
