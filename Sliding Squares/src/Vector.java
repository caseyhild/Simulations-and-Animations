public class Vector
{
    private double x;
    private double y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vector(Vector v)
    {
        x = v.x;
        y = v.y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void add(Vector v)
    {
        x += v.x;
        y += v.y;
    }

    public static Vector sub(Vector v1, Vector v2)
    {
        Vector v = new Vector(0, 0);
        v.setX(v1.x - v2.x);
        v.setY(v1.y - v2.y);
        return v;
    }

    public void mult(double n)
    {
        x *= n;
        y *= n;
    }

    public double mag()
    {
        return Math.sqrt(x * x + y * y);
    }

    public boolean equals(Vector v)
    {
        return x == v.x && y == v.y;
    }

    public String toString()
    {
        return x + " " + y;
    }
}