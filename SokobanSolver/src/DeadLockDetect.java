import java.util.HashSet;

public class DeadLockDetect {
    Sokoban soku;
    HashSet<Coordinate> walls;
    HashSet<Coordinate> storages;
    HashSet<Coordinate> deadlocks;
    int width;
    int height;
    private char[][] map;


    public DeadLockDetect(Sokoban soku){
        this.soku = soku;
        this.walls= this.soku.getWalls();
        this.storages= this.soku.getStorages();
        this.width = this.soku.getWidth();
        this.height = this.soku.getHeight();
        this.map = new char[height][width];
        loadMap();
    }

    public void findDeadLock(){

        int width = soku.getWidth();
        int height = soku.getHeight();


        for(int i=1; i<=height; i++){
            for(int j=1; j<=width; j++){
                Coordinate current = new Coordinate(i,j);
                if (!this.walls.contains(current) && !this.storages.contains(current)){
                    if(cornorTest(current)){
//                        this.deadlocks.add(current);
                        System.out.println(String.format( "**Cornor Deadlock Found**( %d , %d)" , current.getX() , current.getY()) );
                        map[i-1][j-1] = '^';
                        printMap();
                        System.out.println("----------------------");
                    }

                    if(!boundaryTest(current)){
//                        this.deadlocks.add(current);
                        System.out.println(String.format( "**Boundary Deadlock Found**( %d , %d)" , current.getX() , current.getY()) );
                        map[i-1][j-1] = '?';
                        printMap();
                        System.out.println("----------------------");
                    }

                }

            }
        }

    }

    public void loadMap() {
        int rows = height;
        int cols = width;
        map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = ' ';
            }
        }
        for (Coordinate e : walls) {
            map[e.getX()-1][e.getY()-1] = '#';
        }
        for (Coordinate e : storages) {
            map[e.getX()-1][e.getY()-1] = '!';
        }
        for (Coordinate e : soku.getBoxes()) {
            map[e.getX()-1][e.getY()-1] = '@';
        }
        map[soku.getPlayer().getX()-1][soku.getPlayer().getY()-1] = '*';
    }

    public void printMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean cornorTest(Coordinate current){
//        Coordinate up = new Coordinate(current.getX(), current.getY()+1);
//        Coordinate down = new Coordinate(current.getX(), current.getY()-1);
//        Coordinate right = new Coordinate(current.getX()+1, current.getY());
//        Coordinate left = new Coordinate(current.getX()-1, current.getY());

        Coordinate up = getUpNeighbor(current);
        Coordinate down = getdownNeighbor(current);
        Coordinate right = getRightNeighbor(current);
        Coordinate left = getLeftNeighbor(current);

        if(this.walls.contains(up) && this.walls.contains(right)||
                this.walls.contains(up) && this.walls.contains(left)||
                this.walls.contains(down) && this.walls.contains(left)||
                this.walls.contains(down) && this.walls.contains(right)){
            return true;
        }
        return false;
    }

    public boolean boundaryTest(Coordinate current){


        int x = current.getX();
        int y = current.getY();

        System.out.println(String.format( "____________new test_________  :  ( %d , %d) " , x , y ));


        if(this.walls.contains(getLeftNeighbor(current))){
            System.out.println("Left wall...");
            int upbound = this.findNearestUpbound(current);
            int downbound = this.findNearestDownbound(current);

            if(upbound==-1 || downbound ==-1){
                return false;
            }

            else {
                for (int m = upbound + 1; m < downbound; m++) {
                    Coordinate b = new Coordinate(x-1,m);
                    if (!this.walls.contains(b)) {
                        return false;
                    }
                }
            }
        }

        else if(this.walls.contains(getRightNeighbor(current))){
            System.out.println("Right wall...");
            int upbound = this.findNearestUpbound(current);
            int downbound = this.findNearestDownbound(current);

            if(upbound==-1 || downbound ==-1){
                return false;
            }

            else {
                for (int m = upbound + 1; m < downbound; m++) {
                    Coordinate b = new Coordinate(x+1,m);
                    if (!this.walls.contains(b)) {
                        return false;
                    }
                }

            }
        }

        else if(this.walls.contains(getUpNeighbor(current))){
            System.out.println("Up wall...");
            int rightbound = this.findNearestRightbound(current);
            int leftbound = this.findNearestLeftbound(current);

            if(leftbound==-1 || rightbound ==-1){
                return false;
            }

            else {
                for (int m = leftbound + 1; m < rightbound; m++) {
                    Coordinate b = new Coordinate(m,y+1);
                    if (!this.walls.contains(b)) {
                        return false;
                    }
                }
            }
        }

        else if(this.walls.contains(getdownNeighbor(current))){
            System.out.println("Down wall...");
            int rightbound = this.findNearestRightbound(current);
            int leftbound = this.findNearestLeftbound(current);

            if(leftbound==-1 || rightbound ==-1){
                return false;
            }

            else {
                for (int m = leftbound + 1; m < rightbound; m++) {
                    Coordinate b = new Coordinate(m,y-1);
                    if (!this.walls.contains(b)) {
                        return false;
                    }
                }
            }
        }

        return true;

    }

    private Coordinate getRightNeighbor (Coordinate current){
        return (new Coordinate(current.getX(), current.getY()+1));
    }

    private Coordinate getLeftNeighbor (Coordinate current){
        return (new Coordinate(current.getX(), current.getY()-1));
    }
    private Coordinate getUpNeighbor (Coordinate current){
        return (new Coordinate(current.getX()-1, current.getY()));
    }
    private Coordinate getdownNeighbor (Coordinate current){
        return (new Coordinate(current.getX()+1, current.getY()));
    }

    private int findNearestUpbound(Coordinate a){
        int y = a.getY();
        int x = a.getX()-1;

        while(x>=0){
            Coordinate temp = new Coordinate(x,y);
            if(this.walls.contains(temp)){
                System.out.println( String.format("~~upper bound  :  ( %d ) " , x ));
                return x;
            }
            else if(this.storages.contains(temp)){
                System.out.println( String.format("~~upper bound  :  ( %d ) " , -1 ));
                return -1;
            }
            x-=1;
        }
        System.out.println( String.format("~~upper bound  :  ( %d ) " , 0 ));
        return 0;
    }


    private int findNearestDownbound(Coordinate a){
        int y = a.getY();
        int x = a.getX()+1;

        while(x<this.height){
            Coordinate temp = new Coordinate(x,y);
            if(this.walls.contains(temp)){
                System.out.println( String.format("~~lower bound  :  ( %d ) " , x ));
                return x;
            }
            else if(this.storages.contains(temp)){
                System.out.println( String.format("~~lower bound  :  ( %d ) " , -1 ));
                return -1;
            }
            x+=1;
        }
        System.out.println( String.format("~~lower bound  :  ( %d ) " , this.height ));
        return this.height;
    }

    private int findNearestRightbound(Coordinate a){
        int y = a.getY()+1;
        int x = a.getX();

        while(y<this.width){
            Coordinate temp = new Coordinate(x,y);
            if(this.walls.contains(temp)){
                System.out.println( String.format("~~right bound  :  ( %d ) " , y ));
                return y;
            }
            else if(this.storages.contains(temp)){
                System.out.println( String.format("~~right bound  :  ( %d ) " , -1 ));
                return -1;
            }
            y+=1;
        }
        System.out.println( String.format("~~right bound  :  ( %d ) " , this.width ));
        return this.width;
    }

    private int findNearestLeftbound(Coordinate a){
        int y = a.getY()-1;
        int x = a.getX();

        while(y>=0){
            Coordinate temp = new Coordinate(x,y);
            if(this.walls.contains(temp)){
                System.out.println( String.format("~~left bound  :  ( %d ) " , y ));
                return y;
            }
            else if(this.storages.contains(temp)){
                System.out.println( String.format("~~left bound  :  ( %d ) " , -1 ));
                return -1;
            }
            y-=1;
        }
        System.out.println( String.format("~~right bound  :  ( %d ) " , 0 ));
        return 0;
    }




}
