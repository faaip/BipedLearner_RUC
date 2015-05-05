package MDP;

import aima.core.agent.*;
import aima.core.agent.Action;
import aima.core.probability.mdp.MarkovDecisionProcess;
import aima.core.probability.mdp.impl.MDP;

import java.util.Set;

/**
 * Created by frederikjuutilainen on 05/05/15.
 */
public class BalancerMDP implements MarkovDecisionProcess{
    @Override
    public Set states() {
        return null;
    }

    @Override
    public Object getInitialState() {
        return null;
    }

    @Override
    public Set actions(Object o) {
        return null;
    }

    @Override
    public double transitionProbability(Object sDelta, Object o, Action action) {
        return 0;
    }

    @Override
    public double reward(Object o) {
        return 0;
    }
}
