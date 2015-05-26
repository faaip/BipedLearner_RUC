package sample;

import javax.swing.*;

import QLearning.JointAction;
import Rendering_dyn4j.Simulation;
import Rendering_dyn4j.ThreadSync;

import java.awt.*;
import java.io.IOException;

/*
This class contains the components and method for the graphical user interface.
All actions listeners are synchronized to ThreadSync. Refer to the written report for more details on this.
 */

public class GUI {
    public static HighScoreList highScoreList = new HighScoreList(); // JTable containing information for each generation
    private static JLabel generationLabel; //Label for no. of generation
    private static JLabel statesLabel; // Label for total number of states
    private static JLabel currentNsa; // Label for state-action frequency for current state-action pair
    private static JLabel currentQ; // Label for Q-value of current state-action pair
    private static JLabel agentStatus; // Label indicating if agent is exploring or learning
    private static JLabel currentAction; // Label for current action

    public GUI(Simulation world)  {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(350, 500);
        gui.setLocation(820, 0);
        gui.setTitle("Controls");

        //Panel for information about current walker
        JPanel currentBipedPanel = new JPanel();
        currentBipedPanel.setLayout(new GridLayout(0, 1));
        generationLabel = new JLabel("Current walker is generation #" + Main.generation);
        statesLabel = new JLabel("Number of Q-values: " + 0);
        currentNsa = new JLabel("Current Nsa count: " + 0);
        currentQ = new JLabel("Current Q: " + 0);
        agentStatus = new JLabel("Agent is exploring");
        currentAction = new JLabel("Action: ");

        // add components
        currentBipedPanel.add(generationLabel);
        currentBipedPanel.add(statesLabel);
        currentBipedPanel.add(currentNsa);
        currentBipedPanel.add(currentQ);
        currentBipedPanel.add(currentAction);
        currentBipedPanel.add(agentStatus);

        //Panel for controls
        JPanel control = new JPanel();
        JButton resetButton = new JButton("Reset walker"); // Button for resetting walker to initial position
        control.setLayout(new GridLayout(0, 1));
        JToggleButton pauseButton = new JToggleButton("Pause simulation"); // Button for pausing simulation
        JLabel simSpeed = new JLabel(Main.simulation.getSimulationSpeed() + " x Speed", SwingConstants.CENTER);
        JSlider simSpeedSlider = new JSlider(1, 50, 1); // Slider for simulation speed
        JButton randomAction = new JButton("Force random action");
        JButton outputCsvButton = new JButton("CSV");

        // Add Components to panel
        control.add(resetButton);
        control.add(pauseButton);
        control.add(randomAction);
        control.add(outputCsvButton);
        control.add(simSpeed);
        control.add(simSpeedSlider);

        // Panels for highscore table
        JScrollPane highScorePane = new JScrollPane(highScoreList.getTable());

        // Panels are added to two vertical split panes
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, currentBipedPanel, control);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1, highScorePane);

        // Panels are added to frame
        // highScorePane is only added if the reward mode is "0"
        if (Main.mode == 0 || Main.mode == 3 || Main.mode == 4) {
            gui.add(splitPane2);
        } else {
            gui.setSize(350, 250);
            gui.add(splitPane1);
        }
        gui.setVisible(true);

        // Actions listeners
        simSpeedSlider.addChangeListener(e -> {
            // Slider for changing simulation speed
            synchronized (ThreadSync.lock) {
                Main.simulation.setSimulationSpeed(simSpeedSlider.getValue());
                simSpeed.setText(Main.simulation.getSimulationSpeed() + " x Speed");
            }
        });

        resetButton.addActionListener(e1 -> {
            // Action listener for resetting position of walker
            synchronized (ThreadSync.lock) {
                Main.generation++;
                Simulation.walker.resetPosition();
                update();
            }
        });

        outputCsvButton.addActionListener(e2 -> {
            try {
                Main.fileWriter.createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pauseButton.addChangeListener(e1 -> { // TODO hvis pause er slået til og slider benyttes, kan pause ikke længere blablabl
            //Action Listener for pausing or resuming simulation
            synchronized (ThreadSync.lock) {
                if (Main.simulation.getSimulationSpeed() != 0) {
                    Main.simulation.setSimulationSpeed(0);
                } else {
                    Main.simulation.setSimulationSpeed(simSpeedSlider.getValue());
                }
            }
        });

        randomAction.addActionListener(e -> {
            // In cases where the bi-ped is to lazy to explore, one can force a random action by using this button
            JointAction random = Simulation.walker.getState().getRandomAction();
            random.doAction();
            update(random);
        });


    }

    public void update(int Nsa, double Q) {
        // Method for updating gui with current Nsa count and Q-value
        synchronized (ThreadSync.lock) {
            currentNsa.setText("Current Nsa count: " + Nsa);
            statesLabel.setText("Number of Q-values: " + Main.agent.Q.size());
            if (Q == 0.0) {
                currentQ.setText("Current Q: " + null);
                agentStatus.setText("Agent is exploring");
            } else {
                currentQ.setText("Current Q: " + Q);
                agentStatus.setText("Agent is learning");
            }
        }
    }

    public void update() {
        // Overloaded method for updating gui labels
        synchronized (ThreadSync.lock) {
            generationLabel.setText("Current walker is generation #" + Main.generation);
             // TODO FIX ME
        }
    }

    public void update(JointAction action) {
        // Overloaded method for updating gui with label for current action
        synchronized (ThreadSync.lock) {
            currentAction.setText("Action: " + action.toString());
        }
    }
}
