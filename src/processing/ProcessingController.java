package processing;

import controller.ThreadsManager;
import data.ProcessingTile;
import data.Well;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by Borys on 11/21/16.
 */
public class ProcessingController extends Thread {
    private int id;
    private int backTrack;
    private int total;

    private ArrayList<ProcessingUnit> threads = new ArrayList<>();
    private ArrayList<Well> best;
    private Lock lock;

    public ProcessingController(int id, ArrayList<ProcessingTile> tilesToWorkOn, int backTrack, Lock lock, int total, boolean fromFile){
        this.id=id;
        this.backTrack=backTrack;
        this.lock=lock;
        this.best = new ArrayList<>();
        this.total=total;
        readAndSetUpBest(tilesToWorkOn, fromFile);
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
                    tiles.add(tilesToWorkOn.get(j));
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
            ThreadsManager.results.sort(ThreadsManager.cm);
            best=new ArrayList<>();
            if(ThreadsManager.results.size()>1){
                if(total >=backTrack) iter = backTrack;
                else iter = total;
            }
            else iter=1;
            for(int i=0;i<iter;i++){
                best.add(ThreadsManager.results.get(i));
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
        }
        printWell(best.get(0));
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
