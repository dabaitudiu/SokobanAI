import java.util.HashSet;

public class DeadLockDetector {
    Sokoban soku;
    HashSet<Point> walls;
    HashSet<Point> storages;
    HashSet<Point> deadlocks;
    int width;
    int height;
    private char[][] map;


    public DeadLockDetector(Sokoban soku){
        this.soku = soku;
        this.walls= this.soku.getWalls();
        this.storages= this.soku.getStorages();
        this.width = this.soku.getWidth();
        this.height = this.soku.getHeight();
        this.map = new char[height][width];
        deadlocks = new HashSet<Point>();
        loadMap();
    }

    public void findDeadLock(){

        int width = soku.getWidth();
        int height = soku.getHeight();


        for(int i=1; i<=height; i++){
            for(int j=1; j<=width; j++){
                Point current = new Point(i,j);
                if (current!= null && !this.walls.contains(current) && !this.storages.contains(current)){

                    if(cornorTest(current)){
                        try {
                            this.deadlocks.add(current);
                            System.out.println(String.format("**Cornor Deadlock Found**( %d , %d)", current.getX(), current.getY()));
                            map[i - 1][j - 1] = '^';
                            printMap();
//                            System.out.println("-----------------------------------------");
                        }
                        catch(NullPointerException e){
//                            System.out.println("Something went wrong.");
                        }
                    }

                    else if(boundaryTest(current)){
                        try {
                            this.deadlocks.add(current);
//                            System.out.println(String.format("**Boundary Deadlock Found**( %d , %d)", current.getX(), current.getY()));
                            map[i - 1][j - 1] = '?';
//                            printMap();
//                            System.out.println("----------------------------------------");
                        }
                        catch(NullPointerException e){
//                            System.out.println("Something went wrong.");
                        }
                    }

                }

                else{
//                    System.out.println(String.format( "~~is wall || storage~~ ( %d , %d)" , current.getX() , current.getY()) );
                }

            }
        }

    }

    private void loadMap() {
        int rows = height;
        int cols = width;
        map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = ' ';
            }
        }
        for (Point e : walls) {
            map[e.getX()-1][e.getY()-1] = '#';
        }
        for (Point e : storages) {
            map[e.getX()-1][e.getY()-1] = '!';
        }
        for (Point e : soku.getBoxes()) {
            map[e.getX()-1][e.getY()-1] = '@';
        }
        map[soku.getPlayer().getX()-1][soku.getPlayer().getY()-1] = '*';
    }

    private void printMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean cornorTest(Point current){
//        Point up = new Point(current.getX(), current.getY()+1);
//        Point down = new Point(current.getX(), current.getY()-1);
//        Point right = new Point(current.getX()+1, current.getY());
//        Point left = new Point(current.getX()-1, current.getY());

        Point up = getUpNeighbor(current);
        Point down = getdownNeighbor(current);
        Point right = getRightNeighbor(current);
        Point left = getLeftNeighbor(current);

        if(this.walls.contains(up) && this.walls.contains(right)||
                this.walls.contains(up) && this.walls.contains(left)||
                this.walls.contains(down) && this.walls.contains(left)||
                this.walls.contains(down) && this.walls.contains(right)){
            return true;
        }
        return false;
    }

    private boolean boundaryTest(Point current){


        int x = current.getX();
        int y = current.getY();

//        System.out.println(String.format( "____________boundary test_________  :  ( %d , %d) " , x , y ));


        if(this.walls.contains(getLeftNeighbor(current))){
            System.out.println("Left wall...");
            int upbound = this.findNearestUpbound(current);
            int downbound = this.findNearestDownbound(current);

            if(upbound==-1 || downbound ==-1){
                return false;
            }

            else {
                for (int m = upbound + 1; m < downbound; m++) {
                    Point b = new Point(m,y-1);
                    if (!this.walls.contains(b)) {
//                        System.out.println(String.format( "<<wall gap>>( %d , %d)" , b.getX() , b.getY()) );
                        return false;
                    }
                }
            }

            return true;
        }

        if(this.walls.contains(getRightNeighbor(current))){
            System.out.println("Right wall...");
            int upbound = this.findNearestUpbound(current);
            int downbound = this.findNearestDownbound(current);

            if(upbound==-1 || downbound ==-1){
                return false;
            }

            else {
                for (int m = upbound + 1; m < downbound; m++) {
                    Point b = new Point(m,y+1);
                    if (!this.walls.contains(b)) {
//                        System.out.println(String.format( "<<wall gap>>( %d , %d)" , b.getX() , b.getY()) );
                        return false;
                    }
                }

            }
            return true;
        }

        if(this.walls.contains(getUpNeighbor(current))){
            System.out.println("Up wall...");
            int rightbound = this.findNearestRightbound(current);
            int leftbound = this.findNearestLeftbound(current);

            if(leftbound==-1 || rightbound ==-1){

//                System.out.println("storage in the way" );
                return false;
            }

            else {
                for (int m = leftbound + 1; m < rightbound; m++) {
                    Point b = new Point(x-1,m);
                    if (!this.walls.contains(b)) {
//                        System.out.println(String.format( "<<wall gap>>( %d , %d)" , b.getX() , b.getY()) );
                        return false;
                    }
                }
            }
            return true;
        }

        if(this.walls.contains(getdownNeighbor(current))){
            System.out.println("Down wall...");
            int rightbound = this.findNearestRightbound(current);
            int leftbound = this.findNearestLeftbound(current);

            if(leftbound==-1 || rightbound ==-1){
                return false;
            }

            else {
                for (int m = leftbound + 1; m < rightbound; m++) {
                    Point b = new Point(x+1,m);
                    if (!this.walls.contains(b)) {
//                        System.out.println(String.format( "<<wall gap>>( %d , %d)" , b.getX() , b.getY()) );
                        return false;
                    }
                }
            }
            return true;
        }

        return false;

    }

    private Point getRightNeighbor (Point current){
        return (new Point(current.getX(), current.getY()+1));
    }

    private Point getLeftNeighbor (Point current){
        return (new Point(current.getX(), current.getY()-1));
    }
    private Point getUpNeighbor (Point current){
        return (new Point(current.getX()-1, current.getY()));
    }
    private Point getdownNeighbor (Point current){
        return (new Point(current.getX()+1, current.getY()));
    }

    private int findNearestUpbound(Point a){
        int y = a.getY();
        int x = a.getX()-1;

        while(x>=0){
            Point temp = new Point(x,y);
            if(this.walls.contains(temp)){
//                System.out.println( String.format("~~upper bound  :  ( %d ) " , x ));
                return x;
            }
            else if(this.storages.contains(temp)){
//                System.out.println( String.format("~~upper bound  :  ( %d ) " , -1 ));
                return -1;
            }
            x-=1;
        }
//        System.out.println( String.format("~~upper bound  :  ( %d ) " , 0 ));
        return 0;
    }


    private int findNearestDownbound(Point a){
        int y = a.getY();
        int x = a.getX()+1;

        while(x<this.height){
            Point temp = new Point(x,y);
            if(this.walls.contains(temp)){
//                System.out.println( String.format("~~lower bound  :  ( %d ) " , x ));
                return x;
            }
            else if(this.storages.contains(temp)){
//                System.out.println( String.format("~~lower bound  :  ( %d ) " , -1 ));
                return -1;
            }
            x+=1;
        }
//        System.out.println( String.format("~~lower bound  :  ( %d ) " , this.height ));
        return this.height;
    }

    private int findNearestRightbound(Point a){
        int y = a.getY()+1;
        int x = a.getX();

        while(y<this.width){
            Point temp = new Point(x,y);
            if(this.walls.contains(temp)){
//                System.out.println( String.format("~~right bound  :  ( %d ) " , y ));
                return y;
            }
            else if(this.storages.contains(temp)){
//                System.out.println( String.format("~~right bound  :  ( %d ) " , -1 ));
                return -1;
            }
            y+=1;
        }
//        System.out.println( String.format("~~right bound  :  ( %d ) " , this.width ));
        return this.width;
    }

    private int findNearestLeftbound(Point a){
        int y = a.getY()-1;
        int x = a.getX();

        while(y>=0){
            Point temp = new Point(x,y);
            if(this.walls.contains(temp)){
//                System.out.println( String.format("~~left bound  :  ( %d ) " , y ));
                return y;
            }
            else if(this.storages.contains(temp)){
//                System.out.println( String.format("~~left bound  :  ( %d ) " , -1 ));
                return -1;
            }
            y-=1;
        }
//        System.out.println( String.format("~~right bound  :  ( %d ) " , 0 ));
        return 0;
    }

    public HashSet<Point>getDeadlock(){
        findDeadLock();
        return new HashSet<>(deadlocks);

    }

    @Override
    public String toString() {
        String res = "";
        for (Point e : deadlocks) {
            res += e.toString();
            res += " ";
        }
        return res;
    }





}
