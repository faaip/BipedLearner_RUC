package sample;

import QLearning.*;
import Rendering_dyn4j.Simulation;
import Rendering_dyn4j.ThreadSync;

/*
This is the Main class for the BiPed learner.
 */

public class Main {
    public static int generation = 0;
    public static Simulation simulation;
    public static GUI gui;
    public static Agent agent;
    public static double accumulatedReward;
    public static State initState;
    public static JointAction initAction;
    public static int mode = 0;

    public static void main(String[] args) {
        StartDialog dialog = new StartDialog(); // Starting dialog window
        learn();
    }

    public static void learn() {
        // Setup
        simulation = new Simulation();
        gui = new GUI(simulation);

        State.fillActions(); //Add actions to static ArrayList in State

        //First action
        initAction = Simulation.walker.getState().getRandomAction();
        initAction.doAction();

        agent = new Agent(mode); // Agent is created based on chosen reward mode
        initState = Simulation.walker.getState();

        while (true) {// Loops as long as program is running
            accumulatedReward = 0;
            double t = 0;
            boolean isTerminal = false;

            while (!isTerminal) {
                if (!Simulation.walker.isInSight()) { // Reset if out of sight
                    isTerminal = true;
                }
                if (t > 400000) {
                    // Observe and execute
                    JointAction action = agent.execute();
                    if (action != null) {
                        synchronized (ThreadSync.lock) {
                            action.doAction();
                        }
                    } else {// If null is returned, agent is at a terminal state
                        isTerminal = true;
                    }
                    t = 0; // Reset time to zero
                }
                t += simulation.getElapsedTime(); // Increment time
            }
            // When loop is breaked, gui is updated and walker is reset to initial position
            if (isTerminal) {
                updateGuiTable();
                Simulation.walker.resetPosition();
            }
        }
    }

    private static void updateGuiTable() {
        synchronized (ThreadSync.lock) {
            gui.highScoreTable.add(new Generation(generation, accumulatedReward));
            generation++;
            gui.update();
        }
    }
}



