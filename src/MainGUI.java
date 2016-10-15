import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
/**
 * Created by Maciej on 2016-10-15.
 */
public class MainGUI extends JFrame {
    private JButton loadTilesButton;
    private JPanel mainPanel;
    private JButton chooseTilesButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton startNStepsButton;
    private JSpinner nStepsPicker;
    private JButton autoSerializationMinsButton;
    private JSpinner SerializationPicker;
    private JButton backtrackingParameterButton;
    private JSpinner BacktrackingPicker;
    private JButton saveCurrentStateButton;
    private JLabel CurrentStatePanel;

    public MainGUI(){
        super("Hello World");
        setContentPane(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadTilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(MainGUI.this,"clicked!");
            }
        });
        setVisible(true);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
