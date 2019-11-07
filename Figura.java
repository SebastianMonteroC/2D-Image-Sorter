public class Figura {
    private Imagen imagen;
    private int manchas;
    private int limite[];
    private int colorFondo;
    private int colorBorde;
    private int colorRelleno;
    private int matriz[][];
    private int copiaMatriz[][];
    private int cantidadManchas;
    private int ancho;
    private int largo;
    private int cantidadDePixeles; //Cantidad de pixeles DE LA FIGURA (no toma en cuenta el fondo)
    private int area;
    
    public Figura(int colorFondo, int colorBorde, int colorRelleno, int matriz[][], int limite[], int cantidadDePixeles){
        this.colorFondo = colorFondo;
        this.colorBorde = colorBorde;
        this.colorRelleno = colorRelleno;
        this.matriz = matriz;
        this.copiaMatriz = matriz;
        this.limite = limite;
        this.cantidadDePixeles = cantidadDePixeles;
        calcularMedidas();
        //sacarManchas();
        imagen = new Imagen(matriz);
    }
    
    public void calcularMedidas(){
        largo = ((limite[1]+1) - (limite[0]-1));
        ancho = ((limite[3]+1) - (limite[2]-1));
        area = largo * ancho;
    }
    
    public void mostrarFigura(){
       // System.out.println("Largo: " + largo + "\nAncho: "+ ancho + "Area: " + area);
        //System.out.println(limite[0] + "\n" + limite[1] + "\n" + limite[2] + "\n" + limite[3]);
       imagen.dibujar();
    }
    
    public void sacarManchas(){
        for(int fila = limite[0]; fila < limite[1]; fila++){
            for(int columna = limite[2]; columna < limite[3]; columna++){
                if(matriz[fila][columna] != colorFondo && matriz[fila][columna] != colorBorde && matriz[fila][columna] != colorRelleno){
                    analizarPixeles(fila,columna);
                    
                }
            }
        }
        System.out.println(manchas);
    }
    
    public void analizarPixeles(int fila, int columna){
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        
        copiaMatriz[fila][columna] = colorFondo;
        
        while(contador < 9){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            if(copiaMatriz[fila][columna] != colorFondo && copiaMatriz[fila][columna] != colorRelleno){
                analizarPixeles(fila+variableFila,columna+variableColumna);
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

    public void setImagen(){
        this.imagen = new Imagen(matriz);
    }
    
    public void setMatriz(int[][] matriz){
        this.matriz = matriz;
        setImagen();
    }
    
    public void setLimites(int[] limite){
        this.limite = limite;
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
        return manchas;
    }
}


        
        
        