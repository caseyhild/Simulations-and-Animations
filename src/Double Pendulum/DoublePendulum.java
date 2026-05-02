import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class DoublePendulum extends JFrame implements Runnable
{
    private final int width;
    private final int height;
    private final Vector2D origin;
    private final Vector2D pos1;
    private final double m1;
    private final double r1;
    private double a1;
    private double a1_v;
    private double a1_a;
    private final Vector2D pos2;
    private final double m2;
    private final double r2;
    private double a2;
    private double a2_v;
    private double a2_a;
    private final double g;

    private final ArrayList<Vector2D> path;

    private final Thread thread;
    private boolean running;
    private final BufferedImage image;
    private final int[] pixels;

    public DoublePendulum()
    {
        //set size of screen
        width = 640;
        height = 480;

        origin = new Vector2D(width / 2.0, height / 2.0);
        pos1 = new Vector2D();
        m1 = 10;
        r1 = 100;
        a1 = Math.PI/2;
        a1_v = 0;
        a1_a = 0;
        pos2 = new Vector2D();
        m2 = 10;
        r2 = 100;
        a2 = 0;
        a2_v = 0;
        a2_a = 0;
        g = 0.25;
        path = new ArrayList<>();

        //what will be displayed to the user
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Double Pendulum");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //start the DoublePendulum
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
        //updates everything
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                pixels[y * width + x] = RGB(255 * x / width, 0, 0);
            }
        }
        
        double num1 = -g * (2 * m1 + m2) * Math.sin(a1);
        double num2 = -m2 * g * Math.sin(a1 - 2 * a2);
        double num3 = -2 * Math.sin(a1 - a2) * m2;
        double num4 = a2_v * a2_v * r2 + a1_v * a1_v * r1 * Math.cos(a1 - a2);
        double den = r1 * (2 * m1 + m2 - m2 * Math.cos(2 * a1 - 2 * a2));
        a1_a = (num1 + num2 + num3 * num4)/den;
        num1 = 2 * Math.sin(a1 - a2);
        num2 = a1_v * a1_v * r1 * (m1 + m2);
        num3 = g * (m1 + m2) * Math.cos(a1);
        num4 = a2_v * a2_v * r2 * m2 * Math.cos(a1 - a2);
        den = r2 * (2 * m1 + m2 - m2 * Math.cos(2 * a1 - 2 * a2));
        a2_a = num1 * (num2 + num3 + num4) / den;
        
        pos1.x = r1 * Math.sin(a1);
        pos1.y = r1 * Math.cos(a1);
        pos2.x = pos1.x + r2 * Math.sin(a2);
        pos2.y = pos1.y + r2 * Math.cos(a2);
        a1_v += a1_a;
        a2_v += a2_a;
        a1 += a1_v;
        a2 += a2_v;
        path.add(new Vector2D(pos2));
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

        //draws pixel array to screen
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        g.setColor(new Color(0, 128, 128));
        for(int i = 1; i < path.size(); i++)
        {
            g.drawLine((int) (origin.x + path.get(i - 1).x), (int) (origin.y + path.get(i - 1).y), (int) (origin.x + path.get(i).x), (int) (origin.y + path.get(i).y));
        }
        g.setColor(new Color(0, 0, 0));
        g.drawLine((int) (origin.x), (int) (origin.y), (int) (origin.x + pos1.x), (int) (origin.y + pos1.y));
        g.fillOval((int) (origin.x + pos1.x - m1/2), (int) (origin.y + pos1.y - m1/2), (int) m1, (int) m1);
        g.drawLine((int) (origin.x + pos1.x), (int) (origin.y + pos1.y), (int) (origin.x + pos2.x), (int) (origin.y + pos2.y));
        g.fillOval((int) (origin.x + pos2.x - m2/2), (int) (origin.y + pos2.y - m2/2), (int) m2, (int) m2);

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

    private int RGB(int r, int g, int b)
    {
        return r << 16 | g << 8 | b;
    }

    public static void main(String [] args)
    {
        new DoublePendulum();
    }
}