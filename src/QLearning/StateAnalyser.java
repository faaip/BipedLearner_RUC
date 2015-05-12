package QLearning;

import sample.BiPedBody;

import java.util.HashSet;

/**
 * Created by frederikjuutilainen on 12/05/15.
 * This class can identify a state and check if it has occured before
 */
public class StateAnalyser {

    HashSet<State> states = new HashSet<>();

    public StateAnalyser() {

    }

    public State getState(BiPedBody walker) {

        //if states does contain an aproximate state
            //return this aproximate states
        //else
            //add to states and return this new state

        if(states.contains(walker.getState()))
        {
            System.out.println("Old state");
            return walker.getState();
        }
        else
        {
            System.out.println("New state!");
            states.add(new State(walker));
            return walker.getState();
        }



    }
    public void printStates()
    {
        System.out.println(states);
    }

}




