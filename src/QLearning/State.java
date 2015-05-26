package QLearning;

import Rendering_dyn4j.Simulation;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import Rendering_dyn4j.BiPedBody;

import java.util.*;

/*
This class is for states. It takes BiPedBody as an input and creates an approximate state from the values of the BiPedBody joints.
These variables are then rounded in order to create an approximate state.
 */

public class State {
    // State features
    private int worldAngle; // Torso angle relative to World
    private ArrayList<Integer> jointAngles = new ArrayList<>(); // Angles of joints
    private static ArrayList<JointAction> actions = new ArrayList<>(); // List containing possible actions. Static since actions are the same for all states
    public static int roundFactor = 15; // Round factor - the higher the number the lower precision in states // TODO WHAT VALUE!!

    public State(BiPedBody walker) {
        // Constructor for a new state with the walker as input.
        // The jointangles from the walker are rounded and then added to the state in order to make the state approximate of the "actual" state
        for (RevoluteJoint j : walker.joints) {
            jointAngles.add((int) (Math.round(Math.toDegrees(j.getJointAngle()) / roundFactor)));
        }
        this.worldAngle = (int) (Math.round(Math.toDegrees(walker.getRelativeAngle()) / roundFactor));
    }

    public static void fillActions() {
        // This methods creates actions for all joints.
        for (RevoluteJoint joint : BiPedBody.joints) {
            actions.add(new JointAction(joint)); // New relaxed action (!motorOn)
            for (int i = -1; i <= 1; i++) { // Loop that creates three actions for increase, decrease and "lock" joint
                actions.add(new JointAction(joint, i));
            }
        }
    }

    @Override
    public int hashCode() {
        // Hashcode is returned from the states toString, which contains the angles of the state
        return toString().hashCode();
    }

    @Override
    public String toString() {
        // Tostring methods returns a string containing the rounded angles of the state
        String s = "";
        for (double angle : jointAngles) {
            s = s + Math.round(Math.toDegrees(angle) / roundFactor);
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        // equals method is found when collision are found when searching the hash set "states" in StateAnalyser
        State s = (State) o;

        //Checks degree compared to world
        if (Math.round(Math.toDegrees(this.worldAngle) / roundFactor) != Math.round(Math.toDegrees(s.worldAngle) / roundFactor)) {
            return false; // return false if angles do not match
        }

        // Checks degrees of each joints
        for (int i = 0; i < this.jointAngles.size(); i++) {
            if (Math.round(Math.toDegrees(s.jointAngles.get(i) / roundFactor)) != Math.round(Math.toDegrees(this.jointAngles.get(i) / roundFactor))) {
                return false;// return false if angles do not match
            }
        }
        return true;
    }

    public JointAction getRandomAction() {
        //Returns a random action
        return Simulation.walker.getState().actions.get((int) (Math.random() * actions.size()));
    }

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    public static long getTheoreticalNumberOfStates() {
        // The numbers for these intervals are found by looking at the set upper- and lower limit in each joint
        int hipInterval = Math.round(55) / roundFactor;
        int kneeInterval = Math.round(150) / roundFactor;
        int ankleInterval = Math.round(30) / roundFactor;
        int relativeAngle = Math.round(360) / roundFactor;

        return (hipInterval*hipInterval*kneeInterval*kneeInterval*ankleInterval*ankleInterval*relativeAngle);
    }
}
