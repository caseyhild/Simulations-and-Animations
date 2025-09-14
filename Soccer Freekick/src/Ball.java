import java.awt.*;

public class Ball {
    private int r(double v) { return (int)Math.round(v); }

    public void draw(Graphics2D g, int x, int y, int ballSize) {
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(1f));

        g.setColor(Color.WHITE);
        g.fillOval(r(x - ballSize / 2.0), r(y - ballSize / 2.0), ballSize, ballSize);
        g.setColor(Color.BLACK);
        g.drawOval(r(x - ballSize / 2.0), r(y - ballSize / 2.0), ballSize, ballSize);

        // middle pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x + 0.0),
                    r(x + ballSize / 5.0),
                    r(x + (2.0 * ballSize / 5.0) / 3.0),
                    r(x - (2.0 * ballSize / 5.0) / 3.0),
                    r(x - ballSize / 5.0),
                    r(x + 0.0)
            };
            int[] ys = {
                    r(y - ballSize / 5.0),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y - ballSize / 5.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // upper-left hexagon
        {
            int[] xs = {
                    r(x - (ballSize / 2.0) / 3.0),
                    r(x + 0.0),
                    r(x + 0.0),
                    r(x - (ballSize / 5.0)),
                    r(x - ballSize / 3.0),
                    r(x - ballSize / 3.0),
                    r(x - (ballSize / 2.0) / 3.0)
            };
            int[] ys = {
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 11.0 * (ballSize / 10.0) / 3.0),
                    r(y - (ballSize / 5.0)),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y - (ballSize / 10.0)),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0))
            };
            g.drawPolygon(xs, ys, xs.length);
        }

        // upper-right hexagon
        {
            int[] xs = {
                    r(x + (ballSize / 2.0) / 3.0),
                    r(x + ballSize / 3.0),
                    r(x + ballSize / 3.0),
                    r(x + (ballSize / 5.0)),
                    r(x + 0.0),
                    r(x + 0.0),
                    r(x + (ballSize / 2.0) / 3.0)
            };
            int[] ys = {
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - (ballSize / 10.0)),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y - (ballSize / 5.0)),
                    r(y - 11.0 * (ballSize / 10.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0))
            };
            g.drawPolygon(xs, ys, xs.length);
        }

        // lower-right hexagon
        {
            int[] xs = {
                    r(x + ballSize / 3.0),
                    r(x + 13.0 * (ballSize / 10.0) / 3.0),
                    r(x + 11.0 * (ballSize / 10.0) / 3.0),
                    r(x + 7.0 * (ballSize / 10.0) / 3.0),
                    r(x + 2.0 * (ballSize / 5.0) / 3.0),
                    r(x + (ballSize / 5.0)),
                    r(x + ballSize / 3.0)
            };
            int[] ys = {
                    r(y - (ballSize / 10.0)),
                    r(y + (ballSize / 10.0) / 3.0),
                    r(y + 7.0 * (ballSize / 10.0) / 3.0),
                    r(y + 4.0 * (ballSize / 5.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y - (ballSize / 10.0))
            };
            g.drawPolygon(xs, ys, xs.length);
        }

        // bottom hexagon
        {
            int[] xs = {
                    r(x - 2.0 * (ballSize / 5.0) / 3.0),
                    r(x + 2.0 * (ballSize / 5.0) / 3.0),
                    r(x + 7.0 * (ballSize / 10.0) / 3.0),
                    r(x + 2.0 * (ballSize / 5.0) / 3.0),
                    r(x - 2.0 * (ballSize / 5.0) / 3.0),
                    r(x - 7.0 * (ballSize / 10.0) / 3.0),
                    r(x - 2.0 * (ballSize / 5.0) / 3.0)
            };
            int[] ys = {
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + 4.0 * (ballSize / 5.0) / 3.0),
                    r(y + 2.0 * (ballSize / 5.0)),
                    r(y + 2.0 * (ballSize / 5.0)),
                    r(y + 4.0 * (ballSize / 5.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0)
            };
            g.drawPolygon(xs, ys, xs.length);
        }

        // lower-left hexagon
        {
            int[] xs = {
                    r(x - ballSize / 3.0),
                    r(x - (ballSize / 5.0)),
                    r(x - 2.0 * (ballSize / 5.0) / 3.0),
                    r(x - 7.0 * (ballSize / 10.0) / 3.0),
                    r(x - 11.0 * (ballSize / 10.0) / 3.0),
                    r(x - 13.0 * (ballSize / 10.0) / 3.0),
                    r(x - ballSize / 3.0)
            };
            int[] ys = {
                    r(y - (ballSize / 10.0)),
                    r(y - (ballSize / 5.0) / 3.0),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + 4.0 * (ballSize / 5.0) / 3.0),
                    r(y + 7.0 * (ballSize / 10.0) / 3.0),
                    r(y + (ballSize / 10.0) / 3.0),
                    r(y - (ballSize / 10.0))
            };
            g.drawPolygon(xs, ys, xs.length);
        }

        // upper-left pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x - (ballSize / 2.0) / 3.0),
                    r(x - (ballSize / 2.0) / 3.0),
                    r(x - ballSize / 3.0),
                    r(x - 5.0 * (ballSize / 4.0) / 3.0),
                    r(x - 3.0 * (ballSize / 10.0)),
                    r(x - (ballSize / 2.0) / 3.0)
            };
            int[] ys = {
                    r(y - 7.0 * (ballSize / 5.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 7.0 * (ballSize / 5.0) / 3.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // upper-right pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x + (ballSize / 2.0) / 3.0),
                    r(x + (ballSize / 2.0) / 3.0),
                    r(x + ballSize / 3.0),
                    r(x + 5.0 * (ballSize / 4.0) / 3.0),
                    r(x + 3.0 * (ballSize / 10.0)),
                    r(x + (ballSize / 2.0) / 3.0)
            };
            int[] ys = {
                    r(y - 7.0 * (ballSize / 5.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - 4.0 * (ballSize / 5.0) / 3.0),
                    r(y - 2.0 * (ballSize / 5.0)),
                    r(y - 7.0 * (ballSize / 5.0) / 3.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // lower-right pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x + (ballSize / 2.0)),
                    r(x + 13.0 * (ballSize / 10.0) / 3.0),
                    r(x + 11.0 * (ballSize / 10.0) / 3.0),
                    r(x + 2.0 * (ballSize / 5.0)),
                    r(x + 7.0 * (ballSize / 5.0) / 3.0),
                    r(x + (ballSize / 2.0))
            };
            int[] ys = {
                    r(y + 0.0),
                    r(y + (ballSize / 10.0) / 3.0),
                    r(y + 7.0 * (ballSize / 10.0) / 3.0),
                    r(y + 3.0 * (ballSize / 10.0)),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + 0.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // bottom pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x + (ballSize / 2.0) / 3.0),
                    r(x + 2.0 * (ballSize / 5.0) / 3.0),
                    r(x - 2.0 * (ballSize / 5.0) / 3.0),
                    r(x - (ballSize / 2.0) / 3.0),
                    r(x + 0.0),
                    r(x + (ballSize / 2.0) / 3.0)
            };
            int[] ys = {
                    r(y + 7.0 * (ballSize / 5.0) / 3.0),
                    r(y + 2.0 * (ballSize / 5.0)),
                    r(y + 2.0 * (ballSize / 5.0)),
                    r(y + 7.0 * (ballSize / 5.0) / 3.0),
                    r(y + (ballSize / 2.0)),
                    r(y + 7.0 * (ballSize / 5.0) / 3.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // lower-left pentagon
        g.setColor(Color.BLACK);
        {
            int[] xs = {
                    r(x - (ballSize / 2.0)),
                    r(x - 13.0 * (ballSize / 10.0) / 3.0),
                    r(x - 11.0 * (ballSize / 10.0) / 3.0),
                    r(x - 2.0 * (ballSize / 5.0)),
                    r(x - 7.0 * (ballSize / 5.0) / 3.0),
                    r(x - (ballSize / 2.0))
            };
            int[] ys = {
                    r(y + 0.0),
                    r(y + (ballSize / 10.0) / 3.0),
                    r(y + 7.0 * (ballSize / 10.0) / 3.0),
                    r(y + 3.0 * (ballSize / 10.0)),
                    r(y + (ballSize / 2.0) / 3.0),
                    r(y + 0.0)
            };
            g.fillPolygon(xs, ys, xs.length);
        }

        // restore stroke
        g.setStroke(oldStroke);
    }
}
