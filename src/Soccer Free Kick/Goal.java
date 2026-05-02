import java.awt.*;

public class Goal {
    public void draw(Graphics2D g) {
        // Draw front net
        g.setColor(new Color(160, 160, 160)); // gray lines
        g.setStroke(new BasicStroke(1));
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                int glx = 10 * i + 97;
                int gly = 10 * j + 50;
                g.drawLine(glx + i + 5, 50 - 5 * i / 4, glx + 2, 132 - i);
                g.drawLine(100, gly, 270 - 2 * j, gly - 16);
            }
        }

        // Draw first side net
        for (int i2 = 0; i2 < 5; i2++) {
            for (int j2 = 0; j2 < 10; j2++) {
                int glx2 = 10 * i2 + 50;
                int gly2 = 10 * j2 + 31;
                g.drawLine(5 * glx2 / 9 + 50, 38 + 3 * i2, glx2 + 2, 132 + 5 * i2);
                g.drawLine(78 - 10 * j2 / 3, gly2 + j2 / 2 + 10, 103 - 5 * j2 / 4, gly2 + 20 + j2);
            }
        }

        // Draw second side net (translated)
        for (int i3 = 0; i3 < 5; i3++) {
            for (int j3 = 0; j3 < 10; j3++) {
                int glx3 = 10 * i3 + 50;
                int gly3 = 10 * j3 + 31;

                g.translate(200, -20); // simulate JS translate
                g.drawLine(7 * glx3 / 9 + 30, 53 - i3, glx3 + 2, 132 + 5 * i3);
                g.drawLine(78 - 10 * j3 / 3, gly3 + j3 / 2 + 10, 103 - 5 * j3 / 4, gly3 + 20 + j3);
                g.translate(-200, 20); // reset translation
            }
        }

        // Draw back net
        for (int i4 = 0; i4 < 20; i4++) {
            for (int j4 = 0; j4 < 3; j4++) {
                int glx4 = 10 * i4 + 75;
                int gly4 = 4 * j4 + 41;
                g.drawLine(glx4 + 2, 40 - 11 * i4 / 10, glx4 + 32, 50 - 11 * i4 / 10);
                g.drawLine(75 + 25 / 2 * j4, gly4, 270 + 25 / 2 * j4, gly4 - 20);
            }
        }

        // Draw frame
        g.setStroke(new BasicStroke(5));
        g.setColor(new Color(204, 204, 204));
        g.drawLine(100, 50, 300, 30);
        g.drawLine(100, 50, 95, 155);
        g.drawLine(300, 30, 295, 135);
        g.drawLine(100, 50, 75, 40);
        g.drawLine(300, 30, 275, 20);
        g.drawLine(75, 40, 50, 135);
        g.drawLine(270, 34, 252, 117);
        g.drawLine(95, 155, 50, 135);
        g.drawLine(295, 135, 252, 117);
        g.drawLine(75, 40, 275, 20);
        g.drawLine(97, 132, 252, 117);
    }
}
