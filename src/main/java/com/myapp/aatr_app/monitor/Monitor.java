/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor;

import com.myapp.aatr_app.Observable;
import com.myapp.aatr_app.Observer;
import com.myapp.aatr_app.monitor.data.Statistic;
import com.myapp.aatr_app.monitor.data.StatisticsLog;

/**
 *
 * @author eric
 * Capture and record new statistics to the database every set number of minutes or seconds.
 */
public class Monitor implements Observer, Observable{

    private Statistic stat;
    private StatisticsLog stats;
    private int ID;
    
    
    public Monitor(int id){
        this.ID = id;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObserver(java.util.Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObserver(java.util.Observer o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyObserver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
