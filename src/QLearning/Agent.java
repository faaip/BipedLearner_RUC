package QLearning;

import Rendering_dyn4j.Simulation;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import sample.Main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
The Agent class is the learning agent. The code seen here is based largely on the one QLearningAgent included in the AIMA
implementations of algorithms. This can be found on the AIMA github (https://github.com/aima-java/aima-java). The purpose of
this class is contain Q-values, update these and around an action connected to the optimal policy for a given state.
 */


public class Agent {
    private JointAction noneAction = new JointAction(); // A "none"-action
    private double alpha; // Learning rate
    private double gamma; // Decay rate
    private double Rplus; // Optimistic reward prediction
    private int mode; // Reward mode for the walker

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null; // Reward

    private int Ne = 1; // Variable used in exploration function
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>();
    private Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(int mode) { // Constructor with mode as input argument
        this.mode = mode;
        switch (mode) { // Value for Rplus, gamma and alpha depend on mode.
            case 0:
                this.Rplus = 250;
                this.gamma = 0.2; // Lots of reliance of future reward - decay rate
                this.alpha = 0.5; // Large number of states = high learning rate - learning rate
                break;
            case 1:
                this.Rplus = 500;
                this.gamma = 0.2;
                this.alpha = 1;
                break;
            case 2:
                this.Rplus = 150;
                this.gamma = 0.1;
                this.alpha = 0.1;
                this.Ne = 1;
                break;
        }
        this.s = Simulation.walker.getState(); // State is set to initial state for walker
        this.a = Main.initAction; // Initial action
    }

    public JointAction execute() {
        // This method returns a JointAction from the optimal policy
        State sPrime = Simulation.walker.getState();
        double rPrime = Simulation.walker.reward();

        // if terminal
        if (isTerminal()) {
            Q.put(new Pair<>(sPrime, noneAction), rPrime);
        }
        // If State s not null
        if (s != null) {
            Pair<State, JointAction> sa = new Pair<>(s, a);
            Nsa.incrementFor(sa);// Increment frequencies

            // Get Q-value
            Double Qsa = Q.get(sa);
            if (Qsa == null) {
                Qsa = 0.0;
            }

            Main.gui.update(Nsa.getCount(sa), Qsa); // Update gui with info for current actions

            r = Simulation.walker.reward();
            Q.put(sa, Qsa + alpha * (r + gamma * maxAPrime(sPrime) - Qsa));
        }

        if (isTerminal()) { // if terminal, station, action and reward are set accordingly
            s = null;
            a = null;
            r = null;
        } else {
            this.s = sPrime;
            this.a = argmaxAPrime(sPrime);
            this.r = rPrime;
        }

        if (a != null) {
            Main.gui.update(a); // GUI is updated with action
        }
        return a; // JointAction is returned
    }

    private JointAction argmaxAPrime(State sPrime) {
        JointAction a = null;
        Collections.shuffle(sPrime.getActions()); // Shuffle list for random actions
        double max = Double.NEGATIVE_INFINITY;
        for (JointAction aPrime : sPrime.getActions()) {
            Pair<State, JointAction> sPrimeAPrime = new Pair<State, JointAction>(sPrime, aPrime);
            double explorationValue = f(Q.get(sPrimeAPrime), Nsa.getCount(sPrimeAPrime));
            if (explorationValue > max) {
                max = explorationValue;
                a = aPrime;
            }
        }
        return a; // returns a with highest expected utility
    }

    private boolean isTerminal() {
        // Falling is only a terminal state in mode 0
        if (mode == 0) {
            return Simulation.walker.hasFallen() || !Simulation.walker.isInSight();
        }
        return false;
    }

    protected double f(Double u, int n) {
        // A Simple definition of f(u, n):
        if (null == u || n < Ne) {
            return Rplus;
        }
        return u;
    }

    private double maxAPrime(State sPrime) {
        double max = Double.NEGATIVE_INFINITY;
        if (sPrime.getActions().size() == 0) {
            // a terminal state
            max = Q.get(new Pair<State, JointAction>(sPrime, noneAction));
        } else {
            for (JointAction aPrime : sPrime.getActions()) {
                Double Q_sPrimeAPrime = Q.get(new Pair<State, JointAction>(sPrime, aPrime));
                if (null != Q_sPrimeAPrime && Q_sPrimeAPrime > max) {
                    max = Q_sPrimeAPrime;
                }
            }
        }
        if (max == Double.NEGATIVE_INFINITY) {
            // Assign 0 as the Q if no max has been found
            max = 0.0;
        }
        return max;
    }

    public int getQsize() {
        return Q.size(); // Size of the Q HashMap
    }
}
