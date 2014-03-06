package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Tushar Pankaj
 *
 */
public class PIDController
{
    private double Kp;
    private double Ki;
    private double Kd;
    private double setPoint;
    private double previousError;
    private double integral;
    private long lastCallTime;

    public PIDController(double Kp, double Ki, double Kd, double setPoint)
    {
	this.Kp = Kp;
	this.Ki = Ki;
	this.Kd = Kd;
	this.setPoint = setPoint;

	previousError = 0.0;
	integral = 0.0;
	lastCallTime = -1;
    }

    public double tick(double measuredValue)
    {
	long thisCallTime = Timer.getUsClock();
	double dt;
	if (lastCallTime = -1)
	    dt = 0.0;
	else
	    dt = (double)(thisCallTime - lastCallTime) * 0.000001; // convert useconds to seconds
	double error = setPoint - measuredValue;
	integral += error * dt;
	double derivative = (error - previousError) / dt;
	previousError = error;
	lastCallTime = thisCallTime;

	return Kp * error + Ki * integral + Kd * derivative;
    }

    public void setSetPoint(double setPoint)
    {
	this.setPoint = setPoint;
    }

    public double getSetPoint()
    {
	return setPoint;
    }

    public void setKp(double Kp)
    {
	this.setKp = Kp;
    }

    public double getKp()
    {
	return Kp;
    }

    public void setKi(double Ki)
    {
	this.setKi = Ki;
    }

    public double getKi()
    {
	return Ki;
    }

    public void setKd(double Kd)
    {
	this.setKd = Kd;
    }

    public double getKd()
    {
	return Kd;
    }
}
