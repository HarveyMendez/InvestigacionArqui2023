/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public class Game{
    
    public PanamaHitek_Arduino arduino;
    
    private int tower1;
    private int tower2;
    public int finish = 0;
    public int Selection;
    public int pause = 0;
    public int skin;
    
    private Map map;
    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Unit> enemyUnits = new ArrayList<>();
    
    public Game(int tower1, int tower2, int skin, PanamaHitek_Arduino arduino) {
        this.arduino = arduino;
        this.tower1 = tower1;
        this.tower2 = tower2;
        this.skin = skin;
        map = new Map(this.skin);
        
    }
    
    public void chargeMap(Graphics g) {
        map.draw(g);
    }

    public int getSelection() {
        return Selection;
    }

    public int Selection(int x, String msgArduino) {

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

    public int UnitSelected(int xPosition) {
        xPosition = this.Selection;
        int typeUnit = 0;
        switch (xPosition) {
            case 425: // CENTRO
                typeUnit = 3;
                break;
            case 170: // IZQUIERDA
                typeUnit = 1;
                break;
            case 700: // DERECHA
                typeUnit = 2;
                break;
        }
        return typeUnit;
    }

    public boolean checkCollision() {

        for (Unit unit2 : enemyUnits) {
            //CHOQUE CON TORRE ALIADA
            if (unit2.getX() == 10 && unit2.getY() == 540) {
                enemyUnits.remove(unit2);
                towerDamage(1);
                return true;
            }
        }

        for (Unit unit1 : units) {

            //CHOQUE CON TORRE ENEMIGA
            if (unit1.getX() == 745 && unit1.getY() == 10) {
                units.remove(unit1);
                towerDamage(2);
                return true;

            }
            for (Unit unit2 : enemyUnits) {

                //CHOQUE CON TORRE ALIADA
                if (unit2.getX() == 10 && unit2.getY() == 540) {
                    System.out.println("Daño torre aliada");
                    enemyUnits.remove(unit2);
                    towerDamage(1);
                    return true;
                }

                if (unit1 != unit2 && unit1.collision(unit2)) {

                    //COQUE CON OTRA UNIDAD
                    int winner = this.encounterWinner(unit1.getType(), unit2.getType());
                    if (winner == 1) {
                        enemyUnits.remove(unit2);
                    }
                    if (winner == 0) {
                        enemyUnits.remove(unit2);
                        units.remove(unit1);

                    }
                    if (winner == -1) {
                        units.remove(unit1);
                    }

                    return true;
                }

            }
        }
        return false;
    }

    public int encounterWinner(int type1, int type2) {

        if (type1 == type2) {
            return 0; // Empate
        }
        if ((type1 == 1 && type2 == 3)
                || (type1 == 2 && type2 == 1)
                || (type1 == 3 && type2 == 2)) {
            return 1; // Usuario gana
        }
        return -1; // Computadora gana

    }

    public void towerDamage(int numTower){

        if (numTower == 1) {
            this.tower1 -= 1;
            try {
                arduino.sendData("7");// APAGAR LED ARDUINO
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tower1 == 0) {
                try {
                    arduino.sendData("8");// APAGAR LED ARDUINO
                } catch (ArduinoException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.finish = 1;
                this.pause();
            }
        }

        if (numTower == 2) {
            this.tower2 -= 1;
            try {
                arduino.sendData("5");// APAGAR LED ARDUINO
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (tower2 == 0) {
                try {
                    arduino.sendData("6");// APAGAR LED ARDUINO
                } catch (ArduinoException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.finish = 1;
                this.pause();
            }
        }
        
    }

    public void refresh2() {


        for (Unit unit : units) {
            unit.move();
        }

        for (Unit unit : enemyUnits) {
            unit.move();
        }
 
        if (this.checkCollision() == true) {
            
        }

    }

    public void refresh(Graphics g) {
        for (Unit unit : units) {
            unit.draw(g);
        }
        for (Unit unit : enemyUnits) {
            unit.draw(g);
        }
    }

    public void addUnit(int line) {

        Random random = new Random();
        int randomType = random.nextInt(4 - 1) + 1;

        if (line == 2) {
            Unit newUnit = new Unit(110, 540, UnitSelected(Selection), line, skin);
            units.add(newUnit);
        }
        if (line == 1) {
            Unit newUnit = new Unit(10, 440, UnitSelected(Selection), line, skin);
            units.add(newUnit);
        }
        if (line == 3) {

            Unit newUnit = new Unit(700, 10, randomType, line, skin);
            enemyUnits.add(newUnit);
        }

        if (line == 4) {
            Unit newUnit = new Unit(740, 60, randomType, line, skin);
            enemyUnits.add(newUnit);
        }

    }
    
    public boolean getPause() {
        return this.pause != 0;
    }
    
    public void pause() {
        if(this.pause == 1){
            this.pause = 0;
        }else {
            this.pause = 1;
        }
    }
    
    public boolean finish(){
        return this.finish != 0;
    }
    
    public int getGanador(){
        if(this.tower1 == 0) {
            return 2;
        } else {
            return 1;
        }
            
    }
    
}

    
    
    

