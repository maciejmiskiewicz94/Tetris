package data;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Maciej on 2016-10-19.
 * Class representing Well in our program
 */
public class Well {

    private int width;
    public int[][] well;
    public JPanel wellPanel;
    public int wellMult;
    private int height;
    private double quality;

    private int maxHeight;

    private ArrayList<ProcessingTile> tiles;

    public int lastAddedTile;

    /**
     * Basic constructor
     * @param w - Width of the Well
     * @param panel - Jpanel which responsible for displaying well content
     * @param mult - Multiplier which is responsible for correct displaying tiles on the screen
     */
    public Well(int w, JPanel panel, int mult) {
        this.width = w;
        this.wellPanel=panel;
        this.well = new int[w][w];
        this.wellMult=mult;
        this.height=w;
        this.quality = -1;
        this.tiles=new ArrayList<>();
        this.maxHeight = 0;
    }

    /**
     * Constructor to copy given instance of class Well, except for wellPanel which is copied by reference
     * @param w - Well to copy
     */
    public Well(Well w){
        this.width = w.getWidth();
        this.height = w.getHeight();
        this.quality = w.getQuality();
        this.well = new int[height][width];
        for(int i =0;i<height;i++){
            for(int j=0;j<width;j++){
                this.well[i][j] = w.getWell()[i][j];
            }
        }
        this.wellPanel = w.getWellPanel();
        this.wellMult=w.getWellMult();
        ArrayList<ProcessingTile> t = new ArrayList<>();
        for(int j =0;j<w.getTiles().size();j++){
            t.add(new ProcessingTile(w.getTiles().get(j)));
        }
        this.tiles=t;
        this.maxHeight = w.getMaxHeight();
    }

    //Getters and setters for Well class

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        int [][] nWell = new int[height][this.getWidth()];
        for(int i=0;i<this.getHeight();i++){
            for(int j=0;j<this.getWidth();j++){
                nWell[i][j] = this.getWell()[i][j];
            }
        }
        this.height = height;
        this.well = nWell;
    }

    public double getQuality(){
        return quality;
    }
    public void setQuality(double q){
        this.quality=q;
    }

    public int[][] getWell() {
        return well;
    }

    public JPanel getWellPanel() {
        return wellPanel;
    }

    public int getWellMult() {
        return wellMult;
    }

    public ArrayList<ProcessingTile> getTiles(){
        return this.tiles;
    }
    public void setTiles(ArrayList<ProcessingTile> t){
        this.tiles=new ArrayList<>(t);
    }

    public void setWellPanel(JPanel pan){
        this.wellPanel=pan;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
    public void setMaxHeight(int h){
        this.maxHeight=h;
    }
}
