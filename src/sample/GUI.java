package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;
import org.dyn4j.geometry.Vector2;

import java.awt.*;


//TODO Comment and clean
public class GUI {


    public static int simulationSpeed = 1;
    static JLabel generationNo = new JLabel("Generation # " + 0);
    static JLabel highScore = new JLabel("Highscore: ");


    public GUI(Graphics2D world) {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(350, 1000);
        gui.setTitle("Controls");

        // Add controls
//        JButton skip1 = new JButton("Skip 1 minute");
//        JButton skip10 = new JButton("Skip 10 minutes");
        JButton reset = new JButton("Reset walker");
        JSlider amountSlider = new JSlider(1, 25, simulationSpeed);
        JLabel simSpeed = new JLabel(simulationSpeed + " x Speed");


        // Panel for controls
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        controls.setBackground(Color.white);


        amountSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {

                // TODO pause simulation while slider is adjusted
                simulationSpeed = amountSlider.getValue();
                world.step((int) Math.floor(amountSlider.getValue() / world.getStepFrequency()));
            }
            simSpeed.setText(simulationSpeed + " x Speed");
        });

        reset.addActionListener(e -> {
//            Graphics2D.walker.resetPosition();
//            Graphics2D.walker.torso.applyImpulse(-75);
            Main.simulationRunning = false;
            System.out.println(Main.simulationRunning);
        });

        controls.add(reset);
        controls.add(amountSlider);
        controls.add(simSpeed);


        // Panel for info monitoring
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 3));
        info.setBackground(Color.yellow);

        HighScoreList highScoreList = new HighScoreList();
        //todo add labels



        info.add(highScoreList.jList);

//        info.add(generationNo);
//        info.add(highScore);
        // info.add(exploringLabel);
        // info.add(actionLabel);
        // info.add(NsaLabel);
        // info.add(currentStateLabel);
        // info.add(currentGenLabel);

        // Panels are added to splitpane
        JSplitPane split = new JSplitPane();
        split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        split.setLeftComponent(info);
        split.setRightComponent(controls);
        gui.add(split);
        gui.setVisible(true);


        // Panel for graphs
        //   JPanel graphs = new JPanel();
        //  graphs.setLayout(new GridBagLayout());
        //   graphs.setBackground(Color.BLACK);

    }

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void update() {
        generationNo.setText("Generation #" + Main.generation);
        highScore.setText("Highscore was gen. #" + Main.bestGeneration + " reward: ");
    }


}
