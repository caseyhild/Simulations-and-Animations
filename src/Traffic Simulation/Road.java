/**
 * Represents a road within the traffic simulation
 *
 * @author Casey Hild
 * @version 1
 */
import java.util.*;
import java.awt.*;
public class Road
{
    private final ArrayList<Place> places;

    /**
     * Creates a row with a starting location, length, and direction
     * 
     * @param startRow the row of the start of the road
     * @param startColumn the column of the start of the road
     * @param numPlaces the length of the road
     * @param direction the direction vehicles move on the road
     */
    public Road(int startRow, int startColumn, int numPlaces, String direction)
    {
        places = new ArrayList<>();
        for(int i = 0; i < numPlaces; i++)
        {
            switch (direction) {
                case "N" -> places.add(new Place(startRow, startColumn - i));
                case "S" -> places.add(new Place(startRow, startColumn + i));
                case "E" -> places.add(new Place(startRow + i, startColumn));
                case "W" -> places.add(new Place(startRow - i, startColumn));
            }
        }
        if(direction.equals("N") || direction.equals("S"))
        {
            for(int i = 0; i < numPlaces - 1; i++)
            {
                Place place = places.get(i);
                place.setLink1(places.get(i + 1));
            }
            places.get(numPlaces - 1).setLink1(null);
        }
        else
        {
            for(int i = 0; i < numPlaces - 1; i++)
            {
                Place place = places.get(i);
                place.setLink2(places.get(i + 1));
            }
            places.get(numPlaces - 1).setLink2(null);
        }
    }

    /**
     * Returns the Place object at a given index
     * 
     * @param index the index within the road
     * 
     * @return the Place at that index
     */
    public Place getPlace(int index)
    {
        return places.get(index);
    }

    /**
     * Displays the visual representation of the road
     * 
     * @param g the Graphics object needed to draw the road
     */
    public void display(Graphics g)
    {
        for(Place p : places)
        {
            p.display(g);
        }
    }
}