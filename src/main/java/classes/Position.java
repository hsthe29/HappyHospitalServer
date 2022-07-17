package classes;

import java.io.Serial;
import java.io.Serializable;

public class Position implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public double x;
    public double y;
    public int dx;
    public int dy;

    public Position(double x, double y) {
        this.x = x;
        this.dx = (int) x;
        this.y = y;
        this.dy = (int) y;
    }

    public static double between(Position x, Position y) {
        return Math.sqrt((x.x - y.x) * (x.x - y.x) + (x.y - y.y) * (x.y - y.y));
    }
}

