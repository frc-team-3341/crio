package org.wvrobotics.control;

/**
 *
 * @author Vineel
 */
public class JoystickEvent {
    private Controller source;
    private double x;
    private double y;
    private double z;
    private double throttle;
    private long time;

    public JoystickEvent(Controller source, double x, double y, double z, double throttle) {
        this.source = source;
        this.x = x;
        this.y = y;
        this.z = z;
        this.throttle = throttle;
        time = System.currentTimeMillis();
    }

    public Controller getSource() {
        return source;
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

    public long getTime() {
        return time;
    }
}
