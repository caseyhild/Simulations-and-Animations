public class Point
{
    public double x;
    public double y;
    public double z;
    public int color;
    
    public Point(double x, double y, double z, int color)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }
    
    public void rotateZ3D(double theta, double xPt, double yPt) 
    {
        double sinTheta = Math.sin(Math.toRadians(theta));
        double cosTheta = Math.cos(Math.toRadians(theta));
        double xCopy = x - xPt;
        double yCopy = y - yPt;
        x = xPt + xCopy * cosTheta - yCopy * sinTheta;
        y = yPt + yCopy * cosTheta + xCopy * sinTheta;
    } 

    public void rotateY3D(double theta, double xPt, double zPt)
    { 
        double sinTheta = Math.sin(Math.toRadians(theta));
        double cosTheta = Math.cos(Math.toRadians(theta));
        double xCopy = x - xPt;
        double zCopy = z - zPt;
        x = xPt + xCopy * cosTheta - zCopy * sinTheta;
        z = zPt + zCopy * cosTheta + xCopy * sinTheta;
    } 

    public void rotateX3D(double theta, double yPt, double zPt)
    { 
        double sinTheta = Math.sin(Math.toRadians(theta));
        double cosTheta = Math.cos(Math.toRadians(theta));
        double yCopy = y - yPt;
        double zCopy = z - zPt;
        y = yPt + yCopy * cosTheta - zCopy * sinTheta;
        z = zPt + zCopy * cosTheta + yCopy * sinTheta;
    }
}