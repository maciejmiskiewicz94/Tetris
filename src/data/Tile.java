package data;

/**
 * Created by Borys on 10/16/16.
 * Class which is general representation of a Tile in our application
 */
public class Tile {

    private int width;
    private int height;

    private int[][] tile;

    /**
     * Basic constructor for Tile class
     * @param w - Width of the tile
     * @param h - Height of the tile
     * @param tile - Two-dimensional array of integers representing a tile
     */
    public Tile(int w,int h, int[][] tile){
        this.width=w;
        this.height = h;
        this.tile=tile;
    }

    /**
     * Constructor which allows create copy of a given tile, without a reference
     * @param t - tile to copy
     */
    public Tile(Tile t){
        this.width=t.getWidth();
        this.height = t.getHeight();
        this.tile = new int[height][width];
        for(int i=0;i<t.getHeight();i++){
            for(int j=0;j< t.getWidth();j++){
                this.tile[i][j] = t.getTile()[i][j];
            }
        }
    }

    /**
     * @return Tile width
     */
    public int getWidth(){
        return width;
    }

    /**
     *
     * @return Tile height
     */
    public int getHeight(){
        return height;
    }

    /**
     *
     * @return Tile two-dimensional array
     */
    public int[][] getTile(){
        return tile;
    }
}
