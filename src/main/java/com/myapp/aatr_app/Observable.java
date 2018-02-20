/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app;

import java.util.Observer;

/**
 *
 * @author eric
 */
public interface Observable {
    
    public void addObserver(Observer o);
    
    public void removeObserver(Observer o);
    
    public void notifyObserver();
}
