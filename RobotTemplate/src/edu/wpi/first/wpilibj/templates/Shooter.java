/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Jaguar;
import org.wvrobotics.control.ControllerManager;

/**
 * *
 * @author George "Agent 10" Troulis
 */
public class Shooter {

    private static final int shooterMaxPosition = 200;//Will be modified later
    private static final int shooterMinPosition = 50;//Will be modified later

    //potentiometer
    private AnalogPotentiometer pot;

    private Jaguar motor_1;
    private Jaguar motor_2;
    private int state; // 1 forward, 0 stopped, -1 reverse

    private double potVal;

    public Shooter(int _motor_1, int _motor_2) {
        motor_1 = new Jaguar(_motor_1);
        motor_2 = new Jaguar(_motor_2);
        pot = new AnalogPotentiometer(4,54);
        state = 0;
        this.getPotVal();
    }

    public void shoot() {
        if (potVal < shooterMaxPosition) {
            motor_1.set(1.0);
            state = 1;
            motor_2.set(1.0);
        } else {
            this.stop();
        }
    }

    public void reset() {
        if (potVal > shooterMinPosition) {
            motor_1.set(-0.5);
            state = -1;
            motor_2.set(-1.0);
        } else {
            this.stop();
        }
    }
        
    public void stop() {
        state = 0;
        motor_1.set(0);
        motor_2.set(0);
    }
    
    public int getState(){
        return state;
    }

    public double getPotVal() {
        return potVal;
    }

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