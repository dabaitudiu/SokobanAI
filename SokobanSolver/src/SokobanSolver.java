import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SokobanSolver {
    public static void main(String[] args) throws FileNotFoundException {

//        MapParser mps = new MapParser("inputs/map3d.txt");
//        mps.parse();
//
        Sokoban sokoban = new Sokoban("inputs/3c.txt");
        sokoban.printMap();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false);



        Search search = new Search(false);

        // bfs
//        search.bfs(new State(root));

        // dfs
//        search.dfs(new State(root));


        // ucs
//        search.ucs(new State(root));

        // greedy
        search.greedy(new State(root),"euclidean");
//        search.greedy(new State(root),"singleMatch");

    }
}
