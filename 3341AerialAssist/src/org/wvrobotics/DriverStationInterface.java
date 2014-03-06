package org.wvrobotics;

import edu.wpi.first.wpilibj.DriverStationLCD;

public class DriverStationInterface {

    private DriverStationLCD dLCD;
    private String spaces;
    private String[] lcdData;

    public DriverStationInterface() {
        dLCD = DriverStationLCD.getInstance();
        spaces = "";
        for (int i = 0; i < DriverStationLCD.kLineLength; i++)
            spaces += " ";
        lcdData = new String[6];
        for (int i = 0; i < lcdData.length; i++)
            lcdData[i] = "";
    }

    private void updateLCD() {
        dLCD.println(DriverStationLCD.Line.kUser1, 1,
                (String)lcdData[0] + spaces);
        dLCD.println(DriverStationLCD.Line.kUser2, 1,
                (String)lcdData[1] + spaces);
        dLCD.println(DriverStationLCD.Line.kUser3, 1,
                (String)lcdData[2] + spaces);
        dLCD.println(DriverStationLCD.Line.kUser4, 1,
                (String)lcdData[3] + spaces);
        dLCD.println(DriverStationLCD.Line.kUser5, 1,
                (String)lcdData[4] + spaces);
        dLCD.println(DriverStationLCD.Line.kUser6, 1,
                (String)lcdData[5] + spaces);
        dLCD.updateLCD();
    }

    public void setLine(int line, String lineData)
    {
        lcdData[line] = lineData;
        updateLCD();
    }
}
