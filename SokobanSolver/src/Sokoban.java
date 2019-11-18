import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public  class Sokoban {
    private int width;
    private int height;
    private HashSet<Point> walls;
    private HashSet<Point> boxes;
    private HashSet<Point> storages;
    private Point player;
    private char[][] map;

    public Sokoban(String filename) throws FileNotFoundException{
        walls = new HashSet<>();
        storages = new HashSet<>();
        boxes = new HashSet<>();
        readInput(filename);
        loadMap();
    }

    private void readInput(String filename) throws FileNotFoundException{
        Scanner s = new Scanner(new File(filename));
        String[] sizes = s.nextLine().split(" ");
        String[] nWalls = s.nextLine().split(" ");
        String[] nBoxes = s.nextLine().split(" ");
        String[] nStorages = s.nextLine().split(" ");
        String[] pos = s.nextLine().split(" ");

        // handle sizes
        width = Integer.parseInt(sizes[0]);
        height = Integer.parseInt(sizes[1]);
        System.out.println("width : " + width + " height : " + height);

        // handle objects
        load(nWalls, walls, "walls");
        load(nBoxes,boxes, "boxes");
        load(nStorages,storages, "storages");

        // handle player
        player = new Point(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
        System.out.println("Player at: " + player.toString());

    }

    private void load(String[] arr, HashSet<Point> hst, String name) {
        int num = Integer.parseInt(arr[0]);
        System.out.print("totoal " + name + " : " + num + " -> ");
        for (int i = 0; i < num; i++) {
            hst.add(new Point(Integer.parseInt(arr[2 * i + 1]),Integer.parseInt(arr[2 * i + 2])));
        }
        hst.forEach(e -> {System.out.print(e.toString() + " ");});
        System.out.println();
    }

    private void loadMap() {
        map = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = ' ';
            }
        }
        for (Point e : walls) {
            map[e.getX()-1][e.getY()-1] = '#';
        }
        for (Point e : boxes) {
            map[e.getX()-1][e.getY()-1] = '@';
        }
        for (Point e : storages) {
            map[e.getX()-1][e.getY()-1] = '!';
        }
        map[player.getX()-1][player.getY()-1] = '*';
    }

    public void printMap() {
        System.out.println("-------------Initial Map---------------------");
        System.out.println("The map is as follows:");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public HashSet<Point> getWalls() {
        return walls;
    }

    public void setWalls(HashSet<Point> walls) {
        this.walls = walls;
    }

    public HashSet<Point> getStorages() {
        return storages;
    }

    public void setStorages(HashSet<Point> storages) {
        this.storages = storages;
    }

    public HashSet<Point> getBoxes() {
        return boxes;
    }

    public void setBoxes(HashSet<Point> boxes) {
        this.boxes = boxes;
    }

    public Point getPlayer() {
        return player;
    }

    public void setPlayer(Point player) {
        this.player = player;
    }
}
