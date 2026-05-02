import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class StarField extends JPanel implements ActionListener
{
    Timer tm = new Timer(1, this);
    private final int width;
    private final int height;

    private final Vector3D[][] pixels;

    private final Star[][] stars;

    public StarField()
    {
        width = 600;
        height = 600;

        pixels = new Vector3D[height][width];
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                pixels[y][x] = new Vector3D();
            }
        }

        stars = new Star[6][6];

        for(int y = 0; y < stars.length; y++)
        {
            for(int x = 0; x < stars[y].length; x++)
            {
                stars[y][x] = new Star(new Vector3D(Math.random() - 0.5, Math.random() - 0.5, 0), 10, new Vector3D(255, 255, 255));
            }
        }

        setBackground(new Color(0, 0, 0));
    }

    public void update()
    {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                pixels[y][x] = new Vector3D();
            }
        }
    }

    public void draw(Graphics g)
    {
        for (Star[] star : stars) {
            for (Star value : star) {
                value.draw(pixels, width, height);
            }
        }
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                pixels[y][x].setMin(new Vector3D());
                pixels[y][x].setMax(new Vector3D(255));
                g.setColor(new Color((int) pixels[y][x].x, (int) pixels[y][x].y, (int) pixels[y][x].z));
                g.drawLine(x, y, x, y);
            }
        }
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

    public static void main(String[] args)
    {
        StarField s = new StarField();
        JFrame jf = new JFrame();
        jf.setTitle("Star Field");
        jf.setSize(s.width, s.height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(s);
        jf.setVisible(true);
    }
}