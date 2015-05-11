package sample;

import Rendering_dyn4j.GameObject;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

/**
 * Created by frederikjuutilainen on 11/05/15.
 */
public class BiPedBody {

    // Limbs
    GameObject torso;
    GameObject upperLeg1;
    GameObject upperLeg2;
    GameObject lowerLeg1;
    GameObject lowerLeg2;
    GameObject foot1;
    GameObject foot2;
    GameObject util;

    // Joints
    RevoluteJoint hip1;
    RevoluteJoint hip2;
    RevoluteJoint knee1;
    RevoluteJoint knee2;
    RevoluteJoint ankle1;
    RevoluteJoint ankle2;


    public BiPedBody(World world)
    {
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
            foot1.addFixture(bf);
            foot1.translate(0.15, -2.3);
            foot1.setMass(Mass.Type.NORMAL);

        }
        world.addBody(foot1);

    // Joints
        // Hip
        hip1 = new RevoluteJoint(torso, upperLeg1, new Vector2(0.0, -.6));
        hip1.setLimitEnabled(false);
        hip1.setLimits(Math.toRadians(-60.0), Math.toRadians(90.0));
        hip1.setReferenceAngle(Math.toRadians(0.0));
        hip1.setMotorEnabled(false);
        hip1.setMotorSpeed(Math.toRadians(0.0));
        hip1.setMaximumMotorTorque(0.0);
        hip1.setCollisionAllowed(false);
        world.addJoint(hip1);

        // Knee
        knee1 = new RevoluteJoint(upperLeg1, lowerLeg1, new Vector2(0.0, -1.4));
        knee1.setLimitEnabled(true);
        knee1.setLimits(Math.toRadians(0.0), Math.toRadians(150.0));
        knee1.setReferenceAngle(Math.toRadians(0.0));
        knee1.setMotorEnabled(false);
        knee1.setMotorSpeed(Math.toRadians(0.0));
        knee1.setMaximumMotorTorque(0.0);
        knee1.setCollisionAllowed(false);
        world.addJoint(knee1);

        // Ankle
        ankle1 = new RevoluteJoint(lowerLeg1, foot1, new Vector2(0, -2.3));
        ankle1.setLimitEnabled(true);
        ankle1.setLimits(Math.toRadians(-35.0), Math.toRadians(35.0));
        ankle1.setReferenceAngle(Math.toRadians(0.0));
        ankle1.setMotorEnabled(false);
        ankle1.setMotorSpeed(Math.toRadians(0.0));
        ankle1.setMaximumMotorTorque(0.0);
        ankle1.setCollisionAllowed(false);
        world.addJoint(ankle1);

    // Leg 2
        // Upper leg
        upperLeg2 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.4, 1.05);
            BodyFixture bf = new BodyFixture(c);
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
            foot2.addFixture(bf);
            foot2.translate(0.15, -2.3);
            foot2.setMass(Mass.Type.NORMAL);

        }
        world.addBody(foot2);

        // Joints
        // Hip
        hip2 = new RevoluteJoint(torso, upperLeg2, new Vector2(0.0, -.6));
        hip2.setLimitEnabled(false);
        hip2.setLimits(Math.toRadians(-60.0), Math.toRadians(90.0));
        hip2.setReferenceAngle(Math.toRadians(0.0));
        hip2.setMotorEnabled(false);
        hip2.setMotorSpeed(Math.toRadians(0.0));
        hip2.setMaximumMotorTorque(0.0);
        hip2.setCollisionAllowed(false);
        world.addJoint(hip2);

        // Knee
        knee2 = new RevoluteJoint(upperLeg2, lowerLeg2, new Vector2(0.0, -1.4));
        knee2.setLimitEnabled(true);
        knee2.setLimits(Math.toRadians(0.0), Math.toRadians(150.0));
        knee2.setReferenceAngle(Math.toRadians(0.0));
        knee2.setMotorEnabled(false);
        knee2.setMotorSpeed(Math.toRadians(0.0));
        knee2.setMaximumMotorTorque(0.0);
        knee2.setCollisionAllowed(false);
        world.addJoint(knee2);

        // Ankle
        ankle2 = new RevoluteJoint(lowerLeg2, foot2, new Vector2(0, -2.3));
        ankle2.setLimitEnabled(true);
        ankle2.setLimits(Math.toRadians(-35.0), Math.toRadians(35.0));
        ankle2.setReferenceAngle(Math.toRadians(0.0));
        ankle2.setMotorEnabled(false);
        ankle2.setMotorSpeed(Math.toRadians(0.0));
        ankle2.setMaximumMotorTorque(0.0);
        ankle2.setCollisionAllowed(false);
        world.addJoint(ankle2);







        // Util
        util = new GameObject();
        {// Fixture4
            Convex c = Geometry.createCircle(0.10);
            BodyFixture bf = new BodyFixture(c);
            util.addFixture(bf);
            util.translate(new Vector2(0.0, -.6));
        }
        world.addBody(util);


    }


}
