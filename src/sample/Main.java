package sample;

import QLearning.*;
import Rendering_dyn4j.Graphics2D;

public class
        Main {
    public static double gamma = 0.8; // Decay rate
    public static double learningRate = 0.8; // Learning rate
    public static int generation = 1;
    public static Graphics2D simulation = new Graphics2D();
    public static GUI gui = new GUI(simulation);
    public static double bestReward;
    public static double accumulatedReward;
    public static double bestDistance;
    public static int bestGeneration;
    public static State initState;
    public static boolean simulationRunning = true;
    public static JointAction initAction;
    public static StateAnalyser analyser = new StateAnalyser();

    public static void main(String[] args) {
        //Add actions
        State.fillActions();

        //First action
        initAction = Graphics2D.walker.getState().getRandomAction();
        initAction.doAction();

        Agent agent = new Agent(learningRate, gamma);

        initState = Graphics2D.walker.getState();

        while (simulationRunning) { // TODO whileRunning
            accumulatedReward = 0;
            double t = 0;
            boolean isTerminal = false;
            while (!isTerminal) {
                // Reset if out of sight
                if (!Graphics2D.walker.isInSight()) {
                    isTerminal = true;
                }
                if (t > 400000) {
                    // Observe and execute
                    JointAction action = agent.execute();
                    if (action != null) {
                        action.doAction();
                    } else {
                        // If null is returned, agent is at a terminal state
                        isTerminal = true;
                    }
                    t = 0; // Reset time to zero
                }
                t += simulation.getElapsedTime(); // Increment time
            }
            // When loop is breaked, information is printed and walker is reset to initial position
            if (isTerminal) {
                print();
                Graphics2D.walker.resetPosition();
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


