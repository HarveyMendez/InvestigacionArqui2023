/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;


/**
 *
 * @author jodas
 */
public class Unit {

    private int x;
    private int y;
    private Image image;
    private final int type;
    private final int line;

    public Unit(int x, int y, int type, int line, int skin) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.line = line;

        if (this.type == 1) {
            if (skin == 1) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/knightGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 2) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/knightGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 3) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/knightGame.png"));
                this.image = icon.getImage();
            }

        }
        if (this.type == 2) {
            if (skin == 1) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/crossbowGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 2) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/crossbowGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 3) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/crossbowGame.png"));
                this.image = icon.getImage();
            }

        }
        if (this.type == 3) {
            if (skin == 1) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS1/horseGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 2) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS2/horseGame.png"));
                this.image = icon.getImage();
            }
            if (skin == 3) {
                ImageIcon icon = new ImageIcon(getClass().getResource("/img/UNITS/UNITS3/horseGame.png"));
                this.image = icon.getImage();
            }

        }

    }

    public void move() {

        int posX = this.x;
        int posY = this.y;

        if (line == 1) { // se mueve para arriba


            if (posY > 10) {
                posY--;
            } else if (posX < 745 && posY == 10) {
                posX++;
            }

            this.x = posX;
            this.y = posY;

        }
        if (line == 2) { // se mueve para abajo


            if (posX < 745) {
                posX++;
            } else if (posY > 10 && posX == 745) {
                posY--;
            }

            this.x = posX;
            this.y = posY;

        }

        //LINEAS INVERSAS, IA
        if (line == 3) {

            if (posX > 10) {
                posX--;
            } else if (posY < 540 && posX == 10) {
                posY++;
            }

            this.x = posX;
            this.y = posY;
        }

        if (line == 4) {

            if (posY < 540) {
                posY++;

            } else if (posX > 10 && posY == 540) {
                posX--;

            }

            this.y = posY;
            this.x = posX;
     

        }

    }

    public boolean collision(Unit otherUnit) {
        return x < otherUnit.x + 50 && x + 50 > otherUnit.x && y < otherUnit.y + 50 && y + 50 > otherUnit.y; 
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 50, 50, null);
    }

    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getType() {
        return this.type;
    }
    
    
    
    
}
