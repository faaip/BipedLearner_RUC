package sample;

import javax.swing.*;
import Rendering_dyn4j.ExampleGraphics2D;

/**
 * Created by frederikjuutilainen on 05/05/15.
 */
public class GUI {

    private static double amount = 1;
    public static JLabel angleLabel = new JLabel();

    public GUI(ExampleGraphics2D world)
    {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(300, 300);

        // Add controls
        JButton leanRightButton = new JButton("Lean right");
        JButton leanLeftButton = new JButton("Left left");
        JSlider amountSlider = new JSlider(1, 360);

        // Panel for controls
        JPanel controls = new JPanel();

        leanRightButton.addActionListener(e -> {
            world.walker.hip1Stretch();
            world.walker.knee1Stretch();
            world.walker.ankle1Stretch();
            world.walker.hip2Bend();
            world.walker.knee2Bend();
            world.walker.ankle2Bend();
        });
        leanLeftButton.addActionListener(e -> {
//            world.walker.rotateLeftKnee(amount);
            world.walker.hip1Bend();
            world.walker.knee1Bend();
            world.walker.ankle1Bend();
            world.walker.hip2Stretch();
            world.walker.knee2Stretch();
            world.walker.ankle2Stretch();
        });

        amountSlider.addChangeListener(e -> {
            amount = amountSlider.getValue();
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
    }

}
