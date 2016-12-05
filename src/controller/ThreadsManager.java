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
 * Class which connects main GUI and Processing controller
 */
public class ThreadsManager {

    public static ArrayList<Well> results = new ArrayList<>();
    public static Comparator<Well> cm = (o1, o2) -> {
        if (o1.getQuality() < o2.getQuality()) return 1;
        else if (o1.getQuality() > o2.getQuality()) return -1;
        else return 0;
    };
    public static int bounded = -1;
    public  static boolean serializeOnDemand = false;

    public static boolean stopped = false;

    Communicator guiRef;

    ArrayList<ArrayList<ProcessingTile>> tiles;
    ArrayList<Well> wells;
    private int numberOfThreads;
    private int totalNumberOfTiles;

    private Lock lock;

    /**
     * Basic constructor
     * @param n Total number
     * @param tiles
     * @param wells
     * @param numberOfTiles
     * @param gui
     */
    public ThreadsManager(int n, ArrayList<ArrayList<ProcessingTile>>  tiles, ArrayList<Well> wells, int numberOfTiles, Communicator gui){
        this.numberOfThreads = n;
        this.tiles=tiles;
        this.wells=wells;
        this.totalNumberOfTiles=numberOfTiles;
        this.lock=new ReentrantLock();
        ThreadsManager.results = new ArrayList<>();
        guiRef=gui;
    }

    public void initializeThreads(boolean fromFile) throws InterruptedException {
        ThreadsManager.results.add(wells.get(0));
        ProcessingController controller;

        if((ThreadsManager.bounded==-1)||ThreadsManager.bounded>totalNumberOfTiles){
            controller = new ProcessingController(1,tiles,wells,numberOfThreads,lock, totalNumberOfTiles,fromFile, guiRef);
        }
        else{
            controller = new ProcessingController(1,tiles,wells,numberOfThreads,lock, ThreadsManager.bounded,fromFile, guiRef);
        }
        controller.start();
    }

    /**
     * Interface to perform callbacks to the main GUI gui
     */
    public interface Communicator{
        void displayOneStepOfComputation(ArrayList<Well> wells);
        void computationEnded();
        void serializationStart(ArrayList<Well> wells, ArrayList<ArrayList<ProcessingTile>> tilesOfTilesList);
    }

}
