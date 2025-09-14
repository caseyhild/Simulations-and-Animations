public class Complex
{
    public double a;
    public double b;

    public Complex(double a, double b)
    {
        this.a = a;
        this.b = b;
    }

    public void add(Complex c)
    {
        a += c.a;
        b += c.b;
    }
    public static Complex mult(Complex c, Complex d)
    {
        return new Complex(c.a * d.a - c.b * d.b, c.a * d.b + c.b * d.a);
    }

    public void div(double c)
    {
        a /= c;
        b /= c;
    }

    public double amplitude()
    {
        return Math.sqrt(a * a + b * b);
    }

    public String toString()
    {
        String aString = "" + Math.abs(a);
        String bString = "" + Math.abs(b);
        if(a == 0 && b == 0)
            return "0";
        else if(a == 0 && b < 0)
            return "-" + bString + "i";
        else if(a == 0 && b > 0)
            return bString + "i";
        else if(a < 0 && b == 0)
            return "-" + aString;
        else if(a < 0 && b < 0)
            return "-" + aString + " - " + bString + "i";
        else if(a < 0 && b > 0)
            return "-" + aString + " + " + bString + "i";
        else if(a > 0 && b == 0)
            return aString;
        else if(a > 0 && b < 0)
            return aString + " - " + bString + "i";
        else
            return aString + " + " + bString + "i";
    }
}