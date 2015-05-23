package QLearning;

import Rendering_dyn4j.Simulation;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import sample.Main;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//TODO Comment, Clean og lave referencer til AIMA



public class Agent {
    private JointAction noneAction = new JointAction();
    private double alpha; // Learning rate
    private double gamma; // Decay rate
    private double Rplus = 10; // Optimistic reward prediction? //
    private int mode;

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null;

    private int Ne = 1;
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    public Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(int mode) {
        this.mode = mode;
        //TODO find optimal values
        switch (mode) {
            case 0:
                this.Rplus = 25;
                this.gamma = 0.9; // Lots of reliance of future reward - decay rate
                this.alpha = 1; // Large number of states = high learning rate - learning rate
                break;
            case 1:
                this.Rplus = 800;
                this.gamma = 0.1;
                this.alpha = 0.9;

                break;
            case 2:
                this.Rplus = 150;
                this.gamma = 0.1;
                this.alpha = 0.9;
                break;
        }

        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Simulation.walker.getState();
        this.a = Main.initAction;

    }

    public JointAction execute() {
        State sPrime = Simulation.walker.getState();
        double rPrime = Simulation.walker.reward();

        // if terminal
        if (isTerminal(sPrime)) {
            Q.put(new Pair<>(sPrime, noneAction), rPrime);
        }

        // If State s not null
        if (s != null) {
            // Increment frequencies
            Pair<State, JointAction> sa = new Pair<>(s, a);
            Nsa.incrementFor(sa);

            // Get Q-value
            Double Qsa = Q.get(sa);
            if (Qsa == null) {
                Qsa = 0.0;
            }

            Main.gui.update(Nsa.getCount(sa), Qsa); // Update gui with info for current actions

            if (r == null) {
                r = Simulation.walker.reward();
                System.out.println("Reward: " + r);
            }

            r = Simulation.walker.reward();
            Q.put(sa, Qsa + alpha
                    * (r + gamma * maxAPrime(sPrime) - Qsa));

        }

        if (isTerminal(sPrime)) {
            s = null;
            a = null;
            r = null;
        } else {
            this.s = sPrime;
            this.a = argmaxAPrime(sPrime);
            this.r = rPrime;
        }

        if (a != null) {
            Main.gui.update(a);
        }
        return a;
    }

    private JointAction argmaxAPrime(State sPrime) {
        JointAction a = null;
        Collections.shuffle(sPrime.getActions()); // Shuffle list for random actions
        double max = Double.NEGATIVE_INFINITY;
        for (JointAction aPrime : sPrime.getActions()) {
            Pair<State, JointAction> sPrimeAPrime = new Pair<State, JointAction>(sPrime, aPrime);
            double explorationValue = f(Q.get(sPrimeAPrime), Nsa
                    .getCount(sPrimeAPrime));
            if (explorationValue > max) {
                max = explorationValue;
                a = aPrime;
            }
        }
        return a;
    }

    private boolean isTerminal(State s) {
        // TODO måske betyder terminal state at den ikke gider lære.
        // State is terminal if the walker has fallen
        if (mode == 0) {
            return Simulation.walker.hasFallen() || !Simulation.walker.isInSight(); // Falling is a terminal state in mode 0
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
            // Assign 0 as the mimics Q being initialized to 0 up front.
            max = 0.0;
        }
        return max;
    }
}
