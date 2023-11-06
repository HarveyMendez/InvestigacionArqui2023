/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author jodas
 */
public class SkinSelection extends JFrame implements KeyListener {
// -------------------- VARIABLES GLOBALES ---------------------

    public PanamaHitek_Arduino arduino;
    String messageArduino = "";
    public static int Skin = 1;
// -------------------------------------------------------------

    SkinSelection(int i) {// CONSTRUCTOR
        getSkin();// LLAMA AL METODO
    }

    public int getSkin() {// GETTER
        return Skin;
    }

    public void setSkin(int Skin) {// SETTER
        this.Skin = Skin;
    }

    public SkinSelection() {// CONSTRUCTOR
        this.addKeyListener(this);
        setSize(810, 638);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        arduino = new PanamaHitek_Arduino();
        setupArduino();
        init();
    }

    public void init() {// INICIALIZA COMPONENTES
        ImageIcon img = new ImageIcon(getClass().getResource("/img/HUD/skinSelection.png"));
        JLabel jblStart = new JLabel(img);
        jblStart.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        jblStart.setVisible(true);

        this.add(jblStart);
    }

    private void handleArduinoEvent(String messageArduino) throws ArduinoException {// EN ESTE METODO SE EJECUTAN LAS ACCIONES SEGUN EL BOTON DE LA PROTOBOARD QUE SE PRESIONE
        // CABE RESALTAR QUE CADA ACCION ESTA DIVIDIDA EN SUS RESPECTIVOS METODOS PARA SU CLARIDAD
        switch (messageArduino) {
            case "Boton DERECHA presionado":// SKIN MEDIEVAL
                setSkin(1);
                arduino.killArduinoConnection();// ELIMINA LA CONEXION CON EL ARDUINO PORQUE VAMOS A CAMBIAR DE VENTANA
                SkinMessage sm1 = new SkinMessage(1);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
                sm1.setVisible(true);
                this.dispose();
                break;
            case "Boton ARRIBA presionado":// SKIN MAGIA
                setSkin(2);
                arduino.killArduinoConnection();
                SkinMessage sm2 = new SkinMessage(2);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
                sm2.setVisible(true);
                this.dispose();
                break;
            case "Boton ABAJO presionado":// SKIN LEAGUE
                setSkin(3);
                arduino.killArduinoConnection();
                SkinMessage sm3 = new SkinMessage(3);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
                sm3.setVisible(true);
                this.dispose();
                break;
            case "Boton IZQUIERDA presionado": // VOLVER SIN CAMBIAR SKIN SI NO SE ELIGE NINGUNA SKIN, POR DEFECTO SE UTILIZA MEDIEVAL
                arduino.killArduinoConnection();
                MainWindow mw4 = new MainWindow(this.Skin);
                mw4.setVisible(true);
                this.setVisible(false);
                break;
        }
    }

    private void setupArduino() { // CONFIGURACION DEL ARDUINO
        try {
            arduino.arduinoRX("COM3", 9600, createSerialPortListener());
        } catch (Exception e) {

        }

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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            SkinMessage sm1 = new SkinMessage(1);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
            sm1.setVisible(true);
            this.dispose();
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            SkinMessage sm2 = new SkinMessage(2);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
            sm2.setVisible(true);
            this.dispose();
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            SkinMessage sm3 = new SkinMessage(3);// ENVIAMOS UN 1 DE PARAMETRO PARA MOSTRAR UNA VENTANA
            sm3.setVisible(true);
            this.dispose();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
