/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class EncoderTest extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private final int NUM_JAGS = 4;
    Jaguar[] jaguars = new Jaguar[NUM_JAGS];
    Counter[] counters = new Counter[NUM_JAGS];
    Joystick j;
    public void robotInit() {
        j = new Joystick(1);
        for(int i = 0; i < NUM_JAGS; i++){
            jaguars[i] = new Jaguar(i + 1);
            counters[i] = new Counter(i + 1);
            counters[i].start();
            counters[i].setDistancePerPulse(5);
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        for(int i  = 0; i < NUM_JAGS; i++){
            jaguars[i].set(j.getY());
            System.out.println((i+1) + ": " + counters[i].getRate() +"; " + counters[i].getDirection());
        }
        System.out.println("\n");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
