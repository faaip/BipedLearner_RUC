package sample;

import org.dyn4j.geometry.Rectangle;

/**
 * Created by frederikjuutilainen on 01/04/15.
 */
public class BalancingBody extends GameObject {

    public BalancingBody()
    {
        Rectangle torsoShape = new Rectangle(1.5, 2.0);
        addFixture(torsoShape);
       setMass();
        translate(2.0, 1.0);
    }

    void jump(){
        getLinearVelocity().set(0, 10.0);
    }

}
