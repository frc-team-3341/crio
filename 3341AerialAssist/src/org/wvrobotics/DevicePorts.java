package org.wvrobotics;

/**
 * @author George "Agent 10" Troulis
 * @author Tushar Pankaj
 */
public class DevicePorts {
    // Wheel motor PWM ports
    public static final int frontLeftPWM = 1; // jaguar
    public static final int rearLeftPWM = 2; // jaguar
    public static final int frontRightPWM = 3; // jaguar
    public static final int rearRightPWM = 4; // jaguar
    
    // Shooter motor PWM ports
    public static final int shooter1PWM = 5; // jaguar
    public static final int shooter2PWM = 6; // jaguar
    
    // ElToro motor PWM ports
    public static final int vanDoorPWM = 7; // jaguar
    public static final int acquirerLeftPWM = 8; // talon
    public static final int acquirerRightPWM = 9; // talon
    
    // Digital IO ports
    public static final int frontLeftEncoderDigital1 = 1;
    public static final int frontLeftEncoderDigital2 = 2;
    public static final int rearLeftEncoderDigital1 = 3;
    public static final int rearLeftEncoderDigital2 = 4;
    public static final int frontRightEncoderDigital1 = 5;
    public static final int frontRightEncoderDigital2 = 6;
    public static final int rearRightEncoderDigital1 = 7;
    public static final int rearRightEncoderDigital2 = 8;
    
    // Analog sensor ports
    public static final int potentiometerAnalog = 4;
    public static final int ultrasonicAnalog = 5;

}
