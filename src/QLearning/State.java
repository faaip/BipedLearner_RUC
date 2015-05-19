package QLearning;

import Rendering_dyn4j.Graphics2D;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import Rendering_dyn4j.BiPedBody;

import java.util.*;

public class State {
    // TODO comments and description

    // State features
    private double relativeAngle; // Bodys relative angle to surrounding
    private ArrayList<Double> jointAngles = new ArrayList<>(); // Angles of joints

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    private static ArrayList<JointAction> actions = new ArrayList<>();

    // Constructor in case of new state
    public State(BiPedBody walker) {
        for (RevoluteJoint j : walker.joints) {
            jointAngles.add(j.getJointAngle());
        }
        this.relativeAngle = walker.getRelativeAngle();
    }

    public static void fillActions() {
        // Create actions
        //TODO if delete
        // if(actions.size() < 1) {
        System.out.println("Fill Joints happen");
        for (RevoluteJoint joint : BiPedBody.joints) {
            actions.add(new JointAction(joint));
            for (int i = -1; i <= 1; i++) {
                actions.add(new JointAction(joint, i));
            }
        }
        //}
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
            s = s + Math.round(Math.toDegrees(angle) / 20);
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        State s = (State) o;
        int round = 20;

        //Checks degree compared to world
        if (Math.round(Math.toDegrees(this.relativeAngle) / round) != Math.round(Math.toDegrees(s.relativeAngle) / round)) {
            return false;
        }

        // Checks degrees of each joints
        for (int i = 0; i < this.jointAngles.size(); i++) {
            if (Math.round(Math.toDegrees(s.jointAngles.get(i) / round)) != Math.round(Math.toDegrees(this.jointAngles.get(i) / round))) {
                return false;
            }
        }
        return true;
    }

    //first random action
    //TODO move it
    public JointAction getRandomAction() {
        System.out.println("Random action taken");
        return Graphics2D.walker.getState().actions.get((int) (Math.random() * actions.size()));
    }
}
