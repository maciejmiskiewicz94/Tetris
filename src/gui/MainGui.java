package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.ThreadsManager;
import controller.TilesManager;
import data.Well;
import controller.interfaces.Manager;
import processing.ProcessingUnit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Maciej on 2016-10-15.
 */
public class MainGui extends JFrame {

    /*
    * Main gui components
    * */
    private JPanel mainPanel;
    private JPanel properWellPanel;
    private JButton loadTilesButton;
    private JButton chooseTilesButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton startNStepsButton;
    private JLabel currentStatePanel;
    private JButton saveCurrentStateButton;
    private JButton loadProgramStateButton;
    private JPanel buttonPanel;
    private JSpinner serializationPicker;
    private JSpinner startNStepsPicker;
    private JSpinner backtrackingPicker;
    private JPanel wellPanel;

    /*
    * Tiles gui window
    * */
    private TilePickerGui tileGui;

    /*
    * Extra components
    * */
    private File userFile;
    private Manager manager;
    private int backtrackingParam;
    private ThreadsManager thManager;

    private ArrayList<ProcessingUnit> processingUnits;

    public MainGui() {
        super("Tetris simulator (main window)");
        $$$setupUI$$$();
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        chooseTilesButton.setEnabled(false);
        startButton.setEnabled(false);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        startNStepsButton.setEnabled(false);
        startNStepsPicker.setEnabled(false);
        serializationPicker.setEnabled(false);
        backtrackingPicker.setEnabled(false);
        saveCurrentStateButton.setEnabled(false);
        backtrackingPicker.setModel(new SpinnerNumberModel(0, 0, 20, 1));
        startNStepsPicker.setModel(new SpinnerNumberModel(0, 0, 99, 1));
        serializationPicker.setModel(new SpinnerNumberModel(0, 0, 99, 1));
        JScrollPane scrollPanel = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setAutoscrolls(true);
        properWellPanel = new JPanel();
        wellPanel.add(scrollPanel, BorderLayout.CENTER);
        scrollPanel.getViewport().add(properWellPanel);


        buttonPanel.setBorder(BorderFactory.createTitledBorder("Program control"));
        //Extra initializations with lower priority
        this.tileGui = new TilePickerGui();

        loadTilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTilesButton.setEnabled(false);
                if (loadTilesFromFile()) setStatus("Tiles loaded from file!");
                else {
                    setStatus("Error with file loading!");
                }
                loadTilesButton.setEnabled(true);
            }
        });
        chooseTilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tileGui.setVisible(true);
                setStatus("Tiles chooser is open");
            }
        });
        startNStepsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                int n = (int) startNStepsPicker.getValue();
                startGeneratingWellsAfterStart(n);
            }
        });
        startButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNStepsButton.setEnabled(false);
                startGeneratingWellsAfterStart(0);
                start(1);
            }
        }));
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Buttons disabling
                startNStepsButton.setEnabled(true);
                startButton.setEnabled(true);
                pauseButton.setEnabled(true);
                loadTilesButton.setEnabled(true);
                chooseTilesButton.setEnabled(true);
                loadProgramStateButton.setEnabled(true);
                setStatus("Computation has stopped");
            }
        });
        startNStepsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Buttons disabling
                startButton.setEnabled(false);
                pauseButton.setEnabled(false);
                loadTilesButton.setEnabled(false);
                chooseTilesButton.setEnabled(false);
                loadProgramStateButton.setEnabled(false);
                start(2);
            }
        });
        saveCurrentStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPanel.setPreferredSize(new Dimension(wellPanel.getWidth() - 50, wellPanel.getHeight() - 50));
                wellPanel.revalidate();
                wellPanel.repaint();
            }
        });

    }

    private void startGeneratingWellsAfterStart(int n) {
        loadTilesButton.setEnabled(false);
        chooseTilesButton.setEnabled(false);
        loadProgramStateButton.setEnabled(false);
        startButton.setEnabled(false);
        if (n > 0) {
            String param = "" + n;
            setStatus("The computations started and will stop after " + param + " steps.");

        } else {
            setStatus("The computations have started.");
        }

        backtrackingParam = (int) backtrackingPicker.getValue();

        int param = backtrackingParam / 2;

        properWellPanel.removeAll();
        properWellPanel.setLayout(new GridLayout(2, param, 10, 10));
        properWellPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ArrayList<Well> w = manager.generateWells(backtrackingParam);

        for (int i = 0; i < w.size(); i++) {
            JPanel well = w.get(i).wellPanel;
            properWellPanel.add(well);
        }
        wellPanel.revalidate();
        wellPanel.repaint();
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

            manager = new TilesManager(userFile);
            tileGui.showTiles(manager.generateGuiForTiles());
            tileGui.setVisible(true);

            chooseTilesButton.setEnabled(true);
            startButton.setEnabled(true);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            startNStepsButton.setEnabled(true);
            startNStepsPicker.setEnabled(true);
            serializationPicker.setEnabled(true);
            backtrackingPicker.setEnabled(true);
            saveCurrentStateButton.setEnabled(true);

            return loaded;
        } else {
            System.out.println("NO FILE");
            return loaded;
        }
    }

    //Methods to hadle EVERYTHING connected with Algorithm
    //Start param is responsible for the start type, for now leave it as it is...)
    private void start(int startParam) {
        manager.prepareForStart();
        generateProcessingUnits(); //Start algorithm
    }

    private void generateProcessingUnits() {
        this.thManager = new ThreadsManager(backtrackingParam);
        thManager.initializeThreads();
    }
    private void setStatus(String status) {
        currentStatePanel.setText(status);
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
        mainPanel.setLayout(new GridLayoutManager(14, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setMaximumSize(new Dimension(700, 500));
        mainPanel.setMinimumSize(new Dimension(700, 500));
        mainPanel.setPreferredSize(new Dimension(700, 500));
        wellPanel = new JPanel();
        wellPanel.setLayout(new BorderLayout(0, 0));
        wellPanel.setVisible(true);
        mainPanel.add(wellPanel, new GridConstraints(0, 0, 14, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(400, 400), new Dimension(400, 300), null, 1, false));
        final Spacer spacer1 = new Spacer();
        wellPanel.add(spacer1, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(22, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(buttonPanel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        buttonPanel.add(spacer2, new GridConstraints(0, 4, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        buttonPanel.add(spacer3, new GridConstraints(2, 3, 20, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pauseButton = new JButton();
        pauseButton.setText("Pause");
        buttonPanel.add(pauseButton, new GridConstraints(16, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        startNStepsButton = new JButton();
        startNStepsButton.setText("Start (n steps)");
        buttonPanel.add(startNStepsButton, new GridConstraints(17, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Auto Serialization (mins)");
        buttonPanel.add(label1, new GridConstraints(18, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveCurrentStateButton = new JButton();
        saveCurrentStateButton.setText("Save current state");
        buttonPanel.add(saveCurrentStateButton, new GridConstraints(20, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        loadProgramStateButton = new JButton();
        loadProgramStateButton.setText("Load program state");
        buttonPanel.add(loadProgramStateButton, new GridConstraints(21, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 0, false));
        serializationPicker = new JSpinner();
        buttonPanel.add(serializationPicker, new GridConstraints(18, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        buttonPanel.add(stopButton, new GridConstraints(15, 0, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        startButton = new JButton();
        startButton.setText("Start");
        buttonPanel.add(startButton, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        chooseTilesButton = new JButton();
        chooseTilesButton.setText("Choose tiles");
        buttonPanel.add(chooseTilesButton, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        loadTilesButton = new JButton();
        loadTilesButton.setText("Load tiles");
        buttonPanel.add(loadTilesButton, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 32), null, 0, false));
        startNStepsPicker = new JSpinner();
        buttonPanel.add(startNStepsPicker, new GridConstraints(17, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backtrackingPicker = new JSpinner();
        buttonPanel.add(backtrackingPicker, new GridConstraints(19, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Backtracking parameter");
        buttonPanel.add(label2, new GridConstraints(19, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        currentStatePanel = new JLabel();
        currentStatePanel.setText("CURRENT PROGRAM STATE");
        mainPanel.add(currentStatePanel, new GridConstraints(13, 3, 1, 1, GridConstraints.ANCHOR_SOUTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
