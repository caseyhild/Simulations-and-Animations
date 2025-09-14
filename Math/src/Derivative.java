public class Derivative
{
    public static Complex derive(Equation equation, double x)
    {
        double dx = 0.01;
        return Complex.div(Complex.sub(equation.evaluate(x + dx), equation.evaluate(x - dx)), 2 * dx);
    }
}