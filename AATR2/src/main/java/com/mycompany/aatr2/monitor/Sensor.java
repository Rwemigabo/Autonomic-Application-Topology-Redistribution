/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aatr2.monitor;

import com.mycompany.aatr2.DockerManager;
import com.mycompany.aatr2.Observable;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.CpuStats;
import com.spotify.docker.client.messages.MemoryStats;
import java.util.ArrayList;
import com.mycompany.aatr2.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A sensor observes the context element specified to it and notifies it's
 * subscribers of any changes. A context element name has to be provided and
 * that property will be set to be watched.
 *
 * @author eric
 */
public class Sensor extends Thread implements Observable{

    private final int sensId;
    private final ArrayList<Observer> obs = new ArrayList<>();
    private final ContextElement property;
    private String name;
    private CpuStats cpu;
    private MemoryStats mem;
    private final float minThr;
    private final float maxThr;
    private final String contID;
    
    public Sensor(int ID, String context, float min, float max, String cid) {
        this.sensId = ID;
        this.name = context;
        this.property = new ContextElement(context);
        this.minThr = min;
        this.maxThr = max;
        this.contID = cid;
        this.property.setThreshold(max, max);
    }

    /**
     * Notify the observers every 30 seconds of the current status or notify if
     * the threshold is crossed .
     *
     * @throws DockerException
     * @throws InterruptedException
     */
    public void watchContainer() throws DockerException, InterruptedException {
        DockerManager dm = DockerManager.getInstance();
        if (this.property.getName().equals("CPU")) {
            System.out.print("\n Monitoring CPU of conitainer " + this.contID);
            this.property.setThreshold(this.maxThr, this.minThr);
            while (dm.getContainer(this.contID).state().contains("running")) {
                cpu = dm.getContainerStats(this.contID).cpuStats();
                long cpuUsage= cpu.cpuUsage().totalUsage();
                int perCpu = cpu.cpuUsage().percpuUsage().size();
                long prevCPU = dm.getContainerStats(this.contID).precpuStats().cpuUsage().totalUsage();
                long prevSystem = dm.getContainerStats(this.contID).precpuStats().systemCpuUsage();
                long systemUsage = cpu.systemCpuUsage();
                
                long cpuPer = calculateCPU(cpuUsage, prevCPU, systemUsage, prevSystem, perCpu);
                monitorThreshold(cpuPer);
                scheduleNotification(cpu.systemCpuUsage());
                
            }
        } else if (this.property.getName().equals("Memory")) {
            System.out.print("\n Monitoring Memory of conitainer " + this.contID);
            property.setThreshold(this.maxThr, this.minThr);
            while (dm.getContainer(this.contID).state().contains("running")) {
                mem = dm.getContainerStats(this.contID).memoryStats();
                monitorThreshold(mem.usage());
                
            }
        }
    }
    
    @Override
    public void run(){
        try {
            watchContainer();
        } catch (DockerException | InterruptedException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to monitor the threshold of the given metric notify the observers
     * if value is over or under threshold
     *
     * @param metric the metric being monitored by the sensor
     */
    public void monitorThreshold(final long metric) {
        if (metric >= property.getThreshold().getUpperBound() || metric <= property.getThreshold().getLowerBound()) {
            System.out.print("\n Notifying monitor of container "+ this.contID + " "+ property.getName()+ " " + metric);
            notifyObservers(metric);
        } else {
        }

    }
    
    public long calculateCPU(long totalUsage, long prevCPU, long totalSystUse, long prevSystem, int perCpuUsage){
        
        long cpuPerc = 0;
        long cpuDelta = totalUsage - prevCPU;
        long systemDelta = totalSystUse - prevSystem;
        
        if(systemDelta > 0.0 && cpuDelta > 0.0){
            cpuPerc = ((cpuDelta/systemDelta) * (perCpuUsage))*100;
        }
        
        return cpuPerc;
    
    }

    public void scheduleNotification(final long metric) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                notifyObservers(metric);
            }
        }, 1, 1 * 30000);
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
    public void notifyObservers(long metric) {
        obs.forEach((ob) -> {
            ob.update(this.name, metric);
        });
    }

    public int getID() {
        return this.sensId;
    }

    public String sensorContext() {
        return this.name;
    }

    public void setContext(String ctxt, long min, long max) {
        this.name = ctxt;
    }

    public long getLogValue() {
        if (this.name.equals("Memory")) {
            return mem.usage();
        } else if (this.name.equals("CPU")) {
            return cpu.systemCpuUsage();
        }
        return 0;
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
