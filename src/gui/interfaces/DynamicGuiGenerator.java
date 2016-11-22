package gui.interfaces;

import data.Tile;
import data.Well;

import javax.swing.*;

/**
 * Created by Borys on 10/16/16.
 */
public interface DynamicGuiGenerator {
    public JPanel generatePanel(Tile singleTile, int maxHeight, int maxWidth);
    public JPanel generateWell(int maxWidth, int m);

    void fullFillWell(Well toDisplay);
}
