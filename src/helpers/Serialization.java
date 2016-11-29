package helpers;

import controller.ThreadsManager;
import controller.TilesManager;
import data.ProcessingTile;
import data.Tile;
import data.Well;
import gui.MainGui;
import gui.TilesGuiGenerator;
import org.omg.CORBA.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by Maciej on 2016-11-21.
 */
public class Serialization {
    private int id;
    private ArrayList<Well> wells1;
    private ArrayList<ArrayList<ProcessingTile>> tiles1;
    private int backtrack;
    public Serialization(int id)
    {
        this.id=id;
    }

    public void serialize(ArrayList<Well> wellList, ArrayList<ArrayList<ProcessingTile>> tilesOfTilesList, String path) throws IOException {

        FileOutputStream out = new FileOutputStream(path);

        String output="";
        int k = wellList.size();
        output+=Integer.toString(k)+System.lineSeparator();
//        writer.println(Integer.toString(k));

        for(int i=0;i<wellList.size();i++)
        {
            int a=wellList.get(i).getWidth();
            int b=wellList.get(i).getHeight();
            output+=Integer.toString(a)+" "+Integer.toString(b)+" "+System.lineSeparator();
//            writer.println(Integer.toString(a)+" "+Integer.toString(b));
            for(int x=0;x<b;x++)
            {
                for(int y=0;y<a;y++)
                {
                    output+=Integer.toString(wellList.get(i).well[x][y])+ " ";
//                    writer.print(Integer.toString(wellList.get(i).well[x][y])+ " ");
                }
                output+=" "+System.lineSeparator();
            }
            ArrayList<ProcessingTile> currentTiles=tilesOfTilesList.get(i);
            output+=Integer.toString(currentTiles.size())+System.lineSeparator();
            for(int iter=0;iter<currentTiles.size();iter++)
            {
                int width=currentTiles.get(iter).getWidth();
                int heighht=currentTiles.get(iter).getHeight();
                int numOfTiles = currentTiles.get(iter).getNumberOfSuchTiles();
                int tileId = currentTiles.get(iter).getId();
                output+=Integer.toString(width)+" "+Integer.toString(heighht)+" "+numOfTiles+" "+tileId+System.lineSeparator();
                for(int tileheight=0;tileheight<heighht;tileheight++)
                {
                    for(int tilewidth=0;tilewidth<width;tilewidth++)
                    {
                        output+=currentTiles.get(iter).getTile()[tileheight][tilewidth]+ " ";
                    }
                    output+=System.lineSeparator();
                }
            }
        }
        out.write(output.getBytes());
        out.close();
    }
    public void deserialize (File file, MainGui gui)
    {
        wells1 = new ArrayList<>();
        tiles1 = new ArrayList<>();
        TilesGuiGenerator TGG=new TilesGuiGenerator(1);
        int width=0;
        try (Scanner sc = new Scanner(file)) {
            this.backtrack = sc.nextInt();
            for(int i=0;i<backtrack;i++)
            {
                width=sc.nextInt();
                wells1.add(i, new Well(width,TGG.generateWell(width), getMultiplier(width)));
                wells1.get(i).setHeight(sc.nextInt());
                for(int h=0;h<wells1.get(i).getHeight();h++)
                {
                    for(int w=0;w<wells1.get(i).getWidth();w++)
                    {
                        wells1.get(i).well[h][w]=sc.nextInt();
                    }
                }
                int numTiles=sc.nextInt();
                ArrayList<ProcessingTile> tmp = new ArrayList<>();
                for(int j=0;j<numTiles;j++)
                {
                    int h = sc.nextInt();
                    int w = sc.nextInt();
                    int num = sc.nextInt();
                    int id = sc.nextInt();
                    int[][] a= new int[h][w];
                    for(int l=0;l<h;l++){
                        for(int k=0;k<w;k++){
                            a[l][k] = sc.nextInt();
                        }
                    }
                    ProcessingTile tile=new ProcessingTile(w,h,a,id);
                    tile.setNumerOfSuchTiles(num);
                    tmp.add(tile);
                }
                tiles1.add(tmp);
            }
            sc.close();

            gui.deserializationFinish(backtrack,wells1,tiles1);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private int getMultiplier(int width) {
        int multi=0;
        if((width>0)&&(width<=50)){
          //  wellWSize = wellWidth*10;
            multi=10;
        }
        else if((width>50)&&(width<100)){
        //    wellWSize = wellWidth*5;
            multi=5;
        }
        else{
           // wellWSize = wellWidth;
            multi=1;
        }
        return multi;
    }

}



