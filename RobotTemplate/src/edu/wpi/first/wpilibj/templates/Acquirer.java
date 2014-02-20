package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;

/**
 * @author Spaceguy44 (Adam)
 * ElToro style ball collector
 */
public class Acquirer {

    private Jaguar vandoor;
    private Talon acquirer1;
    private Talon acquirer2;
    
    public Acquirer(int vanDoor, int acquirer_1, int acquirer_2) {
        vandoor = new Jaguar(vanDoor);
        acquirer1 = new Talon(acquirer_1);
        acquirer2 = new Talon(acquirer_2);
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
