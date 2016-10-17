package data;

import gui.TilesGuiGenerator;
import gui.interfaces.DynamicGuiGenerator;

import javax.swing.*;

/**
 * Created by Borys on 10/16/16.
 */
public class GuiTile extends Tile {

    JPanel tilePanel;

    public GuiTile(int w, int h, int[][] tile) {
        super(w, h, tile);
//        TilesGuiGenerator generator = new TilesGuiGenerator();
    }
}
