public class Figura {
    private Imagen imagen;
    private int limite[];
    private int color[]; //[0] = colorFondo / [1] = colorBorde / [2] = colorRelleno
    private int matriz[][];
    private int copiaMatriz[][];
    private int matrizDeElementos[][];
    
    private int cantidadManchas;
    private int anchoOriginal;
    private int largoOriginal;
    private int ancho;
    private int largo; 
    private int area;
    private int escala;
    
    public Figura(int colorIn[], int matrizIn[][], int matrizDeElementosIn[][], int limite[]){
        color = new int[3];
        this.color[0] = colorIn[0];
        this.color[1] = colorIn[1];
        this.color[2] = colorIn[2];
        this.limite = limite;
        copiarMatriz(matrizIn,1);
        copiarMatriz(matrizDeElementosIn,2);
        calcularMedidas();
        anchoOriginal = ancho;
        largoOriginal = largo;
        calcularArea();
        this.escala = 1;
        sacarManchas();
        imagen = new Imagen(matriz);
    }
    
    public void copiarMatriz(int matrizIn[][],int opcionMatriz){
        switch(opcionMatriz){
            case 1:
                this.matriz = new int [matrizIn.length][matrizIn[0].length];
                for(int i = 0; i < matrizIn.length; i++){
                    for(int j = 0; j < matrizIn[i].length; j++){
                        this.matriz[i][j] = matrizIn[i][j];
                    }
                }
            break;
            case 2:
                this.matrizDeElementos = new int [matrizIn.length][matrizIn[0].length];
                for(int i = 0; i < matrizIn.length; i++){
                    for(int j = 0; j < matrizIn[i].length; j++){
                        this.matrizDeElementos[i][j] = matrizIn[i][j];
                    }
                }
            break;
        }
    }
    
    /* @Funcion: Calcula el largo y el ancho de la figura según su largo y ancho
     * @Param: null
     * @Return: void
     */
    public void calcularMedidas(){
        largo = ((limite[1]+1) - (limite[0]-1));
        ancho = ((limite[3]+1) - (limite[2]-1));
    }
    
    /* @Funcion: Calcula la cantidad de pixeles que tiene una figura
     * @Param: null
     * @Return: void
     */
    public void calcularArea(){
        for(int i = 0; i < matrizDeElementos.length; i++){
            for(int j = 0; j < matrizDeElementos[i].length; j++){
                if(matrizDeElementos[i][j] != 0){
                    ++area;
                }
            }
        }
    }
    
    /* @Funcion: cuenta la cantidad de manchas que hay por figura.
    * @Param: - 
    * @Return: -
    */
    public void sacarManchas(){
        int manchasEncontradas = 0;
        copiaMatriz = new int[matriz.length][matriz[0].length];
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[i].length; j++){
                copiaMatriz[i][j] = matriz[i][j];
            }
        }
        
        for(int f = 0; f < matrizDeElementos.length; f++){
            for(int c = 0; c < matrizDeElementos[f].length; c++){
                if(matrizDeElementos[f][c] == 3){
                    analizarMancha(f,c);
                    manchasEncontradas++;
                }
            }
        }
        this.cantidadManchas = manchasEncontradas;
    }
    
    /* @Funcion: Analiza la mancha por columnas y filas y los pixeles alrededor de un pixel especifico
     * @Param: fila es la fila de elementos a analizar
     * columna es la columna de elementos a analizar
     * @Return: void
     */
    public void analizarMancha(int fila, int columna){
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        matrizDeElementos[fila][columna] = 4;
        
        while(contador < 8){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            if(matrizDeElementos[fila+variableFila][columna+variableColumna] == 3){
                analizarMancha(fila+variableFila,columna+variableColumna);
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
    
    /* @Funcion: método para mostrar una figura.
    * @Param: - 
    * @Return: -
    */
    public void mostrarFigura(){
       imagen.dibujar();
    }
    
    public void setImagen(){
        this.imagen = new Imagen(matriz);
    }
    
    public void setMatriz(int[][] matriz){
        this.matriz = matriz;
        setImagen();
    }
    
    public void setLimites(int[] limite){
        for(int i = 0; i < limite.length; i++){
            this.limite[i] = limite[i];
        }
        //calcularMedidas();
    }
    
    public void setEscala(){
        escala = escala +2;
    }
    
    public void setLimiteEspecifico(int indice, int limite){
        this.limite[indice] = limite;
    }
    
    public int[][] getMatriz(){
        return matriz;
    }
    
    public int getSize(){
        return area;
    }
    
    public int getLargo(){
        return largo;
    }
    
    public int getAncho(){
        return ancho;
    }
    
    public int getLimite(int posicion){
        return limite[posicion];
    }
    
    public int getManchas(){
        return cantidadManchas;
    }
    
    public int getEscala(){
        return escala;
    }
}


        
        
        