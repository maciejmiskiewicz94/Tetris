package controller;

import controller.interfaces.Manager;
import data.ProcessingTile;
import data.Well;
import processing.ProcessingUnit;

import java.util.ArrayList;

/**
 * Created by Borys on 11/17/16.
 */
public class ThreadsManager {

    public static Well next;

    private Manager manager;
    private ArrayList<ProcessingUnit> threads;
    ProcessingTile[] tiles;
    ArrayList<Well> wells;
    private int numberOfThreads;

    public ThreadsManager(int n, ProcessingTile[] tiles, ArrayList<Well> wells){
        this.numberOfThreads = n;
        this.threads = new ArrayList<>();
        this.tiles=tiles;
        this.wells=wells;
    }

    public void initializeThreads(){
        boolean result = true;
        for(int i=0;i<numberOfThreads;i++){
            ProcessingUnit pu = new ProcessingUnit(i, wells.get(0),tiles);
            threads.add(pu);
            pu.start();
        }
    }
}
