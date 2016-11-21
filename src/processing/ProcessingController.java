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

    private ArrayList<ProcessingTile> tiles;
    private ArrayList<ProcessingUnit> threads = new ArrayList<>();
    private ArrayList<Well> best;
    private Lock lock;

    public ProcessingController(int id, ArrayList<ProcessingTile> tilesToWorkOn, int backTrack, Lock lock){
        this.id=id;
        this.tiles=tilesToWorkOn;
        this.backTrack=backTrack;
        this.lock=lock;
        this.best = new ArrayList<>();
        best.add(ThreadsManager.results.get(0));
    }
    public void run(){
        while (tiles.size()>0){
            int iter = 0;
            if(ThreadsManager.results.size()>1){
                iter=backTrack;
            }
            else iter=1;
            try {
                lock.lock();
                ThreadsManager.results.removeAll(ThreadsManager.results);
                threads.removeAll(threads);
                for(int i = 0;i<iter;i++){
                    ProcessingUnit pu = new ProcessingUnit(i, best.get(i),tiles,backTrack,lock);
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
            for(int i=0;i<iter;i++){
                best.add(ThreadsManager.results.get(i));
                int lastAdded = ThreadsManager.results.get(i).lastAddedTile;
                int n = tiles.get(lastAdded).getNumberOfSuchTiles();
                n--;
                if(n>0){
                    tiles.get(lastAdded).setNumerOfSuchTiles(n);
                }
                else{
                    tiles.remove(lastAdded);
                }
            }
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
