/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.aatr_app.monitor.data;
import java.sql.Timestamp;
/**
 *
 * @author eric
 */
public class Statistic {
    private final int cpu;
    private final int memory;
    private final int BIO;
    private final int network;
    private final Timestamp date;
    
    public Statistic(int setcpu, int setmemory, int setBIO, int setnetwork){
        this.cpu = setcpu;
        this.BIO = setBIO;
        this.memory = setmemory;
        this.network = setnetwork;
        this.date = new Timestamp(System.currentTimeMillis());

    }

    public int getCpu(){
        return this.cpu;
    }

    public int getMemory(){
        return this.memory;
    }

    public int getBIO() {
        return this.BIO;
    }

    public int getNetwork() {
        return this.network;
    }

    public Timestamp getTimestamp() {
        return this.date;
    }
    
    
}
