import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;

public class RandomSquares extends JFrame implements Runnable {
    private final int width; //width of screen
    private final int height; //height of screen
    private final Thread thread; //thread of execution for the program
    private final BufferedImage image; //allows array of pixels to be drawn to the screen
    private final int[] pixels; //array of pixels on screen (pixel (x, y) = pixels[y * width + x])
    private boolean running; //true while program is running
    private final int gridSize = 80;   // grid is 80x80
    private final int cellSize = 5;    // each cell is 5x5 pixels
    private final int numSquares = 10; // number of colors
    private final int[] squares;       // grid of ints
    private final int[] amount;        // counts per color
    private final ArrayList<Color> colors;

    private final Random rand;

    public RandomSquares() {
        width = gridSize * cellSize;
        height = gridSize * cellSize;
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        rand = new Random();

        // setup colors
        colors = new ArrayList<>();
        colors.add(new Color(0, 0, 0));       // black
        colors.add(new Color(255, 0, 0));     // red
        colors.add(new Color(0, 255, 0));     // green
        colors.add(new Color(0, 0, 255));     // blue
        colors.add(new Color(255, 255, 0));   // yellow
        colors.add(new Color(255, 0, 255));   // pink
        colors.add(new Color(0, 255, 255));   // turquoise
        colors.add(new Color(255, 128, 0));   // orange
        colors.add(new Color(192, 0, 255));   // purple
        colors.add(new Color(128, 128, 128)); // gray
        colors.add(new Color(64, 64, 64));    // dark gray

        // setup grid
        squares = new int[gridSize * gridSize];
        amount = new int[numSquares];

        for (int i = 1; i <= numSquares; i++) {
            int x, y;
            do {
                x = rand.nextInt(gridSize);
                y = rand.nextInt(gridSize);
            } while (squares[y * gridSize + x] != 0);
            squares[y * gridSize + x] = i;
        }

        // window
        setSize(width, height + 220);
        setResizable(false);
        setTitle("Random Squares");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        start();
    }

    private synchronized void start() {
        running = true;
        thread.start();
    }

    private void update() {
        // reset counts
        for (int i = 0; i < numSquares; i++) {
            amount[i] = 0;
        }

        // spread colors randomly
        for (int i = 0; i < squares.length; i++) {
            int up = (i >= gridSize) ? squares[i - gridSize] : 0;
            int down = (i < squares.length - gridSize) ? squares[i + gridSize] : 0;
            int left = (i % gridSize != 0) ? squares[i - 1] : 0;
            int right = (i % gridSize != gridSize - 1) ? squares[i + 1] : 0;

            int r = rand.nextInt(5);
            if (r == 1 && up != 0) squares[i] = up;
            else if (r == 2 && down != 0) squares[i] = down;
            else if (r == 3 && left != 0) squares[i] = left;
            else if (r == 4 && right != 0) squares[i] = right;
        }

        // update counts
        for (int value : squares) {
            if (value > 0) amount[value - 1]++;
        }

        // draw to pixel array
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                int idx = squares[y * gridSize + x];
                Color c = colors.get(idx);
                for (int yy = 0; yy < cellSize; yy++) {
                    for (int xx = 0; xx < cellSize; xx++) {
                        int px = (y * cellSize + yy) * width + (x * cellSize + xx);
                        pixels[px] = c.getRGB();
                    }
                }
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.translate(0, 28);

        // draw image
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

        // stats below grid

        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, height, width, height + 220);

        g.setColor(new Color(255, 255, 255));
        g.drawLine(0, height + 1, width, height + 1);
        g.drawString("Squares of Each Color Remaining:", 10, height + 20);

        int y = height + 40;
        Color[] labels = colors.subList(1, Math.min(colors.size(), 11)).toArray(new Color[0]);
        String[] names = {"Red", "Green", "Blue", "Yellow", "Pink", "Cyan", "Orange", "Purple", "Gray", "Dark Gray"};
        for (int i = 0; i < numSquares; i++) {
            g.setColor(labels[i]);
            g.drawString(names[i] + ": " + amount[i], 10, y);
            y += 15;
        }

        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            render();
        }
    }

    public static void main(String[] args) {
        new RandomSquares();
    }
}