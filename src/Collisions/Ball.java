public class Ball
{
    private final int width;
    private final int height;
    private final Vector position;
    private final Vector velocity;
    private final Vector acceleration;
    private final int size;
    private final int color;

    public Ball(double xPos, double yPos, double xVel, double yVel, double xAcc, double yAcc, int size, int color, int width, int height)
    {
        position = new Vector(xPos, yPos);
        velocity = new Vector(xVel, yVel);
        acceleration = new Vector(xAcc, yAcc);
        this.size = size;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public void update()
    {
        position.add(velocity);
        velocity.add(acceleration);
        checkBoundaries();
    }

    public void checkBoundaries()
    {
        if(position.getX() < size/2.0)
        {
            position.setX(size/2.0);
            velocity.setX(-velocity.getX());
        }
        if(position.getX() > width - size/2.0)
        {
            position.setX(width - size/2.0);
            velocity.setX(-velocity.getX());
        }
        if(position.getY() < size/2.0)
        {
            position.setY(size/2.0);
            velocity.setY(-velocity.getY());
        }
        if(position.getY() > height - size/2.0)
        {
            position.setY(height - size/2.0);
            velocity.setY(-velocity.getY());
        }
    }

    public void checkCollision(Ball b)
    {
        double dist = Math.sqrt((position.getX() - b.getPosition().getX()) * (position.getX() - b.getPosition().getX()) + (position.getY() - b.getPosition().getY()) * (position.getY() - b.getPosition().getY()));
        if(dist <= size/2.0 + b.getSize()/2.0)
        {
            doCollision(b);
            separate(b, dist);
        }
    }

    public void separate(Ball b, double dist)
    {
        double amt = (size/2.0 + b.getSize()/2.0 - dist)/2;
        double angle = Math.asin((position.getY() - b.getPosition().getY())/dist);
        if(position.getX() - b.getPosition().getX() < 0)
            angle = Math.PI - angle;
        else if(position.getX() - b.getPosition().getX() > 0 && position.getY() - b.getPosition().getY() < 0)
            angle += 2 * Math.PI;
        Vector dir = new Vector(Math.cos(angle), Math.sin(angle));
        dir.normalize();
        dir.mult(amt);
        position.add(dir);
        b.getPosition().sub(dir);
    }

    public void doCollision(Ball b)
    {
        double v1 = velocity.mag();
        double v2 = b.getVelocity().mag();
        double v1x = velocity.getX();
        double v2x = b.getVelocity().getX();
        double v1y = velocity.getY();
        double v2y = b.getVelocity().getY();
        double dx = b.getPosition().getX() - position.getX();
        double dy = b.getPosition().getY() - position.getY();
        double angle;
        if(Math.abs(dx) <= 0.000001)
            angle = Math.PI/2;
        else
            angle = Math.atan(dy/dx);
        double angle1 = findAngle(v1x, v1y) * Math.PI/180;
        double angle2 = findAngle(v2x, v2y) * Math.PI/180;
        double v1xr = v1 * Math.cos(angle1 - angle);
        double v1yr = v1 * Math.sin(angle1 - angle);
        double v2xr = v2 * Math.cos(angle2 - angle);
        double v2yr = v2 * Math.sin(angle2 - angle);
        double v1xrf = ((size - b.getSize()) * v1xr + 2 * b.getSize() * v2xr)/(size + b.getSize());
        double v2xrf = (2 * size * v1xr + (b.getSize() - size) * v2xr)/(size + b.getSize());
        double v1xf = Math.cos(angle) * v1xrf + Math.cos(angle + Math.PI/2) * v1yr;
        double v1yf = Math.sin(angle) * v1xrf + Math.sin(angle + Math.PI/2) * v1yr;
        double v2xf = Math.cos(angle) * v2xrf + Math.cos(angle + Math.PI/2) * v2yr;
        double v2yf = Math.sin(angle) * v2xrf + Math.sin(angle + Math.PI/2) * v2yr;
        velocity.setX(v1xf);
        velocity.setY(v1yf);
        b.getVelocity().setX(v2xf);
        b.getVelocity().setY(v2yf);
    }

    public double findAngle(double x, double y)
    {
        double angle;
        if(x < -0.000001)
            angle = 180 + Math.atan(y/x) * 180/Math.PI;
        else if(x > 0.000001 && y >= -0.000001)
            angle = Math.atan(y/x) * 180/Math.PI;
        else if(x > 0.000001 && y < -0.000001)
            angle = 360 + Math.atan(y/x) * 180/Math.PI;
        else if(Math.abs(x) <= 0.000001 && Math.abs(y) <= 0.000001)
            angle = 0;
        else if(Math.abs(x) <= 0.000001 && y >= -0.000001)
            angle = 90;
        else
            angle = 270;
        return angle;
    }
    
    public Vector getPosition()
    {
        return position;
    }

    public Vector getVelocity()
    {
        return velocity;
    }

    public int getSize()
    {
        return size;
    }

    public int getColor()
    {
        return color;
    }
}