/*
 * Copyright (c) 2010-2014 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *     and the following disclaimer in the documentation and/or other materials provided with the
 *     distribution.
 *   * Neither the name of dyn4j nor the names of its contributors may be used to endorse or
 *     promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

// TODO kommenter gameLoop og initializeWorld

package Rendering_dyn4j;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import sample.*;
import scpsolver.graph.Graph;

/**
 * Class used to show a simple example of using the dyn4j project using
 * Java2D for rendering.
 * <p>
 * This class can be used as a starting point for projects.
 *
 * @author William Bittle
 * @version 3.1.5
 * @since 3.0.0
 */
public class Graphics2D extends JFrame {

    public static BiPedBody walker;
    public static GameObject floor;

    /**
     * The serial version id
     */
    private static final long serialVersionUID = 5663760293144882635L;

    /**
     * The scale 65 pixels per meter
     */
    public static final double SCALE = 65.0;

    /**
     * The conversion factor from nano to base
     */
    public static final double NANO_TO_BASE = 1.0e9;

    /**i
     * Custom Body class to add drawing functionality.
     *
     * @author William Bittle
     * @version 3.0.2
     * @since 3.0.0
     */

    /**
     * The canvas to draw to
     */
    protected Canvas canvas;

    /**
     * The dynamics engine
     */
     public static World world;

    /**
     * Wether the example is stopped or not
     */
    protected boolean stopped;

    /**
     * The time stamp for the last iteration
     */
    protected long last;

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(int simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    private int simulationSpeed;

    /**
     * Default constructor for the window
     */
    public Graphics2D() {
        super("Graphics2D Example");
        // setup the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add a window listener
        this.addWindowListener(new WindowAdapter() {
            /* (non-Javadoc)
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
             */
            @Override
            public void windowClosing(WindowEvent e) {
                // before we stop the JVM stop the example
                stop();
                super.windowClosing(e);
            }
        });

        // create the size of the window
        Dimension size = new Dimension(800, 600);

        // create a canvas to paint to
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);

        // add the canvas to the JFrame
        this.add(this.canvas);

        // make the JFrame not resizable
        // (this way I dont have to worry about resize events)
        this.setResizable(false);

        // size everything
        this.pack();

        // make sure we are not stopped
        this.stopped = false;

        // setup the world
        this.initializeWorld();

        this.setTitle("Machine Learning");

        // show it
        this.setVisible(true);

        // start it
        this.start();
    }

    /**
     * Creates game objects and adds them to the world.
     * <p>
     * Basically the same shapes from the Shapes test in
     * the TestBed.
     */
    public void initializeWorld() {
        // create the world
        this.world = new World();
        world.shiftCoordinates(new Vector2(1000,0)); // TODO TEST

        // create the floor
        floor = new GameObject();
        BodyFixture floorFixture = new BodyFixture(Geometry.createRectangle(15.0, 1.0));
        floorFixture.setFriction(1000.0); // TODO what value
        floor.addFixture(floorFixture);
        floor.setMass(Mass.Type.INFINITE);

        // move the floor down a bit
        floor.translate(0.0, -2.95);
        this.world.addBody(floor);

        if(Main.mode != 0)
        {
            // Add walls
            Rectangle wall1Rect = new Rectangle(1.0, 15.0);
            GameObject wall1 = new GameObject();
            BodyFixture wall1Fixture = new BodyFixture(Geometry.createRectangle(1.0, 15.0));
            wall1.addFixture(wall1Fixture);
            wall1.setMass(Mass.Type.INFINITE);
            wall1.translate(-6,0);

            GameObject wall2 = new GameObject();
            BodyFixture wall2Fixture = new BodyFixture(Geometry.createRectangle(1.0, 15.0));
            wall2.addFixture(wall2Fixture);
            wall2.setMass(Mass.Type.INFINITE);
            wall2.translate(6, 0);

            world.addBody(wall1);
            world.addBody(wall2);
        }

        walker = new BiPedBody();
    }

    public void resetWalker()
    {
        walker = new BiPedBody();

    }

    public void addListener(CollisionListener cl)
    {
        this.world.addListener(cl);
    }


    /**
     * Start active rendering the example.
     * <p>
     * This should be called after the JFrame has been shown.
     */
    public void start() {
        // initialize the last update time
        this.last = System.nanoTime();
        // don't allow AWT to paint the canvas since we are
        this.canvas.setIgnoreRepaint(true);
        // enable double buffering (the JFrame has to be
        // visible before this can be done)
        this.canvas.createBufferStrategy(2);
        // run a separate thread to do active rendering
        // because we don't want to do it on the EDT
        Thread thread = new Thread() {
            public void run() {
                // perform an infinite loop stopped
                // render as fast as possible
                while (!isStopped()) {
                    gameLoop();
                    // you could add a Thread.yield(); or
                    // Thread.sleep(long) here to give the
                    // CPU some breathing room
                }
            }
        };
        // set the game loop thread to a daemon thread so that
        // it cannot stop the JVM from exiting
        thread.setDaemon(true);
        // start the game loop
        thread.start();




    }


    /**
     * The method calling the necessary methods to update
     * the game, graphics, and poll for input.
     */
    protected void gameLoop() {
        // get the graphics object to render to
        java.awt.Graphics2D g = (java.awt.Graphics2D) this.canvas.getBufferStrategy().getDrawGraphics();

        // before we render everything im going to flip the y axis and move the
        // origin to the center (instead of it being in the top left corner)
        AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
        AffineTransform move = AffineTransform.getTranslateInstance(400, -300);

        g.transform(yFlip);
        g.transform(move);

        // now (0, 0) is in the center of the screen with the positive x axis
        // pointing right and the positive y axis pointing up

        // render anything about the Example (will render the World objects)
        this.render(g);

        // dispose of the graphics object
        g.dispose();

        // blit/flip the buffer
        BufferStrategy strategy = this.canvas.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();

        // update the World

        // get the current time
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
        // convert from nanoseconds to seconds
        double elapsedTime = ((double) diff / NANO_TO_BASE);

        // Multiply with simulation speed
        elapsedTime = elapsedTime*simulationSpeed;
        // update the world with the elapsed time
//        this.world.update(elapsedTime, Integer.MAX_VALUE);

        // Sync to threadsync
        synchronized (ThreadSync.lock) {
            this.world.update(elapsedTime, Integer.MAX_VALUE);
        }
    }

    public double getElapsedTime()
    {
        return world.getAccumulatedTime()*simulationSpeed;
    }


    /**
     * Renders the example.
     *
     * @param g the graphics object to render to
     */
    protected void render(java.awt.Graphics2D g) {
        // lets draw over everything with a white background
        g.setColor(Color.WHITE);
        g.fillRect(-400, -300, 800, 600);

        // lets move the view up some
        g.translate(0.0, -1.0 * SCALE);

        // draw all the objects in the world
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            // get the object
            GameObject go = (GameObject) this.world.getBody(i);
            // draw the object
            go.render(g);
        }


    }

    /**
     * Stops the example.
     */
    public synchronized void stop() {
        this.stopped = true;
    }

    /**
     * Returns true if the example is stopped.
     *
     * @return boolean true if stopped
     */
    public synchronized boolean isStopped() {
        return this.stopped;
    }

    /**
     * Entry point for the example application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // set the look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // create the example JFrame
        Graphics2D window = new Graphics2D();
        window.setTitle("Machine Learning");

        // show it
        window.setVisible(true);

        // start it
        window.start();

    }


    public void step(int i) {
        this.world.step(i);
    }

    public double getStepFrequency()
    {
        return this.world.getSettings().getStepFrequency();
    }
}
