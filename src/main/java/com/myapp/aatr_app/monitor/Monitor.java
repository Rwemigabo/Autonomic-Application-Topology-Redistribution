/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor;

import com.myapp.aatr_app.Observable;
import com.myapp.aatr_app.Observer;
import com.myapp.aatr_app.monitor.data.StatisticsLog;
import com.spotify.docker.client.exceptions.DockerException;
import java.util.ArrayList;

/**
 *TBD monitor/ notify other monitor instances
 * @author eric
 * Capture and record new statistics to the database every set number of minutes or seconds.
 */
public class Monitor implements Observer, Observable{
    private final StatisticsLog stats;
    private final int ID;
    private final String id;
    private final ArrayList<Sensor> sens;
    private final ArrayList<Observer> obs;
    
    //private Observable obs = null;
    /**
     * 
     * @param id an id  for the new monitor
     * @param ID the id for the container to be monitored
     */
    public Monitor(int id, String ID){
        this.sens = new ArrayList<>();
        this.ID = id;
        this.id = ID;
        this.stats = new StatisticsLog(ID);
        this.obs = new ArrayList<>();
    }
    
    @Override
   public void update() {
      System.out.println( "whaaatttt" ); 
   }

    @Override
    public void update(String context, long metric) {
        long metric2 = 0;
        for (Sensor sen : sens) {
            if (!sen.sensorContext().equals(context)){
                metric2 = sen.getLogValue();
            }
        }if(context.equals("CPU")){
            this.stats.newStatistic(metric, metric2);
        
        }else{this.stats.newStatistic(metric2, metric);}
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
       
    }
    
    public void startMonitoring() throws DockerException, InterruptedException{
        for (Sensor sen : sens) {
            if (sen.sensorContext().equals("CPU")){
                sen.watchContainer(this.id, 25, 75);
            }else{sen.watchContainer(this.id, 1000, 3000);}
        }
    }
    
    public void addSensor(Sensor s){
        this.sens.add(s);
    }
    
    public int getID(){
    return this.ID;
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
