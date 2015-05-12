package sample;

import MDP.*;
import Rendering_dyn4j.ExampleGraphics2D;
import aima.core.learning.framework.Example;
import aima.core.learning.reinforcement.agent.QLearningAgent;
import aima.core.probability.mdp.ActionsFunction;
import org.dyn4j.collision.Collisions;
import org.dyn4j.dynamics.CollisionAdapter;
import org.dyn4j.dynamics.CollisionListener;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

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

        // Test
        HashSet<State> states = new HashSet<>();

        // while time left || !hasfallen
        // state analysis (have I seen this state before)
            // if not add to hashSet and get random action
            // if yes, find optimal policy (get action)
        // take action and observe outcome
            // record result
            // update old q-value
        // move on





    }
}
