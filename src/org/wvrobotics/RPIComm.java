/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.wvrobotics;

import com.sun.squawk.io.BufferedReader;
import edu.wpi.first.wpilibj.DriverStationLCD;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public class RPIComm {

    private static class Logger extends Thread implements Runnable {
        private PrintStream output;
        private Shooter shooter;
        
        public Logger(PrintStream output, Shooter shooter) {
            this.output = output;
            this.shooter = shooter;
        }

        public void run() {
            while (true) {
                output.println(System.currentTimeMillis() + "," + shooter.getPWM() + "," + shooter.getRPM());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static class Targeting extends Thread implements Runnable {
        private PrintStream output;
        private BufferedReader input;
        private DriverStationLCD dLCD;
        private boolean noRectangle = true;
        private double distance = 0.0;
        private double rpm = 0.0;
        private double azimuth = 0.0;
        private double tilt = 0.0;
        
        public Targeting(PrintStream output, BufferedReader input) {
            this.output = output;
            this.input = input;
            this.dLCD = DriverStationLCD.getInstance();
        }

        public double getDistance() {
            return distance;
        }

        public double getRPM() {
            return rpm;
        }

        public double getAzimuth() {
            return azimuth;
        }

        public double getTilt() {
            return tilt;
        }

        public void run() {
            while (true) {
                try {
                    output.println("\n");
		    Thread.sleep(100);
                    String currentValue = input.readLine();
                    System.out.println(currentValue);
                    /*if (currentValue.equals("No rectangle"))
                        noRectangle = true;
                    else {
                        int varIndex = 0;
                        for (int i = 0; i < currentValue.length(); i++) {
                            if (currentValue.charAt(i) == ';') {
                                double choppedValue = Float.parseFloat(currentValue.substring(0, i));
                                currentValue = currentValue.substring(i + 1);
                                i = 0;
                                switch (varIndex) {
                                    case 0:
                                        distance = choppedValue;
                                    case 1:
                                        rpm = choppedValue;
                                    case 2:
                                        azimuth = choppedValue;
                                    case 3:
                                        tilt = choppedValue;
                                }
                                varIndex++;
                            }
                        }
                    }*/

                String spaces = "";
                for (int i = 0; i < dLCD.kLineLength; ++i) {
                    spaces += " ";
                }
                dLCD.println(DriverStationLCD.Line.kUser2, 1, currentValue + spaces);
                dLCD.updateLCD();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //dLCD.println(DriverStationLCD.Line.kUser3, 1, "Target Distance: " + (int) distance + spaces);
                //dLCD.println(DriverStationLCD.Line.kUser4, 1, "Target RPM: " + (int) rpm + spaces);
                //dLCD.println(DriverStationLCD.Line.kUser5, 1, "Current Azimuth: " + (int) azimuth + spaces);
                //if (noRectangle)
                //    dLCD.println(DriverStationLCD.Line.kUser6, 1, "No rectangle" + spaces);
                //else
                //    dLCD.println(DriverStationLCD.Line.kUser6, 1, spaces);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private SocketConnection rpiLogger;
    private PrintStream loggerOutput;
    private SocketConnection rpiTargeting;
    private BufferedReader targetingInput;
    private PrintStream targetingOutput;
    private Shooter shooter;
    private Logger logger;
    private Targeting targeting;

    public RPIComm(Shooter shooter) {
        this.shooter = shooter;

        try {
            //rpiLogger = (SocketConnection) Connector.open("socket://10.33.41.42:1433");
            //loggerOutput = new PrintStream(rpiLogger.openDataOutputStream());

            rpiTargeting = (SocketConnection) Connector.open("socket://10.33.41.42:3341");
            targetingOutput = new PrintStream(rpiTargeting.openDataOutputStream());
            targetingOutput.println("test");
            targetingInput = new BufferedReader(new InputStreamReader(rpiTargeting.openDataInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //logger = new Logger(loggerOutput, shooter);
        //logger.start();

        targeting = new Targeting(targetingOutput, targetingInput);
        targeting.start();
    }

    public double getRPM() {
        return targeting.getRPM();
    }
 }
