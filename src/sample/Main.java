package sample;

import QLearning.*;
import Rendering_dyn4j.Graphics2D;

import javax.swing.*;

public class
        Main {
    public static double gamma = 0.5; // Decay rate
    public static double learningRate = 0.8; // Learning rate
    public static int generation = 0;
    public static Graphics2D simulation;
    public static GUI gui;
    public static double bestReward;
    public static double accumulatedReward;
    public static double bestDistance;
    public static int bestGeneration;
    public static State initState;
    public static JointAction initAction;
    public static StateAnalyser analyser = new StateAnalyser();
    public static boolean startNow;
    public static int mode = 0;


    public static void main(String[] args) {

        ModeSelection modeSelection = new ModeSelection();
        modeSelection.askUser();

        JOptionPane start = new JOptionPane();
        start.createDialog("WHAT");
    }


    public static void run() {
        simulation = new Graphics2D();
        gui = new GUI(simulation);


        System.out.println("YES");

        //GUI


        System.out.println("NEW GUI MADE");

        //Add actions
        State.fillActions();

        //First action
        initAction = Graphics2D.walker.getState().getRandomAction();
        initAction.doAction();

        Agent agent = new Agent(mode);

        initState = Graphics2D.walker.getState();

        while (2 > 1) {
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
        gui.highScoreList.add(new Generation(generation, accumulatedReward, 0));
        generation++;
        gui.update();
        System.out.println("Generation " + generation + " Reward: " + accumulatedReward + " State no: " + analyser.states.size() + " Dist: " + Graphics2D.walker.torso.getWorldCenter().distance(0, 0) + " Best reward: " + bestReward + " Best distance: " + bestDistance + " Best generation: " + bestGeneration);
    }
}



