package sample;

import Rendering_dyn4j.ExampleGraphics2D;

import javax.swing.*;

/**
 * Created by frederikjuutilainen on 29/04/15.
 */
public class Main {

    private static double amount = 1;

    public static void main(String[] args) {

        ExampleGraphics2D world = new ExampleGraphics2D();
        world.setTitle("Machine Learning");

        // show it
        world.setVisible(true);

        // start it
        world.start();

        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(300,300);

        // Add controls
        JButton leanRightButton = new JButton("Lean right");
        JButton leanLeftButton = new JButton("Left left");
        JSlider amountSlider = new JSlider(1,10);

        // Panel for controls
        JPanel controls = new JPanel();

        leanRightButton.addActionListener(e -> {
            world.bodyList.get(0).lean(-amount);
        });
        leanLeftButton.addActionListener(e -> {
            world.bodyList.get(0).lean(amount);
        });

        amountSlider.addChangeListener(e -> {
            amount = amountSlider.getValue();
        });

        controls.add(leanLeftButton);
        controls.add(leanRightButton);
        controls.add(amountSlider);

        // Panel for info monitoring
        JPanel info = new JPanel();
        JLabel angleLabel = new JLabel();
        angleLabel.setText("Angle is: " + world.bodyList.get(0).getAngle());

        info.add(angleLabel);

        // Panels are added to splitpane
        JSplitPane split = new JSplitPane();
        split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        split.setLeftComponent(info);
        split.setRightComponent(controls);
        gui.add(split);
    }
}
