public class Vector
{
    private double x;
    private double y;
    
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
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
        x += v.getX();
        y += v.getY();
    }

    public void sub(Vector v)
    {
        x -= v.getX();
        y -= v.getY();
    }

    public void mult(double n)
    {
        x *= n;
        y *= n;
    }

    public void div(double n)
    {
        x /= n;
        y /= n;
    }

    public double mag()
    {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize()
    {
        if(mag() > 0)
            div(mag());
    }

    public String toString()
    {
        return x + " " + y;
    }
}