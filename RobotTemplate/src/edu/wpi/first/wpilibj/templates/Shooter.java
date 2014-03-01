package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Jaguar;
import org.wvrobotics.control.Controller;

/**
 * 
 * @author George "Agent 10" Troulis
 * @author Prathyush Katukojwala
 *  
 */
public class Shooter {

    private static final int shooterAbsoluteMaxPosition = 200;
    private static int shooterMaxPosition = 200;//Will be modified later
    private static final int shooterMinPosition = 50;//Will be modified later

    //potentiometer
    private AnalogPotentiometer pot;

    private Jaguar motor_1;
    private Jaguar motor_2;
    private int state; // 1 forward, 0 stopped, -1 reverse

    private double potVal;
    /**
     * 
     * @param _motor_1 PWM Port where motor 1 is connected
     * @param _motor_2 PWM Port where motor 2 is connected
     */
    public Shooter(int _motor_1, int _motor_2) {
        motor_1 = new Jaguar(_motor_1);
        motor_2 = new Jaguar(_motor_2);
        pot = new AnalogPotentiometer(4,54);
        state = 0;
        this.getPotVal();
    }

    /**
     * Moves both motors forward at top speed if there is room for movement.
     */
    public void shoot() {
        if (potVal < shooterMaxPosition) {
            state = 1;
            motor_1.set(1.0);
            motor_2.set(1.0);
        } else {
            this.stop();
        }
    }

    /**
     * Moves both motors backwards slowly if there is room for movement.
     */
    public void reset() {
        if (potVal > shooterMinPosition) {
            state = -1;
            motor_1.set(-0.2);
            motor_2.set(-0.2);
        } else {
            this.stop();
        }
    }
    
    /**
     * Stops the motors from moving completely.
     */
    public void stop() {
        state = 0;
        motor_1.set(0);
        motor_2.set(0);
    }
    
    /**
     * Gets the state of the motors.
     * 
     * @return Returns the state of the catapult motors. 
     *  1 = forward
     *  0 = stopped
     *  -1 = backwards
     */
    public int getState(){
        return state;
    }

    /**
     * Gets the value of the potentiometer.
     * 
     * @return The value of the potentiometer.
     */
    public double getPotVal() {
        return potVal;
    }
    
    /**
     * Adjusts the max amount that the catapult can be pulled back by a set amount.
     * @param amount The amount by which to set the max position.
     */
    public void setMax(int amount) {
        shooterMaxPosition = amount;
    }
    
    /**
     * Adjusts the max amount that the catapult can be pulled back based on the throttle(slider) of a joystick.
     * @param c The controller to extract the throttle amount from.
     */
    public void adjustMax(Controller c) {
        double rawVal = c.getThrottle();
        int processedVal = (int) ((rawVal + 1) * 25); //
        shooterMaxPosition = shooterAbsoluteMaxPosition - processedVal;
    }

    /**
     * Updates the value of the potentiometer and stops the motors if necessary.
     */
    public void tick() {
        this.potVal = pot.get();
        
        if(state == 1){
            if(potVal >= shooterMaxPosition){
                stop();
                state = 0;
            }
        }
        else if(state == -1){
            if(potVal <= shooterMinPosition){
                stop();
                state = 0;
            }
        }
         //To change body of generated methods, choose Tools | Templates.
    }
}