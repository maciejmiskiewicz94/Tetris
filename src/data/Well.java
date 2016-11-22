package data;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Maciej on 2016-10-19.
 */
public class Well {

    private int width;
    public int[][] well;
    public JPanel wellPanel;
    public int wellMult;
    private int height;
    private double quality;

    private ArrayList<ProcessingTile> tiles;

    public int lastAddedTile;

    public Well(int w, JPanel panel, int mult) {
        this.width = w;
        this.wellPanel=panel;
        this.well = new int[w][w];
        this.wellMult=mult;
        this.height=w;
        this.quality = -1;
        this.tiles=new ArrayList<>();
    }
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
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
}
