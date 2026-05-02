public class Block
{
    Vector position;
    Vector velocity;
    Vector acceleration;
    Vector destination;
    Vector accPoint;
    int color;
    public Block(double xPos, double yPos, double xVel, double yVel, double xAcc, double yAcc, double xDest, double yDest, int color)
    {
        position = new Vector(xPos, yPos);
        velocity = new Vector(xVel, yVel);
        acceleration = new Vector(xAcc, yAcc);
        destination = new Vector(xDest, yDest);
        accPoint = new Vector(0, 0);
        this.color = color;
    }

    public void update(int[][] map)
    {
        if(!destination.equals(new Vector(-1, -1)))
        {
            if(velocity.equals(new Vector(0, 0)))
            {
                accPoint = Vector.sub(destination, position);
                accPoint.mult(0.5);
                accPoint.add(position);
            }
            acceleration = Vector.sub(accPoint, position);
            acceleration.mult(0.0001);
            if(Vector.sub(position, destination).mag() <= 0.00025)
            {
                velocity.mult(0);
                acceleration.mult(0);
                accPoint.mult(0);
                position = new Vector(destination);
                map[(int) destination.getY()][(int) destination.getX()] = 0;
                destination = new Vector(-1, -1);
            }
        }
        velocity.add(acceleration);
        position.add(velocity);
    }

    public Vector getPosition()
    {
        return position;
    }

    public Vector getVelocity()
    {
        return velocity;
    }

    public Vector getDestination()
    {
        return destination;
    }

    public int getColor()
    {
        return color;
    }

    public void setDestination(Vector dest)
    {
        destination = dest;
    }
}