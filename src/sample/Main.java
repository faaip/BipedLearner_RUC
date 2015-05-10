package sample;

import MDP.State;
import Rendering_dyn4j.ExampleGraphics2D;

import javax.swing.*;
import java.util.HashSet;

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
//        GUI gui = new GUI(world);

        // Test
        HashSet<State> states = new HashSet<>();

//        while (!world.balancer.hasFallen()) {
//            gui.angleLabel.setText("Current angle: " + world.balancer.getAngle()+" °");
//            if (states.contains(world.balancer.getCurrentState()))
//            {
//            }else
//            {
//                System.out.println("This state is new" + world.balancer.getCurrentState());
//                states.add(world.balancer.getCurrentState());
//            }
//        }

    }
}
