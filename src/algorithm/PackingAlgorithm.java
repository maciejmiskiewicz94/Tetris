package algorithm;

import data.Tile;
import data.Well;

import java.util.ArrayList;

/**
 * Created by Maciej on 2016-11-18.
 */
public class PackingAlgorithm {

    public Well resultingWell=null;

    public PackingAlgorithm(){
    }

    public Well runAlgorithm(Well well, Tile tile, int tileId)
    {
        Well result=well;
        int[][] kernel;
        int currentHeight=tile.getHeight()-1;
        int counter=0;
        int mainCounter=0;
        ArrayList<Integer> coords = new ArrayList<>();
        //TODO:
        //We need to assign a proper value for well height and make a getter function
        int wellHeight=well.getWidth();
        boolean working = true;
        for(int i=0;i<well.getHeight();i++)
        {
            for(int j=0;j<well.getWidth();j++)
            {
                int initI = i;
                if(currentHeight>=0&&well.well[well.getHeight()-1-i][j]==0&&tile.getTile()[currentHeight][0]==1&&currentHeight<tile.getHeight())
                {
                    if(tile.getWidth()+j>well.getWidth()){
//                        System.out.println("Let's do it!");
                        i++;
                        j=0;
                        break;
                    }
                    if((tile.getHeight()>=(well.getHeight()-1-i))){
                        well = increaseWellSize(well);
                        result=well;
                        System.out.println("INCREASING SIZE");
                    }
                    //Alternative could be 2 DFS to search
                    //First goes through the tile, only available cells and remmebers path
                    //Second goes through the board from starting point and try to recompute path of the first one.\
//                    System.out.println("CURRENT HIGHT - "+currentHeight);
                    boolean flag = false;
                    int a = 0;
                    initI=i;
                    boolean isAZero = true;
//                    System.out.println("COUNTER - "+counter);
                    counter=0;
                    mainCounter=0;
                    coords = new ArrayList<>();
                    for(;a<tile.getWidth();a++)
                    {
                        if(isAZero){
                            a=0;
                            isAZero=false;
                        }
//                        System.out.println("A is - "+ a);
                        if(currentHeight>=0) {
//                            System.out.println("I - "+i+" : J - "+j);
                            if (well.well[well.getHeight()-1-i][j + a] == 0 && tile.getTile()[currentHeight][a] == 1) {
                                flag=true;
                                counter++;
                                mainCounter++;
//                                result.well[well.getHeight()-1-i][j + a] = tileId;
                                coords.add(well.getHeight()-1-i);
                                coords.add(j+a);
//                                System.out.println("Putting tile: "+i+" - "+j+" "+tileId);
//                                System.out.println("Increasing 1 Mcounter for "+i+" - "+j);

                            } else if (tile.getTile()[currentHeight][a] == 0) {
                                counter++;
                                mainCounter++;
//                                System.out.println("Increasing 2 Mcounter for "+i+" - "+j);
                            }
                            if (counter == tile.getWidth()) {
                                currentHeight--;
                                counter = 0;
                                a = 0;
                                i++;
                                isAZero=true;
                            }
                        }
                    }
//                    System.out.println("MAIN COUNTER "+mainCounter);
                    if(!flag){
//                        System.out.println("Start from prev place");
                        result=well;
                        i = initI;
                        currentHeight=tile.getHeight()-1;
                    }
                    else if(mainCounter==(tile.getHeight()*tile.getWidth()))
                    {
                        //PUTTING TILE BECAUSE WE HAVE A PLACE FOR THAT
                        working=false;

                        for(int l=0;l<coords.size()-1;){
                            result.well[coords.get(l)][coords.get(l+1)] = tileId;
                            l+=2;
                        }
                        break;
                    }
                    else{
                        i = initI;
                        currentHeight=tile.getHeight()-1;
                    }
                }else{
//                    System.out.println("I am here! and init I is - "+initI + " And J is - "+j);
                    i=initI;
                    result=well;
                    currentHeight=tile.getHeight()-1;
//                    break;
                }

            }
            if(!working) break;
        }
        return result;
    }

    private Well increaseWellSize(Well well) {
        Well doubledHeightWell = new Well(well.getWidth(),well.wellPanel,well.wellMult);
        int[][] nWell = new int[well.getHeight()*2][well.getWidth()];
        for(int i=0;i<well.getHeight();i++){
            for (int j=0;j<well.getWidth();j++){
                nWell[i+well.getHeight()][j]=well.well[i][j];
            }
        }
        doubledHeightWell.well=nWell;
        doubledHeightWell.setHeight(well.getHeight()*2);
        return doubledHeightWell;
    }
}
