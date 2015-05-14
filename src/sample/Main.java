package sample;

import QLearning.*;
import Rendering_dyn4j.ExampleGraphics2D;
import aima.core.learning.framework.Example;
import burlap.behavior.singleagent.Policy;
import burlap.behavior.singleagent.QValue;
import burlap.behavior.statehashing.StateHashTuple;
import burlap.oomdp.core.AbstractGroundedAction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class
        Main {
    static StateAnalyser analyser;
    static Policy learningPolicy;
    public static double gamma = 0.2; // Decay rate
    public static double learningRate = 0.2; // Learning rate

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

        analyser = new StateAnalyser();
        BiPedBody walker = ExampleGraphics2D.walker;


//        System.out.println(State.actions.size());

//        for (JointAction a : State.actions) {
//            a.doAction();
//        }

        double t = System.currentTimeMillis();
        double x = walker.torso.getWorldCenter().x;


        while (!walker.hasFallen() && (System.currentTimeMillis() < t + 15000)) {
//            currentState.getAction();

            // Analyse state
            State currentState = analyser.getState(walker);
            // Get action
            JointAction action = currentState.getBestAction();
            // Do action
            action.doAction();
            // Record outcome
            State nextState = analyser.getState(walker);
            // Update Q-Value
            currentState.updateQ(currentState,action,nextState);

        }

//        System.exit(0);


    }
}
