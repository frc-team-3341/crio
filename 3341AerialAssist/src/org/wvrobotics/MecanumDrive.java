/*
 * Custom MecanumDrive class to allow for encoder control
 */

package org.wvrobotics;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author George "Agent 10" Troulis
 * @author Tushar Pankaj
 *
 */
public class MecanumDrive {  
    
    private final static int numMotors = 4;
    //holds the actual motors
    private SpeedController[] m_motors = new SpeedController[numMotors];
 
    private Encoder[] m_encoders = new Encoder[numMotors];

    private PIDController[] m_pid = new PIDController[numMotors];
    
    //1 = non-inverted, -1 = inverted
    private int invertedMotors[] = {1, 1, 1, 1};
    
    //modify the specific sped of any motor
    private double speedModifier[] = {1.0, 1.0, 1.0, 1.0};
    
    public MecanumDrive(int frontLeft, int rearLeft, int frontRight, int rearRight) {
        //System.out.println("mech1");
        m_motors[DriveMotorData.frontLeftIndex] = new Jaguar(frontLeft);
        m_motors[DriveMotorData.rearLeftIndex] = new Jaguar(rearLeft);
        m_motors[DriveMotorData.frontRightIndex] = new Jaguar(frontRight);
        m_motors[DriveMotorData.rearRightIndex] = new Jaguar(rearRight);
        //System.out.println("mech2");
        //System.out.println("mech3");
	m_encoders[DriveMotorData.frontLeftIndex] = new Encoder(DevicePorts.frontLeftEncoderDigital1, DevicePorts.frontLeftEncoderDigital2, false, Encoder.EncodingType.k2X);
	m_encoders[DriveMotorData.rearLeftIndex] = new Encoder(DevicePorts.rearLeftEncoderDigital1, DevicePorts.rearLeftEncoderDigital2, false, Encoder.EncodingType.k2X);
        m_encoders[DriveMotorData.frontRightIndex] = new Encoder(DevicePorts.frontRightEncoderDigital1, DevicePorts.frontRightEncoderDigital2, false, Encoder.EncodingType.k2X);
        m_encoders[DriveMotorData.rearRightIndex] = new Encoder(DevicePorts.rearRightEncoderDigital1, DevicePorts.rearRightEncoderDigital2, false, Encoder.EncodingType.k2X);
        //System.out.println("mech4");
        m_encoders[DriveMotorData.frontLeftIndex].setDistancePerPulse(1); //distance in degrees
        m_encoders[DriveMotorData.rearLeftIndex].setDistancePerPulse(1);
        m_encoders[DriveMotorData.frontRightIndex].setDistancePerPulse(1);
        m_encoders[DriveMotorData.rearRightIndex].setDistancePerPulse(1);
        //System.out.println("mech5");
        m_encoders[DriveMotorData.frontLeftIndex].setSamplesToAverage(100);
        m_encoders[DriveMotorData.rearLeftIndex].setSamplesToAverage(100);
        m_encoders[DriveMotorData.frontRightIndex].setSamplesToAverage(100);
        m_encoders[DriveMotorData.rearRightIndex].setSamplesToAverage(100);
        //System.out.println("mech6");
        m_pid[DriveMotorData.frontLeftIndex] =  new PIDController(DriveMotorData.Kp, DriveMotorData.Ki, DriveMotorData.Kd, 0.0);
        m_pid[DriveMotorData.rearLeftIndex] =  new PIDController(DriveMotorData.Kp, DriveMotorData.Ki, DriveMotorData.Kd, 0.0);
        m_pid[DriveMotorData.frontRightIndex] = new PIDController(DriveMotorData.Kp, DriveMotorData.Ki, DriveMotorData.Kd, 0.0);
        m_pid[DriveMotorData.rearRightIndex] = new PIDController(DriveMotorData.Kp, DriveMotorData.Ki, DriveMotorData.Kd, 0.0);

	for (int i = 0; i < numMotors; i++)
	{
	    m_encoders[i].start();
	    m_encoders[i].reset();
	}
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
        double cosA = Math.cos(angle * (Math.PI / 180.0));
        double sinA = Math.sin(angle * (Math.PI / 180.0));
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
        //System.out.println("driving.");
        double xIn = x;
        double yIn = y;
        // Negate y for the joystick.
        yIn = -yIn;
        // Compenstate for gyro angle.
        double rotated[] = rotateVector(xIn, yIn, gyroAngle);
        xIn = rotated[0];
        yIn = rotated[1];

        double wheelSpeeds[] = new double[numMotors];
        wheelSpeeds[DriveMotorData.frontLeftIndex] = xIn + yIn + rotation;
        wheelSpeeds[DriveMotorData.frontRightIndex] = -xIn + yIn - rotation;
        wheelSpeeds[DriveMotorData.rearLeftIndex] = -xIn + yIn + rotation;
        wheelSpeeds[DriveMotorData.rearRightIndex] = xIn + yIn - rotation;

        normalize(wheelSpeeds);
        
        for (int i = 0; i < numMotors; i++)
            System.out.print(m_encoders[i].getRate() + " ");
        System.out.println();
        
        for(int i = 0; i < numMotors; i++) {
            m_motors[i].set(wheelSpeeds[i] * invertedMotors[i] * speedModifier[i]); // non-PID controlled driving
	    /* UNCOMMENT TO ENABLE PID CONTROLLED DRIVING AND COMMENT THE LINE ABOVE
            m_pid[i].setSetPoint(wheelSpeeds[i] * invertedMotors[i] * speedModifier[i] * DriveMotorData.minOfMaxEncoderRates);
	    m_motors[i].set(m_pid[i].tick(m_encoders[i].getRate()) / DriveMotorData.maxEncoderRates[i]);*/
        }
    }
}
