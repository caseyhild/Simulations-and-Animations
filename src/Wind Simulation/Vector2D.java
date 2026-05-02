public class Vector2D {
    public double x, y;
    public Vector2D(double x, double y) { this.x = x; this.y = y; }
    public Vector2D get() { return new Vector2D(x, y); }
    public void add(Vector2D v) { x += v.x; y += v.y; }
    public void div(double val) { x /= val; y /= val; }
}