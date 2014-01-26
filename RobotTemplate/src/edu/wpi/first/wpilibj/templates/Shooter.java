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
 * @author michael
 */
public class Shooter {
    private final int SHOOTER_PORT = 3;
    private final int LOADER_PORT = 4;
    private Jaguar launcher;
    private Jaguar loader;
    
    public Shooter(){
        launcher = new Jaguar(SHOOTER_PORT);
        loader = new Jaguar(LOADER_PORT);
    }
    
    public void shoot(){
        launcher.setPosition(90); //releases the shooter
        launcher.setPosition(0);
    }
    
    private void shoot(int millis){
        loader.set(1.0);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
        }
        loader.set(0);
        shoot();
    }
    
    
    public void shootClose(){
        shoot(500);
    }
    
    public void shootMedium(){
        shoot(1000);
    }
    public void shootFar(){
        shoot(1500);
    }
    
    public void load(){
        loader.set(0.5);
    }
    
    public void stop(){
        loader.set(0);
    }
    
    
}
