package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
    private JSpinner globalChange;

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
        globalChange = new JSpinner();
        globalChange.setModel(new SpinnerNumberModel(0, 0, 10000, 1));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        buttonsPanel.add(acceptTilesButton, BorderLayout.CENTER);
        buttonsPanel.add(clearButton, BorderLayout.AFTER_LAST_LINE);
        buttonsPanel.add(globalChange,BorderLayout.BEFORE_FIRST_LINE);

        paneScrollPane = new JScrollPane();
        paneScrollPane.setPreferredSize(new Dimension(300, 350));

        tilesPanel = new JPanel();
        tilesPanel.setLayout(new BoxLayout(tilesPanel, PAGE_AXIS));

        paneScrollPane.getViewport().add(tilesPanel);

        rootPanel.add(paneScrollPane);
        rootPanel.add(buttonsPanel);

        acceptTilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                globalChange.setValue(0);
            }
        });
    }
    public int getValueForAllTiles(){
        return (int) globalChange.getValue();
    }

    public void showTiles(Stack<JPanel> jPanels) {

        tilesPanel.removeAll();
        while (!jPanels.empty()) tilesPanel.add(jPanels.pop());
    }
}
