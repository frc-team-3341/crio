/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;
import org.wvrobotics.util.Robot;

/**
 *
 * @author Spaceguy44 (Adam)
 */
public class ElToro {

    private Jaguar vandoor;
    private Jaguar acquirer1;
    private Jaguar acquirer2;
    
    public ElToro(int vanDoor, int acquirer_1, int acquirer_2) {
        vandoor = new Jaguar(vanDoor);
        acquirer1 = new Jaguar(acquirer_1);
        acquirer2 = new Jaguar(acquirer_2);
    }
    
    public void pitch_up () {
        vandoor.set(1.0);  
    }
    
    public void pitch_down () {
        vandoor.set(-1.0);
    }
    
    public void pitch_stop() {
        vandoor.set(0.0);
    }
    
    public void collect() {
        acquirer1.set(1.0);
        acquirer2.set(-1.0);
    }
    
    public void dump() {
        acquirer1.set(-1.0);
        acquirer2.set(1.0);
    }
    
    public void acquirer_stop() {
        acquirer1.set(0.0);
        acquirer2.set(0.0);
    }
}
