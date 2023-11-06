/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author diego
 */
public class MainWindow extends JFrame implements KeyListener{
// VARIABLES GLOBALES

    public PanamaHitek_Arduino arduino;
    String messageArduino = "";
    public int skin = 1;

    public MainWindow(int skin) {// CONSTRUCTOR
        setSize(810, 638);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        this.addKeyListener(this);
        arduino = new PanamaHitek_Arduino();
        this.skin = skin;
//        try {
//            setupArduino();
//        } catch (ArduinoException | InterruptedException | SerialPortException ex) {
//            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
        init();

    }

    public void init() {
        ImageIcon img = new ImageIcon(getClass().getResource("/img/HUD/menu.png"));
        JLabel jblStart = new JLabel(img);
        jblStart.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        jblStart.setVisible(true);

        this.add(jblStart);
    }

    private void handleArduinoEvent(String messageArduino) throws ArduinoException {// EN ESTE METODO SE EJECUTAN LAS ACCIONES SEGUN EL BOTON DE LA PROTOBOARD QUE SE PRESIONE
        // CABE RESALTAR QUE CADA ACCION ESTA DIVIDIDA EN SUS RESPECTIVOS METODOS PARA SU CLARIDAD
        switch (messageArduino) {
            case "Boton DERECHA presionado":// COMO JUGAR/TUTORIAL
                arduino.killArduinoConnection();
//                Tutorial tutorial = new Tutorial();
//                tutorial.setVisible(true);
                this.dispose();
                break;
            case "Boton ABAJO presionado":// ELEGIR SKIN/ASPECTO
                arduino.killArduinoConnection();
//                SkinSelection skin = new SkinSelection();
//                skin.setVisible(true);
                this.dispose();
                break;
            case "Boton IZQUIERDA presionado":// SALIR DEL JUEGO
                this.dispose();
//                DamageMessage msg = new DamageMessage(3);
//                msg.setVisible(true);
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });
                timer.setRepeats(false); // Se ejecuta solo una vez
                timer.start();
                break;
            case "Boton PAUSA presionado":// INICIAR JUEGO
                arduino.killArduinoConnection();// PRIMERO HAY QUE ELIMINAR LA CONEXION, DEBIDO A QUE UTILIZAREMOS OTRA PARA LA VENTANA QUE VAMOS A ABRIR

                JFrame frame = new JFrame("Mi Juego");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(815, 745);
                frame.setLocationRelativeTo(null);
                frame.setLayout(null);
                frame.add(new GameWindow(frame, this.skin));
                frame.setVisible(true);

                this.dispose();
                break;
        }

    }

    private void setupArduino() throws ArduinoException, InterruptedException, SerialPortException { // CONFIGURACION DEL ARDUINO
        arduino.arduinoRXTX("COM3", 9600, createSerialPortListener());// RECIBE Y ENVIA SEÑALES
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
        
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            JFrame frame = new JFrame("Mi Juego");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(815, 745);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.add(new GameWindow(frame, skin));
            frame.setVisible(true);

            this.dispose();

        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            Tutorial tutorial = new Tutorial(this);
            tutorial.setVisible(true);
            this.setVisible(false);
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
            this.dispose();
            System.exit(0);
        }

        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            SkinSelection skin = new SkinSelection();
            skin.setVisible(true);
            this.dispose();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
