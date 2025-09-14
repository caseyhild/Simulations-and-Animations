/**
 * Represents a car within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class Car extends Vehicle
{
    private final Color color;
    private Place link1;
    private Place link2;
    private final String direction;

    /**
     * Creates a car with a location, direction, color, speed, and weight
     * 
     * @param l1 the location of the front half of the car
     * @param direction the direction the car is moving
     * @param color the color of the car
     * @param speed the speed of the car
     * @param weight the weight of the car
     */
    public Car(Place l1, String direction, Color color, double speed, double weight)
    {
        link1 = l1;
        link1.block();
        link2 = null;
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
     * Checks if the car is able to move forward
     * 
     * @return true if it can move, false otherwise
     */
    public boolean canMove()
    {
        return link1.freeToMove(direction) || continueThroughIntersection();
    }

    /**
     * Checks if the car should continue through the intersection even though light is yellow
     * 
     * @return true if it should continue, false otherwise
     */
    public boolean continueThroughIntersection()
    {
        return (link1 != null && link1.intersection()) || (link2 != null && link2.intersection());
    }

    /**
     * Moves the car 1 unit forward
     */
    public void move()
    {
        if(link1 != null)
            link1.unblock();
        if(link2 != null)
            link2.unblock();
        link2 = link1;
        assert link1 != null;
        link1 = link1.next(direction);
        if(link1 != null)
            link1.block();
        if(link2 != null)
            link2.block();
    }

    /**
     * Checks if the car is at the end of the road
     * 
     * @return true if it is at the end of the road, false otherwise
     */
    public boolean endOfRoad()
    {
        return link1 == null;
    }

    /**
     * Displays the visual representation of the car
     * 
     * @param g the Graphics object needed to draw the car
     */
    public void display(Graphics g)
    {
        g.setColor(color);
        if(link1 != null)
        {
            if(direction.equals("N"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 + 6, 10, 20);
            if(direction.equals("S"))
                g.fillRect(link1.getRow() * 16 + 3, link1.getColumn() * 16 - 10, 10, 20);
            if(direction.equals("E"))
                g.fillRect(link1.getRow() * 16 - 10, link1.getColumn() * 16 + 3, 20, 10);
            if(direction.equals("W"))
                g.fillRect(link1.getRow() * 16 + 6, link1.getColumn() * 16 + 3, 20, 10);
        }
    }

    /**
     * Sets the car to be inactive
     */
    public void setInactive()
    {
        active = false;
        link2.unblock();
    }
}