package helpers;

import data.Tile;
import data.Well;
import org.omg.CORBA.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Maciej on 2016-11-21.
 */
public class Serialization {
    private int id;
    public Serialization(int id)
    {
        this.id=id;
    }

    public void serialize(ArrayList<Well> wellList, ArrayList<ArrayList<Tile>> tilesOfTilesList)
    {
        String output="";
        int k = wellList.size();
        output+=Integer.toString(k)+System.lineSeparator();

        for(int i=0;i<wellList.size();i++)
        {
            int a=wellList.get(i).getWidth();
            int b=wellList.get(i).getHeight();
            output+=Integer.toString(a)+" "+Integer.toString(b)+System.lineSeparator();
            for(int x=0;x<b;x++)
            {
                for(int y=0;y<a;y++)
                {
                    output+=Integer.toString(wellList.get(i).well[x][y])+ " ";
                }
                output+=System.lineSeparator();
            }
            ArrayList<Tile> currentTiles=tilesOfTilesList.get(i);
            output+=Integer.toString(currentTiles.size())+System.lineSeparator();
            for(int iter=0;iter<currentTiles.size();iter++)
            {
                int width=currentTiles.get(iter).getWidth();
                int heighht=currentTiles.get(iter).getHeight();
                output+=Integer.toString(width)+" "+Integer.toString(heighht)+System.lineSeparator();
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
        try
        {
            File file=new File("currentProgramState.txt");
            FileWriter fw=new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(output);
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
