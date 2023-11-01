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
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
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
    
    public PanamaHitek_Arduino arduino;
    String messageArduino="";
    Game game;
    private boolean paused = false;
    private Timer collisionTimer; 
    
    public ArrayList<Unit> unitList = new ArrayList<>();
    public ArrayList<Unit> enemyUnitList = new ArrayList<>();

     
     
    public boolean isPaused() {
        return paused;
    }
    


private final Object pauseLock = new Object();

    public void togglePause() {
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






    
    public GameWindow(){
       
        setSize(815, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new PanamaHitek_Arduino();
        
        game = new Game(2, 2, arduino);
        
        try {
            arduino.arduinoRXTX("COM3", 9600, listener);
            Thread.sleep(2000);// ESPERA A QUE EL PUERTO SE ABRA PARA ENVIAR LOS DATOS Y ENCENDER LO LEDS
            try {
                arduino.sendData("1");// ENCENDER LED ARDUINO
                arduino.sendData("2");// ENCENDER LED ARDUINO
                arduino.sendData("3");// ENCENDER LED ARDUINO
                arduino.sendData("4");// ENCENDER LED ARDUINO
            } catch (SerialPortException ex) {
                Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            arduino.sendData("5");// APAGAR LED ARDUINO
//            arduino.sendData("6");// APAGAR LED ARDUINO
//            arduino.sendData("7");// APAGAR LED ARDUINO
//            arduino.sendData("8");// APAGAR LED ARDUINO
        } catch (ArduinoException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }          
          init();
    }

    
    ImageIcon imgEnemy = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGameEnemy.png"));
    public JLabel enemyLabel = new JLabel(imgEnemy);
    public Unit enemyUnit = new Unit();
   
    public  ImageIcon imgSelection = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Selection.png"));
    public  JLabel selection = new JLabel(imgSelection);
    
    public void init(){
        
        setLayout(null);
        
        int x = 600; // reemplaza con la coordenada X deseada
        int y = 540; // reemplaza con la coordenada Y deseada
        enemyLabel.setBounds(x, y,imgEnemy.getIconWidth(),imgEnemy.getIconHeight());
        Border border = new LineBorder(Color.BLACK, 2);
        enemyLabel.setBorder(border);
        enemyUnit = new Unit(enemyLabel, SelectedUnit, 1);
         
                                
                                 // Agregar JLabel al ArrayList
                                enemyUnitList.add(enemyUnit);
        
        this.add(enemyUnit.getLabel());
                selection.setBounds(425 , 590, 100, 100);
                
                                
                                
                JLabel fake = new JLabel();
                fake.setBounds(170 , 590, 100, 100);
                game.Selection(fake, messageArduino);
        this.add(selection);
        
        //Tipós de Unidades
        ImageIcon imgKnight = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightpeque.png"));
        JLabel knight = new JLabel(imgKnight);
        knight.setBounds(90, 600, 100, 100);
        this.add(knight);
        
        ImageIcon imgHorse = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horsepeque.png"));
        JLabel horse = new JLabel(imgHorse);
        horse.setBounds(350, 600, 100, 100);
        this.add(horse);
        
        ImageIcon imgCrossBow = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowpeque.png"));
        JLabel crossBow = new JLabel(imgCrossBow);
        crossBow.setBounds(610, 600, 100, 100);
        this.add(crossBow);
        
        //Mapa y hub
        ImageIcon imgHub = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Hud1.png"));
        JLabel hub = new JLabel(imgHub);
        hub.setBounds(0,590, 800, 120);
        this.add(hub);
        
        
        ImageIcon imgMap = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Map1.png"));
        JLabel map = new JLabel(imgMap);
        map.setBounds(0, 0, 800, 600);
        this.add(map);
        
        
        
        
    }
    
    
     private void createAndMoveLabel() {
                                
                                
        // Iniciar el Timer para verificar colisiones cada 10 milisegundos
         
        if(game.stop==0)
        collisionTimer.start();
    }
   
   String SelectedUnit;
   
    SerialPortEventListener listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                
                try {
                    
                    if(arduino.isMessageAvailable()==true){
                      
                    SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        
                        try { 
                            messageArduino=arduino.printMessage();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ArduinoException ex) {
                            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                         
                         //JOptionPane.showMessageDialog(null, arduino.printMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                            if (messageArduino.equals("Boton DERECHA presionado")) {
                                System.out.println("MUEVO CURSOR A LA DERECHA");
                                int position= game.Selection(selection, "DERECHA");
                                selection.setBounds(position , 590, 100, 100);
                                
                            }
                        
                            if(messageArduino.equals("Boton ARRIBA presionado")){
                                System.out.println("PRESIONE ARRIBA");
                                
                                ImageIcon imagen = new ImageIcon();
                                SelectedUnit = game.UnitSelected(game.getSelection());
                                imagen = game.getImageIcon(SelectedUnit);
                                
                                JLabel label = new JLabel(imagen);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
                                    // Establecer las coordenadas específicas (x, y) donde quieres mostrar el JLabel
                                int x = 10; // reemplaza con la coordenada X deseada
                                int y = 440; // reemplaza con la coordenada Y deseada
                                label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
                                
                                
                                // PROBAR COLISIONES
                                Border border = new LineBorder(Color.BLACK, 2);
                                label.setBorder(border);
                                // -----------------------------------------
                                
                                game.movementToplane(label, 745, 10);
                                setVisible(true);
                            }

                            if (messageArduino.equals("Boton ABAJO presionado")) {
                                System.out.println("PRESIONE ABAJO");
                                
                                
                                
                               
                                
                                ImageIcon imagen = new ImageIcon();
                                SelectedUnit = game.UnitSelected(game.getSelection());
                                imagen = game.getImageIcon(SelectedUnit);
                                
                                JLabel  label = new JLabel(imagen);
                                
                                // TODO 
                                // manejar los tipos, si es 1 MEDIEVAL, SI ES 2 MAGIA, SI ES 3 MINIONS
                                
                                Unit unit = new Unit(label, SelectedUnit, 1);
                                
                                 // Agregar JLabel al ArrayList
                                unitList.add(unit);

                                JLayeredPane layeredPane = getLayeredPane();
                                layeredPane.add(unit.getLabel(), JLayeredPane.DEFAULT_LAYER);
                                
                                
                                // Establecer las coordenadas específicas (x, y) donde quieres mostrar el JLabel
                                int x = 110; // reemplaza con la coordenada X deseada
                                int y = 540; // reemplaza con la coordenada Y deseada
                                label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());

                               

                                game.movementBotlane(label, 745, 10);
                                
                                
                                 // -------------------------------------------- PROBAR COLISIONES ----------------------------------------------------------------------------------
                                Border border = new LineBorder(Color.BLACK, 2);
                                label.setBorder(border);
                                
                                collisionTimer = new Timer(10, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // Verificar colisión entre label y enemyLabel
                                        boolean colision = game.checkCollision(label, enemyLabel);
                                        if (colision) {
                                           String unitName= game.encounterWinner();
                                            System.out.println("Hay colisión entre las JLabel.");
                                            int winner=game.win;
                                            System.out.println("GANADOR DEL ENCUENTRO: "+unitName+" Numero: "+winner);
                                           // System.out.println("GANADOR DEL ENCUENTRO: "+game.encounterWinner()+" Numero: "+winner);
                                            switch(winner) {
                                                case 0:
//                                                    label.setVisible(false);
                                                    enemyLabel.setVisible(false);
                                                    layeredPane.remove(unit.getLabel());
                                                    layeredPane.remove(enemyUnit.getLabel());
                                                    
                                                    // Eliminar JLabel del ArrayList y del JLayeredPane
                                                    unitList.remove(unit);
                                                    enemyUnitList.remove(enemyUnit);
                                                    
                                                   
                                                    break;
                                                case 2:
                                                    enemyLabel.setVisible(false);
                                                    layeredPane.remove(enemyLabel);
                                                    layeredPane.remove(enemyUnit.getLabel());
                                                    enemyUnitList.remove(enemyUnit);
                                                    
                                                    
                                                    break;
                                                case 1:
                                                    label.setVisible(false);
                                                    layeredPane.remove(label);
                                                    layeredPane.remove(unit.getLabel());
                                                    unitList.remove(unit);
                                                    break;
                                            }
                                            //game.stop=1;
                                            revalidate();
                                            repaint();        
                                            collisionTimer.stop();
                                            
                                            System.gc();
                                           //  JOptionPane.showMessageDialog(null, "GANADOR DEL ENCUENTRO: "+game.encounterWinner()+" Numero: "+winner, "Información", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            game.stop=0;
                                        }
                                        
                                    }
                                });
                                // ---------------------------------------------------------------------------------------------------------------------------------------------------------
                                createAndMoveLabel();
                                setVisible(true);
                            }


                            if(messageArduino.equals("Boton IZQUIERDA presionado")){
                                System.out.println("MUEVO EL CURSOR A LA IZQUIERDA");
                                int position= game.Selection(selection, "IZQUIERDA");
                                selection.setBounds(position , 590, 100, 100);
//                                
                            }
                        
                        
                        
                        
                            if (messageArduino.equals("Boton PAUSA presionado")) {
                                System.out.println("PAUSAAAAAAAAAAA");
                                togglePause(); // Activa o desactiva la pausa
                            }

                    }
                });
                            
                         }
                       
                } catch (SerialPortException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ArduinoException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
}
         
     
    
    

