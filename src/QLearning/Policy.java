package QLearning;

import burlap.oomdp.core.*;
import burlap.oomdp.core.State;

import java.util.List;

public class Policy extends burlap.behavior.singleagent.Policy {
    @Override
    public AbstractGroundedAction getAction(State s) {

        // return the one with highest Q-value
        // If unknown - explore!


//        return s.qValues.get(0);
        return null;
    }

    @Override
    public List<ActionProb> getActionDistributionForState(State s) {
        return null;
    }

    @Override
    public boolean isStochastic() {
        return false;
    }

    @Override
    public boolean isDefinedFor(State s) {
        return false;
    }
}
