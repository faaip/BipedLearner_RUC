package sample;

import QLearning.*;
import Rendering_dyn4j.Simulation;
import Rendering_dyn4j.ThreadSync;
import org.omg.CORBA.DomainManagerOperations;

import java.io.IOException;

public class Main {
    public static int generation = 0;
    public static Simulation simulation;
    public static GUI gui;
    public static double accumulatedReward;
    public static State initState;
    public static JointAction initAction;
    public static int mode = 0;
    public static int noOfStatesExplored;
    public static Agent agent;
    public static OutputDataWriter fileWriter;
    public static long runTime = 0;


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
        simulation = new Simulation();
        gui = new GUI(simulation);

        //Add actions
        State.fillActions();

        //First action
        initAction = Simulation.walker.getState().getRandomAction();
        initAction.doAction();

        agent = new Agent(mode); // Agent is created based on chosen reward mode
        initState = Simulation.walker.getState();

        fileWriter = new OutputDataWriter("TEST");
        int actionCounter = 0;

        while (2 > 1) {

            accumulatedReward = 0;
            noOfStatesExplored = 0;

            double t = 0;
            boolean isTerminal = false;
            while (!isTerminal) {
                // Reset if out of sight
                if (!Simulation.walker.isInSight()) {
                    isTerminal = true;
                }
                if (t > 400000) {
                    // Observe and execute
                    JointAction action = agent.execute();
                    if (Main.mode != 0) {
                        fileWriter.add(new CsvData(actionCounter, Simulation.walker.reward()));
                    }
                    if (action != null) {
                        synchronized (ThreadSync.lock) {
                            action.doAction();
                        }
                    } else {
                        // If null is returned, agent is at a terminal state
                        isTerminal = true;
                    }
                    t = 0; // Reset time to zero
                    System.out.println(actionCounter);
                    actionCounter++;
                }
                t += simulation.getElapsedTime(); // Increment time

                if (actionCounter >= 50000) {
                    // Make csv
                        try {
                            Main.fileWriter.createFile();
                            System.out.println("csv created");
                        } catch (IOException e) {
                            System.out.println(e + " CSV FAILED");
                        }
                        System.exit(0);
                }

//                runTime += simulation.getElapsedTime(); // Increment runtime

            }
            // When loop is breaked, information is printed and walker is reset to initial position
            if (isTerminal) {
                if (Main.mode == 0) {
                    fileWriter.add(new CsvData(generation, accumulatedReward)); // Add to filewriter
                }
                updateGuiTable();
                Simulation.walker.resetPosition();
            }
        }


    }

    private static void updateGuiTable() {
        synchronized (ThreadSync.lock) {
            gui.highScoreList.add(new Generation(generation, accumulatedReward, noOfStatesExplored)); // TODO fix number of states explored
            generation++;
            gui.update();
        }
    }
}



