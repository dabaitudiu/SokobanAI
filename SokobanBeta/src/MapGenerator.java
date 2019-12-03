import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private int rows;
    private int cols;
    private HashSet<Point> walls;
    private HashSet<Point> boxes;
    private HashSet<Point> storages;
    private Point player;
    private Random random;
    private int numWalls;
    private int numBoxes;
    private String outputFile;

    public MapGenerator(int rows, int cols, int numWalls, int numBoxes, long time, String outputFile) {
        walls = new HashSet<>();
        boxes = new HashSet<>();
        storages = new HashSet<>();
        random = new Random(time);
        this.rows = rows;
        this.cols = cols;
        this.numWalls = numWalls;
        this.numBoxes = numBoxes;
        this.outputFile = outputFile;
    }

    public void generate(int maxTries) {
        int maxRecord = maxTries;
        while (maxTries-- > 0) {
            for (int i = 0; i < rows; i++) {
                walls.add(new Point(i + 1, 1));
                walls.add(new Point(i + 1, cols));
            }
            for (int j = 0; j < cols; j++) {
                walls.add(new Point(1, j + 1));
                walls.add(new Point(rows, j + 1));
            }
            for (int i = 0; i < numWalls; i++) {
                walls.add(new Point(random.nextInt(rows-2)+2, random.nextInt(cols-2)+2));
            }
            for (int j = 0; j < numBoxes;) {
                Point p = new Point(random.nextInt(rows-2)+2, random.nextInt(cols-2)+2);
                if (walls.contains(p)) continue;
                else {
                    boxes.add(p);
                    j++;
                }
            }
            for (int k = 0; k <numBoxes;) {
                Point p = new Point(random.nextInt(rows-2)+2, random.nextInt(cols-2)+2);
                if (walls.contains(p) ||  boxes.contains(p)) continue;
                else {
                    storages.add(p);
                    k++;
                }
            }

            for (int i = 0; i < maxRecord; i++) {
                player = new Point(random.nextInt(rows-2)+2, random.nextInt(cols-2)+2);
                if (!walls.contains(player) && !boxes.contains(player) && !storages.contains(player)) break;
            }

            System.out.println("Map Generation finished. Now begin testing.");
            Sokoban skb = new Sokoban(rows,cols,walls,boxes,storages,player);
            if (doSearch(skb,outputFile)) return;
            System.out.println("Model failed.");
            walls = new HashSet<>();
            boxes = new HashSet<>();
            storages = new HashSet<>();
        }
    }



    boolean doSearch(Sokoban sokoban, String outputFile){
        DeadLockDetector detector = new DeadLockDetector(sokoban);
        HashSet<Point> hst = detector.getDeadlock();
        sokoban.printMap();
        List<Graph> sequence = new ArrayList<>();
        State root = new State(sokoban.getWalls(),sokoban.getBoxes(),sokoban.getStorages(),
                sokoban.getPlayer(),"", sokoban.getHeight(), sokoban.getWidth(),false, hst,sequence);



        Search search = new Search(false, outputFile);

        // bfs
        boolean res = search.dfs(root);
        if (res) System.out.println("This Map works: (" + rows + "×"+cols+") " + "walls: " + numWalls + " boxes: " + boxes);
        if (res) sokoban.printMap();
        return res;

        // dfs
//        search.dfs(new State(root));


        // ucs
//        search.ucs(new State(root));

        // greedy
//        search.greedy(new State(root),"euclidean");
//        search.greedy(new State(root),"manhatten");
    }
}
