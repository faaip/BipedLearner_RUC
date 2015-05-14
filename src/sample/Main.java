package sample;

import QLearning.*;
import Rendering_dyn4j.Graphics2D;
import burlap.behavior.singleagent.Policy;

public class
        Main {
    static StateAnalyser analyser;
    static Policy learningPolicy;
    public static double gamma = 0.2; // Decay rate
    public static double learningRate = 0.2; // Learning rate
    public static GUI gui;
    public static Graphics2D world;

    public static void main(String[] args) {

        world = new Graphics2D();
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
            BiPedBody walker = Graphics2D.walker;
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
//        world.newWalker();
            world.initializeWorld();
        }
    }


}
