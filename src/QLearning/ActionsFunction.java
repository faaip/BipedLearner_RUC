package QLearning;

import aima.core.agent.Action;


import java.util.ArrayList;
import java.util.Set;


public class ActionsFunction implements aima.core.probability.mdp.ActionsFunction {
    @Override
    public Set<Action> actions(Object s) {
        return null;
    }

    public ArrayList<JointAction> jointActions(State s)
    {
        return s.getActions();
    }
}
