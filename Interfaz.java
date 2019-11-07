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
    
    public void mostrarVentana(){
        setVisible(true);
    }
    
    public String pedirNombreImagen(){
        String nombre;
        nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la imagen (.gif) que desea utilizar.");
        return nombre;
    }
    
    public void seleccionarAccion(int instruccion){
        int max;
        int min;
        int numFigura;
        
        try{
            switch(instruccion){
                case 1:
                    catalogo.mostrarInventario();
                break;
                case 2:
                    numFigura = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero de figura que desea ver."));
                    catalogo.filtrarSegunNumFigura(numFigura);
                break;
                case 3:
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el maximo de manchas que desea observar."));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el minimo de manchas que desea observar."));
                    catalogo.filtrarPorManchas(max,min);
                break;
                case 4:
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del maximo en escala que quiere ver."));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el nuamero del minimo en escala que quiere ver."));
                    catalogo.filtrarSegunEscala(max, min);
                break;
                case 5:
                    int maxAncho = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del maximo en ancho que quiere ver."));
                    int minAncho = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del minimo en ancho que quiere ver."));
                    int maxAltura = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del maximo en altura que quiere ver."));
                    int minAltura = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del minimo en altura que quiere ver."));
                    catalogo.filtrarSegunDimensiones(maxAncho, minAncho, maxAltura, minAltura);
                break;
                case 6:
                    max = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del maximo en area que quiere ver."));
                    min = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el numero del minimo en area que quiere ver."));
                    catalogo.filtrarSegunArea(max, min);
                break;
                case 7:
                    System.exit(0);
                break;
            }
        }
        catch(Exception e){
        
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

     /* @Funcion: Crea un boton para ver el puntaje mas alto guardado en la interfaz
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
    
    public void crearBotonFiltrarPorNumero(){
        filtrarPorNumero = new JButton("Filtrar Por Numero");
        filtrarPorNumero.setBounds(150,100,200,40);
        this.add(filtrarPorNumero);
        filtrarPorNumero.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
               seleccionarAccion(2);
            }
        });
    }
    
     /* @Funcion: Crea un boton para cambiar la cantidad de flechas que seran utilizadas por set en la partida
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
    
    
    
     /* @Funcion: Crea un boton para cambiar la cantidad de sets que se jugaran en la partida en la interfaz
     * @Param: - 
     * @Return: -
     */
    
    
     /* @Funcion: Crea un boton para empezar el juego en la interfaz
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
     
    public void crearBotonSalir(){
        salir = new JButton("Salir");
        salir.setBounds(150,350,200,50);
        this.add(salir);
        salir.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent evt){
               seleccionarAccion(7);
            }
        });
    }
    
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

     /* @Funcion: Carga una imagen en la ventana elegeida de la interfaz
     * @Param: String path es la localizacion de la imagen
     * sizeX es el tama�o X de la imagen
     * sizeY es el tama�o Y de la imagen
     * @Return: -
     */
    public void cargarImagen(String path, int sizeX, int sizeY){
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        Image newImg = img.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT);
        this.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(newImg, 1, 1, null);
            }
        });
        }
    
    public void setProcesadorDeImagenes(ProcesadorDeImagenes procesadorDeImagenes){
        this.procesadorDeImagenes = procesadorDeImagenes;
        this.catalogo = procesadorDeImagenes.getCatalogo();
    }
}
