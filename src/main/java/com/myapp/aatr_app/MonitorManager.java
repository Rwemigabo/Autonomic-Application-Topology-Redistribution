/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import com.myapp.aatr_app.monitor.Monitor;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import java.util.ArrayList;

/**
 *Manages all the working monitors
 * @author eric
 */
public class MonitorManager{
    
    private final ArrayList<Monitor> monitors = new ArrayList<>();
    private final SensorManager sm;
    public MonitorManager() throws DockerCertificateException, DockerException, InterruptedException{
        this.sm = new SensorManager();
    
    }
    
    /**
     * 
     * @param ID of the container going to be monitored
     * creates 
     */
    public void newMonitor(String ID){
        int newID = monitors.size()+1;
        Monitor mon = new Monitor(newID, ID);
        mon.addSensor(sm.newSensor("CPU"));
        mon.addSensor(sm.newSensor("Memory"));;
        monitors.add(mon);
    }
    
    public void startMonitor(int ID) throws DockerException, InterruptedException{
        for (Monitor monitor : monitors) {
            if(monitor.getID() == ID){
                monitor.startMonitoring();
            }
        }
    }
    
    public void startMonitors() throws DockerException, InterruptedException{
        for (Monitor monitor : monitors) {
            monitor.startMonitoring();
        }
    }
}
