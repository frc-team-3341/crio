package org.wvrobotics.control;

/**
 *
 * @author Vineel
 */
public interface JoystickListener {
    public void joystickMoved(JoystickEvent e);
    public void throttleMoved(JoystickEvent e);
}
