package QLearning;

import aima.core.agent.Action;
import aima.core.learning.reinforcement.PerceptStateReward;
import aima.core.learning.reinforcement.agent.QLearningAgent;
import aima.core.probability.mdp.ActionsFunction;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import org.dyn4j.dynamics.joint.Joint;
import sample.BiPedBody;
import sample.Main;

import java.util.HashMap;
import java.util.Map;

public class Agent {
    private JointAction noneAction = new JointAction(true);
    private double alpha = 0.0; // Learning rate
    private double gamma = 0.0; // Decay rate
    private double Rplus = 6.0; // Optimistic reward prediction?

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null;
    private BiPedBody walker;

    private int Ne = 2;
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(double alpha, double gamma, BiPedBody walker) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Main.initState;
        this.walker = walker;
    }

    public JointAction execute(){

        // Puts current state if state is null
        if(s == null){this.s = walker.getState();}
        if(a == null){this.a = Main.initAction;}

        // TO-DO - why is this sPrime?!
        State sPrime = walker.getState();
        double rPrime = walker.reward();

        // if terminal
        if(walker.hasFallen())
        {
            Q.put(new Pair<>(sPrime, noneAction), rPrime); // What is a none action?!
        }



        // If State s not null
        if(s != null) {
            // Increment frequencies
            Pair<State, JointAction> sa = new Pair<>(s, a);
            Nsa.incrementFor(sa);
//            System.out.println("State-Action count: " + Nsa.getCount(sa) + " - " + Q.get(sa));

            // Get Q-value
            Double Qsa = Q.get(sa);
            if (Qsa == null) {
                Qsa = 0.0;
            }

            if(r == null)
            {r = walker.reward();
                System.out.println("Reward: " + r);}


            Q.put(sa, Qsa + alpha(Nsa, s, a)
                    * (r + gamma * maxAPrime(sPrime) - Qsa));

//            System.out.println("Reward: " + r);
        }

        if (isTerminal(sPrime)) {
            s = null;
            a = null;
            r = null;
        } else
    {
            s = sPrime;
            a = argmaxAPrime(sPrime);
            r = rPrime;
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
        boolean terminal = false;
//        if (null != s && Main.actionsFunction.actions.size() == 0) {
//            // No actions possible in state is considered terminal.
//            terminal = true;
//        }
        return terminal;
    }

    protected double f(Double u, int n) {
        // A Simple definition of f(u, n):
        if (null == u || n < Ne) {
            return Rplus;
        }
        return u;
    }

    protected double alpha(FrequencyCounter<Pair<State, JointAction>> Nsa, State s, JointAction a) {
        // Default implementation is just to return a fixed parameter value
        // irrespective of the # of times a state action has been encountered
        return alpha;
    }

    private double maxAPrime(State sPrime) {
        double max = Double.NEGATIVE_INFINITY;
        if (Main.actionsFunction.jointActions(sPrime).size() == 0) {
            // a terminal state
            // TO-DO WHAT IS TERMINAL
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
