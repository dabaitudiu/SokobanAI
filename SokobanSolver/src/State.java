import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class State {
    private HashSet<Point> walls;
    private HashSet<Point> boxes;
    private HashSet<Point> storages;
    private Point player;
    private List<State> neighbors;
    private String move;
    private int rows;
    private int cols;
    private char[][] map;

    public State(HashSet<Point> walls, HashSet<Point> boxes, HashSet<Point> storages,
                 Point player, String move, int rows, int cols) {
        this.walls = walls;
        this.boxes = boxes;
        this.storages = storages;
        this.player = player;
        neighbors = new ArrayList<>();
        this.move = move;
        this.rows = rows;
        this.cols = cols;
    }

    public List<State> getNeighbors() {
        int x = player.getX();
        int y = player.getY();

        move(x-1,y,x-2,y,"u");
        move(x+1,y,x+2,y,"d");
        move(x,y-1,x,y-2,"l");
        move(x,y+1,x,y+2,"r");

//        System.out.println("Its neighbors:");
//        for (State e : neighbors) {
//            System.out.println("("+x+","+y+") -> "+e.move.charAt(e.move.length()-1));
//            e.loadMap();
//            e.printMap();
//        }
        return neighbors;
    }

    private boolean inbound(int x, int y) {
        if (x >= 1 && y >= 1 && x <= rows && y <= cols) return true;
        else return false;
    }

    public void move(int ax, int ay, int bx, int by, String s) {
        System.out.println(player.toString() + " move " + s + " :");
        if (!inbound(ax,ay) || !inbound(bx,by)) {
            System.out.println(" not ok.");
            return;
        }
        Point attempt = new Point(ax,ay);
        Point newbox = new Point(bx,by);
        boolean changed = false;
        if (!walls.contains(attempt)) {
            if (!boxes.contains(attempt) || !boxes.contains(newbox) && !walls.contains(newbox)) {
                if (boxes.contains(attempt)) {
                    boxes.remove(attempt);
                    boxes.add(newbox);
                    changed = true;
                    System.out.println("success: box moves to " + newbox.toString());
                }
                System.out.println("player moves to " + attempt.toString());
                State cur = new State(walls,new HashSet<>(boxes),storages,attempt,move + s, rows, cols);
                neighbors.add(cur);
                System.out.println("("+player.getX()+","+player.getY()+") -> "+s + " | total: " + cur.getMove());
                cur.loadMap();
                cur.printMap();
                if (changed) {
                    boxes.remove(newbox);
                    boxes.add(attempt);
                    changed = false;
                }
            } else {
                System.out.println(" not ok.");
            }
        } else {
            System.out.println(" not ok.");
        }
    }

    public boolean reachedGoal() {
        for (Point e : boxes) {
            if (!storages.contains(e)) return false;
        }
        return true;
    }

    public String getMove() {
        return move;
    }

    public Point getPlayer() {
        return player;
    }

    public void loadMap() {
        map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = ' ';
            }
        }
        for (Point e : walls) {
            map[e.getX()-1][e.getY()-1] = '#';
        }
        for (Point e : storages) {
            map[e.getX()-1][e.getY()-1] = '!';
        }
        for (Point e : boxes) {
            map[e.getX()-1][e.getY()-1] = '@';
        }
        map[player.getX()-1][player.getY()-1] = '*';
    }

    public void printMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String s = "Current status: player at (" + player.getX() + "," + player.getY() + ") with path "+ move;
        return s;
    }

    @Override
    public int hashCode() {
        int boxHashCode = 0;
        for (Point e : boxes) boxHashCode += e.hashCode();
        return player.getX() * 137 + player.getY() + boxHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State s = (State) obj;
            return s.hashCode() == hashCode();
        } else return false;
    }

}
