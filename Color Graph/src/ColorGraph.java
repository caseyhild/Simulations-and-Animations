import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorGraph extends JPanel implements ActionListener {
    private static final int width = 1200;
    private static final int height = 600;
    private static final int[][] pascalTriangle =
            {
                    {1},
                    {1, 1},
                    {1, 2, 1},
                    {1, 3, 3, 1},
                    {1, 4, 6, 4, 1},
                    {1, 5, 10, 10, 5, 1},
                    {1, 6, 15, 20, 15, 6, 1},
                    {1, 7, 21, 35, 35, 21, 7, 1},
                    {1, 8, 28, 56, 70, 56, 28, 8, 1},
                    {1, 9, 36, 84, 126, 126, 84, 36, 9, 1}
            };
    private final int[][] ab;
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final int[][] inputColors;
    private final int[][] outputColors;
    javax.swing.Timer tm = new javax.swing.Timer(5, this);

    public ColorGraph() {
//        String equation = "x^9+x^8-x^7+x^6+x^5-x^4-x^3+x^2+x-1";
//        String equation = "x^5-x+1";
        String equation = "x^3-1";
        ab = simplify(equation);
        xMin = -2;
        xMax = 2;
        yMin = -2;
        yMax = 2;
        inputColors = new int[height][height];
        outputColors = new int[height][height];
        int maxDist = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                double angle = findAngle(x - height / 2.0, y - height / 2.0);
                double dist = (int) Math.sqrt((x - height / 2.0) * (x - height / 2.0) + (y - height / 2.0) * (y - height / 2.0));
                double brightness = Math.sqrt(dist / (Math.sqrt(2) * height / 2.0));
                inputColors[y][x] = rgbNum((int) (brightness * getR(getRainbowColor(360 - angle))), (int) (brightness * getG(getRainbowColor(360 - angle))), (int) (brightness * getB(getRainbowColor(360 - angle))));
                int d = getDist(x, y);
                if (dist > maxDist)
                    maxDist = d;
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                function(outputColors, x, y, maxDist);
            }
        }
        setBackground(new Color(0, 0, 0));
    }

    public static void main(String[] args) {
        ColorGraph c = new ColorGraph();
        JFrame jf = new JFrame();
        jf.setTitle("Color Graph");
        jf.setSize(width, height + 28);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.add(c);
        jf.setVisible(true);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                g.setColor(new Color(getR(inputColors[y][x]), getG(inputColors[y][x]), getB(inputColors[y][x])));
                g.drawLine(x, height - 1 - y, x, height - 1 - y);
                g.setColor(new Color(getR(outputColors[y][x]), getG(outputColors[y][x]), getB(outputColors[y][x])));
                g.drawLine(x + width - height, height - 1 - y, x + width - height, height - 1 - y);
            }
        }
        tm.start();
    }

    public int[][] simplify(String equation) {
        equation = equation.replaceAll(" ", "");
        equation = "+" + equation;
        for (int i = 1; i < equation.length(); i++) {
            if (equation.substring(i - 1, i + 1).equals("+x") || equation.substring(i - 1, i + 1).equals("-x"))
                equation = equation.substring(0, i) + "1" + equation.substring(i);
        }
        equation = equation.substring(1);
        equation += "++";
        for (int i = 0; i < equation.length(); i++) {
            if (i < equation.length() - 2 && (equation.startsWith("x+", i) || equation.startsWith("x-", i)))
                equation = equation.substring(0, i + 1) + "^1" + equation.substring(i + 1);
            if (i >= 1 && i < equation.length() - 1 && equation.charAt(i - 1) != '^' && Character.isDigit(equation.charAt(i)) && (equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-'))
                equation = equation.substring(0, i + 1) + "x^0" + equation.substring(i + 1);
        }
        equation = equation.substring(0, equation.length() - 2);
        for (int i = 0; i < equation.length(); i++) {
            if (i >= 1 && Character.isDigit(equation.charAt(i)) && !Character.isDigit(equation.charAt(i - 1))) {
                equation = equation.substring(0, i) + "(" + equation.substring(i);
                i++;
            }
            if (i < equation.length() - 1 && Character.isDigit(equation.charAt(i)) && !Character.isDigit(equation.charAt(i + 1))) {
                equation = equation.substring(0, i + 1) + ")" + equation.substring(i + 1);
                i++;
            }
        }
        if (Character.isDigit(equation.charAt(0)))
            equation = "(" + equation;
        if (Character.isDigit(equation.charAt(equation.length() - 1)))
            equation += ")";
        for (int i = 0; i < equation.length(); i++) {
            if (i < equation.length() - 2 && equation.startsWith("-(", i) && Character.isDigit(equation.charAt(i + 2)))
                equation = equation.substring(0, i) + "+(-" + equation.substring(i + 2);
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < equation.length() - 2; i++) {
            if (equation.startsWith("x^", i)) {
                int exp = Integer.parseInt(equation.substring(i + 3, equation.indexOf(')', i)));
                int coeff = 0;
                for (int j = i; j >= 0; j--) {
                    if (equation.charAt(j) == '(') {
                        coeff = Integer.parseInt(equation.substring(j + 1, equation.indexOf(')', j)));
                        break;
                    }
                }
                while (list.size() <= exp)
                    list.add(0);
                list.set(exp, list.get(exp) + coeff);
            }
        }
        int[][] list2 = new int[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j <= i; j++) {
                list2[i - j][j] += pascalTriangle[i][j] * list.get(i);
            }
        }
        return list2;
    }

    public void function(int[][] output, int x, int y, int maxDist) {
        double xCoord = 1.0 * x / height * (xMax - xMin) + xMin;
        double yCoord = 1.0 * y / height * (yMax - yMin) + yMin;
        double newXCoord = 0;
        double newYCoord = 0;
        for (int a = 0; a < ab.length; a++) {
            for (int b = 0; b < ab[a].length; b++) {
                if (b % 4 == 0)
                    newXCoord += ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else if (b % 4 == 1)
                    newYCoord += ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else if (b % 4 == 2)
                    newXCoord -= ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else
                    newYCoord -= ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
            }
        }
        int newX = (int) ((newXCoord - xMin) / (xMax - xMin) * height);
        int newY = (int) ((newYCoord - yMin) / (yMax - yMin) * height);
        if (xCoord == 0 || yCoord == 0)
            output[y][x] = rgbNum(255, 255, 255);
        else if (output[y][x] != rgbNum(255, 255, 255)) {
            double angle = findAngle(newX - height / 2.0, newY - height / 2.0);
            double dist = (int) Math.sqrt((newX - height / 2.0) * (newX - height / 2.0) + (newY - height / 2.0) * (newY - height / 2.0));
            double brightness = Math.pow(dist / maxDist, 0.25);
            output[y][x] = rgbNum((int) (brightness * getR(getRainbowColor(360 - angle))), (int) (brightness * getG(getRainbowColor(360 - angle))), (int) (brightness * getB(getRainbowColor(360 - angle))));
        }
    }

    public int getDist(int x, int y) {
        double xCoord = 1.0 * x / height * (xMax - xMin) + xMin;
        double yCoord = 1.0 * y / height * (yMax - yMin) + yMin;
        double newXCoord = 0;
        double newYCoord = 0;
        for (int a = 0; a < ab.length; a++) {
            for (int b = 0; b < ab[a].length; b++) {
                if (b % 4 == 0)
                    newXCoord += ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else if (b % 4 == 1)
                    newYCoord += ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else if (b % 4 == 2)
                    newXCoord -= ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
                else
                    newYCoord -= ab[a][b] * Math.pow(xCoord, a) * Math.pow(yCoord, b);
            }
        }
        int newX = (int) ((newXCoord - xMin) / (xMax - xMin) * height);
        int newY = (int) ((newYCoord - yMin) / (yMax - yMin) * height);
        return (int) (2 * Math.sqrt((newX - height / 2.0) * (newX - height / 2.0) + (newY - height / 2.0) * (newY - height / 2.0)));
    }

    public double findAngle(double x, double y) {
        double angle;
        if (x < -0.000001)
            angle = 180 + Math.atan(y / x) * 180 / Math.PI;
        else if (x > 0.000001 && y >= -0.000001)
            angle = Math.atan(y / x) * 180 / Math.PI;
        else if (x > 0.000001 && y < -0.000001)
            angle = 360 + Math.atan(y / x) * 180 / Math.PI;
        else if (Math.abs(x) <= 0.000001 && Math.abs(y) <= 0.000001)
            angle = 0;
        else if (Math.abs(x) <= 0.000001 && y >= -0.000001)
            angle = 90;
        else
            angle = 270;
        return angle;
    }

    private int getRainbowColor(double angle) {
        double percent = angle / 360.0;
        int colorR;
        int colorG;
        int colorB;
        int start = (int) (6 * percent);
        int amt = (int) ((6 * percent - start) * 255);
        if (start == 0) {
            colorR = 255;
            colorG = 0;
            colorB = amt;
        } else if (start == 1) {
            colorR = 255 - amt;
            colorG = 0;
            colorB = 255;
        } else if (start == 2) {
            colorR = 0;
            colorG = amt;
            colorB = 255;
        } else if (start == 3) {
            colorR = 0;
            colorG = 255;
            colorB = 255 - amt;
        } else if (start == 4) {
            colorR = amt;
            colorG = 255;
            colorB = 0;
        } else {
            colorR = 255;
            colorG = 255 - amt;
            colorB = 0;
        }
        if (angle == 360)
            return rgbNum(255, 0, 0);
        return rgbNum(colorR, colorG, colorB);
    }

    private int rgbNum(int r, int g, int b) {
        //gets rgb decimal value from rgb input
        return r * 65536 + g * 256 + b;
    }

    private int getR(int color) {
        //gets r value from rgb decimal input
        return color / 65536;
    }

    private int getG(int color) {
        //gets g value from rgb decimal input
        color -= color / 65536 * 65536;
        return color / 256;
    }

    private int getB(int color) {
        //gets b value from rgb decimal input
        color -= color / 65536 * 65536;
        color -= color / 256 * 256;
        return color;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}