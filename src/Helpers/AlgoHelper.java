package Helpers;

import data.Well;

/**
 * Created by Maciej on 2016-11-17.
 */
public class AlgoHelper {
    private int id;
    public AlgoHelper(int id){
//        this.test=false;
        this.id=id;
    }
    public double calculateQuality(Well wellsource)
    {
        int[][]well=wellsource.well;
       int quality=0;
        int maxheight=0;
        int counter=0;
        int zeroscount=0;
        for(int i=0;i<wellsource.getWidth();i++)
        {
            if(well[i][maxheight]==1)
            {
                counter++;
            }
            else{
                zeroscount++;
                if(zeroscount==wellsource.getWidth())
                {
                    maxheight--;
                }
            }
            if(i==wellsource.getWidth()-1) {
                maxheight++;
            }
            quality=(counter/(wellsource.getWidth()*maxheight))*100;
        }
        return quality;
    }

}
