package sample;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;

/**
 * Created by frederikjuutilainen on 06/04/15.
 */
public class Balancer extends GameObject { // Is sub-class of GameObject for drawing functions

    int score; // Score

    float topPointDist; // Control points distance from init state

    GameObject c = new GameObject(); // Bottom half circle
    GameObject d = new GameObject(); // "Stick"

    Vector2 eVector = new Vector2();
    GameObject e = new GameObject(); // Control Point
    RevoluteJoint joint1; // Joint connecting stick and bottom part
    RevoluteJoint joint2; // Joint connecting stick and control point

    // Constructor
    public Balancer(World world) {
        //Add physical objects
        d.addFixture(Geometry.createRectangle(0.2, 2.5)); // Stick
        d.setMass();
        world.addBody(d);

//        c.addFixture(Geometry.createHalfEllipseAtOrigin(3.0, 1.5)); // Bottom half-circle
        BodyFixture c1 = new BodyFixture(Geometry.createHalfEllipseAtOrigin(3.0, 1.5));
        c1.setFriction(5.0);
        c.addFixture(c1);
        c.rotate(Math.toRadians(180.0));
        c.translate(0.0, -1.7);
        c.setMass();
        world.addBody(c);

        e.addFixture(Geometry.createRectangle(0.4, 0.4)); // Control point (where force is applied)
        e.rotate(Math.toRadians(45.0));
        eVector.add(0.0,1.25);
        e.translate(eVector);
        e.setMass();
        world.addBody(e);

        // Add Joints
        joint1 = new RevoluteJoint(c, d, new Vector2()); // Joint connecting half-circle and stick
        joint1.setLimitEnabled(true);
        world.addJoint(joint1);

        joint2 = new RevoluteJoint(d, e, new Vector2()); // Joint connecting control point and stick
        joint2.setLimitEnabled(true);
        world.addJoint(joint2);

    }

    public void lean(double v) {
        e.applyImpulse(v); //Applies impulse from input

        // Prints leaning direction and distance from starting coordinates
        if (v > 0)
            System.out.println("Leaning left.  Distance from start point is " + getTopPointDist() + " m");
        else if (v < 0) {
            System.out.println("Leaning right. Distance from start point is " + getTopPointDist() + " m");

        }
    }

    public double getTopPointDist() {
        /* CURRENTLY NOT RETURNING PROPER VALUE */
        return eVector.distance(e.getWorldCenter());
    }
}
