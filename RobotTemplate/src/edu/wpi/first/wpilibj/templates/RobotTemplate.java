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
    private Controller drive_controller;
    private Controller shooter_controller;
    private RobotDrive drive;
    private Shooter shooter;
    private ElToro eltoro;
    //wheels
    private final int top_left = 1;
    private final int bottom_left = 2;
    private final int top_right = 3;    
    private final int bottom_right = 4;
    //shooter stuff
    private final int SHOOTER_MOTOR_1 = 5;
    private final int SHOOTER_MOTOR_2 = 6;
    //ElToro Motors
    private final int VAN_DOOR = 7;
    private final int ACQUIRER1 = 8;
    private final int ACQUIRER2 = 9;
    
    public void robotInit() {
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
        //shooter = new Shooter(SHOOTER_MOTOR_1, SHOOTER_MOTOR_2);
        eltoro = new ElToro(VAN_DOOR, ACQUIRER1, ACQUIRER2);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
        drive.mecanumDrive_Cartesian(drive_controller.getX(), drive_controller.getY(), -drive_controller.getZ(), 0);
    }
    
    public void testPeriodic() {    
    }

    public void joystickMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
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
                case 3:
                    //--\\
                    break;
                case 4:
                    //--\\
                    break;
            }
        }
        else { //source == driver_controller
            switch(e.getButton()) {
                case 1:
                    eltoro.collect();
                    break;
                case 2:
                   eltoro.dump();
                   break;
                case 3:
                    eltoro.pitch_down();
                    break;
                case 4:
                    eltoro.pitch_up();
                    break;
            }
        }
    }

    public void buttonReleased(ButtonEvent e) {
        if(e.getSource().getPort() == 2) {//shooter joystick
            switch(e.getButton()) {
                case 1:
                    shooter.stop();
                    break;
                case 2:
                    shooter.stop();
                    break;
                case 3:
                    //--\\
                    break;
                case 4:
                    //--\\
                    break;
            }
        }
        else { //source == driver_controller
            switch(e.getButton()) {
                case 1:
                    eltoro.acquirer_stop();
                    break;
                case 2:
                   eltoro.acquirer_stop();
                   break;
                case 3:
                    eltoro.pitch_stop();
                    break;
                case 4:
                    eltoro.pitch_stop();
                    break;
            }
        }
    }

    public void buttonTyped(ButtonEvent e) {
    }
    
}
