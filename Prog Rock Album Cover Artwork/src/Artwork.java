import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Artwork extends JFrame implements Runnable {
    private final int width; //width of screen
    private final int height; //height of screen
    private final Thread thread; //thread of execution for the program
    private boolean running; //true while program is running
    private final BufferedImage image; //allows array of pixels to be drawn to the screen
    private final int[] pixels; //array of pixels on screen (pixel (x, y) = pixels[y * width + x])
    public Artwork() {
        //set size of screen
        width = 640;
        height = 640;

        //what will be displayed to the user
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Progressive Rock Album Cover Art");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //start the program
        start();
    }

    private synchronized void start() {
        //starts game
        running = true;
        thread.start();
    }

    private void update() {
        //updates everything
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double dist = Math.sqrt((x - width / 2.0) * (x - width / 2.0) + (y - height / 2.0) * (y - height / 2.0));
                int color = (int) (256 * 256 * 256 * Math.min(Math.min(Math.sin(Math.exp(x/43.0)), Math.cos(Math.exp(y/47.0))), Math.tan(dist % 50)));
                pixels[y * width + x] = color;
            }
        }
        for (int i = 0; i < 60; i++) {
            int[] copy = new int[width * height];
            for (int y = 0; y < height; y++) {
                if (width >= 0) System.arraycopy(pixels, y * width, copy, y * width, width);
            }
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    int n = 0;
                    if (x > 0 && y > 0)
                    {
                        int color = copy[(y - 1) * width + x - 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (x > 0)
                    {
                        int color = copy[(y) * width + x - 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (x > 0 && y < height - 1)
                    {
                        int color = copy[(y + 1) * width + x - 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (y > 0)
                    {
                        int color = copy[(y - 1) * width + x];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (y < height - 1)
                    {
                        int color = copy[(y + 1) * width + x];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (x < width - 1 && y > 0)
                    {
                        int color = copy[(y - 1) * width + x + 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if(x < width - 1)
                    {
                        int color = copy[(y) * width + x + 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    if (x < width - 1 && y < height - 1)
                    {
                        int color = copy[(y + 1) * width + x + 1];
                        r += R(color);
                        g += G(color);
                        b += B(color);
                        n++;
                    }
                    r /= n;
                    g /= n;
                    b /= n;
                    pixels[y * width + x] = RGB(r, g, b);
                }
            }
        }
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

    public static void main(String [] args)
    {
        new Artwork();
    }
}