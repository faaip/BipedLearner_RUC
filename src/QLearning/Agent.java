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

/**
 * Created by frederikjuutilainen on 15/05/15.
 */
public class Agent {
    private JointAction noneAction = new JointAction(true);
    private double alpha = 0.0; // Learning rate
    private double gamma = 0.0; // Decay rate
    private double Rplus = 4.0; // Optimistic reward prediction?

    private State s = null; // S (previous State)
    private JointAction a = null; // A (previous action)
    private Double r = null;

    private int Ne = 4;
    private FrequencyCounter<Pair<State, JointAction>> Nsa = new FrequencyCounter<>(); // From aima
    Map<Pair<State, JointAction>, Double> Q = new HashMap<>();

    public Agent(double alpha, double gamma) {
        this.alpha = alpha;
        this.gamma = gamma;
        this.s = Main.initState;
    }

    public JointAction execute(BiPedBody walker){

        // Puts current state if state is null
        if(s == null){this.s = walker.getState();
            System.out.println(s);}
        if(a == null){this.a = s.getRandomAction();}

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
            System.out.println("YES");
            // Increment frequencies
            Pair<State, JointAction> sa = new Pair<>(s, a);
            Nsa.incrementFor(sa);

            // Get Q-value
            Double Qsa = Q.get(sa);
            if (Qsa == null) {
                Qsa = 0.0;
            }

            Q.put(sa, Qsa + alpha(Nsa, s, a)
                    * (r + gamma * maxAPrime(sPrime) - Qsa));
        }



        return a;
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
