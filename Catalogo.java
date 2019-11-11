
public class Catalogo {
    private Figura figura[];
    private Interfaz interfaz;
    private int cantidadFiguras;
    
    public Catalogo(Figura figura[], int cantidadFiguras){
        this.cantidadFiguras = cantidadFiguras;
        crearCatalogo(figura);
    }

    /* @Funcion: Crea un vector de tipo Figura
     * @Param: vector de tipo Figura de la cual se extraen las Figuras
     * para luego introducirlas en el nuevo vector creado
     * @Return: void
     */
    public void crearCatalogo(Figura figura[]){
        this.figura = new Figura[cantidadFiguras];
        for(int i = 0; i < cantidadFiguras; i++){
            this.figura[i] = figura[i];
        }
    }
    
    /*@Funcion: recorre el vector y muestra las figuras dentro de el.
    * @Param: - 
    * @Return: -
    */
    public void mostrarInventario(){
        for(int i = 0; i < cantidadFiguras; i++){
                figura[i].mostrarFigura();
        }
    }
        
    /*@Funcion: filtra las figuras que se mostraran en pantalla según la cantidad de manchas en ellas.
    * @Param: int max, determina la cantidad maxima de manchas que se quiere observar.
    * @Param: int min, determina la cantidad minima de manchas que se quiere observar.
    * @Return: -
    */ 
    public void filtrarPorManchas(int max, int min){
        for(int n = 0; n < figura.length; n++){
            if(enRango(max, min, figura[n].getManchas())){
                figura[n].mostrarFigura();
            }
        }
    }
    
    /*@Funcion: muestra el numero de figura que el usuario quiere ver.
    * @Param: int numFigura, el numero de figura en el vector que el usuario quiere ver (un numero entre 0 y la cantidad de figuras en el vector)
    * @Param: - 
    * @Return: -
    */
    public void filtrarSegunNumFigura(int numFigura){
        if(numFigura < 0 || numFigura > cantidadFiguras){
            interfaz.mensaje("El numero debe ser de 0 a " + (cantidadFiguras-1));
        }
        else{
            figura[numFigura].mostrarFigura();
        }
    }
    
    /*@Funcion: Filtra las figuras según su escala máxima o mínima
     * @Param: max es el límite máximo de la escala
     * min es el límite mínimo de la escala 
     * @Return: void
     */
    public void filtrarSegunEscala(int max, int min){
        for(int i = 0; i < cantidadFiguras; i++){
            if(enRango(max,min,figura[i].getEscala())){
                figura[i].mostrarFigura();
            }
        }   
    }
    
    /* @Funcion: Filtra las figuras según sus dimensiones de ancho y largo de cada una
     * @Param: maxAncho es el ancho máximo que posee la figura seleccionada
     * minAncho es el ancho mínimo que posee la figura seleccionada
     * maxLargo es el largo máximo que posee la figura seleccionada
     * minLargo es el largo mínimo que posee la figura seleccionada
     * @Return: void
     */
    public void filtrarSegunDimensiones(int maxAncho, int minAncho, int maxLargo, int minLargo){
        for(int i = 0; i < cantidadFiguras; i++){
          if(enRango(maxAncho,minAncho,figura[i].getAncho()) && enRango(maxLargo, minLargo,figura[i].getLargo())){
            figura[i].mostrarFigura();
          }
        }

    }
    
    /* @Funcion: Filtra las figuras según el tamaño de sus áreas 
     * @Param: max es el área maxima que tiene la figura
     * min es el área mínima que tiene la figura
     * @Return: void
     */
    public void filtrarSegunArea(int max, int min){
        for(int i = 0; i < cantidadFiguras; i++){
          if(enRango(max,min,figura[i].getSize())){
            figura[i].mostrarFigura();
          }
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
    
    public void setInterfaz(Interfaz interfaz){
        this.interfaz = interfaz;
    }
    
    /* @Funcion: Verifica que el valor a evaluar este en un rango, es decir que sea mayor que min y menor que max.
    * @Param: int max, valor maximo en el rango.
    * @Param: int min, valor mínimo en el rango.
    * @Param: int valorAEvaluar, valor que sera analizado para ver si esta en el rango.
     @Return: Boolean determinando si el valor esta en el rango (true) o no (false). */
     
    public boolean enRango(int max, int min, int valorAEvaluar){
        return (valorAEvaluar >= min && valorAEvaluar <= max);
    }
}
