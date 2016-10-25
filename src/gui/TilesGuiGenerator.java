package gui;

import data.Tile;
import gui.interfaces.DynamicGuiGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
        JSpinner input = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
//        JTextField input = new JTextField("      1");
        controlButtons.add(input);

        fillFullGrid(maxHeight,maxWidth,tileGrid,15,true,singleTile);

        result.add(tileGrid);
        result.add(controlButtons);

        return result;
    }
    public JPanel generateWell(int maxWidth, int m)
    {

        int wellWSize = maxWidth;
        int wellHSize = wellWSize;

        JPanel result=new JPanel();
        result.setLayout(new BorderLayout());
        result.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.black));
        BufferedImage b_img = new BufferedImage(wellWSize, wellHSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D    graphics = b_img.createGraphics();
        graphics.setPaint( Color.WHITE );
        graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );

        ImageIcon oicon = new ImageIcon(b_img);
        JLabel lab = new JLabel(oicon);
        result.add(lab,BorderLayout.CENTER);
        return result;
    }
    private void fillFullGrid(int maxHeight, int maxWidth, JPanel grid, int cellSize, boolean flag, Tile singleTile){
        for(int i=0;i<maxHeight;i++){
            for (int j=0;j<maxWidth;j++){
                BufferedImage b_img = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D    graphics = b_img.createGraphics();
                graphics.setPaint( Color.WHITE );
                if(flag){
                    if((i<singleTile.getHeight())&&(j<singleTile.getWidth())){
                        if(singleTile.getTile()[i][j]==1){
                            graphics.setPaint( Color.BLUE );
                        }
                    }
                }
                graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
                ImageIcon oicon = new ImageIcon(b_img);
                JLabel lab = new JLabel(oicon);
                grid.add(lab);
            }
        }
    }
    private void fillFullGrid(int maxHeight, int maxWidth, JPanel grid, int cellSize){
        fillFullGrid(maxHeight,maxWidth,grid,cellSize,false,new Tile(0,0,new int[4][4]));
    }

}
