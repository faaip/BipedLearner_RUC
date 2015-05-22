package QLearning;

import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashSet;

public class StateAnalyser {
    // TODO description and comments

    public HashSet<State> states = new HashSet<>();

    public State getState(BiPedBody walker) {

        if (states.contains(walker.getState())) {
//            System.out.println("Old state " + states.size());

//            return states.
            return walker.getState();
//            return getApproximate(walker.getState());
        } else {
            states.add(new State(walker));
            Main.noOfStatesExplored++;
            Main.gui.update();
            return walker.getState();
        }
    }
}




