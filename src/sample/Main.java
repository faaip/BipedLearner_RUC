package sample;

import MDP.State;
import Rendering_dyn4j.ExampleGraphics2D;

import javax.swing.*;

public class
        Main {

    public static void main(String[] args) {

        ExampleGraphics2D world = new ExampleGraphics2D();
        world.setTitle("Machine Learning");

        // show it
        world.setVisible(true);

        // start it
        world.start();

        // Controls
        GUI gui = new GUI(world);


    }
}
