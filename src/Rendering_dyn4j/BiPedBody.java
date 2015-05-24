package Rendering_dyn4j;

import QLearning.State;
import org.dyn4j.collision.CategoryFilter;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.*;
import sample.Main;

import java.util.ArrayList;

public class BiPedBody {

    World world;

    // Limbs
    public GameObject torso;
    GameObject upperLeg1;
    GameObject upperLeg2;
    GameObject lowerLeg1;
    GameObject lowerLeg2;
    GameObject foot1;
    public GameObject foot2;
    ArrayList<GameObject> limbs = new ArrayList<>();

    // Joints
    public static ArrayList<RevoluteJoint> joints = new ArrayList<>();
    RevoluteJoint hip1;
    RevoluteJoint hip2;
    RevoluteJoint knee1;
    RevoluteJoint knee2;
    RevoluteJoint ankle1;
    RevoluteJoint ankle2;

    // TO-DO : MOVE THIS LIST OF ACTIONS TO ANOTHER CLASS

    Double maxHipTorque = 250.0;
    Double maxKneeTorque = 250.0;
    Double maxAnkleTorque = 150.0;
    Double jointSpeed = 60.0;

    // Categories (for avoiding collision between leg 1 and leg 2)
    CategoryFilter f1 = new CategoryFilter(1, 1);
    CategoryFilter f2 = new CategoryFilter(2, 2);

    // Constructor
    public BiPedBody() {
        this.world = Simulation.world;

        // Torso
        torso = new GameObject();
        torso.setUserData("torso");
        {
            Convex c = Geometry.createRectangle(0.6, 1.0);
            BodyFixture bf = new BodyFixture(c);
            torso.addFixture(bf);
            torso.translate(0, 0);
            torso.setMass(Mass.Type.NORMAL);
        }
        world.addBody(torso);

        // Leg 1
        // Upper leg
        upperLeg1 = new GameObject();
        upperLeg1.setUserData("upper leg 1");
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
        lowerLeg1.setUserData("lower leg 1");
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
        foot1.setUserData("foot 1");
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
        hip1.setLimits(Math.toRadians(-50.0), Math.toRadians(5.0));
        hip1.setReferenceAngle(Math.toRadians(0.0));
        hip1.setMotorEnabled(true);
//        hip1.setMotorSpeed(Math.toRadians(0.0));
        hip1.setMaximumMotorTorque(maxHipTorque);
        hip1.setCollisionAllowed(false);
        hip1.setUserData("hip1");
        world.addJoint(hip1);

        // Knee - TODO set smaller limits
        knee1 = new RevoluteJoint(upperLeg1, lowerLeg1, new Vector2(0.0, -1.4));
        knee1.setLimitEnabled(true);
        knee1.setLimits(Math.toRadians(0.0), Math.toRadians(150.0));
        knee1.setReferenceAngle(Math.toRadians(0.0));
        knee1.setMotorEnabled(true);
//        knee1.setMotorSpeed(Math.toRadians(0.0));
        knee1.setMaximumMotorTorque(maxKneeTorque);
        knee1.setCollisionAllowed(false);
        knee1.setUserData("knee1");
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
        ankle1.setUserData("ankle1");
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
            upperLeg2.setUserData("upper leg 2");

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
            lowerLeg2.setUserData("lower leg 2");
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
            foot2.setUserData("foot2");

        }
        world.addBody(foot2);

        // Joints
        // Hip
        hip2 = new RevoluteJoint(torso, upperLeg2, new Vector2(0.0, -.6));
        hip2.setLimitEnabled(true);
        hip2.setLimits(Math.toRadians(-50.0), Math.toRadians(5.0));
        hip2.setReferenceAngle(Math.toRadians(0.0));
        hip2.setMotorEnabled(true);
        hip2.setMotorSpeed(Math.toRadians(0.0));
        hip2.setMaximumMotorTorque(maxHipTorque);
        hip2.setCollisionAllowed(false);
        hip2.setUserData("hip2");
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
        knee2.setUserData("knee2");
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
        ankle2.setUserData("ankle2");
        world.addJoint(ankle2);

        // Add joints to list
        joints.add(hip1);
        joints.add(hip2);
        joints.add(knee1);
        joints.add(knee2);
        joints.add(ankle1);
        joints.add(ankle2);

        // Add limbs to arraylist
        limbs.add(torso);
        limbs.add(upperLeg1);
        limbs.add(upperLeg2);
        limbs.add(lowerLeg1);
        limbs.add(lowerLeg2);
        limbs.add(foot1);
        limbs.add(foot2);

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
        return (CollisionDetector.cl.collision(Simulation.walker.torso, Simulation.floor)) ||
                (CollisionDetector.cl.collision(Simulation.walker.upperLeg1, Simulation.floor)) ||
                (CollisionDetector.cl.collision(Simulation.walker.upperLeg2, Simulation.floor));
    }

    private boolean feetOnTheGround()
    {
        return (CollisionDetector.cl.collision(Simulation.walker.foot1, Simulation.floor) || (CollisionDetector.cl.collision(Simulation.walker.foot2, Simulation.floor)));
    }

    public double legsChangeSinceLastFrame() {
        return (upperLeg1.getChangeInPosition().x + upperLeg2.getChangeInPosition().x) * 100;
    }


    public double reward() {
        int mode = Main.mode;
        double reward = 0;

        // TODO minus reward

        switch (mode) {
            case 0:

                if ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) > 0) {
                    reward = ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) * 5000 + Simulation.walker.torso.getWorldCenter().x*100 );
                }
                if ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) < 0) {
                    reward = ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) * 1000 +Simulation.walker.torso.getWorldCenter().x*100);
                }
                if (reward == 0) {
                    reward = -10;
                }
                if (Simulation.walker.hasFallen()) {
                    reward = -100;
                }
                break;
            case 1:
                // TODO extra bonus for begge fødder højt (ikke bare summen)
                reward = 1500 + ((Simulation.walker.foot2.getWorldCenter().y+ Simulation.walker.foot1.getWorldCenter().y) * 1000);
                if(!feetOnTheGround()){reward+=1000;}
                System.out.println(reward);
                break;
            case 2:
                reward = Math.toDegrees(Simulation.walker.knee2.getJointAngle());
                System.out.println(reward);

                break;
            case 3:
                reward = Simulation.walker.torso.getWorldCenter().y*1000;
                if (Simulation.walker.hasFallen()) {
                    reward = -1000;
                }

                break;
            case 4:
                if ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) > 0) {
                    reward = ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) * 5000);
                }
                if ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) < 0) {
                    reward = ((Simulation.walker.foot2.getChangeInPosition().x + Simulation.walker.foot1.getChangeInPosition().x) * 1000);
                }
                if (reward == 0) {
                    reward = -10;
                }
                if (Simulation.walker.hasFallen()) {
                    reward = -1000;
                }
                break;


        }
        Main.accumulatedReward += reward;


        return reward;
    }


    public double getRelativeAngle() {
        return (new Vector2(hip2.getAnchor1(), torso.getWorldCenter()).getAngleBetween(new Vector2(1, 0)));
    }

    public void resetPosition() {
        torso.setTransform(new Transform());
        upperLeg1.setTransform(Transform.IDENTITY);
        upperLeg2.setTransform(Transform.IDENTITY);
        lowerLeg1.setTransform(Transform.IDENTITY);
        lowerLeg2.setTransform(Transform.IDENTITY);
        foot1.setTransform(Transform.IDENTITY);
        foot2.setTransform(Transform.IDENTITY);

        // Set bodyparts to initial positions
        torso.translate(0, 0);
        upperLeg1.translate(0, -1.0);
        upperLeg2.translate(0, -1.0);
        lowerLeg1.translate(0, -1.8);
        lowerLeg2.translate(0, -1.8);
        foot1.translate(0.15, -2.3);
        foot2.translate(0.15, -2.3);

        // Clears force
        for (GameObject limb : limbs) {
            limb.clearForce();
            limb.clearTorque();
            limb.clearAccumulatedTorque();
            limb.clearAccumulatedForce();
            limb.setAngularVelocity(0.0);
            limb.setLinearVelocity(0.0, 0.0);
        }
    }

    public boolean isInSight() {
        // Checks if walkers torso is roughly inside the rendering frame
        return (Simulation.walker.torso.getWorldCenter().x > -5.5 && Simulation.walker.torso.getWorldCenter().x < 5.5);
    }
}
