package sample;

import Rendering_dyn4j.GameObject;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

public class BiPedBodyStupid {

    // Limbs
    GameObject torso = new GameObject();

    // Joints
    RevoluteJoint joint10;


    public BiPedBodyStupid(World world) {

        // Torso
        {// Fixture2
            Convex c = Geometry.createRectangle(0.5,1.8);
            BodyFixture bf = new BodyFixture(c);
            torso.addFixture(bf);
        }
        torso.setMass(Mass.Type.NORMAL);
        world.addBody(torso);



        // Body model from dyn4j forum

        // Head
        GameObject body2 = new GameObject();
        {// Fixture2
            Convex c = Geometry.createCircle(0.25);
            BodyFixture bf = new BodyFixture(c);
            body2.addFixture(bf);
        }
        body2.setMass(Mass.Type.NORMAL);
        world.addBody(body2);

        // Torso
        GameObject body3 = new GameObject();
        {// Fixture4
            Convex c = Geometry.createRectangle(0.5, 1.0);
            BodyFixture bf = new BodyFixture(c);
            body3.addFixture(bf);
        }
        {// Fixture16
            Convex c = Geometry.createRectangle(1.0, 0.25);
            c.translate(new Vector2(0.00390625, 0.375));
            BodyFixture bf = new BodyFixture(c);
            body3.addFixture(bf);
        }
        body3.translate(new Vector2(0.0234375, -0.8125));
        body3.setMass(Mass.Type.NORMAL);
        world.addBody(body3);

        // Right Humerus
        GameObject body4 = new GameObject();
        {// Fixture5
            Convex c = Geometry.createRectangle(0.25, 0.5);
            BodyFixture bf = new BodyFixture(c);
            body4.addFixture(bf);
        }
        body4.translate(new Vector2(0.4375, -0.609375));
        body4.setMass(Mass.Type.NORMAL);
        world.addBody(body4);

        // Right Ulna
        GameObject body5 = new GameObject();
        {// Fixture6
            Convex c = Geometry.createRectangle(0.25, 0.4);
            BodyFixture bf = new BodyFixture(c);
            body5.addFixture(bf);
        }
        body5.translate(new Vector2(0.44140625, -0.98828125));
        body5.setMass(Mass.Type.NORMAL);
        world.addBody(body5);

        // Neck
        GameObject body6 = new GameObject();
        {// Fixture7
            Convex c = Geometry.createRectangle(0.15, 0.2);
            BodyFixture bf = new BodyFixture(c);
            body6.addFixture(bf);
        }
        body6.translate(new Vector2(0.015625, -0.2734375));
        body6.setMass(Mass.Type.NORMAL);
        world.addBody(body6);

        // Left Humerus
        GameObject body7 = new GameObject();
        {// Fixture9
            Convex c = Geometry.createRectangle(0.25, 0.5);
            BodyFixture bf = new BodyFixture(c);
            body7.addFixture(bf);
        }
        body7.translate(new Vector2(-0.3828125, -0.609375));
        body7.setMass(Mass.Type.NORMAL);
        world.addBody(body7);

        // Left Ulna
        GameObject body8 = new GameObject();
        {// Fixture11
            Convex c = Geometry.createRectangle(0.25, 0.4);
            BodyFixture bf = new BodyFixture(c);
            body8.addFixture(bf);
        }
        body8.translate(new Vector2(-0.3828125, -0.9765625));
        body8.setMass(Mass.Type.NORMAL);
        world.addBody(body8);

        // Right Femur
        GameObject body9 = new GameObject();
        {// Fixture12
            Convex c = Geometry.createRectangle(0.25, 0.75);
            BodyFixture bf = new BodyFixture(c);
            body9.addFixture(bf);
        }
        body9.translate(new Vector2(0.1796875, -1.5703125));
        body9.setMass(Mass.Type.NORMAL);
        world.addBody(body9);

        // Left Femur
        GameObject body10 = new GameObject();
        {// Fixture13
            Convex c = Geometry.createRectangle(0.25, 0.75);
            BodyFixture bf = new BodyFixture(c);
            body10.addFixture(bf);
        }
        body10.translate(new Vector2(-0.1328125, -1.5703125));
        body10.setMass(Mass.Type.NORMAL);
        world.addBody(body10);

        // Right Tibia
        GameObject body11 = new GameObject();
        {// Fixture14
            Convex c = Geometry.createRectangle(0.25, 0.5);
            BodyFixture bf = new BodyFixture(c);
            body11.addFixture(bf);
        }
        body11.translate(new Vector2(0.18359375, -2.11328125));
        body11.setMass(Mass.Type.NORMAL);
        world.addBody(body11);

        // Left Tibia
        GameObject body12 = new GameObject();
        {// Fixture15
            Convex c = Geometry.createRectangle(0.25, 0.5);
            BodyFixture bf = new BodyFixture(c);
            body12.addFixture(bf);
        }
        body12.translate(new Vector2(-0.1328125, -2.1171875));
        body12.setMass(Mass.Type.NORMAL);
        world.addBody(body12);

        // Head to Neck
        RevoluteJoint joint1 = new RevoluteJoint(body2, body6, new Vector2(0.01, -0.2));
        joint1.setLimitEnabled(false);
        joint1.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint1.setReferenceAngle(Math.toRadians(0.0));
        joint1.setMotorEnabled(false);
        joint1.setMotorSpeed(Math.toRadians(0.0));
        joint1.setMaximumMotorTorque(0.0);
        joint1.setCollisionAllowed(false);
        world.addJoint(joint1);
        // Neck to Torso
        RevoluteJoint joint2 = new RevoluteJoint(body6, body3, new Vector2(0.01, -0.35));
        joint2.setLimitEnabled(false);
        joint2.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint2.setReferenceAngle(Math.toRadians(0.0));
        joint2.setMotorEnabled(false);
        joint2.setMotorSpeed(Math.toRadians(0.0));
        joint2.setMaximumMotorTorque(0.0);
        joint2.setCollisionAllowed(false);
        world.addJoint(joint2);
        // Torso to Left Humerus
        RevoluteJoint joint3 = new RevoluteJoint(body3, body7, new Vector2(-0.4, -0.4));
        joint3.setLimitEnabled(false);
        joint3.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint3.setReferenceAngle(Math.toRadians(0.0));
        joint3.setMotorEnabled(false);
        joint3.setMotorSpeed(Math.toRadians(0.0));
        joint3.setMaximumMotorTorque(0.0);
        joint3.setCollisionAllowed(false);
        world.addJoint(joint3);
        // Torso to Right Humerus
        RevoluteJoint joint4 = new RevoluteJoint(body3, body4, new Vector2(0.4, -0.4));
        joint4.setLimitEnabled(false);
        joint4.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint4.setReferenceAngle(Math.toRadians(0.0));
        joint4.setMotorEnabled(false);
        joint4.setMotorSpeed(Math.toRadians(0.0));
        joint4.setMaximumMotorTorque(0.0);
        joint4.setCollisionAllowed(false);
        world.addJoint(joint4);
        // Right Humerus to Right Ulna
        RevoluteJoint joint5 = new RevoluteJoint(body4, body5, new Vector2(0.43, -0.82));
        joint5.setLimitEnabled(false);
        joint5.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint5.setReferenceAngle(Math.toRadians(0.0));
        joint5.setMotorEnabled(false);
        joint5.setMotorSpeed(Math.toRadians(0.0));
        joint5.setMaximumMotorTorque(0.0);
        joint5.setCollisionAllowed(false);
        world.addJoint(joint5);
        // Left Humerus to Left Ulna
        RevoluteJoint joint6 = new RevoluteJoint(body7, body8, new Vector2(-0.4, -0.81));
        joint6.setLimitEnabled(false);
        joint6.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint6.setReferenceAngle(Math.toRadians(0.0));
        joint6.setMotorEnabled(false);
        joint6.setMotorSpeed(Math.toRadians(0.0));
        joint6.setMaximumMotorTorque(0.0);
        joint6.setCollisionAllowed(false);
        world.addJoint(joint6);
        // Torso to Right Femur
        RevoluteJoint joint7 = new RevoluteJoint(body3, body9, new Vector2(0.16, -1.25));
        joint7.setLimitEnabled(false);
        joint7.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint7.setReferenceAngle(Math.toRadians(0.0));
        joint7.setMotorEnabled(false);
        joint7.setMotorSpeed(Math.toRadians(0.0));
        joint7.setMaximumMotorTorque(0.0);
        joint7.setCollisionAllowed(false);
        world.addJoint(joint7);
        // Torso to Left Femur
        RevoluteJoint joint8 = new RevoluteJoint(body3, body10, new Vector2(-0.13, -1.25));
        joint8.setLimitEnabled(false);
        joint8.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint8.setReferenceAngle(Math.toRadians(0.0));
        joint8.setMotorEnabled(false);
        joint8.setMotorSpeed(Math.toRadians(0.0));
        joint8.setMaximumMotorTorque(0.0);
        joint8.setCollisionAllowed(false);
        world.addJoint(joint8);
        // Right Femur to Right Tibia
        RevoluteJoint joint9 = new RevoluteJoint(body9, body11, new Vector2(0.17, -1.9));
        joint9.setLimitEnabled(false);
        joint9.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint9.setReferenceAngle(Math.toRadians(0.0));
        joint9.setMotorEnabled(false);
        joint9.setMotorSpeed(Math.toRadians(0.0));
        joint9.setMaximumMotorTorque(0.0);
        joint9.setCollisionAllowed(false);
        world.addJoint(joint9);
        // Left Femur to Left Tibia
        joint10 = new RevoluteJoint(body10, body12, new Vector2(-0.14, -1.9));
        joint10.setLimitEnabled(false);
        joint10.setLimits(Math.toRadians(0.0), Math.toRadians(0.0));
        joint10.setReferenceAngle(Math.toRadians(0.0));
        joint10.setMotorEnabled(false);
        joint10.setMotorSpeed(Math.toRadians(0.0));
        joint10.setMaximumMotorTorque(0.0);
        joint10.setCollisionAllowed(false);
        world.addJoint(joint10);


    }


}
