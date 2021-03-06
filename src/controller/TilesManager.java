package controller;

import data.ProcessingTile;
import data.Tile;
import data.Well;
import controller.interfaces.Manager;
import gui.TilesGuiGenerator;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 * Class which implements Manager interface and provides resources management tools and different components interaction methods
 */
public class TilesManager implements Manager {

    private ProcessingTile[] tiles;

    private ArrayList<Well> wells; //List of all wells

    private int wellWidth;
    private int wellMult;
    private int numberOfTiles;
    private TilesGuiGenerator guiGenerator;

    private int maxTileHeight;
    private int maxTileWidth;

    public int backtrackingParam;
    private ArrayList<JSpinner> spinners;

    private ArrayList<ArrayList<ProcessingTile>> tilesOfTilesList;

    /**
     * Constructor for class TileManager
     * @param f - File to read tiles from
     */
    public TilesManager(File f){
        this.maxTileHeight=0;
        this.maxTileWidth=0;
        this.wellWidth=0;
        this.wellMult=0;
        readTiles(f);
        guiGenerator = new TilesGuiGenerator(1);
        this.tilesOfTilesList = new ArrayList<>();
    }
    public TilesManager(ArrayList<Well> wells, ArrayList<ArrayList<ProcessingTile>> tilesOfTilesList){
        this.maxTileHeight=0;
        this.maxTileWidth=0;
        this.wellWidth=wells.get(0).getWidth();
        this.wellMult=wells.get(0).getWellMult();
        guiGenerator = new TilesGuiGenerator(1);
        this.spinners = new ArrayList<>();
        this.tilesOfTilesList=tilesOfTilesList;

    }

    @Override
    public void readTiles(File f) {
        try (Scanner sc = new Scanner(f)) {
            this.wellWidth = sc.nextInt();
            this.numberOfTiles = sc.nextInt();
            this.tiles = new ProcessingTile[numberOfTiles];
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
                tiles[i] = new ProcessingTile(width,height,tile,i+1);
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
        spinners = guiGenerator.getSpinners();
        return panels;
    }
    public ArrayList<JSpinner> getSpinners(){
        return spinners;
    }

    public int prepareForStart(int valueFromGlobal){
        int totalNumberOfTiles=0;
        if(this.spinners.size()!=0){
            if(valueFromGlobal>0){
                for (ProcessingTile tile : tiles) {
                    tile.setNumerOfSuchTiles(valueFromGlobal);
                    totalNumberOfTiles+=tile.getNumberOfSuchTiles();
                }
            }
            else{
                for(int i=0;i<spinners.size();i++){
                    tiles[i].setNumerOfSuchTiles((Integer) spinners.get(i).getValue());
                    totalNumberOfTiles+=tiles[i].getNumberOfSuchTiles();
                }
            }
        }
        return totalNumberOfTiles;
    }

    @Override
    public ArrayList<Well> getWells() {
        return wells;
    }

    @Override
    public void displayWell(Well toDisplay) {
        guiGenerator.fullFillWell(toDisplay);
    }

    @Override
    public ArrayList<ArrayList<ProcessingTile>> getTilesAsArrayList() {
        if(this.tilesOfTilesList.size()==0){
            ArrayList<ProcessingTile> t = new ArrayList<>();
            for(int i=0;i<tiles.length;i++){
                t.add(tiles[i]);
            }
            ArrayList<ArrayList<ProcessingTile>> res = new ArrayList<ArrayList<ProcessingTile>>();
            res.add(t);
            return res;
        }
        else{
            return this.tilesOfTilesList;
        }
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
        else if((wellWidth>50)&&(wellWidth<=100)){
            wellWSize = wellWidth*5;
            wellMult=5;
        }
        else{
            wellWSize = wellWidth;
            wellMult=1;
        }
        for(int i=0;i<backTrack;i++){
            JPanel result = guiGenerator.generateWell(wellWSize);
            Well tmp = new Well(wellWidth,result,wellMult);
            wells.add(tmp);
        }

        return wells;
    }
}
