package sample;

import QLearning.*;
import QLearning.ActionsFunction;
import Rendering_dyn4j.Graphics2D;
import burlap.behavior.singleagent.Policy;

import org.dyn4j.dynamics.CollisionListener;

public class
        Main {
    public static StateAnalyser analyser;
    public static double gamma = 0.995; // Decay rate
    public static double learningRate = 1; // Learning rate
    public static int generation = 1;
    public static GUI gui;
    public static Graphics2D world;
    public static CollisionListener cl;
    public static double bestReward;
    public static double accumulatedReward;
    public static double bestDistance;
    public static int bestGeneration;
    public static State initState;
    public static boolean simulationRunning = true;
    public static ActionsFunction actionsFunction;
    public static JointAction initAction;

    public static void main(String[] args) {
//TODO rename this world
        world = new Graphics2D();
        world.setTitle("Machine Learning");

        // show it
        world.setVisible(true);

        // start it
        world.start();

        // Controls
        gui = new GUI(world);

        //TODO Make it static
        analyser = new StateAnalyser();

        //Add actions
        State.fillActions();

        //TODO delete and move method
        actionsFunction = new ActionsFunction();

        //First action
        initAction = Graphics2D.walker.getState().getRandomAction();
        initAction.doAction();

        Agent agent = new Agent(learningRate, gamma);

        initState = Graphics2D.walker.getState();

        while (2 > 1) { // TODO whileRunning

            accumulatedReward = 0;
            double t = 0;
            boolean isTerminal = false;
            while (!isTerminal /* !Graphics2D.walker.hasFallen() || Graphics2D.walker.isInSight()*/) { //TODO isInSight - not working now
                if (t > 400000) {
                    // Observe and execute
                    JointAction action = agent.execute();
                    if (action != null) {
                        action.doAction();
                    } else {
                        Graphics2D.walker.resetPosition();
                        print();
                        isTerminal = true;
                    }

                    t = 0;
                }
                t += world.getElapsedTime();

            }
        }
    }

    private static void print() {
        generation++;
        if (accumulatedReward > bestReward) {
            bestReward = accumulatedReward;
            bestGeneration = generation;
        }
        gui.update();
        System.out.println("Generation " + generation + " Reward: " + accumulatedReward + " State no: " + analyser.states.size() + " Dist: " + Graphics2D.walker.torso.getWorldCenter().distance(0, 0) + " Best reward: " + bestReward + " Best distance: " + bestDistance + " Best generation: " + bestGeneration);

    }


}


