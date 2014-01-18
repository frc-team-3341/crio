package org.wvrobotics;

import edu.wpi.first.wpilibj.Jaguar;

public class Feeder {

    private Jaguar feederMotor;

    public Feeder(int feederPWM) {
        feederMotor = new Jaguar(feederPWM);
    }

    public void setPWM(double inputPWM) {
        feederMotor.set(inputPWM);
    }

    public void feed() {
            Runnable r = new Runnable() {
                public void run() {
                    try{
                        setPWM(0.5);
                        Thread.sleep(250);
                        setPWM(0);
                        Thread.sleep(250);
                        setPWM(-0.5);
                        Thread.sleep(200);
                        setPWM(-1);
                        Thread.sleep(100);
                        setPWM(0);
                    } catch (InterruptedException ex) {
                    }
                }
            };
            new Thread(r).start();
    }

}
