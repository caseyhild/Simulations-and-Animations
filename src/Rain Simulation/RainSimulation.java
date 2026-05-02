import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class RainSimulation extends JFrame implements Runnable {
    private final int width = 640;
    private final int height = 480;
    private final Thread thread;
    private boolean running;

    private final ArrayList<RainDrop> drops;
    private double posX;
    private final double posY;
    private final int groundY;

    public RainSimulation() {
        thread = new Thread(this);

        drops = new ArrayList<>();
        posX = Math.random() * (width - 10) + 5;
        posY = -10;
        groundY = 3 * height / 4;

        setSize(width, height + 28);
        setResizable(false);
        setTitle("Rain Simulation");
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
        drops.add(new RainDrop(new Vector2D(posX, posY), groundY));

        for (int i = drops.size() - 1; i >= 0; i--) {
            RainDrop d = drops.get(i);
            d.run();
            if (d.isDead()) drops.remove(i);
        }

        posX = Math.random() * (width - 10) + 5;
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.translate(0, 28);

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        // Ground
        g.setColor(new Color(10, 10, 10));
        g.fillRect(0, groundY, width, height - groundY);

        // Draw raindrops
        for (RainDrop d : drops)
            d.display(g);

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
        new RainSimulation();
    }
}