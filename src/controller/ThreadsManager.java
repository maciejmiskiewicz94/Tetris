package controller;

import controller.interfaces.Manager;
import processing.ProcessingUnit;

import java.util.ArrayList;

/**
 * Created by Borys on 11/17/16.
 */
public class ThreadsManager {

    private Manager manager;

    private ArrayList<ProcessingUnit> threads;
    private int numberOfThreads;

    public ThreadsManager(int n){
        this.numberOfThreads = n;
        threads = new ArrayList<>();
    }

    public void initializeThreads(){
        boolean result = true;
        for(int i=0;i<numberOfThreads;i++){
            ProcessingUnit pu = new ProcessingUnit(i);
            threads.add(pu);
            pu.start();
        }
    }
}
