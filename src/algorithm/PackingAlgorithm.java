package algorithm;

import data.Tile;
import data.Well;

/**
 * Created by Maciej on 2016-11-18.
 */
public class PackingAlgorithm {
    public Well runAlgorithm(Well well, Tile tile, int tileId)
    {
        Well result=null;
        int[][] kernel;
        int currentheight=0;
        int counter=0;
        int mainCounter=0;
        //TODO:
        //We need to assign a proper value for well height and make a getter function
        int wellHeight=well.getWidth();
        for(int i=0;i<well.getWidth();i++)
        {
            for(int j=wellHeight-1;j>=0;j--)
            {
                if(well.well[i][j]==0&&tile.getTile()[currentheight][tile.getHeight()]==1&&currentheight<=tile.getHeight())
                {
                    for(int a=0;a<tile.getWidth();a++)
                    {
                        if(well.well[i+a][j]==0&&tile.getTile()[a][currentheight]==1)
                        {
                            counter++;
                            mainCounter++;
                        }
                        else if(tile.getTile()[a][currentheight]==0)
                        {
                            counter++;
                            mainCounter++;
                        }
                        if(counter==tile.getWidth())
                        {
                            currentheight++;
                            counter=0;
                        }
                    }
                }
                if(mainCounter==tile.getHeight()*tile.getWidth())
                {
                    //PUTTING TILE BECAUSE WE HAVE A PLACE FOR THAT
                }
            }
        }
        return result;
    }
}
