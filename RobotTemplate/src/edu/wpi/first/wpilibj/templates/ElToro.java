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
    private Jaguar acquirer;
    
    public ElToro(int VAN_DOOR_PORT, int ACQUIRER_PORT) {
        vandoor = new Jaguar(VAN_DOOR_PORT);
        acquirer = new Jaguar(ACQUIRER_PORT);
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
    
}
