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

public class Balancer extends GameObject { // Is sub-class of GameObject for drawing functions

    boolean leaningRight; // Which direction it is moving - binary

    GameObject c = new GameObject(); // Bottom half circle
    GameObject d = new GameObject(); // "Stick"

    Vector2 eVector = new Vector2(); // Utility vector
    Vector2 vec2 = new Vector2(); // Vector point connecting stick and bottom part
    GameObject e = new GameObject(); // Control Point
    RevoluteJoint joint1; // Joint connecting stick and bottom part
    RevoluteJoint joint2; // Joint connecting stick and control point

    // Constructor
    public Balancer(World world) {
        //Add physical objects
        d.addFixture(Geometry.createRectangle(0.2, 2.5)); // Stick
        d.setMass();
        world.addBody(d);

        BodyFixture c1 = new BodyFixture(Geometry.createHalfEllipseAtOrigin(3.0, 1.5));
        c1.setFriction(5.0);
        c.addFixture(c1);
        c.rotate(Math.toRadians(180.0));
        c.translate(0.0, -1.7);

        c.setMass();
        world.addBody(c);

        e.addFixture(Geometry.createRectangle(0.4, 0.4)); // Control point (where force is applied)
        e.rotate(Math.toRadians(45.0));
        eVector.add(-1.3369257164839066E-5, 0.3083717045959303);
        e.translate(0.0, 1.25);
        e.setMass();
        world.addBody(e);

        // Add Joints
        joint1 = new RevoluteJoint(c, d, vec2); // Joint connecting half-circle and stick
        joint1.setLimitEnabled(true);
        world.addJoint(joint1);
        joint2 = new RevoluteJoint(d, e, new Vector2()); // Joint connecting control point and stick
        joint2.setLimitEnabled(true);
        world.addJoint(joint2);
    }

    public void lean(double v) {
        e.applyImpulse(v); //Applies impulse from input argument
        System.out.println(getAngle() + " is leaning right: " + isLeaningRight() + " is balanced: " + isBalanced());
    }

    public boolean isLeaningRight() {
        // Returns true if stick is leaning right
        this.leaningRight = (getAngle() > 0);
        return (getAngle() > 0);
    }

    public double getAngle() {
        // Return the angle of the stick in degrees
        return Math.toDegrees(e.getWorldCenter().getAngleBetween(new Vector2(0.0, 1.0)));
    }

    public boolean isBalanced() {
        // Checks if angle is with a set threshold and returns boolean
        double threshold = 1.5;
        return (getAngle() < threshold && getAngle() > -threshold);
    }
    public boolean hasFallen(){
        // Maybe update with collision detection
        return (getAngle()< -122 || getAngle()> 122);

    }
}