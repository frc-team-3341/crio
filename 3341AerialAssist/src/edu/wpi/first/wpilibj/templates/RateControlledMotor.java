/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author edward
 */
/**
 * A wrapper around a SpeedController that is used as a PIDOutput for the
 * purpose of controlling the rate at which something spins.
 */
public final class RateControlledMotor implements PIDOutput {

    /**
     * Motor to adjust power on.
     */
    public final SpeedController _Motor;

    /**
     * Construct a new instance and associate a speed controller with the
     * object.
     *
     * @param motor The speed controller that is controlling the motor.
     */
    public RateControlledMotor(SpeedController motor) {
        _Motor = motor;
    }

    /**
     * Apply power value computed by PID to the motor.
     *
     * <p>
     * The standard PID system basis the power output on the amount of "error".
     * This results in the power going to 0 as the error goes to 0. While this
     * works well for a distance based PID (where you want to stop once you get
     * to where you are going). It does not work well for a rate system (where
     * you want to keep spinning at the same rate).</p>
     *
     * <p>
     * Instead of treating the value passed as a new power level, we treat it as
     * an adjustment to the current power level when we apply it.</p>
     *
     * @param output Power value to apply (computed by PID loop). Goes to zero
     * as we reach the desired spin rate.
     */
    public void pidWrite(double output) {
        // Treat new PID computed power output as an "adjustment"
        double rateOutput = _Motor.get() + output;
        System.out.println("output" + output);
        System.out.println("rateoutput" + rateOutput);
        rateOutput = Math.min(1.0, rateOutput);
        rateOutput = Math.max(-1.0, rateOutput);
        System.out.println("rateouput2");
        _Motor.set(rateOutput);
    }

}
