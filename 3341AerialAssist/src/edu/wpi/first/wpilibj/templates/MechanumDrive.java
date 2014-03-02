/*
 * Custom MechanumDrive class to allow for encoder control
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Victor;

/**
 * @author George "Agent 10" Troulis
 */
public class MechanumDrive {  
    
    private final static int numMotors = 4;
    //holds the actual motors
    private Victor[] m_motors = new Victor[numMotors];
    

    
    //1 = non-inverted, -1 = inverted
    private int invertedMotors[] = {1, 1, 1, 1};
    
    //modify the specific sped of any motor
    private double speedModifier[] = {1.0, 1.0, 1.0, 1.0};
    
    public MechanumDrive(int frontLeft, int rearLeft, int frontRight, int rearRight) {
        m_motors[DriveMotorIndex.frontLeft] = new Victor(frontLeft);
        m_motors[DriveMotorIndex.rearLeft] = new Victor(rearLeft);
        m_motors[DriveMotorIndex.frontRight] = new Victor(frontRight);
        m_motors[DriveMotorIndex.rearRight] = new Victor(rearRight);
    }
    
    public void setInvertedMotor(int motor, boolean isInverted) {
        if (motor < 0 || motor > 3){
            throw new ArrayIndexOutOfBoundsException("motor argument has to be between 0 and 3");
        }
        if(isInverted)
            invertedMotors[motor] = -1;
        else
            invertedMotors[motor] = 1;
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
    
    protected static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

    protected static void normalize(double wheelSpeeds[]) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        int i;
        for (i=1; i<numMotors; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) maxMagnitude = temp;
        }
        if (maxMagnitude > 1.0) {
            for (i=0; i<numMotors; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    public void drive(double x, double y, double rotation, double gyroAngle) {
        double xIn = x;
        double yIn = y;
        // Negate y for the joystick.
        yIn = -yIn;
        // Compenstate for gyro angle.
        double rotated[] = rotateVector(xIn, yIn, gyroAngle);
        xIn = rotated[0];
        yIn = rotated[1];

        double wheelSpeeds[] = new double[numMotors];
        wheelSpeeds[DriveMotorIndex.frontLeft] = xIn + yIn + rotation;
        wheelSpeeds[DriveMotorIndex.frontRight] = -xIn + yIn - rotation;
        wheelSpeeds[DriveMotorIndex.rearLeft] = -xIn + yIn + rotation;
        wheelSpeeds[DriveMotorIndex.rearRight] = xIn + yIn - rotation;

        normalize(wheelSpeeds);

        byte syncGroup = (byte)0x80;
        
        for(int i = 0; i < this.numMotors; i++) {
            m_motors[i].set(wheelSpeeds[i] * invertedMotors[i] * speedModifier[i], syncGroup);
        }
    }
}
