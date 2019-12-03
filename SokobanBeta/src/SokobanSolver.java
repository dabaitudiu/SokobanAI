import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SokobanSolver {

    public static void main(String[] args) throws FileNotFoundException {
//        parse();
//        search();
        String outputFile = "g04.txt";
        MapGenerator generator = new MapGenerator(10,10,20,1,System.currentTimeMillis(),outputFile);
        generator.generate(30);
    }

    static void parse() throws FileNotFoundException {
        MapParser mps = new MapParser("inputs/input01.txt");
        mps.parse();
    }

    static void search() throws FileNotFoundException{
        Sokoban sokoban = new Sokoban("inputs/t10.txt");
        DeadLockDetector detector = new DeadLockDetector(sokoban);
        HashSet<Point> hst = detector.getDeadlock();
        System.out.println("deadlocks: " + detector.toString());
        sokoban.printMap();
        List<Graph> sequence = new ArrayList<>();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false, hst,sequence);



        Search search = new Search(false, "entry_01.txt");

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
