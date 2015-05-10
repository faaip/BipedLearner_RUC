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
        JSlider amountSlider = new JSlider(1, 10);

        // Panel for controls
        JPanel controls = new JPanel();

        leanRightButton.addActionListener(e -> {
        });
        leanLeftButton.addActionListener(e -> {
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
