/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public interface ArduinoListener {
    
    PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    String messageArduino="";
    
    SerialPortEventListener listener = new SerialPortEventListener() {
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

        try {

            if(arduino.isMessageAvailable()==true){
//               messageArduino=arduino.printMessage(); 

                 //JOptionPane.showMessageDialog(null, arduino.printMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    if (messageArduino.equals("Boton DERECHA presionado")) {
//                        System.out.println("Derecha");
//                        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/knightGame.png"));
//                        JLabel label = new JLabel(imagen);
//
//                        JLayeredPane layeredPane = getLayeredPane();
//                        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
//                            // Establecer las coordenadas específicas (x, y) donde quieres mostrar el JLabel
//                        int x = 120; // reemplaza con la coordenada X deseada
//                        int y = 550; // reemplaza con la coordenada Y deseada
//                        label.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
//                        setVisible(true);
                    }

                    if(messageArduino.equals("Boton ARRIBA presionado")){
//                        System.out.println("Arriba");
//                        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/crossbow.png"));
//                        JLabel label = new JLabel(imagen);
//
//                        JLayeredPane layeredPane = getLayeredPane();
//                        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
//                        label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());
//
//                        setVisible(true);
                    }

                    if(messageArduino.equals("Boton ABAJO presionado")){
//                        System.out.println("Abajo");
//                        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/crossbowpeque.png"));
//                        JLabel label = new JLabel(imagen);
//
//                        JLayeredPane layeredPane = getLayeredPane();
//                        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
//                        label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());
//
//                        setVisible(true);
                    }

                    if(messageArduino.equals("Boton IZQUIERDA presionado")){
//                        System.out.println("Izquierda");
//                        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/horsepeque.png"));
//                        JLabel label = new JLabel(imagen);
//
//                        JLayeredPane layeredPane = getLayeredPane();
//                        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
//                        label.setBounds(0, 0, imagen.getIconWidth(), imagen.getIconHeight());
//
//                        setVisible(true);
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
