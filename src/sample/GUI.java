package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;

import java.awt.*;


//TODO Comment and clean
public class GUI {

    private static double amount = 1;
    public static JLabel angleLabel = new JLabel();
    public static int simulationSpeed =1;
    static JLabel generationNo = new JLabel("Generation # " + 0);
    static JLabel highScore = new JLabel("Highscore: ");
    public static double learningRate = 100;


    public GUI(Graphics2D world)
    {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(300, 300);
        gui.setTitle("Controls");




        // Add controls
//        JButton skip1 = new JButton("Skip 1 minute");
//        JButton skip10 = new JButton("Skip 10 minutes");
        JButton reset = new JButton("Reset walker");
        JSlider amountSlider = new JSlider(1, 25,simulationSpeed);
        JLabel simSpeed = new JLabel(simulationSpeed+" x Speed" );
        JSlider learningRateSlider = new JSlider(0, 10, 10); //double to int
        JLabel learnRate = new JLabel(learningRate+ " x Learning Rate");

        // Panel for controls
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        controls.setBackground(Color.white);

        learningRateSlider.addChangeListener(e1 -> {
            synchronized (ThreadSync.lock) {
//                learningRate = learningRateSlider.getValue();

            }
            learnRate.setText(learningRate + " x Learning Rate");

        });

        amountSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {

                // TODO pause simulation while slider is adjusted
                simulationSpeed = amountSlider.getValue();
                world.step((int) Math.floor(amountSlider.getValue() / world.getStepFrequency()));
            }
            simSpeed.setText(simulationSpeed + " x Speed");
        });

        reset.addActionListener(e -> {
            Graphics2D.walker.resetPosition();
        });

        controls.add(reset);
        controls.add(amountSlider);
        controls.add(simSpeed);
        controls.add(learningRateSlider);
        controls.add(learnRate);

        // Panel for info monitoring
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.PAGE_AXIS));
        info.setBackground(Color.yellow);


        info.add(generationNo);
        info.add(highScore);

        // Panels are added to splitpane
        JSplitPane split = new JSplitPane();
        split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        split.setLeftComponent(info);
        split.setRightComponent(controls);
        gui.add(split);
        gui.setVisible(true);
    }
    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void update() {
        generationNo.setText("Generation #" + Main.generation);
        highScore.setText("Highscore was gen. #" + Main.bestGeneration + " reward: ");
    }


}
