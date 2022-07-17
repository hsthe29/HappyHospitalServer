package socket;

import classes.Position;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ArrayList<Position> path;

    public Path(ArrayList<Position> path) {
        this.path = path;
    }
}
