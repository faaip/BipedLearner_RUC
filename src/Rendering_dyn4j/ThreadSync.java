package Rendering_dyn4j;

/*
This class is ensuring sync in threads between GUI and dyn4j simulation. This was done with help from Willaim at the
dyn4j forum.
 */

public class ThreadSync {
    // A new object lock is created making it possible for other methods to sync to this
    public static final Object lock = new Object();


}
