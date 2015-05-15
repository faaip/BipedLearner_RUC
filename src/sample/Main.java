package sample;

import QLearning.*;
import QLearning.ActionsFunction;
import Rendering_dyn4j.Graphics2D;
import aima.core.agent.Action;
import aima.core.learning.reinforcement.PerceptStateReward;
import aima.core.learning.reinforcement.agent.QLearningAgent;
import aima.core.search.framework.*;
import burlap.behavior.singleagent.Policy;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

import java.util.Set;

public class
        Main {
    static StateAnalyser analyser;
    static Policy learningPolicy;
    public static double gamma = 0.5; // Decay rate
    public static double learningRate = 0.2; // Learning rate
    private static int ne = 4;
    public static int generation = 1;
    public static GUI gui;
    public static Graphics2D world;
    public static CollisionListener cl;
    public static double bestReward;
    public static double bestDistance;
    public static int bestGeneration;
    public static State initState;
    public static boolean simulationRunning = true;
    public static ActionsFunction actionsFunction;

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
        BiPedBody walker = Graphics2D.walker;

        //Add actions
        State.fillActions();

        JointAction noOp = new JointAction(false);

        actionsFunction = new ActionsFunction();

        Agent agent = new Agent(learningRate,gamma);



        initState = walker.getState();



        while(2>1) {


            double n = 0;
            double accumulatedReward = 0;
            while (!Graphics2D.walker.hasFallen() && n < 1000000) {

                JointAction action = agent.execute(walker);
                action.doAction();
                n++;

//
            }
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
