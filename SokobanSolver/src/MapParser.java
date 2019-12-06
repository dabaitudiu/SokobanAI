import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapParser {
    char[][] map;
    int row;
    int col;

    /**
     * The Map Parser function reads a characterized map, and maps into a formal input.
     * @param filename
     * @throws FileNotFoundException
     */
    public MapParser(String filename) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filename));
        List<String> lines = new ArrayList<>();
        while(s.hasNextLine()) {
            lines.add(s.nextLine());
        }
        row = lines.size();
        col = lines.get(0).length();
        map = new char[row][col];

        for (int i = 0; i < row; i++) {
            char[] cs = lines.get(i).toCharArray();
            for (int j = 0; j < col; j++) {
                map[i][j] = cs[j];
            }
        }
    }

    void parse() {

        System.out.println(col + " " + row);

        int numWalls = 0;
        int numBoxes = 0;
        int numStorages = 0;
        List<Integer> walls = new ArrayList<>();
        List<Integer> boxes = new ArrayList<>();
        List<Integer> storages = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                switch (map[i][j]) {
                    case '#':
                        walls.add(i+1);
                        walls.add(j+1);
                        numWalls++;
                        break;
                    case '$':
                        boxes.add(i+1);
                        boxes.add(j+1);
                        numBoxes++;
                        break;
                    case '.':
                        storages.add(i+1);
                        storages.add(j+1);
                        numStorages++;
                        break;
                    case '@':
                        pos.add(i+1);
                        pos.add(j+1);
                        break;
                }
            }
        }
        System.out.print(numWalls + " ");
        walls.forEach(e -> {System.out.print(e + " ");});
        System.out.println();

        System.out.print(numBoxes + " ");
        boxes.forEach(e -> {System.out.print(e + " ");});
        System.out.println();

        System.out.print(numStorages + " ");
        storages.forEach(e -> {System.out.print(e + " ");});
        System.out.println();

        pos.forEach(e -> {System.out.print(e + " ");});
        System.out.println();

    }
}
