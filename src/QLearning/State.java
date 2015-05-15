package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.agent.Action;
import aima.core.search.framework.*;
import aima.core.search.framework.ActionsFunction;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import sample.BiPedBody;
import sample.Main;

import java.util.*;

public class State {

    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;
    private boolean upperLeg1InFrontOfTorso;
    private boolean upperLeg2InFrontOfTorso;

    private double relativeAngle;

    ArrayList<Double> jointAngles = new ArrayList<>();

    // -
    boolean exitState = false;

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    public static ArrayList<JointAction> actions = new ArrayList<>();

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
        this.relativeAngle = walker.getRelativeAngle();

        for(RevoluteJoint j : walker.joints)
        {
        jointAngles.add(j.getJointAngle());
        }


        // Fill q with zero's inorder to avoid null point
        fillZero();

    }

    public static void fillActions() {

        // Create actions
        if(actions.size() < 1) {
            for (RevoluteJoint joint : BiPedBody.joints) {
                actions.add(new JointAction(joint));
                for (int i = -1; i <= 1; i++) {
                    actions.add(new JointAction(joint, i));
                }

            }
            System.out.println(actions);

        }
    }

    private void fillZero() {
        for (JointAction a : actions) {
//            q.put(a, Double.valueOf(Math.random()));
            q.put(a,0.0);
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {

        String s = "";
        String fullS;

        for(double angle : jointAngles)
        {
//            s = s+""+(int) Math.floor(Math.toDegrees(angle));
            s = s+5*(Math.round(Math.toDegrees(angle)/25));
//            System.out.println(s);
        }

//        System.out.println(s);
        return s;


//        return booleanToInt(torsoLeaningForward) + "" + booleanToInt(knee1Forward) + "" + booleanToInt(foot1Forward) + "" + booleanToInt(foot1OnGround) + "" + booleanToInt(foot2OnGround) + "" + booleanToInt(upperLeg1InFrontOfTorso) + "" + booleanToInt(upperLeg2InFrontOfTorso) + "";

    }

    @Override
    public boolean equals(Object o) {
        State s = (State) o;
        int round = 25;

        if(Math.round(Math.toDegrees(this.relativeAngle)/round) != Math.round(Math.toDegrees(s.relativeAngle)/round))
        {return false;}


        if(s.foot1OnGround != this.foot1OnGround){return false;}
        if(s.foot2OnGround != this.foot2OnGround){return false;}
        if(s.torsoLeaningForward != this.torsoLeaningForward){return false;}
        if(s.knee1Forward != this.knee1Forward){return false;}
        if(s.upperLeg2InFrontOfTorso != this.upperLeg2InFrontOfTorso){return false;}
        if(s.upperLeg1InFrontOfTorso != this.upperLeg1InFrontOfTorso){return false;}

        for (int i = 0; i < this.jointAngles.size(); i++) {
            if(Math.round(Math.toDegrees(s.jointAngles.get(i)/round)) != Math.round(Math.toDegrees(s.jointAngles.get(i)/round)))
            {return false;}
        }




        return true;

//        return o.toString().equals(this.toString());

    }

    public int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public JointAction getBestAction() {
        double max = -100;
        JointAction action = null;


        for (JointAction a : actions) {
            if (q.get(a) > max) {
                max = q.get(a);
                action = a;

            }
        }

        if(max == 0)
        {

//            return actions.get((int) ((Math.random()*actions.size())));
        }

        return action;
    }

    public void updateQ(State oldState, JointAction action, State nextState) {
        double oldQ = oldState.q.get(action);
        double gamma = Main.gamma;
        double learningRate = Main.learningRate;
        double reward = -0.1 + (Graphics2D.walker.reward());


        double newQ = oldQ + learningRate * (reward+gamma*nextState.q.get(nextState.getBestAction())-oldQ);

        oldState.q.put(action, newQ);




    }

    public void doRandomAction() {
//        actions.get((int) (Math.random()*actions.size())).doAction();
    }

    public double getReward() {

        return -0.1 + (Graphics2D.walker.legsChangeSinceLastFrame());
    }


    public void printQs (){

        for (JointAction a : actions) {

            if(!(q.get(a)== 0.0)){

              //  System.out.println(q.size());

                //System.out.println("q value not zero");


            }

        }

    }


    public JointAction getRandomAction() {




                return actions.get((int) (Math.random()*actions.size()));
    }
}
