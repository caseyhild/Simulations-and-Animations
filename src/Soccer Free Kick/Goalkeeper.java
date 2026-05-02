import java.awt.*;

public class Goalkeeper {
    public void draw(Graphics2D g) {
        // shirt
        g.setStroke(new BasicStroke(1));
        g.setColor(new Color(0, 128, 255)); // (0, 255/2, 255)
        Polygon leftShoulder = new Polygon();
        leftShoulder.addPoint(170, 90);
        leftShoulder.addPoint(178, 98);
        leftShoulder.addPoint(154, 122);
        leftShoulder.addPoint(146, 114);
        g.fillPolygon(leftShoulder);

        Polygon rightShoulder = new Polygon();
        rightShoulder.addPoint(210, 90);
        rightShoulder.addPoint(202, 98);
        rightShoulder.addPoint(226, 122);
        rightShoulder.addPoint(234, 114);
        g.fillPolygon(rightShoulder);

        g.fillRect(170, 140, 15, 20);
        g.fillRect(195, 140, 15, 20);
        g.fillRect(170, 90, 40, 50);

        // shorts line
        g.setColor(Color.BLUE);
        g.drawLine(170, 130, 209, 130);

        // shorts
        g.setColor(Color.BLACK);
        Polygon leftShort = new Polygon();
        leftShort.addPoint(185, 160);
        leftShort.addPoint(170, 160);
        leftShort.addPoint(160, 170);
        leftShort.addPoint(185, 170);
        g.fillPolygon(leftShort);

        Polygon rightShort = new Polygon();
        rightShort.addPoint(195, 160);
        rightShort.addPoint(210, 160);
        rightShort.addPoint(220, 170);
        rightShort.addPoint(195, 170);
        g.fillPolygon(rightShort);

        // head
        g.setColor(new Color(255, 204, 128)); // (255, 4*255/5, 255/2)
        g.fillOval(190 - 16, 75 - 16, 32, 32);

        // ears
        g.fillOval(149 - 5, 119 - 5, 10, 10);
        g.fillOval(231 - 5, 119 - 5, 10, 10);

        // eyes white
        g.setColor(Color.WHITE);
        g.fillOval(182 - 3, 70 - 2, 7, 5);
        g.fillOval(198 - 3, 70 - 2, 7, 5);

        // pupils
        g.setColor(Color.BLACK);
        g.fillOval(182 - 1, 71 - 1, 3, 3);
        g.fillOval(198 - 1, 71 - 1, 3, 3);

        // hair
        g.setColor(new Color(128, 51, 0)); // (255/2, 255/5, 0)
        g.setStroke(new BasicStroke(5));
        g.drawArc(190 - 16, 75 - 16, 32, 32, 50, 80);

        // mouth line
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));
        g.drawLine((int)182.5, 80, (int)196.5, 80);

        // eyebrows
        g.setColor(new Color(128, 51, 0));
        g.drawLine(200, 65, 193, 66);
        g.drawLine(180, 65, 187, 66);
    }
}