/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aatr2.monitor.data;
import java.sql.Timestamp;
/**
 *
 * @author eric
 */
public class Statistic {
    private final long cpu;
    private final long memory;
    //private final int BIO;
    //private final int network;
    private final Timestamp date;
    
    public Statistic(long setcpu, long setmemory){
        this.cpu = setcpu;
        //this.BIO = setBIO;
        this.memory = setmemory;
        //this.network = setnetwork;
        this.date = new Timestamp(System.currentTimeMillis());

    }

    public long getCpu(){
        return this.cpu;
    }

    public long getMemory(){
        return this.memory;
    }

//    public int getBIO() {
//        return this.BIO;
//    }
//
//    public int getNetwork() {
//        return this.network;
//    }
//
    public Timestamp getTimestamp() {
        return this.date;
    }
    
    
}
