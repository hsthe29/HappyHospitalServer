package socket;

import classes.Position;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public int mode;
    private ArrayList<Position> pathPos;
    private ArrayList<ArrayList<ArrayList<Position>>> adjacencyList;
    private ArrayList<Position> groundPos;
    private Position startPos;
    private Position endPos;

    public ArrayList<ArrayList<ArrayList<Position>>> getAdjacencyList() {
        return adjacencyList;
    }

    public ArrayList<Position> getGroundPos() {
        return groundPos;
    }

    public ArrayList<Position> getPathPos() {
        return pathPos;
    }

    public Position getStartPos() {
        return startPos;
    }

    public Position getEndPos() {
        return endPos;
    }

    public Message(int mode) {
        this.mode = mode;
    }

    public Message(int mode, ArrayList<Position> groundPos) {
        this.mode = mode;
        this.groundPos = groundPos;
    }

    public Message(int mode,
                   ArrayList<ArrayList<ArrayList<Position>>> adjacencyList,
                   ArrayList<Position> pathPos
    ) {
        this.mode = mode;
        this.adjacencyList = adjacencyList;
        this.pathPos = pathPos;
    }

    public Message(int mode, Position startPos, Position endPos) {
        this.mode = mode;
        this.startPos = startPos;
        this.endPos = endPos;
    }
}
