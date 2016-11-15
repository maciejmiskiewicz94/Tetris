package data;

import data.interfaces.Manager;
import gui.TilesGuiGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 */
public class TilesManager implements Manager {

    private  ProcessingTile[] processingTiles;
    private Tile[] tiles;

    private ArrayList<Well> wells; //List of all wells to use anywhere

    private int wellWidth;
    private int wellMult;
    private int numberOfTiles;
    private TilesGuiGenerator guiGenerator;

    private int maxTileHeight;
    private int maxTileWidth;

    private int backtrackingParam;

    public TilesManager(File f){
        this.maxTileHeight=0;
        this.maxTileWidth=0;
        this.wellWidth=0;
        this.wellMult=0;
        readTiles(f);
        guiGenerator = new TilesGuiGenerator(1);
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

                if(height>maxTileHeight) maxTileHeight=height;
                if(width>maxTileWidth) maxTileWidth=width;

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

    @Override
    public Stack<JPanel> generateGuiForTiles() {
        Stack<JPanel> panels = new Stack<JPanel>();
        for(int i=0;i<numberOfTiles;i++){
            panels.add(guiGenerator.generatePanel(tiles[i],maxTileHeight,maxTileWidth));
        }
        return panels;
    }

    @Override
    public ArrayList<Well> generateWells(int backTrack) {

        wells = new ArrayList<>();
        this.backtrackingParam = backTrack;

        int wellWSize = 0;
        if((wellWidth>0)&&(wellWidth<=50)){
            wellWSize = wellWidth*10;
            wellMult=10;
        }
        else if((wellWidth>50)&&(wellWidth<100)){
            wellWSize = wellWidth*5;
            wellMult=5;
        }
        else{
            wellWSize = wellWidth;
            wellMult=1;
        }
        for(int i=0;i<backTrack;i++){
            JPanel result = guiGenerator.generateWell(wellWSize, wellMult);
            Well tmp = new Well(wellWidth,result,wellMult);
            wells.add(tmp);
        }

        return wells;
    }
}
