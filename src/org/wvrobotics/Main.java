/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.wvrobotics;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.wvrobotics.control.ButtonEvent;
import org.wvrobotics.control.ButtonListener;
import org.wvrobotics.control.Controller;
import org.wvrobotics.control.ControllerManager;
import org.wvrobotics.control.JoystickEvent;
import org.wvrobotics.control.JoystickListener;
import org.wvrobotics.util.Robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot implements JoystickListener,
        ButtonListener {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private Controller c;
    private Controller c2;
    private BridgeManipulator bm;
    private RobotDrive driveTrain;
    private Shooter shooter;
    private BallAcquirer ballAcquirer;
    private double speed;
    private double curve;
    private int reversed;
    private double shooterPWM;
    private boolean autoFinished = false;
    private DriverStationLCD dLCD;
    private int control = 0;
//	private Counter encoder;

    public void robotInit() {
        c = ControllerManager.getInstance().getController(1, 16);
        c.addButtonListener(this);
        c.addJoystickListener(this);
        c2 = ControllerManager.getInstance().getController(2, 16);
        c2.addButtonListener(this);
        c2.addJoystickListener(this);
        shooter = new Shooter(7, 6);
        bm = new BridgeManipulator(3);
        ballAcquirer = new BallAcquirer(4, 5);
        driveTrain = new RobotDrive(1, 2);
        dLCD = DriverStationLCD.getInstance();
        getWatchdog().setEnabled(false);
        shooterPWM = 0;
        speed = 0;
        curve = 0;

        reversed = 1;

        //encoder = new Counter(1);
        //encoder.start();
    }

    public void autonomousInit() {
        shooter.set(0);
        ballAcquirer.setEnabled(false);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if (autoFinished) {
            return;
        }

        shooter.set(1);
        Robot.pause(3000);
        //while (encoder.getPeriod() < 0.04);
        shooter.shoot();
        shooter.set(-.1);
        ballAcquirer.setEnabled(true);
        Robot.pause(4500);
        shooter.set(1);
        Robot.pause(2400);
        //System.out.println(encoder.getPeriod());
        shooter.shoot();

        autoFinished = true;
    }

    public void teleopInit() {
        speed = 0;
        curve = 0;
        reversed = 1;

        ballAcquirer.setReversed(false);
        ballAcquirer.setEnabled(false);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        control++;
        driveTrain.arcadeDrive(speed * reversed, curve);
        //shooter.shoot();
        bm.set(c2.getY());
        //double period = encoder.getPeriod();
        //System.out.println(period + " " + ((1.0 / period) * 60));
        //System.out.println("RPM: " + shooter.getRPM());
        if (control == 30) {
            String spaces = "";
            for (int i = 0; i < dLCD.kLineLength; ++i) {
                spaces += " ";
            }
            dLCD.println(DriverStationLCD.Line.kUser2, 1, "RPM: " + (int) shooter.getRPM() + spaces);
            dLCD.updateLCD();
            control = 0;
        }
    }

    public void buttonPressed(ButtonEvent e) {
        if (e.getSource().equals(c)) {
            switch (e.getButton()) {
                case 1:
                    ballAcquirer.setEnabled(true);
                    break;
                case 2:
                    ballAcquirer.reverse();
                    break;
                case 5:
                    reversed *= -1;
                    break;
                case 6:
                    bm.toggle();
                    break;
            }
        } else if (e.getSource().equals(c2)) {
            switch (e.getButton()) {
                case 1:
                    shooter.shoot();
                    break;
                case 2:
                    shooter.toggle();
                    break;
                case 5:
                    shooterPWM = 0;
                    shooter.set(shooterPWM);
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    shooterPWM = (e.getButton() - 3) * .1;
                    shooter.set(shooterPWM);
                    break;
            }
        }
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getSource().equals(c)) {
            if (e.getButton() == 1) {
                ballAcquirer.setEnabled(false);
            }
        }

    }

    public void buttonTyped(ButtonEvent e) {
    }

    public void joystickMoved(JoystickEvent e) {
        if (e.getSource().equals(c)) {
            speed = e.getY();
            curve = -e.getZ();
        }
    }

    public void throttleMoved(JoystickEvent e) {
        try {
            if (e.getSource().equals(c2)) {
                shooter.set(shooterPWM + (e.getThrottle() * 0.1));
            }
        } catch (Exception ex) {
        }
    }
}
