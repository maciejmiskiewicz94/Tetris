package data;

import javax.swing.*;

/**
 * Created by Maciej on 2016-10-19.
 */
public class Well {

    private int width;
    public int[][] well;
    public JPanel wellPanel;
    public int wellMult;
    private int height;

    public Well(int w, JPanel panel, int mult) {
        this.width = w;
        this.wellPanel=panel;
        this.well = new int[w][w];
        this.wellMult=mult;
        this.height=w;
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
}
