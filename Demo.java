import javax.swing.*;
public class Demo {

   public static void main(String argv[]){
      //   Imagen i = new Imagen("dibujo.gif");
      String entrada = "dibujo.gif";
      if(argv.length > 0){
         entrada = argv[0];  
      }
      Imagen i = new Imagen(entrada);
  
      i.dibujar();
      
      int m[][] = i.getMatriz();
      
      for(int f = 0; f < m.length; f++){
         for(int c = 0; c < m[f].length; c++){
            System.out.print(" "+m[f][c]); 
         }
         System.out.println("");
      }
      double fNormal = 0;
      double cNormal = 0;
      
      for(int f = 0; f < m.length; f++){
         for(int c = 0; c < m[f].length; c++){
             fNormal = (int) ((double) f / m.length  * 100.0) ;
             cNormal = (int) ((double) c / m[f].length *100.0) ;
                
             if(fNormal==cNormal){
                m[f][c]= -3375053; 
             }
             
         } 
      }
      
      Imagen imagen2 = new Imagen( m );
      imagen2.dibujar();
 
   }
}
