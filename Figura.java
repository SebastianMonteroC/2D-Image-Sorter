public class Figura {
    Imagen imagen;
    int limite[];
    int colorFondo;
    int colorBorde;
    int colorRelleno;
    int matriz[][];
    int cantidadManchas;
    int ancho;
    int largo;
    int cantidadDePixeles; //Cantidad de pixeles DE LA FIGURA (no toma en cuenta el fondo)
    int area;
    
    public Figura(int colorFondo, int colorBorde, int colorRelleno, int matriz[][], int limite[], int cantidadDePixeles){
        this.colorFondo = colorFondo;
        this.colorBorde = colorBorde;
        this.colorRelleno = colorRelleno;
        this.matriz = matriz;
        this.limite = limite;
        this.cantidadDePixeles = cantidadDePixeles;
        calcularMedidas();
        area = largo * ancho;
        imagen = new Imagen(matriz);
    }
    
    public void calcularMedidas(){
        largo = ((limite[1]+1) - (limite[0]-1))+1;
        ancho = ((limite[3]+1) - (limite[2]-1))+1;
    }
    
    public void mostrarFigura(){
        System.out.println("Largo: " + largo + "\nAncho: "+ ancho);
        imagen.dibujar();
    }
    
    public void sacarManchas(){
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        /*
        while(contador < 9){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            if(matriz[fila+variableFila][columna+variableColumna] != colorFondo){
                sacarFigura(fila+variableFila,columna+variableColumna);
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
        }*/
    }
    
}
