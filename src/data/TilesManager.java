package data;

import data.interfaces.Manager;
import gui.TilesGuiGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Borys on 10/16/16.
 */
public class TilesManager implements Manager {

    private  ProcessingTile[] processingTiles;
    private Tile[] tiles;
    private int wellWidth;
    private int numberOfTiles;
    private TilesGuiGenerator guiGenerator;

    private int maxTileHeight;
    private int maxTileWidth;

    public TilesManager(File f){
        this.maxTileHeight=0;
        this.maxTileWidth=0;
        readTiles(f);
        guiGenerator = new TilesGuiGenerator(1);
    }

    @Override
    public void readTiles(File f) {
        try (Scanner sc = new Scanner(f)) {
            this.wellWidth = sc.nextInt();
            this.numberOfTiles = sc.nextInt();
            this.tiles = new Tile[numberOfTiles];
            sc.nextLine();
            for(int i=0;i<numberOfTiles;i++){
                int width = sc.nextInt();
                int height = sc.nextInt();

                if(height>maxTileHeight) maxTileHeight=height;
                if(width>maxTileWidth) maxTileWidth=width;

                int[][] tile = new int[height][width];
                sc.nextLine();
                for(int l=0;l<height;l++){
                    for(int k=0;k<width;k++){
                        tile[l][k] = sc.nextInt();
                    }
                }
                tiles[i] = new Tile(width,height,tile);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stack<JPanel> generateGuiForTiles() {
        Stack<JPanel> panels = new Stack<JPanel>();
        for(int i=0;i<numberOfTiles;i++){
            panels.add(guiGenerator.generatePanel(tiles[i],maxTileHeight,maxTileWidth));
        }
        return panels;
    }

    @Override
    public JPanel generateWell() {
        JPanel result = guiGenerator.generateWell(wellWidth);
        return result;
    }
}
