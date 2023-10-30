/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Game;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jssc.SerialPortEvent;

/**
 *
 * @author diego
 */
public class GameWindow extends JFrame{
    
    public GameWindow(){
        Game newGame = new Game(3, 3);
        setSize(815, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        init();
        
    }

    public void init(){
        
        //Tipós de Unidades
        ImageIcon imgKnight = new ImageIcon(getClass().getResource("/img/knightpeque.png"));
        JLabel knight = new JLabel(imgKnight);
        knight.setBounds(90, 610, 100, 100);
        this.add(knight);
        
        ImageIcon imgHorse = new ImageIcon(getClass().getResource("/img/horsepeque.png"));
        JLabel horse = new JLabel(imgHorse);
        horse.setBounds(350, 610, 100, 100);
        this.add(horse);
        
        ImageIcon imgCrossBow = new ImageIcon(getClass().getResource("/img/crossbowpeque.png"));
        JLabel crossBow = new JLabel(imgCrossBow);
        crossBow.setBounds(610, 620, 100, 100);
        this.add(crossBow);
        
        
        
        
        //Mapa y hub
        ImageIcon imgHub = new ImageIcon(getClass().getResource("/img/Hud1.png"));
        JLabel hub = new JLabel(imgHub);
        hub.setBounds(0,590, 800, 120);
        this.add(hub);
        
        
        ImageIcon imgMap = new ImageIcon(getClass().getResource("/img/Map1.png"));
        JLabel map = new JLabel(imgMap);
        map.setBounds(0, 0, 800, 600);
        this.add(map);
    }
    
     public void serialEvent(SerialPortEvent serialPortEvent) {
         

     }
    
    
}
