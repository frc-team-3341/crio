/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import org.wvrobotics.control.ButtonEvent;
import org.wvrobotics.control.ButtonListener;
import org.wvrobotics.control.Controller;
import org.wvrobotics.control.ControllerManager;
import org.wvrobotics.control.JoystickEvent;
import org.wvrobotics.control.JoystickListener;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot implements JoystickListener, ButtonListener {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private Controller controller;
    private RobotDrive drive;
    private Shooter shooter;
    //wheels
    private final int top_left = 1;
    private final int bottom_left = 2;
    private final int top_right = 3;    
    private final int bottom_right = 4;
    
    public void robotInit() {
        controller = ControllerManager.getInstance().getController(1, 16);
        controller.addButtonListener(this);
        controller.addJoystickListener(this);
        drive = new RobotDrive(top_left, bottom_left, top_right, bottom_right); //1 and 2 are PWM port numbers
        shooter = new Shooter();
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
        drive.mecanumDrive_Polar(controller.getX(), controller.getY(), controller.getZ());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {    
    }

    public void joystickMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
    }

    public void buttonPressed(ButtonEvent e) {
        switch(e.getButton()) {
            case '1':
                shooter.shoot();
                break;
            case '2':
                //todo: add stuff
                break;
        }
        
    }

    public void buttonReleased(ButtonEvent e) {
        if(e.getButton() == 1)
            shooter.stop();
    }

    public void buttonTyped(ButtonEvent e) {
    }
    
}
