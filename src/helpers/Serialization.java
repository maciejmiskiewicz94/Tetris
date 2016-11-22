package helpers;

import controller.TilesManager;
import data.ProcessingTile;
import data.Tile;
import data.Well;
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

    public void serialize(ArrayList<Well> wellList, ArrayList<ArrayList<ProcessingTile>> tilesOfTilesList, String path)
    {

        String output="";
        int k = wellList.size();
        output+=Integer.toString(k)+System.lineSeparator();

        for(int i=0;i<wellList.size();i++)
        {
            int a=wellList.get(i).getWidth();
            int b=wellList.get(i).getHeight();
            output+=Integer.toString(a)+" "+Integer.toString(b)+" "+System.lineSeparator();
            for(int x=0;x<b;x++)
            {
                for(int y=0;y<a;y++)
                {
                    output+=Integer.toString(wellList.get(i).well[x][y])+ " ";
                }
                output+=" "+System.lineSeparator();
            }
            ArrayList<ProcessingTile> currentTiles=tilesOfTilesList.get(i);
            output+=Integer.toString(currentTiles.size())+System.lineSeparator();
            for(int iter=0;iter<currentTiles.size();iter++)
            {
                int width=currentTiles.get(iter).getWidth();
                int heighht=currentTiles.get(iter).getHeight();
                output+=Integer.toString(width)+" "+Integer.toString(heighht)+" "+System.lineSeparator();
                for(int tileheight=0;tileheight<heighht;tileheight++)
                {
                    for(int tilewidth=0;tilewidth<width;tilewidth++)
                    {
                        output+=currentTiles.get(i).getTile()[tileheight][tilewidth]+ " ";
                    }
                    output+=System.lineSeparator();
                }
            }
        }
        Writer writer = null;

        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"));
            writer.write(output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void deserialize (File file)
    {
        TilesGuiGenerator TGG=new TilesGuiGenerator(1);
        int width=0;
        try (Scanner sc = new Scanner(file)) {
            this.backtrack = sc.nextInt();
            sc.nextLine();
            for(int i=0;i<backtrack;i++)
            {
                width=sc.nextInt();
                wells1.add(i, new Well(width,TGG.generateWell(width,getMultiplier(width)), getMultiplier(width)));
                wells1.get(i).setHeight(sc.nextInt());
                sc.nextLine();
                for(int h=0;h<wells1.get(i).getWidth();h++)
                {
                    for(int w=0;w<wells1.get(i).getWidth();w++)
                    {
                        wells1.get(i).well[h][w]=sc.nextInt();
                    }
                    sc.nextLine();
                }
                sc.nextLine();
                int numTiles=sc.nextInt();
                sc.nextLine();
                for(int j=0;j<numTiles;j++)
                {
                   //TODO readind tiles
                    int[][]a=null;
                    ProcessingTile tile=new ProcessingTile(sc.nextInt(),sc.nextInt(),a,j);
                    for(int l=0;l<tile.getHeight();l++){
                        for(int k=0;k<tile.getWidth();k++){
                            tile.getTile()[l][k] = sc.nextInt();
                        }
                    }
                    tiles1.get(i).add(j,tile);

                }
            }
            sc.close();

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



