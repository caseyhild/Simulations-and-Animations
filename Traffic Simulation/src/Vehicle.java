/**
 * Represents a vehicle within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public abstract class Vehicle
{
    protected double speed;
    protected double weight;
    protected int totalTimeOnRoad;
    protected int waitingTime;
    protected int distanceTraveled;
    protected boolean active;
    
    /**
     * Checks if the vehicle is able to move forward
     * 
     * @return true if it can move, false otherwise
     */
    public abstract boolean canMove();
    
    /**
     * Moves the vehicle 1 unit forward
     */
    public abstract void move();
    
    /**
     * Checks if the vehicle is at the end of the road
     * 
     * @return true if it is at the end of the road, false otherwise
     */
    public abstract boolean endOfRoad();
    
    /**
     * Displays the visual representation of the vehicle
     * 
     * @param g the Graphics object needed to draw the vehicle
     */
    public abstract void display(Graphics g);
    
    /**
     * Sets the vehicle to be inactive
     */
    public abstract void setInactive();
    
    /**
     * Increments the time spent on the road to be consistent with the simulation timer
     */
    public void increaseTime()
    {
        totalTimeOnRoad++;
    }
    
    /**
     * Increments the time spent waiting if the vehicle does not move
     */
    public void waiting()
    {
        waitingTime++;
    }
    
    /**
     * Increments the distance traveled if the vehicle is moving
     */
    public void moving()
    {
        distanceTraveled += (int) speed;
    }
    
    /**
     * Returns the total time spent on the road
     * 
     * @return the total time
     */
    public int getTotalTimeOnRoad()
    {
        return totalTimeOnRoad;
    }
    
    /**
     * Returns the total time spent waiting
     * 
     * @return the total time
     */
    public int getWaitingTime()
    {
        return waitingTime;
    }
    
    /**
     * Returns the total distance traveled by the vehicle
     * 
     * @return the total distance
     */
    public int getDistanceTraveled()
    {
        return distanceTraveled;
    }
    
    /**
     * Returns the speed of the vehicle
     * 
     * @return the speed
     */
    public double getSpeed()
    {
        return speed;
    }
    
    /**
     * Returns the weight of the vehicle
     * 
     * @return the weight
     */
    public double getWeight()
    {
        return weight;
    }
}