package QLearning;

import aima.core.agent.Action;


import java.util.Set;

/**
 * Created by frederikjuutilainen on 15/05/15.
 */
public class ActionsFunction implements aima.core.probability.mdp.ActionsFunction {
    @Override
    public Set<Action> actions(Object s) {
        return null;
    }

    public Set<JointAction> jointActions(State s)
    {
        return s.getActions();
    }
}
