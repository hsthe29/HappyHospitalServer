package algorithm;

import classes.Node2D;
import classes.Position;
import classes.StateOfNode2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AStarDirectedPath {
    private final int width;
    private final int height;
    private ArrayList<Position> pathPos;
    private Node2D[][] nodes;

    public AStarDirectedPath(int width,
                             int height,
                             ArrayList<ArrayList<ArrayList<Position>>> adjacencyList,
                             ArrayList<Position> pathPos
    ) {
        this.width = width;
        this.height = height;
        this.nodes = new Node2D[width][height];
        this.pathPos = pathPos;

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                this.nodes[i][j] = new Node2D(i, j, false, 0.05, 2000, 3000);
            }
        }

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < adjacencyList.get(i).get(j).size(); k++) {
                    Position adjacencyNode = adjacencyList.get(i).get(j).get(k);
                    this.nodes[i][j].setNeighbor(this.nodes[adjacencyNode.dx][adjacencyNode.dy]);
                }
            }
        }
    }

    public boolean isInclude(Node2D node, ArrayList<Node2D> nodes) {
        for (Node2D node2D : nodes) {
            if (node.equal(node2D)) return true;
        }
        return false;
    }

    public int heuristic(Node2D node1, Node2D node2) {
        return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
    }

    public ArrayList<Position> calPathAStar(Position startPos, Position endPos) {
        /**
         * Khoi tao cac bien trong A*
         */
        Node2D start = new Node2D(startPos.dx, startPos.dy, false, 0.05, 2000, 3000);
        Node2D end = new Node2D(endPos.dx, endPos.dy, false, 0.05, 2000, 3000);

        ArrayList<Node2D> openSet = new ArrayList<>();
        ArrayList<Node2D> closeSet = new ArrayList<>();
        ArrayList<Node2D> path = new ArrayList<>();
        double[][] astar_f = new double[this.width][this.height];
        double[][] astar_g = new double[this.width][this.height];
        int[][] astar_h = new int[this.width][this.height];
        Node2D[][] previous = new Node2D[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                astar_f[i][j] = 0;
                astar_g[i][j] = 0;
                astar_h[i][j] = 0;
            }
        }
        int lengthOfPath = 1;
        /**
         * Thuat toan
         */
        openSet.add(this.nodes[start.x][start.y]);
        while (openSet.size() > 0) {
            int winner = 0;
            for (int i = 0; i < openSet.size(); i++) {
                if (
                        astar_f[openSet.get(i).x][openSet.get(i).y] < astar_f[openSet.get(winner).x][openSet.get(winner).y]
                ) {
                    winner = i;
                }
            }
            Node2D current = openSet.get(winner);
            if (openSet.get(winner).equal(end)) {
                Node2D cur = this.nodes[end.x][end.y];
                path.add(cur);
                while (previous[cur.x][cur.y] != null) {
                    path.add(previous[cur.x][cur.y]);
                    cur = previous[cur.x][cur.y];
                }
                Collections.reverse(path);
                ArrayList<Position> result = new ArrayList<>(path.size());
                for(int k = 0; k < path.size(); k++) {
                    result.add(new Position(path.get(k).x, path.get(k).y));
                }
                //console.assert(lengthOfPath == path.length, "path has length: " + path.length + " instead of " + lengthOfPath);
                return result;
            }
            openSet.remove(winner);
            closeSet.add(current);
            ArrayList<Node2D> neighbors = new ArrayList<>(Arrays.asList(current.nodeN, current.nodeE, current.nodeS, current.nodeW,
                    current.nodeVN, current.nodeVE, current.nodeVS, current.nodeVW));
            /**/
//            System.out.println(neighbors);

            for (Node2D neighbor : neighbors) {
                if (neighbor != null) {
                    int timexoay = 0;
                    if (
                            previous[current.x][current.y] != null &&
                                    neighbor.x != previous[current.x][current.y].x &&
                                    neighbor.y != previous[current.x][current.y].y
                    ) {
                        timexoay = 1;
                    }
                    double tempG =
                            astar_g[current.x][current.y] + 1 + current.getW() + timexoay;

                    if (!this.isInclude(neighbor, closeSet)) {
                        if (this.isInclude(neighbor, openSet)) {
                            if (tempG < astar_g[neighbor.x][neighbor.y]) {
                                astar_g[neighbor.x][neighbor.y] = tempG;
                            }
                        } else {
                            astar_g[neighbor.x][neighbor.y] = tempG;
                            openSet.add(neighbor);
                            lengthOfPath++;
                        }
                        astar_h[neighbor.x][neighbor.y] = this.heuristic(neighbor, end);
                        astar_f[neighbor.x][neighbor.y] = astar_h[neighbor.x][neighbor.y] + astar_g[neighbor.x][neighbor.y];
                        previous[neighbor.x][neighbor.y] = current;
                    } else {
                        if (tempG < astar_g[neighbor.x][neighbor.y]) {
                            openSet.add(neighbor);
                            int index = closeSet.indexOf(neighbor);
                            if (index > -1) {
                                closeSet.remove(index);
                            }
                        }
                    }
                }
            }
        }//end of while (openSet.length > 0)
        System.out.println("Path not found!");
        return null;
    }
}
