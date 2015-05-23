package sample;

import QLearning.*;
import Rendering_dyn4j.Graphics2D;
import Rendering_dyn4j.ThreadSync;

public class Main {
    public static int generation = 0;
    public static Graphics2D simulation;
    public static GUI gui;
    public static double accumulatedReward;
    public static State initState;
    public static JointAction initAction;
    public static int mode = 0;
    public static int noOfStatesExplored;
    public static Agent agent;
    public static OutputDataWriter fileWriter;

    // TODO Måske skal actions kun incrementere grader med ex. 5 fremfor full action
    // TODO Alle actions skal ikke være tilgængelige i alle states, hvis angle er tæt på max == action not available - sænker kompleksiteten
    // TODO Vi kan overveje om randomaction ved inactivity skal ske automatisk fremfor "forced" af gui
    // TODO Vi skal undersøge betydelsen af terminal state


    public static void main(String[] args) {


        try {
            ModeSelection modeSelection = new ModeSelection();
            modeSelection.askUser();
        } catch (java.lang.NullPointerException e) {
            // Program exit if mode == null
            System.exit(0);
        }
        learn();

    }

    public static void learn() {
        fileWriter = new OutputDataWriter();
        simulation = new Graphics2D();
        gui = new GUI(simulation);
        System.out.println("TIME :"+ simulation.getElapsedTime());

        //Add actions
        State.fillActions();

        //First action
        initAction = Graphics2D.walker.getState().getRandomAction();
        initAction.doAction();

        agent = new Agent(mode); // Agent is created based on chosen reward mode
        initState = Graphics2D.walker.getState();



        while (2 > 1) {

            accumulatedReward = 0;
            noOfStatesExplored = 0;

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
                        synchronized (ThreadSync.lock) {
                            action.doAction();
                        }
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
                updateGuiTable();
                Graphics2D.walker.resetPosition();
            }
        }
    }

    private static void updateGuiTable() {
        synchronized (ThreadSync.lock) {
            gui.highScoreList.add(new Generation(generation, accumulatedReward, noOfStatesExplored)); // TODO fix number of states explored

            fileWriter.add(new CsvData(generation,accumulatedReward)); // Add to filewriter

            generation++;
            gui.update();
        }
    }
}



