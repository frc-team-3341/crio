/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;

/**
 *
 * @author Leena
 */
public class SpeedController 
{
    private Encoder encode_top_left;
    private Encoder encode_bottom_left;
    private Encoder encode_top_right;
    private Encoder encode_bottom_right;
    
    private PIDController pid1;
    private PIDController pid2;
    private PIDController pid3;
    private PIDController pid4;
    
    public SpeedController(int channel1A, int channel1B, int channel2A, int channel2B, int channel3A, int channel3B, int channel4A, int channel4B)
    {
        encode_top_left = new Encoder(channel1A, channel1B);
        encode_bottom_left = new Encoder(channel2A, channel2B);
        encode_top_right = new Encoder(channel3A, channel3B);
        encode_bottom_right = new Encoder(channel4A, channel4B);
        encode_top_left.setDistancePerPulse(0);
        encode_bottom_left.setDistancePerPulse(0);
        encode_top_right.setDistancePerPulse(0);
        encode_bottom_right.setDistancePerPulse(0);
    }
    
    public void pidInitializer(double P1, double I1, double D1, Jaguar top_left, double P2, double I2, double D2, Jaguar bottom_left, double P3, double I3, double D3, Jaguar top_right, double P4, double I4, double D4, Jaguar bottom_right)
    {
        pid1 = new PIDController(P1, I1, D1, encode_top_left, top_left);
        pid2 = new PIDController(P2, I2, D2, encode_bottom_left, bottom_left);
        pid3 = new PIDController(P3, I3, D3, encode_top_right, top_right);
        pid4 = new PIDController(P4, I4, D4, encode_bottom_right, bottom_right);
        pid1.enable();
        pid2.enable();
        pid3.enable();
        pid4.enable();
    }
    
    public void encoderSetDistancePerPulse(double distance)
    {
        encode_top_right.setDistancePerPulse(distance);
        encode_top_left.setDistancePerPulse(distance);
        encode_bottom_left.setDistancePerPulse(distance);
        encode_bottom_right.setDistancePerPulse(distance);
        pid1.setSetpoint(distance);
        pid2.setSetpoint(distance);
        pid3.setSetpoint(distance);
        pid4.setSetpoint(distance);
    }
    
    public void setEncoderSpeed(double speed1, double speed2, double speed3, double speed4)
    {
        pid1.setSetpoint(speed1);
        pid2.setSetpoint(speed2);
        pid3.setSetpoint(speed3);
        pid4.setSetpoint(speed4);
    }
    
    public void regulate(Jaguar top_left, Jaguar bottom_left, Jaguar top_right, Jaguar bottom_right)
    {
        while (pid1.getSetpoint() != encode_top_left.getRate()||pid2.getSetpoint() != encode_bottom_left.getRate()||pid3.getSetpoint() != encode_top_right.getRate()||pid4.getSetpoint() != encode_bottom_right.getRate())
        {
            top_left.set(pid1.get());
            bottom_left.set(pid2.get());
            top_right.set(pid3.get());
            bottom_right.set(pid4.get());
        }
    }
    
    public void Encoderoutput()
    {
        System.out.println("Encoder Output:" + encode_top_left.getRate());
    }
}
