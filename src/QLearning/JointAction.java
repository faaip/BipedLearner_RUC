package QLearning;

import Rendering_dyn4j.Simulation;
import Rendering_dyn4j.ThreadSync;
import aima.core.agent.Action;
import org.dyn4j.dynamics.joint.RevoluteJoint;

/*
This represents an action and therefore implements Action from aima.core.
 */

public class JointAction implements Action {
    RevoluteJoint joint; // Joint used in action
    boolean motorOn; // is motor on or is joint relaxed in this action
    int a; // int indicating negative (-1), locked (0) or positive motor input (1)
    boolean noOp;

    public JointAction(RevoluteJoint joint) {
        // Constructor for a relaxed joint action
        this.joint = joint;
        this.motorOn = false;
    }

    public JointAction(RevoluteJoint joint, int i) {
        // Constructor for an action
        this.joint = joint;
        this.a = i; // Integer indicating a negative, zero or positive motor speed
        this.motorOn = true;
    }

    public JointAction() {
        // Third constructor for none operation (noOp) used in terminal states
        this.noOp = true;
    }

    public void doAction() {
        // this method is called in order to execute an action
        synchronized (ThreadSync.lock) {
            if (noOp) {
                ;
            }
            if (this.motorOn) {
                Simulation.walker.setJoint(this.joint, this.a);
            } else {
                Simulation.walker.relaxJoint(this.joint);
            }
        }
    }

    @Override
    public String toString() {
        // Overridden tostring
        if (noOp) {
            return "noOp";
        } else {
            return joint.getUserData() + " Action: " + a + " Motor on: " + motorOn;
        }
    }

    @Override
    public boolean isNoOp() {
        // isNoOp indicates if this is a none operation
        return noOp;
    }
}
