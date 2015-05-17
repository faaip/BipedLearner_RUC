package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.learning.reinforcement.PerceptStateReward;
import burlap.behavior.singleagent.QValue;
import Rendering_dyn4j.BiPedBody;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StateAnalyser {

    public HashSet<State> states = new HashSet<>();
    private Map<State,List<QValue>> qValues;

    public State getState(BiPedBody walker) {

        //if states does contain an aproximate state
        //return this aproximate states
        //else
        //add to states and return this new state



        if (states.contains(walker.getState())) {
//            System.out.println("Old state " + states.size());

            return walker.getState();
        } else {
//            System.out.println("New state number: " + states.size());
            states.add(new State(walker));
            return walker.getState();
        }


    }

    public void printStates() {
        System.out.println(states);
    }

    public List<QValue> getQs(){

        return null;
    }

    public void updateQ(State currentState, JointAction action, State nextState) {
        double q = currentState.q.get(action);



    }

    public PerceptStateReward percept(State currentState)
    {
        PerceptStateReward p = new PerceptStateReward() {
            @Override
            public Object state() {
                return currentState;
            }

            @Override
            public double reward() {
                return Graphics2D.walker.reward();
            }
        };

        return p;
    }
}




