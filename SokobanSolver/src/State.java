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

        // move up
        move(x-1,y,x-2,y,"u");
        move(x+1,y,x+2,y,"d");
        move(x,y-1,x,y-2,"l");
        move(x,y+1,x,y+2,"r");

        System.out.println("Its neighbors:");
        for (State e : neighbors) {
            System.out.println("("+x+","+y+") -> "+e.move.charAt(e.move.length()-1));
            e.loadMap();
            e.printMap();
        }
        return neighbors;
    }

    private boolean inbound(int x, int y) {
        if (x >= 0 && y >= 0 && x < rows && y < cols) return true;
        else return false;
    }

    public void move(int ax, int ay, int bx, int by, String s) {
        if (!inbound(ax,ay) || !inbound(bx,by)) return;
        Point attempt = new Point(ax,ay);
        Point newbox = new Point(bx,by);
        if (!walls.contains(attempt)) {
            if (!boxes.contains(attempt) || !boxes.contains(newbox) && !walls.contains(newbox)) {
                if (boxes.contains(attempt)) {
                    boxes.remove(attempt);
                    boxes.add(newbox);
                }
                neighbors.add(new State(walls,boxes,storages,attempt,move + s, rows, cols));
            }
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
        System.out.println("The map is as follows:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String s = "Current status: player at (" + player.getX() + "," + player.getY() + ")";
        return s;
    }

    @Override
    public int hashCode() {
        return player.getX() * 137 + player.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State s = (State) obj;
            return s.getPlayer().getX() == player.getX() && s.getPlayer().getY() == player.getY();
        } else return false;
    }

}
