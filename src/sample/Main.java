package sample;

import QLearning.*;
import QLearning.ActionsFunction;
import Rendering_dyn4j.Graphics2D;
import burlap.behavior.singleagent.Policy;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.dyn4j.dynamics.CollisionListener;
import scpsolver.graph.Graph;

public class
        Main {
    public static StateAnalyser analyser;
    static Policy learningPolicy;
    public static double gamma = 0.998; // Decay rate
    public static double learningRate = 0.4; // Learning rate
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


        //Add actions
        State.fillActions();

        JointAction noOp = new JointAction(false);

        actionsFunction = new ActionsFunction();


        initAction = Graphics2D.walker.getState().getRandomAction();
//        initAction.doAction();

        Agent agent = new Agent(learningRate, gamma);

        initState = Graphics2D.walker.getState();
//        initState.doRandomAction();


        while (2 > 1) {

            boolean hasFallen = Graphics2D.walker.hasFallen();

//            System.out.println(Graphics2D.world.getAccumulatedTime());

            double n = 0;
            accumulatedReward = 0;
            while (!Graphics2D.walker.hasFallen() || !Graphics2D.walker.isInSight()) {
//                System.out.println("Has fallen " + hasFallen);

                JointAction action = agent.execute();
                action.doAction();
//                Graphics2D.walker.getState().doRandomAction();


                n++;

                hasFallen = Graphics2D.walker.hasFallen();
            }

//            Graphics2D.world.removeBody(Graphics2D.walker);
            Graphics2D.walker.delete();
//            System.out.println("Angular velocity: " + Graphics2D.walker.torso.getAngularVelocity());



            System.out.println("Generation " + generation + " Reward: " + accumulatedReward + " State no: " + analyser.states.size() + " Dist: " + Graphics2D.walker.torso.getWorldCenter().distance(0, 0) + " Best reward: " + bestReward + " Best distance: " + bestDistance + " Best generation: " + bestGeneration);
            if (accumulatedReward > bestReward) {
                bestReward = accumulatedReward;
                bestGeneration = generation;
            }
            if (Graphics2D.walker.torso.getWorldCenter().distance(0, 0) > bestDistance) {
                bestDistance = Graphics2D.walker.torso.getWorldCenter().distance(0, 0);
                bestGeneration = generation;
            }
            generation++;

            gui.update();


        }
    }


}
