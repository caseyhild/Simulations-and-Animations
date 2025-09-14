import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ExplorableMap extends JFrame implements Runnable, KeyListener
{
    private final int width;
    private final int height;
    private final Thread thread;
    private boolean running;
    private final int[][] map;
    private final ArrayList<Structure> structures;
    private final boolean[][] explored;

    private int playerX;
    private int playerY;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public ExplorableMap()
    {
        //set size of screen
        width = 512;
        height = 512;

        //what will be displayed to the user
        thread = new Thread(this);

        int size = 256;
        map = new int[size][size];
        structures = new ArrayList<>();
        explored = new boolean[size][size];

        playerX = map[0].length/2;
        playerY = map.length/2;
        left = false;
        right = false;
        up = false;
        down = false;

        double[][] noise = Noise.noise(map.length, map[0].length);

        for(int y = 0; y < map.length; y++)
        {
            for(int x = 0; x < map[y].length; x++)
            {
                if(noise[y][x] > 0.5)
                    map[y][x] = RGB(0, (int) (noise[y][x] * 128), 0);
                else
                    map[y][x] = RGB(0, (int) (128 - noise[y][x] * 128), (int) (255 - noise[y][x] * 255));
                if((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY) < 225)
                    explored[y][x] = true;
            }
        }
        structures.add(new Structure("cross", 10, 10, RGB(255, 255, 0)));
        structures.add(new Structure("plus", 100, 200, RGB(255, 255, 255)));
        structures.add(new Structure("square", 100, 100, RGB(128, 0, 255)));

        // Listen for keyboard input
        addKeyListener(this);

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Explorable Map");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //start the program
        start();
    }

    private synchronized void start()
    {
        //starts game
        running = true;
        thread.start();
    }

    private void update()
    {
        for(int y = 0; y < map.length; y++)
        {
            for(int x = 0; x < map[y].length; x++)
            {
                if((x - playerX) * (x - playerX) + (y - playerY) * (y - playerY) < 225)
                    explored[y][x] = true;
            }
        }
        if(left && playerX > 0)
            playerX--;
        if(right && playerX < map[playerY].length - 1)
            playerX++;
        if(up && playerY > 0)
            playerY--;
        if(down && playerY < map.length - 1)
            playerY++;
        playerX = Math.max(0, Math.min(playerX, map[0].length - 1));
        playerY = Math.max(0, Math.min(playerY, map.length - 1));
    }

    private void render()
    {
        //sets up graphics
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.translate(0, 28);
        
        for(int y = 0; y < map.length; y++)
        {
            for(int x = 0; x < map[y].length; x++)
            {
                if(x == playerX && y == playerY)
                    g.setColor(new Color(255, 0, 0));
                else if(explored[y][x])
                {
                    boolean noStructure = true;
                    for (Structure structure : structures) {
                        for (int sy = 0; sy < structure.map.length; sy++) {
                            for (int sx = 0; sx < structure.map[sy].length; sx++) {
                                if (structure.x + sx == x && structure.y + sy == y && structure.map[sy][sx] != 0) {
                                    g.setColor(new Color(structure.color));
                                    noStructure = false;
                                }
                            }
                        }
                    }
                    if(noStructure)
                        g.setColor(new Color(map[y][x]));
                }
                else
                    g.setColor(new Color(0));
                g.fillRect(width * x / map[y].length, height * y / map.length, width / map[y].length, height / map.length);
            }
        }

        //display all the graphics
        bs.show();
    }

    public void run()
    {
        //main game loop
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //60 times per second
        double delta = 0;
        requestFocus();
        while(running)
        {
            //updates time
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) //Make sure update is only happening 60 times a second
            {
                //update
                update();
                delta--;
            }
            //display to the screen
            render();
        }
    }

    public void keyPressed(KeyEvent key)
    {
        if(key.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
        if(key.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        if(key.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        if(key.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
    }

    public void keyReleased(KeyEvent key)
    {
        if(key.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        if(key.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        if(key.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        if(key.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
    }

    public void keyTyped(KeyEvent key) {}

    private int RGB(int r, int g, int b)
    {
        return r << 16 | g << 8 | b;
    }

    public static void main(String [] args)
    {
        new ExplorableMap();
    }
}