/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wvrobotics;

/**
 *  class(enum) for motor indexes
 * @author George "Agent 10" Troulis <georgetroulis@gmail.com>
 * @author Tushar Pankaj
 */
public class DriveMotorData {
    public static final int frontLeftIndex = 0;
    public static final int rearLeftIndex = 1;
    public static final int frontRightIndex = 2;
    public static final int rearRightIndex = 3;
    
    public static final double[] maxEncoderRates = {
        2300.0, // not tuned yet
        2300.0, // not tuned yet
        2300.0, // not tuned yet
        2300.0 // not tuned yet
    };
    public static final double minOfMaxEncoderRates = 2300.0; // not tuned yet
    public static final double Kp = 10; // not tuned yet
    public static final double Ki = 0.1; // not tuned yet
    public static final double Kd = 0.0; // not tuned yet
}
