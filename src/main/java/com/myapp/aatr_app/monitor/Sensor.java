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
import com.myapp.aatr_app.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A sensor observes the context element specified to it and notifies it's
 * subscribers of any changes. A context element name has to be provided and
 * that property will be set to be watched.
 *
 * @author eric
 */
public class Sensor implements Observable {

    private final int sensId;
    private final ArrayList<Observer> obs = new ArrayList<>();
    private ContextElement property;
    private String name;
    private CpuStats cpu;
    //private BlockIoStats bio;
    private MemoryStats mem;

    public Sensor(int ID, String context) {
        this.sensId = ID;
        this.name = context;
        this.property = new ContextElement(context);
    }

    /**
     * Notify the observers every 30 seconds of the current status or notify if
     * the threshold is crossed .
     *
     * @param id container id
     * @param min minimum threshold
     * @param max maximum threshold
     * @throws DockerException
     * @throws InterruptedException
     */
    public void watchContainer(String id, float min, float max) throws DockerException, InterruptedException {
        DockerManager dm = DockerManager.getInstance();
        if (this.property.getName().equals("CPU")) {

            this.property.setThreshold(max, min);
            while (dm.getContainer(id).status().equals("running")) {
                cpu = dm.getContainerStats(id).cpuStats();
                monitorThreshold(cpu.systemCpuUsage());
                scheduleNotification(cpu.systemCpuUsage());
            }
        } else if (this.property.getName().equals("Memory")) {

            property.setThreshold(max, min);
            while (dm.getContainer(id).status().equals("running")) {
                mem = dm.getContainerStats(id).memoryStats();
                monitorThreshold(mem.usage());
            }
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
            notifyObservers(metric);
        } else {
        }

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
        for (Observer ob : obs) {
            ob.update(this.name, metric);
        }
    }

    public int getID() {
        return this.sensId;
    }

    public String sensorContext() {
        return this.name;
    }

    public void setContext(String ctxt) {
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
