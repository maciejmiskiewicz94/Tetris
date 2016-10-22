package data.interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 */
public interface Manager {
    public void readTiles(File f);
    public Stack<JPanel> generateGuiForTiles();
    public JPanel generateWell();
}
