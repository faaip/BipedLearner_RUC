package Rendering_dyn4j;

// This class is for ensuring sync in threads between GUI and dyn4j simulation
// This was done with help from William at dyn4j forum

public class ThreadSync {
    public static final Object lock = new Object();
}
