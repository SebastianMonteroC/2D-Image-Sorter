import javax.swing.*;


public class ProcesadorDeImagenes {
    private Interfaz interfaz;
    private Catalogo catalogo;
    private String entrada;
    private Imagen i;
    private int m[][];
    private int matrizNueva[][];
    private int colorFondo;
    private int limite[] = new int[4];// Limites que permiten conocer las medidas de la figura. La matriz guarda los 4 limites en orden
    int cantidadFiguras;              // limite[0] = limiteArriba / limite[1] = limiteAbajo / limite[2] = limiteIzquierdo / limite[3] = limiteDerecho
    int cantidadDePixeles;
    int largoMayor = 0;
    int anchoMayor = 0;
    int recursiones = 0;
    int cantidadDeColores;
    int colorBorde;
    int colorRelleno;
    
    public ProcesadorDeImagenes(String entrada) {
        i = new Imagen(entrada);
        m = i.getMatriz();
        matrizNueva = new int[m.length][m[0].length];
        colorFondo = m[0][0];
        cantidadFiguras = 0;
        procesarImagen();
    }
    
    public void procesarImagen(){
        
        Figura figura[] = new Figura[100];
        
        for(int fila = 0; fila < m.length; fila++){
            for(int columna = 0; columna < m[fila].length; columna++){
                if(m[fila][columna] != colorFondo){
                    matrizLimpia(matrizNueva.length,matrizNueva[0].length);
                    colorBorde = m[fila][columna];
                    setLimitesIniciales();
                    //colorRelleno = sacarColorRelleno(fila,columna);
                    colorRelleno = -1;
                    sacarFigura(fila,columna);
                    //ARREGLAR CON FONDO BLANCO
                    figura[cantidadFiguras] = new Figura(colorFondo, colorBorde, colorRelleno, matrizNueva,limite,cantidadDePixeles);
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
    
    public void resizeFiguras(){
        for(int i = 0; i < cantidadFiguras; i++){ 
            centrarFigura(catalogo.getFiguraEspecifica(i));
            agrandarImagen(catalogo.getFiguraEspecifica(i));
            catalogo.getFiguraEspecifica(i).mostrarFigura();
        }
    }
    
    public void centrarFigura(Figura figura){
        Imagen imagen;
        int m1[][] = figura.getMatriz();
        int m2[][] = matrizNueva;
        int limite[] = new int[4];
        
        for(int f = 0; f < m2.length; f++){
            for(int c = 0; c < m2[f].length; c++){
                m2[f][c] = colorFondo;
            }
        }
        
        int largoMatriz = m2.length;
        int anchoMatriz = m2[1].length;
        
        limite[0] = (largoMatriz/2) - (figura.getLargo()/2);
        limite[1] = (largoMatriz/2) + (figura.getLargo()/2);
        limite[2] = (anchoMatriz/2) - (figura.getAncho()/2);
        limite[3] = (anchoMatriz/2) + (figura.getAncho()/2);
        
        int medioX = (anchoMatriz/2) - (figura.getAncho()/2);
        int medioY = (largoMatriz/2) - (figura.getLargo()/2);
        
        for(int i = figura.getLimite(0); i <= figura.getLimite(1); i++){
            for(int j = figura.getLimite(2); j <= figura.getLimite(3); j++){ 
               m2[medioY][medioX] = m1[i][j];
               medioX++;
            } 

            medioX = (anchoMatriz/2) - (figura.getAncho()/2);
            medioY++;
        }
        
        figura.setMatriz(m2);
        figura.setLimites(limite);
    }
    
    public void agrandarImagen(Figura figura){
        int contadorDeZoom = 0;
        int m1[][] = figura.getMatriz();
        int zoom[][] = new int[matrizNueva.length][matrizNueva[0].length];
        
        for(int f = 0; f < zoom.length; ++f){
            for(int c = 0; c< zoom[f].length; ++c){
                zoom[f][c]= colorFondo;
            }
        }
        int indiceZoomF = figura.getLimite(0);
        int indiceZoomC = figura.getLimite(2);
        try{
            for(int f = figura.getLimite(0); f < figura.getLimite(1); f++){
                for(int c = figura.getLimite(2); c < figura.getLimite(3); c++){
                    zoom[indiceZoomF][indiceZoomC] = m1[f][c];
                    zoom[indiceZoomF][indiceZoomC-1] = m1[f][c];
                    zoom[indiceZoomF-1][indiceZoomC-1] = m1[f][c];
                    zoom[indiceZoomF-1][indiceZoomC] = m1[f][c];
                    indiceZoomC += 2;
                } 
                indiceZoomF += 2;
                indiceZoomC = figura.getLimite(2);
            }
            figura.setMatriz(zoom);
            
            figura.mostrarFigura();
            figura.setLimiteEspecifico(1,figura.getLimite(1)+figura.getLargo());
            figura.setLimiteEspecifico(3,figura.getLimite(3)+figura.getAncho());
            figura.calcularMedidas();
        }
        catch(Exception e){
            System.out.println("No cabe");
        }
        
        matrizLimpia(matrizNueva.length,matrizNueva[0].length);
        
    }
    
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
    
    public int sacarColorRelleno(int fila, int columna){
        boolean colorEncontrado = false;
        int colorRelleno = -1;
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        int filaSiguiente = fila;
        int columnaSiguiente = columna;

        while(contador < 8){ //While que analiza los 8 pixeles alrededor de un pixel en especifico.
            if(m[fila+variableFila][columna+variableColumna] != colorBorde && m[fila+variableFila][columna+variableColumna] != colorFondo){
                colorRelleno = m[fila+variableFila][columna+variableColumna];
                colorEncontrado = true;
                contador = 8;
            }
            filaSiguiente = fila+variableFila;
            columnaSiguiente = columna+variableColumna;
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
        
        if(colorEncontrado == false){
            colorRelleno = sacarColorRelleno(filaSiguiente,columnaSiguiente);
        }
        return colorRelleno;
    
    }
    
    public void setLimitesIniciales(){
        limite[0] = m.length;
        limite[1] = 0;
        limite[2] = m[0].length;
        limite[3] = 0;
    }
    
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
    
    public void sacarFigura(int fila, int columna){
        
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        
        sacarLimite(fila,columna); //funcion que analiza si el pixel evaluado es el mas hacia arriba, abajo, izquierda o derecha
        // de esta manera, al final del proceso recursivo se pueden obtener las medidas de la figura
        
        matrizNueva[fila][columna] = m[fila][columna];
        m[fila][columna] = colorFondo;
        cantidadDePixeles++;
        
        while(contador < 8){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            if(m[fila+variableFila][columna+variableColumna] != colorFondo){
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
        }
    }
    
    public void matrizLimpia(int largo, int ancho){
        matrizNueva = new int[largo][ancho];
        for(int j = 0; j < largo; j++){
            for(int k = 0; k < ancho; k++){
                matrizNueva[j][k] = colorFondo;
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
    public void sacarFigura(int fila, int columna){
        matrizNueva[fila][columna] = m[fila][columna];
        m[fila][columna] = colorFondo;
        
        if(m[fila-1][columna-1] != -1){
            sacarFigura(fila-1,columna-1);
        }
       
        if(m[fila-1][columna] != -1){
            sacarFigura(fila-1,columna);
        }
        
        if(m[fila-1][columna+1] != -1){
            sacarFigura(fila-1,columna+1);
        }
            
        if(m[fila][columna-1] != -1){
            sacarFigura(fila,columna-1);
         }
               
        if(m[fila][columna+1] != -1){
            sacarFigura(fila,columna+1);
        }
                    
        if(m[fila+1][columna-1] != -1){
            sacarFigura(fila+1,columna-1);
        }
                       
        if(m[fila+1][columna] != -1){
            sacarFigura(fila+1,columna);
        }
                            
        if(m[fila+1][columna+1] != -1){
            sacarFigura(fila+1,columna+1);
        }
                                    
        System.out.println("Figura registrada.");
    }
    */
   /*public int sacarColorRelleno(int fila, int columna,int colorBorde){
        boolean colorEncontrado = false;
        int colorRelleno = 0;
        int contador = 0;
        int variableColumna = -1;
        int variableFila = -1;
        int filaSiguiente = fila;
        int columnaSiguiente = columna;
       
        while(contador < 8){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
            
            if(m[fila+variableFila][columna+variableColumna] != colorBorde && m[fila+variableFila][columna+variableColumna] != colorFondo){
                colorRelleno = m[fila+variableFila][columna+variableColumna];
                colorEncontrado = true;
            }
            filaSiguiente = fila+variableFila;
            columnaSiguiente = columna+variableColumna;
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
        if(colorEncontrado == false){
            colorRelleno = sacarColorRelleno(filaSiguiente,columnaSiguiente,colorBorde);
        }
        System.out.println(colorRelleno);
        return colorRelleno;
    }*/
}

