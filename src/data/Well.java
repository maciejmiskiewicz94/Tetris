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

    public Well(int w, JPanel panel, int mult) {
        this.width = w;
        this.wellPanel=panel;
        this.well = new int[w][w];
        this.wellMult=mult;
    }

    public int getWidth() {
        return width;
    }

}
