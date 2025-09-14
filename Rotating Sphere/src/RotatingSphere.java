import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class RotatingSphere extends JPanel implements ActionListener
{
    Timer tm = new Timer(1, this);
    private static final int width = 600;
    private static final int height = 600;
    private final Point[] points;
    private final int numPoints;

    public RotatingSphere()
    {
        numPoints = 1000;
        points = new Point[numPoints];
        double turnFraction = (1 + Math.sqrt(5)) / 2;
        for(int i = 0; i < numPoints; i++)
        {
            double t = i / (numPoints - 1.0);
            double inclination = Math.acos(1 - 2 * t);
            double azimuth = 2 * Math.PI * turnFraction * i;
            double x = 200 * Math.sin(inclination) * Math.cos(azimuth);
            double y = 200 * Math.sin(inclination) * Math.sin(azimuth);
            double z = 200 * Math.cos(inclination);
            points[i] = new Point(x, y, z, rgbNum((int) Math.round(255 * (z + 200)/400), (int) Math.round(255 * (z + 200)/400), (int) Math.round(255 * (z + 200)/400)));
        }
        setBackground(new Color(0, 0, 0));
    }

    public void update()
    {
        for(int i = 0; i < numPoints; i++)
        {
            points[i].rotateX3D(0.1, 0, 0);
            points[i].rotateY3D(0.1, 0, 0);
            points[i].rotateZ3D(0.1, 0, 0);
            points[i].color = rgbNum((int) Math.round(255 * (points[i].z + 200)/400), (int) Math.round(255 * (points[i].z + 200)/400), (int) Math.round(255 * (points[i].z + 200)/400));
        }
    }

    public void draw(Graphics g)
    {
        g.translate(width/2, height/2);
        double[] dist = new double[numPoints];
        int[] indexes = new int[numPoints];
        for(int i = 0; i < numPoints; i++)
        {
            dist[i] = points[i].z;
            indexes[i] = i;
        }
        quickSort(dist, indexes, 0, numPoints - 1);
        for(int i = 0; i < numPoints; i++)
        {
            g.setColor(new Color(points[indexes[i]].color));
            g.fillOval((int) Math.round(points[indexes[i]].x), (int) Math.round(points[indexes[i]].y), 10, 10);
        }
    }

    public void quickSort(double[] list, int[] index, int first, int last)
    {
        int g = first, h = last;
        int midIndex = (first + last) / 2;
        double dividingValue = list[midIndex];
        do
        {
            while(list[g] < dividingValue)
                g++;
            while(list[h] > dividingValue)
                h--;
            if(g <= h)
            {
                double temp = list[g];
                list[g] = list[h];
                list[h] = temp;
                int tempIndex = index[g];
                index[g] = index[h];
                index[h] = tempIndex;
                g++;
                h--;
            }
        }
        while(g < h);
        if(h > first)
            quickSort(list, index, first, h);
        if(g < last)
            quickSort(list, index, g, last);
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        tm.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        update();
        repaint();
    }

    private int rgbNum(int r, int g, int b)
    {
        //gets rgb decimal value from rgb input
        return r * 65536 + g * 256 + b;
    }

    public static void main(String[] args)
    {
        RotatingSphere s = new RotatingSphere();
        JFrame jf = new JFrame();
        jf.setTitle("Rotating Sphere");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(s);
        jf.setVisible(true);
    }
}