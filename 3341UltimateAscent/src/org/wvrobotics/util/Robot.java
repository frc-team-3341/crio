package org.wvrobotics.util;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * @author Vineel
 *
 * This class exists to allow different subsystems of the robot to communicate
 * over the state of the robot. It is a singleton.
 *
 * Robot also acts as a utilities class, with functions that are universally
 * used but do not fit into one specific place.
 */
public class Robot {
    public static boolean isDisabled() {
        return DriverStation.getInstance().isDisabled();
    }

    public static boolean isTeleop() {
        return DriverStation.getInstance().isOperatorControl();
    }

    public static boolean isAutonomous() {
        return DriverStation.getInstance().isAutonomous();
    }

    public static void pause(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {}
    }
}
