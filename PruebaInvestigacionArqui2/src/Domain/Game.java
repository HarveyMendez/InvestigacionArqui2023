/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author diego
 */
public class Game{
    
    private int tower1;
    private int tower2;
    private int finish = 0;
            
            
    public Game(int tower1, int tower2){
        
        this.tower1 = tower1;
        this.tower2 = tower2;     
        
        
        
    }
    
    
//    public int Selection(JLabel label, String msgArduino){
//        int x = label.getX();
//        //IZQ 170 Boton IZQUIERDA presionado
//        // CENTRO 425 
//        // DERECHA 700  Boton DERECHA presionado
//        
//        // IZQUIERDA DEL CENTRO
//        if(x == 425 && msgArduino.equalsIgnoreCase("IZQUIERDA")){
//            x=170;
//            return x;
//        }
//        // IZQUIERDA DE LA IZQ
//        if(x == 170 &&  msgArduino.equalsIgnoreCase("IZQUIERDA")){
//            x=700;
//            return x;
//        }
//        // IZQUIERDA DE LA DERECHA
//        if(x == 700 &&  msgArduino.equalsIgnoreCase("IZQUIERDA")){
//            x=425;
//            return x;
//        }
//        ////////////////////////////
//        
//        // DERECHA DEL CENTRO
//        if(x == 425 && msgArduino.equalsIgnoreCase("DERECHA")){
//            x=700;
//            return x;
//        }
//        // DERECHA DE LA DERECHA
//        if(x == 700 &&  msgArduino.equalsIgnoreCase("DERECHA")){
//            x=170;
//            return x;
//        }
//        // DERECHA DE LA IZQUIERDA
//        if(x == 170 &&  msgArduino.equalsIgnoreCase("DERECHA")){
//            x=425;
//            return x;
//        }
//        
//        
//        return x;
//    }
    
    
    public int Selection(JLabel label, String msgArduino) {
    int x = label.getX();
    final int IZQUIERDA = 170;
    final int CENTRO = 425;
    final int DERECHA = 700;

    switch (x) {
        case CENTRO:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? IZQUIERDA : DERECHA;
            break;
        case IZQUIERDA:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? DERECHA : CENTRO;
            break;
        case DERECHA:
            x = (msgArduino.equalsIgnoreCase("IZQUIERDA")) ? CENTRO : IZQUIERDA;
            break;
    }

    return x;
}


    
    public boolean checkCollision(JLabel label1, JLabel label2) {
        Rectangle bounds1 = label1.getBounds();
        System.out.println("BOUNDS ALIADOS: "+label1.getBounds());
        Rectangle bounds2 = label2.getBounds();
        System.out.println("BOUNDS ENEMIGOS: "+label2.getBounds());
        return bounds1.intersects(bounds2);
    }
    
    
    public String encounterWinner(String unit1, String unit2){
     
        if (unit1.equals(unit2)) {
            return "empate";
        } else if (
            (unit1.equals("horse") && unit2.equals("knight")) ||
            (unit1.equals("crossBow") && unit2.equals("horse")) ||
            (unit1.equals("knight") && unit2.equals("crossBow"))
        ) {
            return unit1;
        } else {
            return unit2;
        }
        
    } 
    
    
    public void towerDamage(int numTower){
        if(numTower == 1){
            this.tower1 -=1;
            if(tower1 ==0){
                finish=1;
            }
        }
        if(numTower == 2){
            this.tower2 -=1;
        }
        
        
        
        
        
    }
    
    
    public static void movementBotlane(JLabel label, int x, int y, boolean shouldRepeat) {
    System.out.println("MOVIENDOME BOT");

    int delay = 10; // intervalo de tiempo en milisegundos
    Timer timer = new Timer(delay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int posX = label.getX();
            int posY = label.getY();

            // Mover en X primero
            if (posX < x) {
                posX++;
            } else if (posX > x) {
                posX--;
            }

            // Luego mover en Y
            if (posY < y && posX == x) {
                posY+=5;
            } else if (posY > y && posX == x) {
                posY--;
            }

            label.setLocation(posX, posY);

            if (posX == x && posY == y) {
                ((Timer) e.getSource()).stop();
                if (shouldRepeat) {
                    // Restablecer las coordenadas a las originales para permitir el movimiento repetido
                    label.setLocation(label.getX(), label.getY());
                    ((Timer) e.getSource()).start();
                }
            }
        }
    });

    timer.start();
}

    
    public static void movementToplane(JLabel label, int x, int y, boolean shouldRepeat) {
    System.out.println("MOVIENDOME TOP");

    int delay = 10; // intervalo de tiempo en milisegundos
    Timer timer = new Timer(delay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int posX = label.getX();
            int posY = label.getY();

            // Mover en Y primero
            if (posY < y) {
                posY++;
            } else if (posY > y) {
                posY--;
            }

            // Luego mover en X
            if (posX < x && posY == y) {
                posX++;
            } else if (posX > x && posY == y) {
                posX--;
            }

            label.setLocation(posX, posY);

            if (posX == x && posY == y) {
                ((Timer) e.getSource()).stop();
                if (shouldRepeat) {
                    // Restablecer las coordenadas a las originales para permitir el movimiento repetido
                    label.setLocation(label.getX(), label.getY());
                    ((Timer) e.getSource()).start();
                }
            }
        }
    });

    timer.start();
}


}

    
    

