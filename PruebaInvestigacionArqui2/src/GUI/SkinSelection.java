/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author jodas
 */
public class SkinSelection extends JFrame{
    
    public PanamaHitek_Arduino arduino;
    String messageArduino="";
    public static int Skin=1;

    SkinSelection(int i) {
        getSkin();
    }

    public int getSkin() {
        return Skin;
    }

    public void setSkin(int Skin) {
        this.Skin = Skin;
    }
    
    public SkinSelection(){
        
        setSize(810, 638);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new  PanamaHitek_Arduino();
        setupArduino();
        init();
    }

    public void init(){
        ImageIcon img = new ImageIcon(getClass().getResource("/img/HUD/skinSelection.png"));
        JLabel jblStart = new JLabel(img);
        jblStart.setBounds(0, 0, img.getIconWidth(),img.getIconHeight() );
        jblStart.setVisible(true);
        
        this.add(jblStart); 
    }
    
    

            
            private void handleArduinoEvent(String messageArduino) throws ArduinoException {// EN ESTE METODO SE EJECUTAN LAS ACCIONES SEGUN EL BOTON DE LA PROTOBOARD QUE SE PRESIONE
                                                            // CABE RESALTAR QUE CADA ACCION ESTA DIVIDIDA EN SUS RESPECTIVOS METODOS PARA SU CLARIDAD
        switch (messageArduino) {
            case "Boton DERECHA presionado":
                System.out.println("Medieval");
                setSkin(1);
                arduino.killArduinoConnection();
                MainWindow mw = new MainWindow();
                JOptionPane.showMessageDialog(null, "You have selected MEDIEVAL skin", "Back to menu", JOptionPane.INFORMATION_MESSAGE);
                mw.setVisible(true);
                this.setVisible(false);
            break;
            case "Boton ARRIBA presionado":
                System.out.println("Magic");
                setSkin(2);
                arduino.killArduinoConnection();
                JOptionPane.showMessageDialog(null, "You have selected MAGIC skin", "Back to menu", JOptionPane.INFORMATION_MESSAGE);
                MainWindow mw2 = new MainWindow();
                mw2.setVisible(true);
                this.setVisible(false);
            break;
            case "Boton ABAJO presionado":
                System.out.println("League");
                setSkin(3);
                arduino.killArduinoConnection();
                JOptionPane.showMessageDialog(null, "You have selected LEAGUE OF LEGENDS skin", "Back to menu", JOptionPane.INFORMATION_MESSAGE);
                MainWindow mw3 = new MainWindow();
                mw3.setVisible(true);
                this.setVisible(false);
            break;
            case "Boton IZQUIERDA presionado":
                arduino.killArduinoConnection();
                MainWindow mw4 = new MainWindow();
                mw4.setVisible(true);
                this.setVisible(false);
            break;
        }
                System.out.println("NUMERO: "+this.Skin);
    }

    private void setupArduino()  { // CONFIGURACION DEL ARDUINO
      try{
          arduino.arduinoRX("COM3", 9600, createSerialPortListener());
      }catch(Exception e){
//          System.out.println("ERROR");
      }
        //Thread.sleep(2000);// ESPERA A QUE EL PUERTO ESTE LISTO PARA ENVIAR Y RECIBIR DATOS
    }
    
    
                private SerialPortEventListener createSerialPortListener() {// ESTE ES UN METODO QUE UTILIZAMOS PARA QUE EL PROGRAMA ESCUCHE SI SE PRESIONA UN BOTON DESDE LA PROTOBOARD
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

    

