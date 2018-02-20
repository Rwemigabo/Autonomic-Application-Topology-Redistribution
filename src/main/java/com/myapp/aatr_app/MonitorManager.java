/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import com.myapp.aatr_app.monitor.Monitor;
import java.util.ArrayList;

/**
 *Manages all the working monitors
 * @author eric
 */
public class MonitorManager{
    private Monitor mon;
    private ArrayList<Monitor> monitors = new ArrayList<Monitor>();
    public MonitorManager(){
    
    }
    
    public void newMonitor(){
        int newID = monitors.size()+1;
        mon = new Monitor(newID); 
        monitors.add(mon);
    }
}
