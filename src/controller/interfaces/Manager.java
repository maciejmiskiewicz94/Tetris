package controller.interfaces;

import data.ProcessingTile;
import data.Well;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 */
public interface Manager {
    public void readTiles(File f);
    public Stack<JPanel> generateGuiForTiles();
    public ArrayList<Well> generateWells(int backTrack);
    public int prepareForStart();
    public ProcessingTile[] getTiles();
    public ArrayList<Well> getWells();

    ArrayList<ProcessingTile> getTilesAsArrayList();
}
