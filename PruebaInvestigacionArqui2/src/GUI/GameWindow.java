/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Game;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
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
    
    public int cdKnight = 1;
    public JLabel jlbCdKnight;
    
    public int cdCrossbow = 1;
    public JLabel jlbCdCrossbow;
    
    public int cdHorse = 1;
    public JLabel jlbCdHorse;
    
    public  ImageIcon imgSelection;
    public JLabel selection;
    public JLabel knight;
    public JLabel horse;
    public JLabel crossBow;

    
    
    public ImageIcon imgKnight;
    public ImageIcon imgHorse;
    public ImageIcon imgCrossBow;
    public ImageIcon imgHub;
    
    public JFrame container;
    public int skin;
    

    public GameWindow(JFrame container, int skin) { // INICIALIZA LA VENTANA
        this.skin = skin;
        this.container = container;
        setSize(815, 745);
        setLayout(null);
        
        this.addKeyListener(this);
        this.setFocusable(true);
        this.skin = skin;
        arduino = new PanamaHitek_Arduino();
        //CONFIGURAR ARDUINO
//        try {
//            setupArduino();
//        } catch (ArduinoException | InterruptedException | SerialPortException ex) {
//            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
        

        game = new Game(2, 2, this.skin, this.arduino);
        init();
        initTimers();



    }
    
    private void init() {// INICIALIZA LOS COMPONENTES GRAFICOS
        
        
        if (skin == 1) {
            imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
            imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
            imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
            imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
            imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Hud1.png"));
        }
        if (skin == 2) {
            imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD2/Selection.png"));
            imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightpeque.png"));
            imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horsepeque.png"));
            imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowpeque.png"));
            imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD2/Hud1.png"));
        }
        if (skin == 3) {
            imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD3/Selection.png"));
            imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightpeque.png"));
            imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horsepeque.png"));
            imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowpeque.png"));
            imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD3/Hud1.png"));
        }

        selection = new JLabel(imgSelection);
        selection.setBounds(425, 590, 100, 100);
        this.add(selection);

        knight = new JLabel(imgKnight);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);

        horse = new JLabel(imgHorse);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);

        crossBow = new JLabel(imgCrossBow);
        crossBow.setBounds(610, 600, 100, 100);
        this.add(crossBow);


        
        
        //JLABELS TIMERS
        this.jlbCdKnight = new JLabel();
        this.jlbCdKnight.setBounds(110, 600, 100, 100);
        jlbCdKnight.setFont(jlbCdKnight.getFont().deriveFont(Font.BOLD, 50f));
        jlbCdKnight.setForeground(Color.WHITE);
        this.add(this.jlbCdKnight);
        this.jlbCdKnight.setVisible(true);

        this.jlbCdHorse = new JLabel();
        this.jlbCdHorse.setBounds(380, 600, 100, 100);
        jlbCdHorse.setFont(jlbCdKnight.getFont().deriveFont(Font.BOLD, 50f));
        jlbCdHorse.setForeground(Color.WHITE);
        this.add(this.jlbCdHorse);
        this.jlbCdHorse.setVisible(true);

        this.jlbCdCrossbow = new JLabel();
        this.jlbCdCrossbow.setBounds(650, 600, 100, 100);
        jlbCdCrossbow.setFont(jlbCdKnight.getFont().deriveFont(Font.BOLD, 50f));
        jlbCdCrossbow.setForeground(Color.WHITE);
        this.add(this.jlbCdCrossbow);
        this.jlbCdCrossbow.setVisible(true);

        JLabel hub = new JLabel(imgHub);
        hub.setBounds(0, 590, 800, 120);
        this.add(hub);


    }
    
    private void initTimers() {

        //TEMPORIZADOR UNICO Y GENERAL DEL JUEGO
        Timer timer1 = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if (!game.finish()) {
                    if (!game.getPause()) {
                        game.refresh2();
                        repaint();
                    }
                } else { //juego terminado
                    timer1.cancel();
                    closeGame(game.getGanador());
                }
            }
        };
        timer1.schedule(tarea, 0, 20);

        //LOGICA IA
        Random random = new Random();
        Timer timer2 = new Timer();
        TimerTask tarea2 = new TimerTask() {
            @Override
            public void run() {
                if (!game.getPause()) {
                    int randomLine = random.nextInt(5 - 3) + 3;
                    game.addUnit(randomLine);
                }

            }
        };
        timer2.schedule(tarea2, 3500, 3500);

        //TIMER PARA COOLDOWN
        Timer cdUnits = new Timer();
        TimerTask tareaCdUnits = new TimerTask() {
            @Override
            public void run() {
                if (!game.getPause()) {
                    if (cdKnight > 0) {
                        cdKnight -= 1;
                        jlbCdKnight.setText("" + cdKnight);
                    } else {
                        jlbCdKnight.setVisible(false);
                        knight.setVisible(true);
                    }
                    if (cdCrossbow > 0) {
                        cdCrossbow -= 1;
                        jlbCdCrossbow.setText("" + cdCrossbow);
                    } else {
                        jlbCdCrossbow.setVisible(false);
                        crossBow.setVisible(true);
                    }
                    if (cdHorse > 0) {
                        cdHorse -= 1;
                        jlbCdHorse.setText("" + cdHorse);
                    } else {
                        jlbCdHorse.setVisible(false);
                        horse.setVisible(true);
                    }
                }


            }
        };
        cdUnits.schedule(tareaCdUnits, 10, 1000);

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


        switch (messageArduino) {
            case "Boton DERECHA presionado":
                handleButtonRight();
                break;
            case "Boton ARRIBA presionado":
                handleButtonUp();
            break;
            case "Boton ABAJO presionado":
                handleButtonDown();
            break;
            case "Boton IZQUIERDA presionado":
                handleButtonLeft();
                break;
            case "Boton PAUSA presionado":
                togglePause();
                break;
        }

    }

    private void handleButtonUp() {// METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ARRIBA

        if (game.getSelection() == 170 && this.cdKnight == 0) {
            game.addUnit(1);
            this.cdKnight = 10;
            this.knight.setVisible(false);
            this.jlbCdKnight.setVisible(true);
        }
        if (game.getSelection() == 425 && this.cdHorse == 0) {
            game.addUnit(1);
            this.cdHorse = 10;
            this.horse.setVisible(false);
            this.jlbCdHorse.setVisible(true);
        }
        if (game.getSelection() == 700 && this.cdCrossbow == 0) {
            game.addUnit(1);
            this.cdCrossbow = 10;
            this.crossBow.setVisible(false);
            this.jlbCdCrossbow.setVisible(true);
        }

    }

    private void handleButtonDown() {

        if (game.getSelection() == 170 && this.cdKnight == 0) {
            game.addUnit(2);
            this.cdKnight = 10;
            this.knight.setVisible(false);
            this.jlbCdKnight.setVisible(true);
        }
        if (game.getSelection() == 425 && this.cdHorse == 0) {
            game.addUnit(2);
            this.cdHorse = 10;
            this.horse.setVisible(false);
            this.jlbCdHorse.setVisible(true);
        }
        if (game.getSelection() == 700 && this.cdCrossbow == 0) {
            game.addUnit(2);
            this.cdCrossbow = 10;
            this.crossBow.setVisible(false);
            this.jlbCdCrossbow.setVisible(true);
        }

        
    }
    
    private void handleButtonRight() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA LA DERECHA
        int position= game.Selection(selection.getX(), "DERECHA");
        selection.setBounds(position , 590, 100, 100);
    }

    private void handleButtonLeft() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ABAJO
        int position= game.Selection(selection.getX(), "IZQUIERDA");
        selection.setBounds(position , 590, 100, 100);
    }

    public void togglePause() {// METODO PARA MANEJAR LAS PAUSAS, SI SE PRESIONA 1 VEZ SE PAUSA, SI SE VUELVE A PRESIONAR EL JUEGO SE REANUDA
        game.pause();
    }
    
    public void closeGame(int ganador) {
        FinalMessage mw = new FinalMessage(ganador);
        mw.setVisible(true);
        this.container.dispose();
        
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
            this.togglePause();
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
    
