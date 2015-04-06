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
public class Balancer extends GameObject {

    int score; // Score
    ArrayList<Body> bodyParts = new ArrayList<>();
    ArrayList<Joint> jointList = new ArrayList<>();

    GameObject c = new GameObject(); // Bottom half circle
    GameObject d = new GameObject(); // "Stick"
    GameObject e = new GameObject(); // Control Point
    RevoluteJoint joint1; // Joint connecting stick and bottom part
    RevoluteJoint joint2; // Joint connecting stick and control point


    // Constructor
    public Balancer(World world) {
        //Add physical objects
        d.addFixture(Geometry.createRectangle(0.2, 2.5)); // Stick
        d.setMass();
        world.addBody(d);


        c.addFixture(Geometry.createHalfEllipseAtOrigin(3.0, 1.5)); // Bottom half-circle
        c.rotate(Math.toRadians(180.0));
        c.translate(0.0,-1.7);
        c.setMass();
        world.addBody(c);

        e.addFixture(Geometry.createRectangle(0.4, 0.4)); // Control point (where force is applied)
        e.rotate(Math.toRadians(45.0));
        e.translate(0.0, 1.25);
        e.setMass();
        world.addBody(e);

        // Add Joints
        joint1 = new RevoluteJoint(c, d, new Vector2()); // Joint connecting half-circle and stick
        joint1.setLimitEnabled(true);
        world.addJoint(joint1);

        joint2 = new RevoluteJoint(d, e, new Vector2());
        joint2.setLimitEnabled(true);
        world.addJoint(joint2);

    }

    public void leanRight() {
        e.applyImpulse(-1.0);
        System.out.println("Leaning right");
    }

    public void leanLeft() {
//        d.setLinearVelocity(-1.0,0.0);
        e.applyImpulse(1.0);
        System.out.println("Leaning left");
    }
}
