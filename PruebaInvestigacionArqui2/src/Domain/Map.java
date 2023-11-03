/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author diego
 */
public class Map {
   
    private Image image;

    public Map() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/HUD/HUD1/Map1.png"));
        this.image = icon.getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(image, 0, 0, 800, 600, null);
        g.drawOval(40, 560, 10, 10);
    }

}
