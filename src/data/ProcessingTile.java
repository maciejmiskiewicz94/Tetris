package data;

/**
 * Created by Borys on 10/16/16.
 */
public class ProcessingTile extends Tile {

    private Tile[] fourTypes;

    public ProcessingTile(int w, int h, int[][] tile) {
        super(w, h, tile);
        fourTypes = new Tile[4];
        generateTiles(tile);
    }

    private void generateTiles(int[][] tile) {

    }
}
