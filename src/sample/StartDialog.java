package sample;

import QLearning.State;
import javax.swing.*;
import java.awt.*;

/*
This class is for the opening dialog window which is instantiated at runtime.
In this dialog the user can choose mode and adjust rounding factor.
 */


public class StartDialog {
    private JDialog dialog = new JDialog();
    String[] rewardModes = {"Reward for forward motion", "Reward for elevated feet", "Reward for bending one knee"};

    public StartDialog() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(0, 1));
        JLabel chooseModeLabel = new JLabel("Choose mode:", SwingConstants.CENTER);
        JComboBox modeComboBox = new JComboBox(rewardModes);
        JLabel setRoundFactor = new JLabel("Round factor: " + State.roundFactor,SwingConstants.CENTER);
        JSlider roundFactorSlider = new JSlider(5, 30, 15);
        JLabel noOfStatesLabel = new JLabel("Max number of states:",SwingConstants.CENTER);
        JLabel noOfStatesNumber = new JLabel(State.getMaxNumberOfStates() + "",SwingConstants.CENTER);
        JButton runSimulationButton = new JButton("Start learning!");

        modeComboBox.setSelectedIndex(1); // mode 1 is default, since this is more impressive than mode 0
        modeComboBox.updateUI();

        roundFactorSlider.addChangeListener(e1 ->
        {
            State.roundFactor = roundFactorSlider.getValue();
            setRoundFactor.setText("Round factor: " + State.roundFactor);
            noOfStatesNumber.setText(State.getMaxNumberOfStates() + "");
        });

        runSimulationButton.addActionListener(e -> {
            Main.mode = modeComboBox.getSelectedIndex();
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
