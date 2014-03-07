package org.wvrobotics;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author George "Agent 10" Troulis
 * @author Prathyush Katukojwala
 *  
 */
public class Shooter {

    public static int shooterMaxPosition = 110;//Will be modified later
    private static final int shooterMinPosition = 6;//Will be modified later
    
    //height where the motor switches from slow to fast speed
    public int prepareHeight = 10;
    //potentiometer
    private AnalogPotentiometer pot;

    private Jaguar motor_1;
    private Jaguar motor_2;
    private int state; // 1 forward, 0 stopped, -1 reverse
    private long us_startTime;  // microseconds: millionth of a sec.

    private double potVal = 100;
    public double speed = 1.0;
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
        us_startTime = 0;
        this.getPotVal();
    }

    /**
     * Moves both motors forward at top speed if there is room for movement.
     */
    public void shoot() {
        
        if (potVal < shooterMaxPosition) {
            state = 1;
            if(speed == 1.0) {
                if(potVal < prepareHeight) {
                    motor_1.set(0.3);
                    motor_2.set(0.3);
                }
                else {
                    motor_1.set(1.0);
                    motor_2.set(1.0);
                }
                
            }
        } else {
            this.stop();
        }
        
        us_startTime = Timer.getUsClock();
    }

    /**
     * Moves both motors backwards slowly if there is room for movement.
     */
    public void reset() {
        if (potVal > shooterMinPosition) {
            state = -1;
            motor_1.set(-0.1);
            motor_2.set(-0.1);
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
    
    public void tick() {
        this.potVal = pot.get();
        
        /*System.out.println( "pot/state/start/now: " + potVal
                            + " " + state
                            + " " + us_startTime
                            + " " + Timer.getUsClock() );
        */
        if(state == 1){
            if(potVal >= shooterMaxPosition){
                stop();
                state = 0;
            }
            else {
                if(speed == 1.0) {
                    if(potVal < prepareHeight) {
                        motor_1.set(0.3);
                        motor_2.set(0.3);
                    }
                    else {
                        motor_1.set(1.0);
                        motor_2.set(1.0);
                    }
                
                }
            }
            if ( us_startTime + (long)500000 < Timer.getUsClock() ) {
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
    }
}