public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p = (Point) obj;
            return (p.getX() == x && p.getY() == y);
        } else return false;
    }

    @Override
    public String toString() {
        return "Point(" + x + "," + y + "):" + hashCode();
    }
}
