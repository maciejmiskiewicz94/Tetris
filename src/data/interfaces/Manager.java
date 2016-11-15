package data.interfaces;

import data.Well;

import javax.swing.*;
import java.awt.*;
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
}
