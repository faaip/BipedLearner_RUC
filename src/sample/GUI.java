package sample;

import javax.swing.*;

import Rendering_dyn4j.Graphics2D;

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
        JButton leanRightButton = new JButton("Lean right");
        JButton leanLeftButton = new JButton("Left left");
        JSlider amountSlider = new JSlider(1, 20,1);
        JLabel simSpeed = new JLabel(simulationSpeed+" x Speed");

        // Panel for controls
        JPanel controls = new JPanel();

        leanRightButton.addActionListener(e -> {
            world.walker.setJoint(world.walker.hip1, -1);
        });
        leanLeftButton.addActionListener(e -> {;
            world.walker.setJoint(world.walker.hip1,1);
        });

        amountSlider.addChangeListener(e -> {
            simulationSpeed = amountSlider.getValue();
            simSpeed.setText(simulationSpeed + " x Speed");
//            System.out.println(simulationSpeed + " x Speed");
        });

        controls.add(leanLeftButton);
        controls.add(leanRightButton);
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
