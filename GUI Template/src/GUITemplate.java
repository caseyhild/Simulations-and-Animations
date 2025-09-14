import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GUITemplate extends JFrame implements Runnable, MouseListener, MouseMotionListener, KeyListener
{
    private final int width; //width of screen
    private final int height; //height of screen
    private final Thread thread; //thread of execution for the program
    private final BufferedImage image; //allows array of pixels to be drawn to the screen
    private final int[] pixels; //array of pixels on screen (pixel (x, y) = pixels[y * width + x])
    private boolean running; //true while program is running
    private int frame; //current frame of program
    private int mouseX; //x coordinate of mouse
    private int mouseY; //y coordinate of mouse
    private boolean mousePressed; //whether mouse is being pressed (still or moving)
    private boolean keyPressed; //whether any key is being pressed
    private boolean keyReleased; //true immediately after key is released
    private boolean keyTyped; //true if key is pressed and a valid unicode character is generated
    private KeyEvent key; //key currently pressed (or last one pressed if none are pressed)

    private final TextBox textBox1;
    private final TextBox textBox2;

    public GUITemplate()
    {
        //set size of screen
        width = 640;
        height = 480;

        //set initial frame
        frame = 0;

        //what will be displayed to the user
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        //adding any other program-specific information

        //creating 2 text boxes
        textBox1 = new TextBox();
        textBox2 = new TextBox(width/2, 2 * height/3, 12, 12, "Enter Your Name:");

        //keyboard input for text boxes
        addKeyListener(textBox1);
        addKeyListener(textBox2);

        //mouse input for text boxes
        addMouseListener(textBox1);
        addMouseMotionListener(textBox1);
        addMouseListener(textBox2);
        addMouseMotionListener(textBox2);

        //keyboard input
        keyPressed = false;
        keyReleased = false;
        keyTyped = false;
        key = new KeyEvent(new JFrame(), 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
        addKeyListener(this);

        //mouse input
        mouseX = 0;
        mouseY = 0;
        mousePressed = false;
        addMouseListener(this);
        addMouseMotionListener(this);

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("GUI Template");
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

    private synchronized void stop()
    {
        //stops game
        running = false;
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            System.exit(1);
        }
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

        //reset key states
        if(keyReleased)
            keyReleased = false;
        if(keyTyped)
            keyTyped = false;
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

        //draws text boxes
        textBox1.render(frame, g);
        textBox2.render(frame, g);

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

    private int RGB(int r, int g, int b)
    {
        return r << 16 | g << 8 | b;
    }

    private int R(int color)
    {
        return color >> 16;
    }

    private int G(int color)
    {
        return color >> 8 & 255;
    }

    private int B(int color)
    {
        return color & 255;
    }

    public void mouseClicked(MouseEvent me)
    {
        
    }

    public void mouseEntered(MouseEvent me)
    {

    }

    public void mouseExited(MouseEvent me)
    {

    }

    public void mousePressed(MouseEvent me)
    {
        mousePressed = true;
    }

    public void mouseReleased(MouseEvent me)
    {
        mousePressed = false;
    }

    public void mouseDragged(MouseEvent me)
    {
        mousePressed = true;
        mouseX = me.getX() - 1;
        mouseY = me.getY() - 31;
    }

    public void mouseMoved(MouseEvent me)
    {
        mousePressed = false;
        mouseX = me.getX() - 1;
        mouseY = me.getY() - 31;
    }

    public void keyPressed(KeyEvent key)
    {
        keyPressed = !keyTyped;
        this.key = key;
    }

    public void keyReleased(KeyEvent key)
    {
        keyPressed = false;
        keyReleased = true;
        this.key = key;
    }

    public void keyTyped(KeyEvent key)
    {
        keyTyped = true;
    }

    public static void main(String [] args)
    {
        GUITemplate g = new GUITemplate();
    }
}