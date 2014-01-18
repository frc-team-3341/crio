package org.wvrobotics.control;

import java.util.Vector;

import org.wvrobotics.util.Robot;
import org.wvrobotics.util.RobotThread;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author Vineel
 */
public class Controller extends RobotThread {
	public static final int TRIGGER = 1; // trigger is button #1

	private Joystick joystick;
	private int port;

	private volatile boolean[] buttons;
	private volatile double x;
	private volatile double y;
	private volatile double z;
	private volatile double throttle;

	private volatile Vector buttonListeners;
	private volatile Vector joystickListeners;

	public Controller(int port) {
		this(port, 13);
	}

	public Controller(int port, int numButtons) {
		this.port = port;
		joystick = new Joystick(port);

		buttons = new boolean[numButtons];

		x = joystick.getX();
		y = joystick.getY();
		z = joystick.getZ();
		throttle = joystick.getThrottle();

		buttonListeners = new Vector();
		joystickListeners = new Vector();

		start();
	}
	public boolean equals(Object other){
		return port == ((Controller) other).getPort();
	}
	public void addButtonListener(ButtonListener listener) {
		buttonListeners.addElement(listener);
	}

	public void removeButtonListener(ButtonListener listener) {
		buttonListeners.removeElement(listener);
	}

	public void addJoystickListener(JoystickListener listener) {
		joystickListeners.addElement(listener);
	}

	public void removeJoystickListener(JoystickListener listener) {
		joystickListeners.removeElement(listener);
	}

	public int getPort() {
		return port;
	}

	public Joystick getJoystick() {
		return joystick;
	}

	public boolean getButton(int button) {
		return buttons[button - 1];
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getThrottle() {
		return throttle;
	}

	public void run() {
		while (!interrupted()) {
			// handle buttons
			for (int i = 0; i < buttons.length; i++) {
				boolean state = joystick.getRawButton(i + 1);

				if (buttons[i] != state) {
					ButtonEvent e = new ButtonEvent(this, i + 1, state);
					if (state) { // button is pressed
						triggerButtonPressed(e);
					} else {
						triggerButtonReleased(e);
						triggerButtonTyped(e);
					}
					buttons[i] = state;
				}
			}

			// handle joystick and throttle
			double newX = joystick.getX();
			double newY = joystick.getY();
			double newZ = joystick.getZ();
			double newThrottle = joystick.getThrottle();
			JoystickEvent e = new JoystickEvent(this, newX, newY, newZ, newThrottle);
			if ((x != newX) || (y != newY) || (z != newZ)) {
				x = newX;
				y = newY;
				z = newZ;
				triggerJoystickMoved(e);
			}
			if (throttle != newThrottle) {
				triggerThrottleMoved(e);
				throttle = newThrottle;
			}

			Robot.pause(10); // don't hog cpu
		}
	}

	private void triggerButtonPressed(ButtonEvent e) {
		for (int i = 0; i < buttonListeners.size(); i++) {
			ButtonListener listener = (ButtonListener) buttonListeners
					.elementAt(i);
			listener.buttonPressed(e);
		}
	}

	private void triggerButtonReleased(ButtonEvent e) {
		for (int i = 0; i < buttonListeners.size(); i++) {
			ButtonListener listener = (ButtonListener) buttonListeners
					.elementAt(i);
			listener.buttonReleased(e);
		}
	}

	private void triggerButtonTyped(ButtonEvent e) {
		for (int i = 0; i < buttonListeners.size(); i++) {
			ButtonListener listener = (ButtonListener) buttonListeners
					.elementAt(i);
			listener.buttonTyped(e);
		}
	}

	private void triggerJoystickMoved(JoystickEvent e) {
		for (int i = 0; i < joystickListeners.size(); i++) {
			JoystickListener listener = (JoystickListener) joystickListeners
					.elementAt(i);
			listener.joystickMoved(e);
		}
	}

	private void triggerThrottleMoved(JoystickEvent e) {
		for (int i = 0; i < joystickListeners.size(); i++) {
			JoystickListener listener = (JoystickListener) joystickListeners
					.elementAt(i);
			listener.throttleMoved(e);
		}
	}
}
