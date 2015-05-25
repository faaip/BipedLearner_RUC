package sample;

import QLearning.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by frederikjuutilainen on 25/05/15.
 */
public class StartDialog {
    private JDialog dialog = new JDialog();
    String[] rewardModes = {"Reward for forward motion", "Reward for elevated feet", "Reward for bending one knee", "Reward for upright position", "Reward for backwards motion"};

    public StartDialog() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(0, 1));
        JLabel chooseModeLabel = new JLabel("Choose mode:", SwingConstants.CENTER);
        JComboBox modeComboBox = new JComboBox(rewardModes);
        JLabel setRoundFactor = new JLabel("Round factor: " + State.roundFactor,SwingConstants.CENTER);
        JSlider roundFactorSlider = new JSlider(5, 30, 15);
        JLabel noOfStatesLabel = new JLabel("Theoretical number of states:",SwingConstants.CENTER);
        JLabel noOfStatesNumber = new JLabel(State.getTheoreticalNumberOfStates() + "",SwingConstants.CENTER);
        JButton runSimulationButton = new JButton("Start learning!");


        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });

        roundFactorSlider.addChangeListener(e1 ->
        {
            State.roundFactor = roundFactorSlider.getValue();
            setRoundFactor.setText("Round factor: " + State.roundFactor);
            noOfStatesNumber.setText(State.getTheoreticalNumberOfStates() + "");
        });

        runSimulationButton.addActionListener(e -> {
            Main.mode = modeComboBox.getSelectedIndex();
            System.out.println(Main.mode);
            dialog.dispose();
        });

        // Add to startPanel
        startPanel.add(chooseModeLabel);
        startPanel.add(modeComboBox);
        startPanel.add(setRoundFactor);
        startPanel.add(roundFactorSlider);
        startPanel.add(noOfStatesLabel);
        startPanel.add(noOfStatesNumber);
        startPanel.add(runSimulationButton);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.add(startPanel);
        dialog.pack();
        dialog.setLocation(400, 200);

        dialog.setTitle("BiPed Learner");
        dialog.setVisible(true);
    }

}
