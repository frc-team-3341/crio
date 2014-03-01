/*
 * Custom MechanumDrive class to allow for encoder control
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Victor;

/**
 * @author George "Agent 10" Troulis <georgetroulis@gmail.com>
 */
public class MechanumDrive {  
    
    //holds the actual motors
    private static Victor[] m_motors = {};
    
    private final int numMotors = 4;
    
    //1 = non-inverted, -1 = inverted
    private int invertedMotors[] = {1, 1, 1, 1};
    
    //modify the specific sped of any motor
    private double speedModifier[] = {1.0, 1.0, 1.0, 1.0};
    
    public MechanumDrive(int frontLeft, int rearLeft, int frontRight, int rearRight) {
        m_motors[DriveMotorIndex.frontLeft] = new Victor(frontLeft);
        m_motors[DriveMotorIndex.rearLeft] = new Victor(frontRight);
        m_motors[DriveMotorIndex.frontRight] = new Victor(rearLeft);
        m_motors[DriveMotorIndex.rearRight] = new Victor(rearRight);
    }
    
    public void setInvertedMotor(int motor, boolean isInverted) {
        if (motor < 0 || motor > 3){
            throw new ArrayIndexOutOfBoundsException("motor argument has to be between 0 and 3");
        }
        invertedMotors[motor] = isInverted ? -1 : 1;
    }
    
    /**
     * Set the speed modifier of any specific motor.
     * @param motor The motor to change speed
     * @param speed The amount to multiply the original speed by (from 0.0 to 1.0)
     */
    public void setMotorSpeed(int motor, double speed) {
        if (motor < 0 || motor > 3){
            throw new ArrayIndexOutOfBoundsException("motor argument has to be between 0 and 3");
        }
        if (speed < 0.0 || speed > 1.0) {
            throw new IllegalArgumentException("speed argument must be between 0.0 and 1.0");
        }
        speedModifier[motor] = speed;
    }
    
    public void drive(double x, double y, double rotation) {
        double xVal = x;
        double yVal = -y;// Negate y for the joystick.
        
        double wheelSpeeds[] = {};
        wheelSpeeds[DriveMotorIndex.frontLeft] = (xVal + yVal + rotation);
        wheelSpeeds[DriveMotorIndex.rearLeft] = (-xVal + yVal + rotation);
	wheelSpeeds[DriveMotorIndex.frontRight] = (-xVal + yVal - rotation);
        wheelSpeeds[DriveMotorIndex.rearRight] = (xVal + yVal - rotation);
        
        for(int i = 0; i < this.numMotors; i++) {
            m_motors[i].set(wheelSpeeds[i] * invertedMotors[i] * speedModifier[i]);
        }
    }
}
