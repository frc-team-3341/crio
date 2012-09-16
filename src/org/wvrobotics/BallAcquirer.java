package org.wvrobotics;

import edu.wpi.first.wpilibj.Victor;

public class BallAcquirer {
	public static final int MOTOR_REVERSE = -1;
	public static final int MOTOR_STOPPED = 0;
	public static final int MOTOR_UP = 1;

	private Victor front;
	private Victor back;

	private boolean enabled;
	private boolean reversed;

	public BallAcquirer(int frontPort, int backPort) {
		front = new Victor(frontPort);
		back = new Victor(backPort);
		
		enabled = false;
		reversed = false;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
		
		if (enabled)
			start();
		else
			stop();
	}
	
	public void reverse() {
		reversed = !reversed;
		
		if (enabled)
			start();
		else
			stop();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		if (enabled)
			start();
		else
			stop();
	}
	
	public void toggleEnabled() {
		enabled = !enabled;
		
		if (enabled)
			start();
		else
			stop();
	}
	
	public void start() {
		if (!reversed) {
			front.set(MOTOR_REVERSE);
			back.set(MOTOR_UP);
		} else {
			front.set(MOTOR_UP);
			back.set(MOTOR_REVERSE);
		}
	}

	public void stop() {
		front.set(MOTOR_STOPPED);
		back.set(MOTOR_STOPPED);
	}
}
