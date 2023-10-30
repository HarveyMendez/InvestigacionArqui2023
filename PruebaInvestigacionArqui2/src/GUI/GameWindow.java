/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import Domain.Game;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
/**
 *
 * @author diego
 */
public class GameWindow extends javax.swing.JFrame{
    
    PanamaHitek_Arduino arduino;
     String messageArduino="";
    
    public GameWindow(){
        Game newGame = new Game(3, 3);
        setSize(815, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new PanamaHitek_Arduino();
        try {
            arduino.arduinoRX("COM3", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        init();
        
    }

    public void init(){
        
        //Tipós de Unidades
        ImageIcon imgKnight = new ImageIcon(getClass().getResource("/img/knightpeque.png"));
        JLabel knight = new JLabel(imgKnight);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);
        
        ImageIcon imgHorse = new ImageIcon(getClass().getResource("/img/horsepeque.png"));
        JLabel horse = new JLabel(imgHorse);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);
        
        ImageIcon imgCrossBow = new ImageIcon(getClass().getResource("/img/crossbowpeque.png"));
        JLabel crossBow = new JLabel(imgCrossBow);
        crossBow.setBounds(610, 600, 100, 100);
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
    
   
   
    
    SerialPortEventListener listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                
                try {
                    
                    if(arduino.isMessageAvailable()==true){
                       messageArduino=arduino.printMessage(); 
                         
                         //JOptionPane.showMessageDialog(null, arduino.printMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                            if (messageArduino.equals("Boton DERECHA presionado")) {
                                System.out.println("Derecha");
                                ImageIcon imagen = new ImageIcon(getClass().getResource("/img/knightGame.png"));
                                JLabel label = new JLabel(imagen);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
                                    // Establecer las coordenadas específicas (x, y) donde quieres mostrar el JLabel
                                int x = 120; // reemplaza con la coordenada X deseada
                                int y = 540; // reemplaza con la coordenada Y deseada
                                label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                setVisible(true);
                            }
                        
                            if(messageArduino.equals("Boton ARRIBA presionado")){
                                System.out.println("Arriba");
                                ImageIcon imagen = new ImageIcon(getClass().getResource("/img/crossbow.png"));
                                JLabel label = new JLabel(imagen);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
                                label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());

                                setVisible(true);
                            }

                            if(messageArduino.equals("Boton ABAJO presionado")){
                                System.out.println("Abajo");
                                ImageIcon imagen = new ImageIcon(getClass().getResource("/img/crossbowpeque.png"));
                                JLabel label = new JLabel(imagen);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
                                label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());

                                setVisible(true);
                            }

                            if(messageArduino.equals("Boton IZQUIERDA presionado")){
                                System.out.println("Izquierda");
                                ImageIcon imagen = new ImageIcon(getClass().getResource("/img/horsepeque.png"));
                                JLabel label = new JLabel(imagen);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
                                label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());

                                setVisible(true);
                            }
                            
                         }
                       
                } catch (SerialPortException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArduinoException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
}
         
     
    
    

