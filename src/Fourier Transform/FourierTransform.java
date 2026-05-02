import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
public class FourierTransform extends JFrame implements Runnable
{
    private final int width;
    private final int height;
    private int frame;
    private final Thread thread;
    private boolean running;
    private final BufferedImage image;
    private final int[] pixels;

    private double time;
    private double interval;
    private final int numEpicycles;
    private final Complex[] x;
    private final double[] amplitude;
    private final double[] phase;
    private final double[] frequency;

    public FourierTransform()
    {
        //set size of screen
        width = 600;
        height = 600;

        //set initial frame
        frame = 0;

        //what will be displayed to the user
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        int spacing = 10;
        int size = 200;

        time = 0;
        numEpicycles = 4 * size / spacing;
        amplitude = new double[numEpicycles];
        frequency = new double[numEpicycles];
        phase = new double[numEpicycles];

        x = new Complex[numEpicycles];

        for(int i = 0; i < size / spacing; i++)
        {
            x[i] = new Complex(size /2.0, -size /2.0 + spacing * i);
            x[i + size / spacing] = new Complex(size /2.0 - spacing * i, size /2.0);
            x[i + 2 * size / spacing] = new Complex(-size /2.0, size /2.0 - spacing * i);
            x[i + 3 * size / spacing] = new Complex(-size /2.0 + spacing * i, -size /2.0);
        }

        int N = x.length;
        for(int k = 0; k < N; k++)
        {
            Complex sum = new Complex(0, 0);
            for(int n = 0; n < N; n++)
            {
                double phi = (2 * Math.PI * k * n) / N;
                sum.add(Complex.mult(x[n], new Complex(Math.cos(phi), -Math.sin(phi))));
            }
            sum.div(N);
            amplitude[k] = sum.amplitude();
            frequency[k] = k;
            phase[k] = Math.atan2(sum.b, sum.a);
        }
        quickSort(amplitude, frequency, phase, 0, numEpicycles - 1);

        //setting up the window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Fourier Transform");
        setBackground(new Color(0, 0, 0));
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

    public void update()
    {
        if(frame >= 600)
            time += interval;
        if(time > 2 * Math.PI)
            time = 0;
    }

    public void render()
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
        g.translate(width/2,  height/2);

        Vector2D pos = new Vector2D();
        Vector2D prev;
        interval = Math.PI / numEpicycles / 5;
        g.setColor(new Color(255, 0, 0));
        for(int i = 0; i < numEpicycles; i++)
        {
            prev = new Vector2D(pos);
            double theta = frequency[i] * time + phase[i];
            pos.add(new Vector2D(amplitude[i] * Math.cos(theta), amplitude[i] * Math.sin(theta)));
            g.drawOval((int) (prev.x - amplitude[i]), (int) (prev.y - amplitude[i]), (int) (2 * amplitude[i]), (int) (2 * amplitude[i]));
            g.drawLine((int) prev.x, (int) prev.y, (int) pos.x, (int) pos.y);
        }
        pixels[(int) (pos.y + height/2) * width + (int) (pos.x + width/2)] = RGB(255, 255, 255);

        g.setColor(new Color(0, 0, 255));
        for (Complex complex : x) {
            g.fillOval((int) complex.a - 2, (int) complex.b - 2, 4, 4);
        }

        //display all the graphics
        bs.show();
    }
    public void quickSort(double[] amplitude, double[] frequency, double[] phase, int first, int last)
    {
        int g = first, h = last;
        int midIndex = (first + last) / 2;
        double dividingValue = amplitude[midIndex];
        do
        {
            while(amplitude[g] > dividingValue)
                g++;
            while(amplitude[h] < dividingValue)
                h--;
            if(g <= h)
            {
                double temp = amplitude[g];
                amplitude[g] = amplitude[h];
                amplitude[h] = temp;
                double tempFreq = frequency[g];
                frequency[g] = frequency[h];
                frequency[h] = tempFreq;
                double tempPhase = phase[g];
                phase[g] = phase[h];
                phase[h] = tempPhase;
                g++;
                h--;
            }
        }
        while(g < h);
        if(h > first)
            quickSort(amplitude, frequency, phase, first, h);
        if(g < last)
            quickSort(amplitude, frequency, phase, g, last);
    }

    private int RGB(int r, int g, int b)
    {
        return r << 16 | g << 8 | b;
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
        new FourierTransform();
    }
}