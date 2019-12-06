import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SokobanSolver {

    public static void main(String[] args) throws FileNotFoundException {
//        parse();
//        search();
        int n = 5;
        int start = 1;
        int index = start;
        for (int i = start; i < n+start; i++) {
            System.out.println("--------------  Forming model " + (index++) + "  --------------");
            String outputFile = String.format("formal_inputs/input_9_9_3_%d.txt",i);
            MapGenerator generator = new MapGenerator(9,9,10,3,System.currentTimeMillis(),outputFile);
            generator.generate(20);
        }
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
//        search.bfs(new State(root));

        // dfs
        search.dfs(new State(root));


        // ucs
//        search.ucs(new State(root));

        // greedy
//        search.greedy(new State(root),"euclidean");
//        search.greedy(new State(root),"manhatten");
    }
}
