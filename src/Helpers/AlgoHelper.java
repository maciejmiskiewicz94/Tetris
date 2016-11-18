package Helpers;

import data.Well;

/**
 * Created by Maciej on 2016-11-17.
 */
public class AlgoHelper {
    private int id;
    public AlgoHelper(int id){
        this.id=id;
    }
    public double calculateQuality(Well wellSource)
    {
        int[][]well=wellSource.well;
        int quality=0;
        int maxHeight=0;
        int counter=0;
        int zerosCount=0;
        for(int i=0;i<wellSource.getWidth();i++)
        {
            if(well[i][maxHeight]==1)
            {
                counter++;
            }
            else{
                zerosCount++;
                if(zerosCount==wellSource.getWidth())
                {
                    maxHeight--;
                }
            }
            if(i==wellSource.getWidth()-1) {
                maxHeight++;
            }
        }
        quality=(counter/(wellSource.getWidth()*maxHeight))*100;
        return quality;
    }

}
