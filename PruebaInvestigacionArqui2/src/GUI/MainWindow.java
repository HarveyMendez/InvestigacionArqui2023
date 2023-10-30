/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author diego
 */
public class MainWindow extends JFrame{
    
    public MainWindow(){
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        init();
        
        
        
    }

    public void init(){
        
        JLabel jblStart = new JLabel("Start");
        jblStart.setBounds(400, 400, 100, 100);
        jblStart.setVisible(true);
        
        this.add(jblStart);
        
        
    }
    
    
}
