package org.wvrobotics;

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

    public PIDController(double Kp, double Ki, double Kd, double setPoint)
    {
	this.Kp = Kp;
	this.Ki = Ki;
	this.Kd = Kd;
	this.setPoint = setPoint;

	previousError = 0.0;
	integral = 0.0;
    }

    public double tick(double measuredValue)
    {
	double error = setPoint - measuredValue;
	integral += error;
	double derivative = error - previousError;
	previousError = error;

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
	this.Kp = Kp;
    }

    public double getKp()
    {
	return Kp;
    }

    public void setKi(double Ki)
    {
	this.Ki = Ki;
    }

    public double getKi()
    {
	return Ki;
    }

    public void setKd(double Kd)
    {
	this.Kd = Kd;
    }

    public double getKd()
    {
	return Kd;
    }
}
