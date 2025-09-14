/**
 * Represents a truck within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class Truck extends Vehicle
{
    private final Color color;
    private Place link1;
    private Place link2;
    private Place link3;
    private Place link4;
    private Place link5;
    private final String direction;

    /**
     * Creates a truck with a location, direction, color, speed, weight, trailer weight, and destination
     * 
     * @param l1 the location of the front of the truck
     * @param direction the direction the truck is moving
     * @param color the color of the truck
     * @param speed the speed of the truck
     * @param weight the weight of the truck
     */
    public Truck(Place l1, String direction, Color color, double speed, double weight)
    {
        link1 = l1;
        link1.block();
        link2 = null;
        link3 = null;
        link4 = null;
        link5 = null;
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
     * Checks if the truck is able to move forward
     * 
     * @return true if it can move, false otherwise
     */
    public boolean canMove()
    {
        return link1.freeToMove(direction) || continueThroughIntersection();
    }
    
    /**
     * Checks if the truck should continue through the intersection even though light is yellow
     * 
     * @return true if it should continue, false otherwise
     */
    public boolean continueThroughIntersection()
    {
        return (link1 != null && link1.intersection()) || (link2 != null && link2.intersection()) || (link3 != null && link3.intersection()) || (link4 != null && link4.intersection()) || (link5 != null && link5.intersection());
    }

    /**
     * Moves the truck 1 unit forward
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
        if(link5 != null)
            link5.unblock();
        link5 = link4;
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
        if(link5 != null)
            link5.block();
    }

    /**
     * Checks if the truck is at the end of the road
     * 
     * @return true if it is at the end of the road, false otherwise
     */
    public boolean endOfRoad()
    {
        return link1 == null;
    }

    /**
     * Displays the visual representation of the truck
     * 
     * @param g the Graphics object needed to draw the truck
     */
    public void display(Graphics g)
    {
        g.setColor(color);
        if(link1 != null)
        {
            if(direction.equals("N"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 + 6, 10, 68);
            if(direction.equals("S"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 - 58, 10, 68);
            if(direction.equals("E"))
                g.fillRect(link1.getRow() * 16 - 58, link1.getColumn() * 16 + 3, 68, 10);
            if(direction.equals("W"))
                g.fillRect(link1.getRow() * 16 + 6, link1.getColumn() * 16 + 3, 68, 10);
        }
    }

    /**
     * Sets the truck to be inactive
     */
    public void setInactive()
    {
        active = false;
        link2.unblock();
        link3.unblock();
        link4.unblock();
        link5.unblock();
    }
}