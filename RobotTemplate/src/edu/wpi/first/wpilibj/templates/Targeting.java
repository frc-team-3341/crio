package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Targeting {
    
    NetworkTable targetingTable;

    public Targeting() {
        targetingTable = NetworkTable.getTable("targeting");
        targetingTable.putBoolean("connection", false);
        targetingTable.putBoolean("found", false);
        targetingTable.putBoolean("target", false);
        targetingTable.putNumber("distance", 0.0);
        targetingTable.putNumber("horizontalDistance", 0.0);
        //targetingTable.putNumber("angle", 15.0);
        //targetingTable.putNumber("azimuth", 3000.0);
    }

    public boolean isConnected()
    {
        return targetingTable.getBoolean("connection");
    }

    public boolean isFound() {
        return targetingTable.getBoolean("found");
    }

    public boolean getTarget() {
        return targetingTable.getBoolean("target");
    }

    public double getDistance() {
        return targetingTable.getNumber("distance");
    }

    public double getHorizontalDistance() {
        return targetingTable.getNumber("horizontalDistance");
    }

   /* public double getAngle() {
        return targetingTable.getNumber("angle");
    }*/

  /*  public double getAzimuth() {
        return targetingTable.getNumber("azimuth");
    }*/
}