import java.util.HashSet;



public class GameGenerator {
    public int width;
    public int height;
    public HashSet<Point> wall;
    public HashSet<Point> dest;
    public HashSet<Point> box;
    public HashSet<Point> starter;


    public GameGenerator(){
/**
        input is 5 lines defining the board :

        i.     sixeH sizeV, e.g. "3 5"
        ii.     nWallSquares a list of coordinates of wall squares, e.g. "12 1 1 1 2 1 3 2 1 2 3 3 1 3 3 4 1 4 3 5 1 5 2 5 3"\
        iii.     nBoxes a list of coordinates of boxes, e.g. "1 3 2"
        iv.     nStorageLocations a list of coordinates of storage locations, e.g. "1 4 2"
        v.     player’s initial location x and y, e.g. "2 2"
 **/
//
//        String lines[] = input.split("\\r?\\n");
//        String dimension[] = lines[0].split("\\s+");
//        String wallList[] = lines[1].split("\\s+");
//        this.width = Integer.parseInt(dimension[0]);
//        this.height = Integer.parseInt(dimension[1]);

    }

    public int test(int a, int b) {
        return a + b;
    }

}
