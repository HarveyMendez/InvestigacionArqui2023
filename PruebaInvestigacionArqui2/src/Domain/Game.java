/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

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
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public class Game{
    
    private PanamaHitek_Arduino arduino;
    
    
    private int tower1;
    private int tower2;
    public int finish = 0;
    public int Selection;
    
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

    public int getSelection() {
        return Selection;
    }
    
    
    public Game(int tower1, int tower2, PanamaHitek_Arduino arduino) {
        this.tower1 = tower1;
        this.tower2 = tower2;
        this.arduino = arduino; 
    }
    
    
    public int Selection(JLabel label, String msgArduino) {
    int x = label.getX();
        
    final int IZQUIERDA = 170;
    final int CENTRO = 425;
    final int DERECHA = 700;
        
    switch (x) {
        case CENTRO:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? IZQUIERDA : DERECHA;
            this.Selection=x;
            break;
        case IZQUIERDA:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? DERECHA : CENTRO;
            this.Selection=x;
            break;
        case DERECHA:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? CENTRO : IZQUIERDA;
            this.Selection=x;
            break;
        default:
            if(msgArduino.equalsIgnoreCase("")){
            x=CENTRO;
            this.Selection=x;}
            break;
    }

    return x;
}

    
    public String UnitSelected(int xPosition){
        xPosition = this.Selection;
        String unitName="UNIT NOT FOUND";
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

   public ImageIcon icon1 = new ImageIcon();
   public ImageIcon icon2 = new ImageIcon();
   public String playerUnitName;
   public String enemyUnitName;
    public boolean checkCollision(JLabel label1, JLabel label2) {
        Rectangle rectLabel1 = label1.getBounds();
            Rectangle rectLabel2 = label2.getBounds();
        if(label1.isVisible() && label2.isVisible()){
            
        
        icon1 = (ImageIcon) label1.getIcon();
        icon2 = (ImageIcon) label2.getIcon();
        
        if(icon1.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/knightGame.png")){
            playerUnitName="knight";
        }
        if(icon1.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/horseGame.png")){
            playerUnitName="horse";
        }
        if(icon1.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/crossbowGame.png")){
            playerUnitName="crossBow";
        }
        if(icon2.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/knightGameEnemy.png")){
            enemyUnitName="knight";
        }
        if(icon2.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/horseGameEnemy.png")){
            enemyUnitName="horse";
        }
        if(icon2.toString().equalsIgnoreCase("file:/C:/Users/jodas/OneDrive/Desktop/InvestigacionArqui2023/PruebaInvestigacionArqui2/build/classes/img/UNITS/UNITS1/crossbowGameEnemy.png")){
            enemyUnitName="crossBow";
        }
        return rectLabel1.intersects(rectLabel2);
        }else{
            return false; 
        }
//        return rectLabel1.intersects(rectLabel2);
    }
    
    public int win;
 public String encounterWinner() {
    String unit2 = playerUnitName;
    String unit1 = enemyUnitName;
    
    if (unit1.equals(unit2)) {
        win = 0;
        return "empate";
    } else if (("horse".equals(unit1) && "knight".equals(unit2)) || 
               ("crossBow".equals(unit1) && "horse".equals(unit2)) ||
               ("knight".equals(unit1) && "crossBow".equals(unit2))) {
        win = 2;
        return unit2;
    } else {
        win = 1;
        return unit1;
    }
}

    
    public void towerDamage(int numTower){
        
        if(numTower == 1){
            this.tower1 -=1;
            try {
                arduino.sendData("7");// APAGAR LED ARDUINO
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tower1 ==0){
                try {
                    arduino.sendData("8");// APAGAR LED ARDUINO
                    finish=1;
                    JOptionPane.showMessageDialog(null, "¡PERDISTE!", "FIN DEL JUEGO", JOptionPane.WARNING_MESSAGE);
                } catch (ArduinoException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(numTower == 2){
            try {
                this.tower2 -=1;
                arduino.sendData("5");// APAGAR LED ARDUINO 
            } catch (ArduinoException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SerialPortException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(tower2 ==0){
                try {
                    arduino.sendData("6");// APAGAR LED ARDUINO
                    finish=1;
                    JOptionPane.showMessageDialog(null, "¡GANASTE!", "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
                } catch (ArduinoException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
        System.out.println("VIDA TORRE 1:"+tower1+"/2");
        System.out.println("VIDA TORRE 2:"+tower2+"/2");
        
    }
    
    public int stop;
    Timer timer;
   
    public void movementBotlane(JLabel label, int x, int y) {
    System.out.println("VOY POR BOT");
    
    int delay = 10; 
     timer = new Timer(delay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int posX = label.getX();
            int posY = label.getY();
            if(stop==1){
                stop=0;
                timer.stop();
            }else if(stop==0){
                
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

            label.setLocation(posX, posY);

            if (posX == x && posY == y && label.isVisible()) {
                label.setVisible(false);
                ((Timer) e.getSource()).stop();
                towerDamage(2);
            }  
            
            
        }
    });
     if (stop == 0) {
        timer.start();
        // Iniciar el temporizador de colisión
        
    }
       
    
    
}

    
    public  void movementToplane(JLabel label, int x, int y) {
    System.out.println("VOY POR TOP");

    int delay = 10; 
     timer = new Timer(delay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int posX = label.getX();
            int posY = label.getY();
            if(stop==1){
                stop=0;
                timer.stop();
            }else if(stop==0){
                
                timer.start();
            }
            // SE MUEVE EN Y PRIMERO
            if (posY < y && label.isVisible()) {
                posY++;
            } else if (posY > y) {
                posY--;
            }

            // LUEGO EN X
            if (posX < x && posY == y && label.isVisible()){
                posX++;
            } else if (posX > x && posY == y) {
                posX--;
            }

            label.setLocation(posX, posY);

            if (posX == x && posY == y && label.isVisible()) {
                label.setVisible(false);
                ((Timer) e.getSource()).stop();
                towerDamage(2);
                
            }
        }
    });
    if (stop == 0) {
        timer.start();
        // Iniciar el temporizador de colisión
        
    }
    
}

    public ImageIcon getImageIcon(String SelectedUnit) {
        ImageIcon imagen = new ImageIcon();
        skin = new SkinSelection();
            if(SelectedUnit.equalsIgnoreCase("HORSE")){
                
                
        int skinNumber = skin.getSkin();
        System.out.println("skin: "+skinNumber);
        switch (skinNumber) {
            case 1: // SKIN MEDIEVAL
                
                
                
                //HORSE
//                imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGame.png"));
               // imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGameEnemy.png"));
                
               
                
                break;
                
            case 2: // SKIN MAGIC
                
               
                
                //HORSE
               // imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horsepeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGame.png"));
               // imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGameEnemy.png"));
                
               
                break;
                
            case 3:// SKIN LEAGUE
                
                //KNIGHT
              
                
                //HORSE
               // imgHorseHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horsepeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGame.png"));
               // imgEnemyHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGameEnemy.png"));
                
               
                
                break;
            
        }
            }
            
            if(SelectedUnit.equalsIgnoreCase("KNIGHT")){
                int skinNumber = skin.getSkin();
        System.out.println("skin: "+skinNumber);
        switch (skinNumber) {
            case 1: // SKIN MEDIEVAL
                
                //KNIGHT
              //  imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGame.png"));
               // imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGameEnemy.png"));
                
               
                
                break;
                
            case 2: // SKIN MAGIC
                
                //KNIGHT
               // imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGame.png"));
              //  imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGameEnemy.png"));
                
                
                break;
                
            case 3:// SKIN LEAGUE
                
                //KNIGHT
               // imgKnightHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGame.png"));
               // imgEnemyKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGameEnemy.png"));
                
               
                break;
            
        }
            }
            
            if(SelectedUnit.equalsIgnoreCase("CROSSBOWMAN")){
                int skinNumber = skin.getSkin();
        System.out.println("skin: "+skinNumber);
        switch (skinNumber) {
            case 1: // SKIN MEDIEVAL
            
                //CROSSBOW
               // imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGame.png"));
               // imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGameEnemy.png"));
           
                break;
                
            case 2: // SKIN MAGIC
             
                //CROSSBOW
               // imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGame.png"));
               // imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGameEnemy.png"));
                
          
                break;
                
            case 3:// SKIN LEAGUE
                
             
                //CROSSBOW
              //  imgCrossbowHUD = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowpeque.png"));
                imagen = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGame.png"));
              //  imgEnemyCrossbow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGameEnemy.png"));
                
                
                
                break;
            
        }
            }
            System.out.println("Unidad seleccionada: " + SelectedUnit);
            
        return imagen;
    }


}

    
    
    

