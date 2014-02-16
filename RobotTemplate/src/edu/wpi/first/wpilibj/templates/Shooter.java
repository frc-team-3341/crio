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
 * @author George "Agent 10" Troulis
 */
public class Shooter {
    private final int SHOOTER_PORT = 5;
    private Jaguar launcher;
    
    public Shooter(){
        launcher = new Jaguar(SHOOTER_PORT);
    }
    
    public void shoot(){
        launcher.set(1.0);        
    }
    
    public void stop(){
        launcher.set(0);
    }    
}
