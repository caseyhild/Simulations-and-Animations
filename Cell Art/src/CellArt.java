import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CellArt extends JPanel implements ActionListener
{
    Timer tm = new Timer(1, this);
    private static final int width = 512;
    private static final int height = 512;
    private final int[] xPos;
    private final int[] yPos;
    private final int[] color;
    private final int num;
    private final int[][] closest;

    public CellArt()
    {
        num = 100;
        xPos = new int[num];
        yPos = new int[num];
        color = new int[num];
        for(int i = 0; i < num; i++)
        {
            xPos[i] = (int) (Math.random() * width);
            yPos[i] = (int) (Math.random() * height);
            color[i] = rgbNum((int) (255.0 * xPos[i]/width), (int) (255.0 * yPos[i]/height), ((int) (255.0 * xPos[i]/width) + (int) (255.0 * yPos[i]/height))/2);
        }
        closest = new int[height][width];
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                int[] dist = new int[num];
                for(int i = 0; i < num; i++)
                {
                    dist[i] = (x - xPos[i]) * (x - xPos[i]) + (y - yPos[i]) * (y - yPos[i]);
                }
                int min = 0;
                int minDist = dist[0];
                for(int i = 1; i < num; i++)
                {
                    if(dist[i] < minDist)
                    {
                        minDist = dist[i];
                        min = i;
                    }
                }
                closest[y][x] = min;
            }
        }
        for(int i = 0; i < num; i++)
        {
            g.setColor(new Color(getR(color[i]), getG(color[i]), getB(color[i])));
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    if(closest[y][x] == i)
                        g.drawLine(x, y, x, y);
                }
            }
        }
        tm.start();
    }

    public void actionPerformed(ActionEvent e)
    {

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
                CellArt frame = new CellArt();
                frame.setVisible(true);
            }
        );
        CellArt c = new CellArt();
        JFrame jf = new JFrame();
        jf.setTitle("Cell Art");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(c);
        jf.setVisible(true);
    }
}