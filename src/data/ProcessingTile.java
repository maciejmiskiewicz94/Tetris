package data;

import helpers.AlgoHelper;

import java.awt.*;

/**
 * Created by Borys on 10/16/16.
 * Class which is responsible for convenient tile representation is for algorithm processing
 */
public class ProcessingTile extends Tile {

    public Tile[] fourTypes;
    private int numberOfSuchTiles;
    private int tileId;
    private Color tileColor;

    /**
     * Basic constructor for ProcessingTile class
     * @param w - Width of the tile
     * @param h - Height of the tile
     * @param tile - Two-dimensional array of integers representing a tile (0 - empty space, 1- taken cell)
     * @param id - Unique tile id, used for color generation
     */
    public ProcessingTile(int w, int h, int[][] tile, int id) {
        super(w, h, tile);
        fourTypes = new Tile[4];
        fourTypes[0] = new Tile(w, h, tile);

        int[][] arr = generateTiles(fourTypes[0].getTile());
        fourTypes[1] = new Tile(arr[0].length, arr.length, arr);
        int[][] arr2 = generateTiles(fourTypes[1].getTile());
        fourTypes[2] = new Tile(arr2[0].length, arr2.length, arr2);
        int[][] arr3 = generateTiles(fourTypes[2].getTile());
        fourTypes[3] = new Tile(arr3[0].length, arr3.length, arr3);

        AlgoHelper helper = new AlgoHelper(2);
//        helper.seeTheTiles(fourTypes);
        this.numberOfSuchTiles = 1;
        this.tileId = id;
        this.tileColor = new Color((id*10)%255,(id*100)%255,(id*10)%255);
    }

    /**
     * Constructor to copy given instance of class ProcessingTile
     * @param t - Tile to copy
     */

    public ProcessingTile(ProcessingTile t){
        super(t);
        fourTypes = new Tile[4];
        fourTypes[0] = new Tile(this.getWidth(), this.getHeight(), this.getTile());

        int[][] arr = generateTiles(fourTypes[0].getTile());
        fourTypes[1] = new Tile(arr[0].length, arr.length, arr);
        int[][] arr2 = generateTiles(fourTypes[1].getTile());
        fourTypes[2] = new Tile(arr2[0].length, arr2.length, arr2);
        int[][] arr3 = generateTiles(fourTypes[2].getTile());
        fourTypes[3] = new Tile(arr3[0].length, arr3.length, arr3);

        this.numberOfSuchTiles = t.getNumberOfSuchTiles();
        this.tileId = t.getId();
        this.tileColor=t.getTileColor();
    }

    /**
     * Method to generate 90 degree rotation of a tile
     * @param mat - Tile two-dimensional array
     * @return Two dimensional array representing input array rotated by 90 degree
     */
    private int [][] generateTiles(int[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        return ret;
    }

    public void setNumerOfSuchTiles(int num){
        this.numberOfSuchTiles = num;
    }
    public int getNumberOfSuchTiles(){
        return this.numberOfSuchTiles;
    }
    public int getId() {
        return this.tileId;
    }
    public Color getTileColor() {
        return tileColor;
    }
}
