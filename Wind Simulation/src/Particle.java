import java.awt.*;

public class Particle {
    Vector2D acceleration;
    Vector2D velocity;
    Vector2D position;
    double timer;
    int size;

    public Particle(Vector2D position) {
        acceleration = new Vector2D(0, 0.0005);
        velocity = new Vector2D(Math.random()*0.2 - 0.1, Math.random()*0.2 - 0.1);
        this.position = position.get();
        timer = 5000;
        size = 15;
    }

    public void run() { update(); }

    public void applyForce(Vector2D force) {
        Vector2D f = force.get();
        f.div(size);
        acceleration.add(f);
    }

    public void update() {
        velocity.add(acceleration);
        position.add(velocity);
        timer -= 1.0;
    }

    public void display(Graphics g) {
        g.setColor(new Color(204, 204, 255, Math.max(0, (int)(255 * timer/10000))));
        g.fillOval((int)position.x - size/2, (int)position.y - size/2, size, size);
        g.setColor(new Color(255, 255, 255, Math.max(0, (int)(255 * timer/10000))));
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.drawOval((int)position.x - size/2, (int)position.y - size/2, size, size);
    }

    public boolean isDead() { return timer < 0 || position.x > 640 || position.y > 480; }
}