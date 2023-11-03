/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Game;
import Domain.Unit;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 *
 * @author diego
 */
public class GameWindow extends javax.swing.JPanel implements KeyListener{
    
    // ------------------------------ VARIABLES GLOBALES NECESARIAS ------------------------------
    public PanamaHitek_Arduino arduino;
    String messageArduino="";
    Game game;
    
    public ArrayList<Unit> unitList = new ArrayList<>();
    public ArrayList<Unit> enemyUnitList = new ArrayList<>();

    public  ImageIcon imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
    public  JLabel selection = new JLabel(imgSelection);
    
    
    ////////////////////////////////////////////////////// AQUI INICIA EL CODIGO LIMPIO /////////////////////////////////////////////////////////////
    public GameWindow() { // INICIALIZA LA VENTANA
        this.addKeyListener(this);
        this.setFocusable(true);

        game = new Game(2, 2);

        arduino = new PanamaHitek_Arduino();

        //TEMPORIZADOR UNICO Y GENERAL DEL JUEGO
        Timer timer1 = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game.finish()) {
                    if (!game.getPause()) {
                        game.refresh2();
                        repaint();

                    }
                }else { //juego terminado
                    System.out.println("Juego terminado");
                    
                }


            }
        });
        timer1.start();
        
        
        //LOGICA IA
        Random random = new Random();
        Timer timer2 = new Timer(3500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int randomLine = random.nextInt(5 - 3) + 3;
                game.addUnit(randomLine);
                repaint();
            }
        });
        timer2.start();
        

        initializeGameWindow();
        //initComponents();
    }
    
    private void init() {// INICIALIZA LOS COMPONENTES GRAFICOS
        setupUIElements();
    }
    
//    private void createAndAddUnits() {
// 
//    }

    private void setupUIElements() { // EN ESTE METODO SE CREAN LOS ELEMENTOS GRAFICOS PARA MOSTRARLOS EN LA GUI
        setLayout(null);

        selection.setBounds(425 , 590, 100, 100);              
        
        JLabel fake = new JLabel();
        fake.setBounds(170 , 590, 100, 100);
        game.Selection(fake.getX(), messageArduino);
        this.add(selection);

        ImageIcon imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
        JLabel knight = new JLabel(imgKnight);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);
        
        ImageIcon imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
        JLabel horse = new JLabel(imgHorse);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);
        
        ImageIcon imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
        JLabel crossBow = new JLabel(imgCrossBow);
        crossBow.setBounds(610, 600, 100, 100);
        this.add(crossBow);
        
        // --------------------------------------------------- MAPA Y HUD ----------------------------
        ImageIcon imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Hud1.png"));
        JLabel hub = new JLabel(imgHub);
        hub.setBounds(0,590, 800, 120);
        this.add(hub);
        
        
//        ImageIcon imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Map1.png"));
//        JLabel map = new JLabel(imgMap);
//        map.setBounds(0, 0, 800, 600);
//        this.add(map); 
        // -----------------------------------------------------------------------------------------------------------
    }
    
    private void initializeGameWindow() { // SE CONFIGURA LA VENTANA Y SE CREAN LOS OBJETOS NECESARIOS PARA EL FUNCIONAMIENTO DEL ARDUINO
        setSize(815, 745);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        
        
//        try {
//            setupArduino();
//        } catch (ArduinoException | InterruptedException | SerialPortException ex) {
//            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }          
        init();
    }

    private void setupArduino() throws ArduinoException, InterruptedException, SerialPortException { // CONFIGURACION DEL ARDUINO
        arduino.arduinoRXTX("COM3", 9600, createSerialPortListener());
        Thread.sleep(2000);// ESPERA A QUE EL PUERTO ESTE LISTO PARA ENVIAR Y RECIBIR DATOS
        arduino.sendData("1");
        arduino.sendData("2");
        arduino.sendData("3");
        arduino.sendData("4");
    }
    
    private void handleArduinoEvent(String messageArduino) {// EN ESTE METODO SE EJECUTAN LAS ACCIONES SEGUN EL BOTON DE LA PROTOBOARD QUE SE PRESIONE
        // CABE RESALTAR QUE CADA ACCION ESTA DIVIDIDA EN SUS RESPECTIVOS METODOS PARA SU CLARIDAD

        
        if(!game.getPause()){
            
        }
        switch (messageArduino) {
            case "Boton DERECHA presionado":
                handleButtonRight();
                break;
//            case "Boton ARRIBA presionado":
//                handleButtonUp();
//            break;
//            case "Boton ABAJO presionado":
//                handleButtonDown();
//            break;
            case "Boton IZQUIERDA presionado":
                handleButtonLeft();
                break;
            case "Boton PAUSA presionado":
                handlePauseButton();
                break;
        }

    }

    private void handleButtonUp() {// METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ARRIBA
        
        game.addUnit(1);
        repaint();
        
    }

    private void handleButtonDown() {
        
        game.addUnit(2);
        repaint();
        
    }
    
    private void handleButtonRight() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA LA DERECHA
        int position= game.Selection(selection.getX(), "DERECHA");
        selection.setBounds(position , 590, 100, 100);
    }

    private void handleButtonLeft() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ABAJO
        int position= game.Selection(selection.getX(), "IZQUIERDA");
        selection.setBounds(position , 590, 100, 100);
    }

    private void handlePauseButton() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON DE PAUSA
        togglePause();
    }
    
    public void togglePause() {// METODO PARA MANEJAR LAS PAUSAS, SI SE PRESIONA 1 VEZ SE PAUSA, SI SE VUELVE A PRESIONAR EL JUEGO SE REANUDA
        game.pause();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.chargeMap(g);
        game.refresh(g);

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyChar()));
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            this.handleButtonUp();
            
//            cdKnight = 10;
//            java.util.Timer timer = new java.util.Timer();
//
//            TimerTask tt = new TimerTask() {
//                @Override
//                public void run() {
//                    if (cdKnight == -1) {
//                        timer.cancel();
//                    } else {
//                        timerKnight.setText(Integer.toString(cdKnight));
//                        cdKnight--;
//                    }
//
//                }
//            };
//            timer.scheduleAtFixedRate(tt, 0, 1000);

        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            this.handleButtonDown();
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
            this.handleButtonLeft();
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            this.handleButtonRight();
        }
        

        
        if(KeyEvent.getKeyText(e.getKeyCode()).equals("Q")) {
            this.handlePauseButton();
        }
        
        


        
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("Tecla presionada: " + KeyEvent.getKeyText(e.getKeyCode()));
    }
    
    
    
    private SerialPortEventListener createSerialPortListener() {// ESTE ES UN METODO QUE UTILIZAMOS PARA QUE EL PROGRAMA ESCUCHE SI SE PRESIONA UN BOTON DESDE LA PROTOBOARD
        //                                     Y DE ESTA MANERA EJECUTA EL CODIGO DEL ARDUINO PARA QUE LUEGO EL PROGRAMA SEPA QUE ACCION DEBE REALIZAR O SI ESTE DEBE ENCENDER O APAGAR EL LED
        return new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                try {
                    if (arduino.isMessageAvailable()) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    messageArduino = arduino.printMessage();
                                    handleArduinoEvent(messageArduino);
                                } catch (SerialPortException | ArduinoException ex) {
                                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        });
                    }
                } catch (SerialPortException | ArduinoException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

}   
    
