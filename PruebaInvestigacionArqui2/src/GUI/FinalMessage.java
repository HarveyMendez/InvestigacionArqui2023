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
public class FinalMessage extends JFrame {

    private int messageType;

    public FinalMessage(int messageType) {
        this.messageType = messageType;
        setSize(600, 200);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(null);
        

        
        Timer timer = new Timer(5000, new ActionListener() {// ESPERA 5s ANTES DE CERRAR LA VENTANA, PARA DAR TIEMPO A QUE LA DESCONEXION CON EL ARDUINO SE EFECTUE
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        dispose();
                        MainWindow mw = new MainWindow(1);
                        mw.setVisible(true);
                    }
                });
            }
        });
        timer.setRepeats(false); // Se ejecuta solo una vez
        timer.start();
        init();
    }

    public void init() {// DEPENDIENDO DEL TIPO DE MENSAJE QUE RECIBA VA A MOSTRAR UN MENSAJE EN UNA VENTANA EMERGENTE
        ImageIcon img;
    
        switch (messageType) {
            case 1:// VICTORIA
                img = new ImageIcon(getClass().getResource("/img/HUD/Victory.png"));
                break;
            case 2:// DERROTA
                img = new ImageIcon(getClass().getResource("/img/HUD/defeat.png"));
                break;
            default:// VOLVER AL MENU PRINCIPAL
                img = new ImageIcon(getClass().getResource("/img/HUD/default.png"));
                break;
        }

        JLabel jblStart = new JLabel(img);
        jblStart.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        jblStart.setVisible(true);

        add(jblStart);
    }
}
