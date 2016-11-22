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
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int[][] getTile(){
        return tile;
    }
}
