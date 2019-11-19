import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {

//        MapParser mps = new MapParser("inputs/map3.txt");
//        mps.parse();

        Sokoban sokoban = new Sokoban("inputs/3.txt");
        sokoban.printMap();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false);
        Search search = new Search(root);

        // bfs
        search.bfs(new State(root));
    }
}
