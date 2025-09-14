import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
    private int x = 200;
    private int y = 350;
    private double ballSpeed = 5;
    private int click = 0;
    private int counter = 0;

    private final Goal goal;
    private final Goalkeeper keeper;
    private final Ball ball;
    private final Field field;

    public GamePanel() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        addMouseListener(this);

        field = new Field();
        goal = new Goal();
        keeper = new Goalkeeper();
        ball = new Ball();

        Timer timer = new Timer(16, this); // ~60fps
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        field.draw(g2, getWidth(), getHeight());
        goal.draw(g2);
        keeper.draw(g2);
        int ballSize = 30;
        ball.draw(g2, x, y, ballSize);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        // Messages
        if (click == 5 && counter >= 40) g2.drawString("Missed!", 180, 240);
        if (click == 4 && counter >= 35) g2.drawString("GOAL!!!", 160, 240);
        if (click == 3 && counter >= 55) g2.drawString("Saved!", 160, 240);
        if (click == 2 && counter >= 35) g2.drawString("GOAL!!!", 160, 240);
        if (click == 1 && counter >= 40) g2.drawString("Missed!", 180, 240);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ball movement logic
        if (click == 5) {
            x += (int) (4 * ballSpeed / 5);
            y -= (int) (4 * ballSpeed / 3);
            counter++;
            if (counter == 100) reset();
        }
        int curve = 10;
        if (click == 4) {
            y -= curve;
            x += (int) (2 * ballSpeed + 0.4);
            ballSpeed = ((y + 25) / 15.0 - 10) / 4.0;
            if (y <= 50) { x = 275; y = 50; }
            counter++;
            if (counter == 100) reset(true);
        }
        if (click == 3) {
            y -= (int) (4 * ballSpeed / 5);
            if (y <= 160) y = 160;
            counter++;
            if (counter == 100) reset();
        }
        if (click == 2) {
            y -= curve;
            x -= (int) (2 * ballSpeed - 0.5);
            ballSpeed = ((y + 25) / 15.0 - 10) / 4.0;
            if (y <= 65) { x = 120; y = 65; }
            counter++;
            if (counter == 100) reset(true);
        }
        if (click == 1) {
            x -= (int) ballSpeed;
            y -= (int) (4 * ballSpeed / 3);
            counter++;
            if (counter == 100) reset();
        }

        repaint();
    }

    private void reset() {
        reset(false);
    }

    private void reset(boolean resetSpeed) {
        x = 200;
        y = 350;
        counter = 0;
        click = 0;
        if (resetSpeed) ballSpeed = 5;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (counter == 0) {
            int mx = e.getX();
            int width = getWidth();
            if (mx < width / 5) click = 1;
            else if (mx < 2 * width / 5) click = 2;
            else if (mx < 3 * width / 5) click = 3;
            else if (mx < 4 * width / 5) click = 4;
            else click = 5;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (counter == 0) {
            int mx = e.getX();
            int width = getWidth();
            if (mx < width / 5) click = 1;
            else if (mx < 2 * width / 5) click = 2;
            else if (mx < 3 * width / 5) click = 3;
            else if (mx < 4 * width / 5) click = 4;
            else click = 5;
        }
    }

    // Unused
    public void mousePressed(MouseEvent e) {
        if (counter == 0) {
            int mx = e.getX();
            int width = getWidth();
            if (mx < width / 5) click = 1;
            else if (mx < 2 * width / 5) click = 2;
            else if (mx < 3 * width / 5) click = 3;
            else if (mx < 4 * width / 5) click = 4;
            else click = 5;
        }
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}
