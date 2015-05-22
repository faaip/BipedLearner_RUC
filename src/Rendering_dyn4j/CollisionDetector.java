package Rendering_dyn4j;

import org.dyn4j.collision.broadphase.DynamicAABBTree;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;


public class CollisionDetector {
   public static org.dyn4j.dynamics.CollisionListener cl = new org.dyn4j.dynamics.CollisionListener() {
       DynamicAABBTree dynamicAABBTree = new DynamicAABBTree(3);
       @Override
        public boolean collision(Body body, Body body1) {
           synchronized (ThreadSync.lock) {
               dynamicAABBTree.add(body);
               dynamicAABBTree.add(body1);
               return dynamicAABBTree.detect(body,body1);
           }
        }

        @Override
        public boolean collision(Body body, BodyFixture bodyFixture, Body body1, BodyFixture bodyFixture1, Penetration penetration) {
            return false;
        }

        @Override
        public boolean collision(Body body, BodyFixture bodyFixture, Body body1, BodyFixture bodyFixture1, Manifold manifold) {
            return false;
        }

        @Override
        public boolean collision(ContactConstraint contactConstraint) {
            return false;
        }};


}
