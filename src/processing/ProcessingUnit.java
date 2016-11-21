package processing;

import controller.ThreadsManager;
import helpers.AlgoHelper;
import algorithm.PackingAlgorithm;
import data.ProcessingTile;
import data.Well;
import data.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by Borys on 11/15/16.
 */
public class ProcessingUnit extends Thread{
    private int id;
    private int backTrack;

    private Well well;
    private ArrayList<ProcessingTile> tiles;
    private Lock lock;

    private ArrayList<Well> afterPutingTiles;

    public ProcessingUnit(int id, Well wellToWorkOn, int backTrack, Lock lock){
        this.id=id;
        this.well=wellToWorkOn;
        this.tiles=wellToWorkOn.getTiles();
        afterPutingTiles = new ArrayList<>();
        this.backTrack=backTrack;
        this.lock=lock;
    }
    public void run(){
        System.out.println("ExtendsThread : id : " + id);
        PackingAlgorithm pack = new PackingAlgorithm();
        AlgoHelper algo=new AlgoHelper(1);


        for (int i = 0; i < tiles.size(); i++){
            ArrayList<Well> localList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Well tmp = new Well(well);
                tmp = pack.runAlgorithm(tmp, tiles.get(i).fourTypes[j], tiles.get(i).getId());
                tmp.setQuality(algo.calculateQuality(tmp));
                tmp.lastAddedTile = i;
                localList.add(tmp); //Adding all wells with placed tiles to the list
//                    System.out.println("TILE - "+i+", SUBTILE - "+j);
//                    printWell(tmp);
            }
            localList.sort(ThreadsManager.cm);
            afterPutingTiles.add(localList.get(0)); //Adding the best well to the global list of best wells
        }
        afterPutingTiles.sort(ThreadsManager.cm);
        try {
            lock.lock();
            int num = 0;
            if(afterPutingTiles.get(0).getTiles().size()>=backTrack) num = backTrack;
            else num = afterPutingTiles.get(0).getTiles().size();
            for(int i=0;i<num;i++){
                ThreadsManager.results.add(afterPutingTiles.get(i));
            }
            System.out.println("BEST "+backTrack+" WELLS WERE ADDED BY THREAD "+id);
        }finally {
            lock.unlock();
        }
    }
    /*public void run() {
        int counter = tiles.length;
        System.out.println("ExtendsThread : id : " + id);
       // printWell(well);
        PackingAlgorithm pack = new PackingAlgorithm();
        AlgoHelper algo=new AlgoHelper(1);
        while(counter>0) {
            afterPutingTiles = new ArrayList<>();
            for (int i = 0; i < tiles.length; i++)
                if (tiles[i].getNumberOfSuchTiles() > 0) {
                    ArrayList<Well> localList = new ArrayList<>();
//                for(int j=0;j<4;j++){
//                    printTile(tiles[i].fourTypes[j]);
//                    System.out.println();
//                }
                    for (int j = 0; j < 4; j++) {
                        Well tmp = new Well(well);
                        tmp = pack.runAlgorithm(tmp, tiles[i].fourTypes[j], i + 1);
                        tmp.setQuality(algo.calculateQuality(tmp));
                        tmp.lastAddedTile = i;
                        localList.add(tmp); //Adding all wells with placed tiles to the list
//                    System.out.println("TILE - "+i+", SUBTILE - "+j);
//                    printWell(tmp);
                    }
                    Collections.sort(localList, ((o1, o2) -> {
                        if (o1.getQuality() < o2.getQuality()) return 1;
                        else if (o1.getQuality() > o2.getQuality()) return -1;
                        else return 0;
                    }));
                    afterPutingTiles.add(localList.get(0)); //Adding the best well to the global list of best wells
                }

            Collections.sort(afterPutingTiles, (o1, o2) -> {
                if (o1.getQuality() < o2.getQuality()) return 1;
                else if(o1.getQuality() > o2.getQuality()) return -1;
                else return 0;
            });

            System.out.println("QUALITY - " + afterPutingTiles.get(0).getQuality());
//            printWell(afterPutingTiles.get(0));

            well = afterPutingTiles.get(0);
            int n = tiles[afterPutingTiles.get(0).lastAddedTile].getNumberOfSuchTiles();
            tiles[afterPutingTiles.get(0).lastAddedTile].setNumerOfSuchTiles(--n);
            if (tiles[afterPutingTiles.get(0).lastAddedTile].getNumberOfSuchTiles() >= 0) {
                counter--;
            }
        }
        printWell(afterPutingTiles.get(0));
//        ThreadsManager.next = afterPutingTiles.get(0);
//        for(int i=1;i<= 10;i++)
//        {
//            tmp=pack.runAlgorithm(well,tiles[i-1],i);
//            well=tmp;
//        }
//        AlgoHelper algo=new AlgoHelper(1);
//        double quality = algo.calculateQuality(tmp);
//        printWell(tmp);
        //printTile(tiles[0]);
    }

    public void printWell(Well wellToPrint){
        for(int i=0;i<wellToPrint.getHeight();i++){
            for(int j=0;j<wellToPrint.getWidth();j++){
                System.out.print(wellToPrint.well[i][j]);
            }
            System.out.println();
        }
    }
    public void printTile(Tile tileToPrint){
        for(int i=0;i<tileToPrint.getHeight();i++){
            for(int j=0;j<tileToPrint.getWidth();j++){
                System.out.print(tileToPrint.getTile()[i][j]);
            }
            System.out.println();
        }
    }*/
}
