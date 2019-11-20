import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {

//        MapParser mps = new MapParser("inputs/map3b.txt");
//        mps.parse();

        Sokoban sokoban = new Sokoban("/Users/itsyuezeng/Desktop/sokoban/sokoban2/sokoban/SokobanSolver/inputs/3.txt");
        sokoban.printMap();
        DeadLockDetect detector = new DeadLockDetect(sokoban);
        detector.findDeadLock();
//        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
//                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false);
//        Search search = new Search(root);
//
//        // bfs
////        search.bfs(new State(root));
//
//        // ucs
////        search.ucs(new State(root));
//
//        // greedy
//        search.greedy(new State(root),"euclidean");
//        search.greedy(new State(root),"manhatten");

    }
}
