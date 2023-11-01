/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author jodas
 */
public class Unit {
    
    private JLabel label;
    private String unitName;
    private int type;
    private ImageIcon image;

    public Unit(JLabel label, String unitName, int type) {
        this.label = label;
        this.unitName = unitName;
        this.type = type;
    }

    public Unit(JLabel label, String unitName, int type, ImageIcon image) {
        this.label = label;
        this.unitName = unitName;
        this.type = type;
        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
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
