package controller.interfaces;

import data.ProcessingTile;
import data.Well;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 * Interface to define basic functionality of Manager
 */
public interface Manager {
    /**
     * Method to read tiles from File
     * @param f - File to read tiles from
     */
    void readTiles(File f);

    /**
     * Method to generate GUI for tiles
     * @return Stack of instances of JPanel with a tile represented on each panel
     */
    Stack<JPanel> generateGuiForTiles();

    /**
     * Method to generate wells
     * @param backTrack - Back track parameter representing number of Wells to genetare
     * @return ArrayList of instances of class Well is returned
     */
    ArrayList<Well> generateWells(int backTrack);

    /**
     * Method to gather information about total number of tiles
     * @return Integer representing total numebr of tiles
     */
    int prepareForStart(int valueFromGlobal);

    /**
     * Getter for all wells
     * @return ArrayList of instances of class Well
     */
    ArrayList<Well> getWells();

    /**
     * Method to display well in GUI
     * @param toDisplay
     */
    void displayWell(Well toDisplay);

    /**
     * Return tiles as ArrayList
     * @return ArrayList of instances of class ProcessingTile
     */
    ArrayList<ArrayList<ProcessingTile>> getTilesAsArrayList();
}
