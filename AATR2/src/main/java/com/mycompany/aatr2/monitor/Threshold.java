/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aatr2.monitor;

/**
 *To define the boundaries for a particular system property.
 * @author eric
 */
public class Threshold {
    private float lowerBound;
    private float upperBound;
    
    public Threshold(float upper, float lower){
        this.lowerBound = lower;
        this.upperBound = upper;
    }
    
    public void setUpperBound(float upper){
        this.upperBound = upper;
    }
    
    public void setLowerBound(float lower){
        this.lowerBound = lower;
    }
    
    public float getLowerBound(){
        return this.lowerBound;
    
    }
    
    public float getUpperBound(){
        return this.upperBound;
    }
    
}
