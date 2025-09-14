public class Matrix
{
    private double[][] matrix;

    public Matrix()
    {
        matrix = new double[0][0];
    }

    public Matrix(int m, int n)
    {
        matrix = new double[m][n];
    }

    public Matrix(double[][] a)
    {
        matrix = new double[a.length][a[0].length];
        for(int i = 0; i < a.length; i++)
        {
            System.arraycopy(a[i], 0, matrix[i], 0, a[i].length);
        }
    }

    public Matrix(Matrix a)
    {
        matrix = new double[a.matrix().length][a.matrix()[0].length];
        for(int i = 0; i < a.matrix().length; i++)
        {
            for(int j = 0; j < a.matrix()[i].length; j++)
            {
                matrix[i][j] = a.matrix()[i][j];
            }
        }
    }

    public void randomize()
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] = Math.random();
            }
        }
    }

    public double[][] matrix()
    {
        return matrix;
    }

    public void add(Matrix a)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] += a.matrix[i][j];
            }
        }
    }

    public void add(double scalar)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] += scalar;
            }
        }
    }

    public static Matrix add(Matrix a, Matrix b)
    {
        if(a.matrix.length != b.matrix.length || a.matrix[0].length != b.matrix[0].length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < c.matrix.length; i++)
        {
            for(int j = 0; j < c.matrix[i].length; j++)
            {
                c.matrix[i][j] = a.matrix[i][j] + b.matrix[i][j];
            }
        }
        return c;
    }

    public static Matrix add(Matrix a, double scalar)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < b.matrix.length; i++)
        {
            for(int j = 0; j < b.matrix[i].length; j++)
            {
                b.matrix[i][j] = a.matrix[i][j] + scalar;
            }
        }
        return b;
    }

    public void sub(Matrix a)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] -= a.matrix[i][j];
            }
        }
    }

    public void sub(double scalar)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] -= scalar;
            }
        }
    }

    public static Matrix sub(Matrix a, Matrix b)
    {
        if(a.matrix.length != b.matrix.length || a.matrix[0].length != b.matrix[0].length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < c.matrix.length; i++)
        {
            for(int j = 0; j < c.matrix[i].length; j++)
            {
                c.matrix[i][j] = a.matrix[i][j] - b.matrix[i][j];
            }
        }
        return c;
    }

    public static Matrix sub(Matrix a, double scalar)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < b.matrix.length; i++)
        {
            for(int j = 0; j < b.matrix[i].length; j++)
            {
                b.matrix[i][j] = a.matrix[i][j] - scalar;
            }
        }
        return b;
    }

    public static Matrix sub(double scalar, Matrix a)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < b.matrix.length; i++)
        {
            for(int j = 0; j < b.matrix[i].length; j++)
            {
                b.matrix[i][j] = scalar - a.matrix[i][j];
            }
        }
        return b;
    }

    public void mult(Matrix a)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] *= a.matrix[i][j];
            }
        }
    }

    public void mult(double scalar)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] *= scalar;
            }
        }
    }

    public static Matrix mult(Matrix a, Matrix b)
    {
        if(a.matrix.length != b.matrix.length || a.matrix[0].length != b.matrix[0].length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                c.matrix[i][j] = a.matrix[i][j] * b.matrix[i][j];
            }
        }
        return c;
    }

    public static Matrix mult(Matrix a, double scalar)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                b.matrix[i][j] = a.matrix[i][j] * scalar;
            }
        }
        return b;
    }

    public void div(Matrix a)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] /= a.matrix[i][j];
            }
        }
    }

    public void div(double scalar)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] /= scalar;
            }
        }
    }

    public static Matrix div(Matrix a, Matrix b)
    {
        if(a.matrix.length != b.matrix.length || a.matrix[0].length != b.matrix[0].length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                c.matrix[i][j] = a.matrix[i][j] / b.matrix[i][j];
            }
        }
        return c;
    }

    public static Matrix div(Matrix a, double scalar)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                b.matrix[i][j] = a.matrix[i][j] / scalar;
            }
        }
        return b;
    }

    public void pow(Matrix a)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] = Math.pow(matrix[i][j], a.matrix[i][j]);
            }
        }
    }

    public void pow(double scalar)
    {
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                matrix[i][j] = Math.pow(matrix[i][j], scalar);
            }
        }
    }

    public static Matrix pow(Matrix a, Matrix b)
    {
        if(a.matrix.length != b.matrix.length || a.matrix[0].length != b.matrix[0].length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                c.matrix[i][j] = Math.pow(a.matrix[i][j], b.matrix[i][j]);
            }
        }
        return c;
    }

    public static Matrix pow(Matrix a, double scalar)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                b.matrix[i][j] = Math.pow(a.matrix[i][j], scalar);
            }
        }
        return b;
    }

    public static Matrix dot(Matrix a, Matrix b)
    {
        if(a.matrix[0].length != b.matrix.length)
            return new Matrix();
        Matrix c = new Matrix(a.matrix.length, b.matrix[0].length);
        for(int i = 0; i < c.matrix.length; i++)
        {
            for(int j = 0; j < c.matrix[0].length; j++)
            {
                double sum = 0;
                for(int k = 0; k < a.matrix[0].length; k++)
                {
                    sum += a.matrix[i][k] * b.matrix[k][j];
                }
                c.matrix[i][j] = sum;
            }
        }
        return c;
    }

    public void transpose()
    {
        Matrix a = new Matrix(matrix[0].length, matrix.length);
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                a.matrix[i][j] = matrix[j][i];
            }
        }
        matrix = new double[matrix[0].length][matrix.length];
        for(int i = 0; i < matrix.length; i++)
        {
            System.arraycopy(a.matrix[i], 0, matrix[i], 0, matrix[i].length);
        }
    }

    public static Matrix transpose(Matrix a)
    {
        Matrix b = new Matrix(a.matrix[0].length, a.matrix.length);
        for(int i = 0; i < b.matrix.length; i++)
        {
            for(int j = 0; j < b.matrix[i].length; j++)
            {
                b.matrix[i][j] = a.matrix[j][i];
            }
        }
        return b;
    }

    public static Matrix sigmoid(Matrix a)
    {
        Matrix b = new Matrix(a.matrix.length, a.matrix[0].length);
        for(int i = 0; i < b.matrix.length; i++)
        {
            for(int j = 0; j < b.matrix[i].length; j++)
            {
                b.matrix[i][j] = sigmoid(a.matrix[i][j]);
            }
        }
        return b;
    }

    public static double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }

    public void print()
    {
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                System.out.printf("%.2f ", aDouble);
            }
            System.out.println();
        }
    }

    public static void print(Matrix a)
    {
        for(int i = 0; i < a.matrix.length; i++)
        {
            for(int j = 0; j < a.matrix[i].length; j++)
            {
                System.out.printf("%.2f ", a.matrix[i][j]);
            }
            System.out.println();
        }
    }
}