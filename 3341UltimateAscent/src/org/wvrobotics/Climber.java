package org.wvrobotics;

import edu.wpi.first.wpilibj.Jaguar;

public class Climber {
    
    private Jaguar climberMotor;
    private AccelerometerReader acc;
    private boolean isAngling = false;
    
    public Climber(int climberPWM, AccelerometerReader acc) {
        climberMotor = new Jaguar(climberPWM);
        this.acc = acc;
    }

    public void setPWM(double speed) {
        if (!isAngling)
            climberMotor.set(speed);
    }

    public void setShooterAngle(double set) {
        isAngling = true;
        while (true) {
            double measured = acc.getAbsoluteAngle();
            if (Math.abs(set - measured) < 0.1 || Math.abs(measured) > 40.0) {
                System.out.println("Stopping");
                break;
            }
            if (measured > set) {
                climberMotor.set(0.2);
                System.out.println("Going down");
            } else if (measured < set) {
                climberMotor.set(-0.2);
                System.out.println("Going up");
            }
        }
        climberMotor.set(0.0);
        isAngling = false;
    }

    public void pullRobotUp() {
        setPWM(1);
    }

    public void bringRobotDown() {
        setPWM(-1);
    }

    public void stop() {
        setPWM(0);
    }

    public void shrug(){
        Runnable r = new Runnable(){

            public void run() {
                try{
                    setPWM(1);
                    Thread.sleep(1000);
                    setPWM(-1);
                    Thread.sleep(1000);
                } catch(Exception e){}
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
    
}
