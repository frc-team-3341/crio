/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
 * Code developed by:
 *  George Troulis
 *  Adam <something>
 *  Colby Hester
 *  Brenda <something>
 *
 *  Code available on Github
*/
package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
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
    private Controller drive_controller;
    private Controller shooter_controller;
    private RobotDrive drive;
    private Shooter shooter;
    private Acquirer acquirer;
    //wheels
    private final int top_left = 2;
    private final int bottom_left = 3;
    private final int top_right = 4;    
    private final int bottom_right = 5;
    //shooter motors
    private final int SHOOTER_MOTOR_1 = 6;
    private final int SHOOTER_MOTOR_2 = 7;
    //ElToro Motors
    private final int VAN_DOOR = 8;
    private final int ACQUIRER1 = 9;
    private final int ACQUIRER2 = 10;
    //Encoder
//
    
    //Other stuff
    public double speedModifier = 0.75;
    
    public void robotInit() {
        //Drive motors
//        top_left = new Jaguar(1);
//        bottom_left = new Jaguar(2);
//        top_right = new Jaguar(3);   
//        bottom_right = new Jaguar(4);
        //controllers
        drive_controller = ControllerManager.getInstance().getController(1, 16);
        drive_controller.addButtonListener(this);
        drive_controller.addJoystickListener(this);
        
        shooter_controller = ControllerManager.getInstance().getController(2, 16);
        shooter_controller.addButtonListener(this);
        shooter_controller.addJoystickListener(this);
        //motor stuff
        drive = new RobotDrive(top_left, bottom_left, top_right, bottom_right);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //Other functionality
        shooter = new Shooter(SHOOTER_MOTOR_1, SHOOTER_MOTOR_2);
        acquirer = new Acquirer(VAN_DOOR, ACQUIRER1, ACQUIRER2);
        //Encoder Initialization
//        control = new SpeedController(14, 0, 0, 0, 0, 0, 0, 0);
//        control.pidInitializer(1.0, 0, 0, top_left, 1.0, 0, 0, bottom_left, 1.0, 0, 0, top_right, 1.0, 0, 0, bottom_right);
    }

    public void autonomousPeriodic() 
    {
//        control.encoderSetDistancePerPulse(5); //Parameter needs to be set by autonomous coder
//        control.setEncoderSpeed(5,5,5,5); //Parameter needs to be set by autonomous coder
//        control.regulate(top_left, bottom_left, top_right, bottom_right);
    }

    public void teleopPeriodic() {
        //debug stuff
        System.out.println("potVal: " + shooter.getPotVal() + " state: " + shooter.getState());
        //functional stuff
        shooter.adjustMax(shooter_controller);
        shooter.tick();
        drive.mecanumDrive_Cartesian(drive_controller.getX() * speedModifier, drive_controller.getY() * speedModifier, -drive_controller.getZ() * speedModifier, 0);
//        control.encoderSetDistancePerPulse(5);
//        control.setEncoderSpeed(10,10,10,10);
//        control.Encoderoutput();
    }
      
    public void testPeriodic() {
        //TODO: add code to calibrate potentiometer
    }
    public void joystickMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
        if(e.getSource().getPort() == 1) { //driver controller
            
        }
        else { //shooter controller
            
        }
    }      

    public void buttonPressed(ButtonEvent e) {
        if(e.getSource().getPort() == 2) {//shooter joystick
            switch(e.getButton()) {
                case 1:
                    shooter.shoot();
                    break;
                case 2:
                    shooter.reset();
                    break;
            }
        }
        else { //source == driver_controller
            switch(e.getButton()) {
                case 1:
                    acquirer.collect();
                    break;
                case 2:
                   acquirer.dump();
                   break;
                case 3:
                    acquirer.pitch_down();
                    break;
                case 4:
                    acquirer.pitch_up();
                    break;
                case 5:
                    speedModifier = 0.5;
                    break;
                case 6:
                    speedModifier = 0.75;
                    break;
                case 7:
                    speedModifier = 1.00;  
                    break;
                case 8:
                    speedModifier *= -1;
                    break;
            }
       
    }
    }
    public void buttonReleased(ButtonEvent e) {
        if(e.getSource().getPort() == 1) { //source == driver_controller
            switch(e.getButton()) {
                case 1:
                    acquirer.acquirer_stop();
                    break;
                case 2:
                   acquirer.acquirer_stop();
                   break;
                case 3:
                    acquirer.pitch_stop();
                    break;
                case 4:
                    acquirer.pitch_stop();
                    break;
            }
        }
    }

    public void buttonTyped(ButtonEvent e) {
    }
    
}
