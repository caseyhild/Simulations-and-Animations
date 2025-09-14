public class Star
{
    public Vector3D pos;
    public int radius;
    public Vector3D color;

    public Star(Vector3D pos, int radius, Vector3D color)
    {
        this.pos = new Vector3D(pos);
        this.radius = radius;
        this.color = new Vector3D(color);
    }

    public void draw(Vector3D[][] pixels, int width, int height)
    {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                Vector3D p = new Vector3D((x - width / 2.0) / width, (y - height / 2.0) / height, 0);
                p.sub(pos);

                double d = p.mag();
                pixels[y][x].add(Vector3D.mult(color, radius / d / 5000));

                if(d < 0.03)
                {
                    double rays = 0.000001 / Math.abs(p.x) / Math.abs(p.y);
                    pixels[y][x].add(new Vector3D(Vector3D.mult(color, rays)));
                }
            }
        }
    }
}