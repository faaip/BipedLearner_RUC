package MDP;

import Rendering_dyn4j.ExampleGraphics2D;
import sample.Balancer;

import java.util.ArrayList;
import java.util.List;


public class State {
    double angle;
    private double reward; // Reward for state
    boolean exitState = false;
    List<Action> actions = new ArrayList<>(); // List of actions

    // Constructor in case of new state
    public State(Balancer balancer) {
        this.angle = balancer.getAngle();

        // Assigns reward dependent on state
        if (balancer.isBalanced()) {
            this.reward = 1;
            exitState = true;
        } else if (balancer.hasFallen()) {
            this.reward = -1;
            exitState = true;
        } else {
            this.reward = -0.05;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return angle + "";
    }

    @Override
    public boolean equals(Object o) {
        return o.toString().equals(toString());
    }


}
