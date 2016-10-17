package gui;

import gui.interfaces.DynamicGuiGenerator;

import javax.swing.*;

/**
 * Created by Borys on 10/16/16.
 */
public class TilesGuiGenerator implements DynamicGuiGenerator {

    private int id;

    public TilesGuiGenerator(int id){
        this.id=id;
    }
    @Override
    public JPanel generatePanel() {
        return null;
    }
}
