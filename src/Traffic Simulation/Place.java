/**
 * Represents one patch of asphalt on the road
 *
 * @author Casey Hild
 * @version 1
 */
import java.awt.*;
public class Place
{
    private final int row;
    private final int column;
    private Place link1;
    private Place link2;
    private boolean open;

    /**
     * Creates a patch of asphalt at a given location
     * 
     * @param r the row of the place
     * @param c the column of the place
     */
    public Place(int r, int c)
    {
        row = r;
        column = c;
        link1 = null;
        link2 = null;
        open = true;
    }

    /**
     * Checks if a vehicle at this location would be free to move forward
     * 
     * @param direction the direction to move
     * 
     * @return true if the vehicle can move, false otherwise
     */
    public boolean freeToMove(String direction)
    {
        if(direction.equals("N") || direction.equals("S"))
            return link1 == null || link1.isOpen();
        else
            return link2 == null || link2.isOpen();
    }

    /**
     * Returns a link to the next location in the direction specified
     * 
     * @param direction the direction to get hte next location for
     * 
     * @return the Place in that direction
     */
    public Place next(String direction)
    {
        if(direction.equals("N") || direction.equals("S"))
            return link1;
        else
            return link2;
    }

    /**
     * Sets the link to another location in the north or south direction for this place
     * This method is public because the road class needs access to the links but friend classes are not allowed in java
     * 
     * @param p the place to link
     */
    public void setLink1(Place p)
    {
        link1 = p;
    }

    /**
     * Sets the link to another location in the east or west direction for this place
     * This method is public because the road class needs access to the links but friend classes are not allowed in java
     * 
     * @param p the place to link
     */
    public void setLink2(Place p)
    {
        link2 = p;
    }

    /**
     * Checks if this location is open
     * 
     * @return true if it is open, false otherwise
     */
    public boolean isOpen()
    {
        return open;
    }

    /**
     * Blocks this location
     */
    public void block()
    {
        open = false;
    }

    /**
     * Unblocks this location
     */
    public void unblock()
    {
        open = true;
    }

    /**
     * Checks if this location is part of an intersection
     * 
     * @return true if it is, false otherwise
     */
    public boolean intersection()
    {
        return link1 != null && link2 != null;
    }

    /**
     * Returns the row for this location
     * 
     * @return the row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Returns the column for this location
     * 
     * @return the column
     */
    public int getColumn()
    {
        return column;
    }

    /**
     * Displays the visual representation of each patch of asphalt
     * 
     * @param g the Graphics object needed to draw each patch of asphalt
     */
    public void display(Graphics g)
    {
        g.setColor(new Color(128, 128, 128));
        g.fillRect(row * 16, column * 16, 16, 16);
        g.setColor(new Color(64, 64, 64));
        g.drawRect(row * 16, column * 16, 16, 16);
    }
}