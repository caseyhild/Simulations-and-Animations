public class Noise
{

    public static double[][] noise(int xSize, int ySize)
    {
        double[][] random = new double[ySize][xSize];
        double[][] noise = new double[ySize][xSize];
        for(int y = 0; y < ySize; y++)
        {
            for(int x = 0; x < xSize; x++)
            {
                random[y][x] = Math.random();
            }
        }
        for(int y = 0; y < ySize; y++)
        {
            for(int x = 0; x < xSize; x++)
            {
                noise[y][x] = turbulence(random, x, y, Math.min(xSize, ySize), Math.min(xSize, ySize)/8.0);
            }
        }
        return noise;
    }

    private static double smoothNoise(double[][] random, int size, double x, double y)
    {
        double fractX = x - (int) x;
        double fractY = y - (int) y;
        int x1 = ((int) x + size) % size;
        int y1 = ((int) y + size) % size;
        int x2 = (x1 + size - 1) % size;
        int y2 = (y1 + size - 1) % size;
        double value = 0.0;
        value += fractX * fractY * random[y1][x1];
        value += (1 - fractX) * fractY * random[y1][x2];
        value += fractX * (1 - fractY) * random[y2][x1];
        value += (1 - fractX) * (1 - fractY) * random[y2][x2];
        return value;
    }

    private static double turbulence(double[][] random, double x, double y, int size, double Size)
    {
        double value = 0.0;
        double initialSize = Size;
        while(Size >= 1)
        {
            value += smoothNoise(random, size, x / Size, y / Size) * Size;
            Size /= 2.0;
        }
        return (0.5 * value / initialSize);
    }
}