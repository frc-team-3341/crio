package org.wvrobotics;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Tushar Pankaj
 */
public class UltrasonicSensor {
    private AnalogChannel ultrasonic;
    private static final double voltageToInches = 1000.0 / 9.8;
    
    public UltrasonicSensor(int ultrasonicPort)
    {
        ultrasonic = new AnalogChannel(ultrasonicPort);
    }
    
    public double getDistance()
    {
        return ultrasonic.getVoltage() * voltageToInches;
    }
    
    //take an average of 10 values
    //measure 12 values and throw out the 2 extremes
    public double getAverageDistance() 
    {
        double values[] = new int[12];
        
        for(int i = 0; i < 12; i++)
            values[i] = getDistance();
            
        Array.sort(values);//sort in numeric order
        
        double sum;
        for(int i = 1; i <= 10; i++) //don't use the 2 extreme values to calculate the average
            sum += values[i];
            
        double average = sum / 10;
        return average;
    }
    
    public String toString()
    {
        return Double.toString(getDistance() / 12); //to feet
    }
}
