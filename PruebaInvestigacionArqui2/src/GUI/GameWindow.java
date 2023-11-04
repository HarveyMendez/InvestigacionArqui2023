/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Enemy;
import Domain.Game;
import Domain.Unit;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
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
    public int damage=0;
    public int damageEnemy=0;
    
//    public ArrayList<Unit> unitList = new ArrayList<>();
//    public ArrayList<Unit> enemyUnitList = new ArrayList<>();

    String SelectedUnit;
    String UnitName;
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
   
//    ImageIcon imgEnemy = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGameEnemy.png"));
//    public JLabel enemyLabel = new JLabel(imgEnemy);
//    public Unit enemyUnit = new Unit();
//    
//    ImageIcon imgEnemy2 = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGameEnemy.png"));
//    public JLabel enemyLabel2 = new JLabel(imgEnemy2);
//    public Unit enemyUnit2 = new Unit();
   
    public  ImageIcon imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
    public  JLabel selection = new JLabel(imgSelection);
    private ArrayList<Unit> units;
    private ArrayList<Enemy> enemies;
    
    ////////////////////////////////////////////////////// AQUI INICIA EL CODIGO LIMPIO /////////////////////////////////////////////////////////////
    public GameWindow() { // INICIALIZA LA VENTANA
        units = new ArrayList<>();
        enemies = new ArrayList<>();
           cooldownFlags.put("knight", false);
    cooldownFlags.put("horse", false);
    cooldownFlags.put("crossbow", false);
        createAndAddUnits();
        initializeGameWindow();
    //initComponents();
    }
    
    private void init() {// INICIALIZA LOS COMPONENTES GRAFICOS
//        createAndAddUnits();
        getSelectedSkin();
        setupUIElements();
    }
    
    
    public Enemy enemyUnit = new Enemy();
    public Unit playerUnit = new Unit();
    private void createAndAddUnits() {
        Timer timer= new Timer(4000, (ae) -> {
        ImageIcon imagen = new ImageIcon();
        SelectedUnit = game.UnitSelected(game.getSelection());

        Random randomImage = new Random();
        int randomUnit = randomImage.nextInt(3);
            System.out.println(randomUnit);
            switch (randomUnit) {
                case 0:
                    imagen = imgEnemyKnight;
                    UnitName="knight";
                    break;
                case 1:
                    imagen = imgEnemyHorse;
                    UnitName="horse";
                    break;
                default:
                    imagen = imgEnemyCrossbow;
                    UnitName="crossbowMan";
                    break;
            }
//        imagen = imgEnemyKnight;
//         UnitName= "KNIGHT";                      
        JLabel label = new JLabel(imagen);
        enemyUnit = new Enemy(label, UnitName, 1);
        enemies.add(enemyUnit);
//         unitList.add(unit);
        
        
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
       
        int x = 745; 
        int y = 10; 
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                        
        
            Random rand = new Random();
            int random = rand.nextInt(2);
            
            if(random==0){
                game.movementToplane(label, 10, 540,1);
            }else
                game.movementBotlane(label, 10, 540,1);
            
        
        enemyUnit.setCollisionTimer(collisionTimer);
    
        handleCollision(layeredPane);
        setVisible(true);
        
        });
        damageEnemy=1;
        timer.start();
    }
    
    
    private void getSelectedSkin(){
        
        int skinNumber = skin.getSkin();
//        System.out.println("skin: "+skinNumber);
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
int knightTimer = 10;
JLabel knightCounter = new JLabel();
int crossbowTimer = 10;
JLabel crossbowCounter = new JLabel();
int horseTimer = 10;
JLabel horseCounter = new JLabel();
    private void setupUIElements() { // EN ESTE METODO SE CREAN LOS ELEMENTOS GRAFICOS PARA MOSTRARLOS EN LA GUI
        setLayout(null);
        
        Color backgroundColor = new Color(27, 25, 24, 200);
         Font customFont = new Font("Arial", Font.PLAIN, 80);
//         Border border = new LineBorder(Color.BLACK, 2);
//        int knightTimer = 10;
        
        knightCounter.setBounds(90, 600, 100, 100);
        
//        knightCounter.setBorder(border);
        knightCounter.setFont(customFont);
        knightCounter.setBackground(backgroundColor);
        knightCounter.setForeground(Color.WHITE);
       knightCounter.setOpaque(true);
//        knightCounter.setText(Integer.toString(knightTimer));
        knightCounter.setVisible(false);
        this.add(knightCounter);
        
        

        
//        int horseTimer = 10;
//        JLabel horseCounter = new JLabel();
        horseCounter.setBounds(350, 600, 100, 100);
//        horseCounter.setBorder(border);
        horseCounter.setFont(customFont);
        horseCounter.setBackground(backgroundColor);
        horseCounter.setForeground(Color.WHITE);
       horseCounter.setOpaque(true);
        horseCounter.setText(Integer.toString(horseTimer));
        horseCounter.setVisible(false);
        this.add(horseCounter);
        
        
        
//        int crossbowTimer = 10;
//        JLabel crossbowCounter = new JLabel();
        crossbowCounter.setBounds(610, 600, 100, 100);
//        crossbowCounter.setBorder(border);
        crossbowCounter.setFont(customFont);
        crossbowCounter.setBackground(backgroundColor);
        crossbowCounter.setForeground(Color.WHITE);
       crossbowCounter.setOpaque(true);
        crossbowCounter.setText(Integer.toString(crossbowTimer));
        crossbowCounter.setVisible(false);
        this.add(crossbowCounter);
        
        
        
//        // -------------------------------------- PRUEBAS CON ENEMIGOS ---------------------------------------------
//        int x = 600; 
//        int y = 540; 
//        enemyLabel.setBounds(x, y,imgEnemy.getIconWidth(),imgEnemy.getIconHeight());
//        Border border = new LineBorder(Color.BLACK, 2);
//        enemyLabel.setBorder(border);
//        enemyUnit = new Unit(enemyLabel, SelectedUnit, 1);
//
//        // Agregar JLabel al ArrayList
////        enemyUnitList.add(enemyUnit);
//        
//        this.add(enemyUnit.getLabel());
//        selection.setBounds(425 , 590, 100, 100);              
//        // -----------------------------------------------------------------------------------------------------------
        
        
//        // -------------------------------------- PRUEBAS CON ENEMIGOS ---------------------------------------------
//        int x2 = 20; 
//        int y2 = 200; 
//        enemyLabel2.setBounds(x2, y2,imgEnemy2.getIconWidth(),imgEnemy2.getIconHeight());
//        enemyLabel2.setBorder(border);
//        enemyUnit2 = new Unit(enemyLabel2, SelectedUnit, 1);
//
//        // Agregar JLabel al ArrayList
////        enemyUnitList.add(enemyUnit2);
//        
//        this.add(enemyUnit2.getLabel());
        selection.setBounds(425 , 590, 100, 100);              
//        // -----------------------------------------------------------------------------------------------------------
        
        
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



private void handleButtonUp() {
   // Obtener la unidad seleccionada y su imagen
    SelectedUnit = game.UnitSelected(game.getSelection());
    ImageIcon imagen = game.getImageIcon(SelectedUnit);
    JLabel label = new JLabel(imagen);

    // Verificar si la unidad está en cooldown
    if (cooldownFlags.containsKey(SelectedUnit) && cooldownFlags.get(SelectedUnit)) {
        System.out.println("Espera el cooldown para colocar otra unidad "+SelectedUnit);
        return;
    }else{
        startCooldownTimer(SelectedUnit, imagen);
    }

    // Resto del código para colocar la unidad...
                playerUnit = new Unit(label, SelectedUnit, 1);
           units.add(playerUnit); 
           
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
       
        int x = 10; 
        int y = 440; 
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                        
        
        damage=1;                       
        game.movementToplane(label, 745, 10,2);
        playerUnit.setCollisionTimer(collisionTimer);
    
        handleCollision(layeredPane);
        
        setVisible(true);

    // Iniciar el cooldown para la unidad seleccionada
//    startCooldownTimer(SelectedUnit, imagen);
}



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private boolean isCooldownKnightActive = false;
private boolean isCooldownhorseActive = false;
private boolean isCooldowncrossbowActive = false;

boolean cooldownFlag;
private Map<String, Boolean> cooldownFlags = new HashMap<>();
private Map<String, Integer> cooldownTimers = new HashMap<>();
int TimerValue;
private void startCooldownTimer(String unitType, ImageIcon imagen) {
    System.out.println("Iniciando cooldown para " + unitType);
    JLabel counterLabel;
    int value;

    // Configurar el contador y tiempo de cooldown según el tipo de unidad
    if (unitType.equalsIgnoreCase("knight")&&knightCounter.isVisible()==false ) {
        counterLabel = knightCounter;
        value = knightTimer;
            TimerValue = value;
Timer cooldownTimer1 = new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Tick del cooldown para " + unitType);
        counterLabel.setVisible(true);
        counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counterLabel.setText(Integer.toString(TimerValue));  // Usar la copia final
        TimerValue--;  // Decrementar la copia final

        if (TimerValue <= 0) {
             // El cooldown ha terminado, detener el Timer y permitir colocar la unidad nuevamente
                counterLabel.setVisible(false);
                ((Timer) e.getSource()).stop();  // Detener el temporizador cuando el enfriamiento ha terminado
                cooldownFlag = false;
                cooldownFlags.put("knight", true);
                
                // Aquí puedes eliminar la unidad del mapa de cooldowns si es necesario
                cooldownFlags.remove(unitType);
                cooldownTimers.remove(unitType);
                
        }
    }
});

cooldownTimer1.start();
    } else if (unitType.equalsIgnoreCase("horse")&&horseCounter.isVisible()==false ) {
        counterLabel = horseCounter;
        value = horseTimer;
            TimerValue = value;
Timer cooldownTimer2 = new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Tick del cooldown para " + unitType);
        counterLabel.setVisible(true);
        counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counterLabel.setText(Integer.toString(TimerValue));  // Usar la copia final
        TimerValue--;  // Decrementar la copia final

        if (TimerValue <= 0) {
             // El cooldown ha terminado, detener el Timer y permitir colocar la unidad nuevamente
                counterLabel.setVisible(false);
                ((Timer) e.getSource()).stop();  // Detener el temporizador cuando el enfriamiento ha terminado
                cooldownFlag = false;
               
    cooldownFlags.put("horse", true);
    
                // Aquí puedes eliminar la unidad del mapa de cooldowns si es necesario
                cooldownFlags.remove(unitType);
                cooldownTimers.remove(unitType);
                
        }
    }
});
cooldownTimer2.start();
    } else if (unitType.equalsIgnoreCase("crossbowMan")&&crossbowCounter.isVisible()==false) {
        counterLabel = crossbowCounter;
        value = crossbowTimer;
            TimerValue = value;
Timer cooldownTimer3 = new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Tick del cooldown para " + unitType);
        counterLabel.setVisible(true);
        counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counterLabel.setText(Integer.toString(TimerValue));  // Usar la copia final
        TimerValue--;  // Decrementar la copia final

        if (TimerValue <= 0) {
             // El cooldown ha terminado, detener el Timer y permitir colocar la unidad nuevamente
                counterLabel.setVisible(false);
                ((Timer) e.getSource()).stop();  // Detener el temporizador cuando el enfriamiento ha terminado
                cooldownFlag = false;
               
    cooldownFlags.put("crossbow", true);
                // Aquí puedes eliminar la unidad del mapa de cooldowns si es necesario
                cooldownFlags.remove(unitType);
                cooldownTimers.remove(unitType);
                
        }
    }
});
cooldownTimer3.start();

    } else {
        // Manejar otros tipos de unidades si es necesario
        return;
    }
// Iniciar el Timer y establecer el estado del cooldown en true
//    cooldownTimer.start();
    cooldownFlags.put(unitType, true);
    cooldownTimers.put(unitType, TimerValue);
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
//            playerUnit = new Unit(label, SelectedUnit, 1);
//           units.add(playerUnit); 
//           
//        JLayeredPane layeredPane = getLayeredPane();
//        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
//       
//        int x = 10; 
//        int y = 440; 
//        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
//                                        
//        
//        damage=1;                       
//        game.movementToplane(label, 745, 10,2);
//        playerUnit.setCollisionTimer(collisionTimer);
//    
//        handleCollision(layeredPane);
//        
//        setVisible(true);
        
        
        
//         unitList.add(unit);

    private void handleButtonDown() {
      // Obtener la unidad seleccionada y su imagen
    SelectedUnit = game.UnitSelected(game.getSelection());
    ImageIcon imagen = game.getImageIcon(SelectedUnit);
    JLabel label = new JLabel(imagen);

    // Verificar si la unidad está en cooldown
    if (cooldownFlags.containsKey(SelectedUnit) && cooldownFlags.get(SelectedUnit)) {
        System.out.println("Espera el cooldown para colocar otra unidad "+SelectedUnit);
        return;
    }

    // Resto del código para colocar la unidad...
                playerUnit = new Unit(label, SelectedUnit, 1);
           units.add(playerUnit); 
           
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
       
        int x = 110; 
        int y = 540; 
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                        
        
        damage=1;                       
        game.movementBotlane(label, 745, 10,2);
        playerUnit.setCollisionTimer(collisionTimer);
    
        handleCollision(layeredPane);
        
        setVisible(true);

    // Iniciar el cooldown para la unidad seleccionada
    startCooldownTimer(SelectedUnit, imagen);
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

    private void handleCollision(JLayeredPane layeredPane) { // METODO QUE FUNCIONA PARA DETECTAR COLISIONES ENTRE 2 LABELS
        //                                                                                     SI EXISTIERAN COLISIONES LLAMA A OTRO METODO PARA VERIFICAR QUE LABEL SE ELIMINA
        Timer collisionTimer = playerUnit.getCollisionTimer();
        collisionTimer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Iterator<Unit> unitIterator = units.iterator();
    while (unitIterator.hasNext()) {
        Unit unit = unitIterator.next();
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            boolean collision = game.checkCollision(unit, enemy);
            if (collision) {
                  int winner = game.encounterWinner();
//                 
                switch (winner) {
                    case 0:
//                        
                        unit.getLabel().setVisible(false);
                        enemy.getLabel().setVisible(false);
                        unitIterator.remove();
                        enemyIterator.remove();
                        layeredPane.remove(unit.getLabel());
                        layeredPane.remove(enemy.getLabel());
                        break;
                    case 2:
//                    
                        enemy.getLabel().setVisible(false);
                        enemyIterator.remove();
                        layeredPane.remove(enemy.getLabel());
                        break;
                    case 1:
                        unit.getLabel().setVisible(false);
                        unitIterator.remove();
                        layeredPane.remove(unit.getLabel());
                        break;
                }
            }
        }
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
    
