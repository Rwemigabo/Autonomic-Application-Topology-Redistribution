/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor;

import com.myapp.aatr_app.DockerManager;
import com.myapp.aatr_app.Observable;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.CpuStats;
import com.spotify.docker.client.messages.MemoryStats;
import java.util.ArrayList;
import java.util.Observer;

/**
 * A sensor observes the context element specified to it and notifies it's subscribers of any changes.
 * A context element name has to be provided and that property will be set to be watched.
 * @author eric
 */
public class Sensor implements Observable{
    private final DockerManager dm;
    private final int sensId;
    private final ArrayList<Observer> obs = new ArrayList<>();
    private ContextElement property;
    private String name;
    private CpuStats cpu;
    //private BlockIoStats bio;
    private MemoryStats mem;
    public Sensor(DockerManager dm, int ID, String context){
        this.dm = dm;
        this.sensId = ID;
        this.name = context;
        this.property = new ContextElement(context);
    }
    
    /**
     * TBD: Split up into different methods each creating new CElement and setting the name of the element
     * 
     * @param id container id
     * @throws DockerException
     * @throws InterruptedException 
     */
    public void watchContainer(String id, float min, float max) throws DockerException, InterruptedException{
//        if (this.property.getName().equals("BlockIO")){
//            bio = this.dm.getContainerStats(id).blockIoStats();
//            property.setThreshold(max, min);
//        } 
        if(this.property.getName().equals("CPU")){
            cpu = this.dm.getContainerStats(id).cpuStats();
            property.setThreshold(max, min);
        }else if(this.property.getName().equals("Memory")){
            this.dm.getContainerStats(id).memoryStats();
            property.setThreshold(max, min);
        }
    }
    
    public void outsideThreshold(){
        notifyObservers();
    }
    
    @Override
    public void addObserver(Observer o) {
        obs.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        obs.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer ob : obs) {
            
        }
    }

    public int getID() {
        return this.sensId;
    }
    
    public String sensorContext(){
        return this.name;
    }
    
    public void setContext(String ctxt){
        this.name = ctxt;
    }
    
    public long getLogValue(){
        if(this.name.equals("Memory")){
            return mem.usage();
        }else if(this.name.equals("CPU")){
            return cpu.systemCpuUsage();
        }
        return 0;
    }
}
