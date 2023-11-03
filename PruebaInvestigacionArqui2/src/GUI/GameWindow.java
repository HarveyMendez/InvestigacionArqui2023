/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Game;
import Domain.Unit;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 *
 * @author diego
 */
public class GameWindow extends javax.swing.JFrame{
    
    // ------------------------------ VARIABLES GLOBALES NECESARIAS ------------------------------
    public PanamaHitek_Arduino arduino;
    String messageArduino="";
    Game game;
    SkinSelection skin;
    private boolean paused = false;
    private Timer collisionTimer; 
    
    public ArrayList<Unit> unitList = new ArrayList<>();
    public ArrayList<Unit> enemyUnitList = new ArrayList<>();

    String SelectedUnit;
    private final Object pauseLock = new Object();  
    
    ImageIcon imgKnightHUD;
    ImageIcon imgKnight;
    ImageIcon imgEnemyKnight;
    ImageIcon imgHorseHUD;
    ImageIcon imgHorse;
    ImageIcon imgEnemyHorse;
    ImageIcon imgCrossbowHUD;
    ImageIcon imgCrossbow;
    ImageIcon imgEnemyCrossbow;
    
    ImageIcon imgHUD;
    ImageIcon imgMap;
   
    ImageIcon imgEnemy = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGameEnemy.png"));
    public JLabel enemyLabel = new JLabel(imgEnemy);
    public Unit enemyUnit = new Unit();
    
    ImageIcon imgEnemy2 = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGameEnemy.png"));
    public JLabel enemyLabel2 = new JLabel(imgEnemy2);
    public Unit enemyUnit2 = new Unit();
   
    public  ImageIcon imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
    public  JLabel selection = new JLabel(imgSelection);
    
    
    ////////////////////////////////////////////////////// AQUI INICIA EL CODIGO LIMPIO /////////////////////////////////////////////////////////////
    public GameWindow() { // INICIALIZA LA VENTANA
    initializeGameWindow();
    //initComponents();
    }
    
    private void init() {// INICIALIZA LOS COMPONENTES GRAFICOS
//        createAndAddUnits();
        getSelectedSkin();
        setupUIElements();
    }
    
//    private void createAndAddUnits() {
// 
//    }
    
    
    private void getSelectedSkin(){
        
        int skinNumber = skin.getSkin();
        System.out.println("skin: "+skinNumber);
        switch (skinNumber) {
            case 1: // SKIN MEDIEVAL
                
                //KNIGHT
                imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
                imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGame.png"));
                imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGameEnemy.png"));
                
                //HORSE
                imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
                imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGame.png"));
                imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGameEnemy.png"));
                
                //CROSSBOW
                imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
                imgCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGame.png"));
                imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGameEnemy.png"));
                
                //HUD
                imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Map1.png"));
                imgHUD = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Hud1.png"));
                
                break;
                
            case 2: // SKIN MAGIC
                
                //KNIGHT
                imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightpeque.png"));
                imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGame.png"));
                imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGameEnemy.png"));
                
                //HORSE
                imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horsepeque.png"));
                imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGame.png"));
                imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGameEnemy.png"));
                
                //CROSSBOW
                imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowpeque.png"));
                imgCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGame.png"));
                imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGameEnemy.png"));
                
                //HUD
                imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD2/Map1.png"));
                imgHUD = new ImageIcon(getClass().getResource("/img/HUD/HUD2/Hud1.png"));
                
                break;
                
            case 3:// SKIN LEAGUE
                
                //KNIGHT
                imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightpeque.png"));
                imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGame.png"));
                imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGameEnemy.png"));
                
                //HORSE
                imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horsepeque.png"));
                imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGame.png"));
                imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGameEnemy.png"));
                
                //CROSSBOW
                imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowpeque.png"));
                imgCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGame.png"));
                imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGameEnemy.png"));
                
                //HUD
                imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD3/Map1.png"));
                imgHUD = new ImageIcon(getClass().getResource("/img/HUD/HUD3/Hud1.png"));
                
                break;
            
        }
    }

    private void setupUIElements() { // EN ESTE METODO SE CREAN LOS ELEMENTOS GRAFICOS PARA MOSTRARLOS EN LA GUI
        setLayout(null);
        // -------------------------------------- PRUEBAS CON ENEMIGOS ---------------------------------------------
        int x = 600; 
        int y = 540; 
        enemyLabel.setBounds(x, y,imgEnemy.getIconWidth(),imgEnemy.getIconHeight());
        Border border = new LineBorder(Color.BLACK, 2);
        enemyLabel.setBorder(border);
        enemyUnit = new Unit(enemyLabel, SelectedUnit, 1);

        // Agregar JLabel al ArrayList
        enemyUnitList.add(enemyUnit);
        
        this.add(enemyUnit.getLabel());
        selection.setBounds(425 , 590, 100, 100);              
        // -----------------------------------------------------------------------------------------------------------
        
        
        // -------------------------------------- PRUEBAS CON ENEMIGOS ---------------------------------------------
        int x2 = 20; 
        int y2 = 200; 
        enemyLabel2.setBounds(x2, y2,imgEnemy2.getIconWidth(),imgEnemy2.getIconHeight());
        enemyLabel2.setBorder(border);
        enemyUnit2 = new Unit(enemyLabel2, SelectedUnit, 1);

        // Agregar JLabel al ArrayList
        enemyUnitList.add(enemyUnit2);
        
        this.add(enemyUnit2.getLabel());
        selection.setBounds(425 , 590, 100, 100);              
        // -----------------------------------------------------------------------------------------------------------
        
        
        JLabel fake = new JLabel();
        fake.setBounds(170 , 590, 100, 100);
        game.Selection(fake, messageArduino);
        this.add(selection);
        
        // --------------------------------------- AGREGAMOS ELEMENTOS A LA GUI ------------------------------------------------
        //ImageIcon imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
        JLabel knight = new JLabel(imgKnightHUD);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);
        
        //ImageIcon imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
        JLabel horse = new JLabel(imgHorseHUD);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);
        
        //ImageIcon imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
        JLabel crossBow = new JLabel(imgCrossbowHUD);
        crossBow.setBounds(610, 600, 100, 100);
        this.add(crossBow);
        
        // --------------------------------------------------- MAPA Y HUD ----------------------------
//        ImageIcon imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Hud1.png"));
        JLabel hud = new JLabel(imgHUD);
        hud.setBounds(0,590, 800, 120);
        this.add(hud);
        
        
//        ImageIcon imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Map1.png"));
        JLabel map = new JLabel(imgMap);
        map.setBounds(0, 0, 800, 600);
        this.add(map); 
        // -----------------------------------------------------------------------------------------------------------
    }
    
    private void initializeGameWindow() { // SE CONFIGURA LA VENTANA Y SE CREAN LOS OBJETOS NECESARIOS PARA EL FUNCIONAMIENTO DEL ARDUINO
        setSize(815, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new  PanamaHitek_Arduino();
        game = new Game(2, 2, arduino);
        
        try {
            setupArduino();
        } catch (ArduinoException | InterruptedException | SerialPortException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }     
        skin = new SkinSelection(1);
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
                handlePauseButton();
            break;
        }

    }

//    private void moveCursor(String direction) {
//        int position = game.Selection(selection, direction);
//        selection.setBounds(position, 590, 100, 100);
//    }

    private void handleButtonUp() {// METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ARRIBA
        System.out.println("PRESIONE ARRIBA");
        
        ImageIcon imagen = new ImageIcon();
        SelectedUnit = game.UnitSelected(game.getSelection());
        imagen = game.getImageIcon(SelectedUnit);
                                
        JLabel label = new JLabel(imagen);
        Unit unit = new Unit(label, SelectedUnit, 1);
         unitList.add(unit);
        
        
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
       
        int x = 10; 
        int y = 440; 
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                        
        
                                
        game.movementToplane(label, 745, 10);
        unit.setCollisionTimer(collisionTimer);
    
        handleCollision(unit, layeredPane, label, enemyLabel2);
        setVisible(true);
    }

    private void handleButtonDown() {
    ImageIcon imagen = new ImageIcon();
    SelectedUnit = game.UnitSelected(game.getSelection());
    imagen = game.getImageIcon(SelectedUnit);
                                
    JLabel label = new JLabel(imagen);
    Unit unit = new Unit(label, SelectedUnit, 1);
    unitList.add(unit);

    JLayeredPane layeredPane = getLayeredPane();
    layeredPane.add(unit.getLabel(), JLayeredPane.DEFAULT_LAYER);
                                         
    int x = 110;
    int y = 540; 
    label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
    
    game.movementBotlane(label, 745, 10);
    
    unit.setCollisionTimer(collisionTimer);
    
    handleCollision(unit, layeredPane, label, enemyLabel);
    
    
    
    setVisible(true);
}
    
    private void handleButtonRight() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA LA DERECHA
        System.out.println("MUEVO CURSOR A LA DERECHA");
        int position= game.Selection(selection, "DERECHA");
        selection.setBounds(position , 590, 100, 100);
    }

    private void handleButtonLeft() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ABAJO
        System.out.println("MUEVO EL CURSOR A LA IZQUIERDA");
        int position= game.Selection(selection, "IZQUIERDA");
        selection.setBounds(position , 590, 100, 100);
    }

    private void handlePauseButton() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON DE PAUSA
        System.out.println("PAUSAAAAAAAAAAA");
        togglePause();
    }
    
    public void togglePause() {// METODO PARA MANEJAR LAS PAUSAS, SI SE PRESIONA 1 VEZ SE PAUSA, SI SE VUELVE A PRESIONAR EL JUEGO SE REANUDA
        paused = !paused;
        if (paused) {
            System.out.println("Juego pausado");
            try {
                synchronized (pauseLock) {
                    pauseLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Juego reanudado");
        } else {
            System.out.println("Juego reanudado");
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
        }
    }

    private void handleCollision(Unit unit,JLayeredPane layeredPane,JLabel label, JLabel enemyLabel) { // METODO QUE FUNCIONA PARA DETECTAR COLISIONES ENTRE 2 LABELS
        //                                                                                     SI EXISTIERAN COLISIONES LLAMA A OTRO METODO PARA VERIFICAR QUE LABEL SE ELIMINA
        Timer collisionTimer = unit.getCollisionTimer();
        collisionTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean colision = game.checkCollision(label, enemyLabel);
            if (colision) {
                String unitName = game.encounterWinner();
                System.out.println("Hay colisión entre las JLabel.");
                int winner = game.win;
                System.out.println("GANADOR DEL ENCUENTRO: " + unitName + " Numero: " + winner);
                switch (winner) {
                    case 0:
                        label.setVisible(false);
                        enemyLabel.setVisible(false);
                        layeredPane.remove(label);
                        layeredPane.remove(enemyLabel);
                        layeredPane.remove(unit.getLabel());
                        layeredPane.remove(enemyUnit.getLabel());
                        layeredPane.remove(enemyUnit2.getLabel());
                        unitList.remove(unit);
                        enemyUnitList.remove(enemyUnit);
                        enemyUnitList.remove(enemyUnit2);
                        break;
                    case 2:
                        enemyLabel.setVisible(false);
                        layeredPane.remove(enemyLabel);
                        layeredPane.remove(enemyUnit.getLabel());
                        layeredPane.remove(enemyUnit2.getLabel());
                        enemyUnitList.remove(enemyUnit);
                        enemyUnitList.remove(enemyUnit2);
                        break;
                    case 1:
                        label.setVisible(false);
                        layeredPane.remove(label);
                        layeredPane.remove(unit.getLabel());
                        unitList.remove(unit);
                        break;
                }
                revalidate();
                repaint();
                ((Timer) e.getSource()).stop();
            }
        }
        
    });
        collisionTimer.start();
    }
    

        public SerialPortEventListener createSerialPortListener() {// ESTE ES UN METODO QUE UTILIZAMOS PARA QUE EL PROGRAMA ESCUCHE SI SE PRESIONA UN BOTON DESDE LA PROTOBOARD
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
    
