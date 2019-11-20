
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class State {
    private HashSet<Coordinate> walls;
    private HashSet<Coordinate> boxes;
    private HashSet<Coordinate> storages;
    private Coordinate player;
    private List<State> neighbors;
    private String move;
    private int rows;
    private int cols;
    private char[][] map;
    private boolean verbose;

    public State(HashSet<Coordinate> walls, HashSet<Coordinate> boxes, HashSet<Coordinate> storages,
                 Coordinate player, String move, int rows, int cols, boolean verbose) {
        this.walls = walls;
        this.boxes = boxes;
        this.storages = storages;
        this.player = player;
        neighbors = new ArrayList<>();
        this.move = move;
        this.rows = rows;
        this.cols = cols;
        this.verbose = verbose;
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
    }

    public List<State> getNeighbors() {
        int x = player.getX();
        int y = player.getY();


        if (verbose){
            moveVerbose(x-1,y,x-2,y,"u");
            moveVerbose(x+1,y,x+2,y,"d");
            moveVerbose(x,y-1,x,y-2,"l");
            moveVerbose(x,y+1,x,y+2,"r");
        } else {
            move(x-1,y,x-2,y,"u");
            move(x+1,y,x+2,y,"d");
            move(x,y-1,x,y-2,"l");
            move(x,y+1,x,y+2,"r");
        }

        return neighbors;
    }

    private boolean inbound(int x, int y) {
        if (x >= 1 && y >= 1 && x <= rows && y <= cols) return true;
        else return false;
    }

    public void move(int ax, int ay, int bx, int by, String s) {
        if (!inbound(ax,ay) || !inbound(bx,by)) {
            return;
        }
        Coordinate attempt = new Coordinate(ax,ay);
        Coordinate newbox = new Coordinate(bx,by);
        boolean changed = false;
        if (!walls.contains(attempt)) {
            if (!boxes.contains(attempt) || !boxes.contains(newbox) && !walls.contains(newbox)) {
                if (boxes.contains(attempt)) {
                    boxes.remove(attempt);
                    boxes.add(newbox);
                    changed = true;
                }
                State cur = new State(walls,new HashSet<>(boxes),storages,attempt,move + s, rows, cols,verbose);
                neighbors.add(cur);
                if (changed) {
                    boxes.remove(newbox);
                    boxes.add(attempt);
                    changed = false;
                }
            }
        }
    }

    public void moveVerbose(int ax, int ay, int bx, int by, String s) {
        System.out.println(player.toString() + " move " + s + " :");
        if (!inbound(ax,ay) || !inbound(bx,by)) {
            System.out.println(" not ok.");
            return;
        }
        Coordinate attempt = new Coordinate(ax,ay);
        Coordinate newbox = new Coordinate(bx,by);
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
                State cur = new State(walls,new HashSet<>(boxes),storages,attempt,move + s, rows, cols,verbose);
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

    /**
     * - case 1 :
     *   #  or  # @  or  #   or  @ #
     * # @        #      @ #     #
     *
     * - case 2:
     * ## or #@  or  @#  or  @@
     * @@    #@      @#      ##
     *
     *
     * - case 3:
     * #####  or  #?@?#  or  # #  or  # #
     * #?@?#      #####      # ?      ? #
     *                       # @      @ #
     *                       # ?      ? #
     *                       # #      # #
     * @return
     */
    public boolean isDeadLock() {
        for (Coordinate e : boxes) {
            int x = e.getX();
            int y = e.getY();

            // case 1
            //   #  or  # @  or  #   or  @ #
            // # @        #      @ #     #
            if (walls.contains(new Coordinate(x-1,y)) && walls.contains(new Coordinate(x,y-1))) return true;
            if (walls.contains(new Coordinate(x+1,y)) && walls.contains(new Coordinate(x,y-1))) return true;
            if (walls.contains(new Coordinate(x-1,y)) && walls.contains(new Coordinate(x,y+1))) return true;
            if (walls.contains(new Coordinate(x+1,y)) && walls.contains(new Coordinate(x,y+1))) return true;

            // case 2
            // ## or #@  or  @#  or  @@
            // @@    #@      @#      ##
//            if (walls.contains(new Point(x-1,y)) && walls.contains(new Point(x-1,y-1)) &&
//                boxes.contains(new Point(x,y-1))) return true;
//            if (walls.contains(new Point(x,y-1)) && walls.contains(new Point(x+1,y-1)) &&
//                    boxes.contains(new Point(x+1,y))) return true;
//            if (walls.contains(new Point(x,y+1)) && walls.contains(new Point(x+1,y+1)) &&
//                    boxes.contains(new Point(x+1,y))) return true;
//            if (walls.contains(new Point(x+1,y)) && walls.contains(new Point(x+1,y+1)) &&
//                    boxes.contains(new Point(x,y+1))) return true;
        }
        return false;

    }

    public boolean reachedGoal() {
        for (Coordinate e : boxes) {
            if (!storages.contains(e)) return false;
        }
        return true;
    }

    public int euclidean() {
        int x = player.getX();
        int y = player.getY();

        int playerToBoxes = 0;
        for (Coordinate e : boxes) {
            int bx = e.getX();
            int by = e.getY();
            playerToBoxes += Math.sqrt((x-bx)*(x-bx) + (y-by)*(y-by));
        }

        int boxesToStorages = 0;
        for (Coordinate e : storages) {
            int ex = e.getX();
            int ey = e.getY();
            for (Coordinate m : boxes) {
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
        for (Coordinate e : boxes) {
            int bx = e.getX();
            int by = e.getY();
            playerToBoxes += Math.abs(x-bx) + Math.abs(y-by);
        }

        int boxesToStorages = 0;
        for (Coordinate e : storages) {
            int ex = e.getX();
            int ey = e.getY();
            for (Coordinate m : boxes) {
                int mx = m.getX();
                int my = m.getY();
                boxesToStorages += Math.abs(ex-mx) + Math.abs(ey-my);
            }
        }
        return playerToBoxes+boxesToStorages;
    }

    public String getMove() {
        return move;
    }

    public Coordinate getPlayer() {
        return player;
    }

    public void loadMap() {
        map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = ' ';
            }
        }
        for (Coordinate e : walls) {
            map[e.getX()-1][e.getY()-1] = '#';
        }
        for (Coordinate e : storages) {
            map[e.getX()-1][e.getY()-1] = '!';
        }
        for (Coordinate e : boxes) {
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
        String s = "Current status: player at (" + player.getX() + "," + player.getY() + ") with path "+ move
                + " and hashcode = " + hashCode();
        return s;
    }

    @Override
    public int hashCode() {
        int boxHashCode = 0;
        for (Coordinate e : boxes) boxHashCode += e.hashCode();
        return player.getX() * 139 + player.getY() + boxHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State) {
            State s = (State) obj;
            return s.hashCode() == this.hashCode();
        } else return false;
    }

}
