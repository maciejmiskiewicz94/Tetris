package gui.interfaces;

import data.Tile;
import data.Well;

import javax.swing.*;

/**
 * Created by Borys on 10/16/16.
 */
public interface DynamicGuiGenerator {
    /**
     * Method to generate panel for a Tile to display in TileChooser
     * @param singleTile - Instance of the class Tile
     * @param maxHeight - Max height of a tile
     * @param maxWidth - Max Width of a tile
     * @return JPanel to display in TileChooser GUI
     */
    JPanel generatePanel(Tile singleTile, int maxHeight, int maxWidth);

    /**
     * Method to generate Jpanel for a well
     * @param maxWidth - Max width of a well
     * @return JPanel for to represent a Well on GUI
     */
    JPanel generateWell(int maxWidth);

    /**
     * Method to fill given Well
     * @param toDisplay - Well to full fill.
     */
    void fullFillWell(Well toDisplay);
}
