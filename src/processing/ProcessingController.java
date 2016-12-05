package processing;

import controller.ThreadsManager;
import data.ProcessingTile;
import data.Well;
import gui.MainGui;
import helpers.AlgoHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

/**
 * Created by Borys on 11/21/16.
 * Class which controls processing part of the application
 */
public class ProcessingController extends Thread {

    ThreadsManager.Communicator guiRef; //Reference to the gui

    private int id;
    private int backTrack;
    private int total;

    private ArrayList<ProcessingUnit> threads = new ArrayList<>();
    private ArrayList<Well> best;
    private ArrayList<JPanel> panels;

    private Lock lock;

    /**
     *
     * @param id - Controller id
     * @param tilesToWorkOn - Set of tiles to work with
     * @param wells - Set of wells to work with
     * @param backTrack - Backtrack parameter
     * @param lock - Lock to control access to shared resources
     * @param total - Total number of tiles
     * @param fromFile - Indicator if program state was loaded from file
     * @param gui - Reference for MainGui
     */
    public ProcessingController(int id, ArrayList<ArrayList<ProcessingTile>>  tilesToWorkOn, ArrayList<Well> wells, int backTrack, Lock lock, int total, boolean fromFile, ThreadsManager.Communicator gui){
        this.id=id;
        this.backTrack=backTrack;
        this.lock=lock;
        this.guiRef=gui;
        this.best = new ArrayList<>();
        this.total=total;
        if(!fromFile)
            readAndSetUpBest(tilesToWorkOn.get(0), fromFile);
        else{
            for(int i=0;i<backTrack;i++){
                best.add(wells.get(i));
                best.get(i).setTiles(tilesToWorkOn.get(i));
            }
        }
        panels = new ArrayList<>();
        for(int j=0;j<wells.size();j++){
            panels.add(wells.get(j).getWellPanel());
        }
        if(total==0){
            for(int i=0;i<tilesToWorkOn.size();i++) this.total+=tilesToWorkOn.get(0).get(i).getNumberOfSuchTiles();
        }
    }

    /**
     * Helper method for initial set up
     */
    private void readAndSetUpBest(ArrayList<ProcessingTile> tilesToWorkOn, boolean fromFile) {
        int n=0;
        if(fromFile) n = backTrack;
        else n = 1;
        try {
            lock.lock();
            for(int i=0;i<n;i++){
                best.add(ThreadsManager.results.get(i));
                ArrayList<ProcessingTile> tiles = new ArrayList<>();
                for(int j =0;j<tilesToWorkOn.size();j++){
                    tiles.add(new ProcessingTile(tilesToWorkOn.get(j)));
                }
                best.get(i).setTiles(tiles);
            }
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * Main method for the class, works until the tital number of tiles is greater then 0
     */
    public void run(){
        long startTime = System.currentTimeMillis();

        int iter = 0;
        if(ThreadsManager.results.size()>1){
            iter=backTrack;
        }
        else iter=1;
        while (total>0){ //Main loop for the class
            try {
                lock.lock();
                ThreadsManager.results.removeAll(ThreadsManager.results);
                threads.removeAll(threads);
                for(int i = 0;i<iter;i++){ //Processing units creation
                    ProcessingUnit pu = new ProcessingUnit(i, best.get(i),backTrack,lock);
                    threads.add(pu);
                    pu.start();
                }
            }
            finally {
                lock.unlock();
            }
            for(int i = 0;i<iter;i++){
                try {
                    threads.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ThreadsManager.results.sort(ThreadsManager.cm); //Reults sorting
            best=new ArrayList<>();
            if(ThreadsManager.results.size()>1){
                if(ThreadsManager.results.size() >=backTrack) iter = backTrack;
                else{
                    iter = total;
                    if(ThreadsManager.results.size()<iter) iter = ThreadsManager.results.size();
                }
            }
            else iter=1;
            for(int i=0;i<iter;i++){ //k - best results are taken
                best.add(ThreadsManager.results.get(i));
                best.get(i).setWellPanel(panels.get(i));
                int lastAdded = ThreadsManager.results.get(i).lastAddedTile;
                int n = ThreadsManager.results.get(i).getTiles().get(lastAdded).getNumberOfSuchTiles();
                n--;
                if(n>0){
                    ThreadsManager.results.get(i).getTiles().get(lastAdded).setNumerOfSuchTiles(n);
                }
                else{
                    ThreadsManager.results.get(i).getTiles().remove(lastAdded);
                }
            }
            if(total == 141){
                int l =0;
            }
//            System.out.println("TOTAL - "+total);
//            printWell(best.get(0));
//            System.out.println();
            total--;
            guiRef.displayOneStepOfComputation(best);

            //Block for checking parameters
            if(ThreadsManager.serializeOnDemand==true)
            {
                ThreadsManager.serializeOnDemand=false;
                ArrayList<ArrayList<ProcessingTile>> tiless=new ArrayList<ArrayList<ProcessingTile>>();
                for(int i=0;i<best.size();i++)
                {
                    tiless.add(best.get(i).getTiles());
                }
                guiRef.serializationStart(best,tiless);
            }

//            System.out.println(ThreadsManager.stopped);
            if(ThreadsManager.stopped){
                break;
            }
        }
        //Finalization stage, print and final results passing to snapchat
        AlgoHelper helpp=new AlgoHelper(2);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = (stopTime - startTime)/1000;
        long miliSec = (stopTime - startTime) - elapsedTime*1000;
        System.out.println("COMPUTATION TIME: "+(elapsedTime+"."+miliSec)+" seconds");

        String dens="";
        for(int i=0;i<best.size();i++)
        {
            dens+="Density "+ Integer.toString(i+1)+": "+helpp.calculateDensity(best.get(i))+System.lineSeparator();
        }
        dens+="TOTAL TIME : "+(elapsedTime+"."+miliSec)+" SECONDS"+System.lineSeparator();
        JOptionPane.showMessageDialog(null, dens, "InfoBox", JOptionPane.INFORMATION_MESSAGE);

        System.out.println("BEST QUALITY - "+best.get(0).getQuality());
        System.out.println("MAX HEIGHT - "+best.get(0).getMaxHeight());
//        printWell(best.get(0));

        guiRef.computationEnded();
    }
    public void printWell(Well wellToPrint){
        for(int i=0;i<wellToPrint.getHeight();i++){
            for(int j=0;j<wellToPrint.getWidth();j++){
                System.out.print(wellToPrint.well[i][j]);
            }
            System.out.println();
        }
    }
}
