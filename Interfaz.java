import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
    
public class Interfaz extends JFrame{
    //Botones que se usan en la interfaz grafica
    private JButton mostrarInventario;
    private JButton filtrarPorManchas;
    private JButton filtrarPorNumero;
    private JButton filtrarPorEscala;
    private JButton filtrarPorDimensiones;
    private JButton filtrarPorArea;
    private JButton salir;
    
    private ProcesadorDeImagenes procesadorDeImagenes;
    private Catalogo catalogo;
    
    public Interfaz() { //CONSTRUCTOR DE INTERFAZ DE MENU PRINCIPAL

        JFrame ventana = new JFrame("Procesador De Imagenes");
        pack();
        setVisible(false);
        setLayout(null);
        setSize(500,500);
        setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //BOTONES
    
        crearBotonMostrarInventario();
    
        crearBotonFiltrarPorNumero();
        
        crearBotonFiltrarPorManchas();
    
        crearBotonFiltrarPorEscala();
    
        crearBotonFiltrarPorDimensiones();
        
        crearBotonFiltrarPorArea();
   
        crearBotonSalir();

    }
    
    /* @Funcion: hace la ventana visible
     * @Param: -
     * @Return: -
     */
    public void mostrarVentana(){
        setVisible(true);
    }
    
    /* @Funcion: muestra una ventana al usuario y le pide que ingrese el nombre del a imagen.gif
    * @Param: -
    * @Return: String nombre, variable que almacena el nombre de la imagen.gif
    */
    public String pedirNombreImagen(){
        String nombre;
        nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la imagen (.gif) que desea utilizar:");
        if(nombre == null){
            System.exit(0);
        }
        return nombre;
    }
    
    /* @Funcion: elige mediante un switch el metodo a ejecutar.
    * @Param: int instruccion, valor que se selecciona al presionar uno de los botones en pantalla.
    * @Return: -
    */
    public void seleccionarAccion(int instruccion){
        int max;
        int min;
        int numFigura;
        
        switch(instruccion){
            case 1:
                catalogo.mostrarInventario();
            break;
            case 2:
                try{
                    numFigura = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero de figura que desea ver:"));
                    catalogo.filtrarSegunNumFigura(numFigura);
                }
                catch(Exception inputInvalido){
                    mensaje("Datos no ingresados");
                }
                break;
            case 3:
                try{
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Maximo de manchas:"));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Minimo de manchas:"));
                    catalogo.filtrarPorManchas(max,min);
                }
                catch(Exception inputInvalido){
                    mensaje("Datos no ingresados");
                }
            break;
            case 4:
                try{
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Maximo en escala:"));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Minimo en escala:"));
                    catalogo.filtrarSegunEscala(max, min);
                }
                catch(Exception inputInvalido){
                    mensaje("Datos no ingresados");
                }
            break;
            case 5:
                try{
                    int maxAncho = Integer.parseInt(JOptionPane.showInputDialog(null,"Ancho maximo:"));
                    int minAncho = Integer.parseInt(JOptionPane.showInputDialog(null,"Ancho minimo:"));
                    int maxAltura = Integer.parseInt(JOptionPane.showInputDialog(null,"Largo maximo:"));
                    int minAltura = Integer.parseInt(JOptionPane.showInputDialog(null,"Largo minimo:"));
                    catalogo.filtrarSegunDimensiones(maxAncho, minAncho, maxAltura, minAltura);
                }
                catch(Exception inputInvalido){
                    mensaje("Datos no ingresados");
                }
            break;
            case 6:
                try{
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Area maxima:"));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Area minima:"));
                    catalogo.filtrarSegunArea(max, min);
                }
                catch(Exception inputInvalido){
                    mensaje("Datos no ingresados");
                }
            break;
            case 7:
                System.exit(0);
            break;
        }
        
    }
 
   
   
    /* @Funcion: Crea un JOptionPane o imprime en la terminal el mensaje que sea ingresado, dependiendo de si se usa
     * interfaz o terminal
     * @Param: String mensaje que es el que sera mostrado en pantalla
     * @Return: void
     */
    public void mensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje,"Procesador De Imagenes",1);    
    }
    
    /* METODOS QUE CREAN BOTONES */

     /* @Funcion: Crea un boton para ver el inventario.
     * @Param: - 
     * @Return: -
     */
    public void crearBotonMostrarInventario(){   
        mostrarInventario = new JButton("Mostrar Inventario");
        mostrarInventario.setBounds(150,50,200,40);
        this.add(mostrarInventario);
        mostrarInventario.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                seleccionarAccion(1);
            }
        }); 
    }
    
    /* Funcion: crea un boton para ejecutar el metodo filtrarPorNumero en Catalogo.
    * Param: -
    * Return: -
    */
    public void crearBotonFiltrarPorNumero(){
        filtrarPorNumero = new JButton("Mostrar por numero");
        filtrarPorNumero.setBounds(150,100,200,40);
        this.add(filtrarPorNumero);
        filtrarPorNumero.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
               seleccionarAccion(2);
            }
        });
    }
    
     /* @Funcion: Crea un boton para ejecutar el metodo filtrarPorManchas en Catalogo. 
     * @Param: - 
     * @Return: -
     */
    public void crearBotonFiltrarPorManchas(){
        filtrarPorManchas = new JButton("Filtrar Por Manchas");
        filtrarPorManchas.setBounds(150,150,200,40);
        this.add(filtrarPorManchas);
        filtrarPorManchas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
               seleccionarAccion(3);
            }
        });
    }

     /* @Funcion: Crea un boton para filtrar las figuras que se mostraran segun escala. 
     * @Param: - 
     * @Return: -
     */
    public void crearBotonFiltrarPorEscala(){
        filtrarPorEscala = new JButton("Filtrar por Escala");
        filtrarPorEscala.setBounds(150,200,200,40);
        this.add(filtrarPorEscala);
        filtrarPorEscala.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent evt){
                seleccionarAccion(4);
            }
        }); 
    }
    
    /* @Funcion: Crea un boton para filtrar las figuras que se van a mostrar segÃºn dimensiones.
    * @Param: - 
    * @Return: -
    */
    public void crearBotonFiltrarPorDimensiones(){
        filtrarPorDimensiones = new JButton("Filtrar Por Dimensiones");
        filtrarPorDimensiones.setBounds(150,250,200,40);
        this.add(filtrarPorDimensiones);
        filtrarPorDimensiones.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                seleccionarAccion(5);
            }
        });
    }
    
    /* @Funcion: Crea un boton para filtrar las figuras que se van a mostrar segun area.
    * @Param: - 
    * @Return: -
    */
    public void crearBotonFiltrarPorArea(){
        filtrarPorArea = new JButton("Filtrar Por Area");
        filtrarPorArea.setBounds(150,300,200,40);
        this.add(filtrarPorArea);
        filtrarPorArea.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                seleccionarAccion(6);
            }
        });
    }
     
    /* @Funcion: Crea un boton para salir del programa.
    * @Param: - 
    * @Return: -
    */ 
    public void crearBotonSalir(){
        salir = new JButton("Salir");
        salir.setBounds(150,350,200,50);
        this.add(salir);
        salir.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent evt){
               seleccionarAccion(7);
            }
        });
    }
    
    /* @Funcion: se asegura de que el valor ingresado por el usuario sea valido para usarlo en los metodos.
    * @Param: String datoIngresado, valor que cambia dependiendo del metodo que se utilice.
    * @Return: boolean esEntero, devuelve si el valor es valido o no.
    */
    public boolean esEntero(String datoIngresado){
        int numeroPrueba = 0;
        boolean esEntero;
        try{
            numeroPrueba += Integer.parseInt(datoIngresado);
            esEntero = true;
        }
        catch(Exception e){
            esEntero = false;
        }
        return esEntero;
    }

    public void setProcesadorDeImagenes(ProcesadorDeImagenes procesadorDeImagenes){
        this.procesadorDeImagenes = procesadorDeImagenes;
        this.catalogo = procesadorDeImagenes.getCatalogo();
    }
}
