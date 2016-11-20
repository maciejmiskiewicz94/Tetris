package processing;

import Helpers.AlgoHelper;
import algorithm.PackingAlgorithm;
import controller.ThreadsManager;
import data.ProcessingTile;
import data.Well;
import data.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Borys on 11/15/16.
 */
public class ProcessingUnit extends Thread{
    private int id;

    private Well well;
    private ProcessingTile[] tiles;

    private ArrayList<Well> afterPutingTiles;

    public ProcessingUnit(int id, Well wellToWorkOn, ProcessingTile[] tilesToWorkOn){
        this.id=id;
        this.well=wellToWorkOn;
        this.tiles=tilesToWorkOn;
        afterPutingTiles = new ArrayList<>();
    }
    public void run() {
        int counter = tiles.length;
        System.out.println("ExtendsThread : id : " + id);
       // printWell(well);
        PackingAlgorithm pack = new PackingAlgorithm();
        AlgoHelper algo=new AlgoHelper(1);
        while(counter>0) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i].getNumberOfSuchTiles() > 0) {
                    ArrayList<Well> localList = new ArrayList<>();
//                for(int j=0;j<4;j++){
//                    printTile(tiles[i].fourTypes[j]);
//                    System.out.println();
//                }
                    for (int j = 0; j < 1; j++) {
                        Well tmp = new Well(well);
                        tmp = pack.runAlgorithm(tmp, tiles[i].fourTypes[j], i + 1);
                        tmp.setQuality(algo.calculateQuality(tmp));
                        tmp.lastAddedTile = i;
                        localList.add(tmp);
//                    System.out.println("TILE - "+i+", SUBTILE - "+j);
//                    printWell(tmp);
                    }
                    Collections.sort(localList, (o1, o2) -> {
                        if (o1.getQuality() >= o2.getQuality()) return 1;
                        else return -1;
                    });
                    afterPutingTiles.add(localList.get(0));
                }
            }
            Collections.sort(afterPutingTiles, (o1, o2) -> {
                if (o1.getQuality() <= o2.getQuality()) return 1;
                else return -1;
            });

            System.out.println("QUALITY - " + afterPutingTiles.get(0).getQuality());
            printWell(afterPutingTiles.get(0));

            well = afterPutingTiles.get(0);
            int n = tiles[afterPutingTiles.get(0).lastAddedTile].getNumberOfSuchTiles();
            tiles[afterPutingTiles.get(0).lastAddedTile].setNumerOfSuchTiles(--n);
            if (tiles[afterPutingTiles.get(0).lastAddedTile].getNumberOfSuchTiles() >= 0) {
                counter--;
            }
        }
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
    }
}
