package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;

/**
 * Created by frederikjuutilainen on 05/05/15.
 */
public class GUI {

    private static double amount = 1;
    public static JLabel angleLabel = new JLabel();
    public static int simulationSpeed =1;
    

    public GUI(Graphics2D world)
    {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(300, 300);

        // Add controls
        JButton skip1 = new JButton("Skip 1 minute");
        JButton skip10 = new JButton("Skip 10 minutes");
        JButton reset = new JButton("Reset walker");
        JSlider amountSlider = new JSlider(1, 50,1);
        JLabel simSpeed = new JLabel(simulationSpeed+" x Speed");

        // Panel for controls
        JPanel controls = new JPanel();

        skip1.addActionListener(e -> {
            synchronized (ThreadSync.lock) {
                world.step((int) Math.floor(60 * 1 / world.getStepFrequency()));
            }
        });
        skip10.addActionListener(e -> {
            synchronized (ThreadSync.lock) {
                world.step((int) Math.floor(60 * 60 / world.getStepFrequency()));
            }
        });


        amountSlider.addChangeListener(e -> {
            synchronized (ThreadSync.lock) {
                simulationSpeed = amountSlider.getValue();
            }
            simSpeed.setText(simulationSpeed + " x Speed");
//            System.out.println(simulationSpeed + " x Speed");
        });

        controls.add(skip10);
        controls.add(skip1);
        controls.add(reset);
        controls.add(amountSlider);
        controls.add(simSpeed);

        // Panel for info monitoring
        JPanel info = new JPanel();

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
}
