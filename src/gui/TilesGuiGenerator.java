package gui;

import data.Tile;
import data.Well;
import gui.interfaces.DynamicGuiGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;

/**
 * Created by Borys on 10/16/16.
 * Class which implements DynamicGuiGenerator interface
 * It is responsible for various processes to generate different GUI elements
 */
public class TilesGuiGenerator implements DynamicGuiGenerator {

    private int id;
//    private boolean test;
    private ArrayList<JSpinner> spinners;

    public TilesGuiGenerator(int id){
//        this.test=false;
        this.id=id;
        spinners = new ArrayList<>();
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
        spinners.add(input);

        fillFullGrid(maxHeight,maxWidth,tileGrid,15,true,singleTile);

        result.add(tileGrid);
        result.add(controlButtons);

        return result;
    }
    public JPanel generateWell(int maxWidth)
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

    @Override
    public void fullFillWell(Well toDisplay) {
        JPanel p = toDisplay.getWellPanel();
        p.removeAll();

        int wellWSize = toDisplay.getWidth()*toDisplay.getWellMult();
        int wellHSize = toDisplay.getHeight()*toDisplay.getWellMult();


        BufferedImage b_img = new BufferedImage(wellWSize, wellHSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D    graphics = b_img.createGraphics();
        graphics.setPaint( Color.WHITE );
        graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );

        //Filling image with a tile
        graphics.setPaint(Color.BLACK);
        int sizeOfCell = 1;
        sizeOfCell*=toDisplay.getWellMult();

        int x = 0, y = 0;

        for(int i = 0;i < toDisplay.getHeight(); i++){
            x=0;
            for(int j = 0;j < toDisplay.getWidth(); j++){
                if(toDisplay.well[i][j]!=0){
//                    System.out.println("FILLING PIXEL ("+x+","+y+")");
                    int r=0, g=0, b=0;
                    r = (toDisplay.well[i][j]*10) % 255;
                    g = (toDisplay.well[i][j]*100) % 255;
                    b = (toDisplay.well[i][j]*10) % 255;
                    graphics.setPaint(new Color(r,g,b));
                    graphics.fillRect(x,y,sizeOfCell,sizeOfCell);
                }
                x+=sizeOfCell;
            }
            y+=sizeOfCell;
        }

        //-------------------------
        ImageIcon oicon = new ImageIcon(b_img);
        JLabel lab = new JLabel(oicon);
        p.add(lab,BorderLayout.CENTER);

        p.revalidate();
        p.repaint();
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
    public ArrayList<JSpinner> getSpinners(){
        return this.spinners;
    }

}
