package algorithm;

import classes.Position;

import java.util.ArrayList;
import java.util.Collections;

class Spot {
    public double i;
    public double j;
    public double f;
    public double g;
    public double h;
    public ArrayList<Spot> neighbors = new ArrayList<>();
    public Spot previous = null;

    public Spot(double i, double j) {
        this.i = i;
        this.j = j;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }

    public void addNeighbors(ArrayList<Spot> ableSpot) {
        for (int k = 0; k < ableSpot.size(); ++k) {
            if (this.i + 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j)
                this.neighbors.add(ableSpot.get(k));
            else if (this.i == ableSpot.get(k).i && this.j + 1 == ableSpot.get(k).j)
                this.neighbors.add(ableSpot.get(k));
            else if (this.i - 1 == ableSpot.get(k).i && this.j == ableSpot.get(k).j)
                this.neighbors.add(ableSpot.get(k));
            else if (this.i == ableSpot.get(k).i && this.j - 1 == ableSpot.get(k).j)
                this.neighbors.add(ableSpot.get(k));
        }
    }

    public boolean equal(Spot spot) {
        if (spot == null) return false;
        return Math.abs(this.i - spot.i)  < 1e-7 && Math.abs(this.j - spot.j) < 1e-7;
    }
}

public class AStar {

    public int width;
    public int height;
    public ArrayList<Spot> ableSpot;
    public Spot[][] grid;
    public ArrayList<Spot> path = new ArrayList<>();

    public AStar(
            int width,
            int height,
            ArrayList<Position> ablePos) {
        this.width = width;
        this.height = height;

        this.grid = new Spot[width][height]; // 22 + 35 + 14 + 20
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                this.grid[i][j] = new Spot(i, j);
            }
        }

        this.ableSpot = new ArrayList<>();
        for (Position ablePo : ablePos) {
            this.ableSpot.add(this.grid[(int) ablePo.x][(int) ablePo.y]);
        }

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                this.grid[i][j].addNeighbors(this.ableSpot);
            }
        }
    }

    private double heuristic(Spot spot1, Spot spot2) {
        return Math.abs(spot1.i - spot2.i) + Math.abs(spot1.j - spot2.j);
    }

    private double heuristic2(Spot spot1, Spot spot2) {
        return Math.sqrt((spot1.i - spot2.i) * (spot1.i - spot2.i) + (spot1.j - spot2.j) * (spot1.j - spot2.j));
    }

    private boolean isInclude(Spot spot, ArrayList<Spot> spots) {
        for (Spot value : spots) {
            if (spot.i == value.i && spot.j == value.j) return true;
        }
        return false;
    }

    private void reset() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                this.grid[i][j].h = 0;
                this.grid[i][j].f = 0;
                this.grid[i][j].g = 0;
                this.grid[i][j].previous = null;
            }
        }
    }

    public ArrayList<Position> cal(Position startPos, Position endPos) {
        this.path.clear();
        reset();
        Spot start = new Spot(startPos.x, startPos.y);
        Spot end = new Spot(endPos.x, endPos.y);

        ArrayList<Spot> openSet = new ArrayList<>();
        ArrayList<Spot> closeSet = new ArrayList<>();
        openSet.add(this.grid[(int) start.i][(int) start.j]);
        while (!openSet.isEmpty()) {
            int winner = 0;
            for(int i = 0; i < openSet.size(); ++i) {
                if(openSet.get(i).f < openSet.get(winner).f)
                    winner = i;
            }

            Spot current = openSet.get(winner);

            if(openSet.get(winner).equal(end)) {
                Spot cur = this.grid[(int) end.i][(int) end.j];
                this.path.add(cur);
                while (cur.previous != null) {
                    this.path.add(cur.previous);
                    cur = cur.previous;
                }
                Collections.reverse(this.path);
                ArrayList<Position> result = new ArrayList<>();
                for(int k = 0; k < this.path.size(); ++k) {
                    result.add((new Position(this.path.get(k).i, this.path.get(k).j)));
                }
                return result;
            }

            openSet.remove(winner);
            closeSet.add(current);

            ArrayList<Spot> neighbors = current.neighbors;

            for(int i = 0; i < neighbors.size(); ++i) {

                Spot neighbor = neighbors.get(i);
                if (!this.isInclude(neighbor, closeSet)) {
                    double tempG = current.g + 1;
                    if (this.isInclude(neighbor, openSet)) {
                        if (tempG < neighbor.g) {
                            neighbor.g = tempG;
                        }
                    } else {
                        neighbor.g = tempG;
                        openSet.add(neighbor);
                    }

                    neighbor.h = this.heuristic2(neighbor, end);
                    neighbor.f = neighbor.h + neighbor.g;
                    neighbor.previous = current;
                } else {
                    double temmG = current.g + 1;
                    if (temmG < neighbor.g) {
                        openSet.add(neighbor);
                        int index = closeSet.indexOf(neighbor);
                        if (index > -1)
                            closeSet.remove(index);
                    }
                }
            }
        }
        System.out.println("Path not found");
        return null;
    }
}

