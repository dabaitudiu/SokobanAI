import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {

    static final String BFS = "BFS";
    static final String DFS = "DFS";
    static final String IDS = "IDS";
    static final String UCS = "UCS";
    static final String GREEDY_A = "GREEDY_EUCLIDEAN";
    static final String GREEDY_B = "GREEDY_MANHATTEN";
    static final String ASTAR_A = "A*_EUCLIDEAN";
    static final String ASTAR_B = "A*_MANHATTEN";

    public static void main(String[] args) throws FileNotFoundException {


        for (int i = 1; i <= 3; i++) {
            String filename = String.format("formal_inputs/input_10_10_2_%d.txt",i);
            System.out.println("Batch " + i + " : ");
            search(BFS, filename);
            search(DFS, filename);
            search(IDS, filename);
            search(UCS, filename);
            search(GREEDY_A, filename);
            search(GREEDY_B, filename);
            search(ASTAR_A, filename);
            search(ASTAR_B, filename);
            System.out.println();
        }

    }

    static void search(String method, String filename) throws FileNotFoundException{
        Sokoban sokoban = new Sokoban(filename);
//        sokoban.printMap();
        DeadLockDetector detector = new DeadLockDetector(sokoban);
        HashSet<Point> hst = detector.getDeadlock();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false, hst);

        Search search = new Search(false);

        // bfs
        if (method.equals(BFS)) search.bfs(new State(root));

        // dfs
        if (method.equals(DFS)) search.dfs(new State(root));

        // ids
        if (method.equals(IDS)) search.ids(new State(root));

        // ucs
        if (method.equals(UCS)) search.ucs(new State(root));

        // greedy
        if (method.equals(GREEDY_A))  search.greedy(new State(root),"euclidean");
        if (method.equals(GREEDY_B))  search.greedy(new State(root),"manhatten");

        // astar
        if (method.equals(ASTAR_A))  search.astar(new State(root),"euclidean");
        if (method.equals(ASTAR_B))  search.astar(new State(root),"manhatten");
    }

    static void parseMap() throws FileNotFoundException {
        MapParser mps = new MapParser("inputs/input01.txt");
        mps.parse();
    }
}
