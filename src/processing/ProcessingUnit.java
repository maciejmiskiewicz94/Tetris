package processing;

import controller.ThreadsManager;
import helpers.AlgoHelper;
import algorithm.PackingAlgorithm;
import data.ProcessingTile;
import data.Well;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

/**
 * Created by Borys on 11/15/16.
 * Class responsible for a single board processing
 * It goes through the set of all tiles, finds a place for each of them, calculates quality and informs Processing controller
 * about k - best options (where k is a backtrack parameter)
 *
 * It inherits class Thread and implements method run
 */
public class ProcessingUnit extends Thread{
    private int id;
    private int backTrack;

    private Well well;
    private ArrayList<ProcessingTile> tiles;
    private Lock lock;

    private ArrayList<Well> afterPutingTiles;

    /**
     *
     * @param id - Processing unit id
     * @param wellToWorkOn - Well to put tiles on
     * @param backTrack - Backtracking parameter
     * @param lock - Lock which controls access to shared data between threads
     */
    public ProcessingUnit(int id, Well wellToWorkOn, int backTrack, Lock lock){
        this.id=id;
        this.well=wellToWorkOn;
        this.tiles=wellToWorkOn.getTiles();
        afterPutingTiles = new ArrayList<>();
        this.backTrack=backTrack;
        this.lock=lock;
    }

    /**
     * Thread method run which is executed when thread is started
     */
    public void run(){
//        System.out.println("Thread started : id : " + id);
        PackingAlgorithm pack = new PackingAlgorithm();
        AlgoHelper algo=new AlgoHelper(1);

        for (int i = 0; i < tiles.size(); i++){ //Main loop of the method, it goes through the all tiles
            if(tiles.get(i).getNumberOfSuchTiles()>0){
                ArrayList<Well> localList = new ArrayList<>();
                for (int j = 0; j < 4; j++) { //Loop which checks placements for all 4 rotations of a tile
                    Well tmp = new Well(well);
                    tmp = pack.runAlgorithm(tmp, tiles.get(i).fourTypes[j], tiles.get(i).getId()); //Finding a place for a tile
                    tmp.setQuality(algo.calculateQuality(tmp)); //Finding and setting quality for the tile
                    tmp.lastAddedTile = i;
                    localList.add(tmp); //Adding all wells with placed tiles to the list
//                    System.out.println("TILE - "+i+", SUBTILE - "+j);
//                    printWell(tmp);
                }
                localList.sort(ThreadsManager.cm); //Sorting rotations lost by quality
                afterPutingTiles.add(localList.get(0)); //Adding the best well to the global list of best wells
            }
        }
        afterPutingTiles.sort(ThreadsManager.cm); //Sorting the total list of wells
        try {
            lock.lock(); //Acquiring shared resource or wait if resource is busy
            int num = 0;
            if(afterPutingTiles.get(0).getTiles().size()>=backTrack) num = backTrack;
            else num = afterPutingTiles.get(0).getTiles().size();
            for(int i=0;i<num;i++){ //Putting k - best wells into the shared list of wells
                ThreadsManager.results.add(afterPutingTiles.get(i));
            }
//            System.out.println("BEST "+backTrack+" WELLS WERE ADDED BY THREAD "+id);
        }finally {
            lock.unlock();
        }
    }
}
