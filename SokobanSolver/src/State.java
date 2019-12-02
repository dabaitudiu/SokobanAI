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
    private boolean verbose;
    private HashSet<Point> deadlocks;

    public State(HashSet<Point> walls, HashSet<Point> boxes, HashSet<Point> storages,
                 Point player, String move, int rows, int cols, boolean verbose, HashSet<Point> deadlocks) {
        this.walls = walls;
        this.boxes = boxes;
        this.storages = storages;
        this.player = player;
        neighbors = new ArrayList<>();
        this.move = move;
        this.rows = rows;
        this.cols = cols;
        this.verbose = verbose;
        this.deadlocks = new HashSet<>(deadlocks);
    }

    public State(State s) {
        this.walls = s.walls;
        this.walls = s.walls;
        this.boxes = s.boxes;
        this.storages = s.storages;
        this.player = s.player;
        neighbors = new ArrayList<>();
        this.move = s.move;
        this.rows = s.rows;
        this.cols = s.cols;
        this.verbose = s.verbose;
        this.deadlocks = s.deadlocks;
    }

    public List<State> getNeighbors() {
        int x = player.getX();
        int y = player.getY();

        move(x-1,y,x-2,y,"u",verbose);
        move(x+1,y,x+2,y,"d",verbose);
        move(x,y-1,x,y-2,"l",verbose);
        move(x,y+1,x,y+2,"r",verbose);

        return neighbors;
    }

    private boolean inbound(int x, int y) {
        if (x >= 1 && y >= 1 && x <= rows && y <= cols) return true;
        else return false;
    }

    public void move(int ax, int ay, int bx, int by, String s, boolean verbose) {
        if (verbose) System.out.println(player.toString() + " move " + s + " :");
        if (!inbound(ax,ay) || !inbound(bx,by)) {
            if (verbose) System.out.println(" not ok.");
            return;
        }
        Point attempt = new Point(ax,ay);
        Point newbox = new Point(bx,by);
        boolean changed = false;

        if (!walls.contains(attempt)) {
            if (!boxes.contains(attempt) || !boxes.contains(newbox) && !walls.contains(newbox)) {
                if (boxes.contains(attempt)) {
                    if (deadlocks.contains(newbox)) return;
                    boxes.remove(attempt);
                    boxes.add(newbox);
                    changed = true;
                    if (verbose) System.out.println("success: box moves to " + newbox.toString());
                }
                if (verbose) System.out.println("player moves to " + attempt.toString());
                State cur = new State(walls,new HashSet<>(boxes),new HashSet<>(storages),attempt,move + s, rows, cols,verbose, deadlocks);
                neighbors.add(cur);
                if (verbose) {
                    System.out.println("("+player.getX()+","+player.getY()+") -> " +s + " | total: " + cur.getMove());
                    cur.loadMap();
                    cur.printMap();
                }
                if (changed) {
                    boxes.remove(newbox);
                    boxes.add(attempt);
                    changed = false;
                }
            } else {
                if (verbose) System.out.println(" not ok.");
            }
        } else {
            if (verbose) System.out.println(" not ok.");
        }
    }

    public boolean reachedGoal() {
        for (Point e : boxes) {
            if (!storages.contains(e)) return false;
        }
        return true;
    }

    public int euclidean() {
        int x = player.getX();
        int y = player.getY();

        int playerToBoxes = 0;
        for (Point e : boxes) {
            int bx = e.getX();
            int by = e.getY();
            playerToBoxes += Math.sqrt((x-bx)*(x-bx) + (y-by)*(y-by));
        }

        int boxesToStorages = 0;
        for (Point e : storages) {
            int ex = e.getX();
            int ey = e.getY();
            for (Point m : boxes) {
                int mx = m.getX();
                int my = m.getY();
                boxesToStorages += Math.sqrt((ex-mx)*(ex-mx) + (ey-my)*(ey-my));
            }
        }
        return playerToBoxes+boxesToStorages;
    }

    public int manhatten() {
        int x = player.getX();
        int y = player.getY();

        int playerToBoxes = 0;
        for (Point e : boxes) {
            int bx = e.getX();
            int by = e.getY();
            playerToBoxes += Math.abs(x-bx) + Math.abs(y-by);
        }

        int boxesToStorages = 0;
        for (Point e : storages) {
            int ex = e.getX();
            int ey = e.getY();
            for (Point m : boxes) {
                int mx = m.getX();
                int my = m.getY();
                boxesToStorages += Math.abs(ex-mx) + Math.abs(ey-my);
            }
        }
        return playerToBoxes+boxesToStorages;
    }

    public int singleMatch() {
        int curr = Integer.MAX_VALUE;
        int x = player.getX();
        int y = player.getY();
        for (Point e : boxes) {
            int ex = e.getX();
            int ey = e.getY();
            int sum = (x - ex) * (x - ex) + (y - ey) * (y - ey);
            if (sum < curr) curr = sum;
        }
        int cur2 = Integer.MAX_VALUE;
        for (Point e : storages) {
            int ex = e.getX();
            int ey = e.getY();
            int sum = (x - ex) * (x - ex) + (y - ey) * (y - ey);
            if (sum < cur2) cur2 = sum;
        }
        return curr + cur2;
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

    public HashSet<Point> getBoxes() {
        return boxes;
    }


    @Override
    public String toString() {
        String s = "Current status: player at (" + player.getX() + "," + player.getY() + ") with path "+ move
                + " and hashcode = " + hashCode();
        return s;
    }

    @Override
    public int hashCode() {
        int boxHashCode = 0;
        for (Point e : boxes) {
            boxHashCode += e.hashCode();
            boxHashCode *= 37;
        }
        return player.getX() * 73 +  player.getY() + boxHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State s = (State) obj;
            HashSet<Point> boxes1 = boxes;
            HashSet<Point> boxes2 = s.getBoxes();
            Point player2 = s.getPlayer();
            return boxes1.equals(boxes2) && player.equals(player2);
        } else return false;
    }

}
