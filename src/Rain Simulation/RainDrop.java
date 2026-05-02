import java.awt.Color;
import java.awt.Graphics;

public class RainDrop {
    Vector2D acceleration;
    Vector2D velocity;
    Vector2D position;
    int sizex, sizey;
    double timer;
    double depth;
    Color color;
    int groundY;

    public RainDrop(Vector2D position, int groundY) {
        this.position = position.get();
        this.acceleration = new Vector2D(0, 0.05);
        this.velocity = new Vector2D(0, 0);
        this.sizex = 10;
        this.sizey = 10;
        this.timer = 300;
        this.depth = Math.random() * 80;
        int blue = 128 + (int)(Math.random() * 128);
        this.color = new Color(0, 0, blue);
        this.groundY = groundY;
    }

    public void run() {
        velocity.add(acceleration);
        position.add(velocity);
        timer -= 2;
    }

    public void display(Graphics g) {
        if (position.y >= groundY + depth) {
            position.y = groundY + depth;
            sizex = 25; // splash
        }
        g.setColor(color);
        g.fillOval((int)position.x - sizex / 2, (int)position.y - sizey / 2, sizex, sizey);
    }

    public boolean isDead() {
        return timer < 0;
    }
}