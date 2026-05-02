import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Pinwheel extends JPanel implements ActionListener
{
    Timer tm = new Timer(1, this);
    private static final int width = 512;
    private static final int height = 512;
    private int color;
    private int colorNum;
    private final int num;
    private double deg;
    private int frame;

    public Pinwheel()
    {
        num = 4;
        deg = 0;
        frame = 0;
        int[] color = new int[6];
        color[0] = rgbNum(255, 0, 0);
        color[1] = rgbNum(255, 128, 0);
        color[2] = rgbNum(255, 255, 0);
        color[3] = rgbNum(0, 255, 0);
        color[4] = rgbNum(0, 0, 255);
        color[5] = rgbNum(128, 0, 128);
        colorNum = 0;
        this.color = color[colorNum];
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(new Color(0, 128, 255));
        g.fillRect(0, 0, width, height - 50);
        g.setColor(new Color(0, 192, 0));
        g.fillRect(0, height - 50, width, 50);
        int centerX = width/2;
        int centerY = height/2 - 30;
        g.setColor(new Color(0, 0, 0));
        g.fillRect(centerX - 2, centerY, 4, height - centerY - 30);
        for(int i = 0; i < num; i++)
        {
            g.setColor(new Color(getR(color), getG(color), getB(color)));
            int[] x = {centerX, (int) (centerX + width/3 * Math.cos(Math.toRadians(i * 360.0/num + deg + 90))), (int) (centerX + width/6 * Math.cos(Math.toRadians(i * 360.0/num + deg)) - width/6 * Math.sin(Math.toRadians(i * 360.0/num + deg)))};
            int[] y = {centerY, (int) (centerY + height/3 * Math.sin(Math.toRadians(i * 360.0/num + deg + 90))), (int) (centerY + height/6 * Math.sin(Math.toRadians(i * 360.0/num + deg)) + height/6 * Math.cos(Math.toRadians(i * 360.0/num + deg)))};
            g.fillPolygon(x, y, 3);
        }
        for(int i = 0; i < num; i++)
        {
            g.setColor(new Color(getR(color)/2, getG(color)/2, getB(color)/2));
            int[] x = {centerX, (int) (centerX + width/6 * Math.cos(Math.toRadians(i * 360.0/num + deg))), (int) (centerX + width/6 * Math.cos(Math.toRadians(i * 360.0/num + deg)) - width/6 * Math.sin(Math.toRadians(i * 360.0/num + deg)))};
            int[] y = {centerY, (int) (centerY + height/6 * Math.sin(Math.toRadians(i * 360.0/num + deg))), (int) (centerY + height/6 * Math.sin(Math.toRadians(i * 360.0/num + deg)) + height/6 * Math.cos(Math.toRadians(i * 360.0/num + deg)))};
            g.fillPolygon(x, y, 3);
        }
        g.setColor(new Color(0, 0, 0));
        g.fillOval(centerX - 5, centerY - 5, 10, 10);
        tm.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        deg += 0.2;
        frame++;
        if(frame % 1000 == 0)
        {
            int[] color = new int[6];
            color[0] = rgbNum(255, 0, 0);
            color[1] = rgbNum(255, 128, 0);
            color[2] = rgbNum(255, 255, 0);
            color[3] = rgbNum(0, 255, 0);
            color[4] = rgbNum(0, 0, 255);
            color[5] = rgbNum(128, 0, 128);
            colorNum++;
            this.color = color[colorNum % 6];
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
        java.awt.EventQueue.invokeLater(() ->
            {
                Pinwheel frame = new Pinwheel();
                frame.setVisible(true);
            }
        );
        Pinwheel p = new Pinwheel();
        JFrame jf = new JFrame();
        jf.setTitle("Pinwheel");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(p);
        jf.setVisible(true);
    }
}