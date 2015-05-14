package sample;

import javax.swing.*;

import QLearning.StateAnalyser;
import Rendering_dyn4j.ExampleGraphics2D;
import aima.core.learning.framework.Example;

/**
 * Created by frederikjuutilainen on 05/05/15.
 */
public class GUI {

    private static double amount = 1;
    public static JLabel angleLabel = new JLabel();
    public static int simulationSpeed =1;
    

    public GUI(ExampleGraphics2D world)
    {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setSize(300, 300);

        // Add controls
        JButton leanRightButton = new JButton("Lean right");
        JButton leanLeftButton = new JButton("Left left");
        JSlider amountSlider = new JSlider(1, 360);

        // Panel for controls
        JPanel controls = new JPanel();

        leanRightButton.addActionListener(e -> {
//            world.walker.hip1Stretch();
//            world.walker.knee1Stretch();
//            world.walker.ankle1Stretch();
//            world.walker.hip2Bend();
//            world.walker.knee2Bend();
//            world.walker.ankle2Bend();
            world.walker.setJoint(world.walker.hip1,-1);
        });
        leanLeftButton.addActionListener(e -> {
//            world.walker.rotateLeftKnee(amount);
//            world.walker.hip1Bend();
//            world.walker.knee1Bend();
//            world.walker.ankle1Bend();
//            world.walker.hip2Stretch();
//            world.walker.knee2Stretch();
//            world.walker.ankle2Stretch();
            world.walker.setJoint(world.walker.hip1,1);
        });

        amountSlider.addChangeListener(e -> {
            simulationSpeed = amountSlider.getValue();
//            world.walker.test();
        });

        controls.add(leanLeftButton);
        controls.add(leanRightButton);
        controls.add(amountSlider);

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
