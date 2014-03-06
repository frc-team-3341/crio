/*
 * Since enums are not supported, this is a class to hold the ports of the robot motors
 */
package org.wvrobotics;

/**
 * @author George "Agent 10" Troulis <georgetroulis@gmail.com>
 */
public class MotorPorts {
    //wheels
    public static final int top_left = 1;
    public static final int bottom_left = 2;
    public static final int top_right = 3;    
    public static final int bottom_right = 4;
    //shooter motors
    public static final int shooter_1 = 5;
    public static final int shooter_2 = 6;
    //ElToro Motors
    public static final int van_door = 7;
    public static final int acquirer_left = 8;
    public static final int acquirer_right = 9;
}
