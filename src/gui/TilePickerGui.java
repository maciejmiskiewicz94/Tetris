package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.PAGE_AXIS;

/**
 * Created by Maciej on 2016-10-15.
 */
public class TilePickerGui extends JFrame {
    private JButton acceptTilesButton;
    private JPanel rootPanel;
    private JPanel buttonsPanel;
    private JButton clearButton;
    private JScrollPane paneScrollPane;
    private JPanel tilesPanel;

    public TilePickerGui() {
        super("Tetris simulator (tiles picker window)");
        createUIComponents();
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        acceptTilesButton = new JButton("Accept tiles");
        clearButton = new JButton("Clear");
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(acceptTilesButton, BorderLayout.CENTER);
        buttonsPanel.add(clearButton, BorderLayout.AFTER_LAST_LINE);

        paneScrollPane = new JScrollPane();
        paneScrollPane.setPreferredSize(new Dimension(500, 450));

        tilesPanel = new JPanel();
        tilesPanel.setLayout(new BoxLayout(tilesPanel, PAGE_AXIS));

        paneScrollPane.getViewport().add(tilesPanel);

        rootPanel.add(paneScrollPane);
        rootPanel.add(buttonsPanel);
    }
}
