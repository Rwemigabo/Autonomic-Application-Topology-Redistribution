/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import com.myapp.aatr_app.monitor.Sensor;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import java.util.ArrayList;

/**
 *
 * @author eric
 */
public class SensorManager {
    private ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    private final DockerManager dm;
    
    public SensorManager() throws DockerCertificateException, DockerException, InterruptedException{
        dm = new DockerManager();
    }
    
    public Sensor newSensor(String context){
        int newID = sensors.size()+1;
        Sensor sens = new Sensor(dm, newID, context); 
        sensors.add(sens);
        return sens;
    }
    
    public void newSensor2(String context){
        int newID = sensors.size()+1;
        Sensor sens = new Sensor(dm, newID, context); 
        sensors.add(sens);
    }
    
    public ArrayList<Sensor> getAllSensors(){
        return this.sensors;
    }
    
    public Sensor getSeneor(int ID){
        for (Sensor sen : sensors) {
            if (sen.getID() == ID){
                return sen;
            }
        }return null;
    }
    
//    public ArrayList<Sensor> getSystemStats(Timestamp t1, Timestamp t2){
//        
//        ArrayList<Statistic> s = new ArrayList<>();
//        for (Statistic stat : monitorstats) {
//            if (stat.getTimestamp().after(t1) && stat.getTimestamp().before(t2)){
//                s.add(stat);
//            }
//        }return s;
//    }
}
