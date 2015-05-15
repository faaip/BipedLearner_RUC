package sample;

import QLearning.*;
import Rendering_dyn4j.Graphics2D;
import burlap.behavior.singleagent.Policy;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

public class
        Main {
    static StateAnalyser analyser;
    static Policy learningPolicy;
    public static double gamma = 0.7; // Decay rate
    public static double learningRate = 0.6; // Learning rate
    public static int generation = 1;
    public static GUI gui;
    public static Graphics2D world;
    public static CollisionListener cl;
    public static double bestReward;
    public static double bestDistance;
    public static int bestGeneration;

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
//        BiPedBody walker = ExampleGraphics2D.walker;

        //Add actions
        State.fillActions();

        while(2>1) {

            BiPedBody walker = Graphics2D.walker;
            double n = 0;
            double accumulatedReward = 0;
            while (!walker.hasFallen() && n < 1000000) {

                // Analyse state
                State currentState = analyser.getState(walker);

                // Get action
                JointAction action = currentState.getBestAction();
                System.out.println(action);

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

                currentState.printQs();

                accumulatedReward+=currentState.getReward();

                n++;

            }
//        world.newWalker();
            world.initializeWorld();
            System.out.println("Generation " + generation + " Reward: " + accumulatedReward + " State no: " + analyser.states.size() + " Dist: " + walker.torso.getWorldCenter().distance(0, 0) + " Best reward: " + bestReward + " Best distance: " + bestDistance + " Best generation: " + bestGeneration);

            if(accumulatedReward > bestReward){bestReward = accumulatedReward;}
            if(walker.torso.getWorldCenter().distance(0, 0) > bestDistance)
            {bestDistance = walker.torso.getWorldCenter().distance(0, 0);
            bestGeneration=generation;}
            generation++;

            gui.update();


        }
    }






}
