package data;

import data.interfaces.Manager;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Borys on 10/16/16.
 */
public class TilesManager implements Manager {

    private  ProcessingTile[] processingTiles;
    private Tile[] tiles;
    private int wellWidth;
    private int numberOfTiles;

    public TilesManager(File f){
        readTiles(f);
    }

    @Override
    public void readTiles(File f) {
        try (Scanner sc = new Scanner(f)) {
            this.wellWidth = sc.nextInt();
            this.numberOfTiles = sc.nextInt();
            this.tiles = new Tile[numberOfTiles];
            sc.nextLine();
            for(int i=0;i<numberOfTiles;i++){
                int width = sc.nextInt();
                int height = sc.nextInt();
                int[][] tile = new int[height][width];
                sc.nextLine();
                for(int l=0;l<height;l++){
                    for(int k=0;k<width;k++){
                        tile[l][k] = sc.nextInt();
                    }
                }
                tiles[i] = new Tile(width,height,tile);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getWellWidth(){
        return this.wellWidth;
    }
}
