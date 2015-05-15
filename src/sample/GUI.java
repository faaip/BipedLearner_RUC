package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;

import java.awt.*;

/**
 * Created by frederikjuutilainen on 05/05/15.
 */
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
        JSlider amountSlider = new JSlider(1, 50,1);
        JLabel simSpeed = new JLabel(simulationSpeed+" x Speed" );
        JSlider learningRateSlider = new JSlider(0, 10, 10); //double to int
        JLabel learnRate = new JLabel(learningRate+ " x Learning Rate");

        // Panel for controls
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        controls.setBackground(Color.white);


//        skip1.addActionListener(e -> {
//            synchronized (ThreadSync.lock) {
//                world.step((int) Math.floor(60 * 1 / world.getStepFrequency()));
//            }
//        });
//        skip10.addActionListener(e -> {
//            synchronized (ThreadSync.lock) {
//                world.step((int) Math.floor(60 * 60 / world.getStepFrequency()));
//            }
//        });


        learningRateSlider.addChangeListener(e1 -> {
            synchronized (ThreadSync.lock) {
                learningRate = learningRateSlider.getValue();

            }
            learnRate.setText(learningRate + " x Learning Rate");

        });

        amountSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {
                simulationSpeed = amountSlider.getValue();
                world.step((int) Math.floor(amountSlider.getValue() / world.getStepFrequency()));
            }
            simSpeed.setText(simulationSpeed + " x Speed");
//            System.out.println(simulationSpeed + " x Speed");
        });

//        controls.add(skip10);
//        controls.add(skip1);
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

//        angleLabel.setText("Angle is: " + world.balancer.getAngle());

//        info.add(angleLabel);

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
        highScore.setText("Highscore was gen. #" + Main.bestGeneration + " dist: " + Math.round(Main.bestDistance*100));
    }


}
