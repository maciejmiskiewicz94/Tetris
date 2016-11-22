package controller;

import controller.interfaces.Manager;
import data.ProcessingTile;
import data.Well;
import gui.MainGui;
import processing.ProcessingController;
import processing.ProcessingUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Borys on 11/17/16.
 */
public class ThreadsManager {

    public static ArrayList<Well> results = new ArrayList<>();
    public static Comparator<Well> cm = (o1, o2) -> {
        if (o1.getQuality() < o2.getQuality()) return 1;
        else if (o1.getQuality() > o2.getQuality()) return -1;
        else return 0;
    };

    MainGui guiRef;

    private Manager manager;
    private ArrayList<ProcessingUnit> threads = new ArrayList<>();
    ArrayList<ProcessingTile> tiles;
    ArrayList<Well> wells;
    private int numberOfThreads;
    private int totalNumberOfTiles;

    private Lock lock;

    public ThreadsManager(int n, ArrayList<ProcessingTile> tiles, ArrayList<Well> wells, int numberOfTiles, MainGui gui){
        this.numberOfThreads = n;
        this.threads = new ArrayList<>();
        this.tiles=tiles;
        this.wells=wells;
        this.totalNumberOfTiles=numberOfTiles;
        this.lock=new ReentrantLock();
        ThreadsManager.results = new ArrayList<>();
        guiRef=gui;
    }

    public void initializeThreads(boolean fromFile) throws InterruptedException {
        ThreadsManager.results.add(wells.get(0));
        ProcessingController controller = new ProcessingController(1,tiles,wells,numberOfThreads,lock, totalNumberOfTiles,fromFile, guiRef);
        controller.start();
        controller.join();
        System.out.println("Controller finished processing!");
//        boolean result = true;
//        for(int i=0;i<numberOfThreads;i++){
//            ProcessingUnit pu = new ProcessingUnit(i, wells.get(0),tiles,numberOfThreads,lock);
//            threads.add(pu);
//            pu.start();
//        }
    }
    public interface Communicator{
        public void displayOneStepOfComputation(ArrayList<Well> wells);
        public void computationEnded();
    }
}
