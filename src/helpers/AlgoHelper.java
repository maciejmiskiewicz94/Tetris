package helpers;

import data.Tile;
import data.Well;

import java.awt.*;
import java.util.Stack;

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
        int wellHeight=well.getHeight();
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
                   // Well well1=well;
                    //int mini = advancedFunction(well1);
                    //int mini=1;
                    //double output=(double) qty/mini;
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

    private int advancedFunction(Well mainWell) {

        int minimum=0;
        Well tmp=mainWell;
        minimum=0;
        for(int i=mainWell.getHeight()-1;i>=0;i--) {
            for (int j = 0; j < mainWell.getWidth(); j++) {
                if(mainWell.well[i][j]==0)
                {
                    minimum--;
                   // floodFill(mainWell,i,j,minimum);
                }
            }
            }
            minimum=(int) ((int)minimum/(int)mainWell.getWidth());
          //  printWell(mainWell);
        mainWell=tmp;
        return Math.abs(minimum);



    }

    public static void floodFill(Well b, int x, int y, int z)
    {
        Stack<Point> checkPoint = new Stack<Point>(); //to avoid recursion
        Well a=b;
        checkPoint.push(new Point(x, y));
        while (checkPoint.size() > 0)
        {
            // Console.WriteLine(checkPoint.Count);
            Point tmp = checkPoint.pop();
            //  Console.WriteLine(tmp);
            if (tmp.getX() >= b.getWidth() || tmp.getX() < 0 || tmp.getY() >= b.getHeight() || tmp.getY() < 0)
            { }
            else {
                // x, y - starting point coordinates
                // old - must be different than newColor
                if (b.well[(int)tmp.getX()][(int)tmp.getY()] == 0)
                {
                    b.well[(int)tmp.getX()][(int)tmp.getY()]=z;
                    checkPoint.push(new Point((int)tmp.getX() + 1,(int) tmp.getY()));
                    checkPoint.push(new Point((int)tmp.getX() - 1,(int) tmp.getY()));
                    checkPoint.push(new Point((int)tmp.getX(), (int)tmp.getY() + 1));
                    checkPoint.push(new Point((int)tmp.getX(), (int)tmp.getY() - 1));

                }
            }
        }
        b=a;
    }
    public void printWell(Well wellToPrint){
        for(int i=0;i<wellToPrint.getHeight();i++){
            for(int j=0;j<wellToPrint.getWidth();j++){
                System.out.print(wellToPrint.well[i][j]);
            }
            System.out.println();
        }
    }



}
