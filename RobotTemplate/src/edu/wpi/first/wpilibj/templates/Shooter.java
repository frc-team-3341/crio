/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import org.wvrobotics.util.Robot;

/** *
 * @author George "Agent 10" Troulis
 */
public class Shooter {
    private static final int shooterMaxPosition = 1023;//Will be modified later
    private static final int shooterMinPosition = 0;//Will be modified later
    
    //potentiometer
    private AnalogChannel pot;
    
    private Jaguar motor_1;
    private Jaguar motor_2;
    
    private int potVal;
    
    public Shooter(int _motor_1, int _motor_2){
        motor_1 = new Jaguar(_motor_1);
        motor_2 = new Jaguar(_motor_2);
        pot = new AnalogChannel(1);
    }
    
    public void shoot(){
        if(potVal < shooterMaxPosition) {
            motor_1.set(1.0);
            motor_2.set(1.0);
        }
        else
            this.stop();
    }
    
    public void reset(){
        if(potVal > shooterMinPosition){
            motor_1.set(-1.0);
            motor_2.set(-1.0);
        }
        else
            this.stop();
    }
    
    public void stop(){
        motor_1.set(0);
        motor_2.set(0);
    }
    
    public int getPotVal() {
        this.potVal = pot.getValue();
        return potVal;
    }
}
