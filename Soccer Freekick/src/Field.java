import java.awt.*;

public class Field {
    public void draw(Graphics2D g, int panelWidth, int panelHeight) {
        // Grass
        g.setColor(new Color(34, 139, 34));
        g.fillRect(0, 0, panelWidth, panelHeight);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));

        // Base line
        g.drawLine(0, panelHeight / 2 - 30, panelWidth, panelHeight / 3 - 5);

        // Penalty box
        g.drawLine(0, panelHeight / 2 + 80, 60, 340);
        g.drawLine(60, 340, panelWidth, 300);

        // Small box
        g.drawLine(25, panelHeight / 2 - 32, 80, 220);
        g.drawLine(80, 220, panelWidth, 180);
        g.drawLine(panelWidth, 160, 367, 133);

        // Penalty spot
        g.fillOval(panelWidth/2 + 100, panelHeight/2 + 25, 12, 8);

        // Arc outside penalty area
        g.drawArc(panelWidth/2 + 15, panelHeight/2 + 88, 400, 50, 20, -180);
    }
}
