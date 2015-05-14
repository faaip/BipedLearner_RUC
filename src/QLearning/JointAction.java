package QLearning;

import Rendering_dyn4j.ExampleGraphics2D;
import aima.core.learning.framework.Example;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.State;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import burlap.oomdp.singleagent.Action;

public class JointAction {
    RevoluteJoint joint; // Joint used in action
    boolean motorOn; // is motor on or is joint relaxed in this particular action
    int a; // Optional int indicating negative, locked or positive action

    // Constructor a "non-action"
    public JointAction(RevoluteJoint joint) {
        this.joint = joint;
        this.motorOn = false;
    }

    // Overloaded constructor for a joint and an integer indicating the action
    public JointAction(RevoluteJoint joint, int i) {
        this.joint = joint;
        this.a = i;
        this.motorOn = true;
    }

    // Execute appropriate method for action
    public void doAction() {
        if (this.motorOn) {
            ExampleGraphics2D.walker.setJoint(this.joint, this.a);
        } else {
            ExampleGraphics2D.walker.relaxJoint(this.joint
            );
        }
    }

    @Override
    public String toString()
    {
        return "Action " + a + " motor on: " + motorOn;
    }
}