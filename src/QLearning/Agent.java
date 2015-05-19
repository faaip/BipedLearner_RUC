package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashMap;
import java.util.Map;

public class Agent {
    private JointAction noneAction = new JointAction();
    private double alpha; // Learning rate TODO - ADD TO GUI
    private double gamma; // Decay rate TODO - ADD TO GUI
    private double Rplus = 0; // Optimistic reward prediction? //TODO find optimal Rplus

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null;
    private BiPedBody walker = Graphics2D.walker;

    private int Ne = 1;
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(double alpha, double gamma) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Graphics2D.walker.getState();
        this.a = Main.initAction;
    }

    public JointAction execute(){
        State sPrime = Main.analyser.getState(Graphics2D.walker);
        double rPrime = Graphics2D.walker.reward();

        // if terminal
        if (isTerminal(sPrime)) {
            Q.put(new Pair<>(sPrime, noneAction), rPrime);
        }

        // If State s not null
        if(s != null) {
            // Increment frequencies
            Pair<State, JointAction> sa = new Pair<>(s, a);
            Nsa.incrementFor(sa);
            System.out.println("State-Action count: " + Nsa.getCount(sa) + ". Q = " + Q.get(sa));

            // Get Q-value
            Double Qsa = Q.get(sa);
            if (Qsa == null) {
                Qsa = 0.0;
            }

            if(r == null)
            {r = Graphics2D.walker.reward();
                System.out.println("Reward: " + r);}

            r = Graphics2D.walker.reward();
            Q.put(sa, Qsa + alpha(Nsa, s, a)
                    * (r + gamma * maxAPrime(sPrime) - Qsa));

        }

        if (isTerminal(sPrime)) {
            s = null;
            a = null;
            r = null;
        } else
    {
            this.s = sPrime;
            this.a = argmaxAPrime(sPrime);
            this.r = rPrime;
        }

        return a;
    }

    private JointAction argmaxAPrime(State sPrime) {
        JointAction a = null;
        double max = Double.NEGATIVE_INFINITY;
        for (JointAction aPrime : Main.actionsFunction.jointActions(sPrime)) {
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
        return Graphics2D.walker.hasFallen();
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

    protected double alpha(FrequencyCounter<Pair<State, JointAction>> Nsa, State s, JointAction a) {
        // Default implementation is just to return a fixed parameter value
        // irrespective of the # of times a state action has been encountered

        // TODO alpha method - Maybe not

        return alpha;
    }

    private double maxAPrime(State sPrime) {
        double max = Double.NEGATIVE_INFINITY;
        if (Main.actionsFunction.jointActions(sPrime).size() == 0) {
            // a terminal state
            max = Q.get(new Pair<State, JointAction>(sPrime, noneAction));
        } else {
            for (JointAction aPrime : Main.actionsFunction.jointActions((sPrime))) {
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
