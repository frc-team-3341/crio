package org.wvrobotics;

import edu.wpi.first.wpilibj.DriverStationLCD;
import java.util.Vector;

public class DriverStationInterface {

    private DriverStationLCD dLCD;
    private String spaces;
    private Vector lcdData;

    public DriverStationInterface() {
        spaces = "";
        for (int i = 0; i < DriverStationLCD.kLineLength; i++) {
            spaces += " ";
        }

        lcdData = new Vector();
    }

    private void updateLCD() {
        dLCD.println(DriverStationLCD.Line.kUser1, 1,
                (String)lcdData.elementAt(0) + spaces);
        dLCD.println(DriverStationLCD.Line.kUser2, 1,
                (String)lcdData.elementAt(1) + spaces);
        dLCD.println(DriverStationLCD.Line.kUser3, 1,
                (String)lcdData.elementAt(2) + spaces);
        dLCD.println(DriverStationLCD.Line.kUser4, 1,
                (String)lcdData.elementAt(3) + spaces);
        dLCD.println(DriverStationLCD.Line.kUser5, 1,
                (String)lcdData.elementAt(4) + spaces);
        dLCD.println(DriverStationLCD.Line.kUser6, 1,
                (String)lcdData.elementAt(5) + spaces);
        dLCD.updateLCD();
    }

    public void println(String input) {
        int lineCount = (int) Math.ceil((double)input.length() / (double)DriverStationLCD.kLineLength);
        if (lcdData.size() < 6 - lineCount)
            lcdData.addElement(input);
        else {
            for (int i = 0; i <= lineCount; i++)
                lcdData.removeElementAt(lcdData.size() - 1);
            for (int i = 0; i <= lineCount; i++) {
                lcdData.addElement(
                        input.substring(i * DriverStationLCD.kLineLength,
                                   (i + 1) * DriverStationLCD.kLineLength - 1));
            }
        }
        updateLCD();
    }

}
