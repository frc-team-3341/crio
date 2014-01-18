package org.wvrobotics;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.ADXL345_I2C;

public class AccelerometerReader {

    private ADXL345_I2C accelerometer;
    private ADXL345_I2C.AllAxes zero;
    private ADXL345_I2C.AllAxes absoluteZero;

    public AccelerometerReader(int accPort) {
        accelerometer = new ADXL345_I2C(accPort, ADXL345_I2C.DataFormat_Range.k4G);
        absoluteZero = new ADXL345_I2C.AllAxes();
        /*old calibration
         absoluteZero.XAxis = -0.18359375;
        absoluteZero.YAxis = 1.05078125;
        absoluteZero.ZAxis = -0.05078125;*/
        absoluteZero.XAxis = -0.15234375;
        absoluteZero.YAxis = 1.046875;
        absoluteZero.ZAxis = -0.01953125;
        zeroReadings();
    }

    public ADXL345_I2C.AllAxes getReading() {
        return accelerometer.getAccelerations();
    }

    public double getRelativeAngle() {
        ADXL345_I2C.AllAxes currentReading = accelerometer.getAccelerations();
        double angle = (zero.XAxis*currentReading.XAxis +
                zero.YAxis*currentReading.YAxis + zero.ZAxis*currentReading.ZAxis) /
                Math.sqrt((MathUtils.pow(zero.XAxis, 2) +
                MathUtils.pow(zero.YAxis, 2) + MathUtils.pow(zero.ZAxis, 2)) *
                (MathUtils.pow(currentReading.XAxis, 2) +
                MathUtils.pow(currentReading.YAxis, 2) + MathUtils.pow(currentReading.ZAxis, 2)));
        if (angle > 0.98)
            angle = Math.sqrt(1 - MathUtils.pow(angle, 2));
        else
            angle = MathUtils.acos(angle);
        angle = angle * 180.0 / Math.PI;
        return angle;
    }

    public double getAbsoluteAngle() {
        ADXL345_I2C.AllAxes currentReading = accelerometer.getAccelerations();
        double angle = (absoluteZero.XAxis*currentReading.XAxis +
                absoluteZero.YAxis*currentReading.YAxis + absoluteZero.ZAxis*currentReading.ZAxis) /
                Math.sqrt((MathUtils.pow(absoluteZero.XAxis, 2) +
                MathUtils.pow(absoluteZero.YAxis, 2) + MathUtils.pow(absoluteZero.ZAxis, 2)) *
                (MathUtils.pow(currentReading.XAxis, 2) +
                MathUtils.pow(currentReading.YAxis, 2) + MathUtils.pow(currentReading.ZAxis, 2)));
        if (angle > 0.98)
            angle = Math.sqrt(1 - MathUtils.pow(angle, 2));
        else
            angle = MathUtils.acos(angle);
        angle = Math.toDegrees(angle);
        return angle;
    }

    public void zeroReadings() {
        System.out.println("Zeroing...");
        zero = accelerometer.getAccelerations();
    }

}
