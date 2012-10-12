package org.wvrobotics;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Counter;

public class Shooter {
	//private final double MAX_SPEED = 23.93893602;
	private Victor shooterController;
	private Servo ballLoaderController;
	
	private boolean enabled;
	private Counter encoder;
	
	private double lastValue;
	
	public Shooter(int controllerPort, int controllerPort2) {
		shooterController = new Victor(controllerPort);
		ballLoaderController = new Servo(controllerPort2);
		ballLoaderController.set(0);
		encoder = new Counter(1, 2);
		encoder.start();
		enabled = false;
		lastValue = 0;
	}

        public double getPWM(){
            return shooterController.get();
        }
	
	public void toggle() {
		//enabled = !enabled;
		//shooterController.set(enabled ? 1 : 0);
	}
	
	public void set(double value) {
		lastValue = value;
		shooterController.set(value);
	}
	
        public double getRPM() {
	    double shooterPeriod = encoder.getPeriod();
	    //System.out.println("Period: " + shooterPeriod);
            return 60/shooterPeriod;
	}

	public void shoot() {
		System.out.println(lastValue);
		ballLoaderController.set(1);
		Timer.delay(2);
		ballLoaderController.set(0);
	}
}
