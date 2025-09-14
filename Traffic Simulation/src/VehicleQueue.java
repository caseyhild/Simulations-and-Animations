/**
 * Represents a queue of cars within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.util.*;
import java.awt.*;
public class VehicleQueue
{
    private final Place start;
    private final ArrayList<Vehicle> vehicles;
    private final int vehicleProbability;
    private final String direction;

    /**
     * Creates a queue of vehicles
     * 
     * @param start the location of where the queue starts
     * @param direction the direction that the queue goes
     * @param probability the probability a new vehicles enters each timer unit
     */
    public VehicleQueue(Place start, String direction, int probability)
    {
        this.start = start;
        this.direction = direction;
        vehicles = new ArrayList<>();
        vehicleProbability = probability;
    }

    /**
     * Updates the list of vehicles
     * Adds, removes, and moves vehicles
     */
    public void update()
    {
        for(int i = 0; i < vehicles.size(); i++)
        {
            Vehicle v = vehicles.get(i);
            v.increaseTime();
            if(v.canMove())
            {
                v.move();
                v.moving();
            }
            else
                v.waiting();
            if(v.endOfRoad())
            {
                remove();
                v.setInactive();
                i--;
            }
        }
        if((int) (Math.random() * vehicleProbability) == 0)
        {
            if(start.isOpen() && start.next(direction).isOpen())
            {
                int randomColorNum = (int) (Math.random() * 12);
                Color color = switch (randomColorNum) {
                    case 0 -> new Color(255, 0, 0); //red
                    case 1 -> new Color(0, 255, 0); //green
                    case 2 -> new Color(0, 0, 255); //blue
                    case 3 -> new Color(255, 255, 0); //yellow
                    case 4 -> new Color(255, 0, 255); //magenta
                    case 5 -> new Color(0, 255, 255); //aqua
                    case 6 -> new Color(255, 128, 0); //orange
                    case 7 -> new Color(160, 64, 160); //purple
                    case 8 -> new Color(0, 128, 0); //dark green
                    case 9 -> new Color(128, 0, 0); //dark red
                    case 10 -> new Color(0, 0, 0); //black
                    case 11 -> new Color(255, 255, 255);
                    default -> null; //white
                };
                int vehicleType = (int) (Math.random() * 10);
                if(vehicleType < 6) // 6/10
                    add(new Car(start, direction, color, 1, Math.random() * 3000 + 3000));
                else if(vehicleType < 7) // 1/10
                    add(new Truck(start, direction, color, 1, Math.random() * 15000 + 10000));
                else if(vehicleType < 8) // 1/10
                    add(new Bus(start, direction, color, 1, Math.random() * 15000 + 25000));
                else if(vehicleType < 10) // 2/10
                    add(new Motorcycle(start, direction, color, 1, Math.random() * 300 + 400));
            }
        }
    }

    /**
     * Displays the visual representation of each vehicle in the queue
     * 
     * @param g the Graphics object needed to draw the vehicles
     */
    public void display(Graphics g) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.active)
                vehicle.display(g);
        }
    }

    /**
     * Adds a new vehicle to the queue
     * 
     * @param v the vehicle to add
     */
    private void add(Vehicle v)
    {
        vehicles.add(v);
    }

    /**
     * Removes the first vehicle from the queue
     */
    private void remove()
    {
        vehicles.removeFirst();
    }
    
    /**
     * Returns the number of vehicles in the queue
     * 
     * @return the number of vehicles
     */
    public int numberOfVehicles()
    {
        return vehicles.size();
    }
    
    /**
     * Returns the total time spent on the road by all vehicles in the queue
     * 
     * @return the total time
     */
    public int totalTimeOnRoad()
    {
        int total = 0;
        for(Vehicle v : vehicles)
        {
            total += v.getTotalTimeOnRoad();
        }
        return total;
    }
    
    /**
     * Returns the total time spent waiting by all vehicles in the queue
     * 
     * @return the total time
     */
    public int totalWaitingTime()
    {
        int total = 0;
        for(Vehicle v : vehicles)
        {
            total += v.getWaitingTime();
        }
        return total;
    }
    
    /**
     * Returns the total distance traveled by all vehicles in the queue
     * 
     * @return the total distance
     */
    public int totalDistanceTraveled()
    {
        int total = 0;
        for(Vehicle v : vehicles)
        {
            total += v.getDistanceTraveled();
        }
        return total;
    }
    
    /**
     * Returns the combined speed of all vehicles in the queue
     * 
     * @return the combined speed
     */
    public int combinedSpeed()
    {
        int total = 0;
        for(Vehicle v : vehicles)
        {
            total += (int) v.getSpeed();
        }
        return total;
    }
    
    /**
     * Returns the total weight of all vehicles in the queue
     * 
     * @return the total weight
     */
    public int totalWeight()
    {
        int total = 0;
        for(Vehicle v : vehicles)
        {
            total += (int) v.getWeight();
        }
        return total;
    }
}