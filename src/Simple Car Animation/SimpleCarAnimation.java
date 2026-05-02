import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SimpleCarAnimation extends JFrame implements Runnable {
    private final int width;
    private final int height;
    private final Thread thread;
    private boolean running;

    private int x;
    private int speed;

    public SimpleCarAnimation() {
        width = 640;
        height = 480;

        thread = new Thread(this);

        // window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Simple Car Animation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // car
        x = 10;
        speed = 3;

        start();
    }

    public static void main(String[] args) {
        new SimpleCarAnimation();
    }

    private synchronized void start() {
        running = true;
        thread.start();
    }

    private void update() {
        // === Car project logic ===
        if (x >= width) {
            x = 0;
        }
        if (x <= 0) {
            speed = 3;
        }
        x += speed;
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.translate(0, 28);

        // sky
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);

        // ground
        g.setColor(Color.GREEN);
        g.fillRect(0, height / 2, width, height / 2);

        // car body
        g.setColor(Color.RED);
        g.fillRect(x, height / 2 - 15, 50, 10);
        g.fillRect(x + 10, height / 2 - 25, 30, 10);

        // wheels
        g.setColor(Color.BLACK);
        g.fillOval(x + 2, height / 2 - 10, 13, 13);
        g.fillOval(x + 35, height / 2 - 10, 13, 13);

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
}