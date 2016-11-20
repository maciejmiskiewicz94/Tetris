package Helpers;

import data.Tile;
import data.Well;

/**
 * Created by Maciej on 2016-11-17.
 */
public class AlgoHelper {
    private int id;
    public AlgoHelper(int id){
        this.id=id;
    }
    public double calculateQuality(Well well)
    {
        int quality=0;
        int counter=0;
        int zerosCount=0;
        //TODO we need to make getHeight for wells
        int wellHeight=well.getWidth();
        int maxHeight=0;


        for(int i=wellHeight-1;i>=0;i--) {
            for (int j = 0; j < well.getWidth(); j++) {
                if(well.well[i][j]!=0)
                {
                    counter++;
                }
                else if(well.well[i][j]==0)
                {
                    zerosCount++;
                }
                if(zerosCount==well.getWidth())
                {
                    int area=well.getWidth()*(wellHeight-i-1);
                    double qty =(double) (int)counter/(int)area;
                    return qty;
                }
            }
            zerosCount=0;
        }


        //quality=(counter/(wellSource.getWidth()*maxHeight))*100;
        return -1;
    }

    public void seeTheTiles(Tile[] fourTypes) {
        for(int i=0;i<fourTypes.length;i++)
        {
            Tile currentTile=fourTypes[i];
            System.out.println("///");
            System.out.println("///");
            printTile(currentTile);
            System.out.println("///");
            System.out.println("///");
        }
    }

    public void printTile(Tile tileToPrint){
        for(int i=0;i<tileToPrint.getHeight();i++){
            for(int j=0;j<tileToPrint.getWidth();j++){
                System.out.print(tileToPrint.getTile()[i][j]);
            }
            System.out.println();
        }
    }
}
