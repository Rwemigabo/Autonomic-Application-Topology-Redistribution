/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor;

import com.myapp.aatr_app.DockerManager;
import com.myapp.aatr_app.Observable;
import java.util.ArrayList;
import java.util.Observer;

/**
 * A sensor observes the context element specified to it and notifies it's subscribers of any changes.
 * @author eric
 */
public class Sensor implements Observable{
    private final DockerManager dm;
    private final int sensId;
    private ArrayList<Observer> obs = new ArrayList<Observer>();
    private ContextElement property;
    public Sensor(DockerManager dm, int ID){
        this.dm = dm;
        this.sensId = ID;
        
    }
    /**
     * TBD
     * @param name name of the element
     * @param val should be the stream of the data property being monitored by the sensor!! Change from float
     */
    public void setContextEl(String name, float val){
        this.property = new ContextElement(name, val);
    }
    
    @Override
    public void addObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getID() {
        return this.sensId;
    }
    
}
