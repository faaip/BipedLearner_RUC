package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.util.FrequencyCounter;
import aima.core.util.datastructure.Pair;
import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashMap;
import java.util.Map;

public class Agent {
    private JointAction noneAction = new JointAction(true);
    private double alpha = 0.0; // Learning rate
    private double gamma = 0.001; // Decay rate
    private double Rplus = 4.5; // Optimistic reward prediction? //TODO Figure this out

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null;
    private BiPedBody walker = Graphics2D.walker;

    private int Ne = 5; //TODO figures this out
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(double alpha, double gamma) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Main.initState;
    }

    public JointAction execute(){

        // Puts current state if state is null
        if(s == null){this.s = Graphics2D.walker.getState();}
        if(a == null){this.a = Main.initAction;}


        // TO-DO - why is this sPrime?!
        State sPrime = Graphics2D.walker.getState();
        double rPrime = Graphics2D.walker.reward();

        // if terminal
        if(Graphics2D.walker.hasFallen())
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
            {r = Graphics2D.walker.reward();
                System.out.println("Reward: " + r);}

            //TODO Check this, is reward inserted correctly?!
            r = Graphics2D.walker.reward();
            if(Graphics2D.walker.hasFallen()){r = -100.0;}
            Q.put(sa, Qsa + alpha(Nsa, s, a)
                    * (r + gamma * maxAPrime(sPrime) - Qsa));

//            Q.put(sa, Qsa + alpha(Nsa, s, a)
//                    * (r + gamma * maxAPrime(sPrime) - Qsa));

//            System.out.println("Reward: " + r);
        }

        // TODO setTerminalState
        if (isTerminal(sPrime)) {
//            s = null;
//            a = null;
//            r = null;
        } else
    {
            s = sPrime;
            a = argmaxAPrime(sPrime);
            r = rPrime;
        }

        return a;
    }

    public void setWalker()
    {
        this.walker = Graphics2D.walker;
    }

    public BiPedBody getWalker()
    {
        return Graphics2D.walker;
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
            // No actions possible in state is considered terminal.
//            terminal = true;
//        }

        if(Graphics2D.walker.hasFallen()){terminal = true;}

        return terminal;
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
        return alpha;
    }

    private double maxAPrime(State sPrime) {
        double max = Double.NEGATIVE_INFINITY;
        if (Main.actionsFunction.jointActions(sPrime).size() == 0) {
            // a terminal state
            // TODO: WHAT IS TERMINAL
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
