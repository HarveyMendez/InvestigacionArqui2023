/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author diego
 */
public class TimerSecond {
    
    private int start;
    private int interval;
    private int counter;
    private Timer timer = new Timer();

    public TimerSecond(int start, int interval) {

        counter = start;
        
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (counter == -1) {
                    timer.cancel();
                }
                counter--;
            }
        };

        timer.scheduleAtFixedRate(tt, 0, interval);
    }

    public int getCounter(){
        return counter;
    }
    
    
}
