package data;

/**
 * Created by Borys on 10/16/16.
 */
public class ProcessingTile extends Tile {

    private Tile[] fourTypes;

    public ProcessingTile(int w, int h, int[][] tile) {
        super(w, h, tile);
        fourTypes = new Tile[4];
        fourTypes[0]=new Tile(w,h,tile);
        for(int i=0;i<3;i++) {
            fourTypes[i+1] = new Tile(w,h,generateTiles(fourTypes[i].getTile()));
        }
    }

    private int [][] generateTiles(int[][] tile) {
        int[][] rotatedTile = new int[tile[0].length][tile.length];
        for(int i=0; i<tile[0].length; i++){
            for(int j=tile.length-1; j>=0; j--){
                rotatedTile[i][j] = tile[j][i];
            }
        }

        return rotatedTile;
    }
}
