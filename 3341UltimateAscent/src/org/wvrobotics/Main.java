/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*---------------
 \-------------------------------------------------------------*/

package org.wvrobotics;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.wvrobotics.control.ButtonEvent;
import org.wvrobotics.control.ButtonListener;
import org.wvrobotics.control.Controller;
import org.wvrobotics.control.ControllerManager;
import org.wvrobotics.control.JoystickEvent;
import org.wvrobotics.control.JoystickListener;

public class Main extends IterativeRobot implements JoystickListener,
        ButtonListener {

    private final int LEFT_PWM_PORT = 1;
    private final int RIGHT_PWM_PORT = 2;
    private final int CLIMBER_PWM_PORT = 3;
    private final int FEEDER_PWM_PORT = 4;
    private final int SHOOTER_RELAY_PORT1 = 1;
    private final int SHOOTER_RELAY_PORT2 = 2;
    private final int ACCELEROMETER_I2C_PORT = 1;
    private Controller c;
    private Controller c2;
    private double speed;
    private double curve;
    private int multiplier = 1;
    private double climberPWM = 0.0;
    private RobotDrive drive;
    private Climber climber;
    private AccelerometerReader acc;
    private Feeder feeder;
    private Shooter shooter;
    private Targeting targeting;
    private int autoCounter;
    private boolean autoFinished;
    private DriverStationLCD dLCD;
    private double autoSet;
    private Relay targetingLights;

    public void robotInit() {
        targetingLights = new Relay(3);
        targetingLights.set(Relay.Value.kOn);
        getWatchdog().setEnabled(false);
        c = ControllerManager.getInstance().getController(1, 16);
        c.addButtonListener(this);
        c.addJoystickListener(this);
        c2 = ControllerManager.getInstance().getController(2, 16);
        c2.addButtonListener(this);
        c2.addJoystickListener(this);
        acc = new AccelerometerReader(ACCELEROMETER_I2C_PORT);
        climber = new Climber(CLIMBER_PWM_PORT, acc);
        drive = new RobotDrive(new Victor(LEFT_PWM_PORT),
                new Victor(RIGHT_PWM_PORT));
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        feeder = new Feeder(FEEDER_PWM_PORT);
        shooter = new Shooter(SHOOTER_RELAY_PORT1, SHOOTER_RELAY_PORT2);
        targeting = new Targeting();
        autoFinished = false;
        dLCD = DriverStationLCD.getInstance();
    }

    public void autonomousInit() {
        try {
            if (targeting.isFound())
                climber.setShooterAngle(targeting.getAngle());
            else
                climber.setShooterAngle(23);
            shooter.toggle();
            Thread.sleep(1250);
            feeder.feed();
            Thread.sleep(1250);
            feeder.feed();
            Thread.sleep(1250);
            feeder.feed();
            Thread.sleep(1250);
            shooter.toggle();
            climber.setShooterAngle(25);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void autonomousPeriodic() {

    }

    public void teleopInit() {
        acc.zeroReadings();
    }
    
    public void teleopPeriodic() {
        ADXL345_I2C.AllAxes currReading = acc.getReading();
        System.out.println(currReading.XAxis + " " + currReading.YAxis + " " +
                currReading.ZAxis);
        drive.arcadeDrive(speed * multiplier, curve, true);
        climber.setPWM(climberPWM);
        String spaces = "";
        for (int i = 0; i < DriverStationLCD.kLineLength; i++) {
            spaces += " ";
        }
        String dLCDOutputLine1 = "Absolute angle: " + acc.getAbsoluteAngle();
        String dLCDOutputLine2 = "RPI connected: " + targeting.isConnected();
        String dLCDOutputLine3 = "Target found: " + targeting.isFound();
        if (targeting.isFound()) {
            dLCDOutputLine3 += " (";
            if (targeting.getTarget())
                dLCDOutputLine3 += "middle)";
            else
                dLCDOutputLine3 += "high)";
        }
        String dLCDOutputLine4 = "Target distance: " + targeting.getDistance();
        String dLCDOutputLine5 = "Angle: " + targeting.getAngle();
        String dLCDOutputLine6 = "Azimuth: " + targeting.getAzimuth();
        dLCD.println(DriverStationLCD.Line.kUser1, 1, dLCDOutputLine1 + spaces);
        dLCD.println(DriverStationLCD.Line.kUser2, 1, dLCDOutputLine2 + spaces);
        dLCD.println(DriverStationLCD.Line.kUser3, 1, dLCDOutputLine3 + spaces);
        dLCD.println(DriverStationLCD.Line.kUser4, 1, dLCDOutputLine4 + spaces);
        dLCD.println(DriverStationLCD.Line.kUser5, 1, dLCDOutputLine5 + spaces);
        dLCD.println(DriverStationLCD.Line.kUser6, 1, dLCDOutputLine6 + spaces);
        dLCD.updateLCD();
    }

    public void buttonPressed(ButtonEvent e) {
        if (e.getSource().equals(c)) {
            switch (e.getButton()) {
                case 5:
                    multiplier *= -1;
                    break;
            }
        } else if (e.getSource().equals(c2)) {
            switch (e.getButton()) {
                case 1:
                    feeder.feed();
                    break;
                case 2:
                    shooter.toggle();
                    break;
                case 3:
                    climber.setShooterAngle(15);
                    break;
                case 4:
                    climber.setShooterAngle(15);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    climber.setShooterAngle(targeting.getAngle());
                    break;
                case 5:
                    climber.pullRobotUp();
                    break;
                case 6:
                    climber.bringRobotDown();
                    break;
                case 10:
                    feeder.setPWM(-0.5);
                    break;
                case 9:
                    feeder.setPWM(0.5);
                    break;
                case 8:
                    feeder.setPWM(0);
                    break;
            }
        }
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getSource().equals(c2)) {
            switch (e.getButton()) {
                case 5:
                    climber.stop();
                    break;
                case 6:
                    climber.stop();
            }
        }
    }

    public void buttonTyped(ButtonEvent e) {
        
    }

    public void joystickMoved(JoystickEvent e) {
        if (e.getSource().equals(c)) {
            speed = c.getY();
            curve = c.getZ();
        } else if (e.getSource().equals(c2)) {
            climberPWM = c2.getY();
        }
    }
    
    public void throttleMoved(JoystickEvent e) {

    }

}