package org.wvrobotics;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;

import org.wvrobotics.util.RobotThread;

public class BridgeManipulator extends RobotThread {
	private Jaguar controller;
	private volatile boolean armUp;
	private volatile boolean running;

	public BridgeManipulator(int speedPort) {
		controller = new Jaguar(speedPort);
		armUp = true;
		running = false;
	}
	
	public void set(double value) {
		controller.set(value);
	}

	public boolean getArmUp() {
		return armUp;
	}

	public void toggle() {
		if (running) return;
		armUp = !armUp;
		if (armUp) {
			controller.set(0.3);
			Timer.delay(1.7);
			controller.set(0);
		} else {
			controller.set(-0.8);
			Timer.delay(1.2);
			controller.set(0);
		}
	}
	
	public void lift() {
		if (running) return;
		armUp = true;
		if (armUp) {
			controller.set(0.3);
			Timer.delay(1.7);
			controller.set(0);
		} else {
			controller.set(-0.8);
			Timer.delay(1.2);
			controller.set(0);
		}
	}
	
	public void lower() {
		if (running) return;
		armUp = false;
		if (armUp) {
			controller.set(0.3);
			Timer.delay(1.7);
			controller.set(0);
		} else {
			controller.set(-0.8);
			Timer.delay(1.2);
			controller.set(0);
		}
	}
	
	public void run() {
		running = true;
		if (armUp) {
			controller.set(0.3);
			Timer.delay(1.7);
			controller.set(0);
		} else {
			controller.set(-0.8);
			Timer.delay(1.2);
			controller.set(0);
		}
		running = false;
	}
}
