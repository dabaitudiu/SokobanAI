import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {
//        parse();
        search();
    }

    static void parse() throws FileNotFoundException {
        MapParser mps = new MapParser("inputs/map_t8.txt");
        mps.parse();
    }

    static void search() throws FileNotFoundException{
        Sokoban sokoban = new Sokoban("inputs/t8.txt");
        DeadLockDetector detector = new DeadLockDetector(sokoban);
        HashSet<Point> hst = detector.getDeadlock();
        System.out.println("deadlocks: " + detector.toString());
        sokoban.printMap();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false, hst);



        Search search = new Search(false);

        // bfs
        search.bfs(new State(root));

        // dfs
//        search.dfs(new State(root));


        // ucs
//        search.ucs(new State(root));

        // greedy
//        search.greedy(new State(root),"euclidean");
//        search.greedy(new State(root),"manhatten");
    }
}
