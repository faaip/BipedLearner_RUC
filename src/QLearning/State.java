package QLearning;

import Rendering_dyn4j.Graphics2D;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import Rendering_dyn4j.BiPedBody;

import java.util.*;

public class State {

    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;
    private boolean upperLeg1InFrontOfTorso;
    private boolean upperLeg2InFrontOfTorso;

    private double relativeAngle;

    ArrayList<Double> jointAngles = new ArrayList<>();

    // -
    boolean exitState = false;

    public ArrayList<JointAction> getActions() {
        return actions;
    }

    public static ArrayList<JointAction> actions = new ArrayList<>();

    // Constructor in case of new state
    public State(BiPedBody walker) {
        this.torsoLeaningForward = walker.isTorsoLeaningForward();
        this.knee1Forward = walker.isKnee1Forward();
        this.foot1Forward = walker.isFoot1Forward();
        this.foot1OnGround = walker.isFoot1OnGround();
        this.foot2OnGround = walker.isFoot2OnGround();
        this.upperLeg1InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();
        this.upperLeg2InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();
        this.relativeAngle = walker.getRelativeAngle();

        for(RevoluteJoint j : walker.joints)
        {
        jointAngles.add(j.getJointAngle());
        }
    }

    public static void fillActions() {

        // Create actions
        if(actions.size() < 1) {
            for (RevoluteJoint joint : BiPedBody.joints) {
                actions.add(new JointAction(joint));
                for (int i = -1; i <= 1; i++) {
                    actions.add(new JointAction(joint, i));
                }

            }
            System.out.println(actions);

        }
    }

    @Override
    public int hashCode() {
//        System.out.println(toString());

        // TODO new hashCode

        return 0;
    }

    @Override
    public String toString() {

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


//        return booleanToInt(torsoLeaningForward) + "" + booleanToInt(knee1Forward) + "" + booleanToInt(foot1Forward) + "" + booleanToInt(foot1OnGround) + "" + booleanToInt(foot2OnGround) + "" + booleanToInt(upperLeg1InFrontOfTorso) + "" + booleanToInt(upperLeg2InFrontOfTorso) + "";

    }

    @Override
    public boolean equals(Object o) {
        State s = (State) o;
        int round = 20;

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

    public int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public void doRandomAction() {
        JointAction action = actions.get((int) (Math.random() * actions.size()));

        action.doAction();
    }

    public JointAction getRandomAction() {
        System.out.println("Random action taken");
        return Graphics2D.walker.getState().actions.get((int) (Math.random()*actions.size()));
    }
}
