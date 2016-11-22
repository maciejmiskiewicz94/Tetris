package data;

import helpers.AlgoHelper;

import java.awt.*;

/**
 * Created by Borys on 10/16/16.
 */
public class ProcessingTile extends Tile {

    public Tile[] fourTypes;
    private int numberOfSuchTiles;
    private int tileId;
    private Color tileColor;

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
    public void setNumerOfSuchTiles(int num){
        this.numberOfSuchTiles = num;
    }
    public int getNumberOfSuchTiles(){
        return this.numberOfSuchTiles;
    }

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

    public int getId() {
        return this.tileId;
    }

    public Color getTileColor() {
        return tileColor;
    }
}
