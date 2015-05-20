package sample;

import javax.swing.*;

import QLearning.JointAction;
import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;
import org.apache.log4j.Layout;
import org.dyn4j.geometry.Vector2;

import java.awt.*;


//TODO Comment and clean
public class GUI {


    public static int simulationSpeed = 1;
    static JLabel generationNo = new JLabel("Generation # " + 0);
    static JLabel highScore = new JLabel("Highscore: ");
    public static HighScoreList highScoreList = new HighScoreList();
    public static int modeSelected = 0;

    private static JLabel generationLabel;
    private static JLabel statesLabel;
    private static JLabel currentNsa;
    private static JLabel currentQ;
    private static JLabel agentStatus;
    private static JLabel currentAction;


    public GUI(Graphics2D world) {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(350, 500);
        gui.setLocation(820, 0);
        gui.setTitle("Controls");

        //Panel for current information
        JPanel currentBipedPanel = new JPanel();
        currentBipedPanel.setLayout(new GridLayout(0, 1));
        generationLabel = new JLabel("Current walker is generation #" + Main.generation);
        statesLabel = new JLabel("Number of states explored: " + Main.analyser.states.size());
        currentNsa = new JLabel("Current Nsa count: " + 0);
        currentQ = new JLabel("Number of states explored: " + 0);
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
        JButton resetButton = new JButton("Reset walker");
        control.setLayout(new GridLayout(0, 1));
        JToggleButton pauseButton = new JToggleButton("Pause");
        JLabel simSpeed = new JLabel(simulationSpeed + " x Speed", SwingConstants.CENTER);
        JSlider simSpeedSlider = new JSlider(1, 50, 1);

        pauseButton.addChangeListener(e1 -> {
            if (simulationSpeed != 0) {
                simulationSpeed = 0;
            } else {
                simulationSpeed = simSpeedSlider.getValue();
            }
        });

        resetButton.addActionListener(e1 -> {
            Graphics2D.walker.resetPosition();
            Main.generation++;
        });

        // Add Components to panel
        control.add(resetButton);
        control.add(pauseButton);
        control.add(simSpeed);
        control.add(simSpeedSlider);


        // Panels for high score
        JScrollPane highScorePane = new JScrollPane(highScoreList.jList);

        // Split panes
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, currentBipedPanel, control);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane1, highScorePane);


        // Panels are added
        //gui.add(currentBipedPanel);
//        gui.add(control);
//        gui.add(highScores);
        gui.add(splitPane2);
//        gui.add(highScores);
        gui.setVisible(true);


        simSpeedSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {
                simulationSpeed = simSpeedSlider.getValue();
                world.step((int) Math.floor(simSpeedSlider.getValue() / world.getStepFrequency()));
            }
            simSpeed.setText(simulationSpeed + " x Speed");
        });

        resetButton.addActionListener(e -> {
//            Graphics2D.walker.resetPosition();
//            Graphics2D.walker.torso.applyImpulse(-75);
//            Main.simulationRunning = false;
//            System.out.println(Main.simulationRunning);

        });


        // Panel for info monitoring
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 3));


    }

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void update(int Nsa, double Q) {
        synchronized (ThreadSync.lock) {
            currentNsa.setText("Current Nsa count: " + Nsa);
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
        synchronized (ThreadSync.lock) {
            generationLabel.setText("Current walker is generation #" + Main.generation);
            statesLabel.setText("Number of states explored: " + Main.analyser.states.size());

        }
    }


    public void update(JointAction action) {
        synchronized (ThreadSync.lock) {
            currentAction.setText("Action: " + action.toString());
        }
    }
}
