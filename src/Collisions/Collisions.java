import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class Collisions extends JPanel implements ActionListener
{
    javax.swing.Timer tm = new javax.swing.Timer(5, this);
    private static final int width = 800;
    private static final int height = 600;
    private final ArrayList<Ball> balls;
    public Collisions()
    {
        balls = new ArrayList<>();
        for(int i = 0; i < 50; i++)
        {
            int shade = (int) (Math.random() * 255);
            balls.add(new Ball(Math.random() * width, Math.random() * height, Math.random() * 2 - 1, Math.random() * 2 - 1, 0, 0, (int) (Math.random() * 40 + 10), rgbNum(shade, shade, shade), width, height));
            balls.get(i).getVelocity().normalize();
        }
        setBackground(new Color(0, 128, 255));
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (Ball ball : balls) {
            g.setColor(new Color(getR(ball.getColor()), getG(ball.getColor()), getB(ball.getColor())));
            g.fillOval((int) (ball.getPosition().getX() - ball.getSize() / 2), (int) (ball.getPosition().getY() - ball.getSize() / 2), ball.getSize(), ball.getSize());
            g.setColor(new Color(0, 0, 0));
            g.drawOval((int) (ball.getPosition().getX() - ball.getSize() / 2), (int) (ball.getPosition().getY() - ball.getSize() / 2), ball.getSize(), ball.getSize());
        }
        tm.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < balls.size(); i++)
        {
            balls.get(i).update();
            for(int j = i + 1; j < balls.size(); j++)
                balls.get(i).checkCollision(balls.get(j));
        }
        repaint();
    }

    private int rgbNum(int r, int g, int b)
    {
        //gets rgb decimal value from rgb input
        return r * 65536 + g * 256 + b;
    }

    private int getR(int color)
    {
        //gets r value from rgb decimal input
        return color/65536;
    }

    private int getG(int color)
    {
        //gets g value from rgb decimal input
        color -= color/65536 * 65536;
        return color/256;
    }

    private int getB(int color)
    {
        //gets b value from rgb decimal input
        color -= color/65536 * 65536;
        color -= color/256 * 256;
        return color;
    }

    public static void main(String[] args)
    {
        Collisions c  = new Collisions();
        JFrame jf = new JFrame();
        jf.setTitle("Collisions");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(c);
        jf.setVisible(true);
    }
}