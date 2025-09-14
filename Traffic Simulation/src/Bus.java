/**
 * Represents a bus within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class Bus extends Vehicle
{
    private final Color color;
    private Place link1;
    private Place link2;
    private Place link3;
    private Place link4;
    private final String direction;
    
    /**
     * Creates a bus with a location, direction, color, speed, weight, name of school/MBTA, and number of passengers
     * 
     * @param l1 the location of the front of the bus
     * @param direction the direction the bus is moving
     * @param color the color of the bus
     * @param speed the speed of the bus
     * @param weight the weight of the bus
     */
    public Bus(Place l1, String direction, Color color, double speed, double weight)
    {
        link1 = l1;
        link1.block();
        link2 = null;
        link3 = null;
        link4 = null;
        this.direction = direction;
        this.color = color;
        this.speed = speed;
        this.weight = weight;
        totalTimeOnRoad = 0;
        waitingTime = 0;
        distanceTraveled = 0;
        active = true;
    }

    /**
     * Checks if the bus is able to move forward
     * 
     * @return true if it can move, false otherwise
     */
    public boolean canMove()
    {
        return link1.freeToMove(direction) || continueThroughIntersection();
    }
    
    /**
     * Checks if the bus should continue through the intersection even though light is yellow
     * 
     * @return true if it should continue, false otherwise
     */
    public boolean continueThroughIntersection()
    {
        return (link1 != null && link1.intersection()) || (link2 != null && link2.intersection()) || (link3 != null && link3.intersection()) || (link4 != null && link4.intersection());
    }

    /**
     * Moves the bus 1 unit forward
     */
    public void move()
    {
        if(link1 != null)
            link1.unblock();
        if(link2 != null)
            link2.unblock();
        if(link3 != null)
            link3.unblock();
        if(link4 != null)
            link4.unblock();
        link4 = link3;
        link3 = link2;
        link2 = link1;
        link1 = link1.next(direction);
        if(link1 != null)
            link1.block();
        if(link2 != null)
            link2.block();
        if(link3 != null)
            link3.block();
        if(link4 != null)
            link4.block();
    }

    /**
     * Checks if the bus is at the end of the road
     * 
     * @return true if it is at the end of the road, false otherwise
     */
    public boolean endOfRoad()
    {
        return link1 == null;
    }

    /**
     * Displays the visual representation of the bus
     * 
     * @param g the Graphics object needed to draw the bus
     */
    public void display(Graphics g)
    {
        g.setColor(color);
        if(link1 != null)
        {
            if(direction.equals("N"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 + 6, 10, 52);
            if(direction.equals("S"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 - 42, 10, 52);
            if(direction.equals("E"))
                g.fillRect(link1.getRow() * 16 - 42, link1.getColumn() * 16 + 3, 52, 10);
            if(direction.equals("W"))
                g.fillRect(link1.getRow() * 16 + 6, link1.getColumn() * 16 + 3, 52, 10);
        }
    }

    /**
     * Sets the bus to be inactive
     */
    public void setInactive()
    {
        active = false;
        link2.unblock();
        link3.unblock();
        link4.unblock();
    }
}