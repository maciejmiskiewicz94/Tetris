package gui.interfaces;

import data.Tile;

import javax.swing.*;

/**
 * Created by Borys on 10/16/16.
 */
public interface DynamicGuiGenerator {
    public JPanel generatePanel(Tile singleTile, int maxHeight, int maxWidth);
    public JPanel generateWell(int maxWidth, int m);
}
