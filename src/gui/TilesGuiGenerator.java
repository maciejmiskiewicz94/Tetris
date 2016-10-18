package gui;

import data.Tile;
import gui.interfaces.DynamicGuiGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Borys on 10/16/16.
 */
public class TilesGuiGenerator implements DynamicGuiGenerator {

    private int id;

    public TilesGuiGenerator(int id){
        this.id=id;
    }
    @Override
    public JPanel generatePanel(Tile singleTile, int maxHeight, int maxWidth) {
        JPanel result = new JPanel();
        result.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        JPanel tileGrid = new JPanel();
        tileGrid.setLayout(new GridLayout(maxHeight,maxWidth,1,1));
        JPanel controlButtons = new JPanel();
        controlButtons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        JTextField input = new JTextField("Enter number");
        controlButtons.add(input);

        for(int i=0;i<maxHeight;i++){
            for (int j=0;j<maxWidth;j++){
                BufferedImage b_img = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
                Graphics2D    graphics = b_img.createGraphics();
                graphics.setPaint( Color.WHITE );
                if((i<singleTile.getHeight())&&(j<singleTile.getWidth())){
                    if(singleTile.getTile()[i][j]==1){
                        graphics.setPaint( Color.BLUE );
                    }
                }
                graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
                ImageIcon oicon = new ImageIcon(b_img);
                JLabel lab = new JLabel(oicon);
                tileGrid.add(lab);
            }
        }

        result.add(tileGrid);
        result.add(controlButtons);

        return result;
    }
    public JPanel generateWell(int maxWidth)
    {
        JPanel result=new JPanel();

        return result;
    }
}
