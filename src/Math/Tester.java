public class Tester
{
    public static void main(String[] args)
    {
        Equation equation = new Equation("e^(ix)");
        System.out.println("f(x) = " + equation + "\n");
        System.out.println("f(pi) = " + equation.evaluate(-Math.PI/2).round() + "\n");
        System.out.println("f'(pi) = " + Derivative.derive(equation, Math.PI).round() + "\n");
        System.out.println("pi");
        System.out.println("∫f(x)dx = " + Integral.integrate(equation, 0, Math.PI).round());
        System.out.println("0");
    }
}