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

   public void jump(){
        getLinearVelocity().set(0, 6.0);
    }

    public void jumpRight() {
        getLinearVelocity().set(6.0,0);
    }

    public void jumpLeft() {
        getLinearVelocity().set(-6.0,0);

    }
}
