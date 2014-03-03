/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/**
 * 
 *  @author George Troulis
 *  @author Adam Tedeschi
 *  @author Colby Hester
 *  @author Brenda <something>
 *  @author David Mao
 *  @author Prathyush Katukojwala
 *  Code available on Github
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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
<<<<<<< HEAD:3341AerialAssist/src/edu/wpi/first/wpilibj/templates/Main.java
public class Main extends IterativeRobot implements JoystickListener, ButtonListener {
=======
public class RobotTemplate extends IterativeRobot implements JoystickListener, ButtonListener {

>>>>>>> 11b3f6205345e2c491f8fe6ee8e65d969a594d84:RobotTemplate/src/edu/wpi/first/wpilibj/templates/RobotTemplate.java
    private Controller drive_controller;
    private Controller acquirer_controller;
    private MechanumDrive drive;
    private Shooter shooter;
    private Acquirer acquirer;
    private Targeting targeting;
    private boolean shotInAutonomous;
    //moved motor ports to a single class
    // shooter uses these
    private double prevTime;
    //Other stuff
    public double speedModifier = 0.75;

    /**
     * All the variables and objects are initialized in this method. It is called once.
     */
    public void robotInit() {

        //controllers
        drive_controller = ControllerManager.getInstance().getController(1, 16);
        drive_controller.addButtonListener(this);
        drive_controller.addJoystickListener(this);

        acquirer_controller = ControllerManager.getInstance().getController(2, 16);
        acquirer_controller.addButtonListener(this);
        acquirer_controller.addJoystickListener(this);
        //motor stuff
        drive = new MechanumDrive(MotorPorts.top_left, MotorPorts.bottom_left, MotorPorts.top_right, MotorPorts.bottom_right);
        drive.setInvertedMotor(DriveMotorIndex.frontRight, true);
        drive.setInvertedMotor(DriveMotorIndex.rearRight, true);
        //Other functionality
        shooter = new Shooter(MotorPorts.shooter_1, MotorPorts.shooter_2);
        prevTime = 0.0;
        acquirer = new Acquirer(MotorPorts.van_door, MotorPorts.acquirer_left, MotorPorts.acquirer_right);
        targeting = new Targeting();
        shotInAutonomous = false;
        //Encoder Initialization
        //control = new SpeedController(14, 0, 0, 0, 0, 0, 0, 0);
        //control.pidInitializer(1.0, 0, 0, top_left, 1.0, 0, 0, bottom_left, 1.0, 0, 0, top_right, 1.0, 0, 0, bottom_right);
    }

    /**
     * The autonomous code goes here. Called every 20 milliseconds(untested) during autonomous mode, which lasts 10 seconds
     */
    public void autonomousPeriodic() {
        if (!shotInAutonomous) {
            return;
        }
        //control.encoderSetDistancePerPulse(5); //Parameter needs to be set by autonomous coder
        //control.setEncoderSpeed(5,5,5,5); //Parameter needs to be set by autonomous coder
        //control.regulate(top_left, bottom_left, top_right, bottom_right);

        double moveX = 0.0;
        double moveY = 0.0;
        //FILL IN THESE NUMBERS

        double whichSide = 1.0;
        //1.0 -> Left
        //2.0 -> Middle
        //3.0 -> Right

        if (targeting.isFound()) {

            double hotGoal = targeting.getHotGoal();
            if (whichSide == 1.0) {

                if (hotGoal == 1) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                } else if (hotGoal == 2) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                }

            } else if (whichSide == 2.0) {

                if (hotGoal == 1) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                } else if (hotGoal == 2) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                }

            } else if (whichSide == 3.0) {

                if (hotGoal == 1) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                } else if (hotGoal == 2) {
                    drive.move(moveX, moveY);
                    shooter.shoot();
                }

            }
            shotInAutonomous = true;
        } else {
        }
    }

    /**
     * The main code that is called during user control. Called every 20 milliseconds(tested) During user control mode, which lasts about 2 minutes.
     */
    public void teleopPeriodic() {
        //debug stuff
        //Time.timeval now = Time.timeval();
        Timer t = new Timer();
        double now;
        now = Timer.getUsClock();

        // we want angular velocity.
        // it's most accurate to say (delta pot / delta time): we don't NEED to use degrees.
<<<<<<< HEAD:3341AerialAssist/src/edu/wpi/first/wpilibj/templates/Main.java
        //System.out.println("potVal: " + shooter.getPotVal() + " state: " + shooter.getState()
          //      + " timeDelta: " + (now - prevTime) );
        
=======
        System.out.println("potVal: " + shooter.getPotVal() + " state: " + shooter.getState()
                + " timeDelta: " + (now - prevTime));

>>>>>>> 11b3f6205345e2c491f8fe6ee8e65d969a594d84:RobotTemplate/src/edu/wpi/first/wpilibj/templates/RobotTemplate.java
        prevTime = now;
        //functional stuff
        shooter.adjustMax(acquirer_controller);
        shooter.tick();
        System.out.println(drive_controller.getX() + "; " + drive_controller.getY() + "; " + drive_controller.getZ());
        if(drive_controller.getY() < 0.1 && drive_controller.getY() > -0.1)
            drive.drive(drive_controller.getX() * speedModifier, 0, drive_controller.getZ(), 0);
        else 
            drive.drive(drive_controller.getX() * speedModifier, drive_controller.getY() * speedModifier, drive_controller.getZ() * speedModifier, 0);
        //control.encoderSetDistancePerPulse(5);
        //control.setEncoderSpeed(10,10,10,10);
        //control.Encoderoutput();
    }

    /**
     * This function is not called during competition mode, but it can be called through the driver station. It is used to initialize the potentiometer.
     */
    public void testPeriodic() {
        //TODO: add code to calibrate potentiometer
    }

    public void joystickMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
        if (e.getSource().getPort() == 1) { //driver controller
        } else { //shooter controller
        }
    }

    /**
     * Handles all button presses.
     * @param e A button event that caries information about which button(s) are pressed. Different methods are called based on what button was pressed.
     */
    public void buttonPressed(ButtonEvent e) {
        if (e.getSource().getPort() == 1) {//Acquirer joystick
            switch (e.getButton()) {
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
            }
        } else { //source == driver_controller
            switch (e.getButton()) {
                case 1:
                    shooter.shoot();
                    break;
                case 2:
                    shooter.reset();
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

    /**
     * Handles button releases
     * @param e A button event that caries information about which button(s) are released. Different methods are called based on what button was released.
     */
    public void buttonReleased(ButtonEvent e) {
        if (e.getSource().getPort() == 1) { //source == accquirer_controller
            switch (e.getButton()) {
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
