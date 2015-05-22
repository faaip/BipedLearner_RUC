package QLearning;

import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashSet;

/*
This class contains the HashSet which contains the states. There's one method for getting states.
 */


public class StateAnalyser {
    // TODO description and comments

    public HashSet<State> states = new HashSet<>(100000); // Initial capacity set to 100000

    public State getState(BiPedBody walker) {
        // This methods checks if the states contains the current state
        if (states.contains(walker.getState())) {
            return walker.getState();
        } else {
            states.add(new State(walker)); // If not, the state is added to the hashSet
            Main.noOfStatesExplored++;
            Main.gui.update();
            return walker.getState();
        }
    }
}




