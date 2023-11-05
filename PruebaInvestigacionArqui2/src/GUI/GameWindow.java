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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public class GameWindow extends javax.swing.JFrame {

    // ------------------------------ VARIABLES GLOBALES ------------------------------
    public PanamaHitek_Arduino arduino;
    String messageArduino = "";
    Game game;
    SkinSelection skin;
    private boolean paused = false;
    private Timer collisionTimer;
    public int damage = 0;
    public int damageEnemy = 0;

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

    public ImageIcon imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
    public JLabel selection = new JLabel(imgSelection);
    private ArrayList<Unit> units;
    private ArrayList<Enemy> enemies;

    public Enemy enemyUnit = new Enemy();
    public Unit playerUnit = new Unit();

    private boolean isCooldownKnightActive = false;
    private boolean isCooldownhorseActive = false;
    private boolean isCooldowncrossbowActive = false;

    boolean cooldownFlag;
    private Map<String, Boolean> cooldownFlags = new HashMap<>();
    private Map<String, Integer> cooldownTimers = new HashMap<>();
    int TimerValue;
    
    int knightTimer = 10;
    JLabel knightCounter = new JLabel();
    int crossbowTimer = 10;
    JLabel crossbowCounter = new JLabel();
    int horseTimer = 10;
    JLabel horseCounter = new JLabel();
// ----------------------------------------------------------------------------------------------

    public GameWindow() { // INICIALIZA LA VENTANA
        game.finish = 0;
        units = new ArrayList<>();
        enemies = new ArrayList<>();
        cooldownFlags.put("knight", false);
        cooldownFlags.put("horse", false);
        cooldownFlags.put("crossbow", false);
        createAndAddUnits();
        initializeGameWindow();
    }

    private void init() {// INICIALIZA LOS COMPONENTES GRAFICOS
        getSelectedSkin();
        setupUIElements();
    }

    private void createAndAddUnits() {
        Timer timer = new Timer(2500, (ae) -> {

            if (game.isGameFinished() == true) {// VERIFICA SI YA HUBO UN GANADOR
                try {
                    ((Timer) ae.getSource()).stop();
                    arduino.killArduinoConnection();
                    this.setVisible(false);
                    this.dispose();
                    return;
                } catch (ArduinoException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            ImageIcon imagen = new ImageIcon();
            SelectedUnit = game.UnitSelected(game.getSelection());

            Random randomImage = new Random();
            int randomUnit = randomImage.nextInt(3);
            switch (randomUnit) {
                case 0:
                    imagen = imgEnemyKnight;
                    UnitName = "knight";
                    break;
                case 1:
                    imagen = imgEnemyHorse;
                    UnitName = "horse";
                    break;
                default:
                    imagen = imgEnemyCrossbow;
                    UnitName = "crossbowMan";
                    break;
            }

            JLabel label = new JLabel(imagen);
            enemyUnit = new Enemy(label, UnitName, 1);
            enemies.add(enemyUnit);

            JLayeredPane layeredPane = getLayeredPane();
            layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

            int x = 745;
            int y = 10;
            label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());

            Random rand = new Random();
            int random = rand.nextInt(2);

            if (random == 0) {
                game.movementToplane(label, 10, 540, 1);
            } else {
                game.movementBotlane(label, 10, 540, 1);
            }

            enemyUnit.setCollisionTimer(collisionTimer);

            handleCollision(layeredPane);
            setVisible(true);

        });
        damageEnemy = 1;
        timer.start();
    }

    private void getSelectedSkin() {

        int skinNumber = skin.getSkin();
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

        Color backgroundColor = new Color(27, 25, 24, 200);
        Font customFont = new Font("Arial", Font.PLAIN, 80);

        // KNIGHT CONTADOR LABEL
        knightCounter.setBounds(90, 600, 100, 100);
        knightCounter.setFont(customFont);
        knightCounter.setBackground(backgroundColor);
        knightCounter.setForeground(Color.WHITE);
        knightCounter.setOpaque(true);
        knightCounter.setVisible(false);
        this.add(knightCounter);

        // HORSE CONTADOR LABEL
        horseCounter.setBounds(350, 600, 100, 100);
        horseCounter.setFont(customFont);
        horseCounter.setBackground(backgroundColor);
        horseCounter.setForeground(Color.WHITE);
        horseCounter.setOpaque(true);
        horseCounter.setText(Integer.toString(horseTimer));
        horseCounter.setVisible(false);
        this.add(horseCounter);

        // CORSSBOW CONTADOR LABEL
        crossbowCounter.setBounds(610, 600, 100, 100);
        crossbowCounter.setFont(customFont);
        crossbowCounter.setBackground(backgroundColor);
        crossbowCounter.setForeground(Color.WHITE);
        crossbowCounter.setOpaque(true);
        crossbowCounter.setText(Integer.toString(crossbowTimer));
        crossbowCounter.setVisible(false);
        this.add(crossbowCounter);

        selection.setBounds(425, 590, 100, 100);

        JLabel fake = new JLabel();
        fake.setBounds(170, 590, 100, 100);
        game.Selection(fake, messageArduino);
        this.add(selection);

        JLabel knight = new JLabel(imgKnightHUD);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);

        JLabel horse = new JLabel(imgHorseHUD);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);

        JLabel crossBow = new JLabel(imgCrossbowHUD);
        crossBow.setBounds(610, 600, 100, 100);
        this.add(crossBow);

        JLabel hud = new JLabel(imgHUD);
        hud.setBounds(0, 590, 800, 120);
        this.add(hud);

        JLabel map = new JLabel(imgMap);
        map.setBounds(0, 0, 800, 600);
        this.add(map);
    }

    private void initializeGameWindow() { // SE CONFIGURA LA VENTANA Y SE CREAN LOS OBJETOS NECESARIOS PARA EL FUNCIONAMIENTO DEL ARDUINO
        setSize(815, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new PanamaHitek_Arduino();
        game = new Game(2, 2, arduino);// INICIA EL JUEGO CON CADA TORRE EN 2 VIDAS

        try {
            setupArduino();
        } catch (ArduinoException | InterruptedException | SerialPortException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        skin = new SkinSelection(1);// OBTIENE LA SKIN ELEGIDA POR EL USUARIO EN EL MENU PRINCIPAL
        init();
    }

    private void setupArduino() throws ArduinoException, InterruptedException, SerialPortException { // CONFIGURACION DEL ARDUINO
        arduino.arduinoRXTX("COM3", 9600, createSerialPortListener());
        Thread.sleep(2000);// ESPERA A QUE EL PUERTO ESTE LISTO PARA ENVIAR Y RECIBIR DATOS
        arduino.sendData("1");// ENCIENDE LOS LEDS DE LA PROTOBOARD QUE REPRESENTAN LAS VIDAS DE CADA TORRE
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

    private void handleButtonUp() {// SI SE PRESIONA EL BOTON ARRIBA
        // OBTENEMOS LA UNIDAD SELECCIONADA POR EL JUGADOR Y SU IMAGEN
        SelectedUnit = game.UnitSelected(game.getSelection());
        ImageIcon imagen = game.getImageIcon(SelectedUnit);
        JLabel label = new JLabel(imagen);

        // VERIFICA SI LA UNIDAD ESTA EN COOLDOWN (TIEMPO DE ESPERA ANTES DE ENVIAR OTRA IGUAL)
        if (cooldownFlags.containsKey(SelectedUnit) && cooldownFlags.get(SelectedUnit)) {
            return;// SE SALE
        } else {
            startCooldownTimer(SelectedUnit, imagen);// INICIA EL COOLDOWN
        }

        // ----------------- COLOCAR LA UNIDAD SELECCIONADA -------------------------
        playerUnit = new Unit(label, SelectedUnit, 1);
        units.add(playerUnit);

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

        int x = 10;
        int y = 440;
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        // --------------------------------------------------------------------------
        damage = 1;
        game.movementToplane(label, 745, 10, 2); // MOVIMIENTO DE LA UNIDAD
        playerUnit.setCollisionTimer(collisionTimer);// INICIA EL CONTADOR DE COLISIONES

        handleCollision(layeredPane);// SI HAY COLISION DE ESA UNIDAD

        setVisible(true);
    }

    private void startCooldownTimer(String unitType, ImageIcon imagen) {// METODO QUE INICIA EL COOLDOWN DE LA UNIDAD CREADA
        JLabel counterLabel;
        int value;

        // ----------------- SE CONFIGURA EL CONTADOR Y EL TIEMPO DEL COOLDOWN SEGUN EL TIPO DE UNIDAD -----------------------
        if (unitType.equalsIgnoreCase("knight") && knightCounter.isVisible() == false) {
            counterLabel = knightCounter;
            value = knightTimer;
            TimerValue = value;
            Timer cooldownTimer1 = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    counterLabel.setVisible(true);
                    counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    counterLabel.setText(Integer.toString(TimerValue));
                    TimerValue--;  // DECREMENTO DEL TIMER

                    if (TimerValue <= 0) {// EL COOLDOWN YA PASO Y SE PUEDE COLOCAR DE NUEVO LA UNIDAD
                        counterLabel.setVisible(false);
                        ((Timer) e.getSource()).stop(); // DETIENE EL TIMER
                        cooldownFlag = false;// COOLDOWN EN FALSE
                        cooldownFlags.put("knight", true);

                        // SE ELIMINAN DEL MAPA
                        cooldownFlags.remove(unitType);
                        cooldownTimers.remove(unitType);

                    }
                }
            });

            cooldownTimer1.start();
        } else if (unitType.equalsIgnoreCase("horse") && horseCounter.isVisible() == false) {
            counterLabel = horseCounter;
            value = horseTimer;
            TimerValue = value;
            Timer cooldownTimer2 = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    counterLabel.setVisible(true);
                    counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    counterLabel.setText(Integer.toString(TimerValue));
                    TimerValue--;

                    if (TimerValue <= 0) {
                        counterLabel.setVisible(false);
                        ((Timer) e.getSource()).stop();
                        cooldownFlag = false;

                        cooldownFlags.put("horse", true);

                        cooldownFlags.remove(unitType);
                        cooldownTimers.remove(unitType);

                    }
                }
            });
            cooldownTimer2.start();
        } else if (unitType.equalsIgnoreCase("crossbowMan") && crossbowCounter.isVisible() == false) {
            counterLabel = crossbowCounter;
            value = crossbowTimer;
            TimerValue = value;
            Timer cooldownTimer3 = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    counterLabel.setVisible(true);
                    counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    counterLabel.setText(Integer.toString(TimerValue));
                    TimerValue--;

                    if (TimerValue <= 0) {
                        counterLabel.setVisible(false);
                        ((Timer) e.getSource()).stop();
                        cooldownFlag = false;

                        cooldownFlags.put("crossbow", true);

                        cooldownFlags.remove(unitType);
                        cooldownTimers.remove(unitType);

                    }
                }
            });
            cooldownTimer3.start();

        } else {
            return;
        }
        // -------------------------------------------------------------------------------------------------------------

        cooldownFlags.put(unitType, true);// ESTABLECER COOLDOWNS EN TRUE
        cooldownTimers.put(unitType, TimerValue);
    }

    private void handleButtonDown() {// SI SE PRESIONA ABAJO
        // OBTIENE LA UNIDAD SELECCIONADA Y SU IMAGEN
        SelectedUnit = game.UnitSelected(game.getSelection());
        ImageIcon imagen = game.getImageIcon(SelectedUnit);
        JLabel label = new JLabel(imagen);

        // VERIFICA SI HAY COOLDOWN
        if (cooldownFlags.containsKey(SelectedUnit) && cooldownFlags.get(SelectedUnit)) {
            return;
        }

        // ----------------------------- COLOCAR UNIDAD SELECCIONADA --------------------------
        playerUnit = new Unit(label, SelectedUnit, 1);
        units.add(playerUnit);

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);

        int x = 110;
        int y = 540;
        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
//      ------------------------------------------------------------------------------------
        damage = 1;
        game.movementBotlane(label, 745, 10, 2);// MOVIMIENTO
        playerUnit.setCollisionTimer(collisionTimer);// INICIA EL CONTADOR DE COLISION

        handleCollision(layeredPane);// SI HAY COLISION

        setVisible(true);

        startCooldownTimer(SelectedUnit, imagen);// INICIA EL COOLDOWN
    }

    private void handleButtonRight() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA LA DERECHA
        int position = game.Selection(selection, "DERECHA");
        selection.setBounds(position, 590, 100, 100);// MUEVE EL CURSOR HACIA LA DERECHA PARA SELECCIONAR OTRA UNIDAD
    }

    private void handleButtonLeft() { // METODO QUE REALIZA LO QUE SE NECESITA CUANDO SE PRESIONA EL BOTON HACIA ABAJO
        int position = game.Selection(selection, "IZQUIERDA");
        selection.setBounds(position, 590, 100, 100);// MUEVE EL CURSOR HACIA LA IZQUIERDA PARA SELECCIONAR OTRA UNIDAD
    }

    private void handlePauseButton() { // FINALIZA EL JUEGO Y VUELVE A LA PANTALLA PRINCIPAL
        game.finish = 1;
        FinalMessage msj = new FinalMessage(3);// MUESTRA UNA VENTANA EMERGENTE
        msj.setVisible(true);
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

                            switch (winner) {
                                case 0:// EMPATE - SE ELIMINAN AMBOS
                                    unit.getLabel().setVisible(false);
                                    enemy.getLabel().setVisible(false);
                                    unitIterator.remove();
                                    enemyIterator.remove();
                                    layeredPane.remove(unit.getLabel());
                                    layeredPane.remove(enemy.getLabel());
                                    break;
                                case 2:// GANA UNIDAD 2 - SE ELIMINA EL ENEMIGO
                                    enemy.getLabel().setVisible(false);
                                    enemyIterator.remove();
                                    layeredPane.remove(enemy.getLabel());
                                    break;
                                case 1:// GANA UNIDAD 1 - SE ELIMINA EL ALIADO
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
        collisionTimer.start();// INICIA EL CONTADOR
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
