package org.wvrobotics;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Turret {
	private Victor controller;
	public Turret(int controllerport){
		controller = new Victor(controllerport);
	}
	public void setAzimuth(int azimuth){
		double pwm_value = 0.1;
		if(azimuth > 0){
			pwm_value *= -1;
		}
	}
}
