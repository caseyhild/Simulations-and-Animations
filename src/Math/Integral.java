public class Integral
{
    public static Complex integrate(Equation equation, double a, double b)
    {
        double dx = 0.0005;
        Complex integral = new Complex("a + bi", 0, 0);
        for(double i = a; i <= b; i += dx)
        {
            integral.add(Complex.mult(equation.evaluate(i), new Complex("a + bi", dx, 0)));
        }
        return integral;
    }
}