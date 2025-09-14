public class Vector3D
{
    public double x;
    public double y;
    public double z;

    public Vector3D()
    {
        x = 0; 
        y = 0;
        z = 0;
    }

    public Vector3D(double a)
    {
        this.x = a;
        this.y = a;
        this.z = a;
    }
    
    public Vector3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D(Vector3D v)
    {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }
    
    public void setZ(double z)
    {
        this.z = z;
    }

    public void add(Vector3D v)
    {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void sub(Vector3D v)
    {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public static Vector3D mult(Vector3D v1, double n)
    {
        Vector3D v = new Vector3D();
        v.setX(v1.x * n);
        v.setY(v1.y * n);
        v.setZ(v1.z * n);
        return v;
    }

    public double mag()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }
    public void min(Vector3D v)
    {
        x = Math.min(x, v.x);
        y = Math.min(y, v.y);
        z = Math.min(z, v.z);
    }

    public void setMin(Vector3D v)
    {
        max(v);
    }
    
    public void max(Vector3D v)
    {
        x = Math.max(x, v.x);
        y = Math.max(y, v.y);
        z = Math.max(z, v.z);
    }
    
    public void setMax(Vector3D v)
    {
        min(v);
    }

    public String toString()
    {
        return x + " " + y + " " + z;
    }
}