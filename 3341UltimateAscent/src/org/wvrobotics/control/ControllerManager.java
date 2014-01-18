package org.wvrobotics.control;

import java.util.Hashtable;

/**
 *
 * @author Vineel
 */
public class ControllerManager {
    private static ControllerManager instance;

    private Hashtable controllers;

    private ControllerManager() {
        controllers = new Hashtable();
    }

    public static ControllerManager getInstance() {
        if (instance == null) instance = new ControllerManager();
        return instance;
    }

    public Controller getController(int port) {
    	return getController(port, 13);
    }
    
    public Controller getController(int port, int numButtons) {
        Integer key = new Integer(port);
        if (!controllers.containsKey(key))
            controllers.put(key, new Controller(port, numButtons));
        return (Controller) controllers.get(key);
    }
}
