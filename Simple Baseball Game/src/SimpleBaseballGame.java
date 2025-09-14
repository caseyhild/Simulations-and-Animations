import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class SimpleBaseballGame extends JFrame implements Runnable, MouseListener, MouseMotionListener {
    private final int width;
    private final int height;
    private final Thread thread;
    private boolean running;

    private final int batX = 160;
    private final int batY = 340;
    private int batWidth = 10;
    private int batHeight = 70;

    private final int ballX = 200;
    private int ballY = 50;
    private final int speed = 5;
    private int ballVelocityY = 0;
    private boolean pitchFlash = false;
    private int pitchFlashTimer = 0;
    private boolean swinging = false;
    private int swingTimer = 0;

    private int score = 0;
    private String message = "";
    private int messageTimer = 0;

    public SimpleBaseballGame() {
        width = 450;
        height = 450;

        thread = new Thread(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        // window
        setSize(width, height + 28);
        setResizable(false);
        setTitle("Simple Baseball Game");
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
        // Handle pitch flash duration
        if (pitchFlash) {
            pitchFlashTimer--;
            if (pitchFlashTimer <= 0) {
                pitchFlash = false;
            }
        }

        // Ball movement
        ballY += ballVelocityY;

        // Reset ball if it goes out of bounds
        if (ballY >= 425 || ballY <= -25) {
            ballY = 50;
            ballVelocityY = 0;
            batWidth = 10;
            batHeight = 70;
        }

        // Swing animation
        if (swinging) {
            swingTimer++;
            if (swingTimer < 10) {
                batWidth = 70;
                batHeight = 10;
            } else if (swingTimer < 20) {
                batWidth = 10;
                batHeight = 70;
            } else {
                swingTimer = 0;
                swinging = false;
            }
        }

        // Collision detection
        Rectangle batRect = new Rectangle(batX, batY - 5, batWidth, batHeight);
        Rectangle ballRect = new Rectangle(ballX - 10, ballY - 10, 20, 20);
        if (batRect.intersects(ballRect) && swinging && ballVelocityY > 0) {
            score++;
            message = "Hit!";
            messageTimer = 60;
            ballVelocityY = -speed; // reverse direction
        }

        // Message timer
        if (messageTimer > 0) {
            messageTimer--;
            if (messageTimer == 0) message = "";
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

        // background
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);

        // home plate
        Polygon home = new Polygon();
        home.addPoint(175, 350);
        home.addPoint(225, 350);
        home.addPoint(225, 375);
        home.addPoint(200, 400);
        home.addPoint(175, 375);
        g.setColor(Color.WHITE);
        g.fillPolygon(home);
        g.setColor(Color.BLACK);
        g.drawPolygon(home);

        // baseball
        g.setColor(Color.WHITE);
        g.fillOval(ballX - 10, ballY - 10, 20, 20);
        g.setColor(Color.RED);
        g.drawArc(ballX + 3,  ballY - 10, 20, 20, 132, 98);
        g.drawArc(ballX - 23, ballY - 10, 20, 20, 312, 98);
        g.setColor(Color.BLACK);
        g.drawOval(ballX - 10, ballY - 10, 20, 20);

        // bat
        g.setColor(new Color(75, 0, 0));
        g.fillRect(batX, batY, batWidth, batHeight);

        // buttons
        int pitchBtnX = width - 120, pitchBtnY = 0;
        int swingBtnX = width - 60,  swingBtnY = 0;

        // Pitch
        g.setColor(pitchFlash ? new Color(0,100,0) : Color.GREEN);
        g.fillRect(pitchBtnX, pitchBtnY, 50, 35);
        g.setColor(Color.BLACK);
        g.drawRect(pitchBtnX, pitchBtnY, 50, 35);
        g.drawString("Pitch", pitchBtnX + 10, pitchBtnY + 23);

        // Swing
        g.setColor(swinging ? new Color(0,100,0) : Color.GREEN);
        g.fillRect(swingBtnX, swingBtnY, 50, 35);
        g.setColor(Color.BLACK);
        g.drawRect(swingBtnX, swingBtnY, 50, 35);
        g.drawString("Swing", swingBtnX + 6, swingBtnY + 23);

        // score + message
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 30);
        if (!message.isEmpty()) {
            g.setColor(Color.RED);
            g.drawString(message, 4 * width / 5, height/4);
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

    public void mouseClicked(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {
        int mx = me.getX() - 1;
        int my = me.getY() - 31;

        // pitch button
        if (mx > width - 120 && mx < width - 70 && my < 35) {
            pitchFlash = true;
            pitchFlashTimer = 10; // flash ~1/6 second at 60fps
            ballY = 50;
            ballVelocityY = speed;
        }

        // swing button
        if (mx > width - 60 && mx < width - 10 && my < 35 && !swinging) {
            swinging = true;
            swingTimer = 0;
        }
    }
    public void mouseReleased(MouseEvent me) {}
    public void mouseDragged(MouseEvent me) {}
    public void mouseMoved(MouseEvent me) {}

    public static void main(String[] args) {
        new SimpleBaseballGame();
    }
}