package sample;

import QLearning.JointAction;
import QLearning.State;
import Rendering_dyn4j.GameObject;
import Rendering_dyn4j.Graphics2D;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.*;
import scpsolver.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class BiPedBody extends GameObject{

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
    public static ArrayList<RevoluteJoint> joints = new ArrayList<>();
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
    public BiPedBody() {
        this.world = Graphics2D.world;

        // Torso
        torso = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.6, 1.5);
            BodyFixture bf = new BodyFixture(c);
            torso.addFixture(bf);
            torso.translate(0,0);
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
        hip1.setLimits(Math.toRadians(-50.0), Math.toRadians(60.0));
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
        ankle1.setLimits(Math.toRadians(-15.0), Math.toRadians(15.0));
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
        hip2.setLimits(Math.toRadians(-50.0), Math.toRadians(60.0));
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
        ankle2.setLimits(Math.toRadians(-15.0), Math.toRadians(15.0));
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

    }

    public void setJoint(RevoluteJoint joint, int x) {
        if (!joint.isMotorEnabled()) {
            joint.setMotorEnabled(true);
        }
        switch (x) {
            case -1:
                joint.setMotorSpeed(Math.toRadians(jointSpeed));
                break;
            case 0:
                joint.setMotorSpeed(0);
                break;
            case 1:
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
        return (torso.getWorldCenter().y < - 1.2|| knee1.getAnchor1().y < -1.9 || knee2.getAnchor1().y < -1.9);
    }

    public double legsChangeSinceLastFrame()
    {
        return (upperLeg1.getChangeInPosition().x+upperLeg2.getChangeInPosition().x)*100;
    }


    public double reward() {

        if(Graphics2D.walker.hasFallen()){return -150;}

        double base = 200;

//        double reward = base+((Graphics2D.walker.foot2.getWorldCenter().y * 100));
        double reward = 1+((Graphics2D.walker.foot1.getChangeInPosition().x)+(Graphics2D.walker.foot2.getChangeInPosition().x))*500;

        System.out.println("Reward was: " + reward);
        return reward;
//        return -0.5+(Graphics2D.walker.foot1.getWorldCenter().y);
//        return (-1+(new Vector2(0,0).distance(torso.getWorldCenter().x,torso.getWorldCenter().y)));
//        return -2+(Graphics2D.walker.torso.getChangeInPosition().x);
//        return -2+(Graphics2D.walker.knee1.getAnchor1().x+Graphics2D.walker.knee2.getAnchor1().x+Graphics2D.walker.foot1.getWorldCenter().distance(0,0)+Graphics2D.walker.foot2.getWorldCenter().distance(0,0)+Graphics2D.walker.torso.getWorldCenter().y*2);
//        return (-10+(upperLeg1.getChangeInPosition().x+upperLeg2.getChangeInPosition().x)*100+foot1.getWorldCenter().x+foot2.getWorldCenter().x);
    }

    public double getRelativeAngle() {

//        return torso.getWorldVector(this).getAngleBetween(new Vector2(1,0));

        return (new Vector2(hip2.getAnchor1(),torso.getWorldCenter()).getAngleBetween(new Vector2(1,0)));
    }


    public void printLocation() {
        System.out.println(Graphics2D.walker.torso.getWorldCenter().y);
    }

    public void delete() {
//        for(RevoluteJoint joint : joints)
//        {
//            Graphics2D.world.removeJoint(joint);
//        }
//
//        Graphics2D.world.removeBody(torso);
//        Graphics2D.world.removeBody(upperLeg1);
//        Graphics2D.world.removeBody(upperLeg2);
//        Graphics2D.world.removeBody(lowerLeg1);
//        Graphics2D.world.removeBody(lowerLeg2);
//        Graphics2D.world.removeBody(foot1);
//        Graphics2D.world.removeBody(foot2);


        torso.setTransform(new Transform());
        upperLeg1.setTransform(Transform.IDENTITY);
        upperLeg2.setTransform(Transform.IDENTITY);
        lowerLeg1.setTransform(Transform.IDENTITY);
        lowerLeg2.setTransform(Transform.IDENTITY);
        foot1.setTransform(Transform.IDENTITY);
        foot2.setTransform(Transform.IDENTITY);

        torso.translate(0, 0);
        upperLeg1.translate(0, -1.0);
        upperLeg2.translate(0, -1.0);
        lowerLeg1.translate(0, -1.8);
        lowerLeg2.translate(0, -1.8);
        foot1.translate(0.15, -2.3);
        foot2.translate(0.15, -2.3);

        Graphics2D.walker.clearAccumulatedForce();
        Graphics2D.walker.clearAccumulatedTorque();



    }

    public boolean isInSight() {return Graphics2D.walker.getWorldCenter().x > 6 || Graphics2D.walker.getWorldCenter().x < -6;}
}
