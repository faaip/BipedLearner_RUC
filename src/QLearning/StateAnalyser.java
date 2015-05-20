package QLearning;

import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashSet;

public class StateAnalyser {
    // TODO description and comments

    public HashSet<State> states = new HashSet<>();
    public State getState(BiPedBody walker) {

        //if states does contain an aproximate state
        //return this aproximate states
        //else
        //add to states and return this new state

        if (states.contains(walker.getState())) {
//            System.out.println("Old state " + states.size());

//            return states.
            return walker.getState();
        } else {
//            System.out.println("New state number: " + states.size());
            states.add(new State(walker));
            Main.gui.update();
            return walker.getState();
        }
    }

    public void printStates() {
        System.out.println(states);
    }



}




