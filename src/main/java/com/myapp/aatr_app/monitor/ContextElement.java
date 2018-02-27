/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor;

/**
 *to define the system properties with monitoring interest and assign or 
 * reassign them a threshold. 
 * 
 * This monitors a stream of data from one of the selected docker container properties(cpu usage, blockI/O, etc)
 * @author eric
 */
public class ContextElement {
    private Threshold thresh;
    private final String name;
    public ContextElement(String nm){
        this.name = nm;
        
    }
    
    public ContextElement(float upper, float lower, String nm){
        this.thresh = new Threshold(upper, lower);
        this.name = nm;
        
    }
    
    public void setThreshold(float upper, float lower){
        this.thresh = new Threshold(upper, lower);
    }
    
    public Threshold getThreshold(){
        return this.thresh;
    }
    
    public String getName(){
        return this.name;
    }
    
}
