package processing;

import Helpers.AlgoHelper;
import algorithm.PackingAlgorithm;
import data.ProcessingTile;
import data.Well;
import data.Tile;

import java.util.ArrayList;

/**
 * Created by Borys on 11/15/16.
 */
public class ProcessingUnit extends Thread{
    private int id;

    private Well well;
    private ProcessingTile[] tiles;

    ArrayList<Well> afterPutingTiles;

    public ProcessingUnit(int id, Well wellToWorkOn, ProcessingTile[] tilesToWorkOn){
        this.id=id;
        this.well=wellToWorkOn;
        this.tiles=tilesToWorkOn;
        afterPutingTiles = new ArrayList<>();
    }
    public void run() {
        System.out.println("ExtendsThread : id : " + id);
       // printWell(well);
        PackingAlgorithm pack = new PackingAlgorithm();
        Well tmp = pack.runAlgorithm(well,tiles[0],1);
        tmp=pack.runAlgorithm(tmp,tiles[1],2);
      //  for(int i=1;i<tiles.length;i++)
      //  {

      //  }
        AlgoHelper algo=new AlgoHelper(1);
        double quality = algo.calculateQuality(tmp);
        printWell(tmp);
        printTile(tiles[0]);
        printTile(tiles[1]);
        System.out.println(quality);
    }

    public void printWell(Well wellToPrint){
        for(int i=0;i<wellToPrint.getWidth();i++){
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
