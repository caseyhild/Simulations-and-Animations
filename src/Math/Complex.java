public class Complex
{
    public double a;
    public double b;

    public Complex(String format, double a, double b)
    {
        if(format.equals("a + bi"))
        {
            this.a = a;
            this.b = b;
        }
        else if(format.equals("e^ix"))
        {
            this.a = a * Math.cos(b);
            this.b = a * Math.sin(b);
        }
    }

    public void add(Complex c)
    {
        a += c.a;
        b += c.b;
    }

    public static Complex add(Complex c, Complex d)
    {
        return new Complex("a + bi", c.a + d.a, c.b + d.b);
    }

    public void sub(Complex c)
    {
        a -= c.a;
        b -= c.b;
    }

    public static Complex sub(Complex c, Complex d)
    {
        return new Complex("a + bi", c.a - d.a, c.b - d.b);
    }

    public void mult(Complex c)
    {
        double temp = a;
        a = a * c.a - b * c.b;
        b = temp * c.b + b * c.a;
    }

    public static Complex mult(Complex c, Complex d)
    {
        return new Complex("a + bi", c.a * d.a - c.b * d.b, c.a * d.b + c.b * d.a);
    }

    public void div(double c)
    {
        a /= c;
        b /= c;
    }

    public static Complex div(Complex c, double d)
    {
        return new Complex("a + bi", c.a / d, c.b / d);
    }

    public void div(Complex c)
    {
        a = a * c.a - b * c.b;
        b = a * c.b + b * c.a;
    }

    public static Complex div(Complex c, Complex d)
    {
        Complex e = Complex.mult(c, Complex.conjugate(d));
        Complex f = Complex.mult(d, Complex.conjugate(d));
        return Complex.div(e, f.a);
    }

    public void pow(Complex c)
    {
        double d = Math.pow(a * a + b * b, c.a/2) * Math.exp(-c.b * Math.atan2(b, a));
        double temp = a;
        a = d * Math.cos(c.a * Math.atan2(b, a) + 0.5 * c.b * Math.log(a * a + b * b));
        b = d * Math.sin(c.a * Math.atan2(b, a) + 0.5 * c.b * Math.log(a * a + b * b));
    }

    public static Complex pow(Complex c, Complex d)
    {
        double e = Math.pow(c.a * c.a + c.b * c.b, d.a/2) * Math.exp(-d.b * Math.atan2(c.b, c.a));
        double a = e * Math.cos(d.a * Math.atan2(c.b, c.a) + 0.5 * d.b * Math.log(c.a * c.a + c.b * c.b));
        double b = e * Math.sin(d.a * Math.atan2(c.b, c.a) + 0.5 * d.b * Math.log(c.a * c.a + c.b * c.b));
        return new Complex("a + bi", a, b);
    }

    public void log()
    {
        double temp = a;
        a = Math.log(Math.sqrt(a * a + b * b));
        b = Math.atan2(b, temp);
    }

    public static Complex log(Complex c)
    {
        return new Complex("a + bi", Math.log(Math.sqrt(c.a * c.a + c.b * c.b)), Math.atan2(c.b, c.a));
    }

    public void sin()
    {
        double temp = a;
        a = Math.sin(a) * Math.cosh(b);
        b = Math.cos(temp) * Math.sinh(b);
    }

    public static Complex sin(Complex c)
    {
        return new Complex("a + bi", Math.sin(c.a) * Math.cosh(c.b), Math.cos(c.a) * Math.sinh(c.b));
    }

    public void cos()
    {
        double temp = a;
        a = Math.cos(a) * Math.cosh(b);
        b = -Math.sin(temp) * Math.sinh(b);
    }

    public static Complex cos(Complex c)
    {
        return new Complex("a + bi", Math.cos(c.a) * Math.cosh(c.b), -Math.sin(c.a) * Math.sinh(c.b));
    }

    public void tan()
    {
        Complex temp = this;
        a = Complex.div(Complex.sin(temp), Complex.cos(temp)).a;
        b = Complex.div(Complex.sin(temp), Complex.cos(temp)).b;
    }

    public static Complex tan(Complex c)
    {
        return Complex.div(Complex.sin(c), Complex.cos(c));
    }

    public void csc()
    {
        Complex temp = this;
        a = Complex.div(new Complex("a + bi", 1, 0), Complex.sin(temp)).a;
        b = Complex.div(new Complex("a + bi", 1, 0), Complex.sin(temp)).b;
    }

    public static Complex csc(Complex c)
    {
        return Complex.div(new Complex("a + bi", 1, 0), Complex.sin(c));
    }

    public void sec()
    {
        Complex temp = this;
        a = Complex.div(new Complex("a + bi", 1, 0), Complex.cos(temp)).a;
        b = Complex.div(new Complex("a + bi", 1, 0), Complex.cos(temp)).b;
    }

    public static Complex sec(Complex c)
    {
        return Complex.div(new Complex("a + bi", 1, 0), Complex.cos(c));
    }

    public void cot()
    {
        Complex temp = this;
        a = Complex.div(Complex.sin(temp), Complex.cos(temp)).a;
        b = Complex.div(Complex.sin(temp), Complex.cos(temp)).b;
    }

    public static Complex cot(Complex c)
    {
        return Complex.div(Complex.cos(c), Complex.sin(c));
    }

    public Complex conjugate()
    {
        return new Complex("a + bi", a, -b);
    }

    public static Complex conjugate(Complex c)
    {
        return new Complex("a + bi", c.a, -c.b);
    }

    public String round()
    {
        double a1 = (int) Math.round(a * 1000) / 1000.0;
        double b1 = (int) Math.round(b * 1000) / 1000.0;
        String aString = "" + Math.abs(a1);
        String bString = "" + Math.abs(b1);
        if(a1 == 0 && b1 == 0)
            return "0";
        else if(a1 == 0 && b1 < 0)
            return "-" + bString + "i";
        else if(a1 == 0 && b1 > 0)
            return bString + "i";
        else if(a1 < 0 && b1 == 0)
            return "-" + aString;
        else if(a1 < 0 && b1 < 0)
            return "-" + aString + " - " + bString + "i";
        else if(a1 < 0 && b1 > 0)
            return "-" + aString + " + " + bString + "i";
        else if(a1 > 0 && b1 == 0)
            return aString;
        else if(a1 > 0 && b1 < 0)
            return aString + " - " + bString + "i";
        else
            return aString + " + " + bString + "i";
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