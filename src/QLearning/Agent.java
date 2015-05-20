package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import Rendering_dyn4j.BiPedBody;
import sample.Main;
import scpsolver.graph.Graph;

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
    private BiPedBody walker = Graphics2D.walker;

    private int Ne = 1;
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(int mode) {
        this.mode = mode;
        //TODO find optimal values
        switch (mode) {
            case 0:
                this.Rplus = 3;
                this.gamma = 0.9; // Lots of reliance of future reward
                this.alpha = 0.8; // Large number of states = high learning rate
                break;
            case 1:
                this.Rplus = 2;
                this.gamma = 0.2;
                this.alpha = 0.5;

                break;
            case 2:
                break;
        }

        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Graphics2D.walker.getState();
        this.a = Main.initAction;

    }

    public JointAction execute() {
        State sPrime = Main.analyser.getState(Graphics2D.walker);
        double rPrime = Graphics2D.walker.reward();

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

            Main.gui.update(Nsa.getCount(sa),Qsa); // Update gui with info for current actions

            if (r == null) {
                r = Graphics2D.walker.reward();
                System.out.println("Reward: " + r);
            }

            r = Graphics2D.walker.reward();
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

        return a;
    }

    private JointAction argmaxAPrime(State sPrime) {
        JointAction a = null;
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
        // State is terminal if the walker has fallen
        if(mode == 0) {
            return Graphics2D.walker.hasFallen() || !Graphics2D.walker.isInSight(); // Falling is a terminal state in mode 0
        }

        return false;
    }

    protected double f(Double u, int n) {
        // A Simple definition of f(u, n):
        if (null == u || n < Ne) {
//            System.out.println("R PLUS");
            return Rplus;
        }
//        System.out.println(" U U U U U ");
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
