/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import com.myapp.aatr_app.monitor.Monitor;
import com.spotify.docker.client.exceptions.DockerException;
import java.util.ArrayList;

/**
 * Creates new monitors
 * Manages all the working monitors
 *
 * @author eric
 */
public class MonitorManager {

    private final ArrayList<Monitor> monitors = new ArrayList<>();
    private final SensorManager sm;

    private static final MonitorManager inst = new MonitorManager();

    private MonitorManager() {
        sm = SensorManager.getInstance();
    }

    public static MonitorManager getInstance() {
        return inst;
    }

    /**
     *New Monitor created for a container with
     * @param ID of the container going to be monitored
     */
    public void newMonitor(String ID) throws DockerException, InterruptedException {
        DockerManager dm = DockerManager.getInstance();
        int newID = monitors.size() + 1;
        Monitor mon = new Monitor(newID, ID);
        mon.addSensor(this.sm.newSensor("CPU"));
        mon.addSensor(this.sm.newSensor("Memory"));
        monitors.add(mon);
        if(dm.getContainer(ID).status().equals("running")){
            startMonitor(newID);
        }
    }

    public void startMonitor(int ID) throws DockerException, InterruptedException {
        for (Monitor monitor : monitors) {
            if (monitor.getID() == ID) {
                monitor.startMonitoring();
            }
        }
    }

    public void startMonitors() throws DockerException, InterruptedException {
        for (Monitor monitor : monitors) {
            monitor.startMonitoring();
        }
    }

}
