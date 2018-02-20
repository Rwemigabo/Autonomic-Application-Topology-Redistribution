/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor.data;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author eric
 */
public class StatisticsLog {
    private ArrayList<Statistic> monitorstats;
    
    public StatisticsLog(){
        
    }
    
    public ArrayList<Statistic> getSystemStats(){
        return this.monitorstats;
    }
    
    public Statistic getSystemStat(Timestamp time){
        for (Statistic stat : monitorstats) {
            if (stat.getTimestamp().equals(time)){
                return stat;
            }
        }return null;
    }
    
    public ArrayList<Statistic> getSystemStats(Timestamp t1, Timestamp t2){
        
        ArrayList<Statistic> s = new ArrayList<>();
        for (Statistic stat : monitorstats) {
            if (stat.getTimestamp().after(t1) && stat.getTimestamp().before(t2)){
                s.add(stat);
            }
        }return s;
    }
    
    public void newStatistic(Statistic s){
        this.monitorstats.add(s);
    }
    
}
