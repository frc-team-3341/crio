/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;
import org.wvrobotics.util.Robot;

/** *
 * @author George "Agent 10" Troulis
 */
public class Shooter {
    //TODO: add potentiometer somehow
    private Jaguar motor_1;
    private Jaguar motor_2;
    
    public Shooter(int _motor_1, int _motor_2){
        motor_1 = new Jaguar(_motor_1);
        motor_2 = new Jaguar(_motor_2);
    }
    
    public void shoot(){
        motor_1.set(1.0);
        motor_2.set(1.0);
    }
    
    public void reset(){
        motor_1.set(-1.0);
        motor_2.set(-1.0);
    }
    
    public void stop(){
        motor_1.set(0);
        motor_2.set(0);
    }    
}
