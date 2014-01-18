package org.wvrobotics;

import edu.wpi.first.wpilibj.Relay;

public class Shooter {

    private Relay shooterMotor1;
    private Relay shooterMotor2;
    private boolean shooterStatus;

    public Shooter(int shooterRelay1, int shooterRelay2) {
        shooterMotor1 = new Relay(shooterRelay1);
        shooterMotor2 = new Relay(shooterRelay2);
        shooterMotor1.setDirection(Relay.Direction.kReverse);
        shooterMotor2.setDirection(Relay.Direction.kReverse);
        shooterStatus = false;
    }
    
    public void turnOn() {
        shooterMotor1.set(Relay.Value.kOn);
        shooterMotor2.set(Relay.Value.kOn);
        shooterStatus = true;
    }

    public void turnOff() {
        shooterMotor1.set(Relay.Value.kOff);
        shooterMotor2.set(Relay.Value.kOff);
        shooterStatus = false;
    }

    public void toggle() {
        if (shooterStatus)
            turnOff();
        else
            turnOn();
    }
}
