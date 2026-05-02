/**
 * Represents a motorcycle within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class Motorcycle extends Vehicle
{
    private final Color color;
    private Place link;
    private final String direction;

    /**
     * Creates a motorcycle with a location, direction, color, speed, weight, and brand name
     *
     * @param direction the direction the motorcycle is moving
     * @param color the color of the motorcycle
     * @param speed the speed of the motorcycle
     * @param weight the weight of the motorcycle
     */
    public Motorcycle(Place l, String direction, Color color, double speed, double weight)
    {
        link = l;
        link.block();
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
     * Checks if the motorcycle is able to move forward
     * 
     * @return true if it can move, false otherwise
     */
    public boolean canMove()
    {
        return link.freeToMove(direction) || continueThroughIntersection();
    }
    
    /**
     * Checks if the motorcycle should continue through the intersection even though light is yellow
     * 
     * @return true if it should continue, false otherwise
     */
    public boolean continueThroughIntersection()
    {
        return (link != null && link.intersection());
    }

    /**
     * Moves the motorcycle 1 unit forward
     */
    public void move()
    {
        if(link != null)
            link.unblock();
        assert link != null;
        link = link.next(direction);
        if(link != null)
            link.block();
    }

    /**
     * Checks if the motorcycle is at the end of the road
     * 
     * @return true if it is at the end of the road, false otherwise
     */
    public boolean endOfRoad()
    {
        return link == null;
    }

    /**
     * Displays the visual representation of the motorcycle
     * 
     * @param g the Graphics object needed to draw the motorcycle
     */
    public void display(Graphics g)
    {
        g.setColor(color);
        if(link != null)
        {
            if(direction.equals("N"))
                g.fillRect(link.getRow() * 16 + 3, link.getColumn() * 16 + 3, 10, 10);
            if(direction.equals("S"))
                g.fillRect(link.getRow() * 16 + 3, link.getColumn() * 16 + 3, 10, 10);
            if(direction.equals("E"))
                g.fillRect(link.getRow() * 16 + 3, link.getColumn() * 16 + 3, 10, 10);
            if(direction.equals("W"))
                g.fillRect(link.getRow() * 16 + 3, link.getColumn() * 16 + 3, 10, 10);
        }
    }
    
    /**
     * Sets the motorcycle to be inactive
     */
    public void setInactive()
    {
        active = false;
    }
}