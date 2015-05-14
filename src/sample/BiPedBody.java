package sample;

import QLearning.JointAction;
import QLearning.State;
import Rendering_dyn4j.GameObject;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

/**
 * Created by frederikjuutilainen on 11/05/15.
 */
public class BiPedBody {

    World world;

    // Limbs

    GameObject torso;
    GameObject upperLeg1;
    GameObject upperLeg2;
    GameObject lowerLeg1;
    GameObject lowerLeg2;
    GameObject foot1;
    GameObject foot2;

    // Joints
    public ArrayList<RevoluteJoint> joints = new ArrayList<>();
    RevoluteJoint hip1;
    RevoluteJoint hip2;
    RevoluteJoint knee1;
    RevoluteJoint knee2;
    RevoluteJoint ankle1;
    RevoluteJoint ankle2;

    // TO-DO : MOVE THIS LIST OF ACTIONS TO ANOTHER CLASS

    Double maxHipTorque = 150.0;
    Double maxKneeTorque = 150.0;
    Double maxAnkleTorque = 70.0;
    Double jointSpeed = 100.0;

    // Categories (for avoiding collision between leg 1 and leg 2)
    CategoryFilter f1 = new CategoryFilter(1, 1);
    CategoryFilter f2 = new CategoryFilter(2, 2);

    // Features for identifying state
    public boolean isFoot1Forward() {
        return (hip1.getJointAngle() + knee1.getJointAngle() < hip2.getJointAngle() + knee2.getJointAngle());
    }

    public boolean isKnee1Forward() {
        return (hip1.getJointAngle() < hip2.getJointAngle());
    }

    public boolean isTorsoLeaningForward() {

        Vector2 measure = new Vector2(hip1.getAnchor1(), new Vector2(0, 1));

        return torso.getWorldVector(measure).getAngleBetween(measure) > 0;
    }

    public boolean isUpperLeg1InFrontOfTorso(){

        return upperLeg1.getWorldCenter().x>torso.getWorldCenter().x;
    }

    public boolean isUpperLeg2InFrontOfTorso(){

        return upperLeg2.getWorldCenter().x>torso.getWorldCenter().x;
    }




    // Constructor
    public BiPedBody(World world) {
        this.world = world;
        // Torso
        torso = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.6, 1.5);
            BodyFixture bf = new BodyFixture(c);
            torso.addFixture(bf);
            torso.setMass(Mass.Type.NORMAL);
        }
        world.addBody(torso);

        // Leg 1
        // Upper leg
        upperLeg1 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.4, 1.05);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f1);
            upperLeg1.addFixture(bf);
            upperLeg1.translate(0, -1.0);
            upperLeg1.setMass(Mass.Type.NORMAL);

        }
        world.addBody(upperLeg1);

        // Lower leg
        lowerLeg1 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.32, 1.05);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f1);
            lowerLeg1.addFixture(bf);
            lowerLeg1.translate(0, -1.8);
            lowerLeg1.setMass(Mass.Type.NORMAL);
        }
        world.addBody(lowerLeg1);

        // Foot
        foot1 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.6, 0.25);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f1);
            foot1.addFixture(bf);
            foot1.translate(0.15, -2.3);
            foot1.setMass(Mass.Type.NORMAL);

        }
        world.addBody(foot1);

        // Joints
        // Hip
        hip1 = new RevoluteJoint(torso, upperLeg1, new Vector2(0.0, -.6));
        hip1.setLimitEnabled(true);
        hip1.setLimits(Math.toRadians(-90.0), Math.toRadians(60.0));
        hip1.setReferenceAngle(Math.toRadians(0.0));
        hip1.setMotorEnabled(true);
//        hip1.setMotorSpeed(Math.toRadians(0.0));
        hip1.setMaximumMotorTorque(maxHipTorque);
        hip1.setCollisionAllowed(false);
        world.addJoint(hip1);

        // Knee
        knee1 = new RevoluteJoint(upperLeg1, lowerLeg1, new Vector2(0.0, -1.4));
        knee1.setLimitEnabled(true);
        knee1.setLimits(Math.toRadians(0.0), Math.toRadians(150.0));
        knee1.setReferenceAngle(Math.toRadians(0.0));
        knee1.setMotorEnabled(true);
//        knee1.setMotorSpeed(Math.toRadians(0.0));
        knee1.setMaximumMotorTorque(maxKneeTorque);
        knee1.setCollisionAllowed(false);
        world.addJoint(knee1);

        // Ankle
        ankle1 = new RevoluteJoint(lowerLeg1, foot1, new Vector2(0, -2.3));
        ankle1.setLimitEnabled(true);
        ankle1.setLimits(Math.toRadians(-35.0), Math.toRadians(35.0));
        ankle1.setReferenceAngle(Math.toRadians(0.0));
        ankle1.setMotorEnabled(true);
//        ankle1.setMotorSpeed(Math.toRadians(0.0));
        ankle1.setMaximumMotorTorque(maxAnkleTorque);
        ankle1.setCollisionAllowed(false);
        world.addJoint(ankle1);

        // Leg 2
        // Upper leg
        upperLeg2 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.4, 1.05);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f2);
            upperLeg2.addFixture(bf);
            upperLeg2.translate(0, -1.0);
            upperLeg2.setMass(Mass.Type.NORMAL);

        }
        world.addBody(upperLeg2);

        // Lower leg
        lowerLeg2 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.32, 1.05);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f2);
            lowerLeg2.addFixture(bf);
            lowerLeg2.translate(0, -1.8);
            lowerLeg2.setMass(Mass.Type.NORMAL);
        }
        world.addBody(lowerLeg2);

        // Foot
        foot2 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.6, 0.25);
            BodyFixture bf = new BodyFixture(c);
            bf.setFilter(f2);
            foot2.addFixture(bf);
            foot2.translate(0.15, -2.3);
            foot2.setMass(Mass.Type.NORMAL);

        }
        world.addBody(foot2);

        // Joints
        // Hip
        hip2 = new RevoluteJoint(torso, upperLeg2, new Vector2(0.0, -.6));
        hip2.setLimitEnabled(true);
        hip2.setLimits(Math.toRadians(-90.0), Math.toRadians(60.0));
        hip2.setReferenceAngle(Math.toRadians(0.0));
        hip2.setMotorEnabled(true);
        hip2.setMotorSpeed(Math.toRadians(0.0));
        hip2.setMaximumMotorTorque(maxHipTorque);
        hip2.setCollisionAllowed(false);
        world.addJoint(hip2);

        // Knee
        knee2 = new RevoluteJoint(upperLeg2, lowerLeg2, new Vector2(0.0, -1.4));
        knee2.setLimitEnabled(true);
        knee2.setLimits(Math.toRadians(0.0), Math.toRadians(150.0));
        knee2.setReferenceAngle(Math.toRadians(0.0));
        knee2.setMotorEnabled(true);
        knee2.setMotorSpeed(Math.toRadians(0.0));
        knee2.setMaximumMotorTorque(maxKneeTorque);
        knee2.setCollisionAllowed(false);
        world.addJoint(knee2);

        // Ankle
        ankle2 = new RevoluteJoint(lowerLeg2, foot2, new Vector2(0, -2.3));
        ankle2.setLimitEnabled(true);
        ankle2.setLimits(Math.toRadians(-35.0), Math.toRadians(35.0));
        ankle2.setReferenceAngle(Math.toRadians(0.0));
        ankle2.setMotorEnabled(true);
        ankle2.setMotorSpeed(Math.toRadians(0.0));
        ankle2.setMaximumMotorTorque(maxAnkleTorque);
        ankle2.setCollisionAllowed(false);
        world.addJoint(ankle2);

        // Add joints to list
        joints.add(hip1);
        joints.add(hip2);
        joints.add(knee1);
        joints.add(knee2);
        joints.add(ankle1);
        joints.add(ankle2);

        // Create actions
        for(RevoluteJoint joint : joints){
            State.actions.add(new JointAction(joint));
            for (int i = -1; i <= 1; i++) {
                State.actions.add(new JointAction(joint,1));
            }

        }

    }

    public void setJoint(RevoluteJoint joint, int x) {
        if (!joint.isMotorEnabled()) {
            joint.setMotorEnabled(true);
        }
        switch (x) {
            case 1:
                joint.setMotorSpeed(Math.toRadians(jointSpeed));
                break;
            case 0:
                joint.setMotorSpeed(0);
                break;
            case -1:
                joint.setMotorSpeed(Math.toRadians(-jointSpeed));
                break;
        }
    }

    public void relaxJoint(RevoluteJoint joint) {
        if (joint.isMotorEnabled()) {
            joint.setMotorEnabled(false);
        }
    }

    public boolean isFoot1OnGround() {
        return foot1.getWorldCenter().y < -3.37;
    }

    public boolean isFoot2OnGround() {
        return foot2.getWorldCenter().y < -3.37;
    }

    public State getState() {
        return new State(this);
    }

    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public boolean hasFallen() {
        return (torso.getWorldCenter().y < - 1.7|| knee1.getAnchor1().y < -1.9 || knee2.getAnchor1().y < -1.9);
    }

    public double torsoChangeSinceLastFrame()
    {
        return torso.getChangeInPosition().x*100;
    }


}
