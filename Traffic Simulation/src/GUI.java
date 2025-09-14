/**
 * Creates a visual representation of the traffic simulation
 *
 * A lot of this code comes from a simplified version of a GUI template class that I made a while ago to create many visual displays in java
 * I used this class as a starting point to display the simulation for this lab
 * 
 * @author Casey Hild
 * @version 1
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class GUI extends JFrame implements Runnable
{
    private final Thread thread; //thread of execution for the program
    private boolean running; //true while program is running

    private Road northRoad, southRoad, eastRoad, westRoad;
    private VehicleQueue northQueue, southQueue, eastQueue, westQueue;
    private TrafficLight light;

    /**
     * Creates a new GUI and sets up the screen to be displayed
     */
    public GUI()
    {
        //set size of screen
        //width of screen
        int width = 640;
        //height of screen
        int height = 640;

        //setting up the window
        thread = new Thread(this);
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Traffic Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //start the display
        start();
    }

    /**
     * Starts the loop that will display the GUI
     */
    private synchronized void start()
    {
        //starts the display
        running = true;
        thread.start();
    }

    /**
     * Updates the GUI
     * 
     * For this program, updates come from the TrafficSimulation class through the use of the updateTraffic method
     */
    public void update()
    {

    }

    /**
     * Provides the GUI with the information about each road, each queue of vehicles for those roads, and the traffic light
     * 
     * @param northRoad the road going north through the intersection
     * @param southRoad the road going south through the intersection
     * @param eastRoad the road going east through the intersection
     * @param westRoad the road going west through the intersection
     * @param northQueue the queue of vehicles for the north road
     * @param southQueue the queue of vehicles for the south road
     * @param eastQueue the queue of vehicles for the east road
     * @param westQueue the queue of vehicles for the west road
     * @param light the traffic light regulating the intersection
     */
    public void updateTraffic(Road northRoad, Road southRoad, Road eastRoad, Road westRoad, VehicleQueue northQueue, VehicleQueue southQueue, VehicleQueue eastQueue, VehicleQueue westQueue, TrafficLight light)
    {
        this.northRoad = northRoad;
        this.southRoad = southRoad;
        this.eastRoad = eastRoad;
        this.westRoad = westRoad;
        this.northQueue = northQueue;
        this.southQueue = southQueue;
        this.eastQueue = eastQueue;
        this.westQueue = westQueue;
        this.light = light;
    }

    /**
     * Renders all graphics to the screen
     */
    private void render()
    {
        //sets up graphics
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.translate(0, 28);
        northRoad.display(g);
        southRoad.display(g);
        eastRoad.display(g);
        westRoad.display(g);
        northQueue.display(g);
        southQueue.display(g);
        eastQueue.display(g);
        westQueue.display(g);
        light.display(g);

        //display all the graphics
        bs.show();
    }

    /**
     * Main loop for the GUI that updates 60 times per second
     * Note: updates for the traffic simulation are not done in this loop, however rendering the scene is
     */
    public void run()
    {
        //main program loop
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //60 times per second
        double delta = 0;
        requestFocus();
        while(running)
        {
            //updates time
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) //Make sure update is only happening 60 times a second
            {
                //update
                update();
                delta -= 1;
            }
            //display to the screen
            render();
        }
    }
}