package data;

/**
 * Created by Borys on 10/16/16.
 */
public class Tile {

    private int width;
    private int height;

    private int[][] tile;

    public Tile(int w,int h, int[][] tile){
        this.width=w;
        this.height = h;
        this.tile=tile;
    }
}
