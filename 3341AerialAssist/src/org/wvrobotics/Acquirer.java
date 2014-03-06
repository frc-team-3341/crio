package org.wvrobotics;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;

/**
 * @author Spaceguy44 (Adam Tedeschi)
 */
public class Acquirer {

    private Jaguar vandoor;
    private Talon acquirer_left;
    private Talon acquirer_right;
    
    /**
     * Initializes the motors of the acquirer system.
     * @param vanDoor The PWM port of the Van Door motor.
     * @param acquirer_left The PWM port of the left acquirer motor.
     * @param acquirer_right The PWM port of the right acquirer motor
     */
    public Acquirer(int vanDoor, int acquirer_left, int acquirer_right) {
        vandoor = new Jaguar(vanDoor);
        this.acquirer_left = new Talon(acquirer_left);
        this.acquirer_right = new Talon(acquirer_right);
    }
    
    /**
     * Moves the acquirer towards the robot.
     */
    public void pitch_up () {
        vandoor.set(0.2);  
    }
    
    /**
     * Moves the acquirer away from the robot.
     */
    public void pitch_down () {
        vandoor.set(-0.2);
    }
    
    /**
     * Stops the Van Door motor from moving completely.
     */
    public void pitch_stop() {
        vandoor.set(0.0);
    }
    
    /**
     * Moves the acquirer motors in opposite directions to collect the ball.
     */
    public void collect() {
        acquirer_left.set(0.5);
        acquirer_right.set(0.5);
    }
    
    /**
     * Moves the acquirer motors in opposite directions to unload the ball.
     */
    public void dump() {
        acquirer_left.set(0.5);
        acquirer_right.set(0.5);
    }
    
    /**
     * Stops the acquirer motors from moving completely.
     */
    public void acquirer_stop() {
        acquirer_left.set(0.0);
        acquirer_right.set(0.0);
    }    
}
