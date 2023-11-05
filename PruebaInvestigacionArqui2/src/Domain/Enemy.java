/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author jodas
 */
public class Enemy {
    // ATRIBUTOS DE LA CLASE
    private JLabel label;
    private String unitName;
    private int type;
    private ImageIcon image;
    private Timer collisionTimer;

    public Enemy() {// CONSTRUCTOR VACIO

    }
    
    // GETTERS Y SETTERS
    public Timer getCollisionTimer() {
        return collisionTimer;
    }

    public void setCollisionTimer(Timer collisionTimer) {
        this.collisionTimer = collisionTimer;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public Enemy(JLabel label, String unitName, int type, ImageIcon image) {
        this.label = label;
        this.unitName = unitName;
        this.type = type;
        this.image = image;
    }

    public Enemy(JLabel label, String unitName, int type) {
        this.label = label;
        this.unitName = unitName;
        this.type = type;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
