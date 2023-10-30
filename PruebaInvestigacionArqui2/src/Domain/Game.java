/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author diego
 */
public class Game {
    
    private int tower1;
    private int tower2;
    private int finish = 0;
            
            
    public Game(int tower1, int tower2){
        
        this.tower1 = tower1;
        this.tower2 = tower2;     
        
        
        
    }
    
    
    public String encounterWinner(String unit1, String unit2){
     
        if (unit1.equals(unit2)) {
            return "empate";
        } else if (
            (unit1.equals("horse") && unit2.equals("knight")) ||
            (unit1.equals("crossBow") && unit2.equals("horse")) ||
            (unit1.equals("knight") && unit2.equals("crossBow"))
        ) {
            return unit1;
        } else {
            return unit2;
        }
        
    } 
    
    
    public void towerDamage(int numTower){
        if(numTower == 1){
            this.tower1 -=1;
            if(tower1 ==0){
                finish=1;
            }
        }
        if(numTower == 2){
            this.tower2 -=1;
        }
        
        
        
        
        
    }
    
    
}
