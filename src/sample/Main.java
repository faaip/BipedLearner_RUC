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
    public static GUI gui;
    public static ExampleGraphics2D world;

    public static void main(String[] args) {

        world = new ExampleGraphics2D();
        world.setTitle("Machine Learning");

        // show it
        world.setVisible(true);

        // start it
        world.start();

        // Controls
        gui = new GUI(world);

        // while time left || !hasfallen
        // state analysis (have I seen this state before)
        // if not add to hashSet and get random action
        // if yes, find optimal policy (get action)
        // take action and observe outcome
        // record result
        // update old q-value
        // move on

        analyser = new StateAnalyser();
        System.out.println("this happened");
//        BiPedBody walker = ExampleGraphics2D.walker;


        System.out.println(State.actions.size());



        while(2>1) {
            BiPedBody walker = ExampleGraphics2D.walker;
            System.out.println("what 2000");

            while (!walker.hasFallen()) {
//            currentState.getAction();

                // Analyse state
                State currentState = analyser.getState(walker);
                // Get action
                JointAction action = currentState.getBestAction();
                // Do action - or 80 % of the time
                double r = Math.random();
                if (r < 0.8) {
                    action.doAction();
                } else {
                    currentState.doRandomAction();
                }
                // Record outcome
                State nextState = analyser.getState(walker);
                // Update Q-Value
                currentState.updateQ(currentState, action, nextState);
//            System.out.println(currentState.q);

            }
            System.out.println("what");
//        world.newWalker();
            world.initializeWorld();
        }
    }


}
