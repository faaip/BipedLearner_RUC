package QLearning;

import Rendering_dyn4j.Graphics2D;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import Rendering_dyn4j.BiPedBody;

import java.util.*;

public class State {

    // TODO comments and description

    public double getRelativeAngle() {
        return relativeAngle;
    }

    public ArrayList<Double> getJointAngles() {
        return jointAngles;
    }

    public void setRelativeAngle(double relativeAngle) {
        this.relativeAngle = relativeAngle;
    }

    public void setJointAngles(ArrayList<Double> jointAngles) {
        this.jointAngles = jointAngles;
    }

    // State features
    public double relativeAngle; // Bodys relative angle to surrounding
    public ArrayList<Double> jointAngles = new ArrayList<>(); // Angles of joints

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    public void shuffleActions() {
        Collections.shuffle(actions);
    }

    private static ArrayList<JointAction> actions = new ArrayList<>();

    public State(BiPedBody walker) {
//        for (RevoluteJoint j : walker.joints) {
//            jointAngles.add(j.getJointAngle());
//        }
//        this.relativeAngle = walker.getRelativeAngle();

        // TODO clean me

        int round = 10;

        for (RevoluteJoint j : walker.joints) {
//            jointAngles.add(Math.toDegrees(j.getJointAngle()));
            jointAngles.add((double) (Math.round(Math.toDegrees(j.getJointAngle()) / round)));

        }
        this.relativeAngle = (double) (Math.round(Math.toDegrees(walker.getRelativeAngle()) / round));

    }

    public State(State s) {
        this.setRelativeAngle(s.getRelativeAngle());
        this.jointAngles.clear();
        this.jointAngles.addAll(s.getJointAngles());
    }


    public static void fillActions() {
        // Create actions

        System.out.println("Fill Joints happen");
        for (RevoluteJoint joint : BiPedBody.joints) {
            actions.add(new JointAction(joint)); // TODO kommenter ud
            for (int i = -1; i <= 1; i++) {
                actions.add(new JointAction(joint, i));
            }
        }

//        Collections.reverse(actions);
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

        // TODO relative angle er i virkeligheden
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


    public JointAction getRandomAction() {
        //Returns a random action
        return Graphics2D.walker.getState().actions.get((int) (Math.random() * actions.size()));
    }
}
