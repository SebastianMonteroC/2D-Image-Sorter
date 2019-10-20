import javax.swing.*;

public class ProcesadorDeImagenes {
    String entrada;
    Imagen i;
    int m[][];
    int matrizNueva[][];
    int colorFondo;
    int limite[] = new int[4];// Limites que permiten conocer las medidas de la figura. La matriz guarda los 4 limites en orden
    // limite[0] = limiteArriba / limite[1] = limiteAbajo / limite[2] = limiteIzquierdo / limite[3] = limiteDerecho
    
    int cantidadDePixeles;
    
    public ProcesadorDeImagenes(String entrada) {
        this.entrada = entrada;
        i = new Imagen(entrada);
        m = i.getMatriz();
        matrizNueva = new int[m.length][m[0].length];
        colorFondo = m[0][0];
    }
    
    public void procesarImagen(){
        int colorBorde;
        int colorRelleno;
        Figura figura[] = new Figura[10];
        int cantidadFiguras = 0;
        for(int fila = 0; fila < m.length; fila++){
            for(int columna = 0; columna < m[fila].length; columna++){
                if(m[fila][columna] != colorFondo){
                    matrizLimpia(); 
                    colorBorde = m[fila][columna];
                    colorRelleno = sacarColorRelleno(fila,columna,colorBorde);
                    setLimitesIniciales(fila,columna);
                    sacarFigura(fila,columna);
                    figura[cantidadFiguras] = new Figura(colorFondo, colorBorde, colorRelleno, matrizNueva,limite,cantidadDePixeles);
                    figura[cantidadFiguras].mostrarFigura();
                    cantidadFiguras++;
                    cantidadDePixeles = 0;
                }
            }
        }
    }
    
    public int sacarColorRelleno(int fila, int columna,int colorBorde){
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
        
        while(contador < 9){ //While que analiza los 9 pixeles alrededor de un pixel en especifico.
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
    
    public void matrizLimpia(){
        for(int j = 0; j < matrizNueva.length; j++){
            for(int k = 0; k < matrizNueva[j].length; k++){
                matrizNueva[j][k] = colorFondo;
            }
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
}

