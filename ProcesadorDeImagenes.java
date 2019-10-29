import javax.swing.*;
public class ProcesadorDeImagenes {
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
    public ProcesadorDeImagenes(String entrada) {
        i = new Imagen(entrada);
        m = i.getMatriz();
        matrizNueva = new int[m.length][m[0].length];
        colorFondo = m[0][0];
        cantidadFiguras = 0;
    }
    
    public void procesarImagen(){
        int colorBorde;
        int colorRelleno;
        Figura figura[] = new Figura[100];
        for(int fila = 0; fila < m.length; fila++){
            for(int columna = 0; columna < m[fila].length; columna++){
                if(m[fila][columna] != colorFondo){
                    matrizLimpia(matrizNueva.length,matrizNueva[0].length);
                    colorBorde = m[fila][columna];
                    setLimitesIniciales(fila,columna);
                    
                    sacarFigura(fila,columna);
                    colorRelleno = -1;//sacarColorRelleno(limite[0],limite[2],colorBorde);//ARREGLAR CON FONDO BLANCO
                    
                    figura[cantidadFiguras] = new Figura(colorFondo, colorBorde, colorRelleno, matrizNueva,limite,cantidadDePixeles);
                    i = new Imagen(figura[cantidadFiguras].getMatriz());
                    figura[cantidadFiguras].mostrarFigura();
                    
                    cantidadFiguras++;
                    cantidadDePixeles = 0;
                }
            }
        }
        catalogo = new Catalogo(figura,cantidadFiguras);
        
        figuraMasGrande();
    }
    
    public void resizeFiguras(){
        //for(int i = 0; i < cantidadFiguras; i++){
            catalogo.getFiguraEspecifica(0).resize(matrizNueva);
        //}
    }
    
    public void agrandarImagen(/*int ima[][],int fila, int columna*/){
        boolean cuadradoBlanco =false;
        int contadorDeZoom=0;
        
        int zoom[][] = new int[300][300];
        for(int f =0; f<m.length; ++f){
            for(int c=0; c<m[f].length; ++c){
                zoom[f][c]=-1;
            }
        }
        //centrarFigura();
        //se detiene con un indexError, se devuelve a la matriz anterior
        for(int f =1; f<m.length; ++f){
            for(int c=1; c<m[f].length; ++c){
                if(m[f][c]!=-1){
                    if(zoom[f][c]==-1 && zoom[f-1][c]==-1 && zoom[f][c]==-1 && zoom[f][c-1]==-1){
                       zoom[f-1][c-1]=m[f][c];
                       zoom[f-1][c]=m[f][c];
                       zoom[f][c]=m[f][c];
                       zoom[f][c-1]=m[f][c];
                    }
                    else{
                        if(cuadradoBlanco==false){
                            while(cuadradoBlanco==false){
                                for(int i =1; i<zoom.length && cuadradoBlanco; ++i){
                                    for(int j=1; j<zoom[i].length && cuadradoBlanco; ++j){
                                        if(zoom[i][j]==-1 && zoom[i-11][j-1]==-1 && zoom[i-1][j]==-1 && zoom[i][j-1]==-1){
                                           cuadradoBlanco=true;
                                           zoom[i][j]=m[f][c];
                                           zoom[i-1][j-1]=m[f][c];
                                           zoom[i-1][j]=m[f][c];
                                           zoom[i][j-1]=m[f][c];
                                        }
                                    }
                                }
                            }
                        }
                        contadorDeZoom+=2;
                    }
                }
            }
            //Copiar todos los elementos en la matriz que entra por parametro y volver zoom -1 para no tener que crear otro
        }
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
        Imagen imagen1 = new Imagen(matrizNueva);
        imagen1.dibujar();
    }
    
    public int sacarColorRelleno(int fila, int columna, int colorBorde){
        int colorRelleno = 0;
        return colorRelleno;
    }
    
    public void setLimitesIniciales(int fila, int columna){
        limite[0] = fila;
        limite[1] = fila;
        limite[2] = columna;
        limite[3] = columna;
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
    public void revisarColorIgualAFondo(int colorFondo){
        boolean igualAFondo=false;
        if(colorFondo==-1){
            igualAFondo= true;
            //figura.sacarManchas();
        }
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

