import java.awt.*;
import java.util.ArrayList;

public class ParticleSystem {
    Vector2D origin;
    ArrayList<Particle> particles;

    public ParticleSystem(Vector2D origin) {
        this.origin = origin.get();
        particles = new ArrayList<>();
    }

    public void addParticle() { particles.add(new Particle(origin)); }

    public void applyForce(Vector2D f) { for(Particle p : particles) p.applyForce(f); }

    public void run(Graphics g) {
        for(int i = particles.size()-1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.run();
            p.display(g);
            if(p.isDead()) particles.remove(i);
        }
    }
}