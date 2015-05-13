package QLearning;

import sample.BiPedBody;


public class State {

    // This class is for identifying a state

    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;
    private boolean upperLeg1InFrontOfTorso;
    private boolean upperLeg2InFrontOfTorso;

    // -
    boolean exitState = false;
//    List<Action> actions = new ArrayList<>(); // List of actions

    // Constructor in case of new state
    public State(BiPedBody walker) {
        this.torsoLeaningForward = walker.isTorsoLeaningForward();
        this.knee1Forward = walker.isKnee1Forward();
        this.foot1Forward = walker.isFoot1Forward();
        this.foot1OnGround = walker.isFoot1OnGround();
        this.foot2OnGround = walker.isFoot2OnGround();
        this.upperLeg1InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();
        this.upperLeg2InFrontOfTorso = walker.isUpperLeg1InFrontOfTorso();
    }

    public void updateQValue()
    {

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {

        return booleanToInt(torsoLeaningForward)+booleanToInt(knee1Forward)+booleanToInt(foot1Forward)+booleanToInt(foot1OnGround)+booleanToInt(foot2OnGround)+booleanToInt(upperLeg1InFrontOfTorso)+booleanToInt(upperLeg2InFrontOfTorso)+"";

    }

    @Override
    public boolean equals(Object o) {

        return o.toString().equals(this.toString());

    }

    public int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

}
