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
                    int area=well.getWidth()*(wellHeight-i);
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

//    private Well advancedFunction(Well mainWell, int i, int j, int minimalValue) {
//        Stack<Point> q=new Stack<Point>();
//
//        q.push(new Point(i,j));
//
//        Point p1=new Point(i,j);
//        while (q.size()!=0) {
//            p1=q.pop();
//
//            mainWell= putValue((int)p1.getX(),(int)p1.getY(),minimalValue, mainWell);
//
//        }
//
//        if (checkValidity((int)p1.getX()+1,(int)p1.getY(),mainWell))
//            q.push(new Point((int)p1.getX()+1,(int)p1.getY()));
//        if (checkValidity((int)p1.getX()-1,(int)p1.getY(),mainWell))
//            q.push(new Point((int)p1.getX()-1,(int)p1.getY()));
//        if (checkValidity((int)p1.getX(),(int)p1.getY()+1,mainWell))
//            q.push(new Point((int)p1.getX(),(int)p1.getY()+1));
//        if (checkValidity((int)p1.getX(),(int)p1.getY()-1,mainWell))
//            q.push(new Point((int)p1.getX(),(int)p1.getY()-1));
//        return mainWell;
//
//    }
//
//    private Well putValue(int x, int y, int minimalValue, Well well) {
//        Well well1=well;
//        well1.well[x][y]=minimalValue;
//        return well1;
//    }
//    private boolean checkValidity(int x, int y, Well well)
//    {
//        if(x<0) return false;
//        if(x>well.getWidth()-1) return false;
//        if(y<0) return false;
//        if(y>well.getHeight()-1) return false;
//        if(well.well[x][y]==0) return true;
//        else return false;
//    }


}
