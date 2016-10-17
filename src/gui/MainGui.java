package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Maciej on 2016-10-15.
 */
public class MainGui extends JFrame {

    /*
    * Main gui components
    * */
    private JPanel mainPanel;
    private JButton loadTilesButton;
    private JButton chooseTilesButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton startNStepsButton;
    private JSpinner nStepsPicker;
    private JButton autoSerializationMinsButton;
    private JSpinner serializationPicker;
    private JButton backtrackingParameterButton;
    private JSpinner backtrackingPicker;
    private JButton saveCurrentStateButton;
    private JLabel currentStatePanel;

    /*
    * Tiles gui window
    * */
    private TilePickerGui tileGui;

    /*
    * Extra components
    * */
    private File userFile;

    public MainGui() {
        super("Tetris simulator (main window)");
        $$$setupUI$$$();
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //Extra initializations with lower priority
        this.tileGui = new TilePickerGui();

        loadTilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTilesButton.setEnabled(false);
                if (loadTilesFromFile()) currentStatePanel.setText("Tiles loaded from file!");
                else currentStatePanel.setText("Error with file loading!");
                loadTilesButton.setEnabled(true);
            }
        });

    }

    private boolean loadTilesFromFile() {
        boolean loaded = false;
        JFileChooser openFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT file", "txt");
        openFile.setFileFilter(filter);
        int result = openFile.showDialog(null, "Choose file with tiles");
        if (result == JFileChooser.APPROVE_OPTION) {
            userFile = openFile.getSelectedFile();
            loaded = true;
            System.out.println("User has chosen a proper file");
            tileGui.setVisible(true);

            return loaded;
        } else {
            System.out.println("NO FILE");
            return loaded;
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(10, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(700, 500));
        mainPanel.setMinimumSize(new Dimension(700, 500));
        mainPanel.setPreferredSize(new Dimension(700, 500));
        nStepsPicker = new JSpinner();
        mainPanel.add(nStepsPicker, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startNStepsButton = new JButton();
        startNStepsButton.setText("Start (n steps)");
        mainPanel.add(startNStepsButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        pauseButton = new JButton();
        pauseButton.setText("Pause");
        mainPanel.add(pauseButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        mainPanel.add(stopButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        mainPanel.add(startButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        loadTilesButton = new JButton();
        loadTilesButton.setText("Load tiles");
        mainPanel.add(loadTilesButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        chooseTilesButton = new JButton();
        chooseTilesButton.setText("Choose tiles");
        mainPanel.add(chooseTilesButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        autoSerializationMinsButton = new JButton();
        autoSerializationMinsButton.setEnabled(true);
        autoSerializationMinsButton.setText("Auto serialization (mins)");
        mainPanel.add(autoSerializationMinsButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        serializationPicker = new JSpinner();
        mainPanel.add(serializationPicker, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backtrackingParameterButton = new JButton();
        backtrackingParameterButton.setText("Backtracking parameter");
        mainPanel.add(backtrackingParameterButton, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        backtrackingPicker = new JSpinner();
        mainPanel.add(backtrackingPicker, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveCurrentStateButton = new JButton();
        saveCurrentStateButton.setText("Save current state");
        mainPanel.add(saveCurrentStateButton, new GridConstraints(8, 2, 2, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setVisible(true);
        mainPanel.add(panel1, new GridConstraints(0, 0, 10, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, 400), new Dimension(400, 300), new Dimension(400, 400), 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        currentStatePanel = new JLabel();
        currentStatePanel.setText("STATE OF THE PROGRAM");
        mainPanel.add(currentStatePanel, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
