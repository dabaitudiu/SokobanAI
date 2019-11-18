import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {
        Sokoban sokoban = new Sokoban("inputs/1.txt");
        sokoban.printMap();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),sokoban.getPlayer(),"");
        Queue<State> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            if (curr.reachedGoal()) {
                System.out.println(curr.getMove());
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    queue.add(e);
                }
            }
        }

    }
}
