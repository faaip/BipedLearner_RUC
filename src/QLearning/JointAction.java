package QLearning;

import Rendering_dyn4j.Graphics2D;
import aima.core.agent.Action;
import org.dyn4j.dynamics.joint.RevoluteJoint;

public class JointAction implements Action {
    RevoluteJoint joint; // Joint used in action
    boolean motorOn; // is motor on or is joint relaxed in this particular action
    int a; // Optional int indicating negative, locked or positive action
    boolean noOp;

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

    // Third constructor for noOp
    public JointAction(boolean noOp) {
        this.noOp = noOp;
    }

    // Execute appropriate method for action
    public void doAction() {
        if(noOp){;}
        if (this.motorOn) {
            Graphics2D.walker.setJoint(this.joint, this.a);
        } else {
            Graphics2D.walker.relaxJoint(this.joint
            );
        }
    }

    @Override
    public String toString()
    {
        return "Action " + a + " motor on: " + motorOn;
    }

    @Override
    public boolean isNoOp() {
        return !motorOn;
    }
}
