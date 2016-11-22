package processing;

import controller.ThreadsManager;
import data.ProcessingTile;
import data.Tile;
import data.Well;
import gui.MainGui;
import jdk.nashorn.internal.ir.ThrowNode;
import main.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static javax.swing.text.html.HTML.Tag.HEAD;

/**
 * Created by Borys on 11/21/16.
 */
public class ProcessingController extends Thread {

    MainGui guiRef;

    private int id;
    private int backTrack;
    private int total;

    private ArrayList<ProcessingUnit> threads = new ArrayList<>();
    private ArrayList<Well> best;
    private ArrayList<JPanel> panels;

    private Lock lock;

    public ProcessingController(int id, ArrayList<ProcessingTile> tilesToWorkOn, ArrayList<Well> wells, int backTrack, Lock lock, int total, boolean fromFile, MainGui gui){
        this.id=id;
        this.backTrack=backTrack;
        this.lock=lock;
        this.guiRef=gui;
        this.best = new ArrayList<>();
        this.total=total;
        readAndSetUpBest(tilesToWorkOn, fromFile);
        panels = new ArrayList<>();
        for(int j=0;j<wells.size();j++){
            panels.add(wells.get(j).getWellPanel());
        }
    }

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

    public void run(){
        int iter = 0;
        if(ThreadsManager.results.size()>1){
            iter=backTrack;
        }
        else iter=1;
        while (total>0){
            try {
                lock.lock();
                ThreadsManager.results.removeAll(ThreadsManager.results);
                threads.removeAll(threads);
                for(int i = 0;i<iter;i++){
//                    System.out.println(best.get(i).getTiles().size());
                    ProcessingUnit pu = new ProcessingUnit(i, best.get(i),backTrack,lock);
                    threads.add(pu);
                    pu.start();
//                    System.out.println("SIZE, TH "+i+" - "+best.get(i).getTiles().size());
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
            ThreadsManager.results.sort(ThreadsManager.cm);
            best=new ArrayList<>();
            if(ThreadsManager.results.size()>1){
                if(ThreadsManager.results.size() >=backTrack) iter = backTrack;
                else{
                    iter = total;
                    if(ThreadsManager.results.size()<iter) iter = ThreadsManager.results.size();
                }
            }
            else iter=1;
            for(int i=0;i<iter;i++){
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
            total--;
//            System.out.println("BEST TILES:");
//            for(int l=0;l<best.size();l++){
//                printWell(best.get(l));
//                int count =0 ;
//                for(int k=0;k<best.get(l).getTiles().size();k++){
//                    int n = best.get(l).getTiles().get(k).getNumberOfSuchTiles();
//                    count+=n;
//                }
//                System.out.println("NUMBER OF TILES - "+ count);
//                System.out.println();
//            }
//            System.out.println(":-------------:");
            guiRef.displayOneStepOfComputation(best);

//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if(ThreadsManager.serializeOnDemand==true)
            {
                ThreadsManager.serializeOnDemand=false;
                ArrayList<ArrayList<ProcessingTile>> tiless=new ArrayList<ArrayList<ProcessingTile>>();
                for(int i=0;i<ThreadsManager.results.size();i++)
                {
                    tiless.set(i,ThreadsManager.results.get(i).getTiles());
                }
                guiRef.serializationStart(ThreadsManager.results,tiless);
            }

            System.out.println(ThreadsManager.stopped);
            if(ThreadsManager.stopped){
                break;
            }
        }
        System.out.println("BEST QUALITY - "+best.get(0).getQuality());
        System.out.println("MAX HEIGHT - "+best.get(0).getMaxHeight());
        printWell(best.get(0));
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
