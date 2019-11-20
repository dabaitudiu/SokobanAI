public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
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
        return x * 137 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate p = (Coordinate) obj;
            return (p.getX() == x && p.getY() == y);
        } else return false;
    }

    @Override
    public String toString() {
        return "Coordinate(" + x + "," + y + ")";
    }
}
