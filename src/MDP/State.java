package MDP;

import sample.BiPedBody;


public class State {
    // State features
    private boolean torsoLeaningForward;
    private boolean knee1Forward;
    private boolean foot1Forward;
    private boolean foot1OnGround;
    private boolean foot2OnGround;

    // -
    boolean exitState = false;
//    List<Action> actions = new ArrayList<>(); // List of actions

    // Constructor in case of new state
    public State(BiPedBody walker) {
        this.torsoLeaningForward = walker.isTorsoLeaningForward();
        this.knee1Forward = walker.isKnee1Forward();
        this.foot1Forward = walker.isFoot1Forward();
        this.foot1OnGround = walker.isFoot1OnGround();
    }

//    public double getQValue()
//    {
//        // Q(s,a) = R(s,a) + decayRate * max (s',a')
//
//        return Qvalue;
//    }

    public void updateQValue()
    {

    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return (torsoLeaningForward +""+knee1Forward + foot1Forward + foot1OnGround + foot2OnGround);
    }

    @Override
    public boolean equals(Object o) {

        return o.toString().equals(this.toString());

    }


}
