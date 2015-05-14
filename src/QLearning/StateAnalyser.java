package QLearning;

import burlap.behavior.singleagent.QValue;
import burlap.behavior.statehashing.StateHashTuple;
import sample.BiPedBody;
import sample.Main;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class StateAnalyser {

    HashSet<State> states = new HashSet<>();
    private Map<State,List<QValue>> qValues;

    public State getState(BiPedBody walker) {

        //if states does contain an aproximate state
        //return this aproximate states
        //else
        //add to states and return this new state

        if (states.contains(walker.getState())) {
//            System.out.println("Old state");
            return walker.getState();
        } else {
//            System.out.println("New state!");
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
}




