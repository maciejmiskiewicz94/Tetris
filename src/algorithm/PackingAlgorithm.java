package algorithm;

import data.Tile;
import data.Well;

/**
 * Created by Maciej on 2016-11-18.
 */
public class PackingAlgorithm {
    public Well runAlgorithm(Well well, Tile tile, int tileId)
    {
        Well result=well;
        int[][] kernel;
        int currentHeight=0;
        int counter=0;
        int mainCounter=0;
        //TODO:
        //We need to assign a proper value for well height and make a getter function
        int wellHeight=well.getWidth();
        boolean working = true;
        for(int i=0;i<well.getWidth();i++)
        {
            for(int j=wellHeight-1;j>=0;j--)
            {
                if(well.well[i][j]==0&&tile.getTile()[currentHeight][tile.getHeight()]==1&&currentHeight<=tile.getHeight())
                {
                    //Alternative could be 2 DFS to search
                    //First goes through the tile, only available cells and remmebers path
                    //Second goes through the board from starting point and try to recompute path of the first one.
                    for(int a=0;a<tile.getWidth();a++)
                    {
                        if(well.well[i+a][j]==0&&tile.getTile()[a][currentHeight]==1)
                        {
                            counter++;
                            mainCounter++;
                            result.well[i+a][j] = 1;
                        }
                        else if(tile.getTile()[a][currentHeight]==0)
                        {
                            counter++;
                            mainCounter++;
                        }
                        if(counter==tile.getWidth())
                        {
                            currentHeight++;
                            counter=0;
                        }
                    }
                }
                else result=well; //NOT sure
                if(mainCounter==tile.getHeight()*tile.getWidth())
                {
                    //PUTTING TILE BECAUSE WE HAVE A PLACE FOR THAT

                    break;
                }
            }
            if(!working) break;
        }
        return result;
    }
}
