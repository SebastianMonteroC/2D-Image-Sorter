
import javax.swing.*;


public class ProcesadorDeImagenes {
    private Interfaz interfaz;
    private Catalogo catalogo;
    private String entrada;
    private Imagen i;
    private int m[][];
    private int matrizNueva[][];
    private int matrizDeElementos[][];
    private int colorFondo;
    private int limite[] = new int[4];// Limites que permiten conocer las medidas de la figura. La matriz guarda los 4 limites en orden
    private int cantidadFiguras;      // limite[0] = limiteArriba / limite[1] = limiteAbajo / limite[2] = limiteIzquierdo / limite[3] = limiteDerecho
    private int cantidadDePixeles;
    private int largoMayor = 0;
    private int anchoMayor = 0;
    private int recursiones = 0;
    private int cantidadDeColores;
    private int colorBorde;
    private int colorRelleno;
    
    public ProcesadorDeImagenes(String entrada) {
        i = new Imagen(entrada);
        m = i.getMatriz();
        matrizNueva = new int[m.length][m[0].length];
        colorFondo = m[0][0];
        cantidadFiguras = 0;
        procesarImagen();
    }
    
    /* @Funcion: Ser la cabeza del programa, llama los metodos necesarios para procesar la imagen.
     * @Param: -
     * @Return: -
     */
    public void procesarImagen(){
        
        Figura figura[] = new Figura[100];
        int copiaMatrizNueva[][] = new int[matrizNueva.length][matrizNueva[0].length];
        for(int fila = 0; fila < m.length; fila++){
            for(int columna = 0; columna < m[fila].length; columna++){
                if(m[fila][columna] != colorFondo){
                    int color[] = new int [3];
                    matrizLimpia(matrizNueva.length,matrizNueva[0].length);
                    colorBorde = m[fila][columna];
                    setLimitesIniciales();
                    
                    matrizDeElementos = new int[matrizNueva.length][matrizNueva[0].length];
                    inicializarMatrizElementos();
                      
                    sacarBorde(fila,columna);
                    inundarEnCruz(0,0,colorFondo,0);   
                    
                    colorRelleno = sacarColorRelleno();

                    colorearImagen();
                    
                    for(int i = 0; i < matrizNueva.length; i++){
                        for(int j = 0; j < matrizNueva[i].length;j++){
                            copiaMatrizNueva[i][j] = matrizNueva[i][j];
                        }
                    }
                    
                    analizarRelleno();

                    color[0] = colorFondo;
                    color[1] = colorBorde;
                    color[2] = colorRelleno;
                    
                    
                    figura[cantidadFiguras] = new Figura(color, copiaMatrizNueva, matrizDeElementos, limite);
                    
                    
                    cantidadFiguras++;
                    cantidadDePixeles = 0;
                    
                    limite = new int[4];
                    
                }
            }
        }
        catalogo = new Catalogo(figura,cantidadFiguras);
        
        figuraMasGrande();
        resizeFiguras();
        
    }
    
   
    /* @Funcion: Llamar los metodos de centrado y agrandado (en caso de ser necesario)
     * @Param: -
     * @Return: -
     */
    
    public void resizeFiguras(){
        boolean maxSize = false;
        
        for(int i = 0; i < cantidadFiguras; i++){ 
            
            while(!maxSize){ 
                maxSize = agrandarImagen(catalogo.getFiguraEspecifica(i));
            }
            centrarFigura(catalogo.getFiguraEspecifica(i));
            maxSize = false;
        }
    }

    /* @Funcion: Pinta en la matriz nueva, los pixeles de la original
     * @Param: -
     * @Return: -
     */
    public void colorearImagen(){
        for(int i = 0; i < matrizNueva.length; i++){
            for(int j = 0; j < matrizNueva[i].length; j++){
                if(matrizDeElementos[i][j] == 0){
                    matrizNueva[i][j] = colorFondo;
                }
                if(matrizDeElementos[i][j] == 1){
                    matrizNueva[i][j] = colorBorde;
                    m[i][j] = colorFondo;
                }
                if(matrizDeElementos[i][j] == 3){
                    matrizNueva[i][j] = m[i][j];
                    m[i][j] = colorFondo;
                }
            }
        }
    }
    
    /* @Funcion: Analiza el relleno de una figura para mappearlo en matrizDeElementos
     * @Param: -
     * @Return: -
     */
    public void analizarRelleno(){
        boolean esparcido = false;
        for(int i = 0; i < matrizDeElementos.length; i++){
            for(int j = 0; j < matrizDeElementos[i].length; j++){
                if(matrizDeElementos[i][j] == 3){
                    inundarEnCruz(i,j,colorRelleno,2);
                    esparcido = true;
                    break;
                }
            }
            if(esparcido){
                break;
            }
        }
        
    }
    
    /* @Funcion: Inunda pixeles en forma de cruz. Se utiliza para sacar el fondo y el relleno.
     * @Param: 
     * int fila: fila en donde comienza a inundar
     * int columna: columna en donde comienza a inundar
     * int color: color que debe verificar para saber si el pixel analizado debe ser rellenado
     * int valorAsignado: valor que se le asigna a esa posicion en la matrizDeElementos
     * @Return: -
     */
    public void inundarEnCruz(int fila, int columna, int color, int valorAsignado){ 
        matrizNueva[fila][columna] = matrizNueva[fila][columna] -1;
        matrizDeElementos[fila][columna] = valorAsignado;
        if(!(fila == 0)){
            if(matrizNueva[fila-1][columna] == color){
                inundarEnCruz(fila-1,columna,color,valorAsignado);
            }
        }
        if(!(columna == 0)){
            if(matrizNueva[fila][columna-1] == color){
                inundarEnCruz(fila,columna-1,color,valorAsignado);         
            }
        }  
        if(!(columna == matrizNueva[0].length-1)){
            if(matrizNueva[fila][columna+1] == color){
                inundarEnCruz(fila,columna+1,color,valorAsignado);        
            }
        }
        if(!(fila == matrizNueva.length-1)){
            if(matrizNueva[fila+1][columna] == color){
                inundarEnCruz(fila+1,columna,color,valorAsignado);       
            }
        }
    }
    
    /* @Funcion: Centrar la figura en la matriz
     * @Param: Figura figura: la figura que sera centrada
     * @Return: -
     */
    public void centrarFigura(Figura figura){
        Imagen imagen;
        int m1[][] = figura.getMatriz();
        int m2[][] = new int[matrizNueva.length][matrizNueva[0].length];
        int limite1[] = new int[4];
        
        for(int f = 0; f < m2.length; f++){
            for(int c = 0; c < m2[f].length; c++){
                m2[f][c] = colorFondo;
            }
        }
        
        int largoMatriz = m2.length;
        int anchoMatriz = m2[1].length;
        
        limite1[0] = (largoMatriz/2) - (figura.getLargo()/2);
        limite1[1] = (largoMatriz/2) + (figura.getLargo()/2);
        limite1[2] = (anchoMatriz/2) - (figura.getAncho()/2);
        limite1[3] = (anchoMatriz/2) + (figura.getAncho()/2);
 
        int medioX = (anchoMatriz/2) - (figura.getAncho()/2);
        int medioY = (largoMatriz/2) - (figura.getLargo()/2);
                
        for(int i = figura.getLimite(0); i < figura.getLimite(1); i++){
            for(int j = figura.getLimite(2); j < figura.getLimite(3); j++){ 
               m2[medioY][medioX] = m1[i][j];
               medioX++;
            } 
            medioX = (anchoMatriz/2) - (figura.getAncho()/2);
            medioY++;
        }
        
        figura.setMatriz(m2);
        figura.setLimites(limite1);
        
        
    }
    
    /* @Funcion: Agranda la figura X2 que entra por parametro
     * @Param: Figura figura: La figura que sera agrandada
     * @Return: boolean maxSize para saber si ya alcanzo su tamano maximo
     */
    public boolean agrandarImagen(Figura figura){
        
        boolean maxSize = false;
        
        int m1[][] = figura.getMatriz();
        int zoom[][] = new int[matrizNueva.length][matrizNueva[0].length];
        
        for(int f = 0; f < zoom.length; ++f){
            for(int c = 0; c< zoom[f].length; ++c){
                zoom[f][c]= colorFondo;
            }
        }
        
        int indiceZoomF = 2;
        int indiceZoomC = 2;
        
        try{
            
            for(int f = figura.getLimite(0); f <= figura.getLimite(1) ; f++){
                for(int c = figura.getLimite(2); c <= figura.getLimite(3); c++){
                    zoom[indiceZoomF][indiceZoomC] = m1[f][c];
                    zoom[indiceZoomF][indiceZoomC-1] =  m1[f][c];
                    zoom[indiceZoomF-1][indiceZoomC-1] =  m1[f][c];
                    zoom[indiceZoomF-1][indiceZoomC] =  m1[f][c];
                    indiceZoomC += 2;
                } 
                indiceZoomF += 2;
                indiceZoomC = 2;
            }
            
            figura.setMatriz(zoom);
            
            figura.setLimiteEspecifico(1, (figura.getLargo() ) * 2 - 2);
            figura.setLimiteEspecifico(0, 1);
            figura.setLimiteEspecifico(3, (figura.getAncho() )*2 - 2);
            figura.setLimiteEspecifico(2, 1);
            
            figura.calcularMedidas();
            
            figura.setEscala();
        }
        catch(Exception e){
            maxSize = true;
        }
        
        matrizLimpia(matrizNueva.length,matrizNueva[0].length);
        return maxSize;
        
    }
    
    /* @Funcion: Analiza todas las figuras para sacar la que tenga largo mayor y ancho mayor y asigna esos valores
     * a la matrizNueva que sera utilizada para colocar las demas figuras
     * @Param: -
     * @Return: -
     */
    public void figuraMasGrande(){
        int matriz[][];
        int figuraActualLargo;
        int figuraActualAncho;
        for(int i = 0; i < cantidadFiguras; i++){
            figuraActualLargo = (catalogo.getFiguraEspecifica(i)).getLargo();
            figuraActualAncho = (catalogo.getFiguraEspecifica(i)).getAncho();
            if(figuraActualLargo > largoMayor){
                largoMayor = figuraActualLargo;
            }
            if(figuraActualAncho > anchoMayor){
                anchoMayor = figuraActualAncho;
            }
        }
        matrizLimpia(largoMayor+2,anchoMayor+2);
        
        
    }
    
    /* @Funcion: Saca el color de relleno de una figura
     * @Param: -
     * @Return: Retorna el valor entero del color de relleno
     */
    public int sacarColorRelleno(){
        int colorRelleno = -1;
        boolean colorEncontrado = false;
        for(int i = 0; i < matrizDeElementos.length; i++){
            for(int j = 0; j < matrizDeElementos[i].length; j++){
                if(matrizDeElementos[i][j] == 3){
                    colorRelleno = m[i][j];
                    colorEncontrado = true;
                    break;
                }
            }
            if(colorEncontrado){
                break;
            }
        }
        return colorRelleno;
    }
    
    /* @Funcion: Inicializa los valores de la matriz limites para ser comparados
     * @Param: -
     * @Return: -
     */
    public void setLimitesIniciales(){
        limite[0] = m.length;
        limite[1] = 0;
        limite[2] = m[0].length;
        limite[3] = 0;
    }
    
    /* @Funcion: Analiza un pixel en una figura para saber si es el pixel que esta mas arriba, mas abajo, mas izquierdo o mas derecho en la figura
     * Esto servira para sacar el largo y el ancho y las medidas de la figura.
     * @Param: int fila: la fila del pixel
     * int columna: la columna del pixel
     * @Return: -
     */
    public void sacarLimite(int fila, int columna){
        if(fila < limite[0]){
            limite[0] = fila;
        }
        if(fila > limite[1]){
            limite[1] = fila;
        }
        if(columna < limite[2]){
            limite[2] = columna;
        }
        if(columna > limite[3]){
            limite[3] = columna;
        }
    }
    
    /* @Funcion: Analizar todos los pixeles que sean parte del borde de la figura que esta siendo analizada
     * @Param: int fila: la fila del pixel que esta siendo analizado
     * int columna: la columna del pixel que esta siendo analizado
     * @Return: -
     */
    public void sacarBorde(int fila, int columna){
        
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        
        sacarLimite(fila,columna); //funcion que analiza si el pixel evaluado es el mas hacia arriba, abajo, izquierda o derecha
        // de esta manera, al final del proceso recursivo se pueden obtener las medidas de la figura
        
        matrizNueva[fila][columna] = m[fila][columna];
        matrizDeElementos[fila][columna] = 1;
        m[fila][columna] = colorFondo;
        cantidadDePixeles++;
        
        while(contador < 8){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            if(m[fila+variableFila][columna+variableColumna] == colorBorde){
                sacarBorde(fila+variableFila,columna+variableColumna);
            }
            contador++;
            variableColumna++;
            if(variableFila == 0 && variableColumna == 0){
                variableColumna++;
            }
            if(contador == 3 || contador == 5){
                variableColumna = -1;
                variableFila++;
            }
        }
    }
    
    /* @Funcion: Limpia la matrizNueva para reciclarla
     * @Param: int largo: define la cantidad de filas de matrizNueva
     * int ancho: define la cantidad de columnas de matrizNueva
     * @Return: -
     */
    public void matrizLimpia(int largo, int ancho){
        matrizNueva = new int[largo][ancho];
        for(int j = 0; j < largo; j++){
            for(int k = 0; k < ancho; k++){
                matrizNueva[j][k] = colorFondo;
            }
        }
    }
    
    /* @Funcion: inicializa la matriz de elementos de la figura que esta siendo analizada
     * @Param: -
     * @Return: -
     */
    public void inicializarMatrizElementos(){
        for(int f = 0; f < matrizDeElementos.length; ++f){
            for(int c = 0; c < matrizDeElementos[f].length; ++c){
                if(f == 0 || f == matrizDeElementos.length-1 || c == 0 || c == matrizDeElementos[f].length-1){
                    matrizDeElementos[f][c] = 0;
                }
                else{
                    matrizDeElementos[f][c] = 3;
                }
            }
        }
    }
    
    public Catalogo getCatalogo(){
        return catalogo;
    }
    
    public void setInterfaz(Interfaz interfaz){
        this.interfaz = interfaz;
        catalogo.setInterfaz(interfaz);
    }
    
    /* MANERA ALTERNATIVA DE SACAR LA FIGURA (Mas comprensible, menos eficiente)
    public void sacarBorde(int fila, int columna){
        matrizNueva[fila][columna] = m[fila][columna];
        m[fila][columna] = colorFondo;
        
        if(m[fila-1][columna-1] != -1){
            sacarBorde(fila-1,columna-1);
        }
       
        if(m[fila-1][columna] != -1){
            sacarBorde(fila-1,columna);
        }
        
        if(m[fila-1][columna+1] != -1){
            sacarBorde(fila-1,columna+1);
        }
            
        if(m[fila][columna-1] != -1){
            sacarBorde(fila,columna-1);
         }
               
        if(m[fila][columna+1] != -1){
            sacarBorde(fila,columna+1);
        }
                    
        if(m[fila+1][columna-1] != -1){
            sacarBorde(fila+1,columna-1);
        }
                       
        if(m[fila+1][columna] != -1){
            sacarBorde(fila+1,columna);
        }
                            
        if(m[fila+1][columna+1] != -1){
            sacarBorde(fila+1,columna+1);
        }
                                    
        System.out.println("Figura registrada.");
    }
    */
   
}

