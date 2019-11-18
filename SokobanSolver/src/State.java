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

    public State(HashSet<Point> walls, HashSet<Point> boxes, HashSet<Point> storages, Point player, String move) {
        this.walls = walls;
        this.boxes = boxes;
        this.storages = storages;
        this.player = player;
        neighbors = new ArrayList<>();
        this.move = move;
    }

    public List<State> getNeighbors() {
        int x = player.getX();
        int y = player.getY();

        // move up
        move(x-1,y,x-2,y,"u");
        move(x+1,y,x+2,y,"d");
        move(x,y-1,x,y-2,"l");
        move(x,y+1,x,y+2,"r");

        return neighbors;
    }

    public void move(int ax, int ay, int bx, int by, String s) {
        Point attempt = new Point(ax,ay);
        Point newbox = new Point(bx,by);
        if (!walls.contains(attempt)) {
            if (!boxes.contains(attempt) || !boxes.contains(newbox) && !walls.contains(newbox)) {
                if (boxes.contains(attempt)) {
                    boxes.remove(attempt);
                    boxes.add(newbox);
                }
                neighbors.add(new State(walls,boxes,storages,attempt,move + s));
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

}
