/**
 * Represents a traffic light within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class TrafficLight
{
    private final Place north;
    private final Place east;
    private final Place south;
    private final Place west;
    private final int cycleLength;
    private int index;
    private final String[] stateSequence;

    /**
     * Creates a traffic light with roads going through it going north, south, east, and west
     * 
     * @param n the road going north
     * @param e the road going east
     * @param s the road going south
     * @param w the road going west
     * @param greenDurationNS the duration of the green light in the NS direction
     * @param greenDurationEW the duration of the green light in the EW direction
     */
    public TrafficLight(Place n, Place e, Place s, Place w, int greenDurationNS, int greenDurationEW)
    {
        north = n;
        east = e;
        south = s;
        west = w;
        cycleLength = greenDurationNS + 6 + greenDurationEW + 6;
        stateSequence = new String[cycleLength];
        for(int i = 0; i < greenDurationNS; i++)
        {
            stateSequence[i] = "GR";
        }
        for(int i = 0; i < 6; i++)
        {
            stateSequence[greenDurationNS + i] = "YR";
        }
        for(int i = 0; i < greenDurationEW; i++)
        {
            stateSequence[greenDurationNS + 6 + i] = "RG";
        }
        for(int i = 0; i < 6; i++)
        {
            stateSequence[greenDurationNS + 6 + greenDurationEW + i] = "RY";
        }
        index = 0;
    }

    /**
     * Updates the traffic light by blocking and unblocking the parts of the intersection
     */
    public void update()
    {
        index++;
        index %= cycleLength;
        if(currentState().charAt(0) == 'G')
        {
            north.unblock();
            south.unblock();
            east.block();
            west.block();
        }
        else if(currentState().substring(1).equals("G"))
        {
            east.unblock();
            west.unblock();
            north.block();
            south.block();
        }
        else
        {
            north.block();
            south.block();
            east.block();
            west.block();
        }
    }

    /**
     * Returns the current state of the light
     * 
     * @return the state of the light
     */
    public String currentState()
    {
        return stateSequence[index];
    }

    /**
     * Displays the visual representation of the traffic light
     * 
     * @param g the Graphics object needed to draw the traffic light
     */
    public void display(Graphics g)
    {
        if(currentState().charAt(0) == 'G')
            g.setColor(new Color(0, 255, 0));
        else if(currentState().charAt(0) == 'Y')
            g.setColor(new Color(255, 255, 0));
        else if(currentState().charAt(0) == 'R')
            g.setColor(new Color(255, 0, 0));
        g.fillRect(north.getRow() * 16, north.getColumn() * 16 + 13, 16, 3);
        
        if(currentState().charAt(0) == 'G')
            g.setColor(new Color(0, 255, 0));
        else if(currentState().charAt(0) == 'Y')
            g.setColor(new Color(255, 255, 0));
        else if(currentState().charAt(0) == 'R')
            g.setColor(new Color(255, 0, 0));
        g.fillRect(south.getRow() * 16, south.getColumn() * 16, 16, 3);
        
        if(currentState().substring(1).equals("G"))
            g.setColor(new Color(0, 255, 0));
        else if(currentState().substring(1).equals("Y"))
            g.setColor(new Color(255, 255, 0));
        else if(currentState().substring(1).equals("R"))
            g.setColor(new Color(255, 0, 0));
        g.fillRect(east.getRow() * 16, east.getColumn() * 16, 3, 16);
        
        if(currentState().substring(1).equals("G"))
            g.setColor(new Color(0, 255, 0));
        else if(currentState().substring(1).equals("Y"))
            g.setColor(new Color(255, 255, 0));
        else if(currentState().substring(1).equals("R"))
            g.setColor(new Color(255, 0, 0));
        g.fillRect(west.getRow() * 16 + 13, west.getColumn() * 16, 3, 16);
    }
}