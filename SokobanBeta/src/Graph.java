import java.io.PrintWriter;
import java.util.HashSet;

public class Graph {
    int row;
    int col;
    private HashSet<Point> walls;
    private HashSet<Point> boxes;
    private HashSet<Point> storages;
    private Point player;
    private int[][] graph;
    final int len = 20;

    public Graph(int row, int col, HashSet<Point> walls, HashSet<Point> boxes, HashSet<Point> storages, Point player) {
        this.walls = walls;
        this.boxes = boxes;
        this.storages = storages;
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public int[][] getGraph() {
        int x = (len - row) / 2;
        int y = (len - col) / 2;
        graph = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                graph[i][j] = 0;
            }
        }
        for (int i = x; i < x + row; i++) {
            for (int j = y; j < y + col; j++) {
                graph[i][j] = 50;
            }
        }
        for (Point e : walls) {
            graph[x + e.getX()-1][y + e.getY()-1] = 0;
        }
        for (Point e : boxes) {
            graph[x + e.getX()-1][y + e.getY()-1] = 150;
        }
        for (Point e : storages) {
            graph[x + e.getX() - 1][y + e.getY() - 1] = 100;
        }
        graph[x + player.getX() - 1][y + player.getY()-1] = 250;

        return graph;
    }

    public void printCharGraph() {
            int x = (len - row) / 2;
            int y = (len - col) / 2;
            char[][] tmpgraph = new char[len][len];
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    tmpgraph[i][j] = '#';
                }
            }
            for (int i = x; i < x + row; i++) {
                for (int j = y; j < y + col; j++) {
                    tmpgraph[i][j] = ' ';
                }
            }
            for (Point e : walls) {
                tmpgraph[x + e.getX()-1][y + e.getY()-1] = '#';
            }
            for (Point e : storages) {
                tmpgraph[x + e.getX() - 1][y + e.getY() - 1] = '.';
            }
            for (Point e : boxes) {
                if (storages.contains(e)) tmpgraph[x + e.getX()-1][y + e.getY()-1] = '*';
                else tmpgraph[x + e.getX()-1][y + e.getY()-1] = '$';
            }

            tmpgraph[x + player.getX() - 1][y + player.getY()-1] = '@';
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(String.format("%c ",tmpgraph[i][j]));
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");
    }

    public void printGraph() {
        getGraph();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                System.out.print(String.format("%3d ",graph[i][j]));
            }
            System.out.println();
        }
    }

    public void printGraphToFile(PrintWriter printWriter, char s) {
        getGraph();
        int dir = 0;
        if (s == 'u') dir = 0;
        else if (s == 'd') dir = 1;
        else if (s == 'l') dir = 2;
        else if (s == 'r') dir = 3;
        else dir = -1;
        printWriter.print(dir + " ");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                printWriter.print(String.format("%d ",graph[i][j]));
            }
        }
        printWriter.print(",");
    }

}
