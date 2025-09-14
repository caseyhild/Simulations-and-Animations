public class Equation
{
    private final String equation;

    public Equation(String eqn)
    {
        equation = eqn;
    }

    public Complex evaluate(double x)
    {
        String postfix = convertToPostfix(equation);
        postfix = simplify(postfix, x);
        return getValue(postfix);
    }

    private String convertToPostfix(String equation)
    {
        StringBuilder postfix = new StringBuilder();
        Stack stack = new Stack();
        removeSpaces(equation);
        if(!checkParenthesis(equation).isEmpty())
            return checkParenthesis(equation);
        for(int i = 0; i < equation.length(); i++)
        {
            if(equation.charAt(i) == '-' && (i == 0 || !isOperand("" + equation.charAt(i - 1))))
                equation = equation.substring(0, i) + "0" + equation.substring(i);
            if(i > 0 && i < equation.length() - 1 && equation.startsWith("pi", i) && isOperand(equation.substring(i - 1, i)))
                equation = equation.substring(0, i) + "*" + equation.substring(i);
            if(i > 0 && equation.charAt(i) == 'e' && isOperand(equation.substring(i - 1, i)))
                equation = equation.substring(0, i) + "*" + equation.substring(i);
            if(i > 0 && equation.charAt(i) == 'i' && isOperand(equation.substring(i - 1, i)))
                equation = equation.substring(0, i) + "*" + equation.substring(i);
            if(i > 0 && equation.charAt(i) == 'x' && isOperand(equation.substring(i - 1, i)))
                equation = equation.substring(0, i) + "*" + equation.substring(i);
            if(i < equation.length() - 2 && equation.startsWith("pi", i) && isOperand(equation.substring(i + 2, i + 3)))
                equation = equation.substring(0, i + 2) + "*" + equation.substring(i + 2);
            if(i < equation.length() - 1 && equation.charAt(i) == 'e' && isOperand(equation.substring(i + 1, i + 2)))
                equation = equation.substring(0, i + 1) + "*" + equation.substring(i + 1);
            if(i < equation.length() - 1 && equation.charAt(i) == 'i' && isOperand(equation.substring(i + 1, i + 2)))
                equation = equation.substring(0, i + 1) + "*" + equation.substring(i + 1);
            if(i < equation.length() - 1 && equation.charAt(i) == 'x' && isOperand(equation.substring(i + 1, i + 2)))
                equation = equation.substring(0, i + 1) + "*" + equation.substring(i + 1);
        }
        for(int i = 0; i < equation.length(); i++)
        {
            StringBuilder s1 = new StringBuilder(equation.substring(i, i + 1));
            if(i < equation.length() - 1 && equation.startsWith("pi", i))
            {    
                s1 = new StringBuilder("pi");
                i++;
            }
            else if(equation.charAt(i) == 'e')
                s1 = new StringBuilder("e");
            else if(isOperand(s1.toString()))
            {
                //checks for multiple character operands
                i++;
                while(i < equation.length() && (Character.isDigit(equation.charAt(i)) || equation.charAt(i) == '.'))
                {
                    s1.append(equation.charAt(i));
                    i++;
                }
                i--;
                if(i < equation.length() - 1 && isOperand(equation.substring(i + 1, i + 2)))
                {
                    postfix = new StringBuilder("adjacent operands");
                    break;
                }
            }
            if(isOperand(s1.toString()))
                postfix.append(s1).append("|");
            if(i < equation.length() - 2 && equation.startsWith("sin", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(i < equation.length() - 2 && equation.startsWith("cos", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(i < equation.length() - 2 && equation.startsWith("tan", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(i < equation.length() - 2 && equation.startsWith("csc", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(i < equation.length() - 2 && equation.startsWith("sec", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(i < equation.length() - 2 && equation.startsWith("cot", i))
            {    
                s1 = new StringBuilder(equation.substring(i, i + 3));
                i += 2;
            }
            else if(isOperator(s1.toString()))
            {
                if(i < equation.length() - 1 && isOperator(equation.substring(i + 1, i + 2)))
                {
                    postfix = new StringBuilder("adjacent operators");
                    break;
                }
                while(!stack.isEmpty())
                {
                    String s2 = (String) stack.pop();
                    if(priority(s2) < priority(s1.toString()))
                    {
                        stack.push(s2);
                        break;
                    }
                    else if(s2.equals("("))
                        break;
                    postfix.append(s2).append("|");
                }
            }
            if(isOperator(s1.toString()))
                stack.push(s1.toString());
            if(s1.toString().equals("("))
            {
                stack.push(s1.toString());
            }
            else if(s1.toString().equals(")"))
            {
                boolean foundLeft = false;
                while(!stack.isEmpty() && !foundLeft)
                {
                    String s2 = (String) stack.pop();
                    if(s2.equals("("))
                        foundLeft = true;
                    else
                        postfix.append(s2).append("|");
                }
            }
        }
        while(!stack.isEmpty())
        {
            postfix.append(stack.pop()).append("|");
        }
        return postfix.toString();
    }

    private String simplify(String postfix, double x)
    {
        Stack stack = new Stack();
        switch (postfix) {
            case "adjacent operators", "adjacent operands", "missing parenthesis", "extra parenthesis" -> {
                return "";
            }
        }
        for(int i = 0; i < postfix.length(); i += 2)
        {
            StringBuilder s = new StringBuilder(postfix.substring(i, i + 1));
            if(i < postfix.length() - 1 && postfix.startsWith("pi", i))
            {    
                s = new StringBuilder("pi");
                i++;
            }
            else if(isOperand(s.toString()) || s.toString().equals("-"))
            {
                i++;
                while(i < postfix.length() && (Character.isDigit(postfix.charAt(i)) || postfix.charAt(i) == '.'))
                {
                    s.append(postfix.charAt(i));
                    i++;
                }
                i--;
            }
            if(isOperand(s.toString()))
                stack.push(getNumericValue(s.toString(), x));
            if(i < postfix.length() - 2 && postfix.startsWith("sin", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            else if(i < postfix.length() - 2 && postfix.startsWith("cos", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            else if(i < postfix.length() - 2 && postfix.startsWith("tan", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            else if(i < postfix.length() - 2 && postfix.startsWith("csc", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            else if(i < postfix.length() - 2 && postfix.startsWith("sec", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            else if(i < postfix.length() - 2 && postfix.startsWith("cot", i))
            {    
                s = new StringBuilder(postfix.substring(i, i + 3));
                i += 2;
            }
            if(isOperator(s.toString()))
            {
                Complex num1, num2;
                if(!stack.isEmpty())
                    num2 = (Complex) stack.pop();
                else
                    num2 = new Complex("a + bi", 0, 0);
                if(!stack.isEmpty() && numOperands(s.toString()) == 2)
                    num1 = (Complex) stack.pop();
                else
                    num1 = new Complex("a + bi", 0, 0);
                Complex value = new Complex("a + bi", 0, 0);
                switch(s.toString())
                {
                    case "+":
                    value = Complex.add(num1, num2);
                    break;
                    case "-":
                    value = Complex.sub(num1, num2);
                    break;
                    case "sin":
                    value = Complex.sin(num2);
                    break;
                    case "cos":
                    value = Complex.cos(num2);
                    break;
                    case "tan":
                    value = Complex.tan(num2);
                    break;
                    case "csc":
                    value = Complex.csc(num2);
                    break;
                    case "sec":
                    value = Complex.sec(num2);
                    break;
                    case "cot":
                    value = Complex.cot(num2);
                    break;
                    case "*":
                    value = Complex.mult(num1, num2);
                    break;
                    case "/":
                    value = Complex.div(num1, num2);
                    break;
                    case "^":
                    value = Complex.pow(num1, num2);
                    break;
                    default:
                    break;
                }
                stack.push(value);
            }
        }
        return "" + stack.pop();
    }

    private void removeSpaces(String equation)
    {
        for(int i = 0; i < equation.length(); i++)
        {
            if(equation.charAt(i) == ' ')
            {
                equation = equation.substring(0, i) + equation.substring(i + 1);
                i--;
            }
        }
    }

    private String checkParenthesis(String equation)
    {
        int numLeft = 0;
        int numRight = 0;
        for(int i = 0; i < equation.length(); i++)
        {
            if(equation.charAt(i) == '(')
                numLeft++;
            else if(equation.charAt(i) == ')')
                numRight++;
        }
        if(numLeft > numRight)
            return "missing parenthesis";
        else if(numLeft < numRight)
            return "extra parenthesis";
        else
            return "";
    }

    private boolean isOperator(String s)
    {
        return priority(s) > 0;
    }

    private int priority(String op)
    {
        return switch (op) {
            case "^" -> 4;
            case "*", "/" -> 3;
            case "sin", "cos", "tan", "csc", "sec", "cot" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    private boolean isOperand(String s)
    {
        if(s.equals("x") || s.equals("e") || s.equals("pi") || s.equals("i"))
            return true;
        try
        {
            Double.parseDouble(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private int numOperands(String op)
    {
        return switch (op) {
            case "^", "*", "/", "+", "-" -> 2;
            case "sin", "cos", "tan", "csc", "sec", "cot" -> 1;
            default -> 0;
        };
    }

    private Complex getNumericValue(String s, double x)
    {
        if(s.equals("pi"))
            return new Complex("a + bi", Math.PI, 0);
        if(s.equals("e"))
            return new Complex("a + bi", Math.E, 0);
        if(s.equals("i"))
            return new Complex("a + bi", 0, 1);
        if(s.equals("x"))
            return new Complex("a + bi", x, 0);
        try
        {
            Double.parseDouble(s);
        }
        catch(NumberFormatException e)
        {
            System.out.println("non-double value");
            return new Complex("a + bi", 0, 0);
        }
        return new Complex("a + bi", Double.parseDouble(s), 0);
    }

    private Complex getValue(String postfix)
    {
        StringBuilder a = new StringBuilder();
        int i = 0;
        while(i < postfix.length() && postfix.charAt(i) != ' ')
        {
            a.append(postfix.charAt(i));
            i++;
        }
        StringBuilder b = new StringBuilder();
        i = a.length() + 3;
        while(i < postfix.length() && postfix.charAt(i) != 'i')
        {
            b.append(postfix.charAt(i));
            i++;
        }
        if(a.length() < postfix.length() - 1 && postfix.charAt(a.length() + 1) == '-')
            b.insert(0, '-');
        if(a.toString().isEmpty())
            a = new StringBuilder("0.0");
        if(b.toString().isEmpty())
            b = new StringBuilder("0.0");
        try
        {
            Double.parseDouble(a.toString());
            Double.parseDouble(b.toString());
        }
        catch(NumberFormatException e)
        {
            System.out.println("non-double value");
            return new Complex("a + bi", 0, 0);
        }
        return new Complex("a + bi", Double.parseDouble(a.toString()), Double.parseDouble(b.toString()));
    }

    public String toString()
    {
        return equation;
    }
}