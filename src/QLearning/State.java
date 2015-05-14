package QLearning;

import Rendering_dyn4j.ExampleGraphics2D;
import aima.core.util.datastructure.Pair;
import org.dyn4j.dynamics.joint.Joint;
import sample.BiPedBody;
import sample.Main;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;


public class State extends burlap.oomdp.core.State {

    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;
    private boolean upperLeg1InFrontOfTorso;
    private boolean upperLeg2InFrontOfTorso;

    // -
    boolean exitState = false;
    public static ArrayList<JointAction> actions = new ArrayList<JointAction>();
    public Map<JointAction, Double> q = new HashMap<JointAction, Double>();


    // Constructor in case of new state
    public State(BiPedBody walker) {
        this.torsoLeaningForward = walker.isTorsoLeaningForward();
        this.knee1Forward = walker.isKnee1Forward();
        this.foot1Forward = walker.isFoot1Forward();
        this.foot1OnGround = walker.isFoot1OnGround();
        this.foot2OnGround = walker.isFoot2OnGround();
        this.upperLeg1InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();
        this.upperLeg2InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();

        // Fill q with zero's inorder to avoid null point
        fillZero();
    }

    private void fillZero() {

        for (JointAction a : actions) {
            q.put(a, Double.valueOf(0.0));
        }
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {

        return booleanToInt(torsoLeaningForward) + "" + booleanToInt(knee1Forward) + "" + booleanToInt(foot1Forward) + "" + booleanToInt(foot1OnGround) + "" + booleanToInt(foot2OnGround) + "" + booleanToInt(upperLeg1InFrontOfTorso) + "" + booleanToInt(upperLeg2InFrontOfTorso) + "";

    }

    @Override
    public boolean equals(Object o) {

        return o.toString().equals(this.toString());

    }

    public int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public JointAction getBestAction() {
        double max = 0;
        JointAction action = null;

        for (JointAction a : actions) {
            if (q.get(a) > max) {
                max = q.get(a);
                action = a;
                System.out.println(q.get(a));
                ;
            }
        }

        if(action == null)
        {
            System.out.println("Q-value was null");
            return actions.get((int) (Math.random()*actions.size()));
        }

        return action;
    }

    public void updateQ(State oldState, JointAction action, State nextState) {
        double oldQ = oldState.q.get(action);
        double gamma = Main.gamma;
        double learningRate = Main.learningRate;
        double reward = ExampleGraphics2D.walker.torsoChangeSinceLastFrame() * 100;

        double newQ = oldQ + learningRate * (reward+gamma*nextState.q.get(nextState.getBestAction())-oldQ);


        oldState.q.put(action, newQ);


    }

    public void doRandomAction() {

        actions.get((int) (Math.random()*actions.size())).doAction();
        System.out.println("random action");

    }
}
