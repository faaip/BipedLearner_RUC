package MDP;

import Rendering_dyn4j.ExampleGraphics2D;
import sample.Balancer;

import java.util.ArrayList;
import java.util.List;


public class State {
    double angle;
    double reward; // Reward for state
    List<Action> actions = new ArrayList<>(); // List of actions

    // Constructor in case of new state
    public State(Balancer balancer){
        this.angle = balancer.getAngle();

        if(balancer.isBalanced()) {this.reward = 1;}
        else{this.reward = -0.05;}

    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public String toString()
    {
        return angle+"";
    }

    @Override
    public boolean equals(Object o)
    {
        return o.toString().equals(toString());
    }


}
