/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import GUI.DamageMessage;
import GUI.FinalMessage;
import GUI.SkinSelection;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public class Game {
// ---------------------------- VARIABLES GLOBALES --------------------------------
    public PanamaHitek_Arduino arduino;

    private int tower1;
    private int tower2;
    public static int finish = 0;
    public int Selection;
    public int damage;
    public int damageEnemy;

    SkinSelection skin;

    ImageIcon imgKnightHUD;
    ImageIcon imgKnight;
    ImageIcon imgEnemyKnight;
    ImageIcon imgHorseHUD;
    ImageIcon imgHorse;
    ImageIcon imgEnemyHorse;
    ImageIcon imgCrossbowHUD;
    ImageIcon imgCrossbow;
    ImageIcon imgEnemyCrossbow;
    
    public ImageIcon icon1 = new ImageIcon();
    public ImageIcon icon2 = new ImageIcon();
    public String playerUnitName;
    public String enemyUnitName;
    
    public int win;
    public int stop;
    Timer timer;
    DamageMessage dmgMessage;
// ---------------------------------------------------------------------------------
    
    public int getSelection() {
        return Selection;
    }

    public Game(int tower1, int tower2, PanamaHitek_Arduino arduino) {// INICIA EL JUEGO
        this.tower1 = tower1;
        this.tower2 = tower2;
        this.arduino = arduino;
    }

    public int Selection(JLabel label, String msgArduino) { // OBTIENE EL TIPO DE UNIDAD DEPENDIENDO DE LA COORDENADA EN DONDE ESTE EL SELECCIONADOR
        int x = label.getX();

        final int IZQUIERDA = 170;
        final int CENTRO = 425;
        final int DERECHA = 700;

        switch (x) {
            case CENTRO:
                x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? IZQUIERDA : DERECHA;
                this.Selection = x;
                break;
            case IZQUIERDA:
                x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? DERECHA : CENTRO;
                this.Selection = x;
                break;
            case DERECHA:
                x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? CENTRO : IZQUIERDA;
                this.Selection = x;
                break;
            default:
                if (msgArduino.equalsIgnoreCase("")) {
                    x = CENTRO;
                    this.Selection = x;
                }
                break;
        }

        return x;
    }

    public String UnitSelected(int xPosition) {// METODO PARA OBTENER EL NOMBRE LA UNIDAD SELECCIONADA QUE  SE DESEA JUGAR
        xPosition = this.Selection;
        String unitName = "UNIT NOT FOUND";
        switch (xPosition) {
            case 425: // CENTRO
                unitName = "HORSE";
                break;
            case 170: // IZQUIERDA
                unitName = "KNIGHT";
                break;
            case 700: // DERECHA
                unitName = "CROSSBOWMAN";
                break;
        }

        return unitName;
    }

    public boolean checkCollision(Unit label1, Enemy label2) { // VERIFICA QUE HAYA COLISION ENTRE UNA UNIDAD Y UN ENEMIGO Y OBTIENE SUS NOMBRES
        Rectangle rectLabel1 = label1.getLabel().getBounds();
        Rectangle rectLabel2 = label2.getLabel().getBounds();
        if (label1.getLabel().isVisible() && label2.getLabel().isVisible()) {

            playerUnitName = label1.getUnitName();// OBTIENE SUS NOMBRES
            enemyUnitName = label2.getUnitName();

            return rectLabel1.intersects(rectLabel2);// DEVUELVE TRUE SI HAY UNA INTERSECCION
        } else {

            return false;
        }
    }

    public int encounterWinner() {// SI HAY UNA COLISION ENCUENTRA AL GANADOR ENTRE ESAS COLISIONES
        String unit2 = playerUnitName;
        String unit1 = enemyUnitName;

        if (unit1.equalsIgnoreCase(unit2)) {
            win = 0;// EMPATE
            return 0;
        } else if (("horse".equalsIgnoreCase(unit1) && "knight".equalsIgnoreCase(unit2))
                || ("crossBowMan".equalsIgnoreCase(unit1) && "horse".equalsIgnoreCase(unit2))
                || ("knight".equalsIgnoreCase(unit1) && "crossBowMan".equalsIgnoreCase(unit2))) {
            win = 2;// UNIT 2 GANA
            return 2;
        } else {
            win = 1;// UNIT 1 GANA
            return 1;
        }
    }

    public void towerDamage(int numTower) {// VERIFICA CUANTA VIDA TOTAL TIENE LA TORRE Y SI ESTA LLEGA A 0, SE TERMINA EL JUEGO Y ENVIA UN MENSAJE

        if (numTower == 1) {// TORRE ALIADA
            this.tower1 -= 1;
            dmgMessage = new DamageMessage(2);
            dmgMessage.setVisible(true);
            try {
                arduino.sendData("7");// APAGAR LED ARDUINO
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tower1 == 0) {// SI LLEGA A 0 LA TORRE
                try {
                    dmgMessage = new DamageMessage(2);
                    dmgMessage.setVisible(true);
                    arduino.sendData("8");// APAGAR LED ARDUINO
                    finish = 1;// SE TERMINA EL JUEGO, DERROTA

                    FinalMessage msj = new FinalMessage(2);
                    msj.setVisible(true);
                    return;// NO SIGUE EJECUTANDO NADA MAS

                } catch (ArduinoException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (numTower == 2) {// TORRE ENEMIGA
            try {
                this.tower2 -= 1;
                dmgMessage = new DamageMessage(1);
                dmgMessage.setVisible(true);
                arduino.sendData("5");// APAGAR LED ARDUINO 
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tower2 == 0) {// SI LLEGA A 0 LA TORRE
                try {
                    dmgMessage = new DamageMessage(1);
                    dmgMessage.setVisible(true);
                    arduino.sendData("6");// APAGAR LED ARDUINO
                    finish = 1;// SE TERMINA EL JUEGO, VICTORIA

                    FinalMessage msj = new FinalMessage(1);
                    msj.setVisible(true);
                    return;// NO EJECUTA NADA MAS

                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void movementBotlane(JLabel label, int x, int y, int damage) {// MUEVE LA UNIDAD QUE TRAE POR PARAMETRO A SU OBJETIVO
        int delay = 10;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGameFinished() == true) {// SI EL JUEGO SE TERMINO, PARA EL CONTADOR
                    ((Timer) e.getSource()).stop();
                    return;
                }
                int posX = label.getX();
                int posY = label.getY();
                if (stop == 1) {
                    stop = 0;
                    timer.stop();
                } else if (stop == 0) {

                    timer.start();
                }

                // PRIMERO SE MUEVE EN X
                if (posX < x && label.isVisible()) {
                    posX++;
                } else if (posX > x) {
                    posX--;
                }

                // LUEGO EN Y
                if (posY < y && posX == x && label.isVisible()) {
                    posY++;
                } else if (posY > y && posX == x) {
                    posY--;
                }

                label.setLocation(posX, posY);// RECOLOCAR

                if (posX == x && posY == y && label.isVisible()) {
                    label.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    if (damage == 2) {
                        towerDamage(2);
                    }
                    if (damage == 1) {
                        towerDamage(1);
                    }
                }

            }
        });
        if (stop == 0) {
            timer.start();
        }

    }

    public void movementToplane(JLabel label, int x, int y, int damage) {// MUEVE LA UNIDAD DEL PARAMETRO HASTA SU DESTINO
        int delay = 10;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGameFinished() == true) {// SI EL JUEGO TERMINO, PARA EL CONTADOR Y NO REALIZA NADA MAS
                    ((Timer) e.getSource()).stop();
                    return;
                }
                int posX = label.getX();
                int posY = label.getY();
                if (stop == 1) {
                    stop = 0;
                    timer.stop();
                } else if (stop == 0) {

                    timer.start();
                }
                // SE MUEVE EN Y PRIMERO
                if (posY < y && label.isVisible()) {
                    posY++;
                } else if (posY > y) {
                    posY--;
                }

                // LUEGO EN X
                if (posX < x && posY == y && label.isVisible()) {
                    posX++;
                } else if (posX > x && posY == y) {
                    posX--;
                }

                label.setLocation(posX, posY);// RECOLOCANDO

                if (posX == x && posY == y && label.isVisible()) {
                    label.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    if (damage == 2) {
                        towerDamage(2);
                    }
                    if (damage == 1) {
                        towerDamage(1);
                    }

                }
            }
        });
        if (stop == 0) {
            timer.start();
        }

    }

    public ImageIcon getImageIcon(String SelectedUnit) {// OBTIENE EL ICONO DE LA UNIDAD SELECCIONADA PARA ENVIARLA
        ImageIcon imagen = new ImageIcon();
        skin = new SkinSelection();
        if (SelectedUnit.equalsIgnoreCase("HORSE")) {

            int skinNumber = skin.getSkin();

            switch (skinNumber) {
                case 1: // SKIN MEDIEVAL
                    //HORSE
                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGame.png"));
                    break;

                case 2: // SKIN MAGIC

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGame.png"));

                    break;

                case 3:// SKIN LEAGUE

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGame.png"));

                    break;

            }
        }

        if (SelectedUnit.equalsIgnoreCase("KNIGHT")) {
            int skinNumber = skin.getSkin();

            switch (skinNumber) {
                case 1: // SKIN MEDIEVAL

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGame.png"));

                    break;

                case 2: // SKIN MAGIC

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGame.png"));

                    break;

                case 3:// SKIN LEAGUE

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGame.png"));

                    break;

            }
        }

        if (SelectedUnit.equalsIgnoreCase("CROSSBOWMAN")) {
            int skinNumber = skin.getSkin();

            switch (skinNumber) {
                case 1: // SKIN MEDIEVAL

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGame.png"));

                    break;

                case 2: // SKIN MAGIC

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGame.png"));

                    break;

                case 3:// SKIN LEAGUE

                    imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGame.png"));

                    break;

            }
        }

        return imagen;
    }

    public boolean isGameFinished() {// VERIFICA QUE EL JUEGO TERMINE
        if (finish == 1) {
            return true;
        } else {
            return false;
        }
    }

}
