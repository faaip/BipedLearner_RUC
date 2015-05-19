package QLearning;

import Rendering_dyn4j.Graphics2D;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import Rendering_dyn4j.BiPedBody;

import java.util.*;

public class State {
    // TODO comments and description

    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;
    private boolean upperLeg1InFrontOfTorso;
    private boolean upperLeg2InFrontOfTorso;

    private double relativeAngle; // Bodys relative angle to surrounding
    private ArrayList<Double> jointAngles = new ArrayList<>(); // Angles of joints

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    private static ArrayList<JointAction> actions = new ArrayList<>();

    // Constructor in case of new state
    public State(BiPedBody walker) {

        for(RevoluteJoint j : walker.joints)
        {
        jointAngles.add(j.getJointAngle());
        }
    }

    public static void fillActions() {
        // Create actions
        //TODO if delete
       // if(actions.size() < 1) {
        System.out.println("Fill Joints happen");
            for (RevoluteJoint joint : BiPedBody.joints) {
                actions.add(new JointAction(joint));
                for (int i = -1; i <= 1; i++) {
                    actions.add(new JointAction(joint, i));
                }
            }
        //}
    }

    @Override
    public int hashCode() {

        //TODO hashCode
//        return jointAngles.hashCode();
        return 0;
    }

    @Override
    public String toString() {

        //TODO a beautiful toString

        String s = "";
        String fullS;

        for(double angle : jointAngles)
        {
//            s = s+""+(int) Math.floor(Math.toDegrees(angle));
            s = s+5*(Math.round(Math.toDegrees(angle)/25));
//            System.out.println(s);
        }

//        System.out.println(s);
        return s;



    }

    @Override
    public boolean equals(Object o) {

        //TODO FIX

        State s = (State) o;
        int round = 20;

//        if(s.foot1OnGround != this.foot1OnGround){return false;}
//        if(s.foot1Forward != this.foot1Forward){return false;}
//        if(s.foot2OnGround != this.foot2OnGround){return false;}
//        if(s.torsoLeaningForward != this.torsoLeaningForward){return false;}
//        if(s.knee1Forward != this.knee1Forward){return false;}
//        if(s.upperLeg2InFrontOfTorso != this.upperLeg2InFrontOfTorso){return false;}
//        if(s.upperLeg1InFrontOfTorso != this.upperLeg1InFrontOfTorso){return false;}


        //Checks degree compared to world
        if(Math.round(Math.toDegrees(this.relativeAngle)/round) != Math.round(Math.toDegrees(s.relativeAngle)/round))
        {return false;}

        // Checks degrees of each joints
        for (int i = 0; i < this.jointAngles.size(); i++) {
            if(Math.round(Math.toDegrees(s.jointAngles.get(i)/round)) != Math.round(Math.toDegrees(this.jointAngles.get(i)/round)))
            {
                return false;
            }
        }


        return true;

//        return o.toString().equals(this.toString());

    }

//first random action
    //TODO move it
    public JointAction getRandomAction() {
        System.out.println("Random action taken");
        return Graphics2D.walker.getState().actions.get((int) (Math.random()*actions.size()));
    }
}
