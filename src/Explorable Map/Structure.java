public class Structure
{
    public String name;
    public int x, y, color;
    public int[][] map;

    public Structure(String name, int x, int y, int color)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.color = color;
        switch (name) {
            case "cross" -> map = new int[][]{
                    {1, 0, 1},
                    {0, 1, 0},
                    {1, 0, 1}
            };
            case "plus" -> map = new int[][]{
                    {0, 1, 0},
                    {1, 1, 1},
                    {0, 1, 0}
            };
            case "square" -> map = new int[][]{
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1}
            };
        }
    }
}