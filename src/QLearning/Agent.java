package QLearning;

import aima.core.agent.Action;
import aima.core.learning.reinforcement.agent.QLearningAgent;
import aima.core.probability.mdp.ActionsFunction;

/**
 * Created by frederikjuutilainen on 15/05/15.
 */
public class Agent extends QLearningAgent {
    /**
     * Constructor.
     *
     * @param actionsFunction a function that lists the legal actions from a state.
     * @param noneAction      an action representing None, i.e. a NoOp.
     * @param alpha           a fixed learning rate.
     * @param gamma           discount to be used.
     * @param Ne              is fixed parameter for use in the method f(u, n).
     * @param Rplus           R+ is an optimistic estimate of the best possible reward
     */
    public Agent(ActionsFunction actionsFunction, Action noneAction, double alpha, double gamma, int Ne, double Rplus) {
        super(actionsFunction, noneAction, alpha, gamma, Ne, Rplus);
    }
}
