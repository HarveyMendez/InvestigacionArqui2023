/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author jodas
 */
public class SkinMessage extends JFrame {
// VARIABLES GLOBALES
    private int messageType;

    public SkinMessage(int messageType) {// CONSTRUCTOR
        this.messageType = messageType;
        setSize(600, 200);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(null);
        init();

        
        Timer timer = new Timer(5000, new ActionListener() {// ESPERAR 5s PARA CERRAR LA VENTANA Y CERRAR CORRECTAMENTE LA CONEXION CON EL ARDUINO
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        dispose();
                        MainWindow mw = new MainWindow(messageType);
                        mw.setVisible(true);
                    }
                });
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void init() {// DEPENDIENDO DE EL MENSAJE QUE RECIBA UTILIZARA UNA MENSAJE COMO VENTANA EMERGENTE U OTRO
        ImageIcon img;
        switch (messageType) {
            case 1:
                img = new ImageIcon(getClass().getResource("/img/HUD/skin1.png"));
                break;
            case 2:
                img = new ImageIcon(getClass().getResource("/img/HUD/skin2.png"));
                break;
            case 3:
                img = new ImageIcon(getClass().getResource("/img/HUD/skin3.png"));
                break;
            default:
                img = new ImageIcon(getClass().getResource("/img/HUD/skin3.png"));
                break;
        }

        JLabel jblStart = new JLabel(img);
        jblStart.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        jblStart.setVisible(true);

        add(jblStart);
    }
}
