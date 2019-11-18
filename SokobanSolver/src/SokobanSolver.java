import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {

//        MapParser mps = new MapParser("inputs/map1.txt");
//        mps.parse();

        Sokoban sokoban = new Sokoban("inputs/2.txt");
        sokoban.printMap();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth());
        Queue<State> queue = new LinkedList<>();
        Queue<State> visited = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            visited.add(curr);
            System.out.println("Current map: ---------------------");
            curr.loadMap();
            curr.printMap();
            System.out.println(curr.toString());
            if (curr.reachedGoal()) {
                System.out.println("****************Solution Found ******************");
                System.out.println(curr.getMove());
                break;
            } else {
                for (State e : curr.getNeighbors()) {
                    if (!visited.contains(e)) queue.add(e);
                }
            }
        }

    }
}
