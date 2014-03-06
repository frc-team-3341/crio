package org.wvrobotics;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Tushar Pankaj
 */
public class UltrasonicSensor {
    private AnalogChannel ultrasonic;
    private static final double voltageToInches = 102.040816327;
    
    public UltrasonicSensor(int ultrasonicPort)
    {
        ultrasonic = new AnalogChannel(ultrasonicPort);
    }
    
    public double getDistance()
    {
        return ultrasonic.getVoltage() * voltageToInches;
    }
    
    public String toString()
    {
        return Double.toString(getDistance());
    }
}
