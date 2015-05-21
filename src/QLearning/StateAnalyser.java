package QLearning;

import Rendering_dyn4j.BiPedBody;
import sample.Main;

import java.util.HashSet;

public class StateAnalyser {
    // TODO description and comments

    public HashSet<State> states = new HashSet<>();
    private int  noOfStates = 0;

    public State getState(BiPedBody walker) {

        //if states does contain an aproximate state
        //return this aproximate states
        //else
        //add to states and return this new state

        if (states.contains(walker.getState())) {
//            System.out.println("Old state " + states.size());

//            return states.
            return walker.getState();
//            return getApproximate(walker.getState());
        } else {
            System.out.println("New state number: " + states.size());
            states.add(new State(walker));
            noOfStates++;
            Main.gui.update();
            return walker.getState();

            // TODO den returnerer ikke den approximerede!!
        }
    }

    public void printStates() {
        System.out.println(states);
    }

    private State getApproximate(State s) {

        int round = 20;
        State approxState = new State(s);
        Math.round(Math.toDegrees(approxState.relativeAngle)/ round);


        return approxState;


    }


}




