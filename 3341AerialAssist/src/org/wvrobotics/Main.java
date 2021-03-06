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
 *  @author Tushar Pankaj
 *  Code available on [Github](https://github.com/frc-team-3341/crio/tree/master/3341AerialAssist)
 */
package org.wvrobotics;

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
public class Main extends IterativeRobot implements JoystickListener, ButtonListener {
    private Controller drive_controller;
    private Controller acquirer_controller;
    private MecanumDrive drive;
    private Shooter shooter;
    private Acquirer acquirer;
    private DriverStationInterface dsInterface;
    private UltrasonicSensor ultrasonic;
    //private Targeting targeting;
    private boolean shotInAutonomous;
    private double autonomousPWM;
    // shooter uses these
    private double prevTime;
    //Other stuff
    public double speedModifier = 0.75;

    /**
     * All the variables and objects are initialized in this method. It is called once.
     */
    public void robotInit() {
        getWatchdog().setEnabled(false);
        //controllers
        drive_controller = ControllerManager.getInstance().getController(1, 16);
        drive_controller.addButtonListener(this);
        drive_controller.addJoystickListener(this);
        acquirer_controller = ControllerManager.getInstance().getController(2, 16);
        acquirer_controller.addButtonListener(this);
        acquirer_controller.addJoystickListener(this);
        //motor stuff]
        drive = new MecanumDrive(DevicePorts.frontLeftPWM, DevicePorts.rearLeftPWM,
                DevicePorts.frontRightPWM, DevicePorts.rearRightPWM);
        drive.setInvertedMotor(DriveMotorData.frontRightIndex, true);
        drive.setInvertedMotor(DriveMotorData.rearRightIndex, true);
        //Other functionality
        shooter = new Shooter(DevicePorts.shooter1PWM, DevicePorts.shooter2PWM);
        prevTime = 0.0;
        acquirer = new Acquirer(DevicePorts.vanDoorPWM, DevicePorts.acquirerLeftPWM,
                DevicePorts.acquirerRightPWM);
      //  targeting = new Targeting();
        shotInAutonomous = false;
        autonomousPWM = 0.0;
        ultrasonic = new UltrasonicSensor(DevicePorts.ultrasonicAnalog);
        dsInterface = new DriverStationInterface();
    }

    public void autonomousInit() {
        try {
            /*
            Runnable toro = new Runnable(){
            public void run(){
            
            }
            };
            Thread t = new Thread(toro);
            t.start();*/
            /*while (ultrasonic.getDistance() > 9.0 * 12.0) { // drive up to 9 feet from the goal
            drive.drive(0.0, 0.8, 0.0, 0.0);
            dsInterface.setLine(3, "Ultrasonic: " + ultrasonic.toString());
            }
            dsInterface.setLine(2, "Reached 9 feet");
            drive.drive(0.0, 0.0, 0.0, 0.0);
            dsInterface.setLine(2, "Shooting");
            
            shooter.shoot(drive);
            //Thread.sleep(7000);*/
            drive.drive(0,0.5,0,0);
            Thread.sleep(2000);
            drive.drive(0,0,0,0);
            Thread.sleep(1000);
            acquirer.pitch_down();
            Thread.sleep(1000);
            acquirer.pitch_stop();
            shooter.shoot(drive);
            Runnable runShootTick = new Runnable(){
                public void run(){
                    while (true)
                    {
                        shooter.tick();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            };
            Thread runShootTickThread = new Thread(runShootTick);
            runShootTickThread.start();
            Thread.sleep(2000);
            shooter.reset();
            Thread.sleep(3000);
            /*drive.drive(0.0, 0.3, 0.0, 0.0);
            try {
            Thread.sleep(2000);
            } catch (InterruptedException ex) {
            ex.printStackTrace();
            }
            drive.drive(0,0,0,0);*/
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * The autonomous code goes here. Called every 20 milliseconds(untested) during autonomous mode, which lasts 10 seconds
     */
    public void autonomousPeriodic() {
    }
    /*    if (!shotInAutonomous) {
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
        
         */
    /**
     * The main code that is called during user control. Called every 20 milliseconds(tested) During user control mode, which lasts about 2 minutes.
     */
    public void teleopPeriodic() {
        
        Timer t = new Timer();
        double now;
        now = Timer.getUsClock();

        // we want angular velocity.
        // it's most accurate to say (delta pot / delta time): we don't NEED to use degrees.
        //System.out.println("potVal: " + shooter.getPotVal() + " state: " + shooter.getState()
          //      + " timeDelta: " + (now - prevTime) );
        
        //System.out.println("potVal: " + shooter.getPotVal());// + " state: " + shooter.getState()
        //        + " timeDelta: " + (now - prevTime));
        //System.out.println("Teleop");
        //System.out.println(shooter.prepareHeight);
        prevTime = now;
        //functional stuff
        //shooter.adjustMax(acquirer_controller);
        shooter.tick();
        if(acquirer_controller.getY() > 0.1)
            acquirer.pitch_up();
        else if (acquirer_controller.getY() < -0.1)
            acquirer.pitch_down();
        else
            acquirer.pitch_stop();
        if(drive_controller.getY() < 0.1 && drive_controller.getY() > -0.1)
            drive.drive(drive_controller.getX() * speedModifier, 0, drive_controller.getZ(), 0);
        else 
            drive.drive(drive_controller.getX() * speedModifier, drive_controller.getY() * speedModifier, drive_controller.getZ() * speedModifier, 0);
        dsInterface.setLine(1, "PotVal: " + Double.toString(shooter.getPotVal()));
        dsInterface.setLine(2, "MaxPotVal: " + Double.toString(shooter.shooterMaxPosition));
        dsInterface.setLine(3, "Ultrasonic: " + ultrasonic.toString() + " feet");
        dsInterface.setLine(4, "AccHeight: " + shooter.prepareHeight);
        
    }

    /**
     * This function is not called during competition mode, but it can be called through the driver station. It is used to initialize the potentiometer.
     */
    public void testPeriodic() {
        shooter.tick();//bad idea? (tick in test mode might make robot move?)
                //update the potentiometer value while in test mode
        dsInterface.setLine(1, "PotVal: "+Double.toString(shooter.getPotVal()));
        dsInterface.setLine(2, "MaxPotVal: "+Double.toString(shooter.shooterMaxPosition));
        dsInterface.setLine(3, "Ultrasonic: "+ultrasonic.toString());
    }

    public void joystickMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
        if (e.getSource().equals(acquirer_controller)) {
        }
        else if (e.getSource().equals(drive_controller)) {
        }
    }

    /**
     * Handles all button presses.
     * @param e A button event that caries information about which button(s) are pressed. Different methods are called based on what button was pressed.
     */
    public void buttonPressed(ButtonEvent e) {
        if (e.getSource().equals(acquirer_controller)) {
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
        }
        else if (e.getSource().equals(drive_controller)) {
            switch (e.getButton()) {
                case 1:
                    shooter.shoot(drive);
                    break;
                case 2:
                    shooter.reset();
                    break;
                case 3:
                    shooter.shooterMaxPosition += 1;
                    break;
                case 4:
                    shooter.shooterMaxPosition -= 1;
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
                case 9:
                    shooter.speed = 1.0;
                    break;
                case 10:
                    shooter.speed = 0.2;
                    break;
                case 11:
                    shooter.prepareHeight -= 1;
                    break;
                case 12:
                    shooter.prepareHeight += 1;
                    break;
                case 13:
                   //buttons 13-16 are unusable
                    break;
            }

        }
    }

    /**
     * Handles button releases
     * @param e A button event that caries information about which button(s) are released. Different methods are called based on what button was released.
     */
    public void buttonReleased(ButtonEvent e) {
        if (e.getSource().equals(acquirer_controller)) {
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
