import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class WindSimulation extends JFrame implements Runnable
{
    private final int width; //width of screen
    private final int height; //height of screen
    private final Thread thread; //thread of execution for the program
    private boolean running; //true while program is running
    private int frame; //current frame of program

    private final ParticleSystem particleSystem;
    private Vector2D wind;
    private int Wind;

    public WindSimulation()
    {
        //set size of screen
        width = 640;
        height = 480;

        //set initial frame
        frame = 0;

        //what will be displayed to the user
        thread = new Thread(this);

        //particle system setup
        particleSystem = new ParticleSystem(new Vector2D(50, 50));
        wind = new Vector2D(0, 0);
        Wind = 0;

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Wind Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //start the program
        start();
    }

    private synchronized void start()
    {
        //starts program
        running = true;
        thread.start();
    }

    private void update()
    {
        // wind logic
        if(frame % 200 == 0)
            Wind = 1 - Wind;
        if(Wind == 1)
            wind = new Vector2D(0.001, 0);
        else
            wind = new Vector2D(0, 0);

        particleSystem.applyForce(wind);
        particleSystem.addParticle();
    }

    private void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.translate(0, 28);

        g.setColor(new Color(128, 128, 255));
        g.fillRect(0, 0, width, height);

        //draw particles
        particleSystem.run(g);

        //draw wind text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        String windText = (Wind == 1) ? "Wind: ON" : "Wind: OFF";
        g.drawString(windText, 300, 50);

        //display all the graphics
        bs.show();
    }

    public void run()
    {
        //main program loop
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
            frame++;
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

    public static void main(String [] args)
    {
        new WindSimulation();
    }
}