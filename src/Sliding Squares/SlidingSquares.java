import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class SlidingSquares extends JPanel implements ActionListener
{
    javax.swing.Timer tm = new javax.swing.Timer(5, this);
    private static final int width = 600;
    private static final int height = 600;
    private int frame;
    private static final int[][] map =
        {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    private final int mapWidth;
    private final int mapHeight;
    private final ArrayList<Integer> colors;
    private final ArrayList<Block> blocks;
    public SlidingSquares()
    {
        frame = 0;
        mapWidth = map[0].length;
        mapHeight = map.length;
        colors = new ArrayList<>();
        colors.add(rgbNum(255, 255, 255));
        colors.add(rgbNum(0, 0, 0));
        colors.add(rgbNum(255, 0, 0));
        colors.add(rgbNum(0, 255, 0));
        colors.add(rgbNum(0, 0, 255));
        colors.add(rgbNum(255, 255, 0));
        colors.add(rgbNum(255, 0, 255));
        colors.add(rgbNum(0, 255, 255));
        colors.add(rgbNum(255, 128, 0));
        colors.add(rgbNum(128, 0, 255));
        colors.add(rgbNum(192, 192, 192));
        blocks = new ArrayList<>();
        for(int i = 0; i < 8; i++)
        {
            boolean getNewDestination;
            do
            {
                blocks.add(new Block((int) (Math.random() * 13 + 1), (int) (Math.random() * 13 + 1), 0, 0, 0, 0, (int) (Math.random() * 13 + 1), (int) (Math.random() * 13 + 1), colors.get(i % (colors.size() - 3) + 2)));
                getNewDestination = false;
                for(int j = 0; j < i; j++)
                {
                    if(blocks.get(i).getDestination().equals(blocks.get(j).getDestination()))
                    {
                        getNewDestination = true;
                        blocks.removeLast();
                        break;
                    }
                }
            }while(getNewDestination);
        }
        for (Block block : blocks) {
            map[(int) block.getDestination().getY()][(int) block.getDestination().getX()] = colors.size() - 1;
        }
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int y = 0; y < map.length; y++)
        {
            for(int x = 0; x < map[y].length; x++)
            {
                g.setColor(new Color(getR(colors.get(map[y][x])), getG(colors.get(map[y][x])), getB(colors.get(map[y][x]))));
                g.fillRect(width/mapWidth * x, height/mapHeight * y, width/mapWidth, height/mapHeight);
                g.setColor(new Color(0, 0, 0));
                g.drawRect(width/mapWidth * x, height/mapHeight * y, width/mapWidth, height/mapHeight);
            }
        }
        for (Block block : blocks) {
            g.setColor(new Color(getR(block.getColor()), getG(block.getColor()), getB(block.getColor())));
            g.fillRect((int) (width / mapWidth * block.getPosition().getX()), (int) (height / mapHeight * block.getPosition().getY()), width / mapWidth, height / mapHeight);
            g.setColor(new Color(0, 0, 0));
            g.drawRect((int) (width / mapWidth * block.getPosition().getX()), (int) (height / mapHeight * block.getPosition().getY()), width / mapWidth, height / mapHeight);
        }
        tm.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        frame++;
        for(int i = 0; i < blocks.size(); i++)
        {
            blocks.get(i).update(map);
            checkBoundaries(blocks.get(i).getPosition(), blocks.get(i).getVelocity());
            if(frame % 500 == 0)
            {
                boolean getNewDestination;
                do
                {
                    blocks.get(i).setDestination(new Vector((int) (Math.random() * 13 + 1), (int) (Math.random() * 13 + 1)));
                    getNewDestination = false;
                    for(int j = 0; j < i; j++)
                    {
                        if(blocks.get(i).getDestination().equals(blocks.get(j).getDestination()))
                            getNewDestination = true;
                    }
                }while(getNewDestination);
                map[(int) blocks.get(i).getDestination().getY()][(int) blocks.get(i).getDestination().getX()] = colors.size() - 1;
            }
        }
        repaint();
    }

    public void checkBoundaries(Vector position, Vector velocity)
    {
        if(position.getX() < 1)
        {
            position.setX(1);
            velocity.setX(0);
        }
        if(position.getX() > mapWidth - 2)
        {
            position.setX(mapWidth - 2);
            velocity.setX(0);
        }
        if(position.getY() < 1)
        {
            position.setY(1);
            velocity.setY(0);
        }
        if(position.getY() > mapHeight - 2)
        {
            position.setY(mapHeight - 2);
            velocity.setY(0);
        }
    }

    private int rgbNum(int r, int g, int b)
    {
        //gets rgb decimal value from rgb input
        return r * 65536 + g * 256 + b;
    }

    private int getR(int color)
    {
        //gets r value from rgb decimal input
        return color/65536;
    }

    private int getG(int color)
    {
        //gets g value from rgb decimal input
        return color % 65536/256;
    }

    private int getB(int color)
    {
        //gets b value from rgb decimal input
        return color % 65536 % 256;
    }

    public static void main(String[] args)
    {
        SlidingSquares c  = new SlidingSquares();
        JFrame jf = new JFrame();
        jf.setTitle("Sliding Squares");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(c);
        jf.setVisible(true);
    }
}